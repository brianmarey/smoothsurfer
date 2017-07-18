package com.careydevelopment.smoothsurfer.chivereader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LinkRetriever {

	private static final Logger LOGGER = LoggerFactory.getLogger(LinkRetriever.class);
	
	private String url = "http://thechive.com/?s=awesomeness";
	private List<String> links = new ArrayList<String>();
	private String nextPageLink;
	private FetchType fetchType;
	private String anchorSearch = "Morning Awesomeness";
	private String urlSearch = "awesomeness";
	
	
	public enum FetchType {
		MORNING_AWESOMENESS, AFTERNOON_RANDOMNESS, GIRLS;
	}
	
	public LinkRetriever(String url, FetchType fetchType) {
		this.url = url;
		this.fetchType = fetchType;
		
		if (this.fetchType.equals(FetchType.AFTERNOON_RANDOMNESS)) {
			this.anchorSearch = "Afternoon Randomness";
			this.urlSearch = "randomness";
		} else if (this.fetchType.equals(FetchType.GIRLS)) {
			this.anchorSearch = "Photos";
			this.urlSearch = "photos";
		}
	}

	
	public LinkRetriever(FetchType fetchType) {
		this.fetchType = fetchType;
		
		if (fetchType.equals(FetchType.AFTERNOON_RANDOMNESS)) {
			this.url = "http://thechive.com/?s=randomness";
			this.anchorSearch = "Afternoon Randomness";
			this.urlSearch = "randomness";
		} else if (fetchType.equals(FetchType.GIRLS)) {
			this.url = "http://thechive.com/category/girls/";
			this.anchorSearch = "Photos";
			this.urlSearch = "photos";
		}
	}
	
	
	public List<String> getLinks() {
		if (links.size() == 0) {
			try {
				URLConnection conn = new URL(url).openConnection();
				
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
					try (Stream<String> stream = reader.lines()) {
						stream.filter(lineCriteria()).forEach(line ->
						{
							line = line.trim();
							
							if (line.indexOf("Next") > -1) {
								parseNextPageLink(line);
							} else {
								parseLink(line);
							}
						});
					}
				}
			} catch (Exception e) {
				LOGGER.error("Problem reading URL " + url, e);
			}
		}
		
		return links;
	}
	
	
	public String getNextPageLink() {
		if (links.size() == 0) {
			getLinks();
		}
		
		return nextPageLink;
	}
	
	
	private String parseLink(String line) {
		int morningStart = line.indexOf(anchorSearch);
		
		if (morningStart > -1) {
			int httpStart = line.lastIndexOf("http://", morningStart);
			
			if (httpStart > -1) {
				int httpEnd = line.indexOf("\"", httpStart);
				
				if (httpEnd > -1) {
					String link = line.substring(httpStart, httpEnd);
					links.add(link);
				}
			}
		}
		return line;
	}
	
	
	private void parseNextPageLink(String line) {
		int nextLoc = line.indexOf("Next");
		
		if (nextLoc > -1) {
			int httpStart = line.lastIndexOf("http://", nextLoc);
			
			if (httpStart > -1) {
				int httpEnd = line.indexOf("\"", httpStart);
				
				if (httpEnd > -1) {
					nextPageLink = line.substring(httpStart, httpEnd);				
				}				
			}
		}
	}
	
	
	private Predicate<String> lineCriteria() {
		return line -> line.indexOf("<a href") > -1 
				&& !line.startsWith("<li")
				&& (line.indexOf(urlSearch) > -1 || line.indexOf("Next") > -1);
	}
}
