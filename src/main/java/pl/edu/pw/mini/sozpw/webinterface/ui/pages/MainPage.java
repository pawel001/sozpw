package pl.edu.pw.mini.sozpw.webinterface.ui.pages;

import java.util.List;

import pl.edu.pw.mini.sozpw.webinterface.dataobjects.Note;
import pl.edu.pw.mini.sozpw.webinterface.services.NoteService;
import pl.edu.pw.mini.sozpw.webinterface.services.NoteServiceAsync;
import pl.edu.pw.mini.sozpw.webinterface.ui.dialogs.FilterDialog;
import pl.edu.pw.mini.sozpw.webinterface.ui.dialogs.StyledDialogBox;
import pl.edu.pw.mini.sozpw.webinterface.ui.elements.CommentWidget;
import pl.edu.pw.mini.sozpw.webinterface.ui.elements.NoteWidget;
import pl.edu.pw.mini.sozpw.webinterface.ui.handlers.MapClickHandler;
import pl.edu.pw.mini.sozpw.webinterface.utils.Geolocalizator;
import pl.edu.pw.mini.sozpw.webinterface.utils.NoteFilter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.maps.gwt.client.GoogleMap;
import com.google.maps.gwt.client.GoogleMap.BoundsChangedHandler;
import com.google.maps.gwt.client.LatLng;
import com.google.maps.gwt.client.LatLngBounds;
import com.google.maps.gwt.client.MapOptions;
import com.google.maps.gwt.client.MapTypeId;
import com.google.maps.gwt.client.Marker;
import com.google.maps.gwt.client.Marker.ClickHandler;
import com.google.maps.gwt.client.Marker.DragEndHandler;
import com.google.maps.gwt.client.Marker.MouseOutHandler;
import com.google.maps.gwt.client.Marker.MouseOverHandler;
import com.google.maps.gwt.client.MarkerOptions;
import com.google.maps.gwt.client.MouseEvent;

public class MainPage extends MainPageGenerated {

	private GoogleMap map;
	private String username;
	private NoteServiceAsync noteService;
	private NoteFilter noteFilter;

