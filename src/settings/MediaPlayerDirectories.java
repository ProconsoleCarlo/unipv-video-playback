package settings;

import java.io.File;

/**
 * La classe contenente le directory e i file usati dal programma
 */
public class MediaPlayerDirectories {

	public final File settingsFile = new File("./Playlist/Mappa/Settings.dat");
	public final File localVideoDirectory = new File("./VideoTest");
	public final File localPlaylistDirectory = new File("./Playlist");
	public final File galleryFile = new File("./Playlist/Mappa/Mappa.txt");
	public final File thumbnailsFolder = new File("./VideoTest/Thumbnails");
}
