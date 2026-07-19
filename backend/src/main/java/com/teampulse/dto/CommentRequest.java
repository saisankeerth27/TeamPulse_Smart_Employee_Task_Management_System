package com.teampulse.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommentRequest {

    @NotBlank
    private String message;

    private Long taskId;

    private Long employeeId;

}
