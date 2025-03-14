package com.cash.ledger.ledger.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.cash.ledger.ledger.entity.IdCounter;
import com.cash.ledger.ledger.entity.payment.Payment;
import com.cash.ledger.ledger.entity.userAccount.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class DynamoRepositoryImpl implements DynamoRepository {
    private final DynamoDBMapper dynamoDBMapper;
    @Autowired
    public DynamoRepositoryImpl(DynamoDBMapper dynamoDBMapper) {
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
        IdCounter counter = dynamoDBMapper.load(IdCounter.class, "userAccount");
        counter.setLastId(String.valueOf(Integer.parseInt(counter.getLastId()) + 1));


        userAccount.setId(counter.getLastId());

        dynamoDBMapper.save(counter);

        // Save user if no duplicate phoneNumber is found
        dynamoDBMapper.save(userAccount);
        return userAccount;
    }

    @Override
    public UserAccount uploadUserDetails(UserAccount userAccount) {
        IdCounter counter = dynamoDBMapper.load(IdCounter.class, "userAccount");
        counter.setLastId(String.valueOf(userAccount.getId()));


        userAccount.setId(counter.getLastId());

        dynamoDBMapper.save(counter);
        dynamoDBMapper.save(userAccount);
        return userAccount;
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
    public UserAccount getUserAccountByPhoneNumber(String phoneNumber) {
        // Define the query expression
        DynamoDBQueryExpression<UserAccount> queryExpression = new DynamoDBQueryExpression<UserAccount>()
                .withIndexName("PhoneNumberIndex")  // Use the GSI
                .withKeyConditionExpression("phoneNumber = :phoneNumber")
                .withExpressionAttributeValues(Map.of(":phoneNumber", new AttributeValue(phoneNumber)))
                .withConsistentRead(false);  // GSIs require eventual consistency

        List<UserAccount> results = dynamoDBMapper.query(UserAccount.class, queryExpression);

        return results.get(0); // Return the first matching user or null if not found
    }


    @Override
    public String deleteUserAccount(Integer userId) {
        UserAccount acc = dynamoDBMapper.load(UserAccount.class, userId);
        dynamoDBMapper.delete(acc);
        return "User account deleted";
    }

    @Override
    public Payment savePayment(Payment payment) {
        // Generate a new unique Integer ID
        IdCounter counter = dynamoDBMapper.load(IdCounter.class, "payment");
        counter.setLastId(String.valueOf(Integer.parseInt(counter.getLastId()) + 1));

        payment.setId(counter.getLastId());

        dynamoDBMapper.save(counter);
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
    public List<Payment> getUserPayments(String userId) {
        Payment paymentKey = new Payment();
        paymentKey.setUserId(String.valueOf(userId));

        DynamoDBQueryExpression<Payment> queryExpression = new DynamoDBQueryExpression<Payment>()
                .withIndexName("UserIdIndex")
                .withConsistentRead(false)
                .withHashKeyValues(paymentKey);

        return dynamoDBMapper.query(Payment.class, queryExpression);
    }


}
