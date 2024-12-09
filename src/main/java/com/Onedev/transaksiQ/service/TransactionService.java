package com.Onedev.transaksiQ.service;

import com.Onedev.transaksiQ.dto.transaction.*;
import com.Onedev.transaksiQ.entity.Transaction;
import com.Onedev.transaksiQ.entity.UserBalance;

public interface TransactionService {
    void insertBalance(Long id, Integer balance);
    UserBalance getBalance(String email);
    BalanceResponse topUpRequest(TopUpRequest topUpRequest);
    void saveBalance(UserBalance userBalance);
    void saveTransaction(Transaction transaction);
    TransactionResponse createTransaction(TransactionRequest transactionRequest);
    TransactionHistoryResponse getTransactionHistory(int offset, int limit);
}
