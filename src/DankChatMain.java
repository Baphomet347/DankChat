import javax.swing.SwingUtilities;

public class DankChatMain {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> startProgram());
	}

	public static void startProgram() {
		Thread listenthread = new Thread() {
			@Override
			public void run() {
				new ConnectionHandler().listen();
			}
		};
		listenthread.start();

		Thread chatthread = new Thread() {
			@Override
			public void run() {
				new ChatServer().listen();
			}
		};
		chatthread.start();

		Thread menuthread = new Thread() {
			@Override
			public void run() {
				ChatWindow cw = new ChatWindow();
				cw.createAndShowGUI();
				cw.begin();
			}
		};
		menuthread.start();
	}
}
