package com.PaymentService.PaymentService.Services;


import com.PaymentService.PaymentService.Entities.UserPayments;
import com.PaymentService.PaymentService.Repos.UserSubcriptionsRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.logging.Logger;

@Service
public class SubcriptionsService {

    private final UserSubcriptionsRepository userSubcriptionsRepository;

    private RazorpayClient client;

    @Value("${razorpay.key}")
    private String key;

    @Value("${razorpay.key_secret}")
    private String key_secret;



    public SubcriptionsService(UserSubcriptionsRepository userSubcriptionsRepository) {
        this.userSubcriptionsRepository = userSubcriptionsRepository;
    }

    public UserPayments getSubcriptionById(Integer id){
        UserPayments user = userSubcriptionsRepository.findById(id).
                orElseThrow(()-> new RuntimeException("User is not a subcriptioned one"));
        return user;

    }


    public UserPayments addNewSubcriptions(UserPayments userPayments) throws Exception {
        JSONObject orderReq = new JSONObject();
        orderReq.put("amount", userPayments.getAmountPaid()*100);
        orderReq.put("currency","INR");
        orderReq.put("receipt",userPayments.getEmailId());
        this.client = new RazorpayClient(key,key_secret);

         Order order =  client.orders.create(orderReq);

         System.out.println(" the is the razorPay paths ");
         System.out.println(order);
         userPayments.setRazorPayStatus(order.get("status"));
         userPayments.setRazorPayOrderId(order.get("id"));
        UserPayments user = userSubcriptionsRepository.save(userPayments);
        System.out.println("user Subcription is added Succesfully in the Database");
        return user;

    }

    public UserPayments updateUserSubcriptions(UserPayments userPayments){
        UserPayments  user = userSubcriptionsRepository.save(userPayments);
        System.out.println("user Subcription is added Succesfully update with new pay plans and all");
        return user;
    }


    public boolean checkSubcriptionOfUser(Integer id) {
        UserPayments user = userSubcriptionsRepository.findById(id).
                orElseThrow(()-> new RuntimeException("User is not a subcriptioned one"));
        Date currentDate  = Date.valueOf(LocalDate.now());
//        System.out.println(" the values of the expiration date is " + user.getExpirationDate());
        return currentDate.before(user.getExpirationDate());

    }
}
