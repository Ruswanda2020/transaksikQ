package com.Onedev.transaksiQ.service.impl;

import com.Onedev.transaksiQ.entity.Banner;
import com.Onedev.transaksiQ.repository.BannerRepository;
import com.Onedev.transaksiQ.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BannerServiceImpl implements BannerService {

     private final BannerRepository bannerRepository;

    @Override
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }
}
