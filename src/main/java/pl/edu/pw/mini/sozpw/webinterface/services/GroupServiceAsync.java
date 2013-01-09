package pl.edu.pw.mini.sozpw.webinterface.services;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GroupServiceAsync {

	void getUserGroups(String username, AsyncCallback<List<String>> callback);

	void addGroup(String user, String groupName, AsyncCallback<Boolean> callback);

	void removeGroup(String groupName, AsyncCallback<Void> callback);

	void assignUserToGroups(String username, List<String> groups,
			AsyncCallback<Void> callback);

}
