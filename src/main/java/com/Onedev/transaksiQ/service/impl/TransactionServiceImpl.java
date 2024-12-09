package com.Onedev.transaksiQ.service.impl;


import com.Onedev.transaksiQ.dto.transaction.*;
import com.Onedev.transaksiQ.entity.Transaction;
import com.Onedev.transaksiQ.entity.User;
import com.Onedev.transaksiQ.entity.UserBalance;
import com.Onedev.transaksiQ.exception.TransactionApiException;
import com.Onedev.transaksiQ.repository.TransactionRepository;
import com.Onedev.transaksiQ.repository.UserBalanceRepository;
import com.Onedev.transaksiQ.repository.UserRepository;
import com.Onedev.transaksiQ.security.SecurityUtils;
import com.Onedev.transaksiQ.service.ServiceDataService;
import com.Onedev.transaksiQ.service.TransactionService;
import com.Onedev.transaksiQ.util.InvoiceGenerator;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final UserBalanceRepository userBalanceRepository;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;
    private final ServiceDataService serviceDataService;

    @Override
    public void insertBalance(Long id, Integer balance) {;
        User user =  userRepository.findById(id).orElseThrow();

        UserBalance userBalance = new UserBalance();
        userBalance.setUser(user);
        userBalance.setBalance(balance);
        userBalanceRepository.save(userBalance);
    }


    @Override
    public UserBalance getBalance(String email) {
        UserBalance userBalance = userBalanceRepository.findByUserEmail(email)
                .orElseThrow();
        return userBalance;
    }

    @Override
    public BalanceResponse topUpRequest(TopUpRequest topUpRequest) {
        String email = SecurityUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        UserBalance userBalance = userBalanceRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("User balance not found for email: " + email));
        userBalance.setBalance(userBalance.getBalance() + topUpRequest.getTopUpAmount());

        userBalanceRepository.save(userBalance);
        return mapToDto(userBalance);
    }

    @Override
    public void saveBalance(UserBalance userBalance) {
        userBalanceRepository.save(userBalance);
    }

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public TransactionResponse createTransaction(TransactionRequest transactionRequest) {
        com.Onedev.transaksiQ.entity.Service service = serviceDataService.getServiceByCode(transactionRequest.getServiceCode());
        String email = SecurityUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));



        UserBalance userBalance = userBalanceRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("User balance not found for email: " + email));

        if (userBalance.getBalance() < service.getServiceTariff()) {
            throw new TransactionApiException(HttpStatus.BAD_REQUEST, "maaf saldo tidak cukup", 403);
        }

        userBalance.setBalance(userBalance.getBalance() - service.getServiceTariff());
        userBalanceRepository.save(userBalance);

        Transaction transaction = new Transaction();
        transaction.setInvoiceNumber(InvoiceGenerator.generateInvoiceNumber());
        transaction.setServiceCode(service.getServiceCode());
        transaction.setServiceName(service.getServiceName());
        transaction.setUser(user);
        transaction.setTransactionType("PAYMENT");
        transaction.setTotalAmount(service.getServiceTariff());
        transaction.setCreatedOn(LocalDateTime.now());

        transactionRepository.save(transaction);

        return mapToDtoTransection(transaction);
    }

    @Override
    public TransactionHistoryResponse getTransactionHistory(int offset, int limit) {

        String email = SecurityUtils.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).orElseThrow();
        Page<Transaction> transactionPage = transactionRepository.findByUserId(
                user.getId(),
                PageRequest.of(offset, limit)
        );

        List<TransactionRecord> records = transactionPage.getContent().stream()
                .map(transaction -> new TransactionRecord(
                        transaction.getInvoiceNumber(),
                        transaction.getTransactionType(),
                        transaction.getServiceName(),
                        transaction.getTransactionType(),
                        transaction.getTotalAmount(),
                        transaction.getCreatedOn()
                ))
                .toList();
        return new TransactionHistoryResponse(offset, limit, records);
    }


    private BalanceResponse mapToDto(UserBalance userBalance){
        BalanceResponse balanceResponse = modelMapper.map(userBalance, BalanceResponse.class);
        balanceResponse.setEmail(userBalance.getUser().getEmail());
        return balanceResponse;
    }

    private TransactionResponse mapToDtoTransection(Transaction transaction){
        return modelMapper.map(transaction, TransactionResponse.class);
    }


}

