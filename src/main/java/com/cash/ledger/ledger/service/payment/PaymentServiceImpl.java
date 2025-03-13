package com.cash.ledger.ledger.service.payment;

import com.cash.ledger.ledger.config.Constants;
import com.cash.ledger.ledger.entity.payment.Payment;
import com.cash.ledger.ledger.entity.payment.dto.PaymentRequestDto;
import com.cash.ledger.ledger.entity.payment.dto.PaymentSaveRequestDto;
import com.cash.ledger.ledger.entity.payment.dto.PaymentStatusDto;
import com.cash.ledger.ledger.entity.userAccount.UserAccount;
import com.cash.ledger.ledger.repository.DynamoRepository;
import com.cash.ledger.ledger.service.userAccount.UserAccountService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService{
    private final DynamoRepository dynamoRepository;
    private final UserAccountService userAccountService;
    @Autowired
    public PaymentServiceImpl(
            DynamoRepository dynamoRepository,
            UserAccountService userAccountService
    ) {
        this.dynamoRepository = dynamoRepository;
        this.userAccountService = userAccountService;
    }

    @Override
    public Map<String, Object> makePayment(PaymentRequestDto paymentRequestDto) throws Exception {

        Gson gson = new Gson();

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(Constants.PAYMENT_REQUEST_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(paymentRequestDto)))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        if (postResponse.statusCode() != HttpStatus.OK.value()) {
            throw new Exception("Failed to initialize payment::: Status code " + postResponse.statusCode() +" response body:: "+ postResponse.body());
        }

        String jsonString = postResponse.body();
        Map<String, Object> responseMap = gson.fromJson(jsonString, Map.class);

        return responseMap;
    }

    @Override
    public Map<String, Object> lipaStatus(PaymentStatusDto paymentStatusDto) throws Exception {
        Gson gson = new Gson();

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(new URI(Constants.PAYMENT_STATUS_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(paymentStatusDto)))
                .build();

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());

        if (postResponse.statusCode() != HttpStatus.OK.value()) {
            throw new Exception("Failed to initialize payment::: Status code " + postResponse.statusCode() +" response body:: "+ postResponse.body());
        }

        String jsonString = postResponse.body();
        Map<String, Object> responseMap = gson.fromJson(jsonString, Map.class);

        return responseMap;
    }

    @Override
    public Payment savePayment(PaymentSaveRequestDto paymentSaveRequestDto) {

        Payment payment = new Payment();
        payment.setAmount(paymentSaveRequestDto.getAmount());
        payment.setExpiredAt(paymentSaveRequestDto.getExpiredAt());
        payment.setPaidAt(paymentSaveRequestDto.getPaidAt());
        payment.setMonth(paymentSaveRequestDto.getMonth());
        payment.setUserId(paymentSaveRequestDto.getUserId());

        if(paymentSaveRequestDto.getPermanent()) {
            UserAccount userAccount = userAccountService.getUserAccount(paymentSaveRequestDto.getUserId());
            userAccount.setPermanent(true);
            userAccountService.updateUserAccount(paymentSaveRequestDto.getUserId(), userAccount);
        }

        return dynamoRepository.savePayment(payment);
    }



    @Override
    public Payment updatePayment(Integer paymentId, Payment payment) {
        return dynamoRepository.updatePayment(paymentId, payment);
    }

    @Override
    public String deletePayment(Integer paymentId) {
        return dynamoRepository.deletePayment(paymentId);
    }

    @Override
    public Payment getPaymentById(Integer paymentId) {
        return dynamoRepository.getPaymentById(paymentId);
    }

    @Override
    public List<Payment> getUserPayments(String userId) {
        return dynamoRepository.getUserPayments(userId);
    }
}
