package pl.edu.pw.mini.sozpw.webinterface.ui;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;

public class HistoryManager implements ValueChangeHandler<String> {

	private MainUI mainUI;

	public HistoryManager(MainUI mainUI) {
		this.mainUI = mainUI;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String historyToken = event.getValue();
		resetHyperlinkStyle();

		if (historyToken.equals("main")) {
			mainUI.getContentPanel().clear();
			mainUI.getContentPanel().add(mainUI.getMainPage());

			mainUI.getMainHyperlink().setStyleName("gwt-HyperlinkCustomCurrent");
		} else if (historyToken.equals("groups")) {
			mainUI.getContentPanel().clear();
			mainUI.getContentPanel().add(mainUI.getGroupPage());
			mainUI.getGroupPage().initFields();
			
			mainUI.getGroupsHyperlink().setStyleName("gwt-HyperlinkCustomCurrent");
		} else if (historyToken.equals("settings")) {
			mainUI.getContentPanel().clear();
			mainUI.getContentPanel().add(mainUI.getSettingsPage());
			
			mainUI.getSettingsHyperlink().setStyleName("gwt-HyperlinkCustomCurrent");
		}
	}

	private void resetHyperlinkStyle() {
		mainUI.getMainHyperlink().setStyleName("gwt-HyperlinkCustom");
		mainUI.getGroupsHyperlink().setStyleName("gwt-HyperlinkCustom");
		mainUI.getSettingsHyperlink().setStyleName("gwt-HyperlinkCustom");
	}
}
