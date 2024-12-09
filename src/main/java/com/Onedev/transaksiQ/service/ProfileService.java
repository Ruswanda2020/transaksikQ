package com.Onedev.transaksiQ.service;


import com.Onedev.transaksiQ.dto.membersip.ProfileResponse;
import com.Onedev.transaksiQ.dto.membersip.ProfileUpdateRequest;

public interface ProfileService {
    ProfileResponse getProfileByEmail(String email);
    ProfileResponse updateUserProfileByEmail(String email, ProfileUpdateRequest profileUpdateRequest);
}
