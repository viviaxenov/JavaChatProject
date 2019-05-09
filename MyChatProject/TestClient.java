package MyChatProject;

import java.util.*;
import java.io.*;
import java.net.*;


public class TestClient 
{
	static final int port = 9000;
	
	public static void main(String[] args) throws IOException
	{
		String UserName = "Big Sych";
	
		if(args.length > 0)	
		{
			UserName = args[0];
		}
	
		try
		{
			Socket sender = new Socket("localhost", port); 

			ObjectOutputStream writer = new ObjectOutputStream(sender.getOutputStream());
			writer.writeObject(UserName);
			
			while(true)
			{
				System.out.println("Type your message: ");
				String msgText = System.console().readLine();
				Message msg = new Message(UserName, msgText);
				writer.writeObject(msg);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}

		
		

	}
}
