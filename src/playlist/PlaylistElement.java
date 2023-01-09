package playlist;

import java.io.File;

/**
 * Un generico elemento della playlist.
 * 
 */
public class PlaylistElement {

	private File file;
	private int startTime;
	private int endTime;
	
	/**
	 * L'elemento della playlist con i suoi parametri.
	 * @param id L'id dell'elemento
	 * @param startTime Il tempo di inizio dell'elemento
	 * @param endTime Il tempo di fine dell'elemento
	 */
	public PlaylistElement(File file, int startTime, int endTime) {
		super();
		this.file = file;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public int getId() {
		return file.hashCode();
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public int getStartTime() {
		return startTime;
	}
	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	public int getEndTime() {
		return endTime;
	}
	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
}
