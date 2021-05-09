package com.example.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SpamContact {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String phoneNo;
	private long spam;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPhone() {
		return phoneNo;
	}
	public void setPhone(String phone) {
		this.phoneNo = phone;
	}
	public long getSpam() {
		return spam;
	}
	public void setSpam(long spam) {
		this.spam = spam;
	}

	public void incrementSpamCount() {
		spam++;
	}
}
