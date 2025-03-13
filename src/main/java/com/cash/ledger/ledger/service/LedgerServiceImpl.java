package com.cash.ledger.ledger.service;

import com.cash.ledger.ledger.config.Constants;
import com.cash.ledger.ledger.entity.Payment;
import com.cash.ledger.ledger.entity.UserAccount;
import com.cash.ledger.ledger.entity.dto.PaymentRequestDto;
import com.cash.ledger.ledger.repository.LedgerRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LedgerServiceImpl implements LedgerService{
    private final LedgerRepository ledgerRepository;
    @Autowired
    public LedgerServiceImpl(LedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
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
