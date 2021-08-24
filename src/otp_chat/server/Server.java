package otp_chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import otp_chat.OTPMessage;

public class Server {
	public final int port;
	private final ConnectionListener listener;
	List<ClientConnection> connections = Collections.synchronizedList(new ArrayList<>());

	public Server(int port, ConnectionListener listener) {
		this.port = port;
		this.listener = listener;
	}

	public void start() throws IOException {
		ServerSocket s = new ServerSocket(port);
		while (!Thread.interrupted()) {
			Socket conn = s.accept();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String nick = reader.readLine();
			ClientConnection cCon = new ClientConnection(nick, conn, this);
			connections.add(cCon);
			listener.onConnect(cCon);
		}
		s.close();
	}

	public void broadcast(OTPMessage message) {
		for (ClientConnection conn : connections) {
			try {
				conn.send(message);
			} catch (IOException e) {
				System.err.println("Broadcasting message to " + conn.nickname + " failed:");
				e.printStackTrace();
			}
		}
	}

	public void disconnected(ClientConnection conn) {
		connections.remove(conn);
		listener.onDisconnect(conn);
	}

	public ClientConnection[] getConnections() {
		ClientConnection[] conns = new ClientConnection[0];
		return connections.toArray(conns);
	}

}
