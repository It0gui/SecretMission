package com.example.itsfire;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class TeestActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teest);
		MAJ();
		//ADD();
		//REMOVE();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_teest, menu);
		return true;
	}
     public void ADD()
     {
    	 	NoteBD noteBD = new NoteBD(this);
	        noteBD.open();
	       
     }
	 public void MAJ()
	    {
	        NoteBD noteBD = new NoteBD(this);
	        noteBD.open(); 
	        note [] m = noteBD.getNotes();
	        if(m==null)
	        {
	        	 Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_LONG).show();
	        }
	        else
	        {
	        	for(int i=0;i<m.length;i++)
				{
		        	Toast.makeText(getApplicationContext(), m[i].toString(), Toast.LENGTH_LONG).show();
				}
		        
	        }
	        Toast.makeText(getApplicationContext(), "END", Toast.LENGTH_LONG).show();
	        noteBD.close();   
	    }
	 
	 public void REMOVE()
	    {
	    	NoteBD noteBD = new NoteBD(this);
	        noteBD.open();
	        //Calendar c = Calendar.getInstance(); 
	    	//Date dateNow=new Date(c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR));
	    	//Date dateX=new Date(m.getDate());
	    			noteBD.removeNoteWithID(1);
	    			Toast.makeText(this, "Done!", Toast.LENGTH_LONG).show();
	    		//	MAJ();
	    }
}
