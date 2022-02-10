package com.example.ecom.repository;

import com.example.ecom.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, String>
{

    @Query(value = "select * from Products where product_name = ?1 or category = ?2",nativeQuery = true)
    public List<Products> getProducts(String filter1, String filter2);


    @Modifying
    @Transactional
    @Query(value = "update products p set p.orders_placed =((select orders_placed as op from (select * from products) pp where pp.seller_email=?1 and pp.product_name =?2)+1) where p.seller_email=?1 and p.product_name=?2",nativeQuery = true)
    public int updateOrdersPlaced(String seller_email,String product_name);
}
