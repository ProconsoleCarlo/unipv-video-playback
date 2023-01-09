package ui.main.MenuBar;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mediaPlayer.VideoManager;
import playlist.PlaylistElement;
import playlist.PlaylistManager;

/**
 * 
 * Accedendo al File System si seleziona il video.
 *
 */
public class OpenVideoActionListener implements ActionListener{
	private VideoManager videoManager;
	private PlaylistManager playlistManager;

	public OpenVideoActionListener(VideoManager videoManager, PlaylistManager playlistManager) {
		super();
		this.videoManager = videoManager;
		this.playlistManager = playlistManager;
	}
	
	/**
	 * Una file dialog permette di scegliere il video da eseguire
	 * si puo' eseguire l'intero video o una parte di esso.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		playlistManager.pausePlaylist();
		
		FileDialog explorer = new FileDialog(new JFrame(), "Apri video", FileDialog.LOAD);
		explorer.setDirectory("VideoTest");
		explorer.setVisible(true);
		if (explorer.getDirectory() != null && explorer.getFile() != null) {
			PlaylistElement element;
			File file = new File(explorer.getDirectory(), explorer.getFile());
			playlistManager.stopPlaylist();
			playlistManager.cleanWindow();
			videoManager.openVideo(file);
			if (videoManager.isPlayerInitialized()) {
				int selectedOption = JOptionPane.showConfirmDialog(null, 
						"Riprodurre l'intero video?\nSeleziona NO per impostare i tempi di inizio e di fine.", 
						"Riprodurre l'intero video?",
						JOptionPane.YES_NO_OPTION);
				if(selectedOption==JOptionPane.YES_OPTION){
					riproduciInteroVideo(file);
				}else if(selectedOption==JOptionPane.NO_OPTION){
					try{
						int inizio = Integer.parseInt(JOptionPane.showInputDialog(null, "Tempo di inizio:"));
						if (inizio<0){
							Integer.parseInt("Voglio generare un'eccezione");
						}
						int fine = Integer.parseInt(JOptionPane.showInputDialog(null, "Tempo di fine:"));
						element = new PlaylistElement(file, inizio, fine);
						if(playlistManager.startPlaylist(element)){
							if(!playlistManager.playPlaylist()){
								riproduciInteroVideo(file);
							}
						}else{
							JOptionPane.showMessageDialog(null, "ERRORE! File non valido.","Errore", JOptionPane.ERROR_MESSAGE);
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Tempi non validi.\nVerra' riprodotto l'intero video");
						riproduciInteroVideo(file);
					}
				}
			}
		}		
	}

	private void riproduciInteroVideo(File file) {
		PlaylistElement element;
		int inizio = 0;
		int fine = (int)videoManager.getVideoPlayer().getDurationInSeconds();
		element = new PlaylistElement(file, inizio, fine);
		if(playlistManager.startPlaylist(element)){
			playlistManager.playPlaylist();
		}else{
			JOptionPane.showMessageDialog(null, "ERRORE! File non valido.","Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
}