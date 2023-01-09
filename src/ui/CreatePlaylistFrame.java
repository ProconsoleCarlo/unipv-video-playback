package ui;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import utils.OnlyNumberDocument;
import utils.VideosMap;
/**
 * Il frame per creare la playlist di file video 
 * @author Carlo
 *
 */
public class CreatePlaylistFrame {

	private int positionElement = 1;
	public CreatePlaylistFrame() {
		super();
	}

	/**
	 * Crea il frame per creare la playlist di file video
	 */
	public void createParameterFrame() {
		final JFrame frame = new JFrame();
		frame.setTitle("Crea playlist");
		final GridBagLayout layout = new GridBagLayout();
		final GridBagConstraints lim = new GridBagConstraints();
		frame.setLayout(layout);
		
		JLabel positionLabel = new JLabel("#:");
		positionLabel.setPreferredSize(new Dimension(20, 20));
		lim.gridx = 0;
		lim.gridy = 0;
		lim.gridwidth = 1;
		lim.gridheight = 1;
		layout.setConstraints(positionLabel, lim);
		frame.add(positionLabel);
		
		JLabel nameLabel = new JLabel("Nome:");
		nameLabel.setPreferredSize(new Dimension(200, 20));
		lim.gridx = 1;
		lim.gridy = 0;
		lim.gridwidth = 1;
		lim.gridheight = 1;
		layout.setConstraints(nameLabel, lim);
		frame.add(nameLabel);
		
		JLabel startTimeLabel = new JLabel("Inizio:");
		startTimeLabel.setPreferredSize(new Dimension(60, 20));
		lim.gridx = 3;
		lim.gridy = 0;
		lim.gridwidth = 1;
		lim.gridheight = 1;
		layout.setConstraints(startTimeLabel, lim);
		frame.add(startTimeLabel);
		
		JLabel endTimeLabel = new JLabel("Fine:");
		endTimeLabel.setPreferredSize(new Dimension(60, 20));
		lim.gridx = 4;
		lim.gridy = 0;
		lim.gridwidth = 1;
		lim.gridheight = 1;
		layout.setConstraints(endTimeLabel, lim);
		frame.add(endTimeLabel);
		
		JButton addButton = new JButton("+");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				createElement(layout, lim, frame);
				frame.pack();
			}
		});
		lim.gridx = 5;
		lim.gridy = 0;
		lim.gridwidth = 1;
		lim.gridheight = 1;
		layout.setConstraints(addButton, lim);
		frame.add(addButton);

		createElement(layout, lim, frame);
		createMenuBar(frame);
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	private void createElement(GridBagLayout layout, GridBagConstraints lim, final JFrame frame) {
		JLabel positionLabel = new JLabel(""+positionElement);
		positionLabel.setPreferredSize(new Dimension(20, 20));
		lim.gridx = 0;
		lim.gridy = positionElement;
		lim.gridwidth = 1;
		lim.gridheight = 1;
		layout.setConstraints(positionLabel, lim);
		frame.add(positionLabel);
		
		final JTextField elementName = new JTextField();
		elementName.setEditable(false);
		elementName.setPreferredSize(new Dimension(200, 20));
		lim.gridx = 1;
		lim.gridy = positionElement;
		lim.gridwidth = 1;
		lim.gridheight = 1;
		layout.setConstraints(elementName, lim);
		frame.add(elementName);
		
		JButton chooseFile = new JButton("Select");
		chooseFile.setPreferredSize(new Dimension(30, 20));
		chooseFile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileDialog fileDialog = new FileDialog(frame, "Seleziona video", FileDialog.LOAD);
				fileDialog.setVisible(true);
				elementName.setText(fileDialog.getFile());
				VideosMap.getVideosMap().addVideo((fileDialog.getFile()+fileDialog.getDirectory()).hashCode(), new File(fileDialog.getDirectory(), fileDialog.getFile()));
				frame.pack();
			}
		});
		lim.gridx = 2;
		lim.gridy = positionElement;
		lim.gridwidth = 1;
		lim.gridheight = 1;
		layout.setConstraints(chooseFile, lim);
		frame.add(chooseFile);
		
		JTextField startTimeField = new JTextField();
		startTimeField.setDocument(new OnlyNumberDocument());
		startTimeField.setPreferredSize(new Dimension(60, 20));
		lim.gridx = 3;
		lim.gridy = positionElement;
		lim.gridwidth = 1;
		lim.gridheight = 1;
		layout.setConstraints(startTimeField, lim);
		frame.add(startTimeField);
		
		JTextField endTimeField = new JTextField();
		endTimeField.setDocument(new OnlyNumberDocument());
		endTimeField.setPreferredSize(new Dimension(60, 20));
		lim.gridx = 4;
		lim.gridy = positionElement;
		lim.gridwidth = 1;
		lim.gridheight = 1;
		layout.setConstraints(endTimeField, lim);
		frame.add(endTimeField);
		positionElement++;
	}
	private void createMenuBar(JFrame frame) {
		JMenuBar menuBar = new JMenuBar();
			JMenu menuFile = new JMenu("File");
				JMenuItem save = new JMenuItem("Salva");
				save.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						
					}
				});
			menuFile.add(save);
				JMenuItem exit = new JMenuItem("Chiudi");
			menuFile.add(exit);
		menuBar.add(menuFile);
		frame.setJMenuBar(menuBar);
	}
}
