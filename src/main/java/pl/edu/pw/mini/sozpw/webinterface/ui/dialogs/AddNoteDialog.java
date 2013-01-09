package pl.edu.pw.mini.sozpw.webinterface.ui.dialogs;

import java.util.Date;

import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Dedicated;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Note;
import pl.edu.pw.mini.sozpw.webinterface.ui.elements.UploadWidget;
import pl.edu.pw.mini.sozpw.webinterface.ui.pages.MainPage;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;

public class AddNoteDialog extends AddNoteDialogGenerated {

	private MainPage mainPage;
	private MapDialog mapDialog;

	private AddNoteDialog(final DialogBox parent, MainPage mainPage) {
		this.mainPage = mainPage;

		this.getCancelButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				parent.hide();
			}
		});
		
	}

	public AddNoteDialog(final DialogBox parent, MapDialog mapDialog, MainPage mainPage) {
		this(parent, mainPage);
		this.mapDialog = mapDialog;

		this.getSaveButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AddNoteDialog.this.mainPage.processNote(retriveNewNote());
				parent.hide();
			}
		});
	}

	public AddNoteDialog(final DialogBox parent, final Note note, MainPage mainPage) {
		this(parent, mainPage);
		getTopicTextBox().setText(note.getTopic());
		getNoteTextArea().setText(note.getContent());
		getCategoryListBox().setValue(note.getCategory());
		if (note.getExpiryDate() != 0) {
			getDateBox().setValue(new Date(note.getExpiryDate()));
		}
		setUploadWidget(new UploadWidget(note.getFilename()));

		this.getSaveButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				AddNoteDialog.this.mainPage.processNote(getUpdatedNoteCopy(note));
				parent.hide();
			}
		});
	}

	private Note retriveNewNote() {
		Note note = new Note();
		note.setUsername(AddNoteDialog.this.mainPage.getUsername());
		note.setLatitude(AddNoteDialog.this.mapDialog.getPosition().lat());
		note.setLongitude(AddNoteDialog.this.mapDialog.getPosition().lng());
		note.setCreateDate(new Date().getTime());

		retriveNote(note);

		return note;
	}

	private Note getUpdatedNoteCopy(Note note) {
		Note updatedNote = new Note();
		updatedNote.setId(note.getId());
		updatedNote.setCreateDate(note.getCreateDate());
		updatedNote.setUsername(note.getUsername());
		updatedNote.setLatitude(note.getLatitude());
		updatedNote.setLongitude(note.getLongitude());

		retriveNote(updatedNote);

		return updatedNote;
	}

	private void retriveNote(Note note) {
		note.setTopic(getTopicTextBox().getValue());
		note.setContent(getNoteTextArea().getValue());
		note.setCategory(getCategoryListBox().getValue());

		JsArray<Dedicated> dedicationList = getSelectedTokens();
		for (int i = 0; i < dedicationList.length(); i++) {
			note.getDedicationList().add(dedicationList.get(i).getName());
		}

		if (getDateBox().getValue() != null)
			note.setExpiryDate(getDateBox().getValue().getTime());
		String val;
		if ((val = getUploadWidget().getFilename()) != null) {
			note.setFilename(val);
		} else {
			note.setFilename("");
		}
	}

	public native void setTokenInput() /*-{
		scriptaddress = $wnd.location.protocol + "//" + $wnd.location.host
				+ "/dedicationQuery?type=all";
		$wnd.$("#inputTextBox").tokenInput(scriptaddress, {
			theme : "facebook",
			hintText : "Wpisz nazwę użytkownika lub grupy",
			noResultsText : "Brak rezultatów",
			searchingText : "Szukam..."
		});
	}-*/;

	public native JsArray<Dedicated> getSelectedTokens() /*-{
		return $wnd.$("#inputTextBox").tokenInput("get");
	}-*/;

	public native void addToken(String n) /*-{
		$wnd.$("#inputTextBox").tokenInput("add", {"id": 0, "name": n});
	}-*/;

}
