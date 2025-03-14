package com.cash.ledger.ledger.repository;

import com.cash.ledger.ledger.entity.payment.Payment;
import com.cash.ledger.ledger.entity.userAccount.UserAccount;

import java.util.List;

public interface DynamoRepository {
    UserAccount saveUserAccount(UserAccount userAccount);

    UserAccount uploadUserDetails(UserAccount userAccount);
    UserAccount updateUserAccount(String userId, UserAccount userAccount);
    UserAccount getUserAccount(String userId);
    UserAccount getUserAccountByPhoneNumber(String phoneNumber);
    String deleteUserAccount(Integer userId);
    Payment savePayment(Payment payment);

    Payment uploadPayment(Payment payment);
    Payment updatePayment(Integer paymentId, Payment payment);
    String deletePayment(Integer paymentId);
    Payment getPaymentById(Integer paymentId);
    List<Payment> getUserPayments(String userId);
}
