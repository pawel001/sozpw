package pl.edu.pw.mini.sozpw.webinterface.ui.dialogs;

import java.util.ArrayList;
import java.util.List;

import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Dedicated;
import pl.edu.pw.mini.sozpw.webinterface.services.GroupService;
import pl.edu.pw.mini.sozpw.webinterface.services.GroupServiceAsync;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DialogBox;

public class GroupDialog extends GroupDialogGenerated {

	GroupServiceAsync groupService = (GroupServiceAsync) GWT
			.create(GroupService.class);

	final String groupName;

	public GroupDialog(String groupName, boolean isPrivate,
			final DialogBox parent) {
		this.groupName = groupName;
		getPublicCheckBox().setValue(isPrivate);

		getPublicCheckBox().addValueChangeHandler(
				new ValueChangeHandler<Boolean>() {

					@Override
					public void onValueChange(ValueChangeEvent<Boolean> event) {
						groupService.setGroupVisibility(
								GroupDialog.this.groupName, getPublicCheckBox()
										.getValue(), new AsyncCallback<Void>() {

									@Override
									public void onSuccess(Void result) {
										// TODO Auto-generated method stub
									}

									@Override
									public void onFailure(Throwable caught) {
										Window.alert("trueGroupVisibility() failed");
									}
								});
					}
				});

		getCancelButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				parent.hide();
			}
		});

		getSaveButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				JsArray<Dedicated> usersJS = getSelectedTokens();
				List<String> users = new ArrayList<String>();
				for (int i = 0; i < usersJS.length(); i++) {
					users.add(usersJS.get(i).getName());
				}

				groupService.assignUsersToGroup(users,
						GroupDialog.this.groupName, new AsyncCallback<Void>() {

							@Override
							public void onSuccess(Void result) {
								// TODO Auto-generated method stub
							}

							@Override
							public void onFailure(Throwable caught) {
								Window.alert("changeGroupVisibility() failed");
							}
						});
				parent.hide();
			}
		});

	}

	public native void setTokenInput() /*-{
		scriptaddress = $wnd.location.protocol + "//" + $wnd.location.host
				+ "/dedicationQuery?type=users";
		$wnd.$("#groupSubscribersTextBox").tokenInput(scriptaddress, {
			theme : "facebook",
			hintText : "Wpisz nazwę użytkownika",
			noResultsText : "Brak rezultatów",
			searchingText : "Szukam..."
		});
	}-*/;

	public native JsArray<Dedicated> getSelectedTokens() /*-{
		return $wnd.$("#groupSubscribersTextBox").tokenInput("get");
	}-*/;

	public native void addToken(String n) /*-{
		$wnd.$("#groupSubscribersTextBox").tokenInput("add", {
			"id" : 0,
			"name" : n
		});
	}-*/;

	public void initFields() {
		setTokenInput();

		groupService.getSubscribingUsers(groupName,
				new AsyncCallback<List<String>>() {

					@Override
					public void onSuccess(List<String> result) {
						if (result != null) {
							for (String user : result) {
								addToken(user);
							}
						}
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("getSubscribingUsers() failed");
					}
				});
	}
}
