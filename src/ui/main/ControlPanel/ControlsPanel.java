package ui.main.ControlPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import mediaPlayer.VideoManager;
import settings.MediaPlayerSettings;
import ui.ColoredButton;
import utils.DimensionFreezer;
import utils.LimConstraints;

/**
 * Un pannello contenente i controlli, come il bottone play/pause e la barra del tempo corrente/finale.
 * 
 */
public class ControlsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private static MediaPlayerSettings settings = MediaPlayerSettings.getSettings();
	
	private static final int playlistPanelWidth = settings.getPlaylistpanelwidth();
	private static final int playPausaButtonWidth = 50;
	private static final int timeLabelWidth = playlistPanelWidth - playPausaButtonWidth;
	private static final int timeSliderPrecision = 1000/(int)settings.getTimerPeriod(); // 1->1sec, 2->0.5sec, 5->0.2sec, 10 ->0.1sec...
	
	private GridBagLayout layout = new GridBagLayout();
	private LimConstraints limit = new LimConstraints();
	private ColoredButton playPausaButton = new ColoredButton("<html>&#9658</html>");
	private JSlider timeSlider = new JSlider(JSlider.HORIZONTAL,0,1,0);
	private JLabel timeLabel = new JLabel(" 00/00 sec. ");
	private PlayPauseActionListener pippo;

	public ControlsPanel(VideoManager videoManager) {
		super();
		this.pippo = new PlayPauseActionListener(videoManager, playPausaButton);
		this.playPausaButton.addActionListener(pippo);
		videoManager.addObserver(pippo);
	}

	/**
	 * Setta i vari tempi nella barra.
	 * 
	 * @param currentTime
	 *            tempo corrente della playlist (posizione del cursore)
	 * @param stopTime
	 *            durata totale della playlist
	 * @param tempiFinaliVideo
	 *            vettore contenente il tempo finale dei singoli video (relativi alla playlist)
	 */
	public void setTime(double currentTime, int stopTime, int[] tempiFinaliVideo) {
		timeLabel.setText(" " + (int)currentTime + "/" + stopTime + " sec. ");
		timeSlider.setMaximum(stopTime * timeSliderPrecision);
		this.setSliderLableTables(tempiFinaliVideo);
		timeSlider.setValue((int) (currentTime * timeSliderPrecision));

	}

	/**
	 * Inserisce delle label sottostanti la barra, indicanti i tempi di fine
	 * video
	 */
	private void setSliderLableTables(int[] tempiFinaliVideo) {
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(0, new JLabel("0"));
		for (int i = 0; i < tempiFinaliVideo.length; i++) {
			labelTable.put(tempiFinaliVideo[i] * timeSliderPrecision, new JLabel(String.valueOf(tempiFinaliVideo[i])));
		}
		timeSlider.setLabelTable(labelTable);
		timeSlider.setPaintLabels(true);
	}

	/**
	 * Crea i vari componenti che costituiscono il ControlPanelComponent.
	 */
	public void createControlPanelComponent(int thisPanelWidth) {	
		this.setLayout(layout);
		this.setBackground(Color.WHITE);
		
		timeSlider.setMinorTickSpacing(timeSliderPrecision);
		timeSlider.setMajorTickSpacing(10 * timeSliderPrecision);
		timeSlider.setPaintTicks(true);
		timeSlider.setPaintLabels(true);
		timeSlider.setEnabled(false);
		timeSlider.setBackground(settings.getColors().colorBackground);
		limit.setLimAndConstraints(timeSlider, 0, 0, 1, 1, layout);
		this.add(timeSlider);
		
		limit.setLimAndConstraints(playPausaButton, 1, 0, 1, 1, layout);
		this.add(playPausaButton);
		
		timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		timeLabel.setOpaque(true);
		timeLabel.setBackground(settings.getColors().colorBackground);
		limit.setLimAndConstraints(timeLabel, 2, 0, 1, 1, layout);
		this.add(timeLabel);

		resize(thisPanelWidth);
		
		this.addAncestorListener(new DimensionFreezer(timeSlider, playPausaButton, timeLabel));
	}
	
	public void resize(int thisPanelWidth){
		int timeSliderWidth = thisPanelWidth - playlistPanelWidth;
		timeSlider.setPreferredSize(new Dimension(timeSliderWidth, timeSlider.getPreferredSize().height));
		playPausaButton.setPreferredSize(new Dimension(playPausaButtonWidth, timeSlider.getPreferredSize().height));
		timeLabel.setPreferredSize(new Dimension(timeLabelWidth, timeSlider.getPreferredSize().height));
		this.setPreferredSize(new Dimension(thisPanelWidth, timeSlider.getPreferredSize().height));
	}
}
