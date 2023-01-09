package playlist;

import gallery.Gallery;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import utils.CheckVideoLocation;
import utils.FileLoaderWriter;

/**
 * L'interprete di playlist contenute in un file pls e strutturate in tale modo:
 * IdVideo tempoDiInizio tempoDiFine
 */
public class PlaylistFilePlsInterpreter implements IPlaylistFileInterpreter {

	private Gallery gallery = Gallery.getGallery();
	private CheckVideoLocation checkElementLocation = new CheckVideoLocation();
	private FileLoaderWriter fileLoaderWriter = new FileLoaderWriter();

	@Override
	public ArrayList<PlaylistElement> createPlaylist(
			ArrayList<String> playlistPrinted, String playlistName) {
		ArrayList<PlaylistElement> playlist = new ArrayList<PlaylistElement>();
		boolean isModified = false;
		File file = null;
		for (int i = 0; i < playlistPrinted.size() - 1; i++) {
			String[] stringSplitted = playlistPrinted.get(i).split(" ");
			file = gallery.getGalleryFiles().get(
					Integer.valueOf(stringSplitted[0]));
			int startTime = Integer.valueOf(stringSplitted[1]);
			int endTime = Integer.valueOf(stringSplitted[2]);
			if (!checkElementLocation.isFileNotFound(file)) {
				File tempFile = checkElementLocation.getElementFile();
				if (tempFile != null) {
					file = tempFile;
					isModified = true;
				}
				playlist.add(new PlaylistElement(file, startTime, endTime));
			} else {
				isModified = true;
			}
		}
		if (isModified) {
			JOptionPane.showMessageDialog(null,
					"Non ho trovato alcuni file della playlist:\n"
							+ " ho modificato " + playlistName
							+ " e creato una copia di backup");
			new File("./Playlist/" + playlistName).renameTo(new File(
					"./Playlist/" + playlistName + ".backup"));
			fileLoaderWriter.write(createPlaylistToPrint(playlist), new File(
					"./Playlist/" + playlistName));
		}
		return playlist;
	}

	@Override
	public ArrayList<String> createPlaylistToPrint(
			ArrayList<PlaylistElement> playlist) {
		ArrayList<String> playlistToPrint = new ArrayList<String>();
		playlistToPrint.clear();
		int duration = 0;
		;
		for (int i = 0; i < playlist.size(); i++) {
			PlaylistElement element = playlist.get(i);
			duration += element.getEndTime() - element.getStartTime();
			String stringElement = element.getId() + " "
					+ element.getStartTime() + " " + element.getEndTime();
			playlistToPrint.add(stringElement);
		}
		String endPLaylist = "Durata: 0 " + duration;
		playlistToPrint.add(endPLaylist);
		return playlistToPrint;
	}
}
