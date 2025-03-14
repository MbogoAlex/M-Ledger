package com.cash.ledger.ledger.service.userAccount;

import com.cash.ledger.ledger.entity.userAccount.UserAccount;
import com.cash.ledger.ledger.entity.userAccount.dto.AccountCreationRequestDto;
import com.cash.ledger.ledger.entity.userAccount.dto.UserBackupDetailsUpdateDto;
import com.cash.ledger.ledger.entity.userAccount.dto.UserProfileDetailsUpdateRequestBody;

public interface UserAccountService {

    UserAccount createUserAccount(AccountCreationRequestDto accountCreationRequestDto);
    UserAccount saveUserAccount(UserAccount userAccount);

    Object uploadUserDetails();

    UserAccount updateUserDetails(UserProfileDetailsUpdateRequestBody userProfileDetailsUpdateRequestBody);
    UserAccount updateUserBackupDetails(UserBackupDetailsUpdateDto userBackupDetailsUpdateDto);
    UserAccount updateUserAccount(String userId, UserAccount userAccount);
    UserAccount getUserAccount(String userId);
    UserAccount getUserAccountByPhoneNumber(String phoneNumber);
    String deleteUserAccount(Integer userId);
}
