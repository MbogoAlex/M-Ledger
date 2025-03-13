package com.cash.ledger.ledger.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.cash.ledger.ledger.entity.IdCounter;
import com.cash.ledger.ledger.entity.Payment;
import com.cash.ledger.ledger.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class LedgerRepositoryImpl implements LedgerRepository{
    private final DynamoDBMapper dynamoDBMapper;
    @Autowired
    public LedgerRepositoryImpl(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public UserAccount saveUserAccount(UserAccount userAccount) {
        // Query the GSI instead of scanning the entire table
        DynamoDBQueryExpression<UserAccount> queryExpression = new DynamoDBQueryExpression<UserAccount>()
                .withIndexName("PhoneNumberIndex")  // Use the GSI
                .withKeyConditionExpression("phoneNumber = :phoneNumber")
                .withExpressionAttributeValues(Map.of(":phoneNumber", new AttributeValue(userAccount.getPhoneNumber())))
                .withConsistentRead(false);  // GSIs require eventual consistency

        List<UserAccount> existingUsers = dynamoDBMapper.query(UserAccount.class, queryExpression);

        if (!existingUsers.isEmpty()) {
            throw new RuntimeException("User with phone number " + userAccount.getPhoneNumber() + " already exists.");
        }

        // Generate a new unique Integer ID
        String newId = getNextId();
        userAccount.setId(String.valueOf(newId));

        // Save user if no duplicate phoneNumber is found
        dynamoDBMapper.save(userAccount);
        return userAccount;
    }

    /**
     * Fetch the highest `id` in the table and return the next one.
     */
    private String getNextId() {
        IdCounter counter = dynamoDBMapper.load(IdCounter.class, "userAccount");
        if (counter == null) {
            counter = new IdCounter();
            counter.setTableName("userAccount");
            counter.setLastId("1");
        } else {
            counter.setLastId(String.valueOf(Integer.parseInt(counter.getLastId()) + 1));
        }
        dynamoDBMapper.save(counter);
        return counter.getLastId();
    }






    @Override
    public UserAccount updateUserAccount(String userId, UserAccount userAccount) {
        dynamoDBMapper.save(
                userAccount,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("id", new ExpectedAttributeValue(
                                new AttributeValue().withS(userId)
                        ))
        );

        return userAccount;
    }

    @Override
    public UserAccount getUserAccount(String userId) {
        return dynamoDBMapper.load(UserAccount.class, userId);
    }

    @Override
    public String deleteUserAccount(Integer userId) {
        UserAccount acc = dynamoDBMapper.load(UserAccount.class, userId);
        dynamoDBMapper.delete(acc);
        return "User account deleted";
    }

    @Override
    public Payment savePayment(Payment payment) {
        dynamoDBMapper.save(payment);
        return payment;
    }

    @Override
    public Payment updatePayment(Integer paymentId, Payment payment) {
        dynamoDBMapper.save(
                payment,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("id", new ExpectedAttributeValue(
                                new AttributeValue().withN(String.valueOf(paymentId))
                        ))
        );

        return payment;
    }

    @Override
    public String deletePayment(Integer paymentId) {
        Payment payment = dynamoDBMapper.load(Payment.class, paymentId);
        dynamoDBMapper.delete(payment);
        return "User payment deleted";
    }

    @Override
    public Payment getPaymentById(Integer paymentId) {
        return dynamoDBMapper.load(Payment.class, paymentId);
    }

    @Override
    public List<Payment> getUserPayments(Integer userId) {
        Payment paymentKey = new Payment();
        paymentKey.setUserId(userId);

        DynamoDBQueryExpression<Payment> queryExpression = new DynamoDBQueryExpression<Payment>()
                .withIndexName("UserIdIndex")
                .withConsistentRead(false)
                .withHashKeyValues(paymentKey);

        return dynamoDBMapper.query(Payment.class, queryExpression);
    }


}
