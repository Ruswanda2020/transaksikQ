package com.Onedev.transaksiQ.service;



import com.Onedev.transaksiQ.entity.Service;

import java.util.List;

public interface ServiceDataService {
    List<Service> getAllService();
    Service getServiceByCode(String serviceCode);
}
