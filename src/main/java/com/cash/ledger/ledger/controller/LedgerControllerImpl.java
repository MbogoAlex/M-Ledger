package com.cash.ledger.ledger.controller;

import com.cash.ledger.ledger.config.BuildResponse;
import com.cash.ledger.ledger.config.Response;
import com.cash.ledger.ledger.entity.Payment;
import com.cash.ledger.ledger.entity.UserAccount;
import com.cash.ledger.ledger.entity.dto.payment.PaymentRequestDto;
import com.cash.ledger.ledger.entity.dto.payment.PaymentSaveRequestDto;
import com.cash.ledger.ledger.entity.dto.payment.PaymentStatusDto;
import com.cash.ledger.ledger.entity.dto.userAccount.AccountCreationRequestDto;
import com.cash.ledger.ledger.entity.dto.userAccount.UserBackupDetailsUpdateDto;
import com.cash.ledger.ledger.entity.dto.userAccount.UserProfileDetailsUpdateRequestBody;
import com.cash.ledger.ledger.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/")
public class LedgerControllerImpl implements LedgerController{
    private final BuildResponse buildResponse;
    private final LedgerService ledgerService;
    @Autowired
    public LedgerControllerImpl(
            BuildResponse buildResponse,
            LedgerService ledgerService
    ) {
        this.buildResponse = buildResponse;
        this.ledgerService = ledgerService;
    }
    @PostMapping("lipa")
    @Override
    public ResponseEntity<Response> makePayment(@RequestBody PaymentRequestDto paymentRequestDto) throws Exception {
        return buildResponse.createResponse("payment", ledgerService.makePayment(paymentRequestDto), "Payment request successful", HttpStatus.OK);
    }
    @PostMapping("lipa-status")
    @Override
    public ResponseEntity<Response> checkPaymentStatus(@RequestBody PaymentStatusDto paymentStatusDto) throws Exception {
        return buildResponse.createResponse("payment", ledgerService.lipaStatus(paymentStatusDto), "Payment status checked", HttpStatus.OK);
    }
    @PostMapping("lipa-save")
    @Override
    public ResponseEntity<Response> savePayment(@RequestBody PaymentSaveRequestDto paymentSaveRequestDto) {
        return buildResponse.createResponse("payment", ledgerService.savePayment(paymentSaveRequestDto), "Payment saved", HttpStatus.CREATED);
    }
    @PostMapping("user-account")
    @Override
    public ResponseEntity<Response> createUserAccount(@RequestBody AccountCreationRequestDto accountCreationRequestDto) {
        try {
            return buildResponse.createResponse("user account", ledgerService.createUserAccount(accountCreationRequestDto), "User account created", HttpStatus.CREATED);
        } catch (Exception e) {
            return buildResponse.createResponse(null, null, e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    @PutMapping("user-profile-update")
    @Override
    public ResponseEntity<Response> updateUserDetails(@RequestBody UserProfileDetailsUpdateRequestBody userProfileDetailsUpdateRequestBody) {
        return buildResponse.createResponse("user account", ledgerService.updateUserDetails(userProfileDetailsUpdateRequestBody), "User account updated", HttpStatus.OK);
    }
    @PutMapping("user-backup-update")
    @Override
    public ResponseEntity<Response> updateUserBackupDetails(@RequestBody UserBackupDetailsUpdateDto userBackupDetailsUpdateDto) {
        return buildResponse.createResponse("user account", ledgerService.updateUserBackupDetails(userBackupDetailsUpdateDto), "User account updated", HttpStatus.OK);
    }
    @GetMapping("user/{id}")
    @Override
    public ResponseEntity<Response> getUserById(@PathVariable("id") String userId) {
        return buildResponse.createResponse("user", ledgerService.getUserAccount(userId), "User fetched", HttpStatus.OK);
    }
    @GetMapping("payments/{userId}")
    @Override
    public ResponseEntity<Response> getUserPayments(@PathVariable("userId") String userId) {
        return buildResponse.createResponse("user", ledgerService.getUserPayments(userId), "User payment fetched", HttpStatus.OK);
    }
}
