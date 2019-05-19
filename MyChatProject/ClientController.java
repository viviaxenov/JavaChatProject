package MyChatProject;

import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.JTextField;

public class ClientController
{
	protected ClientModel Model;
	protected ClientWindow View;

	protected Thread Receiver;
	protected Thread Sender;

	public ClientController()
	{
		
	}

	public void openAppWindow()
	{
		View = new ClientWindow();
		View.updateChatArea(helpString);

		View.getConnectButton().addActionListener(new ConnectButtonHandler(this));
		View.getInputField().addActionListener(new MessageInputHandler(this));

		View.Show();
	}
	
	public void initConnection(String Username, String Hostname) throws IOException
	{
		Model = new ClientModel(Username, Hostname);

		Model.connect();
		View.clearChatArea();	
		Receiver = new Thread(new ServerReceiver(Model));
		Sender = new Thread(new ServerSender(Model));
		Receiver.setDaemon(true);
		Sender.setDaemon(true);
		Model.addListener(new ModelEventHandler(this));
		Receiver.start();
		Sender.start();
	}
	
	public ClientModel getModel()
	{
		return Model;
	}
	
	public ClientWindow getView()
	{
		return View;
	}

	public static void main(String[] args)
	{
		String Username = "Graphic Sych";
		String Hostname = "localhost";
	
		if(args.length > 0)	
		{
			Username = args[0];
		}
		if(args.length > 1)	
		{
			Hostname = args[1];
		}

		ClientController c = new ClientController();
		c.openAppWindow();
		
	}
	
	static final String helpString = 
				"Welcome to the chat.\n"+
				"To connect, fill your desired user name and hostname/IP"+
				"and press <<Connect>>\n\n";
}

class ModelEventHandler extends ClientController implements ModelListener
{

	ClientController parent;

	ModelEventHandler(ClientController c)
	{	
		super();
		this.Model = c.Model;		
		this.View = c.View;		
		this.Receiver = c.Receiver;		
		this.Sender = c.Sender;
		this.parent = c;	
	}

	public void onMessageReceived()
	{
		Message msg;
		synchronized(Model.Incoming)
		{
			msg = Model.Incoming.getLast();
		}
		View.updateChatArea(msg.toString());

	}

	synchronized public void onIOError()
	{
		Receiver.interrupt();
		Sender.interrupt();

		View.clearChatArea();
		View.updateChatArea("CONNECTION FAILURE; try to reconnect\n");
		
		View.getUsernameField().setEnabled(true);
		View.getHostnameField().setEnabled(true);
	}
}

class ConnectButtonHandler extends ClientController implements ActionListener
{
	ClientController parent;

	ConnectButtonHandler(ClientController c)
	{	
		super();
		this.Model = c.Model;		
		this.View = c.View;		
		this.Receiver = c.Receiver;		
		this.parent = c;
	}

	public void actionPerformed(ActionEvent e)
	{
		try
		{
			String Username = View.getUsernameField().getText();
			String Hostname = View.getHostnameField().getText();

			parent.initConnection(Username, Hostname);

			View.getUsernameField().setEnabled(false);
			View.getHostnameField().setEnabled(false);
			View.getConnectButton().setEnabled(false);
			View.clearChatArea();	
			View.updateChatArea("CONNECTION ESTABLISHED\n\n\n");
		}
		catch(NullPointerException exception)
		{
			View.getUsernameField().setEnabled(true);
			View.getHostnameField().setEnabled(true);
			View.getConnectButton().setEnabled(true);
			View.clearChatArea();	
			View.updateChatArea("ERROR: user or host name not specified; try again\n");
		}
		catch(IOException exception)
		{
			View.getUsernameField().setEnabled(true);
			View.getHostnameField().setEnabled(true);
			View.getConnectButton().setEnabled(true);
			View.clearChatArea();	
			View.updateChatArea("CONNECTION FAILURE: try again\n");
		}
	}
}

class MessageInputHandler implements ActionListener
{
	ClientController parent;

	MessageInputHandler(ClientController c)
	{	
		this.parent = c;
	}

	public void actionPerformed(ActionEvent e)
	{
		JTextField s = (JTextField) e.getSource();
		parent.getModel().addMessage(s.getText());
		s.setText("");
	}
}












