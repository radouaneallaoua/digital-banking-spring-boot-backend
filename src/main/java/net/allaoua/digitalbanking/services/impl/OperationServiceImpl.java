package net.allaoua.digitalbanking.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.allaoua.digitalbanking.dtos.account_operation.AccountOperationRequestDto;
import net.allaoua.digitalbanking.dtos.account_operation.AccountOperationResponseDto;
import net.allaoua.digitalbanking.dtos.account_operation.TransferOperationRequestDto;
import net.allaoua.digitalbanking.entity.BankAccount;
import net.allaoua.digitalbanking.entity.Operation;
import net.allaoua.digitalbanking.enums.OperationType;
import net.allaoua.digitalbanking.exceptions.BalanceInsufficientException;
import net.allaoua.digitalbanking.exceptions.BankAccountNotFoundException;
import net.allaoua.digitalbanking.repository.AccountRepository;
import net.allaoua.digitalbanking.repository.OperationRepository;
import net.allaoua.digitalbanking.services.IOperationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OperationServiceImpl implements IOperationService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final OperationRepository operationRepository;


    @Override
    public ResponseEntity<AccountOperationResponseDto> saveOperation(AccountOperationRequestDto operationRequestDto) throws BankAccountNotFoundException {
        BankAccount bankAccount = accountRepository.findById(operationRequestDto.getAccount()).orElseThrow(() -> new BankAccountNotFoundException("Account not found"));
        OperationType type = operationRequestDto.getType();
        if (type == OperationType.DEBIT && bankAccount.getBalance() < operationRequestDto.getAmount()) {
            throw new BalanceInsufficientException("Balance insufficient");
        }
        Operation operation = modelMapper.map(operationRequestDto, Operation.class);
        operation.setAccount(bankAccount);
        Operation saved = operationRepository.save(operation);
        if(type == OperationType.CREDIT) {
            bankAccount.setBalance(bankAccount.getBalance() + operationRequestDto.getAmount());
        }else{
            bankAccount.setBalance(bankAccount.getBalance() - operationRequestDto.getAmount());
        }
        return new ResponseEntity<>(modelMapper.map(saved, AccountOperationResponseDto.class), HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @Override
    public ResponseEntity<List<AccountOperationResponseDto>> allOperationsByAccount(String accountId, Pageable pageable) {
        Optional<BankAccount> bankAccount = accountRepository.findById(accountId);
        if (bankAccount.isEmpty()) {
            throw new BankAccountNotFoundException("Account not found");
        }
        List<Operation> operationList = operationRepository.findByAccountId(accountId, pageable).getContent();
        return new ResponseEntity<>(operationList.stream().map(op -> modelMapper.map(op, AccountOperationResponseDto.class)).toList(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AccountOperationResponseDto>> transfer(TransferOperationRequestDto transferOperationRequestDto) {
        ResponseEntity<AccountOperationResponseDto> debitOperation = saveOperation(AccountOperationRequestDto.builder()
                .description(transferOperationRequestDto.getDescription())
                .amount(transferOperationRequestDto.getAmount())
                .account(transferOperationRequestDto.getAccountSource())
                .type(OperationType.DEBIT)
                .build());
        ResponseEntity<AccountOperationResponseDto> creditOperation = saveOperation(AccountOperationRequestDto.builder()
                .description(transferOperationRequestDto.getDescription())
                .amount(transferOperationRequestDto.getAmount())
                .account(transferOperationRequestDto.getAccountDestination())
                .type(OperationType.CREDIT)
                .build());
        return new ResponseEntity<>(List.of(Objects.requireNonNull(debitOperation.getBody()), Objects.requireNonNull(creditOperation.getBody())), HttpStatus.OK);

    }
}
