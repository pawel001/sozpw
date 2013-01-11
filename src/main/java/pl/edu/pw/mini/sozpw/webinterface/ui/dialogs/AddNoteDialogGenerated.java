package pl.edu.pw.mini.sozpw.webinterface.ui.dialogs;

import java.io.IOException;
import java.util.Arrays;

import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Category;
import pl.edu.pw.mini.sozpw.webinterface.ui.elements.UploadWidget;

import com.google.gwt.text.shared.Renderer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ValueListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

public class AddNoteDialogGenerated extends Composite {
	private Button cancelButton;
	private Button saveButton;
	private UploadWidget uploadWidget;
	private TextArea noteTextArea;
	private DateBox dateBox;
	private TextBox topicTextBox;
	private TextBox dedicationTextBox;
	private ValueListBox<Category> categoryListBox;
	private FlexTable advancedTable;

	public AddNoteDialogGenerated() {
		
		VerticalPanel verticalPanel = new VerticalPanel();
		verticalPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		initWidget(verticalPanel);
		verticalPanel.setWidth("440px");
		
		VerticalPanel verticalPanel_1 = new VerticalPanel();
		verticalPanel_1.setStyleName("gwt-verticalPanelMargin");
		verticalPanel.add(verticalPanel_1);
		
		Label lblTemat = new Label("Temat");
		verticalPanel_1.add(lblTemat);
		lblTemat.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		verticalPanel.setCellHorizontalAlignment(lblTemat, HasHorizontalAlignment.ALIGN_CENTER);
		lblTemat.setWidth("400px");
		lblTemat.setStyleName("gwt-LabelCustom");
		
		topicTextBox = new TextBox();
		verticalPanel_1.add(topicTextBox);
		verticalPanel.setCellHorizontalAlignment(topicTextBox, HasHorizontalAlignment.ALIGN_CENTER);
		topicTextBox.setSize("400px", "20px");
		topicTextBox.setStyleName("gwt-TextArea400");
		
		Label lblTre = new Label("Treść");
		verticalPanel_1.add(lblTre);
		lblTre.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		verticalPanel.setCellHorizontalAlignment(lblTre, HasHorizontalAlignment.ALIGN_CENTER);
		lblTre.setWidth("400px");
		lblTre.setStyleName("gwt-LabelCustom");
		
		noteTextArea = new TextArea();
		verticalPanel_1.add(noteTextArea);
		verticalPanel.setCellHorizontalAlignment(noteTextArea, HasHorizontalAlignment.ALIGN_CENTER);
		noteTextArea.setWidth("400px");
		noteTextArea.setVisibleLines(3);
		noteTextArea.setStyleName("gwt-TextArea400");
		
		DisclosurePanel disclosurePanel = new DisclosurePanel("Zaawansowane");
		disclosurePanel.setAnimationEnabled(true);
		disclosurePanel.setStyleName("gwt-DisclosurePanelC");
		verticalPanel.add(disclosurePanel);
		verticalPanel.setCellWidth(disclosurePanel, "400px");
		verticalPanel.setCellHorizontalAlignment(disclosurePanel, HasHorizontalAlignment.ALIGN_CENTER);
		disclosurePanel.setWidth("420px");
		
		VerticalPanel verticalPanel_5 = new VerticalPanel();
		verticalPanel_5.setStyleName("gwt-verticalPanelMarginTop");
		verticalPanel_5.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		disclosurePanel.setContent(verticalPanel_5);
		verticalPanel_5.setWidth("390px");
		
		advancedTable = new FlexTable();
		verticalPanel_5.add(advancedTable);
		advancedTable.setSize("300px", "100px");
		
		Label lblNewLabel = new Label("Kategoria");
		lblNewLabel.setStyleName("gwt-LabelCustom");
		advancedTable.setWidget(0, 0, lblNewLabel);
		lblNewLabel.setWidth("100px");
		
		categoryListBox = new ValueListBox<Category>(new Renderer<Category>() {

			@Override
			public String render(Category object) {
				if(object != null)
					return object.toString();
				return "";
			}

			@Override
			public void render(Category object, Appendable appendable) throws IOException {
				appendable.append(object.toString());
			}
		});
		categoryListBox.setAcceptableValues(Arrays.asList(Category.values()));
		categoryListBox.setValue(Category.BRAK_KATEGORII);
		categoryListBox.setStyleName("gwt-ListBoxNoBorder");
		advancedTable.setWidget(0, 1, categoryListBox);
		advancedTable.getCellFormatter().setWidth(0, 1, "300px");
		advancedTable.getCellFormatter().setHeight(0, 1, "26px");
		categoryListBox.setSize("306px", "26px");
		
		Label lblOsobygrupyDoKtrych = new Label("Osoba/grupa");
		advancedTable.setWidget(1, 0, lblOsobygrupyDoKtrych);
		lblOsobygrupyDoKtrych.setStyleName("gwt-LabelCustom");
		lblOsobygrupyDoKtrych.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		lblOsobygrupyDoKtrych.setWidth("100px");
		verticalPanel_5.setCellHorizontalAlignment(lblOsobygrupyDoKtrych, HasHorizontalAlignment.ALIGN_CENTER);
		advancedTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		dedicationTextBox = new TextBox();
		dedicationTextBox.getElement().setId("inputTextBox");
		dedicationTextBox.setStyleName("gwt-TextArea300");
		advancedTable.setWidget(1, 1, dedicationTextBox);
		advancedTable.getCellFormatter().setWidth(1, 1, "300px");
		dedicationTextBox.setSize("300px", "20px");
		
		Label lblWygasa = new Label("Wygasa");
		lblWygasa.setStyleName("gwt-LabelCustom");
		advancedTable.setWidget(2, 0, lblWygasa);
		advancedTable.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		dateBox = new DateBox();
		dateBox.setStyleName("gwt-TextArea300");
		advancedTable.setWidget(2, 1, dateBox);
		advancedTable.getCellFormatter().setWidth(2, 1, "300px");
		advancedTable.getCellFormatter().setHeight(2, 1, "");
		dateBox.setSize("300px", "20px");
		
		Label lblZacznik = new Label("Załącznik");
		lblZacznik.setStyleName("gwt-LabelCustom");
		advancedTable.setWidget(3, 0, lblZacznik);
		advancedTable.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		
		uploadWidget = new UploadWidget();
		advancedTable.setWidget(3, 1, uploadWidget);
		advancedTable.getCellFormatter().setWidth(3, 1, "300px");
		uploadWidget.setSize("300px", "20px");
		advancedTable.getCellFormatter().setHeight(3, 1, "");
		advancedTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		advancedTable.getCellFormatter().setVerticalAlignment(1, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		advancedTable.getCellFormatter().setVerticalAlignment(3, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		advancedTable.getCellFormatter().setVerticalAlignment(3, 0, HasVerticalAlignment.ALIGN_TOP);
		advancedTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
		advancedTable.getCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
		advancedTable.getCellFormatter().setVerticalAlignment(2, 0, HasVerticalAlignment.ALIGN_TOP);
		advancedTable.getCellFormatter().setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		
		VerticalPanel verticalPanel_2 = new VerticalPanel();
		verticalPanel_2.setStyleName("gwt-verticalPanelMargin");
		verticalPanel.add(verticalPanel_2);
		
		FlexTable flexTable = new FlexTable();
		verticalPanel_2.add(flexTable);
		verticalPanel.setCellHorizontalAlignment(flexTable, HasHorizontalAlignment.ALIGN_CENTER);
		flexTable.setWidth("420px");
		
		cancelButton = new Button("Anuluj");
		flexTable.setWidget(0, 0, cancelButton);
		flexTable.getCellFormatter().setHeight(0, 0, "30px");
		flexTable.getCellFormatter().setWidth(0, 0, "150px");
		cancelButton.setText("Anuluj");
		cancelButton.setStyleName("gwt-ButtonCustom");
		cancelButton.setSize("100%", "100%");
		
		saveButton = new Button("Zapisz");
		saveButton.setText("Zapisz");
		flexTable.setWidget(0, 1, saveButton);
		flexTable.getCellFormatter().setHeight(0, 1, "30px");
		saveButton.setStyleName("gwt-ButtonCustom");
		saveButton.setSize("100%", "100%");
	}

	protected Button getCancelButton() {
		return cancelButton;
	}
	protected Button getSaveButton() {
		return saveButton;
	}
	protected UploadWidget getUploadWidget() {
		return uploadWidget;
	}
	protected void setUploadWidget(UploadWidget widget) {
		this.uploadWidget = widget;
		advancedTable.setWidget(3, 1, uploadWidget);
	}
	protected TextArea getNoteTextArea() {
		return noteTextArea;
	}
	protected DateBox getDateBox() {
		return dateBox;
	}
	protected TextBox getTopicTextBox() {
		return topicTextBox;
	}
	protected TextBox getDedicationTextBox() {
		return dedicationTextBox;
	}
	protected ValueListBox<Category> getCategoryListBox() {
		return categoryListBox;
	}
	public FlexTable getAdvancedTable() {
		return advancedTable;
	}
}
