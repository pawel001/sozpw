package pl.edu.pw.mini.sozpw.webinterface.dataobjects;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Note implements IsSerializable {

	public static final int NO_ID = -1;

	int id;

	double latitude;
	double longitude;

	String username;
	String topic;
	String content;

	Category category;
	List<String> dedicationList;
	long createDate;
	long expiryDate;
	String filename;

	List<Comment> comments;

	public Note() {
		this.id = NO_ID;
		dedicationList = new ArrayList<String>();
		comments = new ArrayList<Comment>();
	}

	public Note(String topic, String content) {
		this();
		this.topic = topic;
		this.content = content;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<String> getDedicationList() {
		return dedicationList;
	}

	public void setDedicationList(List<String> dedicationList) {
		this.dedicationList = dedicationList;
	}

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public long getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(long expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setAccordingToNote(Note note) {
		setId(note.getId());
		setLatitude(note.getLatitude());
		setLongitude(note.getLongitude());
		setUsername(note.getUsername());
		setTopic(note.getTopic());
		setContent(note.getContent());
		setCategory(note.getCategory());
		setDedicationList(note.getDedicationList());
		setCreateDate(note.getCreateDate());
		setExpiryDate(note.getExpiryDate());
		setFilename(note.getFilename());
		setComments(note.getComments());
	}
}
