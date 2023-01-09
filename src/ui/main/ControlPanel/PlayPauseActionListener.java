package ui.main.ControlPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

import mediaPlayer.VideoManager;

/**
 * Azione eseguita dal tasto presente nel ControlPanel (barra sottostante al video)
 * se il video e' in esecuzione, viene messo in pausa,
 * se e' in pausa, si riprende l'esecuzione.
 * (con relativa modifica del disegno sul bottone)
 *
 */
public class PlayPauseActionListener implements ActionListener, Observer{
	private VideoManager videoManager;
	private JButton playPausaButton;
	
	public PlayPauseActionListener(VideoManager videoManager, JButton playPausaButton) {
		super();
		this.videoManager = videoManager;
		this.playPausaButton = playPausaButton;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (videoManager.isPlayerInitialized()) {
			if (playPausaButton.getText().equals("<html>&#9658</html>")) {
				videoManager.play();
			}else {
				videoManager.pause();
			}
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		if (!videoManager.isPlayerPaused()) {
			playPausaButton.setText("||");
		}else {
			playPausaButton.setText("<html>&#9658</html>");
		}
	}
}