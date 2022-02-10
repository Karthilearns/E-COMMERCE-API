package com.example.ecom.repository;

import com.example.ecom.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders,String>
{
    @Query(value = "select * from Orders where seller_email=?1 and payment_status='Y'", nativeQuery = true)
    public List<Orders> getAllBySellerEmail(String sellerEmail);

    @Query(value = "select * from Orders where customer_email=?1", nativeQuery = true)
    public List<Orders> getAllByCustomer_email(String customerEmail);

    @Transactional
    @Modifying
    @Query(value = "update orders set status=?1 where id=?2", nativeQuery = true)
    public int updateStatus(String status,String id);

}
