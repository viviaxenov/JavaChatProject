package MyChatProject;
import java.io.Serializable;
import java.util.Date;

/**
* <h1> Message </h1>
* Class for sending user's messages to and from server
* 
* @author viviaxenov
* @since 2019-04-18
*/

public class Message implements Serializable
{

	public String userName;
	public String text;	
	public Date timeStamp;

	public Message(String userName, String text)
	{
		this.userName = userName;	
		this.text = text;
		
		this.timeStamp = new Date();
	}

	public String toString()
	{
		return timeStamp.toString() + " | " +	
			userName + ": " + text;
	}
}

