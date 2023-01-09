package ui.main.MenuBar;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import playlist.PlaylistManager;
import settings.MediaPlayerSettings;

/**
 * Accedendo al File System e' possibile selezionare una playlist.
 * E' presente un filtro per i file .pls
 */
public class OpenPlaylistActionListener implements ActionListener{
	private PlaylistManager playlistManager;
	private File filePrecedente = null;

	public OpenPlaylistActionListener(PlaylistManager playlistManager) {
		super();
		this.playlistManager = playlistManager;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		playlistManager.pausePlaylist();
		
		FileDialog explorer = new FileDialog(new JFrame(), "Apri playlist", FileDialog.LOAD);
		explorer.setDirectory("Playlist");
		explorer.setFile("*"+MediaPlayerSettings.getSettings().getPlaylistFileExtension());
		explorer.setVisible(true);
		if (explorer.getDirectory() != null && explorer.getFile() != null) {
			
			File file = new File(explorer.getDirectory(), explorer.getFile());
			if(playlistManager.startPlaylist(file)){
				filePrecedente = file.getAbsoluteFile();
				playlistManager.playPlaylist();
			}else{
				if(filePrecedente != null){
					JOptionPane.showMessageDialog(null, "ERRORE! File non valido.\n" +
							"Verra' riprodotta la playlist precedentemente in riproduzione.",
							"Errore", JOptionPane.ERROR_MESSAGE);
					playlistManager.startPlaylist(filePrecedente);
					playlistManager.playPlaylist();
				}else{
					JOptionPane.showMessageDialog(null, "ERRORE! File non valido.","Errore", JOptionPane.ERROR_MESSAGE);
				}
			}
		}		
	}
}