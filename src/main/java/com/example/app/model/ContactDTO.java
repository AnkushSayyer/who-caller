package com.example.app.model;

public class ContactDTO extends Contact {

	private String email;
	private long reportedBy;

	public ContactDTO() {
		super();
	}

	public ContactDTO(String phone, String name) {
		super(phone, name);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(long reportedBy) {
		this.reportedBy = reportedBy;
	}
}
