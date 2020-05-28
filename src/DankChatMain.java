import java.awt.Font;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class DankChatMain {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> startProgram());
	}

	public static void startProgram() {
		UIManager.put("OptionPane.messageFont", new Font("Comic Sans MS", Font.BOLD, 30));
		UIManager.put("OptionPane.Font", new Font("Comic Sans MS", Font.PLAIN, 30));
		UIManager.put("OptionPane.buttonFont", new Font("Comic Sans MS", Font.PLAIN, 30));
		UIManager.put("TextField.font",  new Font("Comic Sans MS", Font.PLAIN, 30));

		Thread menuthread = new Thread() {
			@Override
			public void run() {
				ChatWindow cw = new ChatWindow();
				cw.createAndShowGUI();
				cw.begin();
			}
		};

		Thread connectionthread = new Thread() {
			@Override
			public void run() {
				new ConnectionHandler().startChat();
			}
		};
		connectionthread.start();

		menuthread.start();
		//
		//		Thread chatthread = new Thread() {
		//			@Override
		//			public void run() {
		//				//				new ChatServer().listen();
		//			}
		//		};
		//		chatthread.start();




	}
}
