import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;


public class MyConnection implements Runnable{

	public Thread thread;
	public HttpsURLConnection connect = null;
	
	public MyConnection(URL url)
	{
		if( this.connect == null)
		try
		{
			this.connect = (HttpsURLConnection)url.openConnection();
			System.out.println(this.connect.getResponseCode());
			this.thread = new Thread(this);
			this.thread.start();
			
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
	
	public boolean checkConnection()
	{
		if (this.connect == null)
		{
			return false;
		}
		return true;
	}
	
	public void showCertificate(Certificate certificate)
	{
		System.out.println("My Connection  Cert Type : "+  certificate.getType());
		System.out.println("My Connection  Public Key Algorithm : " + certificate.getPublicKey().getAlgorithm());
		System.out.println("My Connection  Public Key Format : " + certificate.getPublicKey().getFormat());	
		try
		{
		byte[] certificateEnc = certificate.getEncoded();
		System.out.println("My Connection  Issuer : " + new String(certificateEnc));
		}
		catch (CertificateEncodingException e)
		{
			e.printStackTrace();
		}
	}
	

	
	public void gettingCertificates()
	{
		this.thread.start();		
	}
	
	public void run()
	{
		Certificate[] certs = new Certificate[0];
		if (this.checkConnection())
		{
			//this.connect.setDoOutput(false);
			//this.connect.setDoInput(true);
			
			System.out.println("Running : Connection : established");
			try
			 {
			   certs = this.connect.getServerCertificates();
			   this.connect.connect();
			   if (certs.length != 0)
			   {
				   System.out.println("My Connection : Starting to dump certificates ");
			       for(Certificate certificate : certs)
			         {
			           showCertificate(certificate);
				     }
			   }
			   else  System.out.println("My Connection :  No Certificates at all ");
			   //this.connect.setDoOutput(true);
			   //this.connect.setRequestMethod("POST");
			   //String param="param1=" + URLEncoder.encode("message1 to dacha","UTF-8");
			   //this.connect.setRequestProperty("Content-length",String.valueOf (param.length()));
			   //PrintWriter out = new PrintWriter(this.connect.getOutputStream());
			   //out.print(param);
					   
			 } 
		    catch (SSLPeerUnverifiedException e) 
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

            String https_url = "https://www.google.com/";
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
