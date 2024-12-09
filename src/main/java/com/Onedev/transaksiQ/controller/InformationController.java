package com.Onedev.transaksiQ.controller;

import com.Onedev.transaksiQ.dto.GenericResponse;
import com.Onedev.transaksiQ.dto.information.BannerResponse;
import com.Onedev.transaksiQ.dto.information.ServiceResponse;
import com.Onedev.transaksiQ.entity.Banner;
import com.Onedev.transaksiQ.entity.Service;
import com.Onedev.transaksiQ.service.BannerService;
import com.Onedev.transaksiQ.service.ServiceDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/content")
@RequiredArgsConstructor
public class InformationController {

    private final BannerService bannerService;
    private final ServiceDataService serviceDataService;

    @GetMapping("/banners")
    public ResponseEntity<GenericResponse<List<BannerResponse>>> getAllBanners() {
        List<Banner> banners = bannerService.getAllBanners();
        List<BannerResponse> bannerResponses = banners.stream()
                .map(banner -> new BannerResponse(
                        banner.getBannerName(),
                        banner.getBannerImage(),
                        banner.getDescription()))
                .collect(Collectors.toList());
        GenericResponse<List<BannerResponse>> response = new GenericResponse<>(
                0, "Sukses",
                bannerResponses);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/services")
    public ResponseEntity<GenericResponse<List<ServiceResponse>>> getAllServices() {
        List<Service> services = serviceDataService.getAllService();
        List<ServiceResponse> serviceResponses = services.stream()
                .map(service -> new ServiceResponse(
                        service.getServiceCode(),
                        service.getServiceName(),
                        service.getServiceIcon(),
                        service.getServiceTariff()))
                .toList();
        GenericResponse<List<ServiceResponse>> response = new GenericResponse<>(
                0, "Sukses",
                serviceResponses);

        return ResponseEntity.ok(response);
    }
}
