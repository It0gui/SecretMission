package com.example.cloud;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
		//Intent messageIntent = new Intent("com.google.android.c2dm.intent.RECEIVE");
		registrationIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
		registrationIntent.putExtra("sender", "371946734881");
		//MyBroadcastReceiver r = new MyBroadcastReceiver();
		//r.onReceive(MyBroadcastReceiver.class, MyIntentService.class);
		this.startService(registrationIntent);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
