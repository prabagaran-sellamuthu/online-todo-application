package com.praba.onlinetodoapp.controller.dto;


import javax.validation.constraints.NotEmpty;

public class TodoDto {

	@NotEmpty
    private String title;
	
	@NotEmpty
	private String description;
	
	
	@NotEmpty
	private String status;
	
	
	@NotEmpty
	private String targetDate;

	@NotEmpty
	private String lastUpdatedDate;

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTargetDate() {
		return targetDate;
	}

	public void setTargetDate(String targetDate) {
		this.targetDate = targetDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	public String getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(String lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@Override
	public String toString() {
		return "TodoDto [title=" + title + ", description=" + description + ", status=" + status + ", targetDate="
				+ targetDate + ", lastUpdatedDate=" + lastUpdatedDate + "]";
	}
	
}
