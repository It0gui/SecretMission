package com.example.itsfire;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class AlertesActivity extends Activity 
{
	private String token=null;
    private String date=null;
    private String login=null;
    private String ip=null;
    public  ListView list;
 
    ArrayList<Integer> idNotification = new ArrayList<Integer>();
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alertes);
		login=getIntent().getStringExtra("login");
        date=getIntent().getStringExtra("date_expiration");
        token=getIntent().getStringExtra("token");
        ip=getIntent().getStringExtra("ip");
        /*
	        ajouter_alerte("Subject1","keeeeeeeeeeeey");
	        ajouter_alerte("Subject2","keeeeeeeeeeeey","date2");
	        ajouter_alerte("Subject3","keeeeeeeeeeeey","date3");
	        ajouter_alerte("Subject4","keeeeeeeeeeeey","date4");
	        ajouter_alerte("Subject5","keeeeeeeeeeeey","date5");
       */
        //ajouter_alerte("Subject1","keeeeeeeeeeeey");
        ajouter_alerte("Subject2","keeeeeeeeeeeey");
        
        //this.deleteDatabase("notesNotification.db");
        
        MAJ();
    }
    
	//s1 Subject,S2 keeywords qui matchent
	public void ajouter_alerte(String s1,String s2)
    {
    	NoteBD noteBD = new NoteBD(getApplicationContext());
        noteBD.open();
        Calendar c = Calendar.getInstance(); 
        String date_=c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR)+" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":"+c.get(Calendar.SECOND);
        note n1 = new note(0,s1,s2,date_,false);
	    noteBD.insertNote(n1);
	    noteBD.close();
    }
    
    
	public void MAJ()
	{
		 	try
		 	{
		 		  	NoteBD noteBD = new NoteBD(getApplicationContext());
			        noteBD.open(); 
			        note [] m = noteBD.getNotes();
			        list = (ListView) findViewById(R.id.listeNotes);
		    	    List<String> exemple = new ArrayList<String>();
		    	    idNotification.clear();
		    	    String s;
			        if(m!=null)
			        {
			        	for(int i=0;i<m.length;i++)
						{
			        		s="";
			        		idNotification.add(m[i].getId());
			        		if(!m[i].isRead()) s="[NEW] ";
			        		s+=m[i].getSubject();
			        		exemple.add(s);
						}
				        
			        }	 
			        noteBD.close();
			        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, exemple);
			        list.setAdapter(adapter);
		    	    
			}
	        catch (Throwable t) 
	       	 {
		 		Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
	       	 }
	}
	
	public void removeNotification(View view)
	{
		list = (ListView) findViewById(R.id.listeNotes);
	 	int i=list.getCheckedItemPosition();
	 	if(i>=0)
	 	{
			NoteBD noteBD = new NoteBD(this);
	        noteBD.open();
	        noteBD.removeNoteWithID(idNotification.get(i));
	        noteBD.close();
	    	Toast.makeText(this, "Done!", Toast.LENGTH_LONG).show();
	    	MAJ();
	 	}
	}
	public void viewNotification(View view)
	{
		list = (ListView) findViewById(R.id.listeNotes);
	 	int i=list.getCheckedItemPosition();
	 	if(i>=0)
	 	{
		 	NoteBD noteBD = new NoteBD(this);
	        noteBD.open();
	        //Toast.makeText(getApplicationContext(), ""+idNotification.get(i), Toast.LENGTH_LONG).show();
	        note m=noteBD.getNoteWithID(idNotification.get(i));
	        noteBD.updateRead(idNotification.get(i));
	        noteBD.close();
	        Toast.makeText(getApplicationContext(), m.toString(), Toast.LENGTH_LONG).show();
	        MAJ();
	 	}
        
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_alertes, menu);
		return true;
	}

}
