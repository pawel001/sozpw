package pl.edu.pw.mini.sozpw.webinterface.ui.dialogs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Category;
import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Dedicated;
import pl.edu.pw.mini.sozpw.webinterface.ui.pages.MainPage;
import pl.edu.pw.mini.sozpw.webinterface.utils.NoteFilter;

import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.DialogBox;

public class FilterDialog extends FilterDialogGenerated {

	public FilterDialog(final MainPage mainPage, final DialogBox parent,
			NoteFilter noteFilter) {

		initFromNoteFilter(noteFilter);

		getResetButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				initDefault();
				clearTokens();
			}
		});

		getCancelButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				parent.hide();
			}
		});

		getOkButton().addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				mainPage.setNoteFilter(retrieveNoteFilter());
				mainPage.initNotes();
				parent.hide();
			}
		});

	}

	public native void setTokenInput() /*-{
		scriptaddress = $wnd.location.protocol + "//" + $wnd.location.host
				+ "/dedicationQuery?type=all";
		$wnd.$("#filterDedicationTextBox").tokenInput(scriptaddress, {
			theme : "facebook",
			hintText : "Wpisz nazwę użytkownika lub grupy",
			noResultsText : "Brak rezultatów",
			searchingText : "Szukam..."
		});
	}-*/;

	public native JsArray<Dedicated> getSelectedTokens() /*-{
		return $wnd.$("#filterDedicationTextBox").tokenInput("get");
	}-*/;

	public native void addToken(String n) /*-{
		$wnd.$("#filterDedicationTextBox").tokenInput("add", {
			"id" : 0,
			"name" : n
		});
	}-*/;

	public native void clearTokens() /*-{
		$wnd.$("#filterDedicationTextBox").tokenInput("clear");
	}-*/;

	private void initDefault() {
		getEntertainmentCheckBox().setValue(true);
		getScienceCheckBox().setValue(true);
		getSportCheckBox().setValue(true);
		getMeetingCheckBox().setValue(true);
		getNoticeCheckBox().setValue(true);
		getNoCategoryCheckBox().setValue(true);

		getCreatedAfterDateBox().setValue(null);
		getCreatedBeforeDateBox().setValue(null);

		getExpiryAfterDateBox().setValue(null);
		getExpiryBeforeDateBox().setValue(null);
	}

	private void initFromNoteFilter(NoteFilter noteFilter) {
		getEntertainmentCheckBox().setValue(
				noteFilter.getCategories().contains(Category.ROZRYWKA));
		getScienceCheckBox().setValue(
				noteFilter.getCategories().contains(Category.NAUKA));
		getSportCheckBox().setValue(
				noteFilter.getCategories().contains(Category.SPORT));
		getMeetingCheckBox().setValue(
				noteFilter.getCategories().contains(Category.SPOTKANIE));
		getNoticeCheckBox().setValue(
				noteFilter.getCategories().contains(Category.OGLOSZENIE));
		getNoCategoryCheckBox().setValue(
				noteFilter.getCategories().contains(Category.BRAK_KATEGORII));

		if (noteFilter.getCreatedAfter() != 0) {
			getCreatedAfterDateBox().setValue(
					new Date(noteFilter.getCreatedAfter()));
		} else {
			getCreatedAfterDateBox().setValue(null);
		}

		if (noteFilter.getCreatedBefore() != Long.MAX_VALUE) {
			getCreatedBeforeDateBox().setValue(
					new Date(noteFilter.getCreatedBefore()));
		} else {
			getCreatedBeforeDateBox().setValue(null);
		}

		if (noteFilter.getExpiryAfter() != 0) {
			getExpiryAfterDateBox().setValue(new Date(noteFilter.getExpiryAfter()));
		} else {
			getExpiryAfterDateBox().setValue(null);
		}

		if (noteFilter.getExpiryBefore() != Long.MAX_VALUE) {
			getExpiryBeforeDateBox().setValue(new Date(noteFilter.getExpiryBefore()));
		} else {
			getExpiryBeforeDateBox().setValue(null);
		}

	}

	public void initDedications(NoteFilter noteFilter) {
		for (String dedicationItem : noteFilter.getDedicatedTo()) {
			addToken(dedicationItem);
		}
	}

	public NoteFilter retrieveNoteFilter() {
		NoteFilter result = new NoteFilter();

		result.setCategories(getCategories());

		long createdBefore = Long.MAX_VALUE;
		long createdAfter = 0;

		if (getCreatedBeforeDateBox().getValue() != null)
			createdBefore = getCreatedBeforeDateBox().getValue().getTime();
		if (getCreatedAfterDateBox().getValue() != null)
			createdAfter = getCreatedAfterDateBox().getValue().getTime();

		result.setCreatedAfter(createdAfter);
		result.setCreatedBefore(createdBefore);

		long expiryBefore = Long.MAX_VALUE;
		long expiryAfter = 0;

		if (getExpiryBeforeDateBox().getValue() != null)
			expiryBefore = getExpiryBeforeDateBox().getValue().getTime();
		if (getExpiryAfterDateBox().getValue() != null)
			expiryAfter = getExpiryAfterDateBox().getValue().getTime();

		result.setExpiryAfter(expiryAfter);
		result.setExpiryBefore(expiryBefore);

		JsArray<Dedicated> dedicatedList = getSelectedTokens();
		List<String> dedicatedTo = new ArrayList<String>();
		for (int i = 0; i < dedicatedList.length(); i++) {
			dedicatedTo.add(dedicatedList.get(i).getName());
		}

		result.setDedicatedTo(dedicatedTo);

		return result;
	}

	private List<Category> getCategories() {
		List<Category> result = new ArrayList<Category>();

		if (getEntertainmentCheckBox().getValue()) {
			result.add(Category.ROZRYWKA);
		}

		if (getScienceCheckBox().getValue()) {
			result.add(Category.NAUKA);
		}
		if (getSportCheckBox().getValue()) {
			result.add(Category.SPORT);
		}

		if (getMeetingCheckBox().getValue()) {
			result.add(Category.SPOTKANIE);
		}

		if (getNoticeCheckBox().getValue()) {
			result.add(Category.OGLOSZENIE);
		}

		if (getNoCategoryCheckBox().getValue()) {
			result.add(Category.BRAK_KATEGORII);
		}

		return result;
	}

}
