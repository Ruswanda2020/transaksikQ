package com.Onedev.transaksiQ.dto.information;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceResponse {

    @JsonProperty("service_code")
    private String serviceCode;
    @JsonProperty("service_name")
    private String serviceName;
    @JsonProperty("service_icon")
    private String serviceIcon;
    @JsonProperty("service_tariff")
    int serviceTariff;
}
