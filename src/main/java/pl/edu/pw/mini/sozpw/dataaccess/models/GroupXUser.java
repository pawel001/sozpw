package pl.edu.pw.mini.sozpw.dataaccess.models;

public class GroupXUser implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private Integer groupId;
	private Integer groupXUserId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getGroupXUserId() {
		return groupXUserId;
	}
	public void setGroupXUserId(Integer groupXUserId) {
		this.groupXUserId = groupXUserId;
	}
}
