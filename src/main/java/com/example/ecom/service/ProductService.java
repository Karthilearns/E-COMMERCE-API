package com.example.ecom.service;

import com.example.ecom.model.Products;
import com.example.ecom.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProductService
{
    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<String> addProduct(Products products)
    {
        try {
            productRepository.save(products);

        }
        catch(Exception e)
        {
           e.printStackTrace();
        }
        return new ResponseEntity<>("product saved", HttpStatus.CREATED);
    }

}
