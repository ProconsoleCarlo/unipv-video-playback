package playlistReader;

import java.io.File;
import java.util.ArrayList;

import utils.FileLoaderWriter;

/**
 * Gestisce le playlist
 *
 */
public class PlaylistManager {

	private IPlaylistFileInterpreter playlistFileInterpreter;
	private FileLoaderWriter fileLoaderWriter = new FileLoaderWriter();
	/**
	 * 
	 * @param playlistFileInterpreter L'interprete dei file in cui è contenuta la playlist
	 */
	public PlaylistManager(IPlaylistFileInterpreter playlistFileInterpreter) {
		super();
		this.playlistFileInterpreter = playlistFileInterpreter;
	}
	/**
	 * Legge il contenuto della playlist da un file e restituisce un ArrayList<PlaylistElement>
	 * @param playlistFile Il file che contiene la playlist
	 * @return un ArrayList<PlaylistElement> contenente tutti i dati della playlist
	 */
	public ArrayList<PlaylistElement> readPlaylistContent(File playlistFile) {
		ArrayList<PlaylistElement> playList = new ArrayList<PlaylistElement>();
		playList.clear();
		ArrayList<String> playlistContent = new ArrayList<String>();
		playlistContent.clear();
		playlistContent = fileLoaderWriter.load(playlistFile);
		for (int i = 0; i < playlistContent.size(); i++) {
			playList.add(playlistFileInterpreter.createPlaylistElement(playlistContent.get(i)));
		}
		return playList;
	}
	
}
