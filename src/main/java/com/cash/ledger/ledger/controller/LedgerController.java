package com.cash.ledger.ledger.controller;

import com.cash.ledger.ledger.config.Response;
import com.cash.ledger.ledger.entity.Payment;
import com.cash.ledger.ledger.entity.UserAccount;
import com.cash.ledger.ledger.entity.dto.payment.PaymentRequestDto;
import com.cash.ledger.ledger.entity.dto.payment.PaymentSaveRequestDto;
import com.cash.ledger.ledger.entity.dto.payment.PaymentStatusDto;
import com.cash.ledger.ledger.entity.dto.userAccount.AccountCreationRequestDto;
import com.cash.ledger.ledger.entity.dto.userAccount.UserBackupDetailsUpdateDto;
import com.cash.ledger.ledger.entity.dto.userAccount.UserProfileDetailsUpdateRequestBody;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface LedgerController {
    ResponseEntity<Response> makePayment(PaymentRequestDto paymentRequestDto) throws Exception;
    ResponseEntity<Response> checkPaymentStatus(PaymentStatusDto paymentStatusDto) throws Exception;

    ResponseEntity<Response> savePayment(PaymentSaveRequestDto paymentSaveRequestDto);

    ResponseEntity<Response> createUserAccount(AccountCreationRequestDto accountCreationRequestDto);
    ResponseEntity<Response> updateUserDetails(UserProfileDetailsUpdateRequestBody userProfileDetailsUpdateRequestBody);

    ResponseEntity<Response> updateUserBackupDetails(UserBackupDetailsUpdateDto userBackupDetailsUpdateDto);
    ResponseEntity<Response> getUserById(String userId);
    ResponseEntity<Response> getUserPayments(String userId);
}
