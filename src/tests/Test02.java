package tests;

import javax.swing.JFrame;

import mediaPlayer.VideoManager;
import mediaPlayer.VideoPlayer;
import playlistReader.PlaylistFileTxtInterpreter;
import playlistReader.PlaylistManager;
import ui.MediaPlayerMenuBar;


public class Test02{

	public static void main(String[] args) {
		final JFrame mainFrame = new JFrame();
		PlaylistFileTxtInterpreter stringInterpreter = new PlaylistFileTxtInterpreter();
		final VideoManager videoManager = new VideoManager(mainFrame, new VideoPlayer());
		PlaylistManager playlistManager = new PlaylistManager(stringInterpreter);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(640, 480);
		mainFrame.setResizable(false);
		// TODO Verra' settata a true quando sara' implementato un metodo 
		// per il resize del video a seconda della dimensione della finestra
		// SENZA deformare l'immagine del video.
		MediaPlayerMenuBar mediaPlayerMenuBar = new MediaPlayerMenuBar(videoManager, playlistManager);
		mainFrame.setJMenuBar(mediaPlayerMenuBar.createMenuBar(mainFrame));
		mainFrame.setVisible(true);
		}
}