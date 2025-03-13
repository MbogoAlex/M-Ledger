package com.cash.ledger.ledger.entity.dto.userAccount;

public class UserBackupDetailsUpdateDto {
    private String userId;
    private String lastBackup;
    private String backupItemsSize;
    private String transactions;
    private String categories;
    private String categoryKeywords;
    private String categoryMappings;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastBackup() {
        return lastBackup;
    }

    public void setLastBackup(String lastBackup) {
        this.lastBackup = lastBackup;
    }

    public String getBackupItemsSize() {
        return backupItemsSize;
    }

    public void setBackupItemsSize(String backupItemsSize) {
        this.backupItemsSize = backupItemsSize;
    }

    public String getTransactions() {
        return transactions;
    }

    public void setTransactions(String transactions) {
        this.transactions = transactions;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCategoryKeywords() {
        return categoryKeywords;
    }

    public void setCategoryKeywords(String categoryKeywords) {
        this.categoryKeywords = categoryKeywords;
    }

    public String getCategoryMappings() {
        return categoryMappings;
    }

    public void setCategoryMappings(String categoryMappings) {
        this.categoryMappings = categoryMappings;
    }
}
