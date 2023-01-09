package ui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;


public class VideoPanel extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;

	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}
}
