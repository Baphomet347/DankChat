import javax.swing.SwingUtilities;

public class DankChatMain {


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> startProgram());
	}

	public static void startProgram() {
		Thread listenthread = new Thread() {
			@Override
			public void run() {
				new ListenServer().listen();
			}
		};
		listenthread.start();

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
