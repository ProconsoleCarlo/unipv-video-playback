package ui.creationPlaylist;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import settings.MediaPlayerSettings;
import utils.LimConstraints;
import utils.OnlyNumberDocument;

public class PlaylistParametersPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	private Color textBgColor = MediaPlayerSettings.getSettings().getColors().colorBackground.brighter();

	private GridBagLayout layout = new GridBagLayout();
	private LimConstraints limit = new LimConstraints();
	
	private MediaPlayerSettings settings = MediaPlayerSettings.getSettings();
	private JLabel startTimeLabel = new JLabel("Inizio:");
	private JTextField startTimeField = new JTextField("");
	private JLabel endTimeLabel = new JLabel("Fine:");
	private JTextField endTimeField = new JTextField("");
	private JLabel timeLabelDescription = new JLabel("Durata:");
	private JLabel timeLabel = new JLabel();
	
	public PlaylistParametersPanel() {
		super();
		this.setLayout(layout);
		realize();
		setEnabled(false);
		setVideoDuration(0, "NULL");
	}
	/**
	 * 
	 * @param videoDuration
	 * @param type tipo di file playlist o video  
	 */
	public void setVideoDuration(int videoDuration, String type) {
		if (type.equals(settings.getPlaylistType())) { //playlist
			enableParameters(false);
			startTimeField.setText("0");
			endTimeField.setText(String.valueOf(videoDuration));
		}else if (type.equals(settings.getVideoType())) {
			enableParameters(true);
			startTimeField.setText("");
			endTimeField.setText("");
		}else if (type.equals("OTHER")||type.equals("NULL")) {
			enableParameters(false);
			timeLabelDescription.setVisible(false);
			timeLabel.setVisible(false);
			startTimeField.setText("0");
			endTimeField.setText("0");
		}
		timeLabel.setText(String.valueOf(videoDuration));
	}
	
	public int[] getStartAndEndTime(){
		return timeValuesChecked();
	}

	private int[] timeValuesChecked() {
		int[] startAndEndTime = new int[2];
		if(timeLabel.isVisible()){
			if (startTimeField.getText().isEmpty()) {
				startTimeField.setText(JOptionPane.showInputDialog(null, "Tempo inizio:"));
				if (startTimeField.getText().isEmpty()) {
					startTimeField.setText("0");
				}
			}
			if (endTimeField.getText().isEmpty()) {
				endTimeField.setText(JOptionPane.showInputDialog(null, "Tempo fine:"));
				if (endTimeField.getText().isEmpty()) {
					endTimeField.setText(timeLabel.getText());
				}
			}
			int videoDuration = Integer.valueOf(timeLabel.getText());
			int startTime = Integer.valueOf(startTimeField.getText());
			int endTime = Integer.valueOf(endTimeField.getText());
			if(startTime >= endTime){	
				JOptionPane.showMessageDialog(null, "ERRORE! Il tempo d'inizio non puo' superare ne' eguagliare il tempo di fine!",
						"Errore", JOptionPane.ERROR_MESSAGE);
				return null;
			}else{
				if(endTime > videoDuration){	
					JOptionPane.showMessageDialog(null, "ERRORE! Il tempo di fine non puo' superare la durata del video..",
							"Errore", JOptionPane.ERROR_MESSAGE);
						return null;
				}else {
					startAndEndTime[0] = Integer.valueOf(startTimeField.getText());
					startAndEndTime[1] = Integer.valueOf(endTimeField.getText());
					return startAndEndTime;
				}
			}
		}else{
			JOptionPane.showMessageDialog(null, "ERRORE! Seleziona un file valido", "Errore", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
	
	private void realize() {
		
		startTimeLabel.setPreferredSize(new Dimension(60, 20));
		limit.setLimAndConstraints(startTimeLabel, 0, 0, 1, 1, layout);
		this.add(startTimeLabel);
	
		startTimeField.setDocument(new OnlyNumberDocument());
		startTimeField.setBackground(textBgColor);
		startTimeField.setPreferredSize(new Dimension(60, 20));
		limit.setLimAndConstraints(startTimeField, 1, 0, 1, 1, layout);
		this.add(startTimeField);
		
		endTimeLabel.setPreferredSize(new Dimension(60, 20));
		limit.setLimAndConstraints(endTimeLabel, 2, 0, 1, 1, layout);
		this.add(endTimeLabel);
		
		endTimeField.setDocument(new OnlyNumberDocument());
		endTimeField.setPreferredSize(new Dimension(60, 20));
		endTimeField.setBackground(textBgColor);
		limit.setLimAndConstraints(endTimeField, 3, 0, 1, 1, layout);
		this.add(endTimeField);
		
		timeLabelDescription.setPreferredSize(new Dimension(60, 20));
		limit.setLimAndConstraints(timeLabelDescription, 4, 0, 1, 1, layout);
		this.add(timeLabelDescription);
		
		timeLabel.setPreferredSize(new Dimension(100, 20));
		limit.setLimAndConstraints(timeLabel, 5, 0, 1, 1, layout);
		this.add(timeLabel);
	}
	
	private void enableParameters(boolean enable) {
		startTimeLabel.setVisible(enable);
		startTimeField.setVisible(enable);
		endTimeLabel.setVisible(enable);
		endTimeField.setVisible(enable);
		timeLabelDescription.setVisible(true);
		timeLabel.setVisible(true);
	}
}
