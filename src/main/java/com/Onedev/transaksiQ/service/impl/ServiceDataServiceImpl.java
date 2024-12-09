package com.Onedev.transaksiQ.service.impl;

import com.Onedev.transaksiQ.dto.transaction.TransactionRequest;
import com.Onedev.transaksiQ.entity.Service;
import com.Onedev.transaksiQ.exception.TransactionApiException;
import com.Onedev.transaksiQ.repository.ServiceRepository;
import com.Onedev.transaksiQ.service.ServiceDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@RequiredArgsConstructor
@org.springframework.stereotype.Service
public class ServiceDataServiceImpl implements ServiceDataService {

    private final ServiceRepository serviceRepository;

    @Override
    public List<Service> getAllService() {
        return serviceRepository.findAll();
    }


    @Override
    public Service getServiceByCode(String serviceCode) {
        Service service = serviceRepository.findByServiceCode(serviceCode)
                .orElseThrow(() -> new TransactionApiException(HttpStatus.NOT_FOUND, "Service ataus Layanan tidak ditemukan", 404));

        // Convert Service entity to TransactionRequest DTO
        return service;
    }
}
