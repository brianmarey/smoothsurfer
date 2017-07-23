package com.careydevelopment.smoothsurfer.model;

import org.springframework.data.domain.Slice;

import com.careydevelopment.smoothsurfer.util.Constants;

public class PaginatedRestResponse<T> extends RestResponse<T>{

	private int pageNumber = 0;
	private int resultsPerPage = 12;
	private int totalPages = 0;
	private boolean isFirstPage = true;
	private boolean isLastPage = false;
	private int nextPageNumber = -1;
	private int previousPageNumber = -1;
	private int startingResult = 0;
	private int endingResult = 0;
	
	public PaginatedRestResponse(Slice<T> slice, int page) {
		super(slice);
		
		this.isFirstPage = slice.isFirst();
		this.isLastPage = slice.isLast();
		this.pageNumber = page;
		
		if (!slice.isLast()) {
			this.nextPageNumber = page + 1;
		}
		
		if (!slice.isFirst()) {
			this.previousPageNumber = page - 1;
		}	
		
		setStartingAndEndingPage();
	}
	
	private void setStartingAndEndingPage() {
		this.startingResult = (pageNumber * this.resultsPerPage) + 1;
		this.endingResult = startingResult + this.getResults().size() - 1;
	}
	
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getResultsPerPage() {
		return resultsPerPage;
	}
	public void setResultsPerPage(int resultsPerPage) {
		this.resultsPerPage = resultsPerPage;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isFirstPage() {
		return isFirstPage;
	}

	public void setFirstPage(boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public boolean isLastPage() {
		return isLastPage;
	}

	public void setLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public int getNextPageNumber() {
		return nextPageNumber;
	}

	public void setNextPageNumber(int nextPageNumber) {
		this.nextPageNumber = nextPageNumber;
	}

	public int getPreviousPageNumber() {
		return previousPageNumber;
	}

	public void setPreviousPageNumber(int previousPageNumber) {
		this.previousPageNumber = previousPageNumber;
	}

	public int getStartingResult() {
		return startingResult;
	}

	public void setStartingResult(int startingResult) {
		this.startingResult = startingResult;
	}

	public int getEndingResult() {
		return endingResult;
	}

	public void setEndingResult(int endingResult) {
		this.endingResult = endingResult;
	}
}
