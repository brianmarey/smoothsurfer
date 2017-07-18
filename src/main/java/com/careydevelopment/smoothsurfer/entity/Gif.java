package com.careydevelopment.smoothsurfer.entity;

import java.util.List;

import org.springframework.data.annotation.Id;


public class Gif {

    @Id
    public String id;

    public String link;
    public List<String> tags;

    public Gif() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}
}

