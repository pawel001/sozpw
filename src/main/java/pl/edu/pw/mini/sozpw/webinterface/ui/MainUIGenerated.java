package pl.edu.pw.mini.sozpw.webinterface.ui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class MainUIGenerated extends Composite {
	private Hyperlink mainHyperlink;
	private Hyperlink settingsHyperlink;
	private Hyperlink groupsHyperlink;
	private FlowPanel contentPanel;
	private Label usernameLabel;
	private Button logoutButton;
	private FlexTable flexTable;
	private FlexTable flexTable_1;
	private HorizontalPanel horizontalPanel;

	public MainUIGenerated() {

		FlowPanel flowPanel = new FlowPanel();
		flowPanel.setStyleName("gwt-mainmainPanel");
		initWidget(flowPanel);
		flowPanel.setSize("100%", "100%");
		
		horizontalPanel = new HorizontalPanel();
		horizontalPanel.setVerticalAlignment(HasVerticalAlignment.ALIGN_MIDDLE);
		horizontalPanel.setStyleName("gwt-menuPanel");
		flowPanel.add(horizontalPanel);
		horizontalPanel.setSize("100%", "40px");
		
		flexTable = new FlexTable();
		flexTable.setStyleName("gwt-mainmenu");
		horizontalPanel.add(flexTable);
		
				mainHyperlink = new Hyperlink("Strona główna", false, "main");
				flexTable.setWidget(0, 0, mainHyperlink);
				mainHyperlink.setStyleName("gwt-HyperlinkCustomCurrent a:LINK");
				
						groupsHyperlink = new Hyperlink("Grupy", false, "groups");
						flexTable.setWidget(0, 1, groupsHyperlink);
						groupsHyperlink.setStyleName("gwt-HyperlinkCustom");
						
								settingsHyperlink = new Hyperlink("Ustawienia", false, "settings");
								flexTable.setWidget(0, 2, settingsHyperlink);
								settingsHyperlink.setStyleName("gwt-HyperlinkCustom");
								flexTable.getCellFormatter().setVerticalAlignment(0, 0, HasVerticalAlignment.ALIGN_TOP);
								flexTable.getCellFormatter().setVerticalAlignment(0, 1, HasVerticalAlignment.ALIGN_TOP);
								flexTable.getCellFormatter().setVerticalAlignment(0, 2, HasVerticalAlignment.ALIGN_TOP);
								
								flexTable_1 = new FlexTable();
								horizontalPanel.add(flexTable_1);
								horizontalPanel.setCellHorizontalAlignment(flexTable_1, HasHorizontalAlignment.ALIGN_RIGHT);
								
								usernameLabel = new Label("");
								flexTable_1.setWidget(0, 0, usernameLabel);
								usernameLabel.setStyleName("gwt-LabelUser");
								usernameLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
								usernameLabel.setSize("300px", "100%");
								
								Image image = new Image("images/separator.png");
								flexTable_1.setWidget(0, 1, image);
								image.setSize("1px", "26px");
								
								logoutButton = new Button("Wyloguj");
								flexTable_1.setWidget(0, 2, logoutButton);
								logoutButton.setText("Wyloguj");
								logoutButton.setStyleName("gwt-ButtonLogout");
								logoutButton.setSize("60px", "100%");

		contentPanel = new FlowPanel();
		contentPanel.setStyleName("gwt-contentPanel");
		flowPanel.add(contentPanel);
		contentPanel.setSize("100%", "100%");

	}

	public Hyperlink getMainHyperlink() {
		return mainHyperlink;
	}

	public Hyperlink getSettingsHyperlink() {
		return settingsHyperlink;
	}

	public Hyperlink getGroupsHyperlink() {
		return groupsHyperlink;
	}

	protected FlowPanel getContentPanel() {
		return contentPanel;
	}
	protected Label getUsernameLabel() {
		return usernameLabel;
	}
	protected Button getLogoutButton() {
		return logoutButton;
	}
}
