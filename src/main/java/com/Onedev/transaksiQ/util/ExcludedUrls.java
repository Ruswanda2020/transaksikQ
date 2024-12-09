package com.Onedev.transaksiQ.util;

import java.util.List;

public class ExcludedUrls {

    public static final List<String> URLS = List.of(
            "/api/v1/user/login",
            "/api/v1/user/register",
            "/api/v1/banner",
            "/api/v1/service",
            "/api/v1/content/banners",
            "/api/v1/content/services",
            "/swagger-ui",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**"
    );
}
