package com.cash.ledger.ledger.service;

import com.cash.ledger.ledger.entity.Payment;
import com.cash.ledger.ledger.entity.UserAccount;
import com.cash.ledger.ledger.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LedgerServiceImpl implements LedgerService{
    private final LedgerRepository ledgerRepository;
    @Autowired
    public LedgerServiceImpl(LedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    @Override
    public UserAccount saveUserAccount(UserAccount userAccount) {
        return ledgerRepository.saveUserAccount(userAccount);
    }

    @Override
    public UserAccount updateUserAccount(Integer userId, UserAccount userAccount) {
        return ledgerRepository.updateUserAccount(userId, userAccount);
    }

    @Override
    public UserAccount getUserAccount(Integer userId) {
        return ledgerRepository.getUserAccount(userId);
    }

    @Override
    public String deleteUserAccount(Integer userId) {
        return ledgerRepository.deleteUserAccount(userId);
    }

    @Override
    public Payment savePayment(Payment payment) {
        return ledgerRepository.savePayment(payment);
    }

    @Override
    public Payment updatePayment(Integer paymentId, Payment payment) {
        return ledgerRepository.updatePayment(paymentId, payment);
    }

    @Override
    public String deletePayment(Integer paymentId) {
        return ledgerRepository.deletePayment(paymentId);
    }

    @Override
    public Payment getPaymentById(Integer paymentId) {
        return ledgerRepository.getPaymentById(paymentId);
    }

    @Override
    public List<Payment> getUserPayments(Integer userId) {
        return ledgerRepository.getUserPayments(userId);
    }
}
