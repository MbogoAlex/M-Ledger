package com.cash.ledger.ledger.service;

import com.cash.ledger.ledger.entity.Payment;
import com.cash.ledger.ledger.entity.UserAccount;
import com.cash.ledger.ledger.entity.dto.PaymentRequestDto;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface LedgerService {
    Map<String, Object> makePayment(PaymentRequestDto paymentRequestDto) throws Exception;
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
