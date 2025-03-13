package com.cash.ledger.ledger.controller;

import com.cash.ledger.ledger.config.BuildResponse;
import com.cash.ledger.ledger.config.Response;
import com.cash.ledger.ledger.entity.userAccount.dto.AccountCreationRequestDto;
import com.cash.ledger.ledger.entity.userAccount.dto.UserBackupDetailsUpdateDto;
import com.cash.ledger.ledger.entity.userAccount.dto.UserProfileDetailsUpdateRequestBody;
import com.cash.ledger.ledger.service.userAccount.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
public class UserAccountControllerImpl implements UserAccountController {
    private final BuildResponse buildResponse;
    private final UserAccountService userAccountService;
    @Autowired
    public UserAccountControllerImpl(
            BuildResponse buildResponse,
            UserAccountService userAccountService
    ) {
        this.buildResponse = buildResponse;
        this.userAccountService = userAccountService;
    }

    @PostMapping("user-account")
    @Override
    public ResponseEntity<Response> createUserAccount(@RequestBody AccountCreationRequestDto accountCreationRequestDto) {
        try {
            return buildResponse.createResponse("user account", userAccountService.createUserAccount(accountCreationRequestDto), "User account created", HttpStatus.CREATED);
        } catch (Exception e) {
            return buildResponse.createResponse(null, null, e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    @PutMapping("user-profile-update")
    @Override
    public ResponseEntity<Response> updateUserDetails(@RequestBody UserProfileDetailsUpdateRequestBody userProfileDetailsUpdateRequestBody) {
        return buildResponse.createResponse("user account", userAccountService.updateUserDetails(userProfileDetailsUpdateRequestBody), "User account updated", HttpStatus.OK);
    }
    @PutMapping("user-backup-update")
    @Override
    public ResponseEntity<Response> updateUserBackupDetails(@RequestBody UserBackupDetailsUpdateDto userBackupDetailsUpdateDto) {
        return buildResponse.createResponse("user account", userAccountService.updateUserBackupDetails(userBackupDetailsUpdateDto), "User account updated", HttpStatus.OK);
    }
    @GetMapping("user/uid/{id}")
    @Override
    public ResponseEntity<Response> getUserById(@PathVariable("id") String userId) {
        return buildResponse.createResponse("user", userAccountService.getUserAccount(userId), "User fetched", HttpStatus.OK);
    }
    @GetMapping("user/phone/{phoneNumber}")
    @Override
    public ResponseEntity<Response> getUserAccountByPhoneNumber(@PathVariable("phoneNumber") String phoneNumber) {
        try {
            return buildResponse.createResponse("user", userAccountService.getUserAccountByPhoneNumber(phoneNumber), "User fetched", HttpStatus.OK);
        } catch (Exception e) {
            return buildResponse.createResponse(null, null, e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
