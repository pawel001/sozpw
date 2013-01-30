package pl.edu.pw.mini.sozpw.webinterface.ui.pages;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class GroupPageGenerated extends Composite {
	private FlowPanel userGroupsPanel;
	private Button addGroupButton;
	private Button confirmSubButton;
	private TextBox newGroupTextBox;
	private Label errorLabel;

	public GroupPageGenerated() {
		
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.setStyleName("gwt-GroupPage");
		initWidget(flowPanel);
		flowPanel.setSize("100%", "100%");
		
		FlowPanel flowPanel_1 = new FlowPanel();
		flowPanel.add(flowPanel_1);
		flowPanel_1.setHeight("");
		flowPanel_1.setStyleName("gwt-insidePanel");
		
		Label lblGrupyDoKtrych = new Label("Subskrybowane grupy");
		lblGrupyDoKtrych.setStyleName("gwt-GroupLabel");
		flowPanel_1.add(lblGrupyDoKtrych);
		
		FlexTable flexTable = new FlexTable();
		flexTable.setStyleName("gwt-table");
		flowPanel_1.add(flexTable);
		
		TextBox currentGroupsTextBox = new TextBox();
		currentGroupsTextBox.setStyleName("gwt-TextArea300");
		currentGroupsTextBox.getElement().setId("currentGroupsTextBox");
		flexTable.setWidget(0, 0, currentGroupsTextBox);
		
		confirmSubButton = new Button("New button");
		confirmSubButton.setStyleName("gwt-ButtonCustom");
		confirmSubButton.setText("Zatwierdź");
		flexTable.setWidget(1, 0, confirmSubButton);
		confirmSubButton.setWidth("100px");
		flexTable.getCellFormatter().setWidth(1, 0, "");
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		Label lblTwojeGrupy = new Label("Grupy, które utworzyłeś");
		lblTwojeGrupy.setStyleName("gwt-GroupLabel");
		flowPanel_1.add(lblTwojeGrupy);
		
		userGroupsPanel = new FlowPanel();
		flowPanel_1.add(userGroupsPanel);
		
		FlexTable flexTable_1 = new FlexTable();
		flowPanel_1.add(flexTable_1);
		
		newGroupTextBox = new TextBox();
		newGroupTextBox.setStyleName("gwt-TextArea300");
		flexTable_1.setWidget(0, 0, newGroupTextBox);
		flexTable_1.getCellFormatter().setWidth(1, 0, "");
		flexTable_1.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		addGroupButton = new Button("New button");
		addGroupButton.setStyleName("gwt-ButtonCustom");
		addGroupButton.setText("Dodaj");
		flexTable_1.setWidget(1, 0, addGroupButton);
		addGroupButton.setWidth("100px");
		
		errorLabel = new Label("");
		errorLabel.setStyleName("gwt-LabelErr");
		flexTable_1.setWidget(2, 0, errorLabel);
	}

	public FlowPanel getUserGroupsPanel() {
		return userGroupsPanel;
	}
	public Button getAddGroupButton() {
		return addGroupButton;
	}
	public Button getConfirmSubButton() {
		return confirmSubButton;
	}
	public TextBox getNewGroupTextBox() {
		return newGroupTextBox;
	}
	public Label getErrorLabel() {
		return errorLabel;
	}
}
