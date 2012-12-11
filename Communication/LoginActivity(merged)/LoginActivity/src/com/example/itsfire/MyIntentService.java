package com.example.itsfire;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

public class MyIntentService extends IntentService 
{

	private static PowerManager.WakeLock sWakeLock;
    private static final Object LOCK = MyIntentService.class;
	private static final String TAG = "Ma premiere application";
	
	private String login;
	private String pwd;
	private String ip;
	private String token;
	
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
            this.ip = intent.getStringExtra("ip");
            this.token = intent.getStringExtra("token");
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
    
    private void handleRegistration(Intent intent) 
    {
        String registrationId = intent.getStringExtra("registration_id");
        String error = intent.getStringExtra("error");
        String unregistered = intent.getStringExtra("unregistered");       
        // registration succeeded
        if (registrationId != null) {
        	Log.i(TAG, "Registration succeeded !!! " );
        	Log.i(TAG, registrationId );
        	
            // store registration ID on shared preferences
            // notify 3rd-party server about the registered ID
        	
        	try {
    		    HttpParams p = new BasicHttpParams();
    	        HttpClient httpclient = new DefaultHttpClient(p); 
    	        String url ="http://"+this.ip+"/PROJET_IF26/service.php?type=set_IDGCM&token="+this.token+"&IDGCM="+registrationId;
    	        HttpPost httppost = new HttpPost(url);
    	        ResponseHandler<String> responseHandler = new BasicResponseHandler();
    	        String responseBody = httpclient.execute(httppost,responseHandler);
    	        JSONObject jso = new JSONObject(responseBody);
    				 	if(jso.has("err"))
    				 	{
    				 		if(jso.has("err"))
    				 		{
    				 		  	Toast.makeText(getApplicationContext(),"Erreur d'entrées..", Toast.LENGTH_LONG).show();	
    				 		}
    				 	}
    			Intent clientIntent = new Intent(this, ClientActivity.class);
    			clientIntent.putExtra("ip",this.ip);
    			clientIntent.putExtra("token",this.token);
    			clientIntent.putExtra("login",this.login);
    			clientIntent.putExtra("password",this.pwd);
    			startActivity(clientIntent);
       	 	} 
       	 catch (Throwable t) 
        	 {
    	 		Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
        	 }
        	
        	
        	
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
