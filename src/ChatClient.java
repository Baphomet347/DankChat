import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatClient extends Thread {
	static String username = "defaultuser";
	String ipadressserver;
	ServerSocket server;
	final static int clientPort = 6970;
	static int connectedchats;
	String sendtext, fontcolor, messageFlag;
	String[] message= new String[4];

	@Override
	public void run() {
		try {
			Socket socket = new Socket(ipadressserver, ChatServer.serverPort);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			String[] message = new MessageBuilder().buildMessage(username, sendtext, "Color.BLACK", 1);
			out.writeObject(message);
			out.flush();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void listen() {
		try {
			server = new ServerSocket(clientPort);
			System.out.println("ServerSocket: " + server);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			try {
				Socket socket = server.accept();
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				try {
					String[] message = (String[]) in.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				//				System.out.println("Client received: "+receivedtext+"\n");
				//				ChatWindow.outputReceived(receivedtext);
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void connectToServer(String serverIp) {
		try {
			Socket socket = new Socket(serverIp, ChatServer.serverPort);

			sendtext = InetAddress.getLocalHost().getHostAddress();
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			String[] message = new MessageBuilder().buildMessage(username, sendtext, "Color.BLACK", 69);
			out.writeObject(message);
			out.flush();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//	public void addHost(String ip) {
	//		connectedchats++;
	//		ipadressserver = ip;
	//		System.out.println("ADDED: " + ipadressserver);
	//
	//	}

}
