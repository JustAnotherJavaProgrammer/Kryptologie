package otp_chat.client.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

import otp.OTP;
import otp_chat.OTPMessage;
import otp_chat.ReceivedMessage;
import otp_chat.client.Client;
import otp_chat.client.ClientListener;
import util.AlphabetMode;
import util.Sanitizer;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.GridBagLayout;

public class ChatPanel extends JPanel {
	private static final long serialVersionUID = -8187196836824875901L;
	private final Client client;
	private final Thread receiveThread;
	private JPanel pnlMessages;

	private final HashMap<AlphabetMode, OTP> otpInstances = new HashMap<>();
	private final OTP defaultOTP;
	private JTextField txtFieldDefaultMessage;
	private JButton btnSendMessage;
	private JScrollPane messageScroller;

	/**
	 * Create the panel.
	 * 
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws UnknownHostException
	 */
	public ChatPanel(String addr, String port, String nickname, GUI gui)
			throws NumberFormatException, UnknownHostException, IOException {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = 0;
		constraints.anchor = GridBagConstraints.LAST_LINE_START;

		addContainerListener(new ContainerAdapter() {
			@Override
			public void componentRemoved(ContainerEvent e) {
				try {
					client.sock.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		defaultOTP = new OTP(AlphabetMode.MODE_UNIVERSAL);
		for (AlphabetMode mode : AlphabetMode.values()) {
			otpInstances.put(mode, mode == AlphabetMode.MODE_UNIVERSAL ? defaultOTP : new OTP(mode));
		}

		client = new Client(new ClientListener() {

			@Override
			public void nicknamesUpdated(String[] nicks) {

			}

			@Override
			public void messageReceived(ReceivedMessage mess) {
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						pnlMessages.add(new JSeparator(JSeparator.HORIZONTAL), constraints,
								pnlMessages.getComponentCount() - 1);
						pnlMessages.add(
								new MessageView(mess,
										otpInstances.getOrDefault(mess.alphabet, defaultOTP).decrypt(mess)),
								constraints, pnlMessages.getComponentCount() - 1);
						JScrollBar scrollBar = messageScroller.getVerticalScrollBar();
						scrollBar.setValue(scrollBar.getMaximum());
						pnlMessages.revalidate();
					}
				});
			}

			@Override
			public void disconnected() {
				gui.changeContentPane(new ConnectPanel(gui));
			}
		}, addr, Integer.valueOf(port), nickname);
		receiveThread = new Thread(client);
		setLayout(new BorderLayout(5, 5));

		JLabel lblMessages = new JLabel("Messages:");
		lblMessages.setFont(lblMessages.getFont().deriveFont(Font.BOLD));
		add(lblMessages, BorderLayout.NORTH);

		pnlMessages = new JPanel();
//		pnlMessages.add(new Box.Filler(new Dimension(0, 0), new Dimension(0, Short.MAX_VALUE),
//				new Dimension(0, Short.MAX_VALUE)));

		messageScroller = new JScrollPane(pnlMessages);
		messageScroller.setViewportBorder(null);
		messageScroller.setAutoscrolls(true);
		GridBagLayout gbl_pnlMessages = new GridBagLayout();
		gbl_pnlMessages.columnWidths = new int[] { 0 };
		gbl_pnlMessages.rowHeights = new int[] { 0 };
		gbl_pnlMessages.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_pnlMessages.rowWeights = new double[] { Double.MIN_VALUE };
		pnlMessages.setLayout(gbl_pnlMessages);
		messageScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(messageScroller, BorderLayout.CENTER);

		JPanel pnlSendDefaultMessages = new JPanel();
		add(pnlSendDefaultMessages, BorderLayout.SOUTH);
		pnlSendDefaultMessages.setLayout(new BorderLayout(5, 5));

		txtFieldDefaultMessage = new JTextField();
		txtFieldDefaultMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnSendMessage.doClick();
			}
		});
		pnlSendDefaultMessages.add(txtFieldDefaultMessage, BorderLayout.CENTER);
		txtFieldDefaultMessage.setColumns(10);

		btnSendMessage = new JButton("Send");
		btnSendMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AlphabetMode alphabet = findAlphabet(txtFieldDefaultMessage.getText());
				try {
					client.send(new OTPMessage(alphabet,
							otpInstances.getOrDefault(alphabet, defaultOTP).encryptWithRandomKey(
									Sanitizer.sanitize(txtFieldDefaultMessage.getText(), alphabet).result),
							client.nickname));
					txtFieldDefaultMessage.setText("");
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Your message could not be sent!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pnlSendDefaultMessages.add(btnSendMessage, BorderLayout.EAST);

		receiveThread.start();
	}

	private AlphabetMode findAlphabet(String str) {
		AlphabetMode[] alphabets = new AlphabetMode[] { AlphabetMode.MODE_UPPER_ONLY, AlphabetMode.MODE_UPPER_SPACE,
				AlphabetMode.MODE_CASE_SENSITIVE, AlphabetMode.MODE_CASE_SENSITIVE_SPACE,
				AlphabetMode.MODE_ONE_WIDTH_CHARS_ASCII, AlphabetMode.MODE_UNIVERSAL };
		for (AlphabetMode alphabet : alphabets) {
			if (!Sanitizer.sanitize(str, alphabet).changed)
				return alphabet;
		}
		return AlphabetMode.MODE_UNIVERSAL;
	}

}
