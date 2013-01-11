package pl.edu.pw.mini.sozpw.webinterface.ui.dialogs;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Button;

public class GroupDialogGenerated extends Composite {
	private CheckBox publicCheckBox;
	private TextBox usersTextBox;
	private FlexTable flexTable;
	private Button cancelButton;
	private Button saveButton;

	public GroupDialogGenerated() {
		
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.setStyleName("gwt-FilterPanel");
		initWidget(flowPanel);
		
		publicCheckBox = new CheckBox("Publiczna");
		publicCheckBox.setStyleName("gwt-LabelCustom");
		flowPanel.add(publicCheckBox);
		
		Label lblSubskrybujcyUytkownicy = new Label("Subskrybujący użytkownicy");
		lblSubskrybujcyUytkownicy.setStyleName("gwt-FilterLabel");
		flowPanel.add(lblSubskrybujcyUytkownicy);
		
		usersTextBox = new TextBox();
		usersTextBox.getElement().setId("groupSubscribersTextBox");
		flowPanel.add(usersTextBox);
		
		flexTable = new FlexTable();
		flexTable.setStyleName("gwt-FilterButtonTable");
		flowPanel.add(flexTable);
		
		cancelButton = new Button("New button");
		cancelButton.setStyleName("gwt-ButtonCustom");
		cancelButton.setText("Anuluj");
		flexTable.setWidget(0, 0, cancelButton);
		cancelButton.setSize("150px", "30px");
		
		saveButton = new Button("New button");
		saveButton.setStyleName("gwt-ButtonCustom");
		saveButton.setText("Zapisz");
		flexTable.setWidget(0, 1, saveButton);
		saveButton.setSize("150px", "30px");
	}

	public CheckBox getPublicCheckBox() {
		return publicCheckBox;
	}
	public TextBox getUsersTextBox() {
		return usersTextBox;
	}
	public Button getSaveButton() {
		return saveButton;
	}
	public Button getCancelButton() {
		return cancelButton;
	}
}
