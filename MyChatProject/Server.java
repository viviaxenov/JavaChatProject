package MyChatProject;

import java.util.*;
import java.io.*;
import java.net.*;


/**
* <h1> Server </h1>
* 
* Server application to broadcast messages to users
* 
* @author viviaxenov
* @since 2019-04-18
*/

public class Server
{
	static String ServName = "Server";
	static String greeting = "Welcome, ";

	static int prevMsg = 5;

	static final int port = 9000;

	private ServerSocket Listener;
	LinkedList<Message> MessageQueue;
	/**
	* Tries to initialize server and establish socket listener
	*
	* @param port		port to establish listener
	*
	* @throws IOException 	if can't establish listener socket
	*/
	public Server(int port) throws IOException
	{
		MessageQueue = new LinkedList<Message>();
		Listener = new ServerSocket(port);
	}

	/**
	* Starts server's main routine: listening for user's connection and creating
	* receiver and sender threads for users
	*
	*
	*/

	public void start() throws IOException
	{
		while(true)
		{
			Socket sock = Listener.accept();
			
			(new UserSender(sock, MessageQueue)).start();
			(new UserReceiver(sock, MessageQueue)).start();
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


	/**
	* Thread for sending messages to individual users
	*
	* 
	*
	*
	*
	*
	*
	*/

	class UserSender extends Thread
	{
		Socket Connection;	
		ObjectOutputStream Sender;
		LinkedList<Message> MessageQueue;
		int CurrentMessage;

		UserSender(Socket c, LinkedList<Message> mq)
		{
			MessageQueue = mq;
			Connection = c;
			int k = MessageQueue.size() - prevMsg;
			CurrentMessage = k > 0 ? k : 0;
		}	
		public void run()
		{
			try
			{
				Sender = new ObjectOutputStream(Connection.getOutputStream());
				while(true)
				{
					Message ToBeSent;
					if(CurrentMessage < MessageQueue.size())
					{
						synchronized(MessageQueue)	
						{
							ToBeSent =  MessageQueue.get(CurrentMessage);
						}
						if(!(ToBeSent.text.equals("")))
							Sender.writeObject(ToBeSent);
						CurrentMessage++;
						continue;
					}
					Thread.sleep(50);
				}
			}
			catch(Exception e)
			{
				System.out.println("Sender failed to send; Details:\n");
				e.printStackTrace();
				return;
			}
		}
	}

	class UserReceiver extends Thread
	{
		ObjectInputStream Receiver;
		Socket Connection;
		LinkedList<Message> MessageQueue;	
		String userName;

		UserReceiver(Socket c, LinkedList<Message> q)
		{
			MessageQueue = q;	
			Connection = c;
			userName = "";
		}

		public void run()
		{
			Object received;
			Message msg;
			try
			{
				Receiver = new ObjectInputStream(Connection.getInputStream());

				received = Receiver.readObject();
				if(!(received instanceof Message))
					throw new IllegalArgumentException();
				msg = (Message) received;
				userName = msg.userName;
					
				System.out.println("New user: ");
				System.out.println((new Date()).toString() + " | " + msg.userName);
				
				Message welcome = new Message(ServName, greeting + userName);
				synchronized(MessageQueue)
				{	
					MessageQueue.add(welcome);
				}
				
				while(true)
				{
					if(!(received instanceof Message))
						throw new IllegalArgumentException();
					msg = (Message) received;

					synchronized(MessageQueue)
					{	
						MessageQueue.add(msg);
					}
					if(! msg.text.equals(""))
						System.out.println(msg.toString());

					if(msg.text.startsWith("\\roll"))
					{
						Message RollResult;
						RollParser rp = new RollParser();
						rp.parseRoll(msg.text.substring(5));
						if(rp.status)
						{
							RollResult = new Message(ServName, msg.userName + " rolled " + rp.result);
						}
						else
						{
							RollResult = new Message(ServName, msg.userName + ", something wrong with your roll. Try again");
						}

						synchronized(MessageQueue)
						{	
							MessageQueue.add(RollResult);
						}
						System.out.println(RollResult.toString());
					}

					received = Receiver.readObject();
				}	
			}
			catch(Exception e)
			{
				String source = (userName.equals("")) ? ("connection with host " + Connection.getInetAddress().getHostName()) : ("user " + userName);
				System.out.println("Receiver: Something wrong with " + source + "; Details:");
				e.printStackTrace();
			}
		}

	}

}
