package tests.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import playlist.PlaylistFilePlsInterpreter;
import mediaPlayer.VideoManager;
import mediaPlayer.VideoPlayer;
import ui.main.MainPanel;
import ui.main.ControlPanel.ControlsPanel;
import ui.main.PlaylistPanel.PlaylistJTree;
import ui.main.PlaylistPanel.PlaylistPanel;

/**
 * Test per visualizzare il posizionamento dei vari componenti sull' UI.
 * 
 */
public class TestMainFrame {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setTitle("MediaPlayer");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(860, 400));
		
		
		VideoManager videoManager = new VideoManager(new VideoPlayer());
		PlaylistPanel playlistPanel = new PlaylistPanel(new PlaylistJTree(new PlaylistFilePlsInterpreter()));
		ControlsPanel controlsPanel = new ControlsPanel(videoManager);
		controlsPanel.setBackground(Color.GREEN);
		MainPanel mainPanel = new MainPanel(frame, playlistPanel, controlsPanel);
		
		
		
		Component[] components = new Component[2];
		final int bothWidth = 1280;
		final int heightVideo = 720;
		
		JPanel panel1 = new JPanel();
		panel1.setPreferredSize(new Dimension(bothWidth,heightVideo));
		panel1.setBackground(Color.yellow);
		components[0] = panel1;

		MenuBar menuBar = new MenuBar();
			Menu menu = new Menu("Non premere");
				MenuItem menuItem = new MenuItem("Operazione non disponibile");
				menuItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						JOptionPane.showMessageDialog(null, "Operazione non disponibile");	
					}
				});
				menu.add(menuItem);
		menuBar.add(menu);
	
	
		frame.setMenuBar(menuBar);
		frame.getContentPane().add(mainPanel);
		frame.setMinimumSize(new Dimension(400, 200));
		frame.setResizable(true);
		frame.setVisible(true);
		
		mainPanel.setComponents(components);
		mainPanel.createPlaylistFromFile(new File(""));
		mainPanel.createMainPanel();
	}
}
