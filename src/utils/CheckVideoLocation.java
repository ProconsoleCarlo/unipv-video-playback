package utils;

import gallery.Gallery;

import java.awt.FileDialog;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class CheckVideoLocation {

	private Gallery gallery = Gallery.getGallery();
	private File elementFile;
	
	public File getElementFile() {
		return elementFile;
	}
	/**
	 * controlla che il video sia presente nella mappa.
	 * Se non si trova, elimina la corrispondenza dalla mappa.
	 * 
	 * @param elementFile
	 */
	public boolean isFileNotFound(File elementFile) {
		if (elementFile == null) {
			this.elementFile = null;
			return true;
		}else if (!elementFile.exists()) {
			//TODO creare joptionpane con alternative Riloca o Elimina
			Object[] options = {"Riloca",
					"Elimina"
					};
			int selectedOption = JOptionPane.showOptionDialog(null, "Non ho trovato su disco:\n"+ elementFile +"\nVuoi indicarmi la sua nuova posizione o lo elimino?",
					"Attenzione!",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,
					options,
					options[1]
					);
			if (selectedOption == JOptionPane.YES_OPTION) {
				FileDialog explorer = new FileDialog(new JFrame(), "Riloca", FileDialog.LOAD);
				explorer.setDirectory("VideoTest");
				explorer.setVisible(true);
				if (explorer.getDirectory() != null && explorer.getFile() != null) {
					//..posso rilocarlo...
					gallery.relocate(elementFile, new File(explorer.getDirectory(), explorer.getFile()));
					this.elementFile = new File(explorer.getDirectory(), explorer.getFile());
					return false;
				}else {
					//...oppure eliminarlo
					this.elementFile = null;
					return true;
				}	
			}else if (selectedOption == JOptionPane.NO_OPTION) {
				//...oppure eliminarlo
				this.elementFile = null;
				return true;
			}
		}
		this.elementFile = null;
		return false;
	}
}
