package Client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Thu May 16 01:37:43 GMT+03:00 2019
 */



/**
 * @author Vitaliy Aksenov
 */
public class ClientWindow {

	public static void main(String[] args)
	{
		new ClientWindow().ChatFrame.setVisible(true);
	}


	public ClientWindow() {
		initComponents();
	}

	private void UsernameFieldActionPerformed(ActionEvent e) {
		// TODO add your code here
		ConnectButton.setEnabled(true);
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Vitaliy Aksenov
		ChatFrame = new JFrame();
		UsernameLabel = new JLabel();
		HostnameLabel = new JLabel();
		UsernameField = new JTextField();
		HostnameField = new JTextField();
		ConnectButton = new JButton();
		scrollPaneChat = new JScrollPane();
		textAreaChat = new JTextArea();
		separator1 = new JSeparator();
		ChatLabel = new JLabel();
		PromptLabel = new JLabel();
		separator2 = new JSeparator();
		InputField = new JTextField();

		//======== ChatFrame ========
		{
			ChatFrame.setResizable(false);
			ChatFrame.setTitle("Best chat ever");
			ChatFrame.setAlwaysOnTop(false);
			var ChatFrameContentPane = ChatFrame.getContentPane();
			ChatFrameContentPane.setLayout(null);
			ChatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			//---- UsernameLabel ----
			UsernameLabel.setText("Username");
			ChatFrameContentPane.add(UsernameLabel);
			UsernameLabel.setBounds(new Rectangle(new Point(15, 15), UsernameLabel.getPreferredSize()));

			//---- HostnameLabel ----
			HostnameLabel.setText("Host");
			ChatFrameContentPane.add(HostnameLabel);
			HostnameLabel.setBounds(15, 45, 47, 13);

			//---- UsernameField ----
			UsernameField.addActionListener(e -> UsernameFieldActionPerformed(e));
			ChatFrameContentPane.add(UsernameField);
			UsernameField.setBounds(90, 15, 300, UsernameField.getPreferredSize().height);

			//---- HostnameField ----
			HostnameField.setText("localhost");
			ChatFrameContentPane.add(HostnameField);
			HostnameField.setBounds(90, 42, 300, 19);

			//---- ConnectButton ----
			ConnectButton.setText("Connect!");
			ConnectButton.setEnabled(false);
			ChatFrameContentPane.add(ConnectButton);
			ConnectButton.setBounds(285, 75, 105, ConnectButton.getPreferredSize().height);

			//======== scrollPaneChat ========
			{

				//---- textAreaChat ----
				textAreaChat.setEditable(false);
				textAreaChat.setWrapStyleWord(true);
				textAreaChat.setLineWrap(true);
				scrollPaneChat.setViewportView(textAreaChat);
			}
			ChatFrameContentPane.add(scrollPaneChat);
			scrollPaneChat.setBounds(17, 125, 375, 220);
			ChatFrameContentPane.add(separator1);
			separator1.setBounds(15, 110, 375, 15);

			//---- ChatLabel ----
			ChatLabel.setText("Chat:");
			ChatFrameContentPane.add(ChatLabel);
			ChatLabel.setBounds(15, 95, ChatLabel.getPreferredSize().width, 18);

			//---- PromptLabel ----
			PromptLabel.setText("Your message:");
			ChatFrameContentPane.add(PromptLabel);
			PromptLabel.setBounds(15, 350, 150, 13);
			ChatFrameContentPane.add(separator2);
			separator2.setBounds(15, 365, 380, 10);

			//---- InputField ----
			ChatFrameContentPane.add(InputField);
			InputField.setBounds(15, 375, 375, InputField.getPreferredSize().height);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < ChatFrameContentPane.getComponentCount(); i++) {
					Rectangle bounds = ChatFrameContentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = ChatFrameContentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				ChatFrameContentPane.setMinimumSize(preferredSize);
				ChatFrameContentPane.setPreferredSize(preferredSize);
			}
			ChatFrame.setSize(410, 455);
			ChatFrame.setLocationRelativeTo(ChatFrame.getOwner());
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}


	// Custom methods

	public void Show()
	{
		ChatFrame.setVisible(true);
	}

	public void updateChatArea(String s)
	{
		textAreaChat.append(s + '\n');
		textAreaChat.update(textAreaChat.getGraphics());
	}
	public void clearChatArea()
	{
		textAreaChat.setText("");
		textAreaChat.update(textAreaChat.getGraphics());
	}
	



	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Vitaliy Aksenov
	private JFrame ChatFrame;
	private JLabel UsernameLabel;
	private JLabel HostnameLabel;
	private JTextField UsernameField;
	private JTextField HostnameField;
	private JButton ConnectButton;
	private JScrollPane scrollPaneChat;
	private JTextArea textAreaChat;
	private JSeparator separator1;
	private JLabel ChatLabel;
	private JLabel PromptLabel;
	private JSeparator separator2;
	private JTextField InputField;

	public JButton getConnectButton()
	{
		return ConnectButton;
	}
	public JTextField getUsernameField()
	{
		return UsernameField;
	}
	public JTextField getHostnameField()
	{
		return HostnameField;
	}
	public JTextField getInputField()
	{
		return InputField;
	}
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
