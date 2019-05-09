package MyChatProject;

import java.util.*;
import java.io.*;
import java.net.*;


/**
* <h1> User </h1>
* Class for storing users' data on the server side.
* 
* 
* 
* @author viviaxenov
* @since 2019-05-09

*/
public class User implements Serializable
{
	final String name;
	ListIterator CurrentMessage;

	Socket connection;

	public User(String name, ListIterator curr, Socket s)
	{
		this.name = name;
		this.CurrentMessage = curr;
		this.connection = s;
	}		

	
	public boolean checkOnline()
	{
		return !(connection == null) && connection.isConnected() && !connection.isClosed();
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
