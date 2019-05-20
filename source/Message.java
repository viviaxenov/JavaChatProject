package Message;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
* <h1> Message </h1>
* Class for sending user's messages to and from server
* 
* @author viviaxenov
* @since 2019-04-18
*/

public class Message implements Serializable
{

	public final String userName;
	public final String text;	
	public final Date timeStamp;

	/**
	* Creates message with timestamp of creation time.
	*	
	* @param userName 	name of the sender
	* @param text		mesage body
	*/

	public Message(String userName, String text)
	{
		this.userName = userName;	
		this.text = text;
		
		this.timeStamp = new Date();
	}

	/**
	* 
	*	
	*
	*
	*	
	*/	
	public String toString()
	{
		String df = "EEE KK:mm:ss";
		String time = (new SimpleDateFormat(df)).format(timeStamp);
		return time + " | " +	
			userName + ": " + text;
	}
}

