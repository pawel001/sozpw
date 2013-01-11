package pl.edu.pw.mini.sozpw.dataaccess.model;

import java.util.List;

import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Comment;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Note;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.User;

public interface Model {

	//User functions
	User loginUser(String username, String pass);

	String registerUser(String username, String pass, String mail);

	boolean confirmRegistration(String username, String key);

	boolean changePassword(String username, String oldPass, String newPass);

	//Notes functions
	List<Note> getNotes(String username);

	Integer addNote(Note note, byte[] attachment);

	boolean addComment(int noteId, Comment comment);

	boolean editNote(Note note, byte[] attachment);

	byte[] getAttachment(int noteId);

	boolean deleteNote(Integer noteId);
	
	//Groups functions
	List<String> getSubscribedGroups(String username);
	
	List<String> getSubscribingUsers(String groupName);
	
	List<String> getCreatedGroups(String username);

	boolean createGroup(String groupName, String username);
	
	boolean getGroupVisibility(String groupName);
	
	void setGroupVisibility(String groupName, boolean isPrivate);
	
	boolean addUser(String groupName, String username);
	
	boolean removeUser(String groupName, String username);

	void removeGroup(String groupName);

	//Helper functions
	List<String> getGroupsHints(String query, int count);

	List<String> getUsersHints(String query, int count);

	List<String> getUsersAndGroupsHints(String query, int count);

}
