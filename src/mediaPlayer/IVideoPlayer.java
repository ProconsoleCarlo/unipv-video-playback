package mediaPlayer;

import java.awt.Component;
import java.io.File;

public interface IVideoPlayer {

	public abstract void setFileVideo(File fileVideo);

	/**
	 * Crea il video player
	 * @return I componenti del video player
	 */
	public abstract Component[] createPlayer();

	/**
	 * Avvia la riproduzione del video player
	 */
	public abstract void play();

}