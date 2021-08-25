package otp_chat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	private final ClientListener listener;
	public final Socket sock;

	public Client(ClientListener listener, String host, int port) throws UnknownHostException, IOException {
		this.listener = listener;
		sock = new Socket(host, port);
	}

	public void start() {
		try {
			ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
			while (!Thread.interrupted()) {

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
