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
	private final ObjectOutputStream out;
	private final ObjectInputStream in;

	public ClientConnection(Socket sock, Server server) throws IOException, ClassNotFoundException {
		this.server = server;
		this.socket = sock;
		this.out = new ObjectOutputStream(sock.getOutputStream());
		this.in = new ObjectInputStream(socket.getInputStream());
		this.nickname = (String) in.readObject();
		for(ClientConnection conn : server.connections) {
			if(conn.nickname.equals(this.nickname)) {
				this.socket.close();
				break;
			}
		}
		this.thread = new Thread(this);
		this.thread.start();
	}

	@Override
	public void run() {
		try {
			while (socket.isConnected() && !Thread.interrupted()) {
				server.broadcast((OTPMessage) in.readObject());
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
		writeObject(nicknames);
	}
	
	private synchronized void writeObject(Object obj) throws IOException {
		out.writeObject(obj);
		out.flush();
	}

	public boolean send(OTPMessage message) throws IOException {
		boolean hasPersonalizedKey = message.personalizedKeys.containsKey(nickname);
		ReceivedMessage mess = hasPersonalizedKey
				? new ReceivedMessage(message.personalizedKeys.get(nickname), message.ciphertext, message.alphabet,
						nickname)
				: new ReceivedMessage(message.key, message.ciphertext, message.alphabet, nickname);
		writeObject(mess);
		return hasPersonalizedKey;
	}

}
