package com.example.httptest;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import android.util.Log;

public class MyConnection implements Runnable{
	
	public Thread thread;
	public HttpsURLConnection connect;
	String url;
	
	public MyConnection(String url)
	{
		this.thread = new Thread(this);
		this.url = url;
		Log.v("My Connection : ", " instantiating connection");
	}
	
	public void giveMyConnection(URL url)
	{
		this.connect = null;
		try
		{
			this.connect = (HttpsURLConnection)url.openConnection();
			Log.v("My Connection   :  ", "Response Code  " + this.connect.getResponseCode());
			//this.thread = new Thread(this);
			//this.thread.start();
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
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
		Log.v("My Connection  Cert Type : " ,  certificate.getType());
		Log.v("My Connection  Public Key Algorithm : " , certificate.getPublicKey().getAlgorithm());
		Log.v("My Connection  Public Key Format : " , certificate.getPublicKey().getFormat());	
		try
		{
		byte[] certificateEnc = certificate.getEncoded();
		Log.v ("My Connection  Issuer : " , new String(certificateEnc));
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
		try 
		{
			URL MyUrl = new URL(this.url);
			this.connect = null;
			try
			{
				this.connect = (HttpsURLConnection)MyUrl.openConnection();
				Log.v("My Connection   :  ", "Response Code  " + this.connect.getResponseCode());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			Certificate[] certs = new Certificate[0];
			if (this.connect != null)
			{
				Log.v("Running : ", "Connection : established");
				try
				 {
				   certs = this.connect.getServerCertificates();
				   this.connect.connect();
				   if (certs.length != 0)
				   {
					   Log.v("My Connection :", " Starting to dump certificates ");
				       for(Certificate certificate : certs)
				         {
				           showCertificate(certificate);
					     }
				   }
				   else  Log.v("My Connection :", "  No Certificates at all ");
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
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		

}}
