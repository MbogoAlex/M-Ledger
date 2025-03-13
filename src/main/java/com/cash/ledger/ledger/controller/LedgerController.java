package com.cash.ledger.ledger.controller;

import com.cash.ledger.ledger.entity.Payment;
import com.cash.ledger.ledger.entity.UserAccount;
import com.cash.ledger.ledger.entity.dto.payment.PaymentRequestDto;
import com.cash.ledger.ledger.entity.dto.payment.PaymentStatusDto;
import com.cash.ledger.ledger.entity.dto.userAccount.AccountCreationRequestDto;

import java.util.Map;

public interface LedgerController {
    Map<String, Object> makePayment(PaymentRequestDto paymentRequestDto) throws Exception;
    Map<String, Object> checkPaymentStatus(PaymentStatusDto paymentStatusDto) throws Exception;

    Payment savePayment(Payment payment);

    UserAccount createUserAccount(AccountCreationRequestDto accountCreationRequestDto);
}
