package com.Onedev.transaksiQ.controller;

import com.Onedev.transaksiQ.dto.GenericResponse;
import com.Onedev.transaksiQ.dto.transaction.*;
import com.Onedev.transaksiQ.entity.UserBalance;
import com.Onedev.transaksiQ.security.SecurityUtils;
import com.Onedev.transaksiQ.service.TransactionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/activity")
public class TransactionController {

    private final TransactionService transactionService;

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/balance")
     ResponseEntity<GenericResponse<BalanceResponse>> getBalance() {

            String userEmail = SecurityUtils.getCurrentUserEmail();
            UserBalance userBalance = transactionService.getBalance(userEmail);
            BalanceResponse balanceResponse = new BalanceResponse();
            balanceResponse.setBalance(userBalance.getBalance());

            GenericResponse<BalanceResponse> response = new GenericResponse<>(
                    0,
                    "Get Balance Berhasil", balanceResponse);
            return ResponseEntity.ok(response);

    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/top-up")
    public ResponseEntity<GenericResponse<BalanceResponse>> topUpRequest(@Valid @RequestBody TopUpRequest topUpRequest) {
        BalanceResponse balanceResponse = transactionService.topUpRequest(topUpRequest);
        GenericResponse<BalanceResponse> response = new GenericResponse<>(
                0,
                "Top-up Berhasil", balanceResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/transaction")
    public ResponseEntity<GenericResponse<TransactionResponse>> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        TransactionResponse transactionResponse = transactionService.createTransaction(transactionRequest);

        GenericResponse<TransactionResponse> response = new GenericResponse<>(0, "Transaksi Berhasil", transactionResponse);
        return ResponseEntity.ok(response);
    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/history/transaction")
    public ResponseEntity<GenericResponse<TransactionHistoryResponse>> getTransactionHistory(
            @RequestParam int offset,
            @RequestParam int limit
    ) {
        TransactionHistoryResponse historyResponse = transactionService.getTransactionHistory( offset, limit);

        GenericResponse<TransactionHistoryResponse> response = new GenericResponse<>(
                0,
                "Get History Berhasil",
                historyResponse
        );

        return ResponseEntity.ok(response);
    }

}
