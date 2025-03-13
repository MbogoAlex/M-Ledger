package com.cash.ledger.ledger.entity.dto;

public class PaymentRequestDto {
    private String amount;
    private String phone_number;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
