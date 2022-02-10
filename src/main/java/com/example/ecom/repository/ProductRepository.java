package com.example.ecom.repository;

import com.example.ecom.model.MiniOrders;
import com.example.ecom.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Products, String>
{


}
