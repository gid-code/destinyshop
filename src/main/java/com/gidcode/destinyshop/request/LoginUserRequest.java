package com.gidcode.destinyshop.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LoginUserRequest(
        @NotBlank @Email
        String email,
        @NotBlank @Min(value = 6)
        String password
) {
}
