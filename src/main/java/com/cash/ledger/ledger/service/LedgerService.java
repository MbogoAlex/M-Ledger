package com.cash.ledger.ledger.service;

import com.cash.ledger.ledger.entity.Payment;
import com.cash.ledger.ledger.entity.UserAccount;
import com.cash.ledger.ledger.entity.dto.payment.PaymentRequestDto;
import com.cash.ledger.ledger.entity.dto.payment.PaymentStatusDto;
import com.cash.ledger.ledger.entity.dto.userAccount.AccountCreationRequestDto;

import java.util.List;
import java.util.Map;

public interface LedgerService {
    Map<String, Object> makePayment(PaymentRequestDto paymentRequestDto) throws Exception;
    Map<String, Object> lipaStatus(PaymentStatusDto paymentStatusDto) throws Exception;
    UserAccount createUserAccount(AccountCreationRequestDto accountCreationRequestDto);
    UserAccount saveUserAccount(UserAccount userAccount);
    UserAccount updateUserAccount(Integer userId, UserAccount userAccount);
    UserAccount getUserAccount(Integer userId);
    String deleteUserAccount(Integer userId);
    Payment savePayment(Payment payment);
    Payment updatePayment(Integer paymentId, Payment payment);
    String deletePayment(Integer paymentId);
    Payment getPaymentById(Integer paymentId);
    List<Payment> getUserPayments(Integer userId);
}
