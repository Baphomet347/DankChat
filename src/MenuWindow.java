import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MenuWindow {

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public MenuWindow() {

	}

	public void createMenu() {
		JFrame jf = InitComponents.setupJFrameAndGet("Menu", screenSize.width - 200, screenSize.height - 200);

		jf.setVisible(true);
	}

}
