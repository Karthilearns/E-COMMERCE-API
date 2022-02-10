package com.example.ecom.service;

import com.example.ecom.model.Orders;
import com.example.ecom.model.User;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService
{
    @Autowired
    UserRepository userepo;

    @Autowired
    OrderRepository orderRepository;


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

     public void placeOrder(Orders orders)
     {
         orderRepository.save(orders);
     }

}
