package com.example.ecom.repository;

import com.example.ecom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
     @Query(value = "select * from User where email = ?1",nativeQuery = true)
     public User getUserById(String email);

     @Query(value = "select token from User where email=?1", nativeQuery = true)
     public String getTokenByEmail(String email);

     @Transactional
     @Modifying
     @Query(value = "update user set email_verified = 'Y' where email=?",nativeQuery = true)
     public void updateEmailVerified(String email);

     @Query(value = "select first_name  from user where email=?1",nativeQuery = true)
     public  String getFirstNameByEmail(String email);


     @Query(value = "update user set password=?1 where email=?2",nativeQuery = true)
     void updatePassword(String newpassword,String email);
}
