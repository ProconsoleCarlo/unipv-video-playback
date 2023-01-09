package videoplayer.ui;

import java.awt.GridLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CutterPanel extends JPanel {

	public CutterPanel(File fileVideo) {
		
		setLayout(new GridLayout(4, 1));
		
		JTextField inizioText = new JTextField("00:00:00");
		JTextField fineText = new JTextField("99:99:99");
		JButton tagliaButton = new JButton("Taglia");
		JButton ripristinaButton = new JButton("Ripristina");
		
		add(inizioText);
		add(fineText);
		add(tagliaButton);
		add(ripristinaButton);
	}
}