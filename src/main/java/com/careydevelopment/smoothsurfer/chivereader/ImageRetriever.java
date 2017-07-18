package com.careydevelopment.smoothsurfer.chivereader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageRetriever {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImageRetriever.class);
	
	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	private URL url;
	private List<String> images = new ArrayList<String>();
	private Date date = new Date();
	
	
	public ImageRetriever(URL url) {
		this.url = url;
	}
	
	public List<String> getImages() {
		if (images.size() == 0) {
			try {
				URLConnection conn = url.openConnection();
				
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
					try (Stream<String> stream = reader.lines()) {
						stream.filter(lineCriteria()).forEach(line ->
						{
							line = line.trim();
							
							if (line.indexOf("datetime") > -1) {
								parseDate(line);
							} else {
								parseImage(line);
							}
						});
					}
				}
			} catch (Exception e) {
				LOGGER.error("Problem reading URL " + url, e);
			}
		}
		
		return images;
	}
	
		
	private void parseImage(String line) {
		int classStart = line.indexOf("attachment-gallery-item-full");
		
		while (classStart > -1) {
			int httpStart = line.lastIndexOf("http", classStart);
			
			if (httpStart > -1) {
				int httpEnd = line.indexOf("?", httpStart);
				if (httpEnd == -1) httpEnd = line.indexOf("\"", httpStart);
				
				if (httpEnd > -1) {
					String link = line.substring(httpStart, httpEnd);
					images.add(link);
					
					classStart = line.indexOf("attachment-gallery-item-full", classStart + 10);
				}
			}
		}
	}
	
	
	private Predicate<String> lineCriteria() {
		return line -> line.indexOf("attachment-gallery-item-full") > -1 || line.indexOf("datetime") > -1;
	}
	
	
	private void parseDate(String line) {
		int dateLoc = line.indexOf("datetime");
		int colonLoc = line.indexOf(":", dateLoc);
		
		if (colonLoc > -1) {
			int firstQuote = line.indexOf("\"", colonLoc);
			
			if (firstQuote > -1) {
				int secondQuote = line.indexOf("\"", firstQuote + 1);
				
				if (secondQuote > -1) {
					String dateString = line.substring(firstQuote + 1, secondQuote);
					
					if (dateString.indexOf("T") > -1) {
						dateString = dateString.substring(0, dateString.indexOf("T"));
						
						try {
							date = DATE_FORMAT.parse(dateString);
						} catch (Exception e) {
							LOGGER.warn("Problem parsing " + dateString, e);
						}
					}
				}
			}
		}
	}
	
	
	public Date getDate() {
		return date;
	}
}
