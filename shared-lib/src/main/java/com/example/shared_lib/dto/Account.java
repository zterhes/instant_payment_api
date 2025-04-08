package com.example.shared_lib.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Account {

    private Integer id;
    private UUID ownerId;
}
