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

	public ChatServer() {

	}

	@Override
	public void run() {
		//		connectedIPs.add("127.0.0.1");
		//		connectedIPs.add("192.168.20.3");
		//		connectedIPs.add("localhost");
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
				String[] message=null;
				try {
					message = (String[]) in.readObject();
				} catch (ClassNotFoundException e) {

					e.printStackTrace();
				}
				String receivedtext = new MessageBuilder().getSendtext(message);
				int messageFlag = new MessageBuilder().getMessageFlag(message);
				System.out.println("Server received: "+receivedtext+"\nwith Flag: "+messageFlag);
				//				ChatWindow.outputReceived(receivedtext);
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
	}
	public void addClient(String ip) {
		connectedIPs.add(ip);
	}
	public void interpretMessageFlag(int messageFlag) {

	}
	//	public static void main (String[] args) {
	//		new ChatServer().run();
	//	}


}
