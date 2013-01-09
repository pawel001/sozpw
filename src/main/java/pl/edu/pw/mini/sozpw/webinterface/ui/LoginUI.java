package pl.edu.pw.mini.sozpw.webinterface.ui;

import pl.edu.pw.mini.sozpw.webinterface.services.LoginService;
import pl.edu.pw.mini.sozpw.webinterface.services.LoginServiceAsync;
import pl.edu.pw.mini.sozpw.webinterface.ui.dialogs.RegisterDialog;
import pl.edu.pw.mini.sozpw.webinterface.ui.dialogs.StyledDialogBox;
import pl.edu.pw.mini.sozpw.webinterface.utils.Validator;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class LoginUI extends LoginUIGenerated {

	LoginServiceAsync loginService;

	public LoginUI() {
		loginService = (LoginServiceAsync) GWT.create(LoginService.class);

		getLoginButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				login();
			}
		});
		
		getLoginPass().addKeyPressHandler(new KeyPressHandler() {
			
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getNativeEvent().getKeyCode() == KeyCodes.KEY_ENTER){
					login();
				}
			}
		});

		getRegisterButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				register();
			}
		});
		
	}
	
	private void login(){
		String username = getLoginUsername().getText();
		String password = getLoginPass().getText();

		getLoginUsername().setText("");
		getLoginPass().setText("");
		getLoginLabel().setText("");

		loginService.login(username, password, new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				if (result != null) {
					RootPanel.get("mp_page_content_center").clear();
					RootPanel.get("mp_page_content_center").add(new MainUI(result));
					Window.Location.reload();
				} else {
					getLoginLabel().setText("Niepoprawny login lub hasło");
				}
			}

			@Override
			public void onFailure(Throwable caught) {

				Window.alert("RPC loginService.login() failed.");

			}
		});
	}
	
	private void register(){
		final String username = getRegisterUsername().getText();
		String pass = getRegisterPass().getText();
		String passConf = getRegisterPassConf().getText();
		String mail = getRegisterMail().getText();

		if (!Validator.verifyName(username)) {
			getRegisterLabel().setText("Niepoprawna nazwa użytkownika");
			return;
		}

		if (!Validator.verifyPass(pass, passConf)) {
			getRegisterLabel().setText("Niepoprawne/Niezgodne hasła");
			return;
		}

		if (!Validator.verifyMail(mail)) {
			getRegisterLabel().setText("Niepoprawny adres email");
			return;
		}

		getRegisterUsername().setText("");
		getRegisterPass().setText("");
		getRegisterPassConf().setText("");
		getRegisterMail().setText("");
		getRegisterLabel().setText("");
		getRegisterWaitImage().setVisible(true);

		loginService.register(username, passConf, mail, new AsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {

				if (result) {
					StyledDialogBox sdb = new StyledDialogBox("Rejestracja");
					sdb.add(new RegisterDialog(sdb));
					sdb.center();
				} else {
					getRegisterLabel().setText("Podany login już istnieje w bazie");
				}

				getRegisterWaitImage().setVisible(false);

			}

			@Override
			public void onFailure(Throwable caught) {

				Window.alert("RPC loginService.register() failed.");

			}
		});
	}

}
