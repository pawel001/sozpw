package pl.edu.pw.mini.sozpw.webinterface.server;

import javax.servlet.http.HttpServletRequest;

import pl.edu.pw.mini.sozpw.dataaccess.model.Model;
import pl.edu.pw.mini.sozpw.dataaccess.model.ModelImpl;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.User;
import pl.edu.pw.mini.sozpw.webinterface.services.LoginService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
	
	Model model = new ModelImpl();

	@Override
	public String login(String username, String pass) {
		
		User user = model.loginUser(username, pass);
		
		if (user != null) {
			addUserToSession(username);
			return user.getUsername();
		}
		
		return null;
	}

	@Override
	public String getUserFromSession() {
		HttpServletRequest request = this.getThreadLocalRequest();
		String username = (String) request.getSession().getAttribute("username");
		return username;
	}

	@Override
	public void logout() {
		removeUserFromSession();
		return;
	}
	
	@Override
	public boolean register(String username, String pass, String mail) {
		String registerKey = model.registerUser(username, pass, mail);
		
		if(registerKey != null){
			RegisterMailSender.sendMail(username, mail, registerKey);
			return true;
		}
		
		return false;
	}

	private void addUserToSession(String username) {
		HttpServletRequest request = this.getThreadLocalRequest();
		request.getSession().setAttribute("username", username);
	}

	private void removeUserFromSession() {
		HttpServletRequest request = this.getThreadLocalRequest();
		request.getSession().invalidate();
	}

	@Override
	public boolean changePassword(String oldPass, String newPass) {
		return model.changePassword(oldPass, newPass);
	}

}
