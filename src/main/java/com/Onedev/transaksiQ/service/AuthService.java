package com.Onedev.transaksiQ.service;

import com.Onedev.transaksiQ.dto.membersip.LoginRequest;
import com.Onedev.transaksiQ.dto.membersip.RegisterRequest;
import com.Onedev.transaksiQ.entity.User;

public interface AuthService {

    String login(LoginRequest loginRequest);
    String Register(RegisterRequest registerDto);
    User getEmail(String email);
}
