package pl.edu.pw.mini.sozpw.webinterface.services;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("groupservice")
public interface GroupService extends RemoteService {
	
	List<String> getUserGroups(String username);
	
	Boolean addGroup(String user, String groupName);
	
	void removeGroup(String groupName);
	
	void assignUserToGroups(String username, List<String> groups);

}
