package ui.creationPlaylist;

import gallery.Gallery;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mediaPlayer.IVideoPlayer;
import playlist.PlaylistManager;
import settings.MediaPlayerSettings;
import utils.FileLoaderWriter;

/**
 * ActionListener per la JComboBox che permette di selezionare i file da inserire in una nuova playlist.
 * Se si sceglie l'elemento 0(nel nostro caso "aggiungi" nella JComboBox di PlaylistCreationFrame)
 * si puo' aggiungere un nuovo video non presente nella mappa.
 *
 */
public class SelectElementActionListener implements ActionListener{

	private PlaylistCreationFrame pcf;
	@SuppressWarnings("rawtypes")
	private JComboBox comboBox;
	private IVideoPlayer videoPlayer;
	private PlaylistManager playlistManager;
	private int videoDuration;
	private Gallery gallery = Gallery.getGallery();
	private MediaPlayerSettings settings = MediaPlayerSettings.getSettings();
	private String elementType;
	private FileLoaderWriter fileLoaderWriter = new FileLoaderWriter();
	
	
	public SelectElementActionListener(PlaylistCreationFrame playlistCreationFrame, IVideoPlayer videoPlayer, PlaylistManager playlistManager) {
		super();
		this.pcf = playlistCreationFrame;
		this.comboBox = pcf.getGalleryComboBox();
		this.videoPlayer = videoPlayer;
		this.playlistManager = playlistManager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		playlistManager.stopPlaylist();
		playlistManager.cleanWindow();
		int selectedItem = comboBox.getSelectedIndex();
		File selectedFile = null;
		if (selectedItem == 0) {
			//Elemento "Aggiungi"
			FileDialog fileDialog = new FileDialog(new JFrame(), "Seleziona video", FileDialog.LOAD);
			fileDialog.setVisible(true);
			if (fileDialog.getDirectory() != null && fileDialog.getFile() != null) {
				selectedFile = new File(fileDialog.getDirectory(), fileDialog.getFile());
				if (selectedFile.getName().endsWith(settings.getPlaylistFileExtension())) {
					//E' una playlist
					addElement(selectedFile);
					videoDuration = getElementDuration(selectedFile);
					System.out.println(videoDuration);
					elementType = settings.getPlaylistType();
					pcf.setVideoDuration(videoDuration, elementType);
				}else if (videoPlayer.initializePlayer(selectedFile)) {
					//E' un video compatibile
					addElement(selectedFile);
					elementType = settings.getVideoType();
					updateTimeLabel(selectedFile, elementType);
				}else {
					elementType = "OTHER";
					JOptionPane.showMessageDialog(null, "ERRORE! File non valido.","Errore", JOptionPane.ERROR_MESSAGE);
					pcf.setVideoDuration(0, elementType);
				}
			}else{
				elementType = "NULL";
				JOptionPane.showMessageDialog(null, "ERRORE! Seleziona un file.","Errore", JOptionPane.ERROR_MESSAGE);
				pcf.setVideoDuration(0, elementType);
			}
		}else{
			//Elementi già noti
			selectedFile = new File((String)comboBox.getSelectedItem());
			if (selectedFile.getName().endsWith(settings.getPlaylistFileExtension())) {
				videoDuration = getElementDuration(selectedFile);
				elementType = settings.getPlaylistType();
				pcf.setVideoDuration(videoDuration, elementType);
			}else {
				videoPlayer.initializePlayer(selectedFile);
				elementType = settings.getVideoType();
				updateTimeLabel(selectedFile, elementType);
			}
		}
	}

	private int getElementDuration(File selectedFile) {
		ArrayList<String> playlistInString = fileLoaderWriter.load(selectedFile);
		String endElement = playlistInString.get(playlistInString.size()-1);
		String[] splitted = endElement.split(" ");
		return Integer.valueOf(splitted[2]);
	}

	@SuppressWarnings("unchecked")
	private void addElement(File selectedFile) {
		if (gallery.addGalleryElement(selectedFile.hashCode(), selectedFile)) {
			comboBox.addItem(selectedFile.toString());
			comboBox.repaint();
		}
		comboBox.setSelectedItem(selectedFile.getAbsolutePath());
	}

	private void updateTimeLabel(File fileVideo, String type) {
		videoDuration = (int)videoPlayer.getDurationInSeconds();
		pcf.setVideoDuration(videoDuration, type);
		videoPlayer.stop();
	}
}
