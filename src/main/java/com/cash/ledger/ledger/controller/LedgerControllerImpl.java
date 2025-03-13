package com.cash.ledger.ledger.controller;

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
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/")
public class LedgerControllerImpl implements LedgerController{
    private final LedgerService ledgerService;
    @Autowired
    public LedgerControllerImpl(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }
    @PostMapping("lipa")
    @Override
    public Map<String, Object> makePayment(@RequestBody PaymentRequestDto paymentRequestDto) throws Exception {
        return ledgerService.makePayment(paymentRequestDto);
    }
    @PostMapping("lipa-status")
    @Override
    public Map<String, Object> checkPaymentStatus(@RequestBody PaymentStatusDto paymentStatusDto) throws Exception {
        return ledgerService.lipaStatus(paymentStatusDto);
    }
    @PostMapping("lipa-save")
    @Override
    public Payment savePayment(@RequestBody PaymentSaveRequestDto paymentSaveRequestDto) {
        return ledgerService.savePayment(paymentSaveRequestDto);
    }
    @PostMapping("user-account")
    @Override
    public UserAccount createUserAccount(@RequestBody AccountCreationRequestDto accountCreationRequestDto) {
        return ledgerService.createUserAccount(accountCreationRequestDto);
    }
    @PutMapping("user-profile-update")
    @Override
    public UserAccount updateUserDetails(@RequestBody UserProfileDetailsUpdateRequestBody userProfileDetailsUpdateRequestBody) {
        return ledgerService.updateUserDetails(userProfileDetailsUpdateRequestBody);
    }
    @PutMapping("user-backup-update")
    @Override
    public UserAccount updateUserBackupDetails(@RequestBody UserBackupDetailsUpdateDto userBackupDetailsUpdateDto) {
        return ledgerService.updateUserBackupDetails(userBackupDetailsUpdateDto);
    }
    @GetMapping("user/{id}")
    @Override
    public UserAccount getUserById(@PathVariable("id") String userId) {
        return ledgerService.getUserAccount(userId);
    }
}
