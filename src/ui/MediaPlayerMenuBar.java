package ui;

import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mediaPlayer.VideoManager;
import playlistReader.PlaylistElement;
import playlistReader.PlaylistManager;
import playlistWriter.PlaylistCreationFrame;
/**
 * La barra dei menu del mediaPlayer
 *
 */
public class MediaPlayerMenuBar {

	private VideoManager videoManager;
	private PlaylistManager playlistManager;

	public MediaPlayerMenuBar(VideoManager videoManager, PlaylistManager playlistManager) {
		super();
		this.videoManager = videoManager;
		this.playlistManager = playlistManager;
	}
	/**
	 * Crea la barra dei menu
	 * @param mainFrame Il frame su cui creare la barra dei menu
	 * @return La barra dei menu
	 */
	public JMenuBar createMenuBar(final JFrame mainFrame) {
		JMenuBar menuBar = new JMenuBar();
			JMenu menuFile = new JMenu("File");
				JMenuItem openFile = new JMenuItem("Apri video");
				openFile.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						FileDialog explorer = new FileDialog(mainFrame, "Apri video", FileDialog.LOAD);
						explorer.setVisible(true);
						videoManager.openVideo(new File(explorer.getDirectory(), explorer.getFile()));
						videoManager.play();
					}
				});
			menuFile.add(openFile);
				//TODO Verra' aperta una finestra in cui poter scegliere tra playlist gia' fatte: DONE!
				//TODO Imporre all'explorer un filtro per visualizzare SOLO file txt (quelli delle playlist)
				// Una volta selezionata una playlist verra' avviato un playlistManager.java
				// che mettera' nel frame due pannelli. A sinistra il video pannel
				// (SENZA barra dei controlli ma col tempo riprodotto e tempo totale DELLA PLAYLIST e un pulsante PAUSA/PLAY)
				// (possibilita' di usare sempre il videoManager...........)
				//TODO A destra sara' presente invece un "playlistPanel" in cui sara' visibile il contenuto della playlist:
				// NOME del video TEMPO_INIZIO e TEMPO_FINE
				JMenuItem openPlaylist = new JMenuItem("Apri playlist");
				openPlaylist.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						FileDialog explorer = new FileDialog(mainFrame, "Apri playlist", FileDialog.LOAD);
						explorer.setDirectory("Playlist");
/*						explorer.setFilenameFilter(new FilenameFilter() {
							@Override
							public boolean accept(File arg0, String arg1) {
								if (arg1.endsWith(".txt")) {
									return true;
								}
								return false;
							}
						});*/
						explorer.setVisible(true);
						if (explorer.getDirectory() != null && explorer.getFile() != null) {
							//I dati della playlist vengono memorizzati in una arrayList<PlaylistElement>
							ArrayList<PlaylistElement> elements = playlistManager.readPlaylistContent(new File(explorer.getDirectory(), explorer.getFile()));
							//Solo per verificarne il funzionamento rapidamente,
							//Per ora stampa il contenuto della playlist sullo standardOutput
							for (int i = 0; i < elements.size(); i++) {
								PlaylistElement element = elements.get(i);
								System.out.println(element.getIndex()+" "+element.getId()+" "+element.getStartTime()+" "+element.getEndTime());
							}
						}
						
						
					}
				});
			menuFile.add(openPlaylist);
			//TODO selezionando questa voce si aprira' invece la DEMO2
			// quindi l'interfaccia per la creazione di una playlist.
			// Vedere storie utente per i dettagli
			JMenuItem createPlaylist = new JMenuItem("Crea playlist");
				createPlaylist.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						PlaylistCreationFrame playlistCreationFrame = new PlaylistCreationFrame();
						playlistCreationFrame.createParameterFrame();
					}
				});
			menuFile.add(createPlaylist);
		menuBar.add(menuFile);
		return menuBar;
	}
}
