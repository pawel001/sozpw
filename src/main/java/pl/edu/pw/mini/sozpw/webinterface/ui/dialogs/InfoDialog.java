package pl.edu.pw.mini.sozpw.webinterface.ui.dialogs;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class InfoDialog extends Composite {

	public InfoDialog(final DialogBox parent, String msg) {

		FlowPanel flowPanel = new FlowPanel();
		flowPanel.setStyleName("gwt-infodialog");
		initWidget(flowPanel);

		Label messageLabel = new Label(msg);
		messageLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		messageLabel.setStyleName("gwt-LabelCustomMargin");
		flowPanel.add(messageLabel);
		messageLabel.setWidth("340px");

		Button btnNewButton = new Button("New button");
		btnNewButton.setStyleName("gwt-ButtonCustom");
		btnNewButton.setText("Ok");
		flowPanel.add(btnNewButton);
		btnNewButton.setSize("340px", "30px");

		btnNewButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				parent.hide();
			}
		});

	}
}
