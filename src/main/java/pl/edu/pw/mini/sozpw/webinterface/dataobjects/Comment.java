package pl.edu.pw.mini.sozpw.webinterface.dataobjects;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Comment implements IsSerializable{

	private String username;
	private String comment;
	private long date;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

}
