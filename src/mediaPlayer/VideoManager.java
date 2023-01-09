package mediaPlayer;

import java.io.File;
import java.util.Observable;

import javax.swing.JFrame;

import ui.VideoPanel;

public class VideoManager extends Observable{

	private VideoPlayer videoPlayer = new VideoPlayer();
	private ui.VideoPanel videoPanel = new VideoPanel();
	private JFrame mainFrame;
	
	public VideoManager( JFrame mainFrame) {
		super();
		this.mainFrame = mainFrame;
		mainFrame.getContentPane().add(videoPanel);
		addObserver(videoPanel);
	}

	public void openVideo(File fileVideo) {
		videoPlayer.setFileVideo(fileVideo);
		videoPlayer.createPlayer(videoPanel);
	}
	/*
	 *	Setta la risoluzione del mainFrame a quella del player così il video non viene distorto nelle due dimensioni 
	 */
	public void play() {
//		System.out.println("Dimensione player: "+videoPanel.getWidth()+"x"+videoPanel.getHeight());
		mainFrame.setSize(videoPanel.getWidth(), videoPanel.getHeight()+30);
		videoPlayer.play();
		update();
	}
	
	public void update() {
		setChanged();
		notifyObservers();
	}
}
