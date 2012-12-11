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
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CompteActivity extends Activity 
{
	private String token=null;
    private String date=null;
    private String login=null;
    private String ip=null;
	private EditText editxt1 = null;
	private EditText editxt2 = null;
	private String t1;
	private String t2;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compte);

	    login=getIntent().getStringExtra("login");
        date=getIntent().getStringExtra("date_expiration");
        token=getIntent().getStringExtra("token");
        ip=getIntent().getStringExtra("ip");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_compte, menu);
		return true;
	}
	public void save(View view)
    {
		 try {
			 editxt1 = (EditText) findViewById(R.id.editText1);
	         editxt2= (EditText) findViewById(R.id.editText2);
	         t1=editxt1.getText().toString();
			 t2=editxt2.getText().toString();
			    HttpParams p = new BasicHttpParams();
		        HttpClient httpclient = new DefaultHttpClient(p); 
		        String url ="http://"+ip+"/PROJET_IF26/service.php?type=set_account&token="+token+"&email="+t1+"&pwd="+t2;
		 		HttpPost httppost = new HttpPost(url);
		        ResponseHandler<String> responseHandler = new BasicResponseHandler();
		        String responseBody = httpclient.execute(httppost,responseHandler);
		        JSONObject jso = new JSONObject(responseBody);
					 	if(jso.has("err"))
					 	{
					 		Toast.makeText(getApplicationContext(),"Erreur d'entrées..", Toast.LENGTH_LONG).show();	
					 	}
				finish();
	     	 } 
	 catch (Throwable t) 
	       	 {
		 		Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
	       	 }
    }
}
