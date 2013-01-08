package pl.edu.pw.mini.sozpw.dataaccess.models;

import java.sql.Timestamp;
import java.util.List;
/** 
 * Model class for note
 */
public class Note implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer noteId;
	private boolean isPrivate;
	private Timestamp createDate;
	private Timestamp expirationDate;
	private boolean isAddressedToGroup;
	private Integer group_id;
	private Integer user_id;
	private Integer addressedUser_id;
	private Integer cathegory_id;
	private String text;
	private String topic;
	private List<Point> points;
	
	public List<Point> getPoints() {
		return points;
	}
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	public Integer getNoteId() {
		return noteId;
	}
	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}
	public boolean getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	public Timestamp getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	public Timestamp getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(Timestamp expirationDate) {
		this.expirationDate = expirationDate;
	}
	public boolean getIsAddressedToGroup() {
		return isAddressedToGroup;
	}
	public void setIsAddressedToGroup(boolean isAddressedToGroup) {
		this.isAddressedToGroup = isAddressedToGroup;
	}
	public Integer getGroup_id() {
		return group_id;
	}
	public void setGroup_id(Integer group_id) {
		this.group_id = group_id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getAddressedUser_id() {
		return addressedUser_id;
	}
	public void setAddressedUser_id(Integer addressedUser_id) {
		this.addressedUser_id = addressedUser_id;
	}
	public Integer getCathegory_id() {
		return cathegory_id;
	}
	public void setCathegory_id(Integer cathegory_id) {
		this.cathegory_id = cathegory_id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
}
