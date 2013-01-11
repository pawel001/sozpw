package pl.edu.pw.mini.sozpw.webinterface.ui.elements;

import pl.edu.pw.mini.sozpw.webinterface.services.GroupService;
import pl.edu.pw.mini.sozpw.webinterface.services.GroupServiceAsync;
import pl.edu.pw.mini.sozpw.webinterface.ui.dialogs.GroupDialog;
import pl.edu.pw.mini.sozpw.webinterface.ui.dialogs.StyledDialogBox;
import pl.edu.pw.mini.sozpw.webinterface.ui.pages.GroupPage;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class GroupWidget extends GroupWidgetGenerated {
	
	GroupServiceAsync groupService = (GroupServiceAsync) GWT
			.create(GroupService.class);
	final GroupPage groupPage;

	public GroupWidget(final String groupName, GroupPage groupPage) {
		
		this.groupPage = groupPage;

		getGroupName().setText(groupName);

		getDeleteButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				GroupWidget.this.groupPage.removeGroup(GroupWidget.this
						.getGroupName().getText());
			}
		});

		getEditButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				groupService.getGroupVisibility(groupName, new AsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean result) {
						StyledDialogBox sdb = new StyledDialogBox(groupName);
						GroupDialog gd = new GroupDialog(groupName, result, sdb);
						sdb.add(gd);
						sdb.center();
						gd.initFields();
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("getGroupVisibility() failed");
					}
				});
				
			}
		});
	}

}
