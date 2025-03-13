package com.cash.ledger.ledger.service.userAccount;

import com.cash.ledger.ledger.entity.userAccount.UserAccount;
import com.cash.ledger.ledger.entity.userAccount.dto.AccountCreationRequestDto;
import com.cash.ledger.ledger.entity.userAccount.dto.UserBackupDetailsUpdateDto;
import com.cash.ledger.ledger.entity.userAccount.dto.UserProfileDetailsUpdateRequestBody;
import com.cash.ledger.ledger.repository.DynamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserAccountServiceImpl implements UserAccountService {
    private final DynamoRepository dynamoRepository;
    @Autowired
    public UserAccountServiceImpl(DynamoRepository dynamoRepository) {
        this.dynamoRepository = dynamoRepository;
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
        return dynamoRepository.saveUserAccount(userAccount);
    }

    @Override
    public UserAccount updateUserDetails(UserProfileDetailsUpdateRequestBody userProfileDetailsUpdateRequestBody) {
        UserAccount userAccount = getUserAccount(userProfileDetailsUpdateRequestBody.getUserId());

        if(userProfileDetailsUpdateRequestBody.getFname() != null &&
                !Objects.equals(userProfileDetailsUpdateRequestBody.getFname(), userAccount.getFname())) {
            userAccount.setFname(userProfileDetailsUpdateRequestBody.getFname());
        }

        if(userProfileDetailsUpdateRequestBody.getLname() != null &&
                !Objects.equals(userProfileDetailsUpdateRequestBody.getLname(), userAccount.getLname())) {
            userAccount.setLname(userProfileDetailsUpdateRequestBody.getLname());
        }

        if(userProfileDetailsUpdateRequestBody.getEmail() != null &&
                !Objects.equals(userProfileDetailsUpdateRequestBody.getEmail(), userAccount.getEmail())) {
            userAccount.setEmail(userProfileDetailsUpdateRequestBody.getEmail());
        }

        if(userProfileDetailsUpdateRequestBody.getPassword() != null &&
                !Objects.equals(userProfileDetailsUpdateRequestBody.getPassword(), userAccount.getPassword())) {
            userAccount.setPassword(userProfileDetailsUpdateRequestBody.getPassword());
        }

        if(userProfileDetailsUpdateRequestBody.getPhoneNumber() != null &&
                !Objects.equals(userProfileDetailsUpdateRequestBody.getPhoneNumber(), userAccount.getPhoneNumber())) {
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
        return dynamoRepository.updateUserAccount(userId, userAccount);
    }

    @Override
    public UserAccount getUserAccount(String userId) {
        return dynamoRepository.getUserAccount(userId);
    }

    @Override
    public UserAccount getUserAccountByPhoneNumber(String phoneNumber) {
        return dynamoRepository.getUserAccountByPhoneNumber(phoneNumber);
    }

    @Override
    public String deleteUserAccount(Integer userId) {
        return dynamoRepository.deleteUserAccount(userId);
    }


}
