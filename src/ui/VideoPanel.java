package ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

/**
 * Un semplice pannello su cui attaccare i componenti da visualizzare
 *
 */
public class VideoPanel extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	private Component[] components;
	
	public void setComponents(Component[] components) {
		this.components = components;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}

	public void buildComponents() {
		this.removeAll();
		this.setLayout(new BorderLayout());
		this.add(components[0], BorderLayout.CENTER);
		this.add(components[1], BorderLayout.SOUTH);
		//TODO Inserire il controllo con la risoluzione dello schermo: il video non può uscire dallo schermo!
		// Non essendo ridimensionabile il frame, non sarebbe possibile vedere il video per intero
		//qualora avesse una ris. maggiore di quella dell schermo.
		this.setSize(components[0].getPreferredSize().width,
					 components[0].getPreferredSize().height+components[1].getPreferredSize().height);
	}
}