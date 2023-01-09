package tests.elements;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import playlist.PlaylistElement;
import playlist.PlaylistFilePlsInterpreter;

/**
 * Test che carica una playlist salvata e stampa a video le stringhe corrispondenti.
 *
 */
public class TestPlaylistFileTxtInterpreter {

	public static void main(String[] args){
		ArrayList<PlaylistElement> playlist = new ArrayList<PlaylistElement>();
		Collections.addAll(playlist, new PlaylistElement(new File("./Playlist/Playlist 1.pls"), 10, 20), 
				new PlaylistElement(new File("./Playlist/Playlist 1.pls"), 30, 40));
		PlaylistFilePlsInterpreter playlistInterpreter = new PlaylistFilePlsInterpreter();
		ArrayList<String> playlistString = playlistInterpreter.createPlaylistToPrint(playlist);
		for (int i = 0; i < playlistString.size(); i++) {
			System.out.println(playlistString.get(i));
		}
	}
}
