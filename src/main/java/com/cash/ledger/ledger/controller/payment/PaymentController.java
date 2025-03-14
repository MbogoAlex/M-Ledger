package com.cash.ledger.ledger.controller.payment;

import com.cash.ledger.ledger.config.Response;
import com.cash.ledger.ledger.entity.payment.dto.PaymentRequestDto;
import com.cash.ledger.ledger.entity.payment.dto.PaymentSaveRequestDto;
import com.cash.ledger.ledger.entity.payment.dto.PaymentStatusDto;
import org.springframework.http.ResponseEntity;

public interface PaymentController {
    ResponseEntity<Response> makePayment(PaymentRequestDto paymentRequestDto) throws Exception;
    ResponseEntity<Response> checkPaymentStatus(PaymentStatusDto paymentStatusDto) throws Exception;

    ResponseEntity<Response> savePayment(PaymentSaveRequestDto paymentSaveRequestDto);
    ResponseEntity<Response> uploadPayments();
    ResponseEntity<Response> getUserPayments(String userId);
}
