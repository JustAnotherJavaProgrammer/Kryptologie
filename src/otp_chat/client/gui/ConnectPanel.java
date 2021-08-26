package otp_chat.client.gui;

import javax.swing.JPanel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.SwingConstants;

import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class ConnectPanel extends JPanel {

	private static final long serialVersionUID = 2724405629196672214L;
	private JTextField enterAddr;
	private JTextField enterPort;
	private JButton btnConnect;
	private JTextField enterNick;

	/**
	 * Create the panel.
	 */
	public ConnectPanel(GUI gui) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JPanel outer_pnl = new JPanel();
		add(outer_pnl);
		outer_pnl.setLayout(new BoxLayout(outer_pnl, BoxLayout.X_AXIS));

		JPanel inner_pnl = new JPanel();
		outer_pnl.add(inner_pnl);
		FlowLayout fl_inner_pnl = (FlowLayout) inner_pnl.getLayout();
		fl_inner_pnl.setAlignment(FlowLayout.LEADING);

		JLabel lblAddress = new JLabel("Network address:");
		lblAddress.setFont(lblAddress.getFont().deriveFont(Font.BOLD));
		inner_pnl.add(lblAddress);

		enterAddr = new JTextField();
		enterAddr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enterPort.requestFocus();
			}
		});
		enterAddr.requestFocus();
		inner_pnl.add(enterAddr);
		enterAddr.setColumns(15);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setFont(lblPort.getFont().deriveFont(Font.BOLD));
		inner_pnl.add(lblPort);

		enterPort = new JTextField();
		enterPort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enterNick.requestFocus();
			}
		});
		inner_pnl.add(enterPort);
		enterPort.setColumns(10);

		JLabel lblNickname = new JLabel("Nickname:");
		inner_pnl.add(lblNickname);

		enterNick = new JTextField();
		enterNick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnConnect.doClick();
			}
		});
		enterNick.setColumns(10);
		inner_pnl.add(enterNick);

		btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					gui.changeContentPane(
							new ChatPanel(enterAddr.getText(), enterPort.getText(), enterNick.getText(), gui));
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Please enter a valid port number and try again.",
							"Connection failed", JOptionPane.WARNING_MESSAGE);
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"The server could not be found!\nPlease enter a valid hostname of IP address and try again.",
							"Connection failed", JOptionPane.WARNING_MESSAGE);
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,
							"Connection failed!\nPlease try again.\n\nMore details:\n" + e1.getMessage(),
							"Connection failed", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConnect.setMnemonic('c');
		outer_pnl.add(btnConnect);

		outer_pnl.add(Box.createRigidArea(new Dimension(5, 0)));

		JSeparator separator = new JSeparator();
		add(separator);

		add(new Box.Filler(new Dimension(0, 0), new Dimension(0, Short.MAX_VALUE), new Dimension(0, Short.MAX_VALUE)));

		JLabel lblWelcome = new JLabel(
				"<html><font size=\"15\">Welcome to <font size=\"20\">OTPChat</font>!</font></html>");
		lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcome.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblWelcome.setFont(lblWelcome.getFont().deriveFont(lblWelcome.getFont().getSize() * 3));
		add(lblWelcome);

		add(new Box.Filler(new Dimension(0, 0), new Dimension(0, Short.MAX_VALUE), new Dimension(0, Short.MAX_VALUE)));

	}

}
