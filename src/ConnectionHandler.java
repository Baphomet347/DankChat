import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JOptionPane;

public class ConnectionHandler {

	ServerSocket server;
	int serverPort = 6969;
	boolean serverup = false;
	static boolean connected = false;
	boolean chatting=false;

	public void startChat() {

		String serverIp="localhost";
		Object[] options1 = { "JOIN EXISTING CHAT ROOM", "CREATE NEW CHATROOM"};
		int result = JOptionPane.showOptionDialog(null, null,"Enter a Number",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
				null, options1, null);
		if (result == JOptionPane.YES_OPTION){

			serverIp = JOptionPane.showInputDialog("What Server do you want to connect to?","");
			System.out.println("JOIN");

		} else if (result==JOptionPane.NO_OPTION ) {
			System.out.println("HOST");
			//			serverIp = "localhost";
			Thread hostthread = new Thread() {
				@Override
				public void run() {
					ChatServer ch = new ChatServer();
					ch.addClient("localhost");
					ch.listen();
				}
			};
			hostthread.start();
		} else {
			System.exit(0);
		}
		new ChatClient().connectToServer(serverIp);
	}


	public String clientStatus(String ip, int timeout) {
		String status;
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ip, serverPort), timeout);
			socket.close();
			status = "online";
			// return true;
		} catch (Exception ex) {
			// return false;
			status = "offline";
		}
		return status;
	}
}
