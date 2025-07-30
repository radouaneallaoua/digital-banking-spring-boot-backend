package net.allaoua.digitalbanking.services;

import net.allaoua.digitalbanking.dtos.account_operation.AccountOperationRequestDto;
import net.allaoua.digitalbanking.dtos.account_operation.AccountOperationResponseDto;
import net.allaoua.digitalbanking.dtos.account_operation.TransferOperationRequestDto;
import net.allaoua.digitalbanking.exceptions.BankAccountNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOperationService {
    ResponseEntity<AccountOperationResponseDto> saveOperation(AccountOperationRequestDto operationRequestDto) throws BankAccountNotFoundException;

    ResponseEntity<List<AccountOperationResponseDto>> allOperationsByAccount(String accountId, Pageable pageable) throws BankAccountNotFoundException;

    ResponseEntity<List<AccountOperationResponseDto>> transfer(TransferOperationRequestDto transferOperationRequestDto);

}
