package otp_chat.client.gui;

import javax.swing.JPanel;

import java.awt.Font;

import javax.swing.JLabel;

import java.awt.FlowLayout;
import javax.swing.JTextArea;

import otp_chat.ReceivedMessage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class MessageView extends JPanel {

	private static final long serialVersionUID = -9027908248606951765L;
	private JPanel detailsView;
	private JPanel extendedView;

	protected final ReceivedMessage message;
	protected final String plaintext;

	public MessageView(ReceivedMessage message, String plaintext, boolean showFrequencyAnalysis) {
		this.message = message;
		this.plaintext = plaintext;
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

		extendedView = new JPanel(new BorderLayout(0, 0));
		extendedView.setVisible(false);
		add(extendedView, BorderLayout.CENTER);

		detailsView = new JPanel();
		detailsView.addMouseListener(clickListener);
		extendedView.add(detailsView, BorderLayout.CENTER);
		detailsView.setLayout(new BoxLayout(detailsView, BoxLayout.Y_AXIS));

		CategoryValueView ciphertextView = new CategoryValueView("Ciphertext", message.ciphertext, clickListener);
		ciphertextView.addMouseListener(clickListener);
		detailsView.add(ciphertextView);

		CategoryValueView keyView = new CategoryValueView("Key", message.key, clickListener);
		keyView.addMouseListener(clickListener);
		detailsView.add(keyView);
		if (showFrequencyAnalysis) {
			JButton btnFrequencyAnalysis = new JButton("<html>Frequency<br>analysis</html>");
			final MessageView here = this;
			btnFrequencyAnalysis.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					FrequencyAnalysisFrame.show(new MessageView(here, false));
				}
			});
			extendedView.add(btnFrequencyAnalysis, BorderLayout.EAST);
		}

	}

	public MessageView(MessageView origin, boolean showFrequencyAnalysis) {
		this(origin.message, origin.plaintext, showFrequencyAnalysis);
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
