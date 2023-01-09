package ui.creationPlaylist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import playlist.PlaylistElement;
import playlist.PlaylistReaderWriter;
import settings.MediaPlayerSettings;

/**
 * MenuBar dell' interfaccia per la creazione di playlists.
 * Permette di salvare una playlist(stampandola su file).
 *
 */
public class CreationPlaylistMenuBar extends JMenuBar {

	private static final long serialVersionUID = 0;
	private MediaPlayerSettings settings = MediaPlayerSettings.getSettings();
	private String nomePlaylist;
	
	public CreationPlaylistMenuBar(final JFrame frame, final ArrayList<PlaylistElement> playlist, final PlaylistReaderWriter playlistReaderWriter) {
		JMenu menuFile = new JMenu("File");
		JMenuItem save = new JMenuItem("Salva");

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				salvaFile(playlist, playlistReaderWriter);
			}
		});

		menuFile.add(save);
		JMenuItem exit = new JMenuItem("Chiudi");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				richiestaSalvataggio(frame, playlist, playlistReaderWriter);
			}
		});
		menuFile.add(exit);
		add(menuFile);
	}
	
	private void salvaFile(ArrayList<PlaylistElement> playlist, PlaylistReaderWriter playlistReaderWriter){
		nomePlaylist = JOptionPane.showInputDialog(null, "Inserisci nome Playlist:");
		try{
			if (!nomePlaylist.isEmpty()) {
				playlistReaderWriter.salvaPlaylist(playlist, "Playlist/"+nomePlaylist+settings.getPlaylistFileExtension());
			}else{
				playlistReaderWriter.salvaPlaylist(playlist, "Playlist/PlaylistSenzaNome"+settings.getPlaylistFileExtension());
				JOptionPane.showMessageDialog(null, "Playlist salvata come: PlaylistSenzaNome"+settings.getPlaylistFileExtension());
			}
		}catch (Exception e) {
			
		}
		
	}

	/**
	 * Se si cerca di chiudere il frame senza aver salvato le modifiche alla playlist,
	 * viene visualizzato un Joptionpane, che da' la posibilita' di salvare.
	 * 
	 * @param frame
	 * @param playlist
	 * @param playlistReaderWriter
	 */
	public void richiestaSalvataggio(final JFrame frame,
			final ArrayList<PlaylistElement> playlist,
			final PlaylistReaderWriter playlistReaderWriter) {
		if (!playlistReaderWriter.isPlaylistSalvata()) {
			int selectedOption = JOptionPane.showConfirmDialog(null,"Playlist non salvata.Salvarla ora?");
			if (selectedOption == JOptionPane.OK_OPTION) {
				salvaFile(playlist, playlistReaderWriter);
				frame.dispose();
			}
			if (selectedOption == JOptionPane.NO_OPTION) {
				frame.dispose();
			}
			if (selectedOption == JOptionPane.CANCEL_OPTION) {
			}

		} else {
			frame.dispose();
		}
	}

}
