package tests.components;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import playlist.PlaylistFilePlsInterpreter;
import playlist.PlaylistManager;
import mediaPlayer.VideoManager;
import mediaPlayer.VideoPlayer;
import ui.creationPlaylist.PlaylistGraphicPanel;
import ui.main.MainPanel;
import ui.main.ControlPanel.ControlsPanel;
import ui.main.PlaylistPanel.PlaylistJTree;
import ui.main.PlaylistPanel.PlaylistPanel;
import utils.LimConstraints;

/**
 * Testa il pannello PlaylistGraphicPanel (quello che aggiunge la timeline).
 * 
 */
public class TestPlaylistGraphicPanel {

	public static void main(String[] args) {
		
		final JFrame frame = new JFrame();
		
		final VideoManager videoManager = new VideoManager(new VideoPlayer());
		PlaylistPanel playlistPanel = new PlaylistPanel(new PlaylistJTree(new PlaylistFilePlsInterpreter()));
		ControlsPanel controlsPanel = new ControlsPanel(videoManager);
		MainPanel mainPanel = new MainPanel(frame, playlistPanel, controlsPanel);
		final PlaylistManager playlistManager = new PlaylistManager(videoManager, mainPanel);

		videoManager.setMainPanel(mainPanel);
		mainPanel.createMainPanel();
		
		GridBagLayout layout = new GridBagLayout();
		LimConstraints limit = new LimConstraints();
		frame.setLayout(layout);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final PlaylistGraphicPanel graphicPanel = new PlaylistGraphicPanel();
		graphicPanel.setPreferredSize(new Dimension(640, 480));
		limit.setLimAndConstraints(graphicPanel, 0, 1, 1, 1, layout);
		frame.add(graphicPanel);
		
		JButton buttonAdd = new JButton("Aggiungi elemento");
		buttonAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				graphicPanel.addElement("prova", videoManager, playlistManager);
				frame.pack();
			}
		});
		limit.setLimAndConstraints(buttonAdd, 0, 0, 1, 1, layout);
		frame.add(buttonAdd);

		frame.pack();
		frame.setVisible(true);
	}
}
