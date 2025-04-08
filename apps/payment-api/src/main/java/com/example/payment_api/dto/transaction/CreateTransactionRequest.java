package com.example.payment_api.dto.transaction;


import com.example.shared_lib.validation.ValidUUID;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(name = "CreateTransactionRequest")
public class CreateTransactionRequest {

    @Schema(description = "Transaction amount", example = "100")
    @NotNull
    @Min(1)
    private Integer amount;

    @Schema(description = "Recipient id", example = "b9c7b6c8-9a8b-4c9d-9e8f-7a6b5c4d3e2f")
    @NotNull
    private UUID recipient;

}
