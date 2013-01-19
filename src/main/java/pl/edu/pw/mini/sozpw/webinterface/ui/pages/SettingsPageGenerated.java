package pl.edu.pw.mini.sozpw.webinterface.ui.pages;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class SettingsPageGenerated extends Composite {
	private PasswordTextBox newPassTextBox;
	private PasswordTextBox newPassConfirmTextBox;
	private PasswordTextBox oldPassTextBox;
	private Label errorLabel;
	private Button changePassButton;

	public SettingsPageGenerated() {
		
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.setStyleName("gwt-SettingsPage");
		initWidget(flowPanel);
		flowPanel.setSize("100%", "100%");
		
		FlowPanel flowPanel_1 = new FlowPanel();
		flowPanel.add(flowPanel_1);
		flowPanel_1.setStyleName("gwt-insidePanel");
		
		Label lblZmieHaso = new Label("Zmień hasło");
		flowPanel_1.add(lblZmieHaso);
		lblZmieHaso.setStyleName("gwt-GroupLabel");
		
		FlexTable flexTable = new FlexTable();
		flexTable.setStyleName("gwt-table");
		flowPanel_1.add(flexTable);
		
		Label lblStareHaso = new Label("STARE HASŁO");
		lblStareHaso.setStyleName("gwt-LabelCustom");
		flexTable.setWidget(0, 0, lblStareHaso);
		
		oldPassTextBox = new PasswordTextBox();
		flexTable.setWidget(0, 1, oldPassTextBox);
		
		Label lblNoweHaso = new Label("NOWE HASŁO");
		lblNoweHaso.setStyleName("gwt-LabelCustom");
		flexTable.setWidget(1, 0, lblNoweHaso);
		
		newPassTextBox = new PasswordTextBox();
		flexTable.setWidget(1, 1, newPassTextBox);
		
		Label lblPowtrzHaso = new Label("POWTÓRZ HASŁO");
		lblPowtrzHaso.setStyleName("gwt-LabelCustom");
		flexTable.setWidget(2, 0, lblPowtrzHaso);
		flexTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		newPassConfirmTextBox = new PasswordTextBox();
		flexTable.setWidget(2, 1, newPassConfirmTextBox);
		
		changePassButton = new Button("New button");
		changePassButton.setStyleName("gwt-ButtonCustom");
		changePassButton.setText("Zmień");
		flexTable.setWidget(3, 1, changePassButton);
		flexTable.getCellFormatter().setHorizontalAlignment(3, 1, HasHorizontalAlignment.ALIGN_RIGHT);
		
		errorLabel = new Label("");
		errorLabel.setStyleName("gwt-LabelErr");
		flowPanel_1.add(errorLabel);
	}

	public PasswordTextBox getNewPassTextBox() {
		return newPassTextBox;
	}
	public PasswordTextBox getNewPassConfirmTextBox() {
		return newPassConfirmTextBox;
	}
	public PasswordTextBox getOldPassTextBox() {
		return oldPassTextBox;
	}
	public Label getErrorLabel() {
		return errorLabel;
	}
	public Button getChangePassButton() {
		return changePassButton;
	}
}
