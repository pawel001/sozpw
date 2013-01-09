package pl.edu.pw.mini.sozpw.webinterface.ui.pages;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Dedicated;
import pl.edu.pw.mini.sozpw.webinterface.services.GroupService;
import pl.edu.pw.mini.sozpw.webinterface.services.GroupServiceAsync;
import pl.edu.pw.mini.sozpw.webinterface.ui.elements.GroupWidget;
import pl.edu.pw.mini.sozpw.webinterface.utils.Validator;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GroupPage extends GroupPageGenerated {

	GroupServiceAsync groupService = (GroupServiceAsync) GWT
			.create(GroupService.class);
	final String username;

	public GroupPage(String username, final MainPage mainPage) {
		this.username = username;

		getAddGroupButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				addGroup();
			}
		});
		
		getConfirmSubButton().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				JsArray<Dedicated> groupsList = getSelectedTokens();
				List<String> groups = new ArrayList<String>();
				for (int i = 0; i < groupsList.length(); i++) {
					groups.add(groupsList.get(i).getName());
				}
				groupService.assignUserToGroups(GroupPage.this.username, groups, new AsyncCallback<Void>() {
					
					@Override
					public void onSuccess(Void result) {
						mainPage.initNotes();
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("assignUserToGroups() failed");
					}
				});
				
			}
		});
	}

	public native void setTokenInput() /*-{
		scriptaddress = $wnd.location.protocol + "//" + $wnd.location.host
				+ "/dedicationQuery?type=groups";
		$wnd.$("#currentGroupsTextBox").tokenInput(scriptaddress, {
			theme : "facebook",
			hintText : "Wpisz nazwę grupy",
			noResultsText : "Brak rezultatów",
			searchingText : "Szukam..."
		});
	}-*/;

	public native JsArray<Dedicated> getSelectedTokens() /*-{
		return $wnd.$("#currentGroupsTextBox").tokenInput("get");
	}-*/;

	public native void addToken(String n) /*-{
		$wnd.$("#currentGroupsTextBox").tokenInput("add", {
			"id" : 0,
			"name" : n
		});
	}-*/;

	public void initFields() {
		setTokenInput();

		groupService.getUserGroups(username, new AsyncCallback<List<String>>() {

			@Override
			public void onSuccess(List<String> result) {
				for (String group : result) {
					addToken(group);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("getUserGroups() failed");
			}
		});
	}

	public void addGroup() {

		final String groupName = getNewGroupTextBox().getValue();
		if (!Validator.verifyName(groupName)) {
			getErrorLabel().setText("Niepoprawna nazwa grupy");
			return;
		} else {
			getErrorLabel().setText("");
		}

		groupService.addGroup(username, getNewGroupTextBox().getValue(),
				new AsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean result) {
						if (result) {
							getUserGroupsPanel().add(
									new GroupWidget(groupName, GroupPage.this));
							getNewGroupTextBox().setText("");
						} else {
							getErrorLabel()
									.setText(
											"Nie można stworzyć grupy o podanej nazwie");
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("addGroup() failed");
					}
				});
	}

	public void removeGroup(final String groupName) {
		groupService.removeGroup(groupName, new AsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {

				int index = -1;

				for (int i = 0; i < getUserGroupsPanel().getWidgetCount(); i++) {
					GroupWidget gw = (GroupWidget) getUserGroupsPanel()
							.getWidget(i);
					if (gw.getGroupName().getText().equals(groupName)) {
						index = i;
						break;
					}
				}

				if (index > -1) {
					getUserGroupsPanel().remove(index);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("removeGroup() failed");
			}
		});
	}

}
