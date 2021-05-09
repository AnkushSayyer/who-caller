package com.example.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.app.model.UserContact;

@Repository
public interface UserContactRepository extends JpaRepository<UserContact, Long> {

	List<UserContact> findByAppUser_Id(Long userId);

	@Query(value="select count(*) from user_contact where app_user_id =:userId and phone_no =:phoneNo", nativeQuery=true)
	int contactOfUser(@Param("userId") Long userId, @Param("phoneNo") String phoneNo);

	@Query(value="select name from user_contact where phone_no =:phoneNo limit 1", nativeQuery=true)
	String getNameByPhoneNo(@Param("phoneNo") String phoneNo);

	@Query(value="select name from user_contact where phone_no =:phoneNo", nativeQuery=true)
	List<String> getNamesByPhoneNo(@Param("phoneNo") String phoneNo);
}