	public MainPage(String username) {
		this.username = username;
		this.noteFilter = new NoteFilter();

		initMap();
		initTimer();

		getFiltersButton().addClickHandler(
				new com.google.gwt.event.dom.client.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						StyledDialogBox sdb = new StyledDialogBox(
								"Filtruj wiadomości");
						FilterDialog fd = new FilterDialog(MainPage.this, sdb,
								MainPage.this.noteFilter);
						sdb.add(fd);
						sdb.center();
						fd.setTokenInput();
						fd.initDedications(MainPage.this.noteFilter);
					}
				});

		getRefreshButton().addClickHandler(
				new com.google.gwt.event.dom.client.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						MainPage.this.initNotes();
					}
				});

		getMyLocationButton().addClickHandler(
				new com.google.gwt.event.dom.client.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						Geolocalizator.setToCurrentLatLng(map);
						map.setZoom(17);
					}
				});

		getMyNotesButton().addClickHandler(
				new com.google.gwt.event.dom.client.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						LatLng ne = calculateLatLng(true, false);
						LatLng sw = calculateLatLng(false, false);

						if (ne != null && sw != null) {
							LatLngBounds bounds = LatLngBounds.create(sw, ne);
							map.panTo(bounds.getCenter());
							map.setZoom(14);
							while(!(map.getBounds().contains(ne) && map.getBounds().contains(sw))){
								map.setZoom(map.getZoom()-1);
							}
						}

					}
				});

		getForMeNotesButton().addClickHandler(
				new com.google.gwt.event.dom.client.ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						LatLng ne = calculateLatLng(true, true);
						LatLng sw = calculateLatLng(false, true);

						if (ne != null && sw != null) {
							LatLngBounds bounds = LatLngBounds.create(sw, ne);
							map.panTo(bounds.getCenter());
							map.setZoom(14);
							while(!(map.getBounds().contains(ne) && map.getBounds().contains(sw))){
								map.setZoom(map.getZoom()-0.1);
							}
						}

					}
				});

		getScrollPanel().setHeight((Window.getClientHeight() - 270) + "px");
		getMapPanel().setHeight((Window.getClientHeight()  - 220) + "px");
		Window.addResizeHandler(new ResizeHandler() {
			public void onResize(ResizeEvent event) {
				int height = event.getHeight();
				if (height < 600) {
					height = 600;
				}
				getScrollPanel().setHeight((height - 270) + "px");
				getMapPanel().setHeight((height - 220) + "px");
			}
		});
	}

	private void initTimer() {
		(new Timer() {
			@Override
			public void run() {
				for (int i = 0; i < getNotesPanel().getWidgetCount(); i++) {
					NoteWidget nw = (NoteWidget) getNotesPanel().getWidget(i);
					nw.updateDateOnTimer();

					for (int j = 0; j < nw.getCommentPanel().getWidgetCount(); j++) {
						((CommentWidget) nw.getCommentPanel().getWidget(j))
								.updateDateOnTimer();
					}
				}
			}
		}).scheduleRepeating(60 * 1000);
	}

	private void initMap() {
		noteService = (NoteServiceAsync) GWT.create(NoteService.class);

		MapOptions myOptions = MapOptions.create();
		myOptions.setZoom(12.0);
		myOptions.setMapTypeId(MapTypeId.ROADMAP);

		map = GoogleMap.create(getMapPanel().getElement(), myOptions);

		map.addClickListener(new MapClickHandler(this));
		map.addBoundsChangedListener(new BoundsChangedHandler() {

			@Override
			public void handle() {
				hideFromPanel();
			}
		});

		Geolocalizator.setToDefaultPosition(map);
		Geolocalizator.setToCurrentLatLng(map);

		initNotes();

	}

	public void initNotes() {

		for (int i = 0; i < getNotesPanel().getWidgetCount(); i++) {
			NoteWidget nw = (NoteWidget) getNotesPanel().getWidget(i);
			nw.getMarker().setMap((GoogleMap) null);
		}

		getNotesPanel().clear();

		noteService.getNotes(username, new AsyncCallback<List<Note>>() {

			@Override
			public void onSuccess(List<Note> result) {
				for (Note note : result) {
					if (MainPage.this.noteFilter.pass(note)) {
						MainPage.this.addNoteToPanel(note);
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// Window.alert("NoteService.getNotes() failed.");
			}
		});
	}

	public void processNote(final Note note) {

		final boolean recentlyCreated = (note.getId() == Note.NO_ID);

		noteService.processNote(note, new AsyncCallback<Integer>() {

			@Override
			public void onSuccess(Integer result) {
				if (result > 0) {
					if (recentlyCreated) {
						note.setId(result);
						addNoteToPanel(note);
					} else {
						updateNoteOnPanel(note);
					}
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("noteService.processNote failed.");
			}
		});
	}

	public void deleteNote(final Note note) {

		noteService.deleteNote(Integer.valueOf(note.getId()),
				new AsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean result) {
						if (result)
							deleteNoteFromPanel(note);
					}

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("noteService.deleteNote failed.");
					}
				});
	}

	private void deleteNoteFromPanel(Note note) {
		int index = -1;

		for (int i = 0; i < getNotesPanel().getWidgetCount(); i++) {
			NoteWidget nw = (NoteWidget) getNotesPanel().getWidget(i);
			if (nw.getNote().getId() == note.getId()) {
				nw.getMarker().setMap((GoogleMap) null);
				index = i;
				break;
			}
		}

		if (index > -1) {
			getNotesPanel().remove(index);
		}
	}

	private void addNoteToPanel(final Note note) {
		MarkerOptions markerOpts = MarkerOptions.create();
		LatLng position = LatLng
				.create(note.getLatitude(), note.getLongitude());

		markerOpts.setPosition(position);
		markerOpts.setMap(map);
		if (note.getUsername().equals(username)) {
			markerOpts.setIcon("images/marker_red.png");
		} else if (note.getDedicationList().contains(username)) {
			markerOpts.setIcon("images/marker_green.png");
		} else {
			markerOpts.setIcon("images/marker_blue.png");
		}
		markerOpts.setShadow("images/shadow.png");
		markerOpts.setDraggable(true);

		final Marker marker = Marker.create(markerOpts);

		final NoteWidget noteWidget = new NoteWidget(this, note, marker);
		getNotesPanel().add(noteWidget);

		if (!note.getUsername().equals(username)) {
			noteWidget.disableForEdit();
			marker.setDraggable(false);
		}

		marker.addMouseOverListener(new MouseOverHandler() {

			@Override
			public void handle(MouseEvent event) {
				noteWidget.setStyleName("gwt-NoteSelected", true);
			}
		});

		marker.addMouseOutListener(new MouseOutHandler() {
			@Override
			public void handle(MouseEvent event) {
				noteWidget.setStyleName("gwt-NoteSelected", false);
			}
		});

		marker.addClickListener(new ClickHandler() {

			@Override
			public void handle(MouseEvent event) {
				noteWidget.getElement().scrollIntoView();
			}
		});

		marker.addDragEndListener(new DragEndHandler() {

			@Override
			public void handle(MouseEvent event) {

				final LatLng prevPosition = LatLng.create(note.getLatitude(),
						note.getLongitude());

				note.setLatitude(event.getLatLng().lat());
				note.setLongitude(event.getLatLng().lng());

				noteService.processNote(note, new AsyncCallback<Integer>() {

					@Override
					public void onSuccess(Integer result) {

					}

					@Override
					public void onFailure(Throwable caught) {
						note.setLatitude(prevPosition.lat());
						note.setLongitude(prevPosition.lng());
						marker.setPosition(prevPosition);
					}
				});
			}
		});
	}

	private void updateNoteOnPanel(Note note) {
		for (int i = 0; i < getNotesPanel().getWidgetCount(); i++) {
			NoteWidget nw = (NoteWidget) getNotesPanel().getWidget(i);
			if (nw.getNote().getId() == note.getId()) {
				nw.updateAccordingToNote(note);
			}
		}
	}

	public GoogleMap getMap() {
		return map;
	}

	public String getUsername() {
		return username;
	}

	public NoteFilter getNoteFilter() {
		return noteFilter;
	}

	public void setNoteFilter(NoteFilter noteFilter) {
		this.noteFilter = noteFilter;
	}

	private void hideFromPanel() {
		LatLngBounds bounds = MainPage.this.map.getBounds();
		for (int i = 0; i < getNotesPanel().getWidgetCount(); i++) {
			NoteWidget nw = (NoteWidget) getNotesPanel().getWidget(i);
			LatLng nwlatlng = LatLng.create(nw.getNote().getLatitude(), nw
					.getNote().getLongitude());
			nw.setVisible(bounds.contains(nwlatlng));
		}
	}

	public LatLng calculateLatLng(boolean ne, boolean notesToMe) {
		double lat = Double.MIN_VALUE;
		double lng = Double.MIN_VALUE;

		if (!ne) {
			lat = Double.MAX_VALUE;
			lng = Double.MAX_VALUE;
		}

		for (int i = 0; i < getNotesPanel().getWidgetCount(); i++) {
			NoteWidget nw = (NoteWidget) getNotesPanel().getWidget(i);
			if ((notesToMe && nw.getNote().getDedicationList()
					.contains(username))
					|| (!notesToMe && nw.getNote().getUsername()
							.equals(username))) {
				if (ne) {
					if (nw.getNote().getLatitude() > lat) {
						lat = nw.getNote().getLatitude();
					}
					if (nw.getNote().getLongitude() > lng) {
						lng = nw.getNote().getLongitude();
					}
				} else {
					if (nw.getNote().getLatitude() < lat) {
						lat = nw.getNote().getLatitude();
					}
					if (nw.getNote().getLongitude() < lng) {
						lng = nw.getNote().getLongitude();
					}
				}
			}
		}

		if (lat != Double.MIN_VALUE && lng != Double.MIN_VALUE
				&& lat != Double.MAX_VALUE && lng != Double.MAX_VALUE) {
			return LatLng.create(lat, lng);
		}
		return null;
	}

}
