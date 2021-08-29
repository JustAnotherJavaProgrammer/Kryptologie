package otp_chat.client.gui;

import javax.swing.JPanel;

import java.awt.Font;

import javax.swing.JLabel;

import java.awt.FlowLayout;
import javax.swing.JTextArea;

import otp_chat.ReceivedMessage;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;

public class MessageView extends JPanel {

	private static final long serialVersionUID = -9027908248606951765L;
	private JPanel extendedView;

	public MessageView(ReceivedMessage message, String plaintext) {
		MouseAdapter clickListener = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				extendedView.setVisible(!extendedView.isVisible());
			}
		};

		addMouseListener(clickListener);
		setLayout(new BorderLayout(0, 0));

		CategoryValueView plaintextView = new CategoryValueView(message.nickname, plaintext.strip(), clickListener);
		plaintextView.addMouseListener(clickListener);
		add(plaintextView, BorderLayout.NORTH);

		extendedView = new JPanel();
		extendedView.addMouseListener(clickListener);
		extendedView.setVisible(false);
		add(extendedView, BorderLayout.CENTER);
		extendedView.setLayout(new BoxLayout(extendedView, BoxLayout.Y_AXIS));

		CategoryValueView ciphertextView = new CategoryValueView("Ciphertext", message.ciphertext, clickListener);
		ciphertextView.addMouseListener(clickListener);
		extendedView.add(ciphertextView);

		CategoryValueView keyView = new CategoryValueView("Key", message.key, clickListener);
		keyView.addMouseListener(clickListener);
		extendedView.add(keyView);

	}

	private static class CategoryValueView extends JPanel {
		private static final long serialVersionUID = -5637586274386918363L;

		public CategoryValueView(String category, String value, MouseListener mouseListener) {
			((FlowLayout) getLayout()).setAlignment(FlowLayout.LEFT);
			JLabel lblCategory = new JLabel(category.strip() + ":");
			lblCategory.addMouseListener(mouseListener);
			add(lblCategory);
			lblCategory.setFont(lblCategory.getFont().deriveFont(Font.BOLD));

			JTextArea txtValue = new JTextArea(value);
			txtValue.addMouseListener(mouseListener);
			add(txtValue);
			txtValue.setEditable(false);
			txtValue.setBackground(getBackground());
			txtValue.setFont(lblCategory.getFont().deriveFont(Font.PLAIN));
		}
	}

}
