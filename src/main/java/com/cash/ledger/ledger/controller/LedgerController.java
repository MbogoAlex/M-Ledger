package com.cash.ledger.ledger.controller;

import com.cash.ledger.ledger.entity.Payment;
import com.cash.ledger.ledger.entity.UserAccount;
import com.cash.ledger.ledger.entity.dto.payment.PaymentRequestDto;
import com.cash.ledger.ledger.entity.dto.payment.PaymentSaveRequestDto;
import com.cash.ledger.ledger.entity.dto.payment.PaymentStatusDto;
import com.cash.ledger.ledger.entity.dto.userAccount.AccountCreationRequestDto;
import com.cash.ledger.ledger.entity.dto.userAccount.UserBackupDetailsUpdateDto;
import com.cash.ledger.ledger.entity.dto.userAccount.UserProfileDetailsUpdateRequestBody;

import java.util.List;
import java.util.Map;

public interface LedgerController {
    Map<String, Object> makePayment(PaymentRequestDto paymentRequestDto) throws Exception;
    Map<String, Object> checkPaymentStatus(PaymentStatusDto paymentStatusDto) throws Exception;

    Payment savePayment(PaymentSaveRequestDto paymentSaveRequestDto);

    UserAccount createUserAccount(AccountCreationRequestDto accountCreationRequestDto);
    UserAccount updateUserDetails(UserProfileDetailsUpdateRequestBody userProfileDetailsUpdateRequestBody);

    UserAccount updateUserBackupDetails(UserBackupDetailsUpdateDto userBackupDetailsUpdateDto);
    UserAccount getUserById(String userId);
    List<Payment> getUserPayments(String userId);
}
