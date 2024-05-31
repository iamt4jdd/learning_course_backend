package com.example.onlinebankingapp.responses.Beneficiary;

import com.example.onlinebankingapp.entities.BeneficiaryEntity;
import com.example.onlinebankingapp.entities.CustomerEntity;
import com.example.onlinebankingapp.entities.PaymentAccountEntity;
import com.example.onlinebankingapp.entities.TransactionEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeneficiaryResponse {
    private Long id;

    private String name;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("customer_id")
    private long customerId;

    @JsonProperty("payment_account_id")
    private long paymentAccountId;

    @JsonProperty("account_number")
    private String accountNumber;

    public static BeneficiaryResponse fromBeneficiary(BeneficiaryEntity beneficiary){
        return BeneficiaryResponse.builder()
                .id(beneficiary.getId())
                .name(beneficiary.getName())
                .customerId(beneficiary.getCustomer().getId())
                .customerName(beneficiary.getCustomer().getName())
                .paymentAccountId(beneficiary.getPaymentAccount().getId())
                .accountNumber(beneficiary.getPaymentAccount().getAccountNumber())
                .build();
    }
}
