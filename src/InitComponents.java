import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class InitComponents {

	public static JFrame setupJFrameAndGet(String title, int width, int height) {
		JFrame tmpJF = new JFrame(title);
		tmpJF.getContentPane().setPreferredSize(new Dimension(width, height));
		tmpJF.pack();
		tmpJF.setLocationRelativeTo(null);
		tmpJF.setLayout(null);
		tmpJF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return tmpJF;
	}
	public static JTextArea setupJTextAreaAndGet(String text, int rows,int columns, boolean setEditableFlag,
			boolean setLineWrapFlag, boolean setWrapStyleWordFlag, boolean setBoundsFlag, int xpos, int ypos, int width,int height) {
		JTextArea tmpJTA = new JTextArea(text, rows, columns);
		tmpJTA.setEditable(setEditableFlag);
		tmpJTA.setLineWrap(setLineWrapFlag);
		tmpJTA.setWrapStyleWord(setWrapStyleWordFlag);
		if (setBoundsFlag = true) {
			tmpJTA.setBounds(xpos, ypos, width, height);
		}
		return tmpJTA;
	}

	public static JScrollPane setupScrollableJTextAreaAndGet(JTextArea jta, int xpos, int ypos, int width, int height) {
		JScrollPane tmpJSP = new JScrollPane(jta);
		tmpJSP.setBounds(xpos, ypos, width, height);
		return tmpJSP;
	}

}
