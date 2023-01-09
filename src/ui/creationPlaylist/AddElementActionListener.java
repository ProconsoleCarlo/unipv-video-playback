package ui.creationPlaylist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.JComboBox;

import settings.MediaPlayerSettings;

/**
 * ActionListener per aggiungere l'elemento selezionato nella ComboBox alla playlist.
 *
 */
public class AddElementActionListener extends Observable implements ActionListener{	
	
	private PlaylistCreationFrame pcf;
	private PlaylistParametersPanel parametersPanel;
	@SuppressWarnings("rawtypes")
	private JComboBox galleryComboBox;
	
	public AddElementActionListener(PlaylistCreationFrame playlistCreationFrame) {
		super();
		this.pcf = playlistCreationFrame;
		this.parametersPanel = pcf.getParametersPanel();
		this.galleryComboBox = pcf.getGalleryComboBox();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String selectedElement = (String)galleryComboBox.getSelectedItem();
		String elementToAdd;
		int[] startAndEndTime = parametersPanel.getStartAndEndTime();
		if (selectedElement.endsWith(MediaPlayerSettings.getSettings().getPlaylistFileExtension()) && startAndEndTime!=null) {
			elementToAdd = selectedElement;
			pcf.addPlaylistElement(elementToAdd);	
		}else {
			if (startAndEndTime!=null) {
				elementToAdd = selectedElement + " " + startAndEndTime[0] + " " + startAndEndTime[1];
				pcf.addPlaylistElement(elementToAdd);
			}
		}
		updateVisualComponents();
	}
	private void updateVisualComponents() {
		setChanged();
		notifyObservers();
	}
}
