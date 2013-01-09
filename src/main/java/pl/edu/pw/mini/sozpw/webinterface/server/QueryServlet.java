package pl.edu.pw.mini.sozpw.webinterface.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.edu.pw.mini.sozpw.dataaccess.model.Model;
import pl.edu.pw.mini.sozpw.dataaccess.model.ModelImpl;

@SuppressWarnings("serial")
public class QueryServlet extends HttpServlet {

	private static final int MAX_HINTS = 5;

	Model model = new ModelImpl();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) {

		response.setContentType("text/html;charset=UTF-8");
		List<String> names;
		if(request.getParameter("type").equals("all")){
			names= model.getDedicationHints(request.getParameter("q"), MAX_HINTS);
		}else{
			names = model.getGroupsHints(request.getParameter("q"), MAX_HINTS);
		}

		try {
			PrintWriter out = response.getWriter();

			out.println('[');

			for (int i = 0; i < names.size(); i++) {
				out.println("  {");
				out.print("    \"name\": \"");
				out.print(names.get(i));
				out.println("\",");
				out.print("    \"id\": ");
				out.print(i);
				if (i < names.size() - 1) {
					out.println("  },");
				} else {
					out.println("  }");
				}

			}

			out.println(']');
			out.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}