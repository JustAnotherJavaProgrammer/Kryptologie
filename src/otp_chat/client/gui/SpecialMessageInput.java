package otp_chat.client.gui;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSeparator;

public class SpecialMessageInput extends JPanel {

	private static final long serialVersionUID = -9027908248606951765L;
	public final String nickname;
	private JTextField textField;

	public SpecialMessageInput(String nickname) {
		this.nickname = nickname;
		setLayout(new BorderLayout(5, 5));

		JLabel lblNickname = new JLabel(nickname + ": ");
		lblNickname.setFont(lblNickname.getFont().deriveFont(Font.BOLD));
		add(lblNickname, BorderLayout.WEST);

		textField = new JTextField();
		textField.setToolTipText("Enter a special message to only one person");
		add(textField, BorderLayout.CENTER);
		textField.setColumns(10);

		JSeparator separator = new JSeparator();
		add(separator, BorderLayout.SOUTH);

	}

	public String getMessage() {
		String stripped = textField.getText().strip();
		return stripped.length() > 0 ? stripped : null;
	}

	public void resetSpecialMessageInput() {
		textField.setText("");
	}

}
