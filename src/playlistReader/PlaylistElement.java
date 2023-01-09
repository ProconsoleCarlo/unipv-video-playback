package playlistReader;
/**
 * Un elemento della playlist con i suoi parametri
 *
 */
public class PlaylistElement {

	private int index;
	private int id;
	private int startTime;
	private int endTime;
	/**
	 * L'elemento della playlist con i suoi parametri
	 * @param index La posizione dell'elemento nella playlist
	 * @param id L'id dell'elemento
	 * @param startTime Il tempo di inizio dell'elemento
	 * @param endTime Il tempo di fine dell'elemento
	 */
	public PlaylistElement(int index, int id, int startTime, int endTime) {
		super();
		this.index = index;
		this.id = id;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
