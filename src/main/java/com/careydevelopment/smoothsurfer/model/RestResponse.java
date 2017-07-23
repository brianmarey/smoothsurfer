package com.careydevelopment.smoothsurfer.model;

import java.util.List;

import org.springframework.data.domain.Slice;

public class RestResponse<T> {

	private String status = "OK";
	private List<T> results;
	
	public RestResponse(Slice<T> slice) {
		results = slice.getContent();
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}
}
