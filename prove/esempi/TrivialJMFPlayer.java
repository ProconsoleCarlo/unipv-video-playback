package esempi;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FileDialog;
import java.io.File;

import javax.media.Manager;
import javax.media.Player;
import javax.swing.JFrame;

public class TrivialJMFPlayer extends JFrame {
	
	public TrivialJMFPlayer() throws java.io.IOException, 
									 java.net.MalformedURLException, 
									 javax.media.MediaException {
		FileDialog fd = new FileDialog(this, "TrivialJMFPlayer", FileDialog.LOAD);
		fd.setVisible(true);
		File fileVideo = new File(fd.getDirectory(), fd.getFile());
		Player player = Manager.createRealizedPlayer(fileVideo.toURI().toURL());
		Component c = player.getVisualComponent();
		Component cpc = player.getControlPanelComponent();
		if (cpc != null) {
			add(cpc, BorderLayout.SOUTH);
		}
		add(c, BorderLayout.CENTER);

		player.start();
	}
	
	public static void main(String[] args) {
		try {
			JFrame f = new TrivialJMFPlayer();
			f.setDefaultCloseOperation(EXIT_ON_CLOSE);
			f.pack();
			f.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}