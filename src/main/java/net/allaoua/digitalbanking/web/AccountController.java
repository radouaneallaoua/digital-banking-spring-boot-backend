package net.allaoua.digitalbanking.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.allaoua.digitalbanking.dtos.currentaccount.CurrentAccountRequestDto;
import net.allaoua.digitalbanking.dtos.currentaccount.CurrentAccountResponseDto;
import net.allaoua.digitalbanking.dtos.savingaccount.SavingAccountRequestDto;
import net.allaoua.digitalbanking.dtos.savingaccount.SavingAccountResponseDto;
import net.allaoua.digitalbanking.entity.BankAccount;
import net.allaoua.digitalbanking.enums.AccountStatus;
import net.allaoua.digitalbanking.services.IAccountService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final IAccountService accountService;


    @GetMapping
    ResponseEntity<List<BankAccount>> allAccounts(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size) {
        return accountService.allAccounts(PageRequest.of(page, size));
    }

    @PutMapping("/{accountId}")
    ResponseEntity<BankAccount> updateAccountStatus(@PathVariable String accountId, @RequestParam AccountStatus status) {
        return accountService.updateAccountStatus(accountId, status);

    }

    @GetMapping("/{accountId}")
    ResponseEntity<BankAccount> getAccountById(@PathVariable String accountId) {
        return accountService.getAccountById(accountId);

    }


    @PostMapping("/save-current-account")
    ResponseEntity<CurrentAccountResponseDto> addCurrentAccount(@Valid @RequestBody CurrentAccountRequestDto account) {
        return accountService.addCurrentAccount(account);
    }

    @PostMapping("/save-saving-account")
    ResponseEntity<SavingAccountResponseDto> addSavingAccount(@Valid @RequestBody SavingAccountRequestDto account) {
        return accountService.addSavingAccount(account);
    }

    @PutMapping("/update-current-account/{accountId}")
    ResponseEntity<CurrentAccountResponseDto> updateCurrentAccount(@PathVariable String accountId, @Valid @RequestBody CurrentAccountRequestDto account) {
        return accountService.updateCurrentAccount(accountId, account);
    }

    @PutMapping("/update-saving-account/{accountId}")
    ResponseEntity<SavingAccountResponseDto> updateSavingAccount(@PathVariable String accountId, @Valid @RequestBody SavingAccountRequestDto account) {
        return accountService.updateSavingAccount(accountId, account);
    }

    @DeleteMapping("{accountId}")
    ResponseEntity<String> deleteCustomer(@PathVariable String accountId) throws AccountNotFoundException {
        return accountService.deleteAccount(accountId);
    }
}
