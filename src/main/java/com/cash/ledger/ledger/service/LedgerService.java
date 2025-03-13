package com.cash.ledger.ledger.service;

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

public interface LedgerService {
    Map<String, Object> makePayment(PaymentRequestDto paymentRequestDto) throws Exception;
    Map<String, Object> lipaStatus(PaymentStatusDto paymentStatusDto) throws Exception;
    UserAccount createUserAccount(AccountCreationRequestDto accountCreationRequestDto);
    UserAccount saveUserAccount(UserAccount userAccount);

    UserAccount updateUserDetails(UserProfileDetailsUpdateRequestBody userProfileDetailsUpdateRequestBody);
    UserAccount updateUserBackupDetails(UserBackupDetailsUpdateDto userBackupDetailsUpdateDto);
    UserAccount updateUserAccount(String userId, UserAccount userAccount);
    UserAccount getUserAccount(String userId);
    String deleteUserAccount(Integer userId);
    Payment savePayment(PaymentSaveRequestDto paymentSaveRequestDto);
    Payment updatePayment(Integer paymentId, Payment payment);
    String deletePayment(Integer paymentId);
    Payment getPaymentById(Integer paymentId);
    List<Payment> getUserPayments(String userId);
}
