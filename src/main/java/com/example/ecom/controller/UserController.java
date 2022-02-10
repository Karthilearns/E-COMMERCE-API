package com.example.ecom.controller;


import com.example.ecom.model.Orders;
import com.example.ecom.model.Products;
import com.example.ecom.model.User;
import com.example.ecom.repository.OrderRepository;
import com.example.ecom.repository.ProductRepository;
import com.example.ecom.repository.UserRepository;
import com.example.ecom.service.UserService;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

import com.example.ecom.model.UserLoginEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserRepository userrepo;

    @Autowired
    UserService userService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    VerificationMailerEntity verificationMailerEntity = new VerificationMailerEntity();

    @ResponseBody
    @GetMapping(value = "/sayhi")
    public ResponseEntity<String> home() {
        return new ResponseEntity<>("hi", HttpStatus.ACCEPTED);
    }

    @ResponseBody
    @GetMapping(value = "/sayhijson", produces = {"application/json", "application/xml"})
    public ResponseEntity<String> homejson() {
        return new ResponseEntity<>("hi", HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/signup", consumes = {"application/json", "application/xml"})
    public ResponseEntity<String> signup(@RequestBody User user) {
        String token = userService.getTokenByEmail(user.getEmail());
        if (token != null) {
            return new ResponseEntity<>("user already registered with this email id", HttpStatus.ALREADY_REPORTED);
        }
        final String encodedPassword = org.apache.commons.codec.binary.Base64
                .encodeBase64String((user.getPassword()).getBytes());
        System.out.println(encodedPassword);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        user.setDob(now.toString());
        user.setEmailVerified("N");
        user.setPhoneNumberVerified("N");
        user.setPassword(encodedPassword);
        UUID otp = UUID.randomUUID();
        user.setToken(otp.toString());
        try {
            userrepo.save(user);
            verificationMailerEntity.sendEmail(user.getEmail(), otp.toString());
        } catch (Exception exception) {
            return new ResponseEntity<String>(new String("User not created, Please try again"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Created", HttpStatus.CREATED);
    }

    @GetMapping(value = "/signin")
    public ResponseEntity<String> signInUser(@RequestBody UserLoginEntity userLoginEntity, HttpServletRequest request) {
        HttpSession session = request.getSession();
        try {
            System.out.print(userLoginEntity.getEmail());
            User user = userService.getUserById(userLoginEntity.getEmail());
            System.out.print(user.toString());
            if (user.equals(null)) {
                throw new UserPrincipalNotFoundException("User not found");
            }
            userLoginEntity.setIsVerified(user.getEmailVerified());
            String passwordEntered = userLoginEntity.getPassword();
            String passwordActuall = new String(java.util.Base64.getDecoder().decode(user.getPassword()));

            if (passwordEntered.equals(passwordActuall) && userLoginEntity.getIsVerified().equals("Y")) {
                session.setAttribute("isAuth", "Y");
                session.setAttribute("email",userLoginEntity.getEmail());
                return new ResponseEntity<>("LOGGED IN SUCCESSFULLY", HttpStatus.ACCEPTED);
            } else if (passwordEntered.equals(passwordActuall) && userLoginEntity.getIsVerified().equals("N")) {
                return new ResponseEntity<>("NOT VERIFIED, PLEASE VERIFY YOUR EMAIL", HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity<String>("Password incorrect", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("INVALID", HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/verify-email/{otp}")
    public ResponseEntity<String> verifyEmail(@PathVariable String otp) {
        String[] payload = new String(java.util.Base64.getDecoder().decode(otp)).split(":");
        System.out.println(payload[0] + "    " + payload[1]);
        String savedToken = userService.getTokenByEmail(payload[0]);
        if (savedToken.equals(payload[1])) {
            userService.updateEmailVerified(payload[0]);
        } else {
            return new ResponseEntity<>("wrong activation link", HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("ok", HttpStatus.ACCEPTED);
    }


    @GetMapping(value = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("isAuth");
        session.invalidate();
        return new ResponseEntity<>("Logged Out", HttpStatus.ACCEPTED);
    }

    @PostMapping(value = "/placeorder")
    public ResponseEntity<Orders> placeOrder(@RequestBody Orders orders)
    {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            orders.setDate(now.toString());
            orders.setStatus("ordered");
            userService.placeOrder(orders);
            productRepository.updateOrdersPlaced(orders.getSellerEmail(),orders.getProduct_name());
            return new ResponseEntity<>(orders,HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return new ResponseEntity<>(orders,HttpStatus.BAD_REQUEST);

    }
    @GetMapping(value = "/getmyorders")
    public ResponseEntity<List<Orders>> getMyOrders(HttpServletRequest request)
    {
        HttpSession session = request.getSession();
        System.out.println((String)session.getAttribute("email"));
        List<Orders> orders = orderRepository.getAllByCustomer_email((String) session.getAttribute("email"));

        return new ResponseEntity<>(orders,HttpStatus.ACCEPTED);

    }

    @GetMapping(value = "/getproducts")
    public ResponseEntity<List<Products>> getProducts(@RequestParam String filter)
    {
       List<Products> products = productRepository.getProducts(filter,filter);
       return new ResponseEntity<>(products, HttpStatus.ACCEPTED);
    }





}