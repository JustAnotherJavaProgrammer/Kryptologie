package otp_chat.client.gui;

import javax.swing.JPanel;

import java.awt.Font;

import javax.swing.JLabel;

import java.awt.FlowLayout;
import javax.swing.JTextArea;

import otp_chat.ReceivedMessage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;

public class MessageView extends JPanel {

	private static final long serialVersionUID = -9027908248606951765L;
	private JPanel extendedView;

	public MessageView(ReceivedMessage message, String plaintext) {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				extendedView.setVisible(!extendedView.isVisible());
			}
		});
		setLayout(new BorderLayout(0, 0));

		CategoryValueView plaintextView = new CategoryValueView(message.nickname, plaintext);
		add(plaintextView, BorderLayout.NORTH);

		extendedView = new JPanel();
		extendedView.setVisible(false);
		add(extendedView, BorderLayout.CENTER);
		extendedView.setLayout(new BoxLayout(extendedView, BoxLayout.Y_AXIS));

		CategoryValueView ciphertextView = new CategoryValueView("Ciphertext", message.ciphertext);
		extendedView.add(ciphertextView);

		CategoryValueView keyView = new CategoryValueView("Key", message.key);
		extendedView.add(keyView);

	}

	private static class CategoryValueView extends JPanel {
		private static final long serialVersionUID = -5637586274386918363L;

		public CategoryValueView(String category, String value) {
			((FlowLayout) getLayout()).setAlignment(FlowLayout.LEFT);
			JLabel lblCategory = new JLabel(category + ":");
			add(lblCategory);
			lblCategory.setFont(lblCategory.getFont().deriveFont(Font.BOLD));

			JTextArea txtValue = new JTextArea(value);
			add(txtValue);
			txtValue.setEditable(false);
			txtValue.setBackground(getBackground());
			txtValue.setFont(lblCategory.getFont().deriveFont(Font.PLAIN));
		}
	}

}
