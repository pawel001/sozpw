package pl.edu.pw.mini.sozpw.webinterface.ui;

import pl.edu.pw.mini.sozpw.webinterface.services.LoginService;
import pl.edu.pw.mini.sozpw.webinterface.services.LoginServiceAsync;
import pl.edu.pw.mini.sozpw.webinterface.ui.pages.GroupPage;
import pl.edu.pw.mini.sozpw.webinterface.ui.pages.MainPage;
import pl.edu.pw.mini.sozpw.webinterface.ui.pages.SettingsPage;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class MainUI extends MainUIGenerated {

	LoginServiceAsync loginService;
	MainPage mainPage;
	String username;

	public MainUI(String username) {

		loginService = (LoginServiceAsync) GWT.create(LoginService.class);
		mainPage = new MainPage(username);
		this.username = username;

		getUsernameLabel().setText(username);
		getContentPanel().add(mainPage);
		History.newItem("main");

		getLogoutButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				loginService.logout(new AsyncCallback<Void>() {
					@Override
					public void onSuccess(Void voidParam) {
						RootPanel.get("mp_page_content_center").clear();
						RootPanel.get("mp_page_content_center").add(new LoginUI());
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("RPC loginService.logout() failed.");
					}
				});
			}
		});

		History.addValueChangeHandler(new HistoryManager(this));
	}

	public MainPage getMainPage() {
		return mainPage;
	}

	public GroupPage getGroupPage() {
		return new GroupPage(username, mainPage);
	}
	
	public SettingsPage getSettingsPage(){
		return new SettingsPage(username);
	}

}
