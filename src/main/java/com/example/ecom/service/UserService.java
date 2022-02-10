package com.example.ecom.service;

import com.example.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.ecom.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    UserRepository userepo;


     public User getUserById(String email)
     {
         return userepo.getUserById(email);
     }

     public String getTokenByEmail(String email){
         return userepo.getTokenByEmail(email);
     }
     public void updateEmailVerified(String email)
     {
         userepo.updateEmailVerified(email);
     }
}
