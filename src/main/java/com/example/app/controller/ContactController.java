package com.example.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.model.Contact;
import com.example.app.model.ContactDTO;
import com.example.app.service.ContactService;

@RestController
public class ContactController {

	@Autowired
	private ContactService contactService;

	@PostMapping("/user/{userId}/contacts/upload")
	@PreAuthorize("hasRole('USER')")
	public String uploadContacts(@PathVariable Long userId, @RequestBody List<Contact> contacts) {
		return contactService.uploadUserContacts(userId, contacts);
	}

	@GetMapping("/user/{userId}/find")
	@PreAuthorize("hasRole('USER')")
	public List<Contact> find(@PathVariable Long userId, @RequestParam String phone) {
		return contactService.find(userId, phone);
	}

	@GetMapping("/user/{userId}/contacts")
	@PreAuthorize("hasRole('USER')")
	public List<Contact> fetchContacts(@PathVariable Long userId) {
		return contactService.fetchContacts(userId);
	}

	@GetMapping("/user/{userId}/details")
	@PreAuthorize("hasRole('USER')")
	public ContactDTO getDetails(@PathVariable Long userId, @RequestParam String phone) {
		return contactService.getDetails(userId, phone);
	}

	@GetMapping("/user/{userId}/marked-spam")
	@PreAuthorize("hasRole('USER')")
	public List<Contact> markedSpamByUser(@PathVariable Long userId) {
		return contactService.markedSpamByUser(userId);
	}

	@PostMapping("/user/{userId}/spam")
	@PreAuthorize("hasRole('USER')")
	public String markSpam(@PathVariable Long userId, @RequestParam String phone) {
		return contactService.markSpam(userId, phone);
	}
}
