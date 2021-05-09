package com.example.app.appuser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

	Optional<AppUser> findByPhoneNo(String phoneNo);

	@Query(value="select phone_no from app_user where id =:userId", nativeQuery=true)
	String getPhoneNoById(@Param("userId") Long userId);
}
