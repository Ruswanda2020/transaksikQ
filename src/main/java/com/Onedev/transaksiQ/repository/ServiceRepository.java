package com.Onedev.transaksiQ.repository;

import com.Onedev.transaksiQ.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByServiceCode(String serviceCode);

}
