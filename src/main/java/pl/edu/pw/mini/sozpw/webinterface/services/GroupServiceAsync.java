package pl.edu.pw.mini.sozpw.webinterface.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GroupServiceAsync {

	void getSubscribedGroups(String username,
			AsyncCallback<List<String>> callback);

	void getCreatedGroups(String username, AsyncCallback<List<String>> callback);

	void createGroup(String groupName, String username,
			AsyncCallback<Boolean> callback);

	void removeGroup(String groupName, AsyncCallback<Void> callback);

	void assignUserToGroups(String username, List<String> newGroups,
			AsyncCallback<Void> callback);

	void assignUsersToGroup(List<String> users, String groupName,
			AsyncCallback<Void> callback);

	void getSubscribingUsers(String groupName,
			AsyncCallback<List<String>> callback);

	void setGroupVisibility(String groupName, boolean isPrivate, AsyncCallback<Void> callback);

	void getGroupVisibility(String groupName,AsyncCallback<Boolean> callback);

}
