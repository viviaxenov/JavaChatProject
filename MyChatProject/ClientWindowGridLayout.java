import java.awt.event.*;
import javax.swing.*;
import net.miginfocom.swing.*;
/*
 * Created by JFormDesigner on Thu May 16 11:04:15 GMT+03:00 2019
 */



/**
 * @author Vitaliy Aksenov
 */
public class ClientWindowGridLayout  {

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
		ChatFrame = new JFrame();
		UsernameLabel = new JLabel();
		UsernameField = new JTextField();
		HostnameLabel = new JLabel();
		textField2 = new JTextField();
		ChatLabel = new JLabel();
		ConnectButton = new JButton();
		separator1 = new JSeparator();
		scrollPaneChat = new JScrollPane();
		textAreaChat = new JTextArea();
		PromptLabel = new JLabel();
		separator2 = new JSeparator();
		InputField = new JTextField();

		//======== ChatFrame ========
		{
			ChatFrame.setResizable(false);
			ChatFrame.setTitle("Best chat ever");
			ChatFrame.setAlwaysOnTop(true);
			var ChatFrameContentPane = ChatFrame.getContentPane();
			ChatFrameContentPane.setLayout(new MigLayout(
				"insets 0,hidemode 3,gap 5 5",
				// columns
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]",
				// rows
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[fill]" +
				"[]" +
				"[]" +
				"[]" +
				"[]" +
				"[]" +
				"[]" +
				"[]" +
				"[]" +
				"[]" +
				"[]" +
				"[]" +
				"[]" +
				"[]"));

			//---- UsernameLabel ----
			UsernameLabel.setText("Username");
			ChatFrameContentPane.add(UsernameLabel, "cell 1 1");

			//---- UsernameField ----
			UsernameField.addActionListener(e -> UsernameFieldActionPerformed(e));
			ChatFrameContentPane.add(UsernameField, "cell 2 1 10 1");

			//---- HostnameLabel ----
			HostnameLabel.setText("Hostname");
			ChatFrameContentPane.add(HostnameLabel, "cell 1 2");

			//---- textField2 ----
			textField2.setText("localhost");
			ChatFrameContentPane.add(textField2, "cell 2 2 10 1");

			//---- ChatLabel ----
			ChatLabel.setText("Chat:");
			ChatFrameContentPane.add(ChatLabel, "cell 1 3 1 2");

			//---- ConnectButton ----
			ConnectButton.setText("Connect!");
			ConnectButton.setEnabled(false);
			ConnectButton.addActionListener(e -> ConnectButtonActionPerformed(e));
			ChatFrameContentPane.add(ConnectButton, "cell 11 3");
			ChatFrameContentPane.add(separator1, "cell 1 4 11 1");

			//======== scrollPaneChat ========
			{

				//---- textAreaChat ----
				textAreaChat.setEditable(false);
				scrollPaneChat.setViewportView(textAreaChat);
			}
			ChatFrameContentPane.add(scrollPaneChat, "cell 1 5 11 11");

			//---- PromptLabel ----
			PromptLabel.setText("Your message:");
			ChatFrameContentPane.add(PromptLabel, "cell 1 16 3 1");
			ChatFrameContentPane.add(separator2, "cell 1 17 11 1");

			//---- InputField ----
			InputField.addActionListener(e -> InputFieldActionPerformed(e));
			ChatFrameContentPane.add(InputField, "cell 1 18 11 2");
			ChatFrame.setSize(385, 465);
			ChatFrame.setLocationRelativeTo(ChatFrame.getOwner());
		}
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	// Generated using JFormDesigner Evaluation license - Vitaliy Aksenov
	private JFrame ChatFrame;
	private JLabel UsernameLabel;
	private JTextField UsernameField;
	private JLabel HostnameLabel;
	private JTextField textField2;
	private JLabel ChatLabel;
	private JButton ConnectButton;
	private JSeparator separator1;
	private JScrollPane scrollPaneChat;
	private JTextArea textAreaChat;
	private JLabel PromptLabel;
	private JSeparator separator2;
	private JTextField InputField;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
