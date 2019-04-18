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
	private long userID;

	public String userName;
	public String text;	
	public Date timeStamp;

	public Message(long userID, String userName, String text)
	{
		this.userName = userName;	
		this.userID = userID;	
		
		this.timeStamp = new Date();
	}
}

