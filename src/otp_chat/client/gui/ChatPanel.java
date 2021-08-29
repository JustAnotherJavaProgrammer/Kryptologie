package otp_chat.client.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;

import otp.OTP;
import otp.OTP.KeyCiphertextPair;
import otp_chat.OTPMessage;
import otp_chat.ReceivedMessage;
import otp_chat.client.Client;
import otp_chat.client.ClientListener;
import util.AlphabetMode;
import util.Arrays;
import util.RandomKeyGenerator;
import util.Sanitizer;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;

import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.GridBagLayout;
import javax.swing.JSplitPane;

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
	private JSplitPane splitPane;
	private JScrollPane scrollPane;
	private JPanel pnlSpecialMessages;

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
			private String[] nickList = new String[] {};

			@Override
			public void nicknamesUpdated(String[] nicks) {
				ArrayList<String> added = new ArrayList<>();
				ArrayList<String> removed = new ArrayList<>();
				for (String nick : nicks) {
					if (!Arrays.contains(nickList, nick) && !nick.equals(client.nickname))
						added.add(nick);
				}
				for (String nick : nickList) {
					if (!Arrays.contains(nicks, nick))
						removed.add(nick);
				}
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						for (Component comp : pnlSpecialMessages.getComponents()) {
							if (!(comp instanceof SpecialMessageInput))
								continue;
							if (removed.contains(((SpecialMessageInput) comp).nickname)) {
								pnlSpecialMessages.remove(comp);
							}
						}
						for (String nickname : added) {
							pnlSpecialMessages.add(new SpecialMessageInput(nickname), constraints);
						}
						pnlSpecialMessages.revalidate();
					}
				});
			}

			@Override
			public void messageReceived(ReceivedMessage mess) {
				EventQueue.invokeLater(new Runnable() {

					@Override
					public void run() {
						if (gui.hasFocus()) {
							AudioPlayer.getInstance().play("in_focus.wav");
						} else {
							AudioPlayer.getInstance().play("in_background_" + RandomKeyGenerator.generateNumKey(3)+".wav");
						}
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
				AudioPlayer.getInstance().play("disconnect.wav");
				gui.changeContentPane(new ConnectPanel(gui));
			}
		}, addr, Integer.valueOf(port), nickname);
		receiveThread = new Thread(client);
		setLayout(new BorderLayout(5, 5));

		JLabel lblMessages = new JLabel("Messages:");
		lblMessages.setFont(lblMessages.getFont().deriveFont(Font.BOLD));
		add(lblMessages, BorderLayout.NORTH);

		splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.95);
		splitPane.setContinuousLayout(true);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);

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
		splitPane.add(messageScroller, JSplitPane.TOP);

		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		splitPane.setRightComponent(scrollPane);

		pnlSpecialMessages = new JPanel();
		scrollPane.setViewportView(pnlSpecialMessages);
		GridBagLayout gbl_pnlSpecialMessages = new GridBagLayout();
		gbl_pnlSpecialMessages.columnWidths = new int[] { 0 };
		gbl_pnlSpecialMessages.rowHeights = new int[] { 0 };
		gbl_pnlSpecialMessages.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_pnlSpecialMessages.rowWeights = new double[] { Double.MIN_VALUE };
		pnlSpecialMessages.setLayout(gbl_pnlSpecialMessages);

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
				ArrayList<SpecialMessageInput> inputsWithMessages = new ArrayList<>();
				for (Component comp : pnlSpecialMessages.getComponents()) {
					if (!(comp instanceof SpecialMessageInput))
						continue;
					if (((SpecialMessageInput) comp).getMessage() != null)
						inputsWithMessages.add((SpecialMessageInput) comp);
				}
				final AlphabetMode alphabet;
				int messageLength = 0;
				{
					StringBuilder cumulatedMessages = new StringBuilder(txtFieldDefaultMessage.getText().strip());
					messageLength = cumulatedMessages.length();
					for (SpecialMessageInput input : inputsWithMessages) {
						String messageFromInput = input.getMessage();
						messageLength = Math.max(messageFromInput.length(), messageLength);
						cumulatedMessages.append(messageFromInput);
					}
					if (cumulatedMessages.length() == 0)
						return;
					alphabet = findAlphabet(cumulatedMessages.toString());
				}
				ConcurrentHashMap<String, String> specialKeys = new ConcurrentHashMap<>();
				OTP otpInstance = otpInstances.getOrDefault(alphabet, defaultOTP);
				KeyCiphertextPair defaultMessage = otpInstance.encryptWithRandomKey(Sanitizer
						.sanitize(padRight(txtFieldDefaultMessage.getText().strip(), messageLength), alphabet).result);
				for (SpecialMessageInput input : inputsWithMessages) {
					specialKeys.put(input.nickname, otpInstance.inferKey(padRight(input.getMessage(), messageLength),
							defaultMessage.ciphertext));
				}

				try {
					client.send(new OTPMessage(alphabet, defaultMessage, specialKeys, client.nickname));
					txtFieldDefaultMessage.setText("");
					for (SpecialMessageInput input : inputsWithMessages) {
						input.resetSpecialMessageInput();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Your message could not be sent!", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		pnlSendDefaultMessages.add(btnSendMessage, BorderLayout.EAST);

		receiveThread.start();
		AudioPlayer.getInstance().play("connect.wav");
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

	/**
	 * Pads a String with space characters
	 * 
	 * @param s The String to be padded
	 * @param n The desired length of the resulting string
	 * @return The padded string
	 * @author RealHowTo on StackOverflow (see this answer:
	 *         {@linkplain https://stackoverflow.com/questions/388461/how-can-i-pad-a-string-in-java/391978#391978}
	 */
	private static String padRight(String s, int n) {
		return String.format("%-" + n + "s", s);
	}

}
