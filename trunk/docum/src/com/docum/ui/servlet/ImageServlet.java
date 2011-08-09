package com.docum.ui.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.docum.service.ApplicationConfigService;

public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = -4517424990173918539L;
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	private String imagePath;
	private ApplicationConfigService configService;

	public void init() throws ServletException {

		WebApplicationContext springContext = WebApplicationContextUtils
				.getWebApplicationContext(getServletContext());
		configService = (ApplicationConfigService) springContext
				.getBean("applicationConfigService");
		// Define base path somehow. You can define it as init-param of the
		// servlet.
		imagePath = configService.getImagesStoragePath();

		// In a Windows environment with the Applicationserver running on the
		// c: volume, the above path is exactly the same as "c:\images".
		// In UNIX, it is just straightforward "/images".
		// If you have stored files in the WebContent of a WAR, for example in
		// the
		// "/WEB-INF/images" folder, then you can retrieve the absolute path by:
		// this.imagePath = getServletContext().getRealPath("/WEB-INF/images");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String requestedImage = request.getPathInfo();
		if (requestedImage == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			return;
		}
		File image = new File(imagePath, URLDecoder.decode(requestedImage, "UTF-8"));
		if (!image.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			return;
		}
//TODO Странно, но *.JPG файл вернул contentType==null
		String contentType = getServletContext().getMimeType(image.getName());
		if (contentType == null || !contentType.startsWith("image")) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			return;
		}

		response.reset();
		response.setBufferSize(DEFAULT_BUFFER_SIZE);
		response.setContentType(contentType);
		response.setHeader("Content-Length", String.valueOf(image.length()));
		response.setHeader("Content-Disposition", "inline; filename=\"" + image.getName() + "\"");

		BufferedInputStream input = null;
		BufferedOutputStream output = null;

		try {
			input = new BufferedInputStream(new FileInputStream(image), DEFAULT_BUFFER_SIZE);
			output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

			byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			int length;
			while ((length = input.read(buffer)) > 0) {
				output.write(buffer, 0, length);
			}
		} finally {
			close(output);
			close(input);
		}
	}

	private static void close(Closeable resource) {
		if (resource != null) {
			try {
				resource.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
