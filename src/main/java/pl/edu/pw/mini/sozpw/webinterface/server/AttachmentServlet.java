package pl.edu.pw.mini.sozpw.webinterface.server;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.edu.pw.mini.sozpw.dataaccess.model.Model;
import pl.edu.pw.mini.sozpw.dataaccess.model.ModelImpl;

@SuppressWarnings("serial")
public class AttachmentServlet extends HttpServlet {

	Model model = new ModelImpl();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			byte[] file = model.getAttachment(Integer.parseInt(request
					.getParameter("noteId")));
			if(file != null){
				String fileName = request.getParameter("filename");
	
				response.setContentType("application/octet-stream");
				response.setContentLength(file.length);
				response.setHeader("Content-Disposition", "attachment; filename=\""
						+ fileName + "\"");
	
				byte[] byteBuffer = new byte[1024];
				DataInputStream in = new DataInputStream(new ByteArrayInputStream(
						file));
	
				int length = 0;
				ServletOutputStream outStream = response.getOutputStream();
	
				while ((in != null) && ((length = in.read(byteBuffer)) != -1)) {
					outStream.write(byteBuffer, 0, length);
				}
				
				in.close();
				outStream.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}