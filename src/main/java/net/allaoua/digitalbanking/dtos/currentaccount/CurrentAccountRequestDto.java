package net.allaoua.digitalbanking.dtos.currentaccount;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrentAccountRequestDto {
    @NotBlank(message = "currency is required")
    private String currency;
    @Min(value = 0,message = "balance shouldn't be less than 0")
    private double balance;
    @NotNull
    private String customerId;
    @NotNull
    private double overDraft;
}
