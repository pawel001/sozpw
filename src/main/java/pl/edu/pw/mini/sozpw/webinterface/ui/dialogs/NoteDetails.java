package pl.edu.pw.mini.sozpw.webinterface.ui.dialogs;

import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Note;
import pl.edu.pw.mini.sozpw.webinterface.utils.DateResolver;
import pl.edu.pw.mini.sozpw.webinterface.utils.Geolocalizator;
import pl.edu.pw.mini.sozpw.webinterface.utils.Geolocalizator.GeoCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.maps.gwt.client.LatLng;

public class NoteDetails extends NoteDetailsGenerated {

	public NoteDetails(final DialogBox parent, Note note) {
		getLatLngTextBox().setText(note.getLatitude() + ", " + note.getLongitude());
		Geolocalizator.putStringLocation(LatLng.create(note.getLatitude(), note.getLongitude()),
				new GeoCallback() {
					@Override
					public void geocodeSuccess(String location) {
						getLocationTextBox().setText(location);

					}
				});
		getCategoryTextBox().setText(note.getCategory().toString());
		getCreateDateTextBox().setText(DateResolver.resolveDate(note.getCreateDate()));
		getExpiryDateTextBox().setText(DateResolver.resolveDate(note.getExpiryDate()));
		
		String dedication = "";
		for(String item : note.getDedicationList()){
			dedication = dedication + item + " ";
		}
		getDedicationTextBox().setValue(dedication);
		
		Anchor attachentLink = new Anchor();
		attachentLink.setTarget("_blank");
		attachentLink.setText(note.getFilename());
		attachentLink.setHref(GWT.getHostPageBaseURL() + "attachmentservice?noteId=" + note.getId() + "&filename=" + note.getFilename());
		getAttachmentDiv().add(attachentLink);

		getOkButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				parent.hide();
			}
		});
	}
}
