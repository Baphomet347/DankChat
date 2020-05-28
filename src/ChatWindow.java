import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class ChatWindow implements KeyListener, ActionListener{

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	int screenWidth = screenSize.width;
	int screenHeight = screenSize.height;
	String title;
	String text;
	JFrame jf;
	static JTextArea jta;
	JScrollPane jsp;

	static int initialCaretPosition;
	int currentCaretPosition;
	boolean inputAvailable = false;

	int pointerPos=0;
	int BACKSPACE = 8;
	int ENTER = 10;
	int PG_UP = 33; // do nothing for this key pressed
	int PG_DN = 34; // do nothing for this key pressed
	int END = 35;
	int HOME = 36;
	int LEFT_ARROW = 37;
	int UP_ARROW = 38; // do nothing for this key pressed
	// int RIGHT_ARROW = 39; // handled by JTextArea
	int DOWN_ARROW = 40; // do nothing for this key pressed

	int CTRL = 128;
	int A = 65; // disable ctrl-a
	int H = 72; // handle ctrl-h

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int cCurrPos = jta.getCaretPosition();
		jta.selectAll();
		jta.copy();
		jta.select(cCurrPos, cCurrPos);

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		int keyCode = ke.getKeyCode();

		if ((keyCode == PG_UP) || (keyCode == PG_DN) || (keyCode == UP_ARROW) || (keyCode == DOWN_ARROW)
				|| ((keyCode == A) && (ke.getModifiersEx() == CTRL))) {
			ke.consume();
		} else if ((keyCode == LEFT_ARROW) || (keyCode == BACKSPACE)
				|| ((keyCode == H) && (ke.getModifiersEx() == CTRL))) {
			synchronized (this) {
				if (jta.getCaretPosition() <= initialCaretPosition) {
					ke.consume();
				}
			}
		} else if (keyCode == HOME) {
			synchronized (this) {
				jta.setCaretPosition(initialCaretPosition);
				ke.consume();
			}
		} else if (keyCode == END) {
			synchronized (this) {
				jta.setCaretPosition(jta.getDocument().getLength());
				ke.consume();
			}
		}
		else if (keyCode == ENTER) {
			jta.setCaretPosition(jta.getDocument().getLength());
			synchronized (this) {
				currentCaretPosition = jta.getCaretPosition();
				try {
					String charAtInitialCaretPosition = jta.getText(initialCaretPosition, 1);
					if ((charAtInitialCaretPosition.equals("\n")) == true) {
						initialCaretPosition++;
					}
				} catch (Exception e) {
				}
				if ((currentCaretPosition - initialCaretPosition) > 0) {
					inputAvailable = true;
					notifyAll();
				}
			}
		}
	}

	String getInputFromJTextArea(JTextArea jta) {
		int len = 0;
		String inputFromUser = "";
		while (true) {
			synchronized (this) {
				if (inputAvailable == true) {
					len = currentCaretPosition - initialCaretPosition;
					try {
						inputFromUser = jta.getText(initialCaretPosition, len);
						initialCaretPosition = currentCaretPosition;
					} catch (Exception e) {
						inputFromUser = "";
						return inputFromUser;
					} // end of outer try catch

					inputAvailable = false;
					return inputFromUser;
				} else {
					try {
						wait();
						continue;
					} catch (Exception e) {
					}
				}
			}
		}
	}

	public void outputToJTextArea(JTextArea jta, String text) {
		try {
			jta.append(text);
			jta.setCaretPosition(jta.getDocument().getLength());
			jta.repaint();
			// jta.append(text);
			jta.setCaretPosition(jta.getDocument().getLength());
			synchronized (this) {
				initialCaretPosition = jta.getCaretPosition();
			}
		} catch (Exception e) {

		}
	}

	public static void outputReceived(String text) {
		try {
			text = "RECEIVED: "+text+"\n";
			jta.append(text);
			jta.setCaretPosition(jta.getDocument().getLength());
			jta.repaint();
			jta.setCaretPosition(jta.getDocument().getLength());
			initialCaretPosition = jta.getCaretPosition();
		} catch (Exception e) {

		}

	}

	void begin() {
		CommandHandler ch = new CommandHandler();
		while (true) {
			outputToJTextArea(jta, ChatClient.username + ": ");
			String input = getInputFromJTextArea(jta);
			if (input.startsWith("/") == true) {
				outputToJTextArea(jta, ch.executeCommand(input));
			} else {
				if (ConnectionHandler.connected == true) {
					System.out.println();
					//					new ChatClient().sendMessage(input);
				}
			}
		}
	}

	void configureJTextAreaForInputOutput(JTextArea jta) {
		jta.addKeyListener(this);
		for (MouseListener listener : jta.getMouseListeners()) {
			jta.removeMouseListener(listener);
		}
		for (MouseMotionListener listener : jta.getMouseMotionListeners()) {
			jta.removeMouseMotionListener(listener);
		}
		for (MouseWheelListener listener : jta.getMouseWheelListeners()) {
			jta.removeMouseWheelListener(listener);
		}
	}

	void createAndShowGUI() {
		title = "DankChat";
		jf = InitComponents.setupJFrameAndGet(title, screenWidth - 200, screenHeight - 200);
		jta = InitComponents.setupJTextAreaAndGet("", 1000, 100, true, true, true, false, 0, 0, 0, 0);
		configureJTextAreaForInputOutput(jta);

		jsp = InitComponents.setupScrollableJTextAreaAndGet(jta, 10, 10, screenWidth - 220, screenHeight - 220);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jf.add(jsp);
		// jf.setLocation(screenWidth / 5, screenHeight / 6);
		WindowListener exitListener = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int confirm = JOptionPane.showOptionDialog(null, "are you sure to close DankChat?",
						"Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
				if (confirm == 0) {
					System.exit(0);
				}
			}
		};
		jf.addWindowListener(exitListener);

		jta.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		jf.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
		jta.setBackground(Color.BLACK);
		jta.setForeground(Color.WHITE);
		jf.setVisible(true);
	}


}
