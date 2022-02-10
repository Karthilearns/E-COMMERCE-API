package com.example.ecom.repository;

import com.example.ecom.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Products, UUID>
{

}
