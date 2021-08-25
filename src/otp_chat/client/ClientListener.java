package otp_chat.client;

import otp_chat.ReceivedMessage;

public interface ClientListener {
	public void nicknamesUpdated(String[] nicks);
	public void messageReceived(ReceivedMessage mess);
}
