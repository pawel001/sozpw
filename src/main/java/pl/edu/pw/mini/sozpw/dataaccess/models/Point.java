package pl.edu.pw.mini.sozpw.dataaccess.models;

public class Point implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer pointId;
	private float longitude;
	private float latitude;
	//private Integer note_id;
	private Note note;
	 
	public Note getNote() {
		return note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

	public Integer getPointId() {
		return pointId;
	}
	
	public void setPointId(Integer pointId) {
		this.pointId = pointId;
	}
	
	public float getLongitude() {
		return longitude;
	}
	
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	
	public float getLatitude() {
		return latitude;
	}
	
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	
	//public Integer getNote_id() {
	//	return note_id;
	//}
	
	//public void setNote_id(Integer note_id) {
	//	this.note_id = note_id;
	//}
}
