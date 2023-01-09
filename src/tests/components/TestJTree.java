package tests.components;

import gallery.Gallery;

import java.awt.FileDialog;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import playlist.DrawablePlaylistNode;
import playlist.PlaylistFilePlsInterpreter;


/**
 * Terza versione piu' articolata che usa i PlaylistElement (generati in modo casuale) al posto di File generici
 * E' come la seconda ma e' divisa la parte di creazione degli elementi e delle playlist dalla loro visualizzazione
 * 
 */
public class TestJTree {


	public static void main(String[] args) {

		Gallery.getGallery().initialize();
		
		DrawablePlaylistNode node = new DrawablePlaylistNode(new PlaylistFilePlsInterpreter());
		
		JScrollPane treeView;
		
		FileDialog explorer = new FileDialog(new JFrame(), "Apri playlist", FileDialog.LOAD);
		explorer.setDirectory("Playlist");
		explorer.setVisible(true);
		if (explorer.getDirectory() != null && explorer.getFile() != null) {	
			File file = new File(explorer.getDirectory(), explorer.getFile());
			JTree tree = new JTree(node.drawTreeAndCreatePlaylist(file, new DefaultMutableTreeNode("In riproduzione")));
				tree.setRootVisible(false);
				tree.setShowsRootHandles(false);
				treeView = new JScrollPane(tree);
		}else{
			 treeView = new JScrollPane(new JLabel("Seleziona un file"));
		}
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640, 480);
		frame.add(treeView);
		frame.setVisible(true);
	}
}
