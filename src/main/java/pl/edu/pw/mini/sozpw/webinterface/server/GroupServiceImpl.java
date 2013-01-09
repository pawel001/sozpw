package pl.edu.pw.mini.sozpw.webinterface.server;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pw.mini.sozpw.dataaccess.model.Model;
import pl.edu.pw.mini.sozpw.dataaccess.model.ModelImpl;
import pl.edu.pw.mini.sozpw.webinterface.services.GroupService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class GroupServiceImpl extends RemoteServiceServlet implements GroupService {
	
	Model model = new ModelImpl();

	@Override
	public List<String> getUserGroups(String username) {
		List<String> result = model.getUserGropus(username);
		if(result == null){
			result = new ArrayList<String>();
		}
		return result;
	}

	@Override
	public Boolean addGroup(String user, String groupName) {
		return model.addGroup(user, groupName);
	}

	@Override
	public void removeGroup(String groupName) {
		model.removeGroup(groupName);
	}

	@Override
	public void assignUserToGroups(String username, List<String> groups) {
		model.assignUserToGroups(username, groups);
	}

}
