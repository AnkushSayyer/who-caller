package com.example.app.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.example.app.model.Contact;

@Repository
public interface ContactRepository extends PagingAndSortingRepository<Contact, Long> {

}
