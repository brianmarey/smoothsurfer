package com.careydevelopment.smoothsurfer.entity;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;


public class Images {

    @Id
    public String id;

    public String image;
    public String category;
    public Date date;
    public List<String> keys;

    public Images() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

}

