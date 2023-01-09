package utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class VideosMap {

	//Singleton
	private static VideosMap videosMap = new VideosMap();
	private VideosMap() {
		// TODO Auto-generated constructor stub
	}
	public static VideosMap getVideosMap() {
		return videosMap;
	}
	
	//Classe
	private Map<Integer, File> videos = new HashMap<Integer, File>();
	public Map<Integer, File> getVideos() {
		return videos;
	}
	/**
	 * Aggiunge una corrispondenza l'id e il file video
	 * @param id L'id del video
	 * @param file Il file video
	 */
	public void addVideo(Integer id, File file) {
		videos.put(id, file);
	}
	
}
