import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ListenServer {

	ServerSocket server;
	int serverPort = 6969;

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
				DataInputStream dis = new DataInputStream(socket.getInputStream());

				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
