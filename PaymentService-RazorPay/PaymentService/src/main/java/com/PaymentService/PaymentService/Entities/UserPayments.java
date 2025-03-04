package com.PaymentService.PaymentService.Entities;


import jakarta.persistence.*;
import lombok.*;


import java.sql.Date;


@Builder
@Getter
@ToString
@Setter
@Entity
@Table(name = "userPayments")
public class UserPayments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer orderId;

    private String razorPayStatus;
    private String razorPayOrderId;

    @Column(nullable = false)
    private String emailId;
    @Column(nullable = false)
    private String  subcriptionType;
    @Column(nullable = false)
    private Integer amountPaid;
    @Column(nullable = false)
    private Integer UserId;
    @Column(nullable = false)
    private Date PaymentDate;
    @Column(nullable = false)
    private Date expirationDate;
}
