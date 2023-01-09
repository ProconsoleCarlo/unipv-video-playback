package mediaPlayer;

import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.CannotRealizeException;
import javax.media.Manager;
import javax.media.NoPlayerException;
import javax.media.Player;
/**
 * Un video player basato su JMF
 *
 */
public class VideoPlayer implements IVideoPlayer {

	public VideoPlayer() {
		super();
	}
	private File fileVideo;
	private Player player;
	/* (non-Javadoc)
	 * @see mediaPlayer.IVideoPlayer#setFileVideo(java.io.File)
	 */
	@Override
	public void setFileVideo(File fileVideo) {
		this.fileVideo = fileVideo;
	}
	/* (non-Javadoc)
	 * @see mediaPlayer.IVideoPlayer#createPlayer()
	 */
	@Override
	public Component[] createPlayer() {
		/*
		 * Se il player è già in esecuzione viene fermato, deallocato e rimosso dal pannello
		 */
		
		if (player != null) {
			player.stop();
			player.deallocate();
		}
		Component[] components = new Component[2];
		try {
			player = Manager.createRealizedPlayer(fileVideo.toURI().toURL());
			components[0] = player.getVisualComponent();
			components[1] = player.getControlPanelComponent();
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
		
		return components;
	}
	/* (non-Javadoc)
	 * @see mediaPlayer.IVideoPlayer#play()
	 */
	@Override
	public void play() {
		if (player != null) {
			player.start();
		}else {
			System.out.println("Player non caricato!");
		}
	}
}
