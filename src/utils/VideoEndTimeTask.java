package utils;

import java.util.TimerTask;

import mediaPlayer.IVideoPlayer;
import mediaPlayer.VideoManager;
import playlist.PlaylistManager;

/**
 * Questo e' il task che il timer in playlistManager deve eseguire.
 */
public class VideoEndTimeTask extends TimerTask{

	private int endTime;
	private double currentVideoRemainingTime;
	private VideoManager videoManager;
	private IVideoPlayer videoPlayer;
	private PlaylistManager playlistManager;
	
	/**
	 *
	 * @param endTime Il tempo di fine del video attuale
	 * @param videoManager Il gestore dei video
	 * @param playlistManager Il gestore della playlist
	 */
	public VideoEndTimeTask(int endTime, VideoManager videoManager, PlaylistManager playlistManager) {
		this.endTime = endTime;
		this.videoManager = videoManager;
		this.videoPlayer = videoManager.getVideoPlayer();
		this.playlistManager = playlistManager;
	}

	/**
	 * Se il player e' in esecuzione(non e' in pausa),
	 * calcola il tempo rimanente del video e
	 * passa il tempo attuale del video al playlistManager.
	 * Se il tempo rimanenente del video non cambia dal precedente campionamento,
	 * il video e' finito e passa al successivo della playlist.
	 */
	@Override
	public void run() {
		if (!videoManager.isPlayerPaused() && videoPlayer.getCurrentTime()!=0) {
			playlistManager.setCurrentVideoTime(videoPlayer.getCurrentTime());
			if (currentVideoRemainingTime == (double)endTime - videoPlayer.getCurrentTime() || currentVideoRemainingTime<0) {
				currentVideoRemainingTime = 0;
				playlistManager.setCurrentVideoTime(endTime);
				playlistManager.playPlaylist();
				cancel();
			}else {
				currentVideoRemainingTime = (double)endTime - videoPlayer.getCurrentTime();
			}
		}
	}
}
