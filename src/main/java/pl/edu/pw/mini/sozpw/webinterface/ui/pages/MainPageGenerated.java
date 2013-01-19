package pl.edu.pw.mini.sozpw.webinterface.ui.pages;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class MainPageGenerated extends Composite {
	private Button filtersButton;
	private FlowPanel mapPanel;
	private ScrollPanel scrollPanel;
	private FlowPanel notesPanel;
	private Button refreshButton;
	private FlowPanel flowPanel;
	private Button myNotesButton;
	private Button forMeNotesButton;
	private Button myLocationButton;
	private FlowPanel flowPanel_1;
	private FlexTable flexTable;
	private FlowPanel flowPanel_2;
	private FlexTable flexTable_1;
	private HorizontalPanel horizontalPanel_1;
	private VerticalPanel flowPanel_3;

	public MainPageGenerated() {

		flowPanel_3 = new VerticalPanel();
		initWidget(flowPanel_3);
		flowPanel_3.setSize("100%", "100%");

		horizontalPanel_1 = new HorizontalPanel();
		flowPanel_3.add(horizontalPanel_1);
		flowPanel_3.setCellHeight(horizontalPanel_1, "100%");
		horizontalPanel_1.setSize("100%", "100%");

		flowPanel = new FlowPanel();
		horizontalPanel_1.add(flowPanel);
		horizontalPanel_1.setCellHorizontalAlignment(flowPanel,
				HasHorizontalAlignment.ALIGN_RIGHT);
		horizontalPanel_1.setCellHeight(flowPanel, "100%");
		horizontalPanel_1.setCellWidth(flowPanel, "100%");
		flowPanel.setSize("100%", "");

		flowPanel_1 = new FlowPanel();
		flowPanel.add(flowPanel_1);
		flowPanel_3.setCellWidth(flowPanel_1, "100%");
		flowPanel_1.setStyleName("gwt-mapfunctionspanel");

		flexTable = new FlexTable();
		flexTable.setBorderWidth(0);
		flowPanel_1.add(flexTable);

		myNotesButton = new Button("New button");
		flexTable.setWidget(0, 1, myNotesButton);
		myNotesButton.setStyleName("gwt-ButtonCustomGrey");
		myNotesButton.setText("Moje notatki");

		forMeNotesButton = new Button("New button");
		flexTable.setWidget(0, 2, forMeNotesButton);
		forMeNotesButton.setStyleName("gwt-ButtonCustomGrey");
		forMeNotesButton.setText("Notatki dla mnie");

		myLocationButton = new Button("New button");
		flexTable.setWidget(0, 3, myLocationButton);
		myLocationButton.setStyleName("gwt-ButtonCustomGrey");
		myLocationButton.setText("Moja lokalizacja");
		flexTable.getCellFormatter().setVerticalAlignment(0, 1,
				HasVerticalAlignment.ALIGN_TOP);
		flexTable.getCellFormatter().setVerticalAlignment(0, 2,
				HasVerticalAlignment.ALIGN_TOP);
		flexTable.getCellFormatter().setVerticalAlignment(0, 3,
				HasVerticalAlignment.ALIGN_TOP);

		mapPanel = new FlowPanel();
		flowPanel.add(mapPanel);
		mapPanel.setWidth("100%");
		mapPanel.setStyleName("gwt-mapCanvas");

		flowPanel_2 = new FlowPanel();
		horizontalPanel_1.add(flowPanel_2);
		horizontalPanel_1.setCellHeight(flowPanel_2, "100%");
		horizontalPanel_1.setCellWidth(flowPanel_2, "324px");
		horizontalPanel_1.setCellHorizontalAlignment(flowPanel_2,
				HasHorizontalAlignment.ALIGN_CENTER);
		flowPanel_2.setSize("324px", "");

		flexTable_1 = new FlexTable();
		flexTable_1.setStyleName("gwt-notepaneltable");
		flowPanel_2.add(flexTable_1);

		refreshButton = new Button("New button");
		flexTable_1.setWidget(0, 0, refreshButton);
		refreshButton.setText("Odśwież");
		refreshButton.setStyleName("gwt-ButtonCustom");
		refreshButton.setSize("149px", "30px");

		filtersButton = new Button("Filtruj wiadomości");
		flexTable_1.setWidget(0, 1, filtersButton);
		filtersButton.setText("Filtruj");
		filtersButton.setStyleName("gwt-ButtonCustom");
		filtersButton.setSize("149px", "30px");

		scrollPanel = new ScrollPanel();
		flowPanel_2.add(scrollPanel);
		scrollPanel.setSize("304px", "");

		notesPanel = new FlowPanel();
		scrollPanel.setWidget(notesPanel);
		notesPanel.setSize("284px", "");
		notesPanel.setStyleName("gwt-MainNotePanel");
	}

	protected Button getFiltersButton() {
		return filtersButton;
	}

	protected FlowPanel getMapPanel() {
		return mapPanel;
	}

	public ScrollPanel getScrollPanel() {
		return scrollPanel;
	}

	public FlowPanel getNotesPanel() {
		return notesPanel;
	}

	public Button getRefreshButton() {
		return refreshButton;
	}

	public Button getMyNotesButton() {
		return myNotesButton;
	}

	public Button getMyLocationButton() {
		return myLocationButton;
	}

	public Button getForMeNotesButton() {
		return forMeNotesButton;
	}
}
