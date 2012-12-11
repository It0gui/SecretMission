package com.example.itsfire;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver{

	 public final void onReceive(Context context, Intent intent) {
	        MyIntentService.runIntentInService(context, intent);
	        setResult(Activity.RESULT_OK, null, null);
	    }

	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
