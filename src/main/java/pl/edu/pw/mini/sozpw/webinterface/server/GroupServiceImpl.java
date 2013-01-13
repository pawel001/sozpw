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
	public List<String> getSubscribedGroups(String username) {
		List<String> result = model.getSubscribedGroups(username);
		if(result == null){
			result = new ArrayList<String>();
		}
		return result;
	}

	@Override
	public Boolean createGroup(String groupName, String username) {
		return model.createGroup(groupName, username);
	}

	@Override
	public void removeGroup(String groupName) {
		model.removeGroup(groupName);
	}

	@Override
	public void assignUserToGroups(String username, List<String> newGroups) {
		List<String> oldGropus = model.getSubscribedGroups(username);
		if(oldGropus == null){
			oldGropus = new ArrayList<String>();
		}
		List<String> summedGroups = new ArrayList<String>();
		
		for(String group : newGroups){
			if(!summedGroups.contains(group)){
				summedGroups.add(group);
			}
		}
		
		for(String group : oldGropus){
			if(!summedGroups.contains(group)){
				summedGroups.add(group);
			}
		}
		
		for(String group : summedGroups){
			if(oldGropus.contains(group) && !newGroups.contains(group)){
				model.removeUser(group, username);
			}
			
			if(!oldGropus.contains(group) && newGroups.contains(group)){
				model.addUser(group, username);
			}
		}
	}

	@Override
	public void assignUsersToGroup(List<String> newUsers, String groupName) {
		List<String> oldUsers = model.getSubscribingUsers(groupName);
		if(oldUsers == null){
			oldUsers = new ArrayList<String>();
		}
		List<String> summedUsers = new ArrayList<String>();
		
		for(String user : newUsers){
			if(!summedUsers.contains(user)){
				summedUsers.add(user);
			}
		}
		
		for(String user : oldUsers){
			if(!summedUsers.contains(user)){
				summedUsers.add(user);
			}
		}
		
		for(String user : summedUsers){
			if(oldUsers.contains(user) && !newUsers.contains(user)){
				model.removeUser(groupName, user);
			}
			
			if(!oldUsers.contains(user) && newUsers.contains(user)){
				model.addUser(groupName, user);
			}
		}
		
	}
	
	@Override
	public List<String> getCreatedGroups(String username) {
		return model.getCreatedGroups(username);
	}

	@Override
	public void setGroupVisibility(String groupName, boolean isPrivate) {
		model.setGroupVisibility(groupName, isPrivate);
	}

	@Override
	public List<String> getSubscribingUsers(String groupName) {
		return model.getSubscribingUsers(groupName);
	}

	@Override
	public Boolean getGroupVisibility(String groupName) {
		return model.getGroupVisibility(groupName);
	}

}
