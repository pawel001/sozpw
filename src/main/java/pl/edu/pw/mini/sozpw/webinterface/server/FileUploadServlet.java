package pl.edu.pw.mini.sozpw.webinterface.server;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import pl.edu.pw.mini.sozpw.webinterface.utils.FileUploadProperties;

@SuppressWarnings("serial")
public class FileUploadServlet extends HttpServlet {

	public static final int MAX_FILE_SIZE = 1024 * 1024;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("FileServlet doPost() method");
		response.setContentType("text/html;charset=UTF-8");

		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (isMultipart) {
			try {
				ServletFileUpload upload = new ServletFileUpload();
				FileItemIterator iter = upload.getItemIterator(request);

				while (iter.hasNext()) {

					FileItemStream item = iter.next();

					if (!item.isFormField()) {
						
						System.out.println(item.getName());
						System.out.println(item.getName().replaceAll("^.*[\\/]", ""));

						InputStream stream = item.openStream();
						ByteArrayOutputStream out = new ByteArrayOutputStream();

						byte[] bytes = new byte[1024];
						int read;

						while ((read = stream.read(bytes)) != -1) {
							out.write(bytes, 0, read);
							
							if (out.size() > MAX_FILE_SIZE) {
								response.getWriter().write(FileUploadProperties.SUBMIT_TOO_LARGE);
								response.getWriter().flush();
								response.getWriter().close();
								return;
							}
						}
						
						request.getSession().setAttribute(item.getName().replaceAll("^.*[\\/]", ""), out.toByteArray());
						response.getWriter().write(FileUploadProperties.SUBMIT_SUCCESS);
						response.getWriter().flush();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}