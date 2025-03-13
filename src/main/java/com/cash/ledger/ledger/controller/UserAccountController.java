package com.cash.ledger.ledger.controller;

import com.cash.ledger.ledger.config.Response;
import com.cash.ledger.ledger.entity.userAccount.dto.AccountCreationRequestDto;
import com.cash.ledger.ledger.entity.userAccount.dto.UserBackupDetailsUpdateDto;
import com.cash.ledger.ledger.entity.userAccount.dto.UserProfileDetailsUpdateRequestBody;
import org.springframework.http.ResponseEntity;

public interface UserAccountController {

    ResponseEntity<Response> createUserAccount(AccountCreationRequestDto accountCreationRequestDto);
    ResponseEntity<Response> updateUserDetails(UserProfileDetailsUpdateRequestBody userProfileDetailsUpdateRequestBody);

    ResponseEntity<Response> updateUserBackupDetails(UserBackupDetailsUpdateDto userBackupDetailsUpdateDto);
    ResponseEntity<Response> getUserById(String userId);
    ResponseEntity<Response> getUserAccountByPhoneNumber(String phoneNumber);
}
