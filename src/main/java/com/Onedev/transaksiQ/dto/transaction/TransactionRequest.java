package com.Onedev.transaksiQ.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionRequest {
    @JsonProperty("service_code")
    private String serviceCode;
}
