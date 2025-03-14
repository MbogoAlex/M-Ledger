package com.cash.ledger.ledger.service.userAccount;

import com.cash.ledger.ledger.entity.payment.Payment;
import com.cash.ledger.ledger.entity.userAccount.UserAccount;
import com.cash.ledger.ledger.entity.userAccount.dto.AccountCreationRequestDto;
import com.cash.ledger.ledger.entity.userAccount.dto.UserBackupDetailsUpdateDto;
import com.cash.ledger.ledger.entity.userAccount.dto.UserProfileDetailsUpdateRequestBody;
import com.cash.ledger.ledger.repository.DynamoRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
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
    public Object uploadUserDetails() {
        String filePath = "/home/mbogo/Downloads/supabase-user-accounts.csv";

        int counter = 0;

        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();
            boolean isFirstRow = true;

            for (String[] row : records) {
                if (isFirstRow) {
                    isFirstRow = false;
                    continue; // Skip header row
                }

                UserAccount userAccount = new UserAccount();
                userAccount.setId(row[0]);
                userAccount.setCreatedAt(row[1]);
                userAccount.setEmail(row[2]);
                userAccount.setFname(row[3]);
                userAccount.setLname(row[4]);
                userAccount.setPhoneNumber(row[5]);
                userAccount.setPassword(row[6]);
                userAccount.setRole(row[7].isEmpty() ? null : Integer.parseInt(row[7]));
                userAccount.setLastLogin(row[8]);
                userAccount.setMonth(row[9].isEmpty() ? null : Integer.parseInt(row[9]));
                userAccount.setPermanent(Boolean.parseBoolean(row[10]));
                userAccount.setBackupSet(Boolean.parseBoolean(row[11]));
                userAccount.setLastBackup(row[12]);
                userAccount.setBackupItemsSize(row[13]);
                userAccount.setTransactions(row[14]);
                userAccount.setCategories(row[15]);
                userAccount.setCategoryKeywords(row[16]);
                userAccount.setCategoryMappings(row[17]);

                dynamoRepository.uploadUserDetails(userAccount);

                counter = counter + 1;
                System.out.println("UPLOAD COUNT: "+counter);
            }

        } catch (Exception e) {
            System.out.println("SAVING ERROR: "+e);
            e.printStackTrace();
        }
        return null;
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
