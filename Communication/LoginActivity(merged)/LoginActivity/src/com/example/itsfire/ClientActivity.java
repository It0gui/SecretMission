package com.example.itsfire;


import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ClientActivity extends Activity 
{
	private String token=null;
    private String date=null;
    private String login=null;
    private String ip=null;
    ImageView etat;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client);
		 	
		    login=getIntent().getStringExtra("login");
	        date=getIntent().getStringExtra("date_expiration");
	        token=getIntent().getStringExtra("token");
	        ip=getIntent().getStringExtra("ip");
	}
    public void onResume()
    {
    	super.onResume();
        setContentView(R.layout.activity_client);
	    
	}
	public boolean getState()
	 {
		 try {
			    HttpParams p = new BasicHttpParams();
		        HttpClient httpclient = new DefaultHttpClient(p); 
		        String url ="http://"+ip+"/PROJET_IF26/service.php?type=check_account&token="+token;
		        HttpPost httppost = new HttpPost(url);
		        ResponseHandler<String> responseHandler = new BasicResponseHandler();
		        String responseBody = httpclient.execute(httppost,responseHandler);
		        JSONObject jso = new JSONObject(responseBody);
					 	if(jso.has("err"))
					 	{
					 		Toast.makeText(getApplicationContext(),"Erreur d'entrées..", Toast.LENGTH_LONG).show();	
					 		return false;
					 	}
				    	else
				    	{
				    		String s=jso.getString("etat");
				    		return (s.compareTo("ok")==0);
				    	}            
	     	 } 
	 catch (Throwable t) 
	       	 {
		 		Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
		 		return false;
	       	 }

	 }
	 public void viewEtat(View view)
	 {
		    boolean etat=getState(); 
		    if(etat) 
		    	Toast.makeText(getApplicationContext(),"Le service est OK..", Toast.LENGTH_LONG).show();	
	        else 
	        	Toast.makeText(getApplicationContext(),"Le service n'est pas OK..", Toast.LENGTH_LONG).show();	
	 }
	 public void gestCompte(View view)
	 {
			Intent intent=new Intent(this,CompteActivity.class);
			intent.putExtra("login",login);
			intent.putExtra("token",token);
			intent.putExtra("date_expiration",date);
			intent.putExtra("ip",ip);
			startActivity(intent);	 
	 }
	 public void gestKeywords(View view)
	 {
			Intent intent=new Intent(this,KeywordsActivity.class);
			intent.putExtra("login",login);
			intent.putExtra("token",token);
			intent.putExtra("date_expiration",date);
			intent.putExtra("ip",ip);
			startActivity(intent);	 
	 }
	 public void viewAlertes(View view)
	 {
			Intent intent=new Intent(this,AlertesActivity.class);
			intent.putExtra("login",login);
			intent.putExtra("token",token);
			intent.putExtra("date_expiration",date);
			intent.putExtra("ip",ip);
			startActivity(intent);	 
	 }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_client, menu);
		return true;
	}

}
