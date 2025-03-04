package com.PaymentService.PaymentService.Controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterMapper {
    @GetMapping("/")
    public String init() {
        return "paymentPage";
    }



}
