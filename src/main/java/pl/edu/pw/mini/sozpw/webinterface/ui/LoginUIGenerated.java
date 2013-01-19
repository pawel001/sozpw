package pl.edu.pw.mini.sozpw.webinterface.ui;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class LoginUIGenerated extends Composite {
	private TextBox registerMail;
	private PasswordTextBox loginPass;
	private TextBox loginUsername;
	private TextBox registerUsername;
	private Label registerLabel;
	private Label loginLabel;
	private PasswordTextBox registerPassConf;
	private PasswordTextBox registerPass;
	private Button loginButton;
	private Button registerButton;
	private Image registerWaitImage;

	public LoginUIGenerated() {
		
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.setStyleName("gwt-contentPanel");
		initWidget(flowPanel);
		flowPanel.setSize("100%", "100%");
		
		VerticalPanel verticalPanel = new VerticalPanel();
		flowPanel.add(verticalPanel);
		verticalPanel.setStyleName("gwt-login");
		verticalPanel.setSpacing(30);
		verticalPanel.setSize("333px", "130px");
		
		FlexTable flexTable = new FlexTable();
		verticalPanel.add(flexTable);
		flexTable.setSize("320px", "110px");
		
		Label lblLogin = new Label("LOGIN");
		lblLogin.setStyleName("gwt-LabelCustom");
		flexTable.setWidget(0, 0, lblLogin);
		lblLogin.setWidth("150px");
		
		loginUsername = new TextBox();
		flexTable.setWidget(0, 1, loginUsername);
		loginUsername.setWidth("170px");
		
		Label lblHaso = new Label("HASŁO");
		lblHaso.setStyleName("gwt-LabelCustom");
		flexTable.setWidget(1, 0, lblHaso);
		
		loginPass = new PasswordTextBox();
		flexTable.setWidget(1, 1, loginPass);
		loginPass.setWidth("170px");
		loginPass.setStyleName("gwt-TextBox");
		
		loginButton = new Button("Zaloguj");
		loginButton.setStyleName("gwt-ButtonCustom");
		flexTable.setWidget(2, 1, loginButton);
		loginButton.setSize("100%", "25px");
		flexTable.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable.getCellFormatter().setVerticalAlignment(2, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		
		loginLabel = new Label("");
		verticalPanel.add(loginLabel);
		loginLabel.setSize("", "20px");
		loginLabel.setStylePrimaryName("gwt-LabelErr");
		loginLabel.setStyleName("gwt-LabelErr");
		loginLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		
		FlexTable flexTable_1 = new FlexTable();
		verticalPanel.add(flexTable_1);
		flexTable_1.setSize("320px", "186px");
		
		Label lblLogin_1 = new Label("LOGIN");
		lblLogin_1.setStyleName("gwt-LabelCustom");
		flexTable_1.setWidget(0, 0, lblLogin_1);
		
		registerUsername = new TextBox();
		flexTable_1.setWidget(0, 1, registerUsername);
		registerUsername.setWidth("170px");
		
		Label lblHaso_1 = new Label("HASŁO");
		lblHaso_1.setStyleName("gwt-LabelCustom");
		flexTable_1.setWidget(1, 0, lblHaso_1);
		
		registerPass = new PasswordTextBox();
		flexTable_1.setWidget(1, 1, registerPass);
		registerPass.setWidth("170px");
		registerPass.setStyleName("gwt-TextBox");
		
		Label lblPowtrzHaso = new Label("POWTÓRZ HASŁO");
		lblPowtrzHaso.setStyleName("gwt-LabelCustom");
		flexTable_1.setWidget(2, 0, lblPowtrzHaso);
		lblPowtrzHaso.setWidth("150px");
		lblPowtrzHaso.setWordWrap(false);
		
		registerPassConf = new PasswordTextBox();
		flexTable_1.setWidget(2, 1, registerPassConf);
		registerPassConf.setWidth("170px");
		registerPassConf.setStyleName("gwt-TextBox");
		
		Label lblEmail = new Label("EMAIL");
		lblEmail.setStyleName("gwt-LabelCustom");
		flexTable_1.setWidget(3, 0, lblEmail);
		lblEmail.setWordWrap(false);
		
		registerMail = new TextBox();
		flexTable_1.setWidget(3, 1, registerMail);
		registerMail.setWidth("170px");
		
		registerWaitImage = new Image("images/loadingicon.gif");
		registerWaitImage.setVisible(false);
		flexTable_1.setWidget(4, 0, registerWaitImage);
		registerWaitImage.setSize("25px", "25px");
		
		registerButton = new Button("Zarejestruj");
		registerButton.setStyleName("gwt-ButtonCustom");
		flexTable_1.setWidget(4, 1, registerButton);
		registerButton.setSize("100%", "25px");
		flexTable_1.getCellFormatter().setVerticalAlignment(4, 1, HasVerticalAlignment.ALIGN_MIDDLE);
		flexTable_1.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable_1.getCellFormatter().setHorizontalAlignment(1, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable_1.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable_1.getCellFormatter().setHorizontalAlignment(3, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable_1.getCellFormatter().setHorizontalAlignment(4, 0, HasHorizontalAlignment.ALIGN_RIGHT);
		flexTable_1.getCellFormatter().setVerticalAlignment(4, 0, HasVerticalAlignment.ALIGN_MIDDLE);
		
		registerLabel = new Label("");
		verticalPanel.add(registerLabel);
		registerLabel.setHeight("20px");
		registerLabel.setStylePrimaryName("gwt-LabelErr");
		registerLabel.setStyleName("gwt-LabelErr");
		registerLabel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
	}
	protected TextBox getRegisterMail() {
		return registerMail;
	}
	protected PasswordTextBox getLoginPass() {
		return loginPass;
	}
	protected TextBox getLoginUsername() {
		return loginUsername;
	}
	protected TextBox getRegisterUsername() {
		return registerUsername;
	}
	protected Label getRegisterLabel() {
		return registerLabel;
	}
	protected Label getLoginLabel() {
		return loginLabel;
	}
	protected PasswordTextBox getRegisterPassConf() {
		return registerPassConf;
	}
	protected PasswordTextBox getRegisterPass() {
		return registerPass;
	}
	protected Button getLoginButton() {
		return loginButton;
	}
	protected Button getRegisterButton() {
		return registerButton;
	}
	protected Image getRegisterWaitImage() {
		return registerWaitImage;
	}
}
