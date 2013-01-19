package pl.edu.pw.mini.sozpw.webinterface.ui.pages;

import pl.edu.pw.mini.sozpw.webinterface.services.LoginService;
import pl.edu.pw.mini.sozpw.webinterface.services.LoginServiceAsync;
import pl.edu.pw.mini.sozpw.webinterface.ui.dialogs.InfoDialog;
import pl.edu.pw.mini.sozpw.webinterface.ui.dialogs.StyledDialogBox;
import pl.edu.pw.mini.sozpw.webinterface.utils.Validator;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SettingsPage extends SettingsPageGenerated {

	public SettingsPage(final String username) {

		getChangePassButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getErrorLabel().setText("");
				String oldPass = getOldPassTextBox().getValue();
				String newPass = getNewPassTextBox().getValue();
				String newPassConf = getNewPassConfirmTextBox().getValue();

				if (Validator.verifyPass(newPass, newPassConf)) {

					LoginServiceAsync loginService = (LoginServiceAsync) GWT
							.create(LoginService.class);

					loginService.changePassword(username, oldPass, newPass,
							new AsyncCallback<Boolean>() {

								@Override
								public void onSuccess(Boolean result) {
									if (result) {
										getOldPassTextBox().setText("");
										getNewPassTextBox().setText("");
										getNewPassConfirmTextBox().setText("");
										StyledDialogBox sdb = new StyledDialogBox("Potwierdzenie");
										sdb.add(new InfoDialog(sdb, "Hasło zostało zmienione"));
										sdb.center();
									} else {
										getErrorLabel().setText(
												"Nie można zmienić hasła");
									}
								}

								@Override
								public void onFailure(Throwable caught) {
									Window.alert("changePassword() failed");
								}
							});

				} else {
					getErrorLabel().setText("Niepoprawne nowe hasło");
				}

			}
		});

	}

}
