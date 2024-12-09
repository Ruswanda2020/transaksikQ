package com.Onedev.transaksiQ.dto.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BannerResponse {

     @JsonProperty("banner_name")
     String bannerName;
     @JsonProperty("banner_image")
     String bannerImage;
     String description;
}
