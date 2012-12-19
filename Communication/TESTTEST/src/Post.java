import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.ws.ProtocolException;
import javax.xml.ws.WebServiceException;


public class Post {

	
	public static void main(String[] args) {
		try 
		{
			URL url2 = new URL("https://pastel.diplomatie.gouv.fr/fildariane/index2.html"); 
			URL url = new URL("https://www.google.fr?tata=mama");
			HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
			connection.setInstanceFollowRedirects( true );
			connection.connect();
	
             
			System.out.println("Resp Code:"+connection.getResponseCode()); 
			System.out.println("Resp Message:"+ connection.getResponseMessage()); 
			
			
			String location = connection.getHeaderField( "Location" );
			System.out.println( location );
			
			
			try {
				 
				   System.out.println("****** Content of the URL ********");			
				   BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			       String input;
			       while ((input = br.readLine()) != null)
			       {
				      System.out.println(input);
				   }
				   br.close();
			 
				} catch (IOException e) {
				   e.printStackTrace();
				}
            
			
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		 catch (IOException pe)
 		{
 			pe.printStackTrace();
 		} 
		
		catch (WebServiceException e)
		{
			e.printStackTrace();
		}
		
	}

}
