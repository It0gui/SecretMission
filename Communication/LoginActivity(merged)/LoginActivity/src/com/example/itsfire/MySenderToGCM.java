package com.example.itsfire;

import java.io.IOException;

import com.google.android.gcm.server.Message;



public class MySenderToGCM 
{

	public static void main(String[] args) 
	{
     
		//String myApiKey = "key";
		//Sender sender1 = new Sender(myApiKey);
		//System.out.println(sender1);
		com.google.android.gcm.server.Sender sender = new com.google.android.gcm.server.Sender("AIzaSyDLbgzRPpGZrA1DLT14lupILxb4VrAKyCA");      
		Message message = new Message.Builder().collapseKey("1").timeToLive(3).delayWhileIdle(true).addData("message","trial message").build();
		System.out.println(message);
		if (message instanceof com.google.android.gcm.server.Message)
		{
			System.out.print("le message est bien de la classe message de google");
		}
		else System.out.print("oulaaaaa ! c'est quoi comme truc ?");

        try 
        {
            com.google.android.gcm.server.Result result = sender.send(message, "APA91bGf8q43uHxEKhqSlDHh5-5Bw-W6Kev3BHFPNUMxSe6sP2GwB2VcS9w8z6CYeEg-ux-jar9TzjPaxSsQBhLwvp38iul_31S7LMS7XgVzMvXtUDKv6_ZuzbD8yLswfPOeyTasBOQ7AbguY5syJt7z4GGtld2rgQ", 3);
            System.out.println(result.toString());
        } catch (IOException e) 
        {
            // TODO Auto-generated catch block
           e.printStackTrace();
        }
}

}
