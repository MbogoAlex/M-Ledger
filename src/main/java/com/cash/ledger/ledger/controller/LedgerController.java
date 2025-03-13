package com.cash.ledger.ledger.controller;

import com.cash.ledger.ledger.entity.dto.PaymentRequestDto;

import java.util.Map;

public interface LedgerController {
    Map<String, Object> makePayment(PaymentRequestDto paymentRequestDto) throws Exception;
}
