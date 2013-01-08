package pl.edu.pw.mini.sozpw.dataaccess.models;

public class Comment implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	
	public Integer commentId;
	public Integer note_id;
	public String text;
	public Integer user_id;
	
	public Integer getCommentId() {
		return commentId;
	}
	
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	
	public Integer getNote_id() {
		return note_id;
	}
	
	public void setNote_id(Integer note_id) {
		this.note_id = note_id;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public Integer getUser_id() {
		return user_id;
	}
	
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
}
