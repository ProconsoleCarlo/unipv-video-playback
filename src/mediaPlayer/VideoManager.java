package mediaPlayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.util.Observable;

import javax.swing.JFrame;

import ui.VideoPanel;
/**
 * Classe che gestisce tutto il media player
 *
 */
public class VideoManager extends Observable{

	private IVideoPlayer videoPlayer;
	private ui.VideoPanel videoPanel = new VideoPanel();
	private JFrame mainFrame;
	private Component[] components;
	/**
	 * @param mainFrame Il frame su cui realizzare il player
	 */
	public VideoManager( JFrame mainFrame, IVideoPlayer videoPlayer) {
		super();
		this.mainFrame = mainFrame;
		this.videoPlayer = videoPlayer;
		mainFrame.getContentPane().add(videoPanel);
		this.addObserver(videoPanel);
	}
	/**
	 * Apre il file video e inizializza il player
	 * @param fileVideo
	 */
	public void openVideo(File fileVideo) {
		videoPlayer.setFileVideo(fileVideo);
		components = videoPlayer.createPlayer();
		videoPanel.setComponents(components);
	}
	/**
	 * Avvia la riproduzione del video player, settando la giusta dimensione del frame
	 */
	public void play() {
		videoPanel.buildComponents();
	//	final int width = videoPanel.getWidth();
	//	final int height = videoPanel.getHeight()+30;
	//	mainFrame.setSize(width, height);
	//	System.err.println("w "+width +"\nh "+ height);
		videoPlayer.play();
		mainFrame.pack();
		update();
	}
	
	public void update() {
		setChanged();
		notifyObservers();
	}
}
