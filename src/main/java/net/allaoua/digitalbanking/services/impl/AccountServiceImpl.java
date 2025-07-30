package net.allaoua.digitalbanking.services.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.allaoua.digitalbanking.dtos.currentaccount.CurrentAccountRequestDto;
import net.allaoua.digitalbanking.dtos.currentaccount.CurrentAccountResponseDto;
import net.allaoua.digitalbanking.dtos.savingaccount.SavingAccountRequestDto;
import net.allaoua.digitalbanking.dtos.savingaccount.SavingAccountResponseDto;
import net.allaoua.digitalbanking.entity.BankAccount;
import net.allaoua.digitalbanking.entity.CurrentAccount;
import net.allaoua.digitalbanking.entity.Customer;
import net.allaoua.digitalbanking.entity.SavingAccount;
import net.allaoua.digitalbanking.enums.AccountStatus;
import net.allaoua.digitalbanking.exceptions.BankAccountNotFoundException;
import net.allaoua.digitalbanking.exceptions.CustomerNotFoundException;
import net.allaoua.digitalbanking.repository.AccountRepository;
import net.allaoua.digitalbanking.repository.CustomerRepository;
import net.allaoua.digitalbanking.services.IAccountService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountServiceImpl implements IAccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;


    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<List<BankAccount>> allAccounts(Pageable pageable) {
        log.info("Request all bank accounts");
        List<BankAccount> bankAccounts = accountRepository.findAll(pageable).getContent();
        return new ResponseEntity<>(bankAccounts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankAccount> updateAccountStatus(String accountId,AccountStatus status) {
        BankAccount bankAccount = accountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
        bankAccount.setStatus(status);
        return new ResponseEntity<>(bankAccount, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BankAccount> getAccountById(String accountId) {
        BankAccount bankAccount = accountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
        return new ResponseEntity<>(bankAccount, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CurrentAccountResponseDto> addCurrentAccount(CurrentAccountRequestDto accountRequestDto) {
        Customer customer = customerRepository.findById(accountRequestDto.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setBalance(accountRequestDto.getBalance());
        currentAccount.setCurrency(accountRequestDto.getCurrency());
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setCustomer(customer);
        currentAccount.setOverDraft(accountRequestDto.getOverDraft());
        CurrentAccount savedAccount = accountRepository.save(currentAccount);
        return new ResponseEntity<>(modelMapper.map(savedAccount, CurrentAccountResponseDto.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<SavingAccountResponseDto> addSavingAccount(SavingAccountRequestDto accountRequestDto) {
        Customer customer = customerRepository.findById(accountRequestDto.getCustomerId()).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setBalance(accountRequestDto.getBalance());
        savingAccount.setCurrency(accountRequestDto.getCurrency());
        savingAccount.setStatus(AccountStatus.CREATED);
        savingAccount.setCustomer(customer);
        savingAccount.setInterestRate(accountRequestDto.getInterestRate());
        SavingAccount savedAccount = accountRepository.save(savingAccount);
        return new ResponseEntity<>(modelMapper.map(savedAccount, SavingAccountResponseDto.class), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<String> deleteAccount(String accountId) throws AccountNotFoundException {
        accountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return new ResponseEntity<>("Account deleted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CurrentAccountResponseDto> updateCurrentAccount(String accountId, CurrentAccountRequestDto accountRequestDto) throws BankAccountNotFoundException {
        BankAccount bankAccount = accountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
        CurrentAccount currentAccount = (CurrentAccount) bankAccount;
        currentAccount.setId(accountId);
        CurrentAccount savedAccount = accountRepository.save(currentAccount);
        return new ResponseEntity<>(modelMapper.map(savedAccount, CurrentAccountResponseDto.class), HttpStatus.OK);

    }

    @Override
    public ResponseEntity<SavingAccountResponseDto> updateSavingAccount(String accountId, SavingAccountRequestDto accountRequestDto) throws BankAccountNotFoundException {
        BankAccount bankAccount = accountRepository.findById(accountId).orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
        SavingAccount savingAccount = (SavingAccount) bankAccount;
        savingAccount.setId(accountId);
        SavingAccount savedAccount = accountRepository.save(savingAccount);
        return new ResponseEntity<>(modelMapper.map(savedAccount, SavingAccountResponseDto.class), HttpStatus.OK);
    }


}
