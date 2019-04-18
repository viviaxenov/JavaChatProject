package MyChatProject;
import java.io.Serializable;
/**
* <h1> ConnectionRequest </h1>
* Class for sending serialized user data to server right after connection
* 
* @author viviaxenov
* @since 2019-04-18
*/

public class ConnectionRequest implements Serializable
{
	public String userName;
	public String password;
	public long ID;

	public ConnectionRequest(String userName, String password, long ID)
	{
		this.userName = userName;
		this.password = password;
		this.ID = ID;
	}

	public ConnectionRequest(String userName, String password)
	{
		this(userName, password, -1);
	}
}


