package com.cash.ledger.ledger.controller;

import com.cash.ledger.ledger.entity.dto.PaymentRequestDto;
import com.cash.ledger.ledger.repository.LedgerRepositoryImpl;
import com.cash.ledger.ledger.service.LedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/")
public class LedgerControllerImpl implements LedgerController{
    private final LedgerService ledgerService;
    @Autowired
    public LedgerControllerImpl(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }
    @PostMapping("lipa")
    @Override
    public Map<String, Object> makePayment(@RequestBody PaymentRequestDto paymentRequestDto) throws Exception {
        return ledgerService.makePayment(paymentRequestDto);
    }
}
