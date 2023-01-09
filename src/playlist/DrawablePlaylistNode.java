package playlist;

import java.io.File;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

import settings.MediaPlayerSettings;
import utils.PlaylistStack;

public class DrawablePlaylistNode {
	
	private ArrayList<PlaylistElement> playlist = new ArrayList<PlaylistElement>();
	private File playlistStartedFile;
	private int i = 0;
	private PlaylistReaderWriter playlistReaderWriter;
	private PlaylistStack stackDraw = new PlaylistStack();
	
	private DefaultMutableTreeNode previousPlaylistNode = null;
	private DefaultMutableTreeNode videoNode = null;
	private DefaultMutableTreeNode top;
	
	public DrawablePlaylistNode(IPlaylistFileInterpreter interpreter) {
		super();
		playlistReaderWriter = new PlaylistReaderWriter(interpreter);
	}

	private ArrayList<PlaylistElement> playablePlaylist = new  ArrayList<PlaylistElement>();
	
	/**
	 * Restituisce la playlist eseguibile contenente solo file video creata col metodo drawTree.
	 * 
	 * @return la playlist eseguibile con {@link PlaylistManager}
	 */
	public ArrayList<PlaylistElement> getPlayablePlaylist() {
		return playablePlaylist;
	}

	/**
	 * Aggiunge come nodi tutti i figli descriti in un file ad un nodo radice e 
	 * crea un ArrayList<PlaylistElement> contenente i soli elementi video.
	 * (ottenibile col metodo getPlayablePlaylist)
	 * 
	 * @param playlistFile il file della playlist da visualizzare e riprodurre
	 * @param rootNode il nodo radice che si vuole visualizzare e riprodurre
	 * @return rootNode con l'aggiunta di tutti i suoi nodi (rami e foglie)
	 */
	public DefaultMutableTreeNode drawTreeAndCreatePlaylist(File playlistFile, DefaultMutableTreeNode rootNode){
		playablePlaylist.clear();
		this.top = rootNode;
		previousPlaylistNode = new DefaultMutableTreeNode(playlistFile.getName());
		top.add(previousPlaylistNode);
		i=0;
		playlist.clear();
		stackDraw.clear();
		inizialize(playlistFile);
		drawPlaylist();
		return top;
	}

	/**
	 * Aggiunge come nodo un singolo elemento video ad un nodo radice e 
	 * crea un ArrayList<PlaylistElement> contenente solo quell'elemento.
	 * (ottenibile col metodo getPlayablePlaylist)
	 * 
	 * @param element l'elemento video da visualizzare e riprodurre
	 * @param rootNode il nodo radice che si vuole visualizzare e riprodurre
	 * @return rootNode con l'aggiunta del nuovo nodo (foglia)
	 */
	public DefaultMutableTreeNode drawTreeAndCreatePlaylist(PlaylistElement element, DefaultMutableTreeNode rootNode){
		playablePlaylist.clear();
		this.top = rootNode;
		videoNode = new DefaultMutableTreeNode(element.getFile().getName() + " " + element.getStartTime() + " " + element.getEndTime(), false);
		top.add(videoNode);
		playablePlaylist.add(element);
		return top;
	}
	
	private boolean inizialize(File playlistFile) {
		this.playlistStartedFile = playlistFile;
		if(playlistFile.exists()){
			this.playlist = playlistReaderWriter.readPlaylistContent(playlistFile);
		}
		return !playlist.isEmpty();
	}

	private boolean inizialize(File playlistFile, DefaultMutableTreeNode playlistNode) {
		this.previousPlaylistNode.add(playlistNode);
		this.previousPlaylistNode = playlistNode;
		return inizialize(playlistFile);
	}
	
	private void drawPlaylist() {
		if (i<playlist.size()) {
			File file = playlist.get(i).getFile();
			if (file.getName().endsWith(MediaPlayerSettings.getSettings().getPlaylistFileExtension())) { 
				//Playlist
				DefaultMutableTreeNode playlistNode = new DefaultMutableTreeNode(playlist.get(i).getFile().getName());
				if (stackDraw.push(playlistStartedFile, i)) { 
					//Playlist - nidificata
					this.playlist.clear();
					this.i = 0;
					if(!inizialize(file, playlistNode)){ 
						//Playlist - vuota
						this.i++;
					}
				}else{ 
					//Playlist - loop
					DefaultMutableTreeNode loopNode = new DefaultMutableTreeNode("LOOP - " + playlist.get(i).getFile().getName());
					this.previousPlaylistNode.add(loopNode);
					this.i++;
				}
				drawPlaylist();
			}else { 
				//File video
				videoNode = new DefaultMutableTreeNode(playlist.get(i).getFile().getName() + " " + playlist.get(i).getStartTime() + " " + playlist.get(i).getEndTime(), false);
				playablePlaylist.add(playlist.get(i));
				this.previousPlaylistNode.add(videoNode);
				this.i++;
				drawPlaylist();
			}
		} else {
			ritornoNodoPrecedente();
		}
	}
	
	private void ritornoNodoPrecedente() { 
		int index = stackDraw.popIndex();
		if(index != -1){ 
			this.playlist.clear();
			inizialize(stackDraw.popPlaylist());
			previousPlaylistNode = (DefaultMutableTreeNode) previousPlaylistNode.getParent();
			if (index+1 < playlist.size()) {
				this.i = index+1;
				drawPlaylist();
			}else{
				ritornoNodoPrecedente();
			}
		//else: Fine albero
		}
	}
}
