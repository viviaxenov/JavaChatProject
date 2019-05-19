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
	static final int defaultPort = 9000;
	static final String defaultLogPath = "./log.txt";

	static String ServName = "Server";
	static String greeting = "Welcome, ";
	static String goodbye = " disconnected; See you!";

	int prevMsg = 5;		// the number of messages from previous conversation sent to new user on login


	private ServerSocket Listener;

	String logPath;
	PrintStream logger;

	LinkedList<Message> MessageQueue;
	/**
	* Tries to initialize server and establish socket listener
	*
	* @param port		port to establish listener
	* @param lp		String - path to log file
	*
	* @throws IOException 	if can't establish listener socket
	*/
	public Server(int port, String lp) throws IOException
	{
		logPath = lp;
		logger = new PrintStream(logPath);
		MessageQueue = new LinkedList<Message>();
		Listener = new ServerSocket(port);
	}
	public Server(String lp) throws IOException
	{
		this(defaultPort, lp);
	}
	public Server() throws IOException
	{
		this(defaultPort, defaultLogPath);
	}

	/**
	* 
	* Starts server's main routine: listening for user's connection and creating
	* receiver and sender threads for users
	*/

	public void start() throws IOException
	{
		while(true)
		{
			Socket sock = Listener.accept();

			logger.println( (new Date()).toString() + "New server session\nServer name: " + ServName + "\n");		
			
			(new UserSender(sock, MessageQueue, logger)).start();
			(new UserReceiver(sock, MessageQueue, logger)).start();
		}
	}


	public static void main(String[] args)
	{
		Server serv;
		try
		{
			serv = new Server();
			serv.start();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}


	/**
	* <h1> UserSender </h1>
	* Thread for sending messages to individual users
	* <p>
	* Wakes up every 50ms to check 
	* if it has unsent messages in MessageQueue 
	*
	*/

	class UserSender extends Thread
	{
		Socket Connection;	
		ObjectOutputStream Sender;
		LinkedList<Message> MessageQueue;
		PrintStream logger;
		int CurrentMessage;

		/**
		* Constructor
		*	
		* @param c		Socket to communicate with user
		* @param mq		LinkedList\<Message\> - queue of messages to send
		*/
		UserSender(Socket c, LinkedList<Message> mq, PrintStream l)
		{
			MessageQueue = mq;
			Connection = c;
			int k = MessageQueue.size() - prevMsg;
			CurrentMessage = k > 0 ? k : 0;
			logger = l;
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
				logger.println("Sender failed to send; Details:\n");
				e.printStackTrace(logger);
				return;
			}
		}
	}

	/**
	* <h1> UserReceiver </h1>
	* Thread for receiving messages from individual users
	* <p>
	* Receives message from user and puts it into message queue
	*/
	class UserReceiver extends Thread
	{
		ObjectInputStream Receiver;

		Socket Connection;
		LinkedList<Message> MessageQueue;	
		String userName;
		PrintStream logger;

		UserReceiver(Socket c, LinkedList<Message> q, PrintStream l)
		{
			MessageQueue = q;	
			Connection = c;
			userName = "";
			logger = l;
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

					if(! msg.text.equals(""))
					{
						synchronized(MessageQueue)
						{	
							MessageQueue.add(msg);
						}
					}

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
					}

					received = Receiver.readObject();
				}	
			}
			catch(Exception e)
			{
				String source = (userName.equals("")) ? ("connection with host " + Connection.getInetAddress().getHostName()) : ("user " + userName);
				
				if(! userName.equals(""))
				{
					synchronized(MessageQueue)
					{
						MessageQueue.add(new Message(ServName, userName + Server.goodbye));
					}
				}
				// TODO logging
				logger.println("Receiver: Something wrong with " + source + "; Details:");
				e.printStackTrace(logger);
				return;
			}
		}

	}

}
