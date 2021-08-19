package otp_chat.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
	public final int port;
	private final ConnectionListener listener;
	
	
	public Server(int port, ConnectionListener listener) {
		this.port = port;
		this.listener = listener;
	}
	
	public void start() throws IOException {
		ServerSocket s = new ServerSocket(port);
		while(!Thread.interrupted()) {
			// TODO do something
		}
	}

}
