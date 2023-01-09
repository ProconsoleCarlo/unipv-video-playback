package tests;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.media.ControllerAdapter;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class VideoPlayerTest01 extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Player player;
    Component center;
    Component south;
  
    @SuppressWarnings("deprecation")
	public VideoPlayerTest01() {
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      JButton button = new JButton("Select File");
      ActionListener listener = 
          new ActionListener() {
        public void actionPerformed(
            ActionEvent event) {
          JFileChooser chooser = 
            new JFileChooser(".");
          int status = 
            chooser.showOpenDialog(VideoPlayerTest01.this);
          if (status == 
              JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try {
              load(file);
            } catch (Exception e) {
              System.err.println("Try again: " + e);
            }
          }
        }
      };
      button.addActionListener(listener);
      getContentPane().add(button, 
        BorderLayout.NORTH);
      pack();
      show();
    }
  
    public void load(final File file) 
        throws Exception {
      URL url = file.toURI().toURL();
      final Container contentPane = 
        getContentPane();
      if (player != null) {
        player.stop();
      }
      player = Manager.createPlayer(url);
      ControllerListener listener = 
          new ControllerAdapter() {
        public void realizeComplete(
            RealizeCompleteEvent event) {
          Component vc = 
            player.getVisualComponent();
          if (vc != null) {
            contentPane.add(vc, 
              BorderLayout.CENTER);
            center = vc;
          } else {
            if (center != null) {
              contentPane.remove(center);
              contentPane.validate();
            }
          }
          Component cpc = 
            player.getControlPanelComponent();
          if (cpc != null) {
            contentPane.add(cpc, 
              BorderLayout.SOUTH);
            south = cpc;
          } else {
            if (south != null) {
              contentPane.remove(south);
              contentPane.validate();
            }
          }
          pack();
          setTitle(file.getName());
        }
      };
      player.addControllerListener(listener);
      player.start();
    }
  
    public static void main(String args[]) {
    	new VideoPlayerTest01();
    }

}
		 
