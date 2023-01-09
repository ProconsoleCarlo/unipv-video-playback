package mediaPlayer;

import java.awt.Component;
import java.io.File;

public interface IVideoPlayer {

	/**
	 * Crea il videoPlayer. E' un adapter in caso di uso di altre librerie.
	 * @param fileVideo
	 * @return false solo se non riesce a creare il videoPlayer.
	 * Se necessario viene mostrato un messaggio di errore
	 * (es. "ERRORE! File non valido.") dopo aver chiamato questo metodo.
	 */
	public abstract boolean initializePlayer(File fileVideo);
	
	/**
	 * @return I componenti del video player
	 */
	public abstract Component[] createPlayerComponents();

	/**
	 * Permette di eseguire un video.
	 * 
	 */
	public abstract void play();
	
	/**
	 * Permette di mettere in pausa l'esecuzione di un video.
	 * 
	 */
	public abstract void pause();
	
	/**
	 * Permette di fermare l'esecuzione di un video.
	 * 
	 */
	public abstract void stop();
	
	/**
	 * @return tempo corrente(media time) del video in esecuzione
	 */
	public double getCurrentTime();
	
	/**
	 * Permette di settare la durata di esecuzione di un video.
	 * @param inizio secondo di inizio
	 * @param fine secondo di fine
	 */
	public void setDuration(int inizio, int fine);
	
	/**
	 * Calcola la durata del video.
	 * @return durata del video in secondi
	 */
	public double getDurationInSeconds();
}