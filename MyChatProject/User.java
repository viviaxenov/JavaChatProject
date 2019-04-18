package MyChatProject;

import java.util.*;
import java.io.*;
import java.net.*;


/**
* <h1> User </h1>
* Class for storing users' data on the server side.
* ID, name and password always remain as they were created
* Socket is set each session when this user connects
* 
* @author viviaxenov
* @since 2019-04-18

*/
public class User implements Serializable
{
	private long ID;
	private String name;
	private String password;


	public Socket connection;

	public User(long id, String name, String password)
	{
		this.ID = id;
		this.name = name;
		this.password = password;
		this.connection = null;
	}		

	public void connect(Socket s)
	{
		this.connection = s;
	}
	
	public boolean checkOnline()
	{
		return !(connection == null) && connection.isConnected() && (! connection.isClosed());
	}

	public void disconnect()
	{
		if(connection == null) 
			return;
		if(! connection.isClosed())
		{
			try
			{
				connection.close();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}	
		}
		connection = null;	
		return;
	}
}
