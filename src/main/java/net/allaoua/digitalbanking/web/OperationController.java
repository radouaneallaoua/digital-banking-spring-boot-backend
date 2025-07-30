package net.allaoua.digitalbanking.web;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.allaoua.digitalbanking.dtos.account_operation.AccountOperationRequestDto;
import net.allaoua.digitalbanking.dtos.account_operation.AccountOperationResponseDto;
import net.allaoua.digitalbanking.dtos.account_operation.TransferOperationRequestDto;
import net.allaoua.digitalbanking.services.IOperationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/operations")
public class OperationController {
    private final IOperationService operationService;

    @PostMapping
    ResponseEntity<AccountOperationResponseDto> saveOperation(@Valid @RequestBody AccountOperationRequestDto accountOperationRequestDto) {
        return operationService.saveOperation(accountOperationRequestDto);
    }

    @GetMapping("/by-account/{accountId}")
    @Operation(description = "get all operations by account id", summary = "get all operations by account id")
    ResponseEntity<List<AccountOperationResponseDto>> allOperationsByAccount(@RequestParam(defaultValue = "0", required = false) int page, @RequestParam(defaultValue = "10", required = false) int size, @PathVariable String accountId) {
        return operationService.allOperationsByAccount(accountId, PageRequest.of(page, size));
    }

    @PostMapping("/transfer")
    ResponseEntity<List<AccountOperationResponseDto>> transfer(@Valid @RequestBody TransferOperationRequestDto transferOperationRequestDto) {
        return operationService.transfer(transferOperationRequestDto);
    }


}
