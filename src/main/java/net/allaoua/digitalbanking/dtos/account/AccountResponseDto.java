package net.allaoua.digitalbanking.dtos.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.allaoua.digitalbanking.entity.BankAccount;
import net.allaoua.digitalbanking.enums.OperationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountResponseDto {
    private String id;
    private double amount;
    private OperationType type;
    private String description;
    private BankAccount account;
}

