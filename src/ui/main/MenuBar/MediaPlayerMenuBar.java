
package ui.main.MenuBar;

import gallery.Gallery;

import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import mediaPlayer.VideoManager;
import playlist.IPlaylistFileInterpreter;
import playlist.PlaylistManager;
import playlist.PlaylistReaderWriter;
import ui.CreditsPanel;
import ui.SettingsPanel;
import ui.creationPlaylist.PlaylistCreationFrame;

/**
 * La barra del mainpanel, dalla quale si puo' scegliere l'azione da eseguire.
 * 
 */
public class MediaPlayerMenuBar {

	private VideoManager videoManager;
	private PlaylistManager playlistManager;
	private IPlaylistFileInterpreter interpreter;
	private JFrame frame;

	public MediaPlayerMenuBar(VideoManager videoManager, PlaylistManager playlistManager, IPlaylistFileInterpreter interpreter) {
		super();
		this.videoManager = videoManager;
		this.playlistManager = playlistManager;
		this.interpreter = interpreter;
	}
	
	public void setFrame(JFrame frame) {
		this.frame = frame;
	}
	
	/**
	 * Crea la barra dei menu.
	 * @param mainFrame Il frame su cui creare la barra dei menu
	 * @return La barra dei menu
	 */
	public MenuBar createMenuBar() {
		MenuBar menuBar = new MenuBar();
	
			Menu menuFile = new Menu("File");
				MenuItem openFile = new MenuItem("Apri video");
				openFile.addActionListener(new OpenVideoActionListener(videoManager, playlistManager));
			menuFile.add(openFile);
				MenuItem openPlaylist = new MenuItem("Apri playlist");
				openPlaylist.addActionListener(new OpenPlaylistActionListener(playlistManager));
			menuFile.add(openPlaylist);
			MenuItem createPlaylist = new MenuItem("Crea playlist");
				createPlaylist.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						playlistManager.stopPlaylist();
						playlistManager.cleanWindow();
						new PlaylistCreationFrame(videoManager, new PlaylistReaderWriter(interpreter), playlistManager);
					}
				});
			menuFile.add(createPlaylist);
		menuBar.add(menuFile);
			Menu menuImpostazioni = new Menu("Impostazioni");
				MenuItem clearGallery = new MenuItem("Pulisci galleria");
				clearGallery.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						Gallery.getGallery().cleanGallery();
					}
				});
				menuImpostazioni.add(clearGallery);
				MenuItem preferences = new MenuItem("Impostazioni");
				preferences.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						new SettingsPanel(frame);
					}
				});
				menuImpostazioni.add(preferences);
		menuBar.add(menuImpostazioni);
			Menu menuInfo = new Menu("?");
				MenuItem credits = new MenuItem("Informazioni");
				credits.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						new CreditsPanel();
					}
				});
			menuInfo.add(credits);
		menuBar.add(menuInfo);
		return menuBar;
	}
}
