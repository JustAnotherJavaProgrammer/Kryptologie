package otp_chat.server;

import java.io.IOException;
import java.net.InetAddress;

public class Headless_Server {
	public static void main(String[] args) throws IOException {
		Server serv = new Server(4444, new ConnectionListener() {

			@Override
			public void onDisconnect(ClientConnection conn) {
				System.out.println(conn.nickname + " disconnected");
			}

			@Override
			public void onConnect(ClientConnection conn) {
				System.out.println(conn.nickname + " connected");
			}
		});
		System.out.println("Starting server on port " + serv.port + "...");
		System.out.println("Listing own IPs: " + InetAddress.getLocalHost().getHostName() + " or "
				+ InetAddress.getLocalHost().getHostAddress());
		serv.start();
	}
}
