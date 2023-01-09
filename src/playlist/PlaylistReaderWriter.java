package playlist;

import java.io.File;
import java.util.ArrayList;

import utils.FileLoaderWriter;

/**
 * Classe che permette di leggere o scrivere su file una playlist. 
 *
 */
public class PlaylistReaderWriter {

	private IPlaylistFileInterpreter playlistFileInterpreter;
	private FileLoaderWriter fileLoaderWriter = new FileLoaderWriter();
	private boolean playlistSalvata = true;
	private ArrayList<PlaylistElement> playlist;
	
	/**
	 * @param playlistFileInterpreter Il gestore del contenuti dei playlistFiles
	 */
	public PlaylistReaderWriter(IPlaylistFileInterpreter playlistFileInterpreter) {
		super();
		this.playlistFileInterpreter = playlistFileInterpreter;
	}
	
	public void setPlaylistSalvata(boolean playlistSalvata) {
		this.playlistSalvata = playlistSalvata;
	}
	
	public boolean isPlaylistSalvata() {
		return playlistSalvata;
	}
	
	/**
	 * Legge il contenuto della playlist da un file.
	 * 
	 * @param playlistFile Il file che contiene la playlist
	 * @return La playlist letta dal file in formato di ArrayList<PlaylistElement> eseguibile
	 */
	public ArrayList<PlaylistElement> readPlaylistContent(File playlistFile) {
		this.playlist = playlistFileInterpreter.createPlaylist(fileLoaderWriter.load(playlistFile), playlistFile.getName());
		return playlist;
	}
	
	/**
	 * Salva la playlist su un file di nome nomePlaylist.
	 * 
	 * @param playlist La playlist da salvare
	 * @param nomePlaylist Il nome della playlist
	 */
	public void salvaPlaylist(final ArrayList<PlaylistElement> playlist, String nomePlaylist) {
		fileLoaderWriter.write(playlistFileInterpreter.createPlaylistToPrint(playlist), new File(nomePlaylist));
		setPlaylistSalvata(true);
	}
}
