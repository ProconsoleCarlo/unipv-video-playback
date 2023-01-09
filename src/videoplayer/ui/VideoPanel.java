package videoplayer.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;

import javax.media.Manager;
import javax.media.Player;
import javax.swing.JPanel;

public class VideoPanel extends JPanel {
	
	public VideoPanel(File fileVideo) throws java.io.IOException, 
									 java.net.MalformedURLException, 
									 javax.media.MediaException {
		
		Player player = Manager.createRealizedPlayer(fileVideo.toURI().toURL());
		Component c = player.getVisualComponent();
		Component cpc = player.getControlPanelComponent();
		
		setLayout(new BorderLayout());
		
		if (cpc != null) {
			add(cpc, BorderLayout.SOUTH);
		}
		add(c, BorderLayout.CENTER);

		player.start();
	}
}