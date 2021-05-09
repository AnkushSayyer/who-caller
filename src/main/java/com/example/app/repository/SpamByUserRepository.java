package com.example.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.app.model.SpamByUser;

@Repository
public interface SpamByUserRepository extends JpaRepository<SpamByUser, Long>{

	List<SpamByUser> findByAppUser_Id(Long userId);

	@Query(value="select count(*) from spam_by_user where id =:userId and spamId =:spamId", nativeQuery=true)
	int isMarkedSpam(Long userId, Long spamId);
}
