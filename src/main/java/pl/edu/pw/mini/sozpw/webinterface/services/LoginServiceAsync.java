package pl.edu.pw.mini.sozpw.webinterface.services;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void login(String username, String password, AsyncCallback<String> callback);

	void getUserFromSession(AsyncCallback<String> callback);

	void logout(AsyncCallback<Void> callback);

	void register(String username, String pass, String mail, AsyncCallback<Boolean> callback);

	void changePassword(String oldPass, String newPass,
			AsyncCallback<Boolean> callback);

}
