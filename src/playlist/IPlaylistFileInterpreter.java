package playlist;

import java.util.ArrayList;

/**
 * Un generico interprete di file contenente una playlist.
 *
 */
public interface IPlaylistFileInterpreter {

	/**
	 * Crea un ArrayList<PlaylistElement> data la plalist scritta in ArrayList<String>.
	 * @param playlistPrinted La playlist in forma di ArrayList<String>
	 * @return La playlist in forma di ArrayList<PlaylistElement>
	 */
	public ArrayList<PlaylistElement> createPlaylist(ArrayList<String> playlistPrinted, String playlistName);
	
	/**
	 * Rappresenta la playlist in un ArrayList<String> stampabile (con hash-code).
	 * @param playlist La playlist da stampare
	 * @return La playlist in forma di ArrayList<String> stampabile
	 */
	public ArrayList<String> createPlaylistToPrint(ArrayList<PlaylistElement> playlist);
}
