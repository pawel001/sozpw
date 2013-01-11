package pl.edu.pw.mini.sozpw.webinterface.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.edu.pw.mini.sozpw.dataaccess.model.Model;
import pl.edu.pw.mini.sozpw.dataaccess.model.ModelImpl;

@SuppressWarnings("serial")
public class RegistrationServlet extends HttpServlet {

	public static final String REG_PARAM_NAME = "registerKey";
	public static final String USER_PARAM_NAME = "user";

	Model model = new ModelImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		String key = request.getParameter(REG_PARAM_NAME);
		String user = request.getParameter(USER_PARAM_NAME);
		String result;

		// IMPORTANT! key can be null
		if (model.confirmRegistration(user, key)) {
			result = "Rejestracja zakończona. Przejdź do strony głównej aby się zalogować.";
		} else {
			result = "Nieprawidłowy link.";
		}

		try {
			response.setContentType("text/html;charset=UTF-8");
			printOutputPage(response.getWriter(), result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printOutputPage(PrintWriter out, String msg) {
		out.print("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><html><head><link rel=\"stylesheet\" type=\"text/css\" href=\"main_style.css\"><meta http-equiv=\"refresh\" content=\"5; url=http://quintus.mini.pw.edu.pl/\"><title>System obsługi zorientowanych przestrzennie wiadomości</title></head><body><div id=\"mp_top_bar\"><div id=\"mp_top_bar_center\"><a id=\"mp_top_bar_center_link\" href=\"http://quintus.mini.pw.edu.pl/\"><img src=\"images/logo.png\" /></a></div></div><div id=\"mp_page_content\"><div id=\"mp_page_content_center\"><div style=\"font-family: 'Segoe UI', Tahoma, Helvetica, Sans-Serif; width: 100%; text-align: center; vertical-align: middle; padding-top: 150px;\"><p>"
				+ msg
				+ "</p></div></div></div><div id=\"mp_bottom_bar_small\"></div><div id=\"mp_bottom_bar\"></div></body></html>");
		out.flush();
		out.close();
	}
}
