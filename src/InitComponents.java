import javax.swing.JFrame;

public class InitComponents {

	public static JFrame setupJFrameAndGet(String title, int width, int height) {
		JFrame tmpJF = new JFrame(title);
		tmpJF.setSize(width, height);
		tmpJF.setLocationRelativeTo(null);
		tmpJF.setLayout(null);
		tmpJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return tmpJF;
	}

}
