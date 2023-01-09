package ui;

import gallery.Gallery;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import settings.MediaPlayerSettings;
import utils.LimConstraints;

/**
 * pannello delle impostazioni, che permette di visualizzare il percorso della cartella video, della cartella playlist
 * e da' la possibilita' di scegliere una cartella video alternativa.
 * 
 */
public class SettingsPanel extends JDialog{

	private static final long serialVersionUID = 1L;
	private GridBagLayout layout = new GridBagLayout();
	private LimConstraints limit = new LimConstraints();
	private MediaPlayerSettings settings = MediaPlayerSettings.getSettings();
	private static int pathLabelWidth = 200;
	private static int pathFieldWidth = 400;
	
	public SettingsPanel(JFrame frame) {
		super(frame);
		this.setLayout(layout);
		this.setTitle("Impostazioni");
		realize(frame);
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private void realize(final JFrame frame) {
		try{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (Exception e){
			e.printStackTrace();
		}
		JLabel localVideosDirectoryLabel = new JLabel("Cartella video locali:");
		localVideosDirectoryLabel.setPreferredSize(new Dimension(pathLabelWidth, (int)localVideosDirectoryLabel.getPreferredSize().getHeight()));
		limit.setLimAndConstraints(localVideosDirectoryLabel, 0, 0, 1, 1, layout);
		this.add(localVideosDirectoryLabel);
		
		JTextField localVideoDirectoryField = new JTextField();
		File localVideoDirectory = settings.getDirectories().localVideoDirectory;
		localVideoDirectoryField.setText(localVideoDirectory.getAbsolutePath());
		localVideoDirectoryField.setBorder(new MatteBorder(1, 5, 1, 5, Color.BLUE));
		localVideoDirectoryField.setPreferredSize(new Dimension(pathFieldWidth, (int)localVideoDirectoryField.getPreferredSize().getHeight()));
		localVideoDirectoryField.setEditable(false);
		limit.setLimAndConstraints(localVideoDirectoryField, 1, 0, 1, 1, layout);
		this.add(localVideoDirectoryField);
		
		JLabel localPlaylistDirectoryLabel = new JLabel("Cartella playlist:");
		localPlaylistDirectoryLabel.setPreferredSize(new Dimension(pathLabelWidth, (int)localPlaylistDirectoryLabel.getPreferredSize().getHeight()));
		limit.setLimAndConstraints(localPlaylistDirectoryLabel, 0, 1, 1, 1, layout);
		this.add(localPlaylistDirectoryLabel);
		
		JTextField localPlaylistDirectoryField = new JTextField();
		File localPlaylistDirectory = settings.getDirectories().localPlaylistDirectory;
		localPlaylistDirectoryField.setText(localPlaylistDirectory.getAbsolutePath());
		localPlaylistDirectoryField.setBorder(new MatteBorder(1, 5, 1, 5, Color.BLUE));
		localPlaylistDirectoryField.setPreferredSize(new Dimension(pathFieldWidth, (int)localPlaylistDirectoryField.getPreferredSize().getHeight()));
		localPlaylistDirectoryField.setEditable(false);
		limit.setLimAndConstraints(localPlaylistDirectoryField, 1, 1, 1, 1, layout);
		this.add(localPlaylistDirectoryField);
		
		JLabel userVideosDirectoryLabel = new JLabel("Cartella video alternativa:");
		userVideosDirectoryLabel.setPreferredSize(new Dimension(pathLabelWidth, (int)userVideosDirectoryLabel.getPreferredSize().getHeight()));
		limit.setLimAndConstraints(userVideosDirectoryLabel, 0, 2, 1, 1, layout);
		this.add(userVideosDirectoryLabel);
		
		final JTextField userVideosDirectoryField = new JTextField();
		userVideosDirectoryField.setPreferredSize(new Dimension(pathFieldWidth, (int)userVideosDirectoryField.getPreferredSize().getHeight()));
		userVideosDirectoryField.setBorder(new MatteBorder(1, 5, 1, 5, Color.BLUE));
		userVideosDirectoryField.setEditable(false);
		limit.setLimAndConstraints(userVideosDirectoryField, 1, 2, 1, 1, layout);
		this.add(userVideosDirectoryField);
		
		ColoredButton selectDirectoryButton = new ColoredButton("...");
		selectDirectoryButton.setPreferredSize(new Dimension(50, (int)selectDirectoryButton.getPreferredSize().getHeight()));
		selectDirectoryButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileChooser = new JFileChooser("Seleziona cartella di video");
				fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.showOpenDialog(frame);
				File selectedDirectory = fileChooser.getSelectedFile();
				if (selectedDirectory != null) {
					userVideosDirectoryField.setText(selectedDirectory.getAbsolutePath());
					settings.addVideoDirectory(selectedDirectory.getAbsolutePath());
					Gallery.getGallery().initialize();
				}
			}
		});
		limit.setLimAndConstraints(selectDirectoryButton, 2, 2, 1, 1, layout);
		this.add(selectDirectoryButton);
		
		JLabel resizeLabel = new JLabel("Attiva il resize: ");
		resizeLabel.setPreferredSize(new Dimension(pathLabelWidth, (int)resizeLabel.getPreferredSize().getHeight()));
		limit.setLimAndConstraints(resizeLabel, 0, 3, 1, 1, layout);
		this.add(resizeLabel);
		
		final JCheckBox resizeCheckBox = new JCheckBox();
		resizeCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				if (resizeCheckBox.isSelected()) {
					resizeCheckBox.setSelected(false);
					int response = JOptionPane.showOptionDialog(null, 
							"Vuoi attivare il resize della finestra?\n" +
							"(ATTENZIONE: versione beta, alcuni componenti potrebbero avere una dimensione sbagliata!\n" +
							"Se alcuni componenti si presentano della dimensione sbagliata,\n" +
							"allargare leggermente la finestra dopo averla messa della dimensione desiderata", 
							"Attenzione!", 
							JOptionPane.YES_NO_OPTION,
							JOptionPane.INFORMATION_MESSAGE, 
							null, null, null);
					if (response == JOptionPane.YES_OPTION) {
						resizeCheckBox.setSelected(true);
						frame.setResizable(true);
					}else {
						resizeCheckBox.setSelected(false);
						frame.setResizable(false);
					}
				}
				
			}
		});
		limit.setLimAndConstraints(resizeCheckBox, 1, 3, 1, 1, layout);
		this.add(resizeCheckBox);
	}

}
