package com.example.ecom.controller;


import com.example.ecom.model.Orders;
import com.example.ecom.model.Products;
import com.example.ecom.model.Seller;
import com.example.ecom.model.SellerLoginEntity;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.repository.ProductRepository;
import com.example.ecom.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/seller")
public class SellerController
{
    @Autowired
    SellerRepository sellerRepository;


    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    SellerVerificationMailerEntity sellerverificationMailerEntity= new SellerVerificationMailerEntity();;


    @PostMapping(value = "/signup")
    public ResponseEntity<String> signup(@RequestBody Seller seller)
    {
        String token = sellerRepository.getTokenByEmail(seller.getEmail());
        if (token != null) {
            return new ResponseEntity<>("Seller already registered with this email id", HttpStatus.ALREADY_REPORTED);
        }
        final String encodedPassword = org.apache.commons.codec.binary.Base64
                .encodeBase64String((seller.getPassword()).getBytes());
        System.out.println(encodedPassword);

        seller.setEmail_verified("N");
        seller.setPhone_verified("N");
        seller.setPassword(encodedPassword);
        UUID otp = UUID.randomUUID();
        seller.setToken(otp.toString());
        try {
            sellerRepository.save(seller);
            sellerverificationMailerEntity.sendEmail(seller.getEmail(), otp.toString());
        } catch (Exception exception) {
            return new ResponseEntity<String>(new String("Seller not created, Please try again"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Seller Created", HttpStatus.CREATED);
    }

    @GetMapping(value = "/verify-email/{otp}")
    public ResponseEntity<String> verifyEmail(@PathVariable String otp) {
        String[] payload = new String(java.util.Base64.getDecoder().decode(otp)).split(":");
        System.out.println(payload[0] + "    " + payload[1]);
        String savedToken = sellerRepository.getTokenByEmail(payload[0]);
        if (savedToken.equals(payload[1])) {
            sellerRepository.updateEmailVerified(payload[0]);
        } else {
            return new ResponseEntity<>("wrong activation link", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/signin")
    public ResponseEntity<String> signInUser(@RequestBody SellerLoginEntity sellerLoginEntity, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            System.out.print(sellerLoginEntity.getEmail());
            Seller seller = sellerRepository.getUserById(sellerLoginEntity.getEmail());
            System.out.print(seller.toString());
            if (seller==null) {
                throw new UserPrincipalNotFoundException("User not found");
            }
            sellerLoginEntity.setIsEmailVerified(seller.getEmail_verified());
            String passwordEntered =sellerLoginEntity.getPassword();
            String passwordActuall = new String(java.util.Base64.getDecoder().decode(seller.getPassword()));

            if (passwordEntered.equals(passwordActuall) && sellerLoginEntity.getIsEmailVerified().equals("Y")) {
                session.setAttribute("isAuth", "Y");
                session.setAttribute("email",sellerLoginEntity.getEmail());
                return new ResponseEntity<>("LOGGED IN SUCCESSFULLY", HttpStatus.ACCEPTED);
            } else if (passwordEntered.equals(passwordActuall) && sellerLoginEntity.getIsEmailVerified().equals("N")) {
                return new ResponseEntity<>("NOT VERIFIED, PLEASE VERIFY YOUR EMAIL", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<String>("Password incorrect", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("INVALID", HttpStatus.ACCEPTED);
    }
    @PostMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("isAuth");
        session.invalidate();
        return new ResponseEntity<>("Logged Out", HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/addproduct")
    public ResponseEntity<String> addproduct(@RequestBody Products product)
    {
        try {
            productRepository.save(product);
            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("NOT SAVED TRY AGAIN", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getmyorders")
    public ResponseEntity<List<Orders>> getMyOrders(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        System.out.println((String)session.getAttribute("email"));
       List<Orders> orders = orderRepository.getAllBySellerEmail((String) session.getAttribute("email"));

       return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);

    }


    @PutMapping(value = "/updateStatus")
    public ResponseEntity<String> updateStatus(@RequestParam String status, String id)
    {
        orderRepository.updateStatus(status,id);
        return new ResponseEntity<>("UPDATED TO "+status, HttpStatus.ACCEPTED);
    }



}
