package utils;

import java.util.ArrayList;
/**
 * I dati della playlist
 * @author Carlo
 *
 */
public class PlaylistData {

	//Singleton
	private static PlaylistData playlistData = new PlaylistData();
	private PlaylistData() {
		// TODO Auto-generated constructor stub
	}
	public static PlaylistData getPlaylistData() {
		return playlistData;
	}
	
	//Classe
	private ArrayList<String> playlistVideosData = new ArrayList<String>();
	/**
	 * Aggiunge un elemento della playlist in forma di stringa in un ArrayList<String>
	 * @param videoData L'elemento della playlist
	 */
	public void addVideoData(String videoData) {
		playlistVideosData.add(videoData);
	}
	public ArrayList<String> getPlaylistVideosData() {
		return playlistVideosData;
	}
	
}
