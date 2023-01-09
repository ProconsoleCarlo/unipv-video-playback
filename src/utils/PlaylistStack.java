package utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Permette di gestire playlist nidificate
 * utilizzando la logica dello stack
 *
 */
public class PlaylistStack {

	private ArrayList<Integer> indexStack = new ArrayList<Integer>();
	private ArrayList<File> playlistFileStack = new ArrayList<File>();
	private int stackIndex = 0;
	private int stackPlatlistIndex = 0;
	private boolean isEmpty = true;
	
	public boolean isEmpty(){
		return this.isEmpty;
	}
	
	public int size(){
		return stackIndex;
	}
	
	public void clear() {
		playlistFileStack.clear();
		indexStack.clear();
		stackIndex=0;
		stackPlatlistIndex=0;
		isEmpty = true;
	}
	
	/**
	 * salva nello stack il file playlistFile, contenuto in una playlist con indice = index
	 * @param playlistFile
	 * @param index
	 * @return se il salvataggio è andato a buon fine
	 */
	public boolean push(File playlistFile, int index){
		while(playlistFileStack.size() > stackIndex && indexStack.size() > stackPlatlistIndex){
			playlistFileStack.remove(stackIndex);
			indexStack.remove(stackPlatlistIndex);
		}
		if(!playlistFileStack.contains(playlistFile)){
			isEmpty = false;
			playlistFileStack.add(stackIndex, new File(playlistFile.toString()));
			indexStack.add(stackIndex, index);
			stackIndex++;
			stackPlatlistIndex++;
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * estrae la playlist puntata dallo stack
	 * sposta puntatore.
	 * @return playlist estratta
	 */
	public File popPlaylist() {
		stackPlatlistIndex--;
		if (stackPlatlistIndex < 0) {
			return null;
		} else {
			return playlistFileStack.get(stackPlatlistIndex);
		}
	}
	
	/**
	 * @return indice del puntatore.
	 */
	public int popIndex() {
		if (stackIndex == 0) {
			return -1;
		} else {
			stackIndex--;
			if (stackIndex == 0) {
				isEmpty = true;
			}
			return indexStack.get(stackIndex);
		}
	}
}
