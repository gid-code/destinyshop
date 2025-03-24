package com.gidcode.destinyshop.request;

import org.hibernate.annotations.NaturalId;

public record CreateUserRequest(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
