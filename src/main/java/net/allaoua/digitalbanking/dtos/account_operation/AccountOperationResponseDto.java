package net.allaoua.digitalbanking.dtos.account_operation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class AccountOperationResponseDto {
    private String id;
    private double amount;
    private OperationType type;
    private String description;
    @JsonIgnoreProperties({"customer"})
    private BankAccount account;
}
