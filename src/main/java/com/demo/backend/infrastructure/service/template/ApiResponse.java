package com.demo.backend.infrastructure.service.template;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ApiResponse<T> {
    private T data;
    private boolean success = true;
    private String message;
}
