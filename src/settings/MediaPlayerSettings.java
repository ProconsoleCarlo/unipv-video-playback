package settings;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import utils.FileLoaderWriter;

/**
 * I settings standard, riguardanti JList e percorso della Mappa video.
 * 
 */
public class MediaPlayerSettings {

	private static MediaPlayerSettings settings = new MediaPlayerSettings();

	private MediaPlayerSettings() {
		super();
	}
	public static MediaPlayerSettings getSettings() {
		return settings;
	}

	private static MediaPlayerColors colors = new MediaPlayerColors();
	private static MediaPlayerDirectories directories = new MediaPlayerDirectories();
	private static FileLoaderWriter fileLoaderWriter = new FileLoaderWriter();
	private static String userDirectoryText = "UserDirectory:";
	private static ArrayList<File> videoDirectories = new ArrayList<File>();
	private static ArrayList<String> supportedThumbnailsExtension = new ArrayList<String>();
	
	private static String playlistFileExtension = ".pls";
	private static int playlistPanelWidth = 200;
	private static long timerPeriod = 200; //Ogni quanto viene aggiornato il tempo durante la riproduzione
	
	private static String playlistType = "PLAYLIST";
	private static String videoType = "VIDEO";

	/*------------------------------------------------------------------*/
	
	public MediaPlayerColors getColors() {
		return colors;
	}
	public MediaPlayerDirectories getDirectories() {
		return directories;
	}
	
	public int getPlaylistpanelwidth() {
		return playlistPanelWidth;
	}
	public long getTimerPeriod() {
		return timerPeriod;
	}
	
	public String getPlaylistType() {
		return playlistType;
	}
	public String getVideoType() {
		return videoType;
	}
	
	public ArrayList<File> getVideoDirectories() {
		return videoDirectories;
	}
	public String getPlaylistFileExtension() {
		return playlistFileExtension;
	}
	public ArrayList<String> getSupportedThumbnailsExtension() {
		return supportedThumbnailsExtension;
	}

	public void initializeSettings() {
		Collections.addAll(supportedThumbnailsExtension, "jpg", "JPG", "jpeg", "JPEG", "png");
		loadSettings();
	}
	public void addVideoDirectory(String directoryPath) {
		if (!videoDirectories.contains(new File(directoryPath))) {
			videoDirectories.add(new File(directoryPath));
		}
		updateSettings();
	}
	private void loadSettings() {
		ArrayList<String> settingsReaded = fileLoaderWriter.load(directories.settingsFile);
		if (!settingsReaded.isEmpty()) {
			String line = settingsReaded.get(0);
			int lineIndex = 1;
			if (line.equalsIgnoreCase(userDirectoryText)){
				line = settingsReaded.get(lineIndex);
				while (!line.equals(" ")) {
					videoDirectories.add(new File(line));
					lineIndex++;
					line = settingsReaded.get(lineIndex);
				}
			}
		}
	}
	private void updateSettings() {
		ArrayList<String> settingsToWrite = new ArrayList<String>();
		settingsToWrite.add(userDirectoryText);
		for (int i = 0; i < videoDirectories.size(); i++) {
			settingsToWrite.add(videoDirectories.get(i).getAbsolutePath());
		}
		settingsToWrite.add(" ");
		fileLoaderWriter.write(settingsToWrite, directories.settingsFile);
		loadSettings();
	}
}
