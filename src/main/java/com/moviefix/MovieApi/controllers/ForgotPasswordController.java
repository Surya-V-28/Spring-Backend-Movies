package com.moviefix.MovieApi.controllers;


import com.moviefix.MovieApi.auth.entities.ForgotPassword;
import com.moviefix.MovieApi.auth.entities.User;
import com.moviefix.MovieApi.auth.repository.ForgotPasswordRepository;
import com.moviefix.MovieApi.auth.repository.UserRepository;
import com.moviefix.MovieApi.auth.service.EmailService;
import com.moviefix.MovieApi.auth.utils.ChangePassword;
import com.moviefix.MovieApi.dto.MailBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/forgotPassword")
public class ForgotPasswordController {

    private final UserRepository userRepository;
    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;
    private final ForgotPasswordRepository forgotPasswordRepository;

    public ForgotPasswordController(UserRepository userRepository, EmailService emailService, PasswordEncoder passwordEncoder, ForgotPasswordRepository forgotPasswordRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

    @PostMapping("/veryEmail/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new RuntimeException("Please provide the validate  email addresss")
        );
        Integer otp = otpGenerator();
        MailBody mailBody  = MailBody.builder()
                .to(email)
                .Subject("OTP for Forgot Password")
                .message("the is the OTP for the Forgot Password request " + otp)
                .build();

        ForgotPassword fp = ForgotPassword.builder()
                .otp(otp)
                .expirationDate(new Date(System.currentTimeMillis() + 300*1000))
                .user(user)
                .build();

        emailService.sendSimpleMessage(mailBody);
        forgotPasswordRepository.save(fp);

        return ResponseEntity.ok("Email sent for the verifications");

    }

    @PostMapping("/verifyOtp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable Integer otp, @PathVariable String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(
                        ()-> new RuntimeException("User is not found please enter the valid user")
                );

        ForgotPassword fp =  forgotPasswordRepository.findByOtpAndUser(otp,user).orElseThrow(
                () -> new RuntimeException("invalid otp has given")
        );

        if(fp.getExpirationDate().before(Date.from(Instant.now()))) {
            forgotPasswordRepository.deleteById(fp.getFpid());
            return new ResponseEntity<>("OTP has expired", HttpStatus.EXPECTATION_FAILED);
        }

        return   ResponseEntity.ok("OTP has Verified");

    }

    @PostMapping("/changePassword/{email}")
    public ResponseEntity<String> changePasswordHandler(@PathVariable String email, @RequestBody ChangePassword changePassword) {


        if(!Objects.equals(changePassword.password(),changePassword.repeatPassword())){
            return new ResponseEntity<>("Please enter the password again same, its a mismatch ",HttpStatus.EXPECTATION_FAILED);
        }

        String encodePassword = passwordEncoder.encode(changePassword.password());
        userRepository.updatePassword(email,encodePassword);
        return ResponseEntity.ok("Password has been changed ");
    }


    private Integer otpGenerator() {
        Random random = new Random();
        return random.nextInt(100_000,99_9999);
    }


}
