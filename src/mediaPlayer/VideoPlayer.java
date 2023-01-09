package mediaPlayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
import javax.swing.JPanel;

import ui.VideoPanel;

public class VideoPlayer {

	public VideoPlayer() {
		super();
	}
	private File fileVideo;
	private Player player;
	public void setFileVideo(File fileVideo) {
		this.fileVideo = fileVideo;
	}

	public JPanel createPlayer(VideoPanel videoPanel) {
		/*
		 * Se il player è già in esecuzione viene fermato, deallocato e rimosso dal pannello
		 */
		if (player != null) {
			player.stop();
			player.deallocate();
			videoPanel.removeAll();
		}else {
			videoPanel.setLayout(new BorderLayout());
			try {
				player = Manager.createRealizedPlayer(fileVideo.toURI().toURL());
				Component c = player.getVisualComponent();
				videoPanel.add(c, BorderLayout.CENTER);
				Component cpc = player.getControlPanelComponent();
				videoPanel.add(cpc, BorderLayout.SOUTH);
				videoPanel.setSize(c.getPreferredSize().width, c.getPreferredSize().height+cpc.getPreferredSize().height);
			} catch (NoPlayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CannotRealizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return videoPanel;
	}
	public void play() {
		if (player != null) {
			player.start();
		}else {
			System.out.println("Player non caricato!");
		}
	}
}
