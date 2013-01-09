package pl.edu.pw.mini.sozpw.webinterface.ui.elements;

import pl.edu.pw.mini.sozpw.webinterface.ui.pages.GroupPage;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;

public class GroupWidget extends GroupWidgetGenerated {

	final GroupPage groupPage;

	public GroupWidget(String groupName, GroupPage groupPage) {
		this.groupPage = groupPage;

		getGroupName().setText(groupName);

		getDeleteButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				GroupWidget.this.groupPage.removeGroup(GroupWidget.this
						.getGroupName().getText());
			}
		});
	}

}
