package com.example.cloud;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

public class MyIntentService extends IntentService{

	private static PowerManager.WakeLock sWakeLock;
    private static final Object LOCK = MyIntentService.class;
	private static final String TAG = "Ma premiere application";
    
    public MyIntentService(){
    	super(null);
    }
    
    static void runIntentInService(Context context, Intent intent) {
        synchronized(LOCK) {
            if (sWakeLock == null) {
                PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
                sWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "my_wakelock");
            }
        }
        sWakeLock.acquire();
        intent.setClassName(context, MyIntentService.class.getName());
        context.startService(intent);
    }
    
    @Override
    public final void onHandleIntent(Intent intent) {
        try {
            String action = intent.getAction();
            if (action.equals("com.google.android.c2dm.intent.REGISTRATION")) {
                handleRegistration(intent);
            } else if (action.equals("com.google.android.c2dm.intent.RECEIVE")) {
                //handleMessage(intent);
            	 Log.i(TAG, "Je ne recois pas des messages ");
            	 handleMessage(intent);
            }
        } finally {
            synchronized(LOCK) {
                sWakeLock.release();
            }
        }
    }
    
    private void handleRegistration(Intent intent) {
        String registrationId = intent.getStringExtra("registration_id");
        String error = intent.getStringExtra("error");
        String unregistered = intent.getStringExtra("unregistered");       
        // registration succeeded
        if (registrationId != null) {
        	Log.i(TAG, "Registration succeeded !!! " );
        	Log.i(TAG, registrationId );
            // store registration ID on shared preferences
            // notify 3rd-party server about the registered ID
        }
            
        // unregistration succeeded
        if (unregistered != null) {
            // get old registration ID from shared preferences
            // notify 3rd-party server about the unregistered ID
        } 
            
        // last operation (registration or unregistration) returned an error;
        if (error != null) {
            if ("SERVICE_NOT_AVAILABLE".equals(error)) {
               // optionally retry using exponential back-off
               // (see Advanced Topics)
            } else {
                // Unrecoverable error, log it
                Log.i(TAG, "Received error: " + error);
            }
        }
    }
    
    public void handleMessage (Intent intent){
    	String message = intent.getStringExtra("message");
    	Log.i(TAG, message);
    	
    }

}
