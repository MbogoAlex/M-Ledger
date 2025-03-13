package com.cash.ledger.ledger.repository;

import com.cash.ledger.ledger.entity.*;

import java.util.List;

public interface LedgerRepository {
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
