package com.cash.ledger.ledger.service;

import com.cash.ledger.ledger.config.Constants;
import com.cash.ledger.ledger.entity.Payment;
import com.cash.ledger.ledger.entity.UserAccount;
import com.cash.ledger.ledger.entity.dto.payment.PaymentRequestDto;
import com.cash.ledger.ledger.entity.dto.payment.PaymentSaveRequestDto;
import com.cash.ledger.ledger.entity.dto.payment.PaymentStatusDto;
import com.cash.ledger.ledger.entity.dto.userAccount.AccountCreationRequestDto;
import com.cash.ledger.ledger.entity.dto.userAccount.UserBackupDetailsUpdateDto;
import com.cash.ledger.ledger.entity.dto.userAccount.UserProfileDetailsUpdateRequestBody;
import com.cash.ledger.ledger.repository.LedgerRepository;
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
import java.util.Objects;

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
    public UserAccount createUserAccount(AccountCreationRequestDto accountCreationRequestDto) {
        UserAccount userAccount = new UserAccount();
        userAccount.setPhoneNumber(accountCreationRequestDto.getPhoneNumber());
        userAccount.setPassword(accountCreationRequestDto.getPassword());
        userAccount.setPhoneNumber(accountCreationRequestDto.getPhoneNumber());
        userAccount.setCreatedAt(accountCreationRequestDto.getCreatedAt());
        userAccount.setRole(accountCreationRequestDto.getRole());
        userAccount.setBackupSet(false);
        userAccount.setPermanent(false);
        userAccount.setMonth(accountCreationRequestDto.getMonth());

        return saveUserAccount(userAccount);
    }

    @Override
    public UserAccount saveUserAccount(UserAccount userAccount) {
        return ledgerRepository.saveUserAccount(userAccount);
    }

    @Override
    public UserAccount updateUserDetails(UserProfileDetailsUpdateRequestBody userProfileDetailsUpdateRequestBody) {
        UserAccount userAccount = getUserAccount(userProfileDetailsUpdateRequestBody.getUserId());

        if(!Objects.equals(userProfileDetailsUpdateRequestBody.getFname(), userAccount.getFname())) {
            userAccount.setFname(userProfileDetailsUpdateRequestBody.getFname());
        }

        if(!Objects.equals(userProfileDetailsUpdateRequestBody.getLname(), userAccount.getLname())) {
            userAccount.setLname(userProfileDetailsUpdateRequestBody.getLname());
        }

        if(!Objects.equals(userProfileDetailsUpdateRequestBody.getEmail(), userAccount.getEmail())) {
            userAccount.setEmail(userProfileDetailsUpdateRequestBody.getEmail());
        }

        if(!Objects.equals(userProfileDetailsUpdateRequestBody.getPassword(), userAccount.getPassword())) {
            userAccount.setPassword(userProfileDetailsUpdateRequestBody.getPassword());
        }

        if(!Objects.equals(userProfileDetailsUpdateRequestBody.getPhoneNumber(), userAccount.getPhoneNumber())) {
            userAccount.setPhoneNumber(userProfileDetailsUpdateRequestBody.getPhoneNumber());
        }

        return updateUserAccount(userProfileDetailsUpdateRequestBody.getUserId(), userAccount);
    }

    @Override
    public UserAccount updateUserBackupDetails(UserBackupDetailsUpdateDto userBackupDetailsUpdateDto) {
        UserAccount userAccount = getUserAccount(userBackupDetailsUpdateDto.getUserId());
        userAccount.setBackupSet(true);
        userAccount.setLastBackup(userBackupDetailsUpdateDto.getLastBackup());
        userAccount.setBackupItemsSize(userBackupDetailsUpdateDto.getBackupItemsSize());
        userAccount.setTransactions(userBackupDetailsUpdateDto.getTransactions());
        userAccount.setCategories(userBackupDetailsUpdateDto.getCategories());
        userAccount.setCategoryKeywords(userBackupDetailsUpdateDto.getCategoryKeywords());
        userAccount.setCategoryMappings(userBackupDetailsUpdateDto.getCategoryMappings());
        return updateUserAccount(userBackupDetailsUpdateDto.getUserId(), userAccount);
    }

    @Override
    public UserAccount updateUserAccount(String userId, UserAccount userAccount) {
        return ledgerRepository.updateUserAccount(userId, userAccount);
    }

    @Override
    public UserAccount getUserAccount(String userId) {
        return ledgerRepository.getUserAccount(userId);
    }

    @Override
    public String deleteUserAccount(Integer userId) {
        return ledgerRepository.deleteUserAccount(userId);
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
            UserAccount userAccount = getUserAccount(paymentSaveRequestDto.getUserId());
            userAccount.setPermanent(true);
            updateUserAccount(paymentSaveRequestDto.getUserId(), userAccount);
        }

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
