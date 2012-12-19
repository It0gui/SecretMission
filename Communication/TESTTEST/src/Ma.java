import java.net.MalformedURLException;
import java.net.URL;


public class Ma {

	public Ma(){};
	
	public void hello ()
	{
		System.out.println("hello");
	}
	public static void main(String[] args) {
		
		Ma ma = new Ma();
		ma.hello();
		try 
		{
			String https_url = "https://www.google.com/";
	        URL url = new URL(https_url);
	        MyConnection myConnect = new MyConnection(url);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		for (int i = 0; i<10; i++)
		{
			ma.hello();
		}
		
		
		

	}

}
