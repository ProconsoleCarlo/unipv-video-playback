package ui.main.PlaylistPanel;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.MatteBorder;

import playlist.PlaylistElement;
import settings.MediaPlayerSettings;

/**
 * 
 * Un pannello scorribile contenente gli elementi della playlist in esecuzione.
 *
 */
public class PlaylistPanel extends JScrollPane{
	private static final long serialVersionUID = 1L;
	
	private PlaylistJTree playlistTree;
	
	public PlaylistPanel(PlaylistJTree playlistTree) {
		super(playlistTree);
		this.playlistTree = playlistTree;
		this.setLayout(new ScrollPaneLayout());
		this.playlistTree.setBackground(MediaPlayerSettings.getSettings().getColors().colorBackground.brighter());
		this.setBorder(new MatteBorder(1, 1, 1, 1, MediaPlayerSettings.getSettings().getColors().colorPinkBorder));
		realize(false);
	}
	
	/**
	 * Imposta il playlistFile di cui si vuole disegnare l'albero e in piu'
	 * restituisce la playlist eseguibile sotto forma di ArrayList<PlaylistElement>.
	 * 
	 * @param playlistFile il file di cui costruire l'albero
	 * @return la playlist eseguibile (contenente solo elementi video)
	 */
	public ArrayList<PlaylistElement> createPlaylistFromFile(File playlistFile) {
		ArrayList<PlaylistElement> playablePlaylist = playlistTree.createPlaylistFromFile(playlistFile);
		realize(true);
		return playablePlaylist;
	}
	
	/**
	 * Imposta il singolo elemento video di cui si vuole disegnare l'albero.
	 * @param element l'elemento video di cui costruire l'albero
	 */
	public void visualizeSingleElement(PlaylistElement element) {
		playlistTree.visualizeSingleElement(element);
		realize(true);
	}
	
	public void cleanWindow() {
		playlistTree.cleanWindow();
		realize(false);
	}
	
	private void realize(boolean b) {
		playlistTree.updateUI(); 
		playlistTree.setRootVisible(b); // Per nascondere "In riproduzione"
		expandAllNodes(playlistTree, 0, playlistTree.getRowCount());
		playlistTree.setShowsRootHandles(false); // Per nascondere il "+" per espandere nel primo row.
		playlistTree.setVisible(true);
	}
	
	private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
	    for(int i=startingIndex;i<rowCount;++i){
	        tree.expandRow(i);
	    }
	    if(tree.getRowCount()!=rowCount){
	        expandAllNodes(tree, rowCount, tree.getRowCount());
	    }
	}
}
