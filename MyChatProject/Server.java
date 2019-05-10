package MyChatProject;

import java.util.*;
import java.io.*;
import java.net.*;


public class Server
{

	static final int port = 9000;

	private int IDCount;
	private LinkedList<User> Users;
	private ServerSocket Listener;
	LinkedList<Message> MessageQueue;

	public Server(int port) throws IOException
	{
		IDCount = 0;
		MessageQueue = new LinkedList<Message>();
		Users = new LinkedList<User>();
		Listener = new ServerSocket(port);
	}

	public void start() throws IOException
	{
		while(true)
		{
			Socket sock = Listener.accept();
			InputStream is = sock.getInputStream();
		
			ObjectInputStream Receiver = new ObjectInputStream(is);
			ObjectOutputStream Sender = new ObjectOutputStream(sock.getOutputStream());
			Object input;
			try
			{
				input = Receiver.readObject();
				if(! (input instanceof String))
					throw new IllegalArgumentException();
				String UserName = (String) input;	
				Date LoginTime = new Date();
				IDCount++;	

				User NewUser = new User(UserName, Users.listIterator(0), sock, Receiver, Sender);
				
				Users.add(NewUser);

				new UserReceiver(NewUser, MessageQueue).start();
				new UserSender(NewUser).start();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
				continue;
			}
			catch(Exception e)
			{
				System.out.println("Wrong request from " + sock.getInetAddress().getHostName());
				sock.close();
				continue;
			}
		}
	}


	public static void main(String[] args)
	{
		Server serv;
		try
		{
			serv = new Server(port);
			serv.start();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

}

class UserSender extends Thread
{
	User u;
	UserSender(User u)
	{
		this.u = u;
	}	
	public void run()
	{
		while(true)
		{
			if(! u.CurrentMessage.hasNext())
			{
				try
				{
					Thread.sleep(50);
				}
				catch(InterruptedException e)
				{
					System.out.println("Sender for user " + u.name + " interrupted; Details:\n");
					e.printStackTrace();
				}
				continue;
			}
			else
			{
				Message ToBeSent = (Message) u.CurrentMessage.next();
				try
				{
					u.Sender.writeObject(ToBeSent);
				}
				catch(IOException e)
				{
					System.out.println("Sender for user " + u.name + " failed to send; Details:\n");
					e.printStackTrace();
					return;
				}
			}
		}
	}
}

class UserReceiver extends Thread
{

	User u;
	LinkedList<Message> MessageQueue;	

	UserReceiver(User u, LinkedList<Message> q)
	{
		this.MessageQueue = q;	
		this.u = u;
	}

	public void run()
	{
		System.out.println("New login:");
		System.out.println((new Date()).toString() + " | " + u.name);
		while(true)
		{
			try
			{
				Object received = u.Receiver.readObject();
				if(!(received instanceof Message))
					throw new IllegalArgumentException();
				Message msg = (Message) received;
				MessageQueue.add(msg);
				System.out.println(msg.toString());

				if(msg.text.startsWith("\\roll"))
				{
					Message RollResult;
					RollParser rp = new RollParser();
					rp.parseRoll(msg.text.substring(5));
					if(rp.status)
					{
						RollResult = new Message("DiceRoller", msg.userName + " rolled " + rp.result);
					}
					else
					{
						RollResult = new Message("DiceRoller", msg.userName + ", something wrong with your roll. Try again");
					}
					MessageQueue.add(RollResult);
					System.out.println(RollResult.toString());
				}
			}
			catch(IllegalArgumentException e)
			{
				System.out.println("Bad input from user " + u.name + " ; User shall be disconnected");
				u.disconnect();
				return;
			}
			catch(Exception e)
			{
				System.out.println("Something wrong with" + u.name + " ; User shall be disconnected");
				System.out.println("Details:");
				e.printStackTrace();
				System.out.println("");
				u.disconnect();
				return;
			}
		}	
	}

}
