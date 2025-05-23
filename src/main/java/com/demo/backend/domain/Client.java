package com.demo.backend.domain;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client extends Person {
    private String password;
    private boolean state;
}
