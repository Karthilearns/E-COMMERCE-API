package com.example.ecom.repository;

import com.example.ecom.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders,String>
{
    @Query(value = "select * from Orders where seller_email=?1", nativeQuery = true)
    public List<Orders> getAllBySellerEmail(String sellerEmail);

    @Query(value = "update orders set status=?1", nativeQuery = true)
    public boolean updateStatus(String status);
}
