package Client;
import Message.Message;

import java.util.*;
import java.io.*;
import java.net.*;


interface ModelListener
{
	void onMessageReceived();
	void onIOError();
}

class ClientModel 
{
	static final int port = 9000;

	protected String Username;
	protected String Hostname;

	protected Socket Connection;

	protected LinkedList<Message> Incoming;
	protected LinkedList<Message> Pending;

	protected LinkedList<ModelListener> Listeners;

	ClientModel(String u, String h)
	{
		Username = u;
		Hostname = h;
		
		Incoming = new LinkedList<Message>();
		Pending = new LinkedList<Message>();

		Listeners = new LinkedList<ModelListener>();
	}

	void connect() throws IOException
	{
		Connection = new Socket(InetAddress.getByName(Hostname), port);
	}

	void addListener(ModelListener l)
	{
		Listeners.add(l);
	}

	void addMessage(String text)
	{
		synchronized(Pending)
		{
			Pending.offerLast(new Message(Username, text));
		}
	}

	
	
}

class ServerReceiver extends ClientModel implements Runnable
{
	private ObjectInputStream Receiver;
	
	ServerReceiver(ClientModel c) throws IOException	
	{
		super(c.Username, c.Hostname);
                
	       	Connection = c.Connection;
                
               	Incoming = c.Incoming;
		Pending = c.Pending;
		Listeners = c.Listeners;

		Receiver = new ObjectInputStream(Connection.getInputStream());
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

				synchronized(Incoming)
				{
					Incoming.add((Message) Received);
				}
				for(ModelListener l:Listeners)
					l.onMessageReceived();

			}
			catch(IOException e)
			{
				//TODO: handling errors here
				for(ModelListener l:Listeners)
					l.onIOError();
				return;
			}
			catch(Exception e)
			{
				System.out.println("FATAL ERROR; ABORTING");
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
}

class ServerSender extends ClientModel implements Runnable
{
	private ObjectOutputStream Sender;
	
	ServerSender(ClientModel c) throws IOException
	{
		super(c.Username, c.Hostname);
                
	       	Connection = c.Connection;
                
               	Incoming = c.Incoming;
		Pending = c.Pending;
		Listeners = c.Listeners;

		Sender = new ObjectOutputStream(Connection.getOutputStream());
	}

	public void run()
	{
		try
		{
			// Greeting message for server to know our username
			Message msg = new Message(Username, "");
			Sender.writeObject(msg);
			
			while(true)
			{
				Message toBeSent;
				synchronized(Pending)
				{
					toBeSent = Pending.pollFirst();
				}
				if(toBeSent == null)
					Thread.sleep(100);
				else
					Sender.writeObject(toBeSent);
			}
		}
		catch(IOException e)
		{
			//TODO: handling errors here
			for(ModelListener l:Listeners)
				l.onIOError();
			return;
		}
		catch(InterruptedException e)
		{
			return;
		}
		catch(Exception e)
		{
			System.out.println("FATAL ERROR; ABORTING");
			e.printStackTrace();
			System.exit(-1);
		}
		
	}

}
