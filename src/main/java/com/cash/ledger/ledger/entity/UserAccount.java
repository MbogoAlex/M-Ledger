package com.cash.ledger.ledger.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.time.LocalDateTime;

@DynamoDBTable(tableName = "userAccount")
public class UserAccount {
    private Integer id;
    private LocalDateTime createdAt;
    private String email;
    private String fname;
    private String lname;
    private String phoneNumber;
    private String password;
    private Integer role;
    private String lastLogin;
    private Integer month;
    private Boolean permanent;
    private Boolean backupSet;
    private LocalDateTime lastBackup;
    private Integer backupItemsSize;
    private Integer transactions;
    private Integer categories;
    private Integer categoryKeywords;
    private Integer categoryMappings;
}
