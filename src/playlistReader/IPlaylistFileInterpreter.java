package playlistReader;
/**
 * Un generico interprete di file contenente una playlist
 *
 */
public interface IPlaylistFileInterpreter {

	/**
	 * Crea un PlaylistElement data una stringa contenente i dati di un elemento della playlist
	 * @param element La stringa contenente i dati di un elemento della playlist
	 * @return Il PlayListElement creato dalla stringa
	 */
	public PlaylistElement createPlaylistElement(String element);
}
