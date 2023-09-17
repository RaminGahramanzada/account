package com.javadevcom.account.service;

import com.javadevcom.account.dto.AccountDto;
import com.javadevcom.account.dto.AccountDtoConverter;
import com.javadevcom.account.dto.CreateAccountRequest;
import com.javadevcom.account.model.Account;
import com.javadevcom.account.model.Customer;
import com.javadevcom.account.model.Transaction;
import com.javadevcom.account.repository.AccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Component
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final CustomerService customerService;
    private final TransactionService transactionService;
    private final AccountDtoConverter converter;

    public AccountService(AccountRepository accountRepository, CustomerService customerService,
                          TransactionService transactionService,
                          AccountDtoConverter converter) {
        this.accountRepository = accountRepository;
        this.customerService = customerService;
        this.transactionService = transactionService;
        this.converter = converter;
    }
    public AccountDto createAccount(CreateAccountRequest createAccountRequest){
      Customer customer =customerService.findCustomerById(createAccountRequest.getCustomerId());

        Account account = new Account( customer,
                createAccountRequest.getInitialCredit(),
                LocalDateTime.now()
               );
        if(createAccountRequest.getInitialCredit().compareTo(BigDecimal.ZERO)>0){
            Transaction transaction =transactionService.initiateMoney(account,createAccountRequest.getInitialCredit());
            account.getTransaction().add(transaction);
        }
        return converter.convert(accountRepository.save(account));
    }
}
