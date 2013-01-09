package pl.edu.pw.mini.sozpw.webinterface.services;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("loginservice")
public interface LoginService extends RemoteService {

	String login(String username, String password);

	String getUserFromSession();

	void logout();
	
	boolean register(String username, String pass, String mail);
	
	boolean changePassword(String oldPass, String newPass);

}
