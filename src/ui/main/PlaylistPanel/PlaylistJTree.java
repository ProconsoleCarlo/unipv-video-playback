package ui.main.PlaylistPanel;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import playlist.DrawablePlaylistNode;
import playlist.IPlaylistFileInterpreter;
import playlist.PlaylistElement;

/**
 * Permette di visualizzare gli elementi contenuti nella playlist in esecuzione.
 *
 */
public class PlaylistJTree extends JTree {
	private static final long serialVersionUID = 1L;

	private DrawablePlaylistNode node;
	
	private static DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("In riproduzione:");

	public PlaylistJTree(IPlaylistFileInterpreter interpreter) {
		super((TreeNode)rootNode);
		node = new DrawablePlaylistNode(interpreter);
	}
	
	/**
	 * Imposta il playlistFile di cui si vuole disegnare l'albero e in piu'
	 * restituisce la playlist sottoforma di ArrayList<PlaylistElement>.
	 * 
	 * @param playlistFile il file di cui costruire l'albero
	 * @return la playlist eseguibile (contenente solo elementi video)
	 */
	public ArrayList<PlaylistElement> createPlaylistFromFile(File playlistFile) {
		rootNode.removeAllChildren();
		rootNode = node.drawTreeAndCreatePlaylist(playlistFile, rootNode);
		return node.getPlayablePlaylist();
	}
	
	/**
	 * Imposta il singolo elemento video di cui si vuole disegnare l'albero.
	 * @param element l'elemento video di cui costruire l'albero
	 */
	public void visualizeSingleElement(PlaylistElement element) {
		rootNode.removeAllChildren();
		rootNode = node.drawTreeAndCreatePlaylist(element, rootNode);
	}
	
	public void cleanWindow() {
		rootNode.removeAllChildren();
	}
}
