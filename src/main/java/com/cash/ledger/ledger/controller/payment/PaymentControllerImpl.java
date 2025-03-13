package com.cash.ledger.ledger.controller.payment;

import com.cash.ledger.ledger.config.BuildResponse;
import com.cash.ledger.ledger.config.Response;
import com.cash.ledger.ledger.entity.payment.dto.PaymentRequestDto;
import com.cash.ledger.ledger.entity.payment.dto.PaymentSaveRequestDto;
import com.cash.ledger.ledger.entity.payment.dto.PaymentStatusDto;
import com.cash.ledger.ledger.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class PaymentControllerImpl implements PaymentController{
    private final BuildResponse buildResponse;
    private final PaymentService paymentService;
    @Autowired
    public PaymentControllerImpl(
            PaymentService paymentService,
            BuildResponse buildResponse
    ) {
        this.paymentService = paymentService;
        this.buildResponse = buildResponse;
    }

    @PostMapping("lipa")
    @Override
    public ResponseEntity<Response> makePayment(@RequestBody PaymentRequestDto paymentRequestDto) throws Exception {
        return buildResponse.createResponse("payment", paymentService.makePayment(paymentRequestDto), "Payment request successful", HttpStatus.OK);
    }
    @PostMapping("lipa-status")
    @Override
    public ResponseEntity<Response> checkPaymentStatus(@RequestBody PaymentStatusDto paymentStatusDto) throws Exception {
        return buildResponse.createResponse("payment", paymentService.lipaStatus(paymentStatusDto), "Payment status checked", HttpStatus.OK);
    }
    @PostMapping("lipa-save")
    @Override
    public ResponseEntity<Response> savePayment(@RequestBody PaymentSaveRequestDto paymentSaveRequestDto) {
        return buildResponse.createResponse("payment", paymentService.savePayment(paymentSaveRequestDto), "Payment saved", HttpStatus.CREATED);
    }

    @GetMapping("payments/{userId}")
    @Override
    public ResponseEntity<Response> getUserPayments(@PathVariable("userId") String userId) {
        return buildResponse.createResponse("user", paymentService.getUserPayments(userId), "User payment fetched", HttpStatus.OK);
    }
}
