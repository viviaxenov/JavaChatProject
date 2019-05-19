import java.util.*;
import java.io.*;
import java.net.*;


public class TextCient 
{
	static final int port = 9000;
	
	public static void main(String[] args) 
	{
		String UserName = "Big Sych";
	
		if(args.length > 0)	
		{
			UserName = args[0];
		}
	
		try
		{
			Socket sock = new Socket("localhost", port); 

			ObjectOutputStream writer = new ObjectOutputStream(sock.getOutputStream());
			ObjectInputStream receiver = new ObjectInputStream(sock.getInputStream());

			writer.writeObject(new Message(UserName, ""));

			(new RecvPrinter(receiver)).start();
			
			while(true)
			{
				String msgText = System.console().readLine();
				if(msgText.startsWith("\\exit"))
					System.exit(1);
				Message msg = new Message(UserName, msgText);
				writer.writeObject(msg);
				Thread.sleep(5000);
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}

		
		

	}
}

class RecvPrinter extends Thread
{
	ObjectInputStream Receiver;

	RecvPrinter(ObjectInputStream r)
	{
		Receiver = r;		
	}

	public void run()
	{
		while(true)
		{
			try
			{
				Object Received = Receiver.readObject();

				if (! (Received instanceof Message))
					throw new IllegalArgumentException();

				System.out.println(((Message) Received).toString());
			}
			catch(IOException e)
			{
				System.out.println("Connection error; Details:\n");
				e.printStackTrace();
				System.exit(-1);
			}
			catch(IllegalArgumentException | ClassNotFoundException e)
			{
				System.out.println("Bad input from server; Details:\n");
				e.printStackTrace();
			}
		}
	}
}
