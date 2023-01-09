package mediaPlayer;

import java.awt.Component;
import java.io.File;

import javax.media.Manager;
import javax.media.Player;
import javax.media.Time;
import javax.swing.JOptionPane;

/**
 * Componente che riproduce il video.
 *
 */
public class VideoPlayer implements IVideoPlayer {

	public VideoPlayer() {
		super();
	}

	private Player initializedPlayer;

	@Override
	public boolean initializePlayer(File fileVideo) {
		try {
			initializedPlayer = Manager.createRealizedPlayer(fileVideo.toURI().toURL());
			return true;
		} catch (Exception e) {
			initializedPlayer = null;
			return false;
		}
	}

	/**
	 * Crea il componente visuale del player.
	 * @return i componenti del player:
	 * 				components[0] = VisualComponent.
	 */
	@Override
	public Component[] createPlayerComponents() {
		Component[] components = new Component[1];
		components[0] = initializedPlayer.getVisualComponent();
		return components;
	}

	@Override
	public void setDuration(int secin, int secout) {
		initializedPlayer.stop();
		initializedPlayer.setMediaTime(new Time((double)secin));
		initializedPlayer.setStopTime(new Time((double)secout));
	}
	
	@Override
	public double getDurationInSeconds() {
		if (initializedPlayer != null) {
			return initializedPlayer.getDuration().getSeconds();
		}
		return 0;
	}

	@Override
	public double getCurrentTime() {
		return initializedPlayer.getMediaTime().getSeconds();
	}
	
	@Override
	public void play() {
		if (initializedPlayer != null) {
			initializedPlayer.start();
		} else {
			JOptionPane.showMessageDialog(null, "ERRORE! Player non caricato!","Errore", JOptionPane.ERROR_MESSAGE);
			initializedPlayer.stop();
		}
	}
	
	@Override
	public void pause() {
		if (initializedPlayer != null) {
			initializedPlayer.stop();
		}
	}
	
	@Override
	public void stop() {
		if (initializedPlayer != null) {
			initializedPlayer.stop();
			initializedPlayer.deallocate();
			initializedPlayer.close();
			initializedPlayer = null;
		}	
	}
}