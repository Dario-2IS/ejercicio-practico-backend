package com.demo.backend.infrastructure.service.template;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiError {
    private String message;
    private int status;
    private boolean success = false;
}
