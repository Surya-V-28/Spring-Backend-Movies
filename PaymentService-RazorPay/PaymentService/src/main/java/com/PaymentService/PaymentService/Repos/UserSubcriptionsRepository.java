package com.PaymentService.PaymentService.Repos;


import com.PaymentService.PaymentService.Entities.UserPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSubcriptionsRepository extends JpaRepository<UserPayments, Integer> {
}
