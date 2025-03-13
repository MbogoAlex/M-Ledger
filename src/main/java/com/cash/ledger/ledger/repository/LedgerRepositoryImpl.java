package com.cash.ledger.ledger.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.cash.ledger.ledger.entity.Payment;
import com.cash.ledger.ledger.entity.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LedgerRepositoryImpl implements LedgerRepository{
    private final DynamoDBMapper dynamoDBMapper;
    @Autowired
    public LedgerRepositoryImpl(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public UserAccount saveUserAccount(UserAccount userAccount) {
        dynamoDBMapper.save(userAccount);
        return userAccount;
    }

    @Override
    public UserAccount updateUserAccount(Integer userId, UserAccount userAccount) {
        dynamoDBMapper.save(
                userAccount,
                new DynamoDBSaveExpression()
                        .withExpectedEntry("id", new ExpectedAttributeValue(
                                new AttributeValue().withN(String.valueOf(userId))
                        ))
        );

        return userAccount;
    }

    @Override
    public UserAccount getUserAccount(Integer userId) {
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
