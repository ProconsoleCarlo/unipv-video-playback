package videoplayer.main;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.io.File;

import javax.swing.JFrame;

import videoplayer.ui.CutterPanel;
import videoplayer.ui.VideoPanel;

public class VideoPlayer {

	public static void main(String[] args) {

		JFrame frame =new JFrame();
		FileDialog fd = new FileDialog(frame, "TrivialJMFPlayer", FileDialog.LOAD);
		fd.setVisible(true);
		File fileVideo = new File(fd.getDirectory(), fd.getFile());
		
		try {
			frame.setLayout(new BorderLayout());
			frame.add(new CutterPanel(fileVideo), BorderLayout.EAST);
			frame.add(new VideoPanel(fileVideo), BorderLayout.CENTER);
//// Che differenza c'è? Qual è meglio???
//			frame.getContentPane().add(new CutterPanel(fileVideo), BorderLayout.EAST);
//			frame.getContentPane().add(new VideoPanel(fileVideo), BorderLayout.CENTER);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
