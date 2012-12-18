import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class Test {

	
	public static void main(String[] args) {
		String a = "test";
		try 
		{
			 Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			 Cipher cipher = Cipher.getInstance("RSA", "BC");
			 
			 /*
			 KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
			 RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger("12345678", 16), new BigInteger("11", 16));
		     RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(new BigInteger("12345678", 16), new BigInteger("343",16));
		     RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
		     RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);*/
			 
			 KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
			 keyGen.initialize(1024);
			 KeyPair key = keyGen.generateKeyPair();
			 Key pubKey = key.getPublic();
			 Key privKey = key.getPrivate();

		
		     
		     System.out.println("public key : "+ pubKey);
		     System.out.println("private key : "+ privKey.toString());
		     //System.out.println("provider : "+ keyFactory.getProvider());
		     
		     cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		     byte[] input = new byte[] { 1, 2, 3, 54 };
		     byte[] cipherText = cipher.doFinal(input);
		     String encoded = "";
		     
		     // en hexa .................................................................
		     StringBuffer buffer = new StringBuffer();
		    	 
		    	    for(byte b : cipherText) {
		    	      buffer.append(Integer.toHexString(b));
		    	      buffer.append(" ");
		    	    }
		    	 
		    	    System.out.println(buffer.toString().toUpperCase());   
		    	    
		    // en tableau de chars......................................................
		    	    char ch;
		    	    System.out.println("en mode char :");
		    	    for (int i = 0; i< cipherText.length; i++)
		    	    {
		    	    	ch = (char)cipherText[i];
		    	    	System.out.print(ch);
		    	    }
		    a = new String(cipherText);  
		     System.out.println("cipher: " + a);
		     
		     
		     
		     
		     cipher.init(Cipher.DECRYPT_MODE, privKey);
		     byte[] plainText = cipher.doFinal(cipherText);
		     System.out.println("plain : " +plainText[3]);
		    //   cipher.doFinal(a.getBytes());
		}
		catch (NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		/*
		catch (InvalidKeySpecException e)
		{
			e.printStackTrace();
		}
		*/
		catch (IllegalBlockSizeException e)
		{
			e.printStackTrace();
		}
		catch (BadPaddingException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchProviderException e)
		{
			e.printStackTrace();
		}
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeyException e)
		{
			e.printStackTrace();
		}
		
		
		
		//SecretKeySpec k = new SecretKeySpec();
        
	
		
	}

}
