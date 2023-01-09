package tests.elements;

import gallery.Gallery;

import java.io.File;

import settings.MediaPlayerSettings;
import utils.CheckVideoLocation;


/**
 * controlla tutte le playlist nella cartella Playlist, e controlla che i video al loro interno
 * siano presenti sulla mappa.
 */
public class TestCheckVideoLocation {

	public static void main(String[] args) {
		Gallery.getGallery().initialize();
		CheckVideoLocation checkVideoLocation = new CheckVideoLocation();
		File playlistDirectory = new File("./Playlist");
		File[] playlists = playlistDirectory.listFiles();
		for (int i = 0; i < playlists.length; i++) {
			if (playlists[i].getName().endsWith(MediaPlayerSettings.getSettings().getPlaylistFileExtension())) {
				checkVideoLocation.isFileNotFound(playlists[i]);
			}
		}
		
	}
}
