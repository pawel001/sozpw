package pl.edu.pw.mini.sozpw.dataaccess.models;

public class LogInSession implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer sessionId;
	private String key;
	private Integer userId;
	public Integer getSessionId() {
		return sessionId;
	}
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
