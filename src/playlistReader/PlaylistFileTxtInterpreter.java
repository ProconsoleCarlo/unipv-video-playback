package playlistReader;
/**
 * L'interprete di playlist contenute in un file txt e strutturate in tale modo:
 * Posizione_nella_playlist id_video tempo_di_inizio tempo_di_fine
 *
 */
public class PlaylistFileTxtInterpreter implements IPlaylistFileInterpreter{

	@Override
	public PlaylistElement createPlaylistElement(String element) {
		String[] stringSplitted = element.split(" ");
		int index = Integer.valueOf(stringSplitted[0]);
		int id = Integer.valueOf(stringSplitted[1]);
		int startTime = Integer.valueOf(stringSplitted[2]);
		int endTime = Integer.valueOf(stringSplitted[3]);
		return new PlaylistElement(index, id, startTime, endTime);
	}
}
