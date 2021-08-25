package otp_chat.client;

import java.awt.HeadlessException;
import java.io.IOException;

import javax.swing.JOptionPane;

import otp.OTP;
import otp_chat.OTPMessage;
import otp_chat.ReceivedMessage;
import util.AlphabetMode;

public class TestClient {

	public static void main(String[] args) {
		OTP otp = new OTP(AlphabetMode.MODE_UNIVERSAL);
		try {
			Client c = new Client(new ClientListener() {

				@Override
				public void nicknamesUpdated(String[] nicks) {
					System.out.println("Updated list of nicknames:");
					for(String nick : nicks) {
						System.out.println(nick);
					}
					System.out.println("---");
				}

				@Override
				public void messageReceived(ReceivedMessage mess) {
					JOptionPane.showMessageDialog(null, otp.decrypt(mess), "Message from " + mess.nickname, JOptionPane.PLAIN_MESSAGE);
				}

				@Override
				public void disconnected() {
					JOptionPane.showMessageDialog(null, "Connection closed");
				}
			}, JOptionPane.showInputDialog("Please enter a network address:"),
					Integer.valueOf(JOptionPane.showInputDialog("Enter the port number:")), JOptionPane.showInputDialog("Enter your nickname: "));
			Thread clientThread = new Thread(c);
			clientThread.start();
			while(true) {
				String messageText = JOptionPane.showInputDialog("Enter a message: ");
				if(messageText == null) {
					break;
				}
				c.send(new OTPMessage(otp.mode, otp.encryptWithRandomKey(messageText), c.nickname));
			}
			clientThread.interrupt();
		} catch (HeadlessException | NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}

}
