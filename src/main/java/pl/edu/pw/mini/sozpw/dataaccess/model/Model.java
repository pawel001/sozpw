package pl.edu.pw.mini.sozpw.dataaccess.model;

import java.util.List;

import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Comment;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Note;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.User;

public interface Model {

	User loginUser(String username, String pass);

	String registerUser(String username, String pass, String mail);

	boolean confirmRegistration(String key);

	List<Note> getNotes(String user);

	Integer addNote(Note note, byte[] attachment);

	boolean addComment(int noteId, Comment comment);

	boolean editNote(Note note, byte[] attachment);

	boolean deleteNote(Integer noteId);

	List<String> getDedicationHints(String query, int count);
	
	List<String> getGroupsHints(String query, int count);

	List<String> getUserGropus(String user);

	boolean addGroup(String user, String groupName);

	void removeGroup(String groupName);

	void assignUserToGroups(String username, List<String> groups);
	
	boolean changePassword(String oldPass, String newPass);
	
	byte[] getAttachment(int noteId);

}
