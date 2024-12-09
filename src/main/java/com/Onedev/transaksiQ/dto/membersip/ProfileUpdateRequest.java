package com.Onedev.transaksiQ.dto.membersip;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUpdateRequest {

    @JsonProperty("first_name")
    String firstName;
    @JsonProperty("last_name")
    String lastName;

    @JsonProperty("profile_image")
    String profileImage;


}
