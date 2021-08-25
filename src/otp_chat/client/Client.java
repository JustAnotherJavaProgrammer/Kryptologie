package otp_chat.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import otp_chat.OTPMessage;
import otp_chat.ReceivedMessage;

public class Client implements Runnable {
	private final ClientListener listener;
	public final Socket sock;
	private final ObjectOutputStream out;
	public final String nickname;

	public Client(ClientListener listener, String host, int port, String nickname)
			throws UnknownHostException, IOException {
		this.listener = listener;
		this.nickname = nickname;
		sock = new Socket(host, port);
		this.out = new ObjectOutputStream(sock.getOutputStream());
		this.out.writeObject(nickname);
		this.out.flush();
	}

	public void run() {
		try {
			ObjectInputStream in = new ObjectInputStream(sock.getInputStream());
			while (!Thread.interrupted()) {
				Object obj = in.readObject();
				if (obj instanceof ReceivedMessage)
					listener.messageReceived((ReceivedMessage) obj);
				else if (obj instanceof String[])
					listener.nicknamesUpdated((String[]) obj);
				else
					throw new IllegalStateException("Unknown message type!");
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		listener.disconnected();
	}

	public void send(OTPMessage message) throws IOException {
		out.writeObject(message);
		out.flush();
	}
}
