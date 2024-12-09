package com.Onedev.transaksiQ.dto.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TopUpRequest {

    @JsonProperty("top_up_amount")
    @NotNull(message = "Top-up tidak boleh null.")
    @Min(value = 1, message = "Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0")
    private Integer TopUpAmount;
}
