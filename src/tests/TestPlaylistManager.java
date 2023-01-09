package tests;

import java.io.File;
import java.util.ArrayList;

import playlistReader.PlaylistElement;
import playlistReader.PlaylistFileTxtInterpreter;
import playlistReader.PlaylistManager;



public class TestPlaylistManager{

	public static void main(String[] args) {
		PlaylistFileTxtInterpreter fileTxtInterpreter = new PlaylistFileTxtInterpreter();
		PlaylistManager playlistManager = new PlaylistManager(fileTxtInterpreter);
		ArrayList<PlaylistElement> elements = playlistManager.readPlaylistContent(new File("Playlist 1.txt"));
		for (int i = 0; i < elements.size(); i++) {
			PlaylistElement element = elements.get(i);
			System.out.println(element.getIndex()+" "+element.getId()+" "+element.getStartTime()+" "+element.getEndTime());
		}
	}
}