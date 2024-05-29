package com.example.onlinebankingapp.controllers;

import com.example.onlinebankingapp.dtos.OTPRequest;
import com.example.onlinebankingapp.dtos.OTPVerificationRequest;
import com.example.onlinebankingapp.dtos.TransactionDTO;
import com.example.onlinebankingapp.responses.ResponseObject;
import com.example.onlinebankingapp.services.EmailServices.EmailService;
import com.example.onlinebankingapp.services.EmailServices.OTPService;
import com.example.onlinebankingapp.services.Transaction.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final OTPService otpService;
    private final EmailService emailService;

    @PostMapping("/sendOtp")
    public ResponseEntity<?> sendOtp(@RequestBody OTPRequest request) {
        try {
            String otp = otpService.generateOtp(request.getReceiverEmail());
            emailService.sendOtpEmail(request.getReceiverEmail(), otp);
            return ResponseEntity.ok(
                    ResponseObject.builder()
                            .message("OTP sent to email!")
                            .status(HttpStatus.OK)
                            .build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verifyOtpAndMakeTransaction")
    public ResponseEntity<?> verifyOtpAndMakeTransaction(@RequestBody OTPVerificationRequest request) {
        try {
            boolean isValid = otpService.verifyOtp(request.getReceiverEmail(), request.getOtp());
            if (isValid) {
                transactionService.makeTransaction(request.getTransactionDTO());
                return ResponseEntity.ok(
                        ResponseObject.builder()
                                .message("Transaction Completed!")
                                .status(HttpStatus.OK)
                                .build());
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
