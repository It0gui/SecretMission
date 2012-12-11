package com.example.itsfire;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity 
{
	
	
	
	
	protected static final int TIMEOUT_MILLISEC = 10000;
	private EditText editxt1 = null;
	private EditText editxt2 = null;
	private EditText editxt3 = null;
	private String login;
	private String pwd;
	private String ip;
	private String RegistrationId;

    public boolean isIpFormat(String ip)
    {
        Pattern pattern;
        Matcher matcher;
        pattern= Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
        matcher = pattern.matcher(ip);
        return matcher.find();  
    }
    
    public void seConnecter(View view)
    {
    	 editxt1 = (EditText) findViewById(R.id.editText1);
         editxt2= (EditText) findViewById(R.id.editText2);
         editxt3 = (EditText) findViewById(R.id.editText3);    
       	 this.login=editxt2.getText().toString();
		 this.pwd=editxt3.getText().toString();
		 this.ip=editxt1.getText().toString();
	
		 if(isIpFormat(ip))
		        {
		        	 try {
			 			    HttpParams p = new BasicHttpParams();
			 		        HttpClient httpclient = new DefaultHttpClient(p); 
			 		        String url ="http://"+ip+"/PROJET_IF26/service.php?type=login&login="+login+"&pwd="+pwd;
			 		        HttpPost httppost = new HttpPost(url);
					        ResponseHandler<String> responseHandler = new BasicResponseHandler();
					        String responseBody = httpclient.execute(httppost,responseHandler);
					        JSONObject jso = new JSONObject(responseBody);
								 	if(jso.has("err"))
								 	{
								 		switch(jso.getInt("err"))
								 		{
								 		case 1:
								 			Toast.makeText(getApplicationContext(),"vous etes banni (durée :"+jso.getString("interval")+")", Toast.LENGTH_LONG).show();
								 			break;
								 		case 2:
								 			Toast.makeText(getApplicationContext(),"cet utilisateur n'existe pas..", Toast.LENGTH_LONG).show();
								 			break;
								 		case 3:
								 			Toast.makeText(getApplicationContext(),"Password erronée", Toast.LENGTH_LONG).show();
								 			break;							 		
								 		default:
									    	Toast.makeText(getApplicationContext(),"Erreur d'entrées..", Toast.LENGTH_LONG).show();	
								 		}
								 	}
							    	else
							    	{
							    		if(jso.has("ID"))
							    		{
								    		//sendID(GCM(),jso.getString("token"));
							    			
							    			//if the client doesn't have any registration id, it throws an intent to register it to GCM
							    			//in this intent, what we'll do is that we :
							    			// 1 : reister to the GCM service
							    			// 2 :once the registration id received, we send it to the server
							    			
							    			Intent registerMeIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
							    			registerMeIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
							    			registerMeIntent.putExtra("sender", "371946734881");
							    			registerMeIntent.putExtra("login",login);
							    			registerMeIntent.putExtra("token",jso.getString("token"));
							    			registerMeIntent.putExtra("date_expiration",jso.getString("Date_expiration"));
							    			registerMeIntent.putExtra("ip",ip);
							    			
							    			this.startService(registerMeIntent);
							    			
							    			//redirection(jso,MyIntentService.class);
								 		}
							    		redirection(jso,ClientActivity.class);
							    	}            
				     	 } 
				 catch (Throwable t) 
				       	 {
					 		Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
				       	 }

		        }
		 else
		 {
			 Toast.makeText(getApplicationContext(),"Adresse IP Non Valide", Toast.LENGTH_LONG).show();
		 }
		        
    }
    
    
    public void sendID(String IDGCM,String token)
    {
   	 try {
		    HttpParams p = new BasicHttpParams();
	        HttpClient httpclient = new DefaultHttpClient(p); 
	        String url ="http://"+ip+"/PROJET_IF26/service.php?type=set_IDGCM&token="+token+"&IDGCM="+IDGCM;
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
			             
   	 	} 
   	 catch (Throwable t) 
    	 {
	 		Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
    	 }

    }
    
    
    public void GCM()
    
    {
    	//Intent GCMIntent = new Intent(this, MainActivity.class);
    	//startActivityForResult(GCMIntent, 110);
    	
    	Intent registrationIntent = new Intent("com.google.android.c2dm.intent.REGISTER");
		registrationIntent.putExtra("app", PendingIntent.getBroadcast(this, 0, new Intent(), 0));
		registrationIntent.putExtra("sender", "371946734881");
		this.startService(registrationIntent);
    	
    	//return this.RegistrationId;
    }
    
    
    
    public void redirection(JSONObject jso,Class x) throws JSONException
    {
		Intent intent=new Intent(this,x);
		intent.putExtra("login",login);
		intent.putExtra("token",jso.getString("token"));
		intent.putExtra("date_expiration",jso.getString("Date_expiration"));
		intent.putExtra("ip",ip);
		startActivity(intent);
    }
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		Intent intent = getIntent();
	    String RegistrationId = intent.getStringExtra("id");
	    this.RegistrationId = RegistrationId;

	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		Intent intent = getIntent();
		String RegistrationId = intent.getStringExtra("id");
	    this.RegistrationId = RegistrationId;
	    
	 
       return true;
    }
}
