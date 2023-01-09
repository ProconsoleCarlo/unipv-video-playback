package tests;

import gallery.Gallery;

import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mediaPlayer.VideoManager;
import mediaPlayer.VideoPlayer;
import playlist.PlaylistFilePlsInterpreter;
import playlist.PlaylistManager;
import settings.MediaPlayerSettings;
import ui.main.MainPanel;
import ui.main.ControlPanel.ControlsPanel;
import ui.main.PlaylistPanel.PlaylistJTree;
import ui.main.PlaylistPanel.PlaylistPanel;

/**
 * Test per UI della singola DEMO1 (riprodurre una playlist)
 * 
 */
public class TestRiproduciPlaylist{
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("DEMO1: riproduttore di playlist");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(860, 400));
		
		MediaPlayerSettings.getSettings().initializeSettings();
		Gallery.getGallery().initialize();
		
		PlaylistFilePlsInterpreter playlistInterpreter = new PlaylistFilePlsInterpreter();
		
		VideoManager videoManager = new VideoManager(new VideoPlayer());
		
		PlaylistJTree playlistTree = new PlaylistJTree(playlistInterpreter);
		PlaylistPanel playlistPanel = new PlaylistPanel(playlistTree);
		ControlsPanel controlsPanel = new ControlsPanel(videoManager);
		MainPanel mainPanel = new MainPanel(frame, playlistPanel, controlsPanel);
		final PlaylistManager playlistManager = new PlaylistManager(videoManager, mainPanel);
		
		videoManager.setMainPanel(mainPanel);
		
		MenuBar menuBar = new MenuBar();
			Menu menu = new Menu("Riproduci...");
				MenuItem mi1 = new MenuItem("Playlist1");
					mi1.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							File file = new File("./Playlist/Playlist1.pls");
							if (file.exists() && playlistManager.startPlaylist(file)) {
								playlistManager.playPlaylist();
							} else {
								JOptionPane.showMessageDialog(null,
										"ERRORE! File non valido.");
							}
						}
					});
				menu.add(mi1);
			menuBar.add(menu);
		frame.setMenuBar(menuBar);
		
		frame.getContentPane().add(mainPanel);
		frame.setMinimumSize(new Dimension(400, 200));
		mainPanel.createMainPanel();
		frame.setResizable(true);
		frame.setVisible(true);
	}
}