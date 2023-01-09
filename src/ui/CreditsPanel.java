package ui;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CreditsPanel extends JPanel{

	private static final long serialVersionUID = 1L;

	public CreditsPanel() {
		super();
		realize();
	}
	
	private void realize() {
		String crediti = "<html>Questo programma e' stato realizzato da:<br>" +
				"<br>"+"<p style=\"text-align:center\">"+
				"<font color=\"#0000CD\">Gabriele Gisco<br>" +
				"<font color=\"#00FF00\">Erika Strotz<br>" +
				"<font color=\"#1E90FF\">Eleonora Aiello<br>" +
				"<font color=\"#FF8C00\">Tomas Lacovara<br>" +
				"<font color=\"#4169E1\">Fabio Tagliani<br>" +
				"<font color=\"#FF0000\">Carlo Bobba<br>" +
				"<br>"+
				"<font color=\"black\">Grazie per averlo utilizzato! :) </p></html>";
		
		Object[] options = {"Chiudi",
				"Contattaci"
				};
		int n = JOptionPane.showOptionDialog(this, crediti, "Informazioni sul programma",
				JOptionPane.YES_NO_OPTION, 
				JOptionPane.INFORMATION_MESSAGE, 
				null, options, options[1]);
		if (n == JOptionPane.NO_OPTION) {
			JOptionPane.showMessageDialog(null, "Grazie per il tuo interessamento! :)");
		}
	}
}
