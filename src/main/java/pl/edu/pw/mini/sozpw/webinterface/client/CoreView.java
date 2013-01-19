package pl.edu.pw.mini.sozpw.webinterface.client;

import pl.edu.pw.mini.sozpw.webinterface.services.LoginService;
import pl.edu.pw.mini.sozpw.webinterface.services.LoginServiceAsync;
import pl.edu.pw.mini.sozpw.webinterface.ui.LoginUI;
import pl.edu.pw.mini.sozpw.webinterface.ui.MainUI;
import pl.edu.pw.mini.sozpw.webinterface.ui.WrapperUI;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;

public class CoreView implements EntryPoint {

	private static LoginServiceAsync loginService = (LoginServiceAsync) GWT.create(LoginService.class);
	private static final WrapperUI wrapper = new WrapperUI();
	private static final int margin = 155;

	@Override
	public void onModuleLoad() {
		wrapper.getVerticalPanel().setWidth("100%");
		wrapper.getVerticalPanel().setHeight(
				(Window.getClientHeight() - margin) + "px");
		
		Window.addResizeHandler(new ResizeHandler() {
			public void onResize(ResizeEvent event) {
				int height = event.getHeight();
				int width = event.getWidth();
				if(height < 600){
					height = 600;
				}
				if(width < 800){
					width = 800;
				}
				wrapper.getVerticalPanel().setHeight((height - margin) + "px");
				wrapper.getVerticalPanel().setWidth(width + "px");
			}
		});

		RootPanel.get("mp_page_content_center").add(wrapper);

		start();

	}
	
	public static void start(){
		loginService.getUserFromSession(new AsyncCallback<String>() {

			@Override
			public void onSuccess(String result) {
				if (result != null) {
					wrapper.getVerticalPanel().clear();
					wrapper.getVerticalPanel().add(new MainUI(result));
				} else {
					wrapper.getVerticalPanel().clear();
					wrapper.getVerticalPanel().setHeight(
							(Window.getClientHeight() - margin) + "px");
					wrapper.getVerticalPanel().add(new LoginUI());
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("RPC loginService.getUserFromSession() failed.");
			}
		});
	}

}
