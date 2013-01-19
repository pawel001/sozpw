package pl.edu.pw.mini.sozpw.webinterface.ui;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class WrapperUI extends Composite {
	private VerticalPanel verticalPanel;

	public WrapperUI() {
		
		verticalPanel = new VerticalPanel();
		initWidget(verticalPanel);
	}

	public VerticalPanel getVerticalPanel() {
		return verticalPanel;
	}
}
