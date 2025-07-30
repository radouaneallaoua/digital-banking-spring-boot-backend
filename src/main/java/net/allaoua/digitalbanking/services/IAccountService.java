package net.allaoua.digitalbanking.services;

import net.allaoua.digitalbanking.dtos.currentaccount.CurrentAccountRequestDto;
import net.allaoua.digitalbanking.dtos.currentaccount.CurrentAccountResponseDto;
import net.allaoua.digitalbanking.dtos.savingaccount.SavingAccountRequestDto;
import net.allaoua.digitalbanking.dtos.savingaccount.SavingAccountResponseDto;
import net.allaoua.digitalbanking.entity.BankAccount;
import net.allaoua.digitalbanking.enums.AccountStatus;
import net.allaoua.digitalbanking.exceptions.BankAccountNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface IAccountService {
    ResponseEntity<List<BankAccount>> allAccounts(Pageable pageable);
    ResponseEntity<BankAccount> updateAccountStatus(String accountId,AccountStatus status);
    ResponseEntity<BankAccount> getAccountById(String accountId);

    ResponseEntity<CurrentAccountResponseDto> addCurrentAccount(CurrentAccountRequestDto accountRequestDto);

    ResponseEntity<SavingAccountResponseDto> addSavingAccount(SavingAccountRequestDto accountRequestDto);

    ResponseEntity<String> deleteAccount(String accountId) throws AccountNotFoundException;

    ResponseEntity<CurrentAccountResponseDto> updateCurrentAccount(String accountId, CurrentAccountRequestDto accountRequestDto) throws BankAccountNotFoundException;

    ResponseEntity<SavingAccountResponseDto> updateSavingAccount(String accountId, SavingAccountRequestDto accountRequestDto) throws BankAccountNotFoundException;

}
