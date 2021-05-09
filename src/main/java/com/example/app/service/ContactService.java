package com.example.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.appuser.AppUser;
import com.example.app.appuser.AppUserRepository;
import com.example.app.model.Contact;
import com.example.app.model.ContactDTO;
import com.example.app.model.SpamByUser;
import com.example.app.model.SpamContact;
import com.example.app.model.UserContact;
import com.example.app.repository.SpamByUserRepository;
import com.example.app.repository.SpamContactRepository;
import com.example.app.repository.UserContactRepository;

@Service
public class ContactService {

	@Autowired
	private UserContactRepository userContactRepository;
	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private SpamContactRepository spamContactRepository;
	@Autowired
	private SpamByUserRepository spamByUserRepository;

	public String uploadUserContacts(Long userId, List<Contact> contacts) {
		AppUser user = new AppUser(userId);

		List<UserContact> userContacts = contacts.stream()
				.map(contact -> new UserContact(user, contact.getPhoneNo(), contact.getName()))
				.collect(Collectors.toList());

		userContactRepository.saveAll(userContacts);
		return "success";
	}

	public List<Contact> fetchContacts(Long userId) {
		return userContactRepository.findByAppUser_Id(userId).stream()
			.map(userContact -> new Contact(userContact.getPhoneNo(), userContact.getName()))
			.collect(Collectors.toList());
	}

	public List<Contact> find(Long userId, String phoneNo){
		List<Contact> contacts = null;
		AppUser regContact = appUserRepository.findByPhoneNo(phoneNo).orElse(null);

		if(regContact == null) {
			contacts = userContactRepository.getNamesByPhoneNo(phoneNo).stream()
				.map(name -> new Contact(phoneNo, name))
				.collect(Collectors.toList());
		}
		else
			contacts = Arrays.asList(new Contact(phoneNo, regContact.getUsername()));

		return contacts;
	}

	public ContactDTO getDetails(Long userId, String phone) {
		ContactDTO contactDto = new ContactDTO();
		contactDto.setPhoneNo(phone);
		AppUser regContact = appUserRepository.findByPhoneNo(phone).orElse(null);
		AppUser user = appUserRepository.findById(userId).orElse(null);

		if(regContact != null) {
			if(isContactOf(regContact.getId(), user.getPhoneNo())) {
				contactDto.setName(regContact.getUsername());
				contactDto.setEmail(regContact.getEmail());
			}
		}
		else {
			String name = userContactRepository.getNameByPhoneNo(phone);
			contactDto.setName(name);
		}

		Optional<SpamContact> optionalSpamInfo = spamContactRepository.findByPhoneNo(phone);
		SpamContact spamInfo = optionalSpamInfo.isPresent() ? optionalSpamInfo.get() : null;
		if(spamInfo != null)
			contactDto.setReportedBy(spamInfo.getSpam());

		return contactDto;
	}

	private boolean isContactOf(Long userId, String phoneNo) {
		return userContactRepository.contactOfUser(userId, phoneNo) > 0;
	}

	public List<Contact> markedSpamByUser(Long userId) {
		List<Contact> spamContactsByUser = new ArrayList<>();
		List<SpamByUser> spamByUser = spamByUserRepository.findByAppUser_Id(userId);

		for(SpamByUser spam : spamByUser) {
			String spamNo = spam.getSpam().getPhone();
			AppUser regContact = appUserRepository.findByPhoneNo(spamNo).orElse(null);
			String name = "";
			if(regContact == null) {
				name = userContactRepository.getNameByPhoneNo(spamNo);
			}
			else
				name = regContact.getUsername();

			spamContactsByUser.add(new Contact(spamNo, name));
		}
		return spamContactsByUser;
	}

	public String markSpam(Long userId, String phone) {
		AppUser appUser = appUserRepository.findById(userId).orElse(null);
		SpamContact spamContact = spamContactRepository.findByPhoneNo(phone).orElse(null);

		if(spamContact != null && isAlreadyMarked(userId, spamContact.getId()))
			return "Already Marked";

		if(spamContact == null) {
			spamContact = new SpamContact();
			spamContact.setPhone(phone);
		}
		spamContact.incrementSpamCount();
		spamContactRepository.save(spamContact);

		SpamByUser spamByUser = new SpamByUser();
		spamByUser.setAppUser(appUser);
		spamByUser.setSpam(spamContact);
		spamByUserRepository.save(spamByUser);

		return "Marked spam";
	}

	private boolean isAlreadyMarked(Long userId, Long spamContactId) {
		return spamByUserRepository.isMarkedSpam(userId, spamContactId) > 0;
	}
}
