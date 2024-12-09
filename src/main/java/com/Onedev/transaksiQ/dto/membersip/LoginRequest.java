package com.Onedev.transaksiQ.dto.membersip;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Parameter email tidak boleh kosong")
    @NotNull(message = "Parameter email tidak boleh null")
    @Email(message = "Paramter email tidak sesuai format")
    String email;

    @Size(min = 8, message = "Parameter password minimum 8 karakter")
    @NotNull(message = "Parameter password tidak boleh null")
    String password;
}
