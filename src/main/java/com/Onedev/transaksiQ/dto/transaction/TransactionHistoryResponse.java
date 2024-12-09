package com.Onedev.transaksiQ.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TransactionHistoryResponse {
    private int offset;
    private int limit;
    private List<TransactionRecord> records;

}
