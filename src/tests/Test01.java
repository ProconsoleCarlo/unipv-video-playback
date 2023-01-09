package tests;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mediaPlayer.VideoManager;
import mediaPlayer.VideoPlayer;


public class Test01{

	public static void main(String[] args) {
		final JFrame mainFrame = new JFrame();

		final VideoManager videoManager = new VideoManager(mainFrame, new VideoPlayer());
//		videoManager.addObserver(videoPanel);
		try {
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			mainFrame.setSize(640, 480);
			mainFrame.setVisible(true);
			mainFrame.setResizable(true);
			JMenuBar menuBar = new JMenuBar();
			menuBar.add(menuFile(mainFrame, videoManager));
			mainFrame.setJMenuBar(menuBar);

			
//			mainFrame.getContentPane().add(videoPanel);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static JMenu menuFile(final JFrame mainFrame, final VideoManager videoManager) {
		JMenu menuFile = new JMenu("File");
			JMenuItem open = new JMenuItem("Apri");
				open.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						FileDialog explorer = new FileDialog(mainFrame, "Apri", FileDialog.LOAD);
						explorer.setVisible(true);
						videoManager.openVideo(new File(explorer.getDirectory(), explorer.getFile()));
						videoManager.play();
					}
				});
			menuFile.add(open);
		return menuFile;
	}
}