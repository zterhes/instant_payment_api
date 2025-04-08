package com.example.shared_lib.dto;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GetTransactionResponse {

    private Integer id;

    private Account recipient;

    private double amount;

    private LocalDateTime createdAt;
}
