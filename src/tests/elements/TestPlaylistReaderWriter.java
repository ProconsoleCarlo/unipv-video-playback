package tests.elements;

import gallery.Gallery;

import java.io.File;
import java.util.ArrayList;

import playlist.PlaylistElement;
import playlist.PlaylistFilePlsInterpreter;
import playlist.PlaylistReaderWriter;

/**
 * Test che stampa su console il contenuto di una playlist.
 *
 */
public class TestPlaylistReaderWriter{

	public static void main(String[] args) {
		Gallery.getGallery().initialize();
		PlaylistFilePlsInterpreter playlistInterpreter = new PlaylistFilePlsInterpreter();
		PlaylistReaderWriter playlistReaderWriter = new PlaylistReaderWriter(playlistInterpreter);

		ArrayList<PlaylistElement> elements = playlistReaderWriter.readPlaylistContent(new File("./Playlist/Playlist1.pls"));
		for (int i = 0; i < elements.size(); i++) {
			PlaylistElement element = elements.get(i);
			System.out.println(element.getId()+" "+element.getStartTime()+" "+element.getEndTime());
		}
	}
}