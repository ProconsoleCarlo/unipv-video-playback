package ui.main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JPanel;

import playlist.PlaylistElement;
import settings.MediaPlayerSettings;
import ui.main.ControlPanel.ControlsPanel;
import ui.main.PlaylistPanel.PlaylistPanel;
import utils.DimensionFreezer;
import utils.LimConstraints;

/**
 * il pannello principale, contenente il componente visuale, il pannello della playlist e i controlli.
 * 
 */
public class MainPanel extends JPanel implements Observer{
	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	private ControlsPanel controlsPanel;
	private GridBagLayout layout = new GridBagLayout();
	private LimConstraints limit = new LimConstraints();
	private PlaylistPanel playlistPanel;
	private VideoPanel videoPanel;
	private MediaPlayerSettings settings = MediaPlayerSettings.getSettings();
		
	public MainPanel(final JFrame frame, PlaylistPanel playlistPanel, ControlsPanel controlsPanel) {
		super();
		this.frame = frame;
		this.playlistPanel = playlistPanel;
		this.setLayout(layout);
		this.videoPanel = new VideoPanel(layout, limit);
		this.controlsPanel = controlsPanel;
		this.frame.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent arg0) {
			}
			@Override
			public void componentResized(ComponentEvent arg0) {
				resize();
			}
			@Override
			public void componentMoved(ComponentEvent arg0) {
			}
			@Override
			public void componentHidden(ComponentEvent arg0) {
			}
		});
		this.setSize(this.frame.getSize());

	}
	
	public void setComponents(Component[] components) {
		videoPanel.setComponents(components);
	}
	
	public void setTime(double currentTime, int stopTime, int[] tempiFinaliVideo){
		controlsPanel.setTime(currentTime, stopTime, tempiFinaliVideo);
	}
	
	/**
	 * Imposta il playlistFile da visualizzare e in piu'
	 * restituisce la playlist eseguibile sotto forma di ArrayList<PlaylistElement>.
	 * 
	 * @param playlistFile il file di cui costruire l'albero
	 * @return la playlist eseguibile (contenente solo elementi video)
	 */
	public ArrayList<PlaylistElement> createPlaylistFromFile(File playlistFile) {
		return playlistPanel.createPlaylistFromFile(playlistFile);
	}

	public void visualizeSingleElement(PlaylistElement element) {
		playlistPanel.visualizeSingleElement(element);
	}
	
	public void cleanWindow() {
		playlistPanel.cleanWindow();
		setTime((double)0, 0, new int[0]);
	}
	
	/**
	 * Crea i vari componenti che costituiscono il MainPanel.
	 */
	public void createMainPanel() {
		this.removeAll();
		this.repaint();
		int restoWidth = this.getWidth();
		int restoHeight = this.getHeight();
		this.setMinimumSize(new Dimension(restoWidth, restoHeight));

		// Componente dei controlli
		controlsPanel.createControlPanelComponent(restoWidth);
		restoHeight -= controlsPanel.getPreferredSize().height;
		limit.setLimAndConstraints(controlsPanel, 0, 3, 4, 1, layout);
		this.add(controlsPanel);
		
		// Componente playlistPanel
		playlistPanel.setPreferredSize(new Dimension(settings.getPlaylistpanelwidth(), restoHeight));
		restoWidth -= playlistPanel.getPreferredSize().width;
		limit.setLimAndConstraints(playlistPanel, 3, 0, 1, 3, layout);
		this.add(playlistPanel);

		//Componenti video
		JPanel[] videoPanels = videoPanel.createVideoImageComponents(restoWidth, restoHeight);
		for (int i = 0; i < videoPanels.length; i++) {
			this.add(videoPanels[i]);
		}
		
		DimensionFreezer freezer = new DimensionFreezer(controlsPanel, playlistPanel, videoPanels[0], videoPanels[1], videoPanels[2], videoPanels[3], videoPanels[4]);
		this.addAncestorListener(freezer);
	
		frame.pack();
	}

	private void resize(){
		int restoWidth = this.getWidth();
		int restoHeight = this.getHeight();
		
		controlsPanel.resize(restoWidth);
		restoHeight -= controlsPanel.getPreferredSize().height;
		playlistPanel.setPreferredSize(new Dimension(settings.getPlaylistpanelwidth(), restoHeight));
		restoWidth -= playlistPanel.getPreferredSize().width;
		videoPanel.resize(restoWidth, restoHeight);
		frame.pack();
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
		frame.repaint();
	}
}
