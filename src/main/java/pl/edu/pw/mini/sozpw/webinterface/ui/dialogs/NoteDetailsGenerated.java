package pl.edu.pw.mini.sozpw.webinterface.ui.dialogs;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

public class NoteDetailsGenerated extends Composite {
	private TextBox dedicationTextBox;
	private TextBox latLngTextBox;
	private TextBox locationTextBox;
	private FlowPanel attachmentDiv;
	private Button okButton;
	private TextBox categoryTextBox;
	private TextBox expiryDateTextBox;
	private TextBox createDateTextBox;

	public NoteDetailsGenerated() {
		
		FlowPanel flowPanel = new FlowPanel();
		initWidget(flowPanel);
		
		FlexTable flexTable_1 = new FlexTable();
		flowPanel.add(flexTable_1);
		
		FlexTable flexTable = new FlexTable();
		flexTable.setStyleName("gwt-NoteDetailsTopTable");
		flexTable_1.setWidget(0, 0, flexTable);
		
		Label lblWsprzdne = new Label("Współrzędne");
		lblWsprzdne.setStyleName("gwt-LabelCustom");
		lblWsprzdne.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.setWidget(0, 0, lblWsprzdne);
		flexTable.getCellFormatter().setWidth(0, 0, "100px");
		
		latLngTextBox = new TextBox();
		latLngTextBox.setStyleName("gwt-TextArea300");
		latLngTextBox.setReadOnly(true);
		flexTable.setWidget(0, 1, latLngTextBox);
		latLngTextBox.setWidth("100%");
		
		Label lblLokacja = new Label("Lokalizacja");
		lblLokacja.setStyleName("gwt-LabelCustom");
		lblLokacja.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.setWidget(1, 0, lblLokacja);
		
		locationTextBox = new TextBox();
		locationTextBox.setStyleName("gwt-TextArea300");
		locationTextBox.setReadOnly(true);
		flexTable.setWidget(1, 1, locationTextBox);
		locationTextBox.setWidth("100%");
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		FlexTable flexTable_2 = new FlexTable();
		flexTable_2.setStyleName("gwt-NoteDetailsMiddleTable");
		flexTable_1.setWidget(1, 0, flexTable_2);
		
		Label lblKategoria = new Label("Kategoria");
		flexTable_2.setWidget(0, 0, lblKategoria);
		flexTable_2.getCellFormatter().setWidth(0, 0, "100px");
		lblKategoria.setStyleName("gwt-LabelCustom");
		lblKategoria.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		categoryTextBox = new TextBox();
		flexTable_2.setWidget(0, 1, categoryTextBox);
		categoryTextBox.setReadOnly(true);
		categoryTextBox.setStyleName("gwt-TextArea300");
		categoryTextBox.setWidth("100%");
		
		Label lblStworzona = new Label("Stworzona");
		flexTable_2.setWidget(1, 0, lblStworzona);
		lblStworzona.setStyleName("gwt-LabelCustom");
		lblStworzona.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		createDateTextBox = new TextBox();
		flexTable_2.setWidget(1, 1, createDateTextBox);
		createDateTextBox.setReadOnly(true);
		createDateTextBox.setStyleName("gwt-TextArea300");
		createDateTextBox.setWidth("100%");
		
		Label lblWygasa = new Label("Wygasa");
		flexTable_2.setWidget(2, 0, lblWygasa);
		lblWygasa.setStyleName("gwt-LabelCustom");
		lblWygasa.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		expiryDateTextBox = new TextBox();
		flexTable_2.setWidget(2, 1, expiryDateTextBox);
		expiryDateTextBox.setReadOnly(true);
		expiryDateTextBox.setStyleName("gwt-TextArea300");
		expiryDateTextBox.setWidth("100%");
		
		Label lblSkierowanaDo = new Label("Skierowana do");
		flexTable_2.setWidget(3, 0, lblSkierowanaDo);
		lblSkierowanaDo.setStyleName("gwt-LabelCustom");
		lblSkierowanaDo.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		dedicationTextBox = new TextBox();
		flexTable_2.setWidget(3, 1, dedicationTextBox);
		dedicationTextBox.setStyleName("gwt-TextArea300");
		dedicationTextBox.setReadOnly(true);
		dedicationTextBox.setWidth("100%");
		
		Label lblZacznik = new Label("Załącznik");
		flexTable_2.setWidget(4, 0, lblZacznik);
		lblZacznik.setStyleName("gwt-LabelCustom");
		lblZacznik.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		attachmentDiv = new FlowPanel();
		attachmentDiv.setStyleName("gwt-NoteDetailsAttachment");
		flexTable_2.setWidget(4, 1, attachmentDiv);
		
		okButton = new Button("OK");
		okButton.setStyleName("gwt-ButtonCustom");
		flexTable_1.setWidget(2, 0, okButton);
		okButton.setSize("100%", "30px");
	}

	public TextBox getDedicationTextBox() {
		return dedicationTextBox;
	}
	public TextBox getLatLngTextBox() {
		return latLngTextBox;
	}
	public TextBox getLocationTextBox() {
		return locationTextBox;
	}
	public FlowPanel getAttachmentDiv() {
		return attachmentDiv;
	}
	public Button getOkButton() {
		return okButton;
	}
	public TextBox getCategoryTextBox() {
		return categoryTextBox;
	}
	public TextBox getExpiryDateTextBox() {
		return expiryDateTextBox;
	}
	public TextBox getCreateDateTextBox() {
		return createDateTextBox;
	}
}
