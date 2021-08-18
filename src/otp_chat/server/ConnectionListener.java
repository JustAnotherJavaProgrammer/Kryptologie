package otp_chat.server;

public interface ConnectionListener {
	public void onConnect(ClientConnection conn);
	public void onDisconnect(ClientConnection conn);
	
}
