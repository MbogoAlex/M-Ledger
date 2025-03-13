package com.cash.ledger.ledger.service.payment;

import com.cash.ledger.ledger.entity.payment.Payment;
import com.cash.ledger.ledger.entity.payment.dto.PaymentRequestDto;
import com.cash.ledger.ledger.entity.payment.dto.PaymentSaveRequestDto;
import com.cash.ledger.ledger.entity.payment.dto.PaymentStatusDto;

import java.util.List;
import java.util.Map;

public interface PaymentService {
    Map<String, Object> makePayment(PaymentRequestDto paymentRequestDto) throws Exception;
    Map<String, Object> lipaStatus(PaymentStatusDto paymentStatusDto) throws Exception;
    Payment savePayment(PaymentSaveRequestDto paymentSaveRequestDto);
    Payment updatePayment(Integer paymentId, Payment payment);
    String deletePayment(Integer paymentId);
    Payment getPaymentById(Integer paymentId);
    List<Payment> getUserPayments(String userId);
}
