import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer extends Thread {
	ServerSocket server;
	final static int serverPort = 6971;
	ArrayList<String> connectedIPs= new ArrayList<String>();
	String sendtext;

	String  username, receivedtext, fontColor;
	int messageFlag;

	String[] message=null;

	public ChatServer() {

	}

	@Override
	public void run() {
		for(String ip : connectedIPs) {
			String ipadress = ip;
			System.out.println(ipadress);
			try {
				Socket socket = new Socket(ipadress, serverPort);
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				String[] message = new MessageBuilder().buildMessage(ChatClient.username, sendtext, "Color.BLACK", 1);
				out.writeObject(message);
				out.flush();
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void listen() {
		try {
			server = new ServerSocket(serverPort);
			System.out.println("ServerSocket: " + server);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				Socket socket = server.accept();
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				try {
					message = (String[]) in.readObject();
				} catch (ClassNotFoundException e) {

					e.printStackTrace();
				}
				receivedtext = new MessageBuilder().getSendtext(message);
				username = new MessageBuilder().getUser(message);
				messageFlag = new MessageBuilder().getMessageFlag(message);

				System.out.println("Server received: '"+receivedtext+"' from user: '"+username+"' with Flag: '"+messageFlag+"'");
				//				ChatWindow.outputReceived(receivedtext);

				interpretMessageFlag();

				socket.close();
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
	}
	public void addClient(String ip) {
		System.out.println("IP: "+ip+" added to connection.");
		connectedIPs.add(ip);
	}
	public void interpretMessageFlag() {
		switch (messageFlag) {
		case 1: ChatWindow.outputReceive(username+": "+receivedtext);
		break;
		case 69: ChatWindow.outputReceive("--- IP: "+receivedtext+" joined the chat ---");

		break;
		}
	}
	//	public static void main (String[] args) {
	//		new ChatServer().run();
	//	}


}
