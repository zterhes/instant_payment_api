package com.example.payment_api.dto.transaction;


import com.example.shared_lib.validation.ValidUUID;
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
public class CreateTransactionRequest {

    @NotNull
    @Min(1)
    private Integer amount;

    @NotNull
    private UUID recipient;

}
