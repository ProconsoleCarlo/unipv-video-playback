package gallery;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import settings.MediaPlayerSettings;
import utils.FileLoaderWriter;

/**
 * Genera la mappa dei video, creando una corrispondenza 
 * tra percorso relativo dei video e id contenuto nelle playlist.
 *
 */
public class Gallery {

	private static Gallery gallery = new Gallery();
	private Gallery() {
		
	}
	public static Gallery getGallery() {
		return gallery;
	}

	private FileLoaderWriter fileLoaderWriter = new FileLoaderWriter();
	private MediaPlayerSettings settings = MediaPlayerSettings.getSettings();
	private File galleryFile = settings.getDirectories().galleryFile;
	private Map<Integer, File> galleryFiles = new HashMap<Integer, File>();
	private Map<String, Icon> videoThumbnailsMap = new HashMap<String, Icon>();
	boolean fileNotFound = false;
	public Map<Integer, File> getGalleryFiles() {
		return galleryFiles;
	}
	public Map<String, Icon> getVideoThumbnailsMap() {
		return videoThumbnailsMap;
	}
	/**
	 * Aggiunge una corrispondenza tra id e file video, se non e' gia presente nella mappa.
	 * @param id L'id del video
	 * @param file Il file video
	 */
	public boolean addGalleryElement(Integer id, File file) {
		if (!galleryFiles.containsKey(id)) {
			galleryFiles.put(id, file);
			fileLoaderWriter.addLine(id+" "+file, galleryFile);
			return true;
		}
		return false;
	}
	
	/**
	 * Metodo che inizializza la mappa dei video caricando dal file mappa.txt la mappa gia' esistente
	 * Quando la carica, verifica che i file presenti sulla mappa esistano e
	 * elimina quelli non piu' presenti dal file mappa.txt
	 */
	public void initialize() {
		fileNotFound = false;
		galleryFiles.clear();
		if (!galleryFile.exists()) {
			try {
				galleryFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			ArrayList<String> stringGallery = fileLoaderWriter.load(galleryFile);
			for (int i = 0; i < stringGallery.size(); i++) {
				String[] stringSplitted = stringGallery.get(i).split(" ", 2);
				File file = new File(stringSplitted[1]);
				if (!file.exists()) {
					fileNotFound = true;
				}
				galleryFiles.put(Integer.valueOf(stringSplitted[0]), new File(stringSplitted[1]));
			}
			loadGalleryFiles();
			loadThumbnails();
		}	
	}
	/**
	 * Utility per pulire la mappa dai file non trovati su disco.
	 */
	public void cleanGallery() {
		if (fileNotFound) {
			int selectedOption = JOptionPane.showConfirmDialog(null, "Nella galleria sono presenti dei file che non trovo su disco,\n" +
					"vuoi eliminarli?", "Attenzione!", JOptionPane.YES_NO_OPTION);
			if (selectedOption == JOptionPane.YES_OPTION) {
				updateGalleryFile(true);
			}
		}else {
			JOptionPane.showMessageDialog(null, "La galleria non contiene file non presenti su disco! :)");
		}
	}
	/**
	 * Metodo per rilocare un file:
	 * sostituisce il percorso del vecchio file con quello del nuovo nella gallery lasciando inalterato l'id
	 * (per mantenere il funzionamento di altre playlist che usavano il file rilocato)
	 * quindi aggiunge la corrispondenza nuovo percorso con il nuovo hashcode.
	 * @param oldFile Il vecchio file
	 * @param newFile Il nuovo file
	 */
	public void relocate(File oldFile, File newFile) {
		galleryFiles.put(oldFile.hashCode(), newFile);
		this.addGalleryElement(newFile.hashCode(), newFile);
		updateGalleryFile(false);
	}
	private void updateGalleryFile(boolean destructive) {
		ArrayList<String> stringGalleryUpdated = new ArrayList<String>();
		for (Map.Entry<Integer, File> entry  : galleryFiles.entrySet()) {
			if (destructive) {
				if (entry.getValue().exists()) {
					stringGalleryUpdated.add(entry.getKey()+" "+entry.getValue());
				}
			}else {
				stringGalleryUpdated.add(entry.getKey()+" "+entry.getValue());
			}
		}
		fileLoaderWriter.write(stringGalleryUpdated, galleryFile);
		initialize();
	}
	private void loadGalleryFiles() {
		File[] localVideoFiles = settings.getDirectories().localVideoDirectory.listFiles();
		for (int i = 0; i < localVideoFiles.length; i++) {
			//Manca il controllo se il file è un video, che non so come fare...
			if (localVideoFiles[i].isFile()) {
				addGalleryElement(localVideoFiles[i].hashCode(), localVideoFiles[i]);
			}
		}
		File[] localPlaylistFiles = settings.getDirectories().localPlaylistDirectory.listFiles();
		for (int i = 0; i < localPlaylistFiles.length; i++) {
			if (localPlaylistFiles[i].isFile() && localPlaylistFiles[i].getName().endsWith(settings.getPlaylistFileExtension())) {
				addGalleryElement(localPlaylistFiles[i].hashCode(), localPlaylistFiles[i]);
			}
		}
		if (!settings.getVideoDirectories().isEmpty()) {
			ArrayList<File> userDirectories = settings.getVideoDirectories();
			for (int i = 0; i < userDirectories.size(); i++) {
				File[] userVideoFiles = userDirectories.get(i).listFiles();
				for (int j = 0; j < userVideoFiles.length; j++) {
					if (userVideoFiles[j].isFile()) {
						addGalleryElement(userVideoFiles[j].hashCode(), userVideoFiles[j]);
					}
				}				
			}
		}
	}
	private void loadThumbnails() {
		File[] thumbnails = settings.getDirectories().thumbnailsFolder.listFiles();
		for (int i = 0; i < thumbnails.length; i++) {
			String fileExtension = thumbnails[i].getName().split("\\.")[1];
			if (settings.getSupportedThumbnailsExtension().contains(fileExtension)) {
				videoThumbnailsMap.put(thumbnails[i].getName().split("\\.")[0], new ImageIcon(thumbnails[i].toString()));
			}
		}
	}
}
