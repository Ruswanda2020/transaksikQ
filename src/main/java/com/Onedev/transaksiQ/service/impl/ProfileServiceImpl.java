package com.Onedev.transaksiQ.service.impl;


import com.Onedev.transaksiQ.dto.membersip.ProfileResponse;
import com.Onedev.transaksiQ.dto.membersip.ProfileUpdateRequest;
import com.Onedev.transaksiQ.entity.User;
import com.Onedev.transaksiQ.repository.UserRepository;
import com.Onedev.transaksiQ.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public ProfileResponse getProfileByEmail(String email) {
       User user  = userRepository.findByEmail(email)
               .orElseThrow();

        return mapToDto(user);
    }

    @Override
    public ProfileResponse updateUserProfileByEmail(String email, ProfileUpdateRequest profileUpdateRequest) {
        User user = userRepository.findByEmail(email).orElseThrow();

        user.setFirstName(profileUpdateRequest.getFirstName() == null ||
                profileUpdateRequest.getFirstName().isEmpty() ?
                user.getFirstName() : profileUpdateRequest.getFirstName());

        user.setLastName(profileUpdateRequest.getLastName() == null ||
                profileUpdateRequest.getLastName().isEmpty() ?
                user.getLastName() : profileUpdateRequest.getLastName());

        user.setProfileImage(profileUpdateRequest.getProfileImage() == null ||
                profileUpdateRequest.getProfileImage().isEmpty() ?
                user.getProfileImage() : profileUpdateRequest.getProfileImage());

        User userUpdate = userRepository.save(user);
        return mapToDto(userUpdate);
    }

    private ProfileResponse mapToDto(User user){
  return modelMapper.map(user, ProfileResponse.class);
 }



}
