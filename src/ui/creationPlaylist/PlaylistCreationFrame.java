package ui.creationPlaylist;

import gallery.Gallery;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.MatteBorder;

import mediaPlayer.IVideoPlayer;
import mediaPlayer.VideoManager;
import playlist.PlaylistElement;
import playlist.PlaylistManager;
import playlist.PlaylistReaderWriter;
import settings.MediaPlayerSettings;
import ui.ColoredButton;
import utils.LimConstraints;
/**
 * Il frame per creare la playlist di file video.
 * 
 */
public class PlaylistCreationFrame {

	private Color bgColor = MediaPlayerSettings.getSettings().getColors().colorBackground;
	private Color bgBrighterColor = MediaPlayerSettings.getSettings().getColors().colorBackgroundBrighter;
	
	private JFrame frame = new JFrame();
	private JPanel mainpanel = new JPanel();
	private GridBagLayout layout = new GridBagLayout();
	private LimConstraints limit = new LimConstraints();
	@SuppressWarnings("rawtypes")
	private JComboBox galleryComboBox = new JComboBox();
	private PlaylistParametersPanel parametersPanel = new PlaylistParametersPanel();
	private PlaylistGraphicPanel graphicPanel = new PlaylistGraphicPanel();
	
	private PlaylistReaderWriter playlistReaderWriter;
	private VideoManager videoManager;
	private PlaylistManager playlistManager;

	@SuppressWarnings("rawtypes")
	private JList list = new JList();
	private ArrayList<String> playlistInJList = new ArrayList<String>();
	private ArrayList<PlaylistElement> playlist = new ArrayList<PlaylistElement>();
	private Gallery gallery = Gallery.getGallery();
	
	public PlaylistCreationFrame(VideoManager videoManager, PlaylistReaderWriter playlistReaderWriter, PlaylistManager playlistManager) {
		super();
		this.videoManager = videoManager;
		this.playlistReaderWriter = playlistReaderWriter;
		this.playlistManager = playlistManager;
		realize(videoManager.getVideoPlayer(), playlistReaderWriter);
	}
	public PlaylistParametersPanel getParametersPanel() {
		return parametersPanel;
	}
	@SuppressWarnings("rawtypes")
	public JComboBox getGalleryComboBox() {
		return galleryComboBox;
	}

	public PlaylistGraphicPanel getGraphicPanel() {
		return graphicPanel;
	}
	
	public void setVideoDuration(int videoDuration, String type) {
		parametersPanel.setVideoDuration(videoDuration, type);
	}
	
	/**
	 * aggiunge un elemento alla JList e al graphicPanel
	 * @param element
	 */
	@SuppressWarnings("unchecked")
	public void addPlaylistElement(String element) {
		int[] startAndEndTime = parametersPanel.getStartAndEndTime();
		playlist.add(new PlaylistElement(new File((String)galleryComboBox.getSelectedItem()), startAndEndTime[0], startAndEndTime[1]));
		playlistInJList.add(element);
		list.setListData(playlistInJList.toArray(new String[playlistInJList.size()]));
		list.repaint();
		graphicPanel.addElement(element, videoManager, playlistManager);
		playlistReaderWriter.setPlaylistSalvata(false);
		frame.pack();
	}
	
	private void realize(IVideoPlayer videoPlayer, final PlaylistReaderWriter playlistReaderWriter) {
		
		frame.setTitle("Crea playlist");
		frame.getContentPane().add(mainpanel);
		mainpanel.setLayout(layout);
		mainpanel.setBackground(bgBrighterColor);
		createComponents(videoPlayer);
		final CreationPlaylistMenuBar menuBar = new CreationPlaylistMenuBar(frame, playlist, playlistReaderWriter);
		frame.setJMenuBar(menuBar);
		frame.setResizable(false);
		frame.setMinimumSize(frame.getPreferredSize());
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent arg0) {}
			@Override
			public void windowIconified(WindowEvent arg0) {}
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			@Override
			public void windowClosing(WindowEvent arg0) {
				graphicPanel.clear();
				menuBar.richiestaSalvataggio(frame, playlist, playlistReaderWriter);
			}
			@Override
			public void windowClosed(WindowEvent arg0) {}
			@Override
			public void windowActivated(WindowEvent arg0) {}
		});
		frame.pack();
		frame.setVisible(true);
	}

	@SuppressWarnings("unchecked")
	private void createComponents(IVideoPlayer videoPlayer) {
		
		JLabel nameLabel = new JLabel("Nome:");
		nameLabel.setPreferredSize(new Dimension(60, 20));
		limit.setLimAndConstraints(nameLabel, 0, 0, 1, 1, layout);
		mainpanel.add(nameLabel);
		
		galleryComboBox.setPreferredSize(new Dimension(400, 20));
		galleryComboBox.addItem("Aggiungi");
		galleryComboBox.setBackground(bgColor.brighter());
		for (Map.Entry<Integer, File> entry  : gallery.getGalleryFiles().entrySet()) {
			if (entry.getValue().exists()) {
				galleryComboBox.addItem(entry.getValue().toString());
			}
		}
		galleryComboBox.addActionListener(new SelectElementActionListener(this, videoPlayer, playlistManager));
		limit.setLimAndConstraints(galleryComboBox, 1, 0, 1, 1, layout);
		mainpanel.add(galleryComboBox);
		
		ColoredButton addButton = new ColoredButton("<html>&#8594</html>");
		addButton.setPreferredSize(new Dimension(40, 40));
		AddElementActionListener addElementActionListener = new AddElementActionListener(this);
		addElementActionListener.addObserver(graphicPanel);
		addButton.addActionListener(addElementActionListener);
		limit.setLimAndConstraints(addButton, 2, 0, 1, 1, layout);
		mainpanel.add(addButton);
		
		list.setVisible(true);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(bgColor.brighter());
		JScrollPane scrollPane = new JScrollPane(list);
		scrollPane.setPreferredSize(new Dimension(300, 300));
		limit.setLimAndConstraints(scrollPane, 3, 0, 1, 3, layout);
		mainpanel.add(scrollPane);
		
		parametersPanel.setBackground(bgColor);
		limit.setLimAndConstraints(parametersPanel, 1, 1, 1, 1, layout);
		mainpanel.add(parametersPanel);
		
		graphicPanel.setPreferredSize(new Dimension((int)frame.getPreferredSize().getWidth(), 160));
		graphicPanel.setBorder(new MatteBorder(1, 5, 1, 5, MediaPlayerSettings.getSettings().getColors().colorPinkBorder));
		limit.setLimAndConstraints(graphicPanel, 0, 3, 4, 1, layout);
		mainpanel.add(graphicPanel);

	}
}
