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
	public ClientWindow() {
		initComponents();
	}

	private void UsernameFieldActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void ConnectButtonActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void InputFieldActionPerformed(ActionEvent e) {
		// TODO add your code here
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		// Generated using JFormDesigner Evaluation license - Vitaliy Aksenov
		ChatClient = new JFrame();
		UsernameLable = new JLabel();
		HostnmaneLabel = new JLabel();
		UsernameField = new JTextField();
		textField2 = new JTextField();
		ConnectButton = new JButton();
		scrollPaneChat = new JScrollPane();
		textAreaChat = new JTextArea();
		separator1 = new JSeparator();
		ChatLabel = new JLabel();
		PromptLabel = new JLabel();
		separator2 = new JSeparator();
		InputField = new JTextField();

		//======== ChatClient ========
		{
			ChatClient.setResizable(false);
			ChatClient.setTitle("Best chat ever");
			var ChatClientContentPane = ChatClient.getContentPane();
			ChatClientContentPane.setLayout(null);

			//---- UsernameLable ----
			UsernameLable.setText("Username");
			ChatClientContentPane.add(UsernameLable);
			UsernameLable.setBounds(new Rectangle(new Point(15, 15), UsernameLable.getPreferredSize()));

			//---- HostnmaneLabel ----
			HostnmaneLabel.setText("Hostname");
			ChatClientContentPane.add(HostnmaneLabel);
			HostnmaneLabel.setBounds(15, 45, 47, 13);

			//---- UsernameField ----
			UsernameField.addActionListener(e -> UsernameFieldActionPerformed(e));
			ChatClientContentPane.add(UsernameField);
			UsernameField.setBounds(70, 15, 320, UsernameField.getPreferredSize().height);

			//---- textField2 ----
			textField2.setText("localhost");
			ChatClientContentPane.add(textField2);
			textField2.setBounds(70, 42, 320, 19);

			//---- ConnectButton ----
			ConnectButton.setText("Connect!");
			ConnectButton.setEnabled(false);
			ConnectButton.addActionListener(e -> ConnectButtonActionPerformed(e));
			ChatClientContentPane.add(ConnectButton);
			ConnectButton.setBounds(305, 75, 85, ConnectButton.getPreferredSize().height);

			//======== scrollPaneChat ========
			{

				//---- textAreaChat ----
				textAreaChat.setEditable(false);
				scrollPaneChat.setViewportView(textAreaChat);
			}
			ChatClientContentPane.add(scrollPaneChat);
			scrollPaneChat.setBounds(17, 125, 375, 220);
			ChatClientContentPane.add(separator1);
			separator1.setBounds(15, 110, 375, 15);

			//---- ChatLabel ----
			ChatLabel.setText("Chat:");
			ChatClientContentPane.add(ChatLabel);
			ChatLabel.setBounds(15, 95, ChatLabel.getPreferredSize().width, 18);

			//---- PromptLabel ----
			PromptLabel.setText("Your message:");
			ChatClientContentPane.add(PromptLabel);
			PromptLabel.setBounds(15, 350, 75, 13);
			ChatClientContentPane.add(separator2);
			separator2.setBounds(15, 365, 380, 10);

			//---- InputField ----
			InputField.addActionListener(e -> InputFieldActionPerformed(e));
			ChatClientContentPane.add(InputField);
			InputField.setBounds(17, 375, 375, InputField.getPreferredSize().height);

			{ // compute preferred size
				Dimension preferredSize = new Dimension();
				for(int i = 0; i < ChatClientContentPane.getComponentCount(); i++) {
					Rectangle bounds = ChatClientContentPane.getComponent(i).getBounds();
					preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
					preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
				}
				Insets insets = ChatClientContentPane.getInsets();
				preferredSize.width += insets.right;
				preferredSize.height += insets.bottom;
				ChatClientContentPane.setMinimumSize(preferredSize);
				ChatClientContentPane.setPreferredSize(preferredSize);
			}
			ChatClient.pack();
			ChatClient.setLocationRelativeTo(ChatClient.getOwner());
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Vitaliy Aksenov
	private JFrame ChatClient;
	private JLabel UsernameLable;
	private JLabel HostnmaneLabel;
	private JTextField UsernameField;
	private JTextField textField2;
	private JButton ConnectButton;
	private JScrollPane scrollPaneChat;
	private JTextArea textAreaChat;
	private JSeparator separator1;
	private JLabel ChatLabel;
	private JLabel PromptLabel;
	private JSeparator separator2;
	private JTextField InputField;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
