package playlist;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;

import mediaPlayer.VideoManager;
import settings.MediaPlayerSettings;
import ui.main.MainPanel;
import utils.VideoEndTimeTask;

public class PlaylistManager {
	
	private MediaPlayerSettings settings = MediaPlayerSettings.getSettings();
	private VideoManager videoManager;
	private ArrayList<PlaylistElement> playlist = new ArrayList<PlaylistElement>();
	private int actualPlaylistVideo = 0;
	private int actualVideoPositionTime = 0;
	private double actualPlaylistTime = 0;
	private int totalPlaylistTime = 0;
	private MainPanel mainPanel;
	private VideoEndTimeTask task;
	private int[] tempiFinaliVideo;
	
	public PlaylistManager(VideoManager videoManager, MainPanel mainPanel) {
		super();
		this.videoManager = videoManager;
		this.mainPanel = mainPanel;
	}

	/**
	 * Fa partire una playlist contenuta in file.
	 * @param file
	 * @return
	 */
	public boolean startPlaylist(File file) {
		stopPlaylist();
		createPlaylistFromFile(file);
		actualPlaylistVideo = 0;
		inizializeTime();
		return !playlist.isEmpty();
	}

	/**
	 * Tratta il singolo video come una playlist, permettendo di riprodurlo.
	 * @param element il video (con tempo inizio/fine)
	 * @return
	 */
	public boolean startPlaylist(PlaylistElement element) {
		stopPlaylist();
		this.playlist.clear();
		this.playlist.add(element);
		visualizeSingleElement(element);
		actualPlaylistVideo = 0;
		inizializeTime();
		return !playlist.isEmpty();
	}
	
	/**
	 * Imposta il playlistFile da visualizzare e
	 * ottiene la playlist eseguibile (contenente solo elementi video)
	 * sotto forma di ArrayList<PlaylistElement> .
	 * 
	 * @param playlistFile il file da convertire e di cui costruire l'albero
	 */
	public void createPlaylistFromFile(File playlistToVisualize) {
		this.playlist.clear();
		this.playlist = mainPanel.createPlaylistFromFile(playlistToVisualize);
	}
	
	public void visualizeSingleElement(PlaylistElement element) {
		mainPanel.visualizeSingleElement(element);
	}
	
	public void cleanWindow() {
		mainPanel.cleanWindow();
	}
	
	/**
	 * Gestisce l'esecuzione della playlist, avviando un video alla volta dalla playlist.
	 * Per la gestione dei tempi di inizio e fine del video, vedere {@link VideoManager}.play(int startTime, int endTime).
	 * 
	 * @return true se i tempi di inizio e fine di un video sono corretti, false altrimenti.
	 * 
	 */
	public boolean playPlaylist() {
		boolean playabledTime = true;
		if (actualPlaylistVideo < playlist.size()){
			videoManager.openVideo(playlist.get(actualPlaylistVideo).getFile());
			playabledTime = videoManager.play(playlist.get(actualPlaylistVideo).getStartTime(), playlist.get(actualPlaylistVideo).getEndTime());
			inizializeTime();
			waitForEnd();
		}else{ 
			stopPlaylist();
			playPlaylist();
			videoManager.pause();
		}
		return playabledTime;
	}
	
	public void pausePlaylist() {
		videoManager.pause();
	}
	
	/**
	 * Termina l'esecuzione della playlist e reinizializza il sistema.
	 */
	public void stopPlaylist(){
		if (task != null) {
			task.cancel();
			videoManager.stop();
			actualPlaylistVideo = 0;
			inizializeTime();
		}	
	}
	
	/**
	 * Dato il tempo del video attualmente in riproduzione,
	 * calcola il tempo attuale della playlist, passandolo al MainPanel per visualizzarlo.
	 * @param currentVideoTime Il tempo del video attualmente in riproduzione
	 */
	public void setCurrentVideoTime(double currentVideoTime) {
		if(actualPlaylistVideo>0){
			actualPlaylistTime = actualVideoPositionTime + currentVideoTime - playlist.get(actualPlaylistVideo-1).getStartTime();
		}else{
			actualPlaylistTime = totalPlaylistTime;
		}
		mainPanel.setTime(actualPlaylistTime, totalPlaylistTime, tempiFinaliVideo);
	}
	
	private void waitForEnd() {
		Timer timer = new Timer();
		task = new VideoEndTimeTask(playlist.get(actualPlaylistVideo).getEndTime(), videoManager, this);
		timer.schedule(task, 0, settings.getTimerPeriod());
		actualPlaylistVideo++;
	}
	
	private void inizializeTime() {
		if (actualPlaylistVideo==0) {
			actualVideoPositionTime = 0;
			totalPlaylistTime = 0;
			calculateTotalPlaylistTime();
			mainPanel.setTime(0, totalPlaylistTime, tempiFinaliVideo);
		}else {
			actualVideoPositionTime += playlist.get(actualPlaylistVideo-1).getEndTime() - playlist.get(actualPlaylistVideo-1).getStartTime();
		}
	}
	
	private void calculateTotalPlaylistTime(){
		tempiFinaliVideo = new int[playlist.size()];
		for (int j = 0; j < playlist.size(); j++) {
			 totalPlaylistTime += (playlist.get(j).getEndTime() - playlist.get(j).getStartTime());
			 tempiFinaliVideo[j] = totalPlaylistTime;
		}
	}
}
