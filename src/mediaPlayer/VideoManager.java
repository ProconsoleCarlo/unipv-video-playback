package mediaPlayer;

import java.io.File;
import java.util.Observable;

import javax.swing.JOptionPane;

import ui.main.MainPanel;

/**
 * Classe che gestisce il media player.
 *
 */
public class VideoManager extends Observable{

	private IVideoPlayer videoPlayer;
	private MainPanel mainPanel;
	private boolean playerInitialized = false;
	private boolean playerPaused = false;
	
	/**
	 * @param videoPlayer Il videoPlayer da utilizzare
	 */
	public VideoManager(IVideoPlayer videoPlayer) {
		this.videoPlayer = videoPlayer;	
	}
	
	public IVideoPlayer getVideoPlayer() {
		return videoPlayer;
	}
	
	public void setMainPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		this.addObserver(mainPanel);
	}
	
	public boolean isPlayerInitialized() {
		return playerInitialized;
	}
	public boolean isPlayerPaused() {
		return playerPaused;
	}
	
	/**
	 * Apre il file video e inizializza il player (se possibile).
	 * Se non e' possibile aprire/riprodurre il file video, lo si comunica all'utente.
	 * 
	 * @param fileVideo Il video da aprire
	 */
	public void openVideo(File fileVideo) {
		videoPlayer.stop();
		if(playerInitialized = videoPlayer.initializePlayer(fileVideo)){
			mainPanel.setComponents(videoPlayer.createPlayerComponents());
			mainPanel.createMainPanel();
		} else {
			JOptionPane.showMessageDialog(null, "ERRORE! File non valido.","Errore", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Permette di mettere in esecuzione il video player.
	 * 
	 */
	public void play() {
		playerPaused = false;
		videoPlayer.play();
		update();
	}
	
	/**
	 * Avvia la riproduzione del video 
	 * specificando il tempo di inizio e fine riproduzione.
	 * Se i tempi non sono validi, avvisa l'utente, poi riproduce l'intero video.
	 * 
	 * @param startTime Il tempo di inizio
	 * @param endTime Il tempo di fine
	 * @return true se i tempi sono validi, false altrimenti
	 */
	public boolean play(int startTime, int endTime) {
		if(startTime >= 0 && startTime < endTime && endTime <= (int)videoPlayer.getDurationInSeconds()){
			videoPlayer.setDuration(startTime, endTime);
			play();
			return true;
		} else {
			JOptionPane.showMessageDialog(null, "Tempi non validi.\nVerra' riprodotto l'intero video."
												+ "\nI tempi visualizzati potrebbero essere sbagliati"
												+ " se stai riproducendo una playlist.","Errore", JOptionPane.ERROR_MESSAGE);
			videoPlayer.setDuration(0, (int)videoPlayer.getDurationInSeconds());
			play();
			return false;
		}
	}
	
	/**
	 * Permette di mettere in pausa la riproduzione
	 * quindi a differenza di VideoPlayer.stop(),
	 * non dealloca il videoplayer.
	 * 
	 */
	public void pause(){
		playerPaused = true;
		videoPlayer.pause();
		update();
	}
	
	/**
	 * Permette di stoppare la riproduzione, deallocare il player e reinizializzare il mediaPlayer
	 */
	public void stop() {
		playerPaused = true;
		playerInitialized = false;
		videoPlayer.stop();
		mainPanel.setComponents(null);
		mainPanel.createMainPanel();
		update();
	}
	
	public void update() {
		setChanged();
		notifyObservers();
	}
}