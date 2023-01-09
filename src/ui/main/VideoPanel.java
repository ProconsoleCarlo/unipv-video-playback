package ui.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

import utils.LimConstraints;

/**
 * Classe che contiene i vari componenti visibili nel panel di riporduzione video.
 *
 */
public class VideoPanel{

	private GridBagLayout layout;
	private LimConstraints limit;
	private Component[] components;
	private JPanel panel = new JPanel();
	private JPanel borderNorth = new JPanel();
	private JPanel borderSouth = new JPanel();
	private JPanel borderWest = new JPanel();
	private JPanel borderEast = new JPanel();
	
	public VideoPanel(GridBagLayout layout, LimConstraints limit) {
		super();
		this.layout = layout;
		this.limit = limit;
	}
	
	public void setComponents(Component[] components) {
		this.components = components;
	}

	/**
	 * Crea i vari componenti visibili nel panel di riporduzione video.
	 * @param restoWidth
	 * @param restoHeight
	 * @return
	 */
	public JPanel[] createVideoImageComponents(int restoWidth, int restoHeight) {
		panel.removeAll();
		
		JPanel[] videoPanels = new JPanel[5];
		
		// Componente del video
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.BLACK);
		if (components != null) {
			panel.add(components[0], BorderLayout.CENTER);
		}
		limit.setLimAndConstraints(panel, 1, 1, 1, 1, layout);
		videoPanels[0] = panel;

		// Riga nera alta
		borderNorth.setBackground(Color.BLACK);
		limit.setLimAndConstraints(borderNorth, 0, 0, 3, 1, layout);
		videoPanels[1] = borderNorth;

		// Riga nera bassa
		borderSouth.setBackground(Color.BLACK);
		limit.setLimAndConstraints(borderSouth, 0, 2, 3, 1, layout);
		videoPanels[2] = borderSouth;

		// Riga nera sx
		borderWest.setBackground(Color.BLACK);
		limit.setLimAndConstraints(borderWest, 0, 0, 1, 3, layout);
		videoPanels[3] = borderWest;

		// Riga nera dx
		borderEast.setBackground(Color.BLACK);
		limit.setLimAndConstraints(borderEast, 2, 0, 1, 3, layout);
		videoPanels[4] = borderEast;
		
		resize(restoWidth, restoHeight);

		return videoPanels;
	}
	
	public void resize(int restoWidth, int restoHeight){
		Dimension maxSize = new Dimension(restoWidth, restoHeight);
		if (components != null) {
			panel.setPreferredSize(getProporzionalSize(components[0].getPreferredSize(), maxSize));
		}
		restoWidth -= panel.getPreferredSize().width;
		restoHeight -= panel.getPreferredSize().height;
		
		borderNorth.setPreferredSize(new Dimension(maxSize.width, (int)(restoHeight/2)));
		borderSouth.setPreferredSize(new Dimension(maxSize.width, (restoHeight/2 + restoHeight%2)));
		borderWest.setPreferredSize(new Dimension((int)(restoWidth/2), maxSize.height));
		borderEast.setPreferredSize(new Dimension((restoWidth/2 + restoWidth%2), maxSize.height));
	}
	
	private Dimension getProporzionalSize(Dimension videoDimension, Dimension maxDimension){
		int videoWidth = videoDimension.width;
		int videoHeight = videoDimension.height;
		if(videoHeight > maxDimension.height){
			videoHeight = maxDimension.height;
			videoWidth = videoHeight * videoDimension.width / videoDimension.height;
		}else if (videoHeight < maxDimension.height && videoWidth*maxDimension.height < videoHeight*maxDimension.width) {
			videoHeight = maxDimension.height;
			videoWidth = videoHeight * videoDimension.width / videoDimension.height;
		}
		if(videoWidth > maxDimension.width){
			videoWidth = maxDimension.width;
			videoHeight = videoWidth * videoDimension.height / videoDimension.width;
		}else if (videoWidth < maxDimension.width && videoWidth*maxDimension.height > videoHeight*maxDimension.width) {
			videoWidth = maxDimension.width;
			videoHeight = videoWidth * videoDimension.height / videoDimension.width;
		}
		return new Dimension(videoWidth, videoHeight);
	}
}
