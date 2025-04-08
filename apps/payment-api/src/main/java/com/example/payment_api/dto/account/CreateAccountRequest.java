package com.example.payment_api.dto.account;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(name = "CreateAccountRequest")
public class CreateAccountRequest {

    @Schema(description = "Initial balance of the account", example = "100")
    private double initialBalance;
}
