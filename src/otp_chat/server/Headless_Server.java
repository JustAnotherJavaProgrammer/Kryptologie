package otp_chat.server;

public class Headless_Server {

	public static void main(String[] args) {
		Server s = new Server(4444, new ConnectionListener() {
			
			@Override
			public void onDisconnect(ClientConnection conn) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onConnect(ClientConnection conn) {
				// TODO Auto-generated method stub
				
			}
		});
	}

}
