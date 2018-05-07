package com.example.demo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {

	private final AtomicLong counter = new AtomicLong();
	private static final String EXTERNAL_FILE_PATH = "C:/demoFiles/";

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome() {

		return "Welcome! This is the first App whit Spring Boot ";
	}

	@RequestMapping(value = "/file", method = RequestMethod.GET)
	public List<FileContent> files() throws IOException {
		List<FileContent> fileContent = new ArrayList<>();
		File dir = new File("c:/demoFiles");
		String[] children = dir.list();
		for (int i = 0; i < children.length; i++) {
			fileContent.add(new FileContent(counter.incrementAndGet(), children[i]));
		}
		return fileContent;
	}

	@RequestMapping(value = "/file/{name}", method = RequestMethod.GET)
	public FileContenuto file(@PathVariable String name) throws IOException {
		String content = "";
		BufferedReader reader = new BufferedReader(new FileReader("C:/demoFiles/" + name));
		String line = reader.readLine();
		while (line != null) {
			content = content + "" + line;
			line = reader.readLine();
		}
		reader.close();
		return new FileContenuto(counter.incrementAndGet(), name, content);

	}

	@RequestMapping("/file/download/{fileName:.+}")
	public void downloadResource(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("fileName") String fileName) throws IOException {

		File file = new File(EXTERNAL_FILE_PATH + fileName);
		if (file.exists()) {

			// get the mimetype
			String mimeType = URLConnection.guessContentTypeFromName(file.getName());

			if (mimeType == null) {
				// unknown mimetype so set the mimetype to
				// application/octet-stream
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);

			/**
			 * In a regular HTTP response, the Content-Disposition response
			 * header is a header indicating if the content is expected to be
			 * displayed inline in the browser, that is, as a Web page or as
			 * part of a Web page, or as an attachment, that is downloaded and
			 * saved locally.
			 * 
			 */

			/**
			 * Here we have mentioned it to show inline
			 */
			response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

			// Here we have mentioned it to show as attachment
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

			FileCopyUtils.copy(inputStream, response.getOutputStream());

		}
	}

}
