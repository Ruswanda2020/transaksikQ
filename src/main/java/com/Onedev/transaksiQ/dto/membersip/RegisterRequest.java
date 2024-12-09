package com.Onedev.transaksiQ.dto.membersip;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequest {

    @JsonProperty("first_name")
    @NotBlank(message = "Parameter first name tidak boleh kosong")
    @NotNull(message = "Parameter first name tidak boleh null")
    String firstName;

    @JsonProperty("last_name")
    @NotBlank(message = "Parameter last name tidak boleh kosong")
    @NotNull(message = "Parameter last name tidak boleh null")
    String lastName;

    @NotBlank(message = "Parameter email tidak boleh kosong")
    @NotNull(message = "Parameter email tidak boleh null")
    @Email(message = "Paramter email tidak sesuai format")
    String email;

    @Size(min = 8, message = "Parameter password minimum 8 karakter")
    @NotNull(message = "Parameter password tidak boleh null")
    String password;
}
