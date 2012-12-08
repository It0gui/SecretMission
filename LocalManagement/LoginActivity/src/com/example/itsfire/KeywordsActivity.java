package com.example.itsfire;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class KeywordsActivity extends Activity 
{
	private String token=null;
    private String date=null;
    private String login=null;
    private String ip=null;
    public  ListView list;
    JSONArray jsoArray;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keywords);
		login=getIntent().getStringExtra("login");
        date=getIntent().getStringExtra("date_expiration");
        token=getIntent().getStringExtra("token");
        ip=getIntent().getStringExtra("ip");
        MAJ();
	}
	
	public void MAJ()
	{
		 	try
		 	{
				HttpParams p = new BasicHttpParams();
		        HttpClient httpclient = new DefaultHttpClient(p); 
		        String url ="http://"+ip+"/PROJET_IF26/service.php?type=get_keyword&token="+token;
		 		HttpPost httppost = new HttpPost(url);
		        ResponseHandler<String> responseHandler = new BasicResponseHandler();
		        String responseBody = httpclient.execute(httppost,responseHandler);
		        JSONObject jso = new JSONObject(responseBody);
		        jsoArray = jso.getJSONArray("keywords");
		        
		        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
		    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    	list = (ListView) findViewById(R.id.listeNotes);
	    	    List<String> exemple = new ArrayList<String>();
		        
		        for(int i=0;i<jsoArray.length();i++)
				{
		        	JSONObject rec = jsoArray.getJSONObject(i);
		        	exemple.add(rec.getString("keyword"));
				}
		        
		    	    		    	        
		    	    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, exemple);
		    	    list.setAdapter(adapter);
		    	    
			}
	        catch (Throwable t) 
	       	 {
		 		Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
	       	 }
	}
	
	 public void removeKeyword(View view)
	 {
		 try
		 	{
			 	list = (ListView) findViewById(R.id.listeNotes);
			 	int i=list.getCheckedItemPosition();
			 	if(i>=0)
			 	{
				 	JSONObject rec = jsoArray.getJSONObject(i);
		        	HttpParams p = new BasicHttpParams();
			        HttpClient httpclient = new DefaultHttpClient(p); 
			        String url ="http://"+ip+"/PROJET_IF26/service.php?type=remove_keyword&token="+token+"&key_word="+rec.getString("keyword");
			 		HttpPost httppost = new HttpPost(url);
			        ResponseHandler<String> responseHandler = new BasicResponseHandler();
			        String responseBody = httpclient.execute(httppost,responseHandler);
			        MAJ();
			 	}
			}
	        catch (Throwable t) 
	       	 {
		 		Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
	       	 }
		 
	 }
	 public void addKeyword(View view)
	 {
		 try
		 {
			EditText editxt1 = (EditText) findViewById(R.id.editText1);
			String keyword=editxt1.getText().toString();
			if(!keyword.isEmpty())
		 	{
				HttpParams p = new BasicHttpParams();
		        HttpClient httpclient = new DefaultHttpClient(p); 
		        String url ="http://"+ip+"/PROJET_IF26/service.php?type=add_keyword&token="+token+"&key_word="+keyword;
		 		HttpPost httppost = new HttpPost(url);
		        ResponseHandler<String> responseHandler = new BasicResponseHandler();
		        String responseBody = httpclient.execute(httppost,responseHandler);
		        MAJ();
		        editxt1.setText("");
		 	}
		 }
	    catch (Throwable t) 
	       	 {
		 		Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
	       	 }
	 }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_keywords, menu);
		return true;
	}

}
