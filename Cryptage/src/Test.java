import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

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
			 KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
			 RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger("12345678", 16), new BigInteger("11", 16));
		     RSAPrivateKeySpec privKeySpec = new RSAPrivateKeySpec(new BigInteger("12345678", 16), new BigInteger("12345678",16));
		     RSAPublicKey pubKey = (RSAPublicKey) keyFactory.generatePublic(pubKeySpec);
		     RSAPrivateKey privKey = (RSAPrivateKey) keyFactory.generatePrivate(privKeySpec);
		     cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		     byte[] input = new byte[] { 1, 2, 3 };
		     byte[] cipherText = cipher.doFinal(input);
		     System.out.println("cipher: " + new String(cipherText));
		    //   cipher.doFinal(a.getBytes());
		}
		catch (NoSuchPaddingException e)
		{
			e.printStackTrace();
		}
		catch (InvalidKeySpecException e)
		{
			e.printStackTrace();
		}
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
