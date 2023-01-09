package ui.creationPlaylist;

import gallery.Gallery;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.MatteBorder;

import mediaPlayer.VideoManager;
import playlist.PlaylistManager;
import settings.MediaPlayerSettings;
import utils.LimConstraints;

/**
 * Un pannello scorribile contenente gli elementi della playlist visualizzati sottoforma di jlabel.
 */
public class PlaylistGraphicPanel extends JScrollPane implements Observer{
	private static final long serialVersionUID = 1L;
	
	private Color bgColor = MediaPlayerSettings.getSettings().getColors().colorBackground;
	
	private GridBagLayout layout = new GridBagLayout();
	private LimConstraints limit = new LimConstraints();
	private int nextElementPosition = 0;
	private static JPanel panel = new JPanel();
	private ArrayList<JLabel> graphicsPlaylist = new ArrayList<JLabel>();
	private static int numberOfVisibleElements = 11;
	private static int elementWidth = 70;
	private static int elementHeight = 120;
	private Gallery gallery = Gallery.getGallery();
	private MediaPlayerSettings settings = MediaPlayerSettings.getSettings();
	
	public PlaylistGraphicPanel() {
		super(panel);
		this.setLayout(new ScrollPaneLayout());
		panel.setLayout(layout);
		panel.setBackground(bgColor);
		initialize();
	}

	private void initialize() {
		for (int i = 0; i < numberOfVisibleElements; i++) {
			JLabel startLabel = new JLabel();
			startLabel.setPreferredSize(new Dimension(elementWidth, elementHeight));
			graphicsPlaylist.add(startLabel);
			limit.setLimAndConstraints(startLabel, i, 0, 1, 1, layout);
			panel.add(startLabel);
		}
	}

	public void clear() {
		panel.removeAll();
		graphicsPlaylist.clear();
		initialize();
	}
	
	/**
	 * aggiunge un elemento(in questo caso un immagine) rappresentante il video/playlist selezionato
	 * e' possibile riprodurre il video cliccando sull'immagine.
	 * @param elementName
	 */
	public void addElement(final String elementName, VideoManager videoManager, PlaylistManager playlistManager) {
		ClickedElementMouseListener mouseListener = new ClickedElementMouseListener(videoManager, playlistManager);
		String[] nameSplitted = elementName.substring(elementName.lastIndexOf("\\")+1).split("\\.");
		String toShow = nameSplitted[0];
		JLabel elementLabel;
		if (nextElementPosition<numberOfVisibleElements) {
			elementLabel = graphicsPlaylist.get(nextElementPosition);
			elementLabel.setText(toShow+" "+nextElementPosition);
			limit.setLimAndConstraints(elementLabel, nextElementPosition, 0, 1, 1, layout);
		}else {
			elementLabel = new JLabel(toShow+" "+nextElementPosition);
			elementLabel.setPreferredSize(new Dimension(elementWidth, elementHeight));
			limit.setLimAndConstraints(elementLabel, nextElementPosition, 0, 1, 1, layout);
			panel.add(elementLabel);
			graphicsPlaylist.add(elementLabel);
		}
		elementLabel.setBorder(new MatteBorder(2, 1, 2, 1, Color.black));
		Icon elementIcon;
		if (elementName.endsWith(settings.getPlaylistFileExtension())) {
			elementIcon = gallery.getVideoThumbnailsMap().get("Playlist");
			mouseListener.setParameters(elementName, settings.getPlaylistType());
			elementLabel.addMouseListener(mouseListener);
			
		}else {
			elementIcon = gallery.getVideoThumbnailsMap().get(toShow);
			if (elementIcon == null) {
				elementIcon = gallery.getVideoThumbnailsMap().get("GenericVideo");
			}
			mouseListener.setParameters(elementName, settings.getVideoType());
			elementLabel.addMouseListener(mouseListener);
		}
		elementLabel.setIcon(elementIcon);
		nextElementPosition++;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}
}
