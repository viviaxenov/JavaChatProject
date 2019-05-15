package MyChatProject;

import java.util.*;
import java.io.*;
import java.net.*;



public class Server
{
	static String ServName = "Death Star comm";
	static String greeting = "May the force be with  ";
	static int prevMsg = 1;

	static final int port = 9000;

	private ServerSocket Listener;
	LinkedList<Message> MessageQueue;

	public Server(int port) throws IOException
	{
		MessageQueue = new LinkedList<Message>();
		Listener = new ServerSocket(port);
	}

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
			int k = MessageQueue.size() - 1;
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
					synchronized(MessageQueue)	
					{
						if(CurrentMessage < MessageQueue.size() - 1)
						{
							CurrentMessage++;
							ToBeSent =  MessageQueue.get(CurrentMessage);
							Sender.writeObject(ToBeSent);
							continue;
						}
					}
					Thread.sleep(50);
				}
			}
			catch(Exception e)
			{
				System.out.println("Sender for failed to send; Details:\n");
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
				MessageQueue.add(welcome);
				
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
