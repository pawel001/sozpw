package pl.edu.pw.mini.sozpw.webinterface.ui.dialogs;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Button;

public class FilterDialogGenerated extends Composite {
	private DateBox createdBeforeDateBox;
	private CheckBox entertainmentCheckBox;
	private Button cancelButton;
	private DateBox expiryAfterDateBox;
	private CheckBox scienceCheckBox;
	private DateBox expiryBeforeDateBox;
	private DateBox createdAfterDateBox;
	private Button resetButton;
	private CheckBox meetingCheckBox;
	private CheckBox noticeCheckBox;
	private Button okButton;
	private CheckBox sportCheckBox;
	private CheckBox noCategoryCheckBox;

	public FilterDialogGenerated() {
		
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.setStyleName("gwt-FilterPanel");
		initWidget(flowPanel);
		
		Label label = new Label("Pokaż notatki z kategorii:");
		label.setStyleName("gwt-FilterLabel");
		flowPanel.add(label);
		
		FlexTable flexTable = new FlexTable();
		flowPanel.add(flexTable);
		
		entertainmentCheckBox = new CheckBox("Rozrywka");
		entertainmentCheckBox.setStyleName("gwt-LabelCustom");
		flexTable.setWidget(0, 0, entertainmentCheckBox);
		
		scienceCheckBox = new CheckBox("Nauka");
		scienceCheckBox.setStyleName("gwt-LabelCustom");
		flexTable.setWidget(0, 1, scienceCheckBox);
		
		sportCheckBox = new CheckBox("Sport");
		sportCheckBox.setStyleName("gwt-LabelCustom");
		flexTable.setWidget(0, 2, sportCheckBox);
		
		meetingCheckBox = new CheckBox("Spotkanie");
		meetingCheckBox.setStyleName("gwt-LabelCustom");
		flexTable.setWidget(1, 0, meetingCheckBox);
		
		noticeCheckBox = new CheckBox("Ogłoszenie");
		noticeCheckBox.setStyleName("gwt-LabelCustom");
		flexTable.setWidget(1, 1, noticeCheckBox);
		
		noCategoryCheckBox = new CheckBox("Brak kategorii");
		noCategoryCheckBox.setStyleName("gwt-LabelCustom");
		flexTable.setWidget(1, 2, noCategoryCheckBox);
		
		Label lblNewLabel = new Label("Stworzona:");
		lblNewLabel.setStyleName("gwt-FilterLabel");
		flowPanel.add(lblNewLabel);
		
		FlexTable flexTable_1 = new FlexTable();
		flowPanel.add(flexTable_1);
		
		Label lblPo = new Label("Po");
		lblPo.setStyleName("gwt-LabelCustom");
		lblPo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable_1.setWidget(0, 0, lblPo);
		
		createdAfterDateBox = new DateBox();
		flexTable_1.setWidget(0, 1, createdAfterDateBox);
		
		Label lblPrzed = new Label("Przed");
		lblPrzed.setStyleName("gwt-LabelCustom");
		lblPrzed.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable_1.setWidget(1, 0, lblPrzed);
		
		createdBeforeDateBox = new DateBox();
		flexTable_1.setWidget(1, 1, createdBeforeDateBox);
		
		Label lblWygasa = new Label("Wygasa:");
		lblWygasa.setStyleName("gwt-FilterLabel");
		flowPanel.add(lblWygasa);
		
		FlexTable flexTable_2 = new FlexTable();
		flowPanel.add(flexTable_2);
		
		Label label_1 = new Label("Po");
		label_1.setStyleName("gwt-LabelCustom");
		label_1.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable_2.setWidget(0, 0, label_1);
		
		expiryAfterDateBox = new DateBox();
		flexTable_2.setWidget(0, 1, expiryAfterDateBox);
		
		Label label_2 = new Label("Przed");
		label_2.setStyleName("gwt-LabelCustom");
		label_2.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable_2.setWidget(1, 0, label_2);
		
		expiryBeforeDateBox = new DateBox();
		flexTable_2.setWidget(1, 1, expiryBeforeDateBox);
		
		Label lblSkierowanaDo = new Label("Skierowana do:");
		lblSkierowanaDo.setStyleName("gwt-FilterLabel");
		flowPanel.add(lblSkierowanaDo);
		
		TextBox dedicationTextBox = new TextBox();
		dedicationTextBox.setStyleName("gwt-FilterDedicationTextBox");
		dedicationTextBox.getElement().setId("filterDedicationTextBox");
		flowPanel.add(dedicationTextBox);
		
		FlexTable flexTable_3 = new FlexTable();
		flexTable_3.setStyleName("gwt-FilterButtonTable");
		flowPanel.add(flexTable_3);
		
		resetButton = new Button("New button");
		resetButton.setStyleName("gwt-ButtonCustom");
		resetButton.setText("Reset");
		flexTable_3.setWidget(0, 0, resetButton);
		
		cancelButton = new Button("Anuluj");
		cancelButton.setStyleName("gwt-ButtonCustom");
		flexTable_3.setWidget(0, 1, cancelButton);
		cancelButton.setWidth("60px");
		
		okButton = new Button("New button");
		okButton.setStyleName("gwt-ButtonCustom");
		okButton.setText("Zastosuj");
		flexTable_3.setWidget(0, 2, okButton);
		okButton.setWidth("190px");
	
		
	}

	public DateBox getCreatedBeforeDateBox() {
		return createdBeforeDateBox;
	}
	public CheckBox getEntertainmentCheckBox() {
		return entertainmentCheckBox;
	}
	public Button getCancelButton() {
		return cancelButton;
	}
	public DateBox getExpiryAfterDateBox() {
		return expiryAfterDateBox;
	}
	public CheckBox getScienceCheckBox() {
		return scienceCheckBox;
	}
	public DateBox getExpiryBeforeDateBox() {
		return expiryBeforeDateBox;
	}
	public DateBox getCreatedAfterDateBox() {
		return createdAfterDateBox;
	}
	public Button getResetButton() {
		return resetButton;
	}
	public CheckBox getMeetingCheckBox() {
		return meetingCheckBox;
	}
	public CheckBox getNoticeCheckBox() {
		return noticeCheckBox;
	}
	public Button getOkButton() {
		return okButton;
	}
	public CheckBox getSportCheckBox() {
		return sportCheckBox;
	}
	public CheckBox getNoCategoryCheckBox() {
		return noCategoryCheckBox;
	}
}
