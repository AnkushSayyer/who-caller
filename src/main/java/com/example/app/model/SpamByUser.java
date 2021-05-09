package com.example.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.example.app.appuser.AppUser;

@Entity
public class SpamByUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "appUser_id")
	private AppUser appUser;
	@ManyToOne
	@JoinColumn(name = "spam_id")
	private SpamContact spam;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public AppUser getAppUser() {
		return appUser;
	}
	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}
	public SpamContact getSpam() {
		return spam;
	}
	public void setSpam(SpamContact spam) {
		this.spam = spam;
	}
}
