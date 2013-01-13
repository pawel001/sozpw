package pl.edu.pw.mini.sozpw.dataaccess.models;

public class Group implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private boolean isPrivate;
	private boolean isGeneric;
	private String name;
	private Integer ownerId;
	private Integer groupId;
	
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	public boolean getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	public boolean getIsGeneric() {
		return isGeneric;
	}
	public void setIsGeneric(boolean isGeneric) {
		this.isGeneric = isGeneric;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
