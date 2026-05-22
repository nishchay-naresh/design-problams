package com.nishchay.file.pojo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UserForm {

    @NotNull
    private String email;   // Must not be null, but "" is allowed

    @NotEmpty
    private List<String> roles; // Must not be null or empty

    @NotBlank
    private String username; // Must not be null, empty, or whitespace-only
}