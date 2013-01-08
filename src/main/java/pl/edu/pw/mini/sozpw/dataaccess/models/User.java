package pl.edu.pw.mini.sozpw.dataaccess.models;

import java.sql.Timestamp;


public class User implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer idUsers;
	private String username;
	private String email;
	private String password;
	private boolean isActive;
	private Timestamp createDate;
	private Timestamp lastLoginDate;
	private String phone;
	private String salt;
	
	public User() {
	
	}
	
	public Integer getIdUsers() {
		return this.idUsers;
	}
	
	public void setIdUsers(Integer idUsers) {
		this.idUsers = idUsers;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPhone() {
		return this.phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public boolean getIsActive() {
		return this.isActive;
	}
	
	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public Timestamp getCreateDate() {
		return this.createDate;
	}
	
	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}
	
	public Timestamp getLastLoginDate() {
		return this.lastLoginDate;
	}
	
	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
	public String getSalt() {
		return this.salt;
	}
	
	public void setSalt(String salt) {
		this.salt = salt;
	}
}
