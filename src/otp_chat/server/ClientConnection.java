package otp_chat.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import otp_chat.OTPMessage;
import otp_chat.ReceivedMessage;

public class ClientConnection implements Runnable {

	public final Thread thread;
	public final String nickname;
	private final Server server;
	private final Socket socket;

	public ClientConnection(String nickname, Socket sock, Server server) {
		this.server = server;
		this.socket = sock;
		this.nickname = nickname;

		this.thread = new Thread(this);
		this.thread.start();
	}

	@Override
	public void run() {
		try {
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			while (socket.isConnected() && !Thread.interrupted()) {
				switch (in.read()) {
				case 0:
					server.broadcast((OTPMessage) in.readObject());
					break;
				case 1: 
					sendNicknames();
					break;
				default:
					break;
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.disconnected(this);
	}
	
	public void disconnect() throws IOException {
		socket.close();
		server.disconnected(this);
	}
	
	public void sendNicknames() throws IOException {
		String[] nicknames = new String[server.connections.size()];
		for (int i = 0; i < nicknames.length; i++) {
			nicknames[i] = server.connections.get(i).nickname;
		}
		new ObjectOutputStream(socket.getOutputStream()).writeObject(nicknames);
	}

	public boolean send(OTPMessage message) throws IOException {
		socket.getOutputStream().write(0);
		boolean hasPersonalizedKey = message.personalizedKeys.containsKey(nickname);
		ReceivedMessage mess = hasPersonalizedKey
				? new ReceivedMessage(message.personalizedKeys.get(nickname), message.ciphertext, message.alphabet, nickname)
				: new ReceivedMessage(message.key, message.ciphertext, message.alphabet, nickname);
		new ObjectOutputStream(socket.getOutputStream()).writeObject(mess);
		return hasPersonalizedKey;
	}

}
