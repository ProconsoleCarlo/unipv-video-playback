package main;

import gallery.Gallery;

import java.awt.Dimension;

import javax.swing.JFrame;

import mediaPlayer.VideoManager;
import mediaPlayer.VideoPlayer;
import playlist.PlaylistFilePlsInterpreter;
import playlist.PlaylistManager;
import settings.MediaPlayerSettings;
import ui.main.MainPanel;
import ui.main.ControlPanel.ControlsPanel;
import ui.main.MenuBar.MediaPlayerMenuBar;
import ui.main.PlaylistPanel.PlaylistJTree;
import ui.main.PlaylistPanel.PlaylistPanel;

/**
 * Lancia il programma, permettendo di vedere un singolo video, una playlist predefinita o di crearne una nuova.
 *
 */
public class Main{

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("MediaPlayer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(800, 400));
		
		MediaPlayerSettings.getSettings().initializeSettings();
		Gallery.getGallery().initialize();
		
		PlaylistFilePlsInterpreter playlistInterpreter = new PlaylistFilePlsInterpreter();
		VideoManager videoManager = new VideoManager(new VideoPlayer());
		
		PlaylistPanel playlistPanel = new PlaylistPanel(new PlaylistJTree(playlistInterpreter));
		ControlsPanel controlsPanel = new ControlsPanel(videoManager);
		MainPanel mainPanel = new MainPanel(frame, playlistPanel, controlsPanel);
		mainPanel.setBackground(MediaPlayerSettings.getSettings().getColors().colorBackground);
		
		videoManager.setMainPanel(mainPanel);
		PlaylistManager playlistManager = new PlaylistManager(videoManager, mainPanel);
		
		MediaPlayerMenuBar mediaPlayerMenuBar = new MediaPlayerMenuBar(videoManager, playlistManager, playlistInterpreter);
		mediaPlayerMenuBar.setFrame(frame);
		frame.setMenuBar(mediaPlayerMenuBar.createMenuBar());
		
		frame.getContentPane().add(mainPanel);
		frame.setMinimumSize(new Dimension(400, 200));
		mainPanel.createMainPanel();
		frame.setResizable(false);
		frame.setVisible(true);
	}
}