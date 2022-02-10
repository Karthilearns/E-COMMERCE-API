package com.example.ecom.repository;

import com.example.ecom.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface SellerRepository extends JpaRepository<Seller,String> {
    @Query(value = "select * from seller_profile where email = ?1",nativeQuery = true)
    public Seller getUserById(String email);

    @Query(value = "select token from seller_profile where email=?1", nativeQuery = true)
    public String getTokenByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "update seller_profile set email_verified = 'Y' where email=?",nativeQuery = true)
    public void updateEmailVerified(String email);
}
