package ui.creationPlaylist;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JOptionPane;

import mediaPlayer.VideoManager;

import playlist.PlaylistElement;
import playlist.PlaylistManager;
import settings.MediaPlayerSettings;

/**
 * MouseListener chiamato per riprodurre un video cliccando sulla sua immagine in timeline.
 */
public class ClickedElementMouseListener implements MouseListener{

	private MediaPlayerSettings settings = MediaPlayerSettings.getSettings();
	private VideoManager videoManager;
	private PlaylistManager playlistManager;
	private String elementName;
	private String type;
	
	public ClickedElementMouseListener(VideoManager videoManager, PlaylistManager playlistManager) {
		super();
		this.videoManager = videoManager;
		this.playlistManager = playlistManager;
		
	}
	public void setParameters(String elementName, String type){
		this.elementName = elementName;
		this.type = type;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	public void mousePressed(MouseEvent arg0) {
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (type.equals(settings.getPlaylistType())) {
			playPlaylist(elementName);
		}else if (type.equals(settings.getVideoType())) {
			playVideo(elementName);
		}
	}
	
	private void playPlaylist(String elementName) {
		playlistManager.pausePlaylist();
		File file = new File(elementName);
		if(playlistManager.startPlaylist(file)){
			playlistManager.playPlaylist();
		}else{
			JOptionPane.showMessageDialog(null, "ERRORE! File non valido.");
		}
	}
	
	private void playVideo(String elementName) {
		playlistManager.pausePlaylist();
		PlaylistElement element;
		String[] elements = elementName.split(" ");
		String path = elements[0];
		for (int i = 1; i < elements.length-2; i++) {
			path = path+" "+elements[i];
		}
		File file = new File(path);
		videoManager.openVideo(file);
		if (videoManager.isPlayerInitialized()) {
			int inizio = Integer.valueOf(elements[elements.length-2]);
			int fine = Integer.valueOf(elements[elements.length-1]);
			element = new PlaylistElement(file, inizio, fine);
			if(playlistManager.startPlaylist(element)){
				playlistManager.playPlaylist();
			}else{
				JOptionPane.showMessageDialog(null, "ERRORE! File non valido.");
			}
		}
	}
}
