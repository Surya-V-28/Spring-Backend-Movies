package com.PaymentService.PaymentService.Controllers;

import com.PaymentService.PaymentService.Entities.UserPayments;
import com.PaymentService.PaymentService.Repos.UserSubcriptionsRepository;
import com.PaymentService.PaymentService.Services.SubcriptionsService;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.time.LocalDate;

@RestController
@RequestMapping("/payments")
@CrossOrigin
public class UserPaymentController {

    private final SubcriptionsService subcriptionsService;
    private final UserSubcriptionsRepository userSubcriptionsRepository;



    public UserPaymentController(SubcriptionsService subcriptionsService, UserSubcriptionsRepository userSubcriptionsRepository) {
        this.subcriptionsService = subcriptionsService;
        this.userSubcriptionsRepository = userSubcriptionsRepository;
    }

    @GetMapping("/checkSubscription")
    public boolean checkSubcriptionOfUser(Integer id){
        return subcriptionsService.checkSubcriptionOfUser(id);
    }

    @PostMapping
    public UserPayments addNewSubcription(
            @RequestParam Integer id,@RequestParam String emailId,@RequestParam String SubcriptionType
            ,@RequestParam Integer amount

    ) throws Exception {
                UserPayments userBuilder = UserPayments.builder().
              emailId(emailId).
                subcriptionType(SubcriptionType).
                amountPaid(amount).
                PaymentDate(Date.valueOf(LocalDate.now())).
                expirationDate(Date.valueOf(LocalDate.now().plusDays(45))).
                UserId(id).
                build();
        UserPayments user=  subcriptionsService.addNewSubcriptions(userBuilder);
        return user;
    }

    @GetMapping("/{id}")
    public UserPayments getNewScription(@PathVariable  Integer id){
         UserPayments user = subcriptionsService.getSubcriptionById(id);
        return user;
    }


}
