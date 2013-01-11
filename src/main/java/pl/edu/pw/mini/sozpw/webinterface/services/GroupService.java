package pl.edu.pw.mini.sozpw.webinterface.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("groupservice")
public interface GroupService extends RemoteService {
	
	void assignUserToGroups(String username, List<String> newGroups);
	
	void assignUsersToGroup(List<String> users, String groupName);

	List<String> getSubscribedGroups(String username);
	
	List<String> getSubscribingUsers(String groupName);

	List<String> getCreatedGroups(String username);

	Boolean createGroup(String groupName, String username);
	
	Boolean getGroupVisibility(String groupName);

	void setGroupVisibility(String groupName, boolean isPrivate);

	void removeGroup(String groupName);

}
