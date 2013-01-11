package pl.edu.pw.mini.sozpw.webinterface.ui.elements;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Button;

public class GroupWidgetGenerated extends Composite {
	private Button deleteButton;
	private Label groupName;
	private Button editButton;

	public GroupWidgetGenerated() {
		
		FlowPanel flowPanel = new FlowPanel();
		initWidget(flowPanel);
		
		FlexTable flexTable = new FlexTable();
		flexTable.setStyleName("gwt-Widget");
		flowPanel.add(flexTable);
		flexTable.setWidth("");
		
		groupName = new Label("");
		groupName.setStyleName("get-GroupwWidgetLabel");
		flexTable.setWidget(0, 0, groupName);
		groupName.setSize("252px", "18px");
		
		editButton = new Button("New button");
		editButton.setText("");
		editButton.setStyleName("gwt-editButton");
		flexTable.setWidget(0, 1, editButton);
		editButton.setSize("18px", "18px");
		
		deleteButton = new Button("Usuń");
		deleteButton.setText("");
		deleteButton.setStyleName("gwt-deleteButton");
		flexTable.setWidget(0, 2, deleteButton);
		deleteButton.setSize("18px", "18px");
	}

	public Button getDeleteButton() {
		return deleteButton;
	}
	public Label getGroupName() {
		return groupName;
	}
	public Button getEditButton() {
		return editButton;
	}
}
