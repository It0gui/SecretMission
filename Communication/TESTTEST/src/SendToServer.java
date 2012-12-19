import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.ws.ProtocolException;


public class SendToServer implements Runnable{

	public Thread thread;
	public HttpsURLConnection connect = null;
	
	public SendToServer(URL url)
	{
		if( this.connect == null)
		try
		{
			this.connect = (HttpsURLConnection)url.openConnection();
			this.connect.setDoOutput(true);
			this.thread = new Thread(this);
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		else 
		{
			this.connect.disconnect();
		}
		
		
	};
	
	
	
	public void sendingData()
	{
		this.thread.start();		
	}
	
	public void run()
	{
		if (this.connect != null)
		{
			try 
			{
				String param = "param1=" + URLEncoder.encode("value1","UTF-8");
				
					this.connect.setRequestMethod("POST");
					this.connect.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");	
				    PrintWriter out = new PrintWriter(this.connect.getOutputStream());
				    out.print(param);
				    out.close();
				    
				    String response= "";

				    //start listening to the stream
				    Scanner inStream = new Scanner(this.connect.getInputStream());

				    //process the stream and store it in StringBuilder
				    while(inStream.hasNextLine())
				    response+=(inStream.nextLine());
				    System.out.println("Response from the server : " + response);

			  }
				
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			catch (ProtocolException e)
			{
				e.printStackTrace();
			}
			 catch (IOException e)
			    {
			    	e.printStackTrace();
			    }
			
			
			
			
		}
	}
	
	public static void main(String[] args) {
		try 
        {

            String https_url = "http://localhost/https/test.php?param1=toto";
            URL url = new URL(https_url);
            MyConnection myConnect = new MyConnection(url);
            if (myConnect.checkConnection() == true)
            {
            	System.out.println("Etat de mon connexion :  established");
            }
            //myConnect.gettingCertificates();
        }
        catch (MalformedURLException e)
        {
        	e.printStackTrace();
        }
	}

}
