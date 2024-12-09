package com.Onedev.transaksiQ.controller;

import com.Onedev.transaksiQ.dto.GenericResponse;
import com.Onedev.transaksiQ.dto.membersip.*;
import com.Onedev.transaksiQ.security.SecurityUtils;
import com.Onedev.transaksiQ.service.AuthService;
import com.Onedev.transaksiQ.service.ProfileService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.Onedev.transaksiQ.dto.membersip.LoginRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private static final String UPLOADED_FOLDER = "uploadGambler/";
    private final AuthService authService;
    private final ProfileService profileService;

    @PostMapping(value = {"/register"})
    public ResponseEntity<GenericResponse<?>> register(@RequestBody @Valid RegisterRequest registerRequest) {

        authService.Register(registerRequest);
        GenericResponse<?> response = new GenericResponse<>(
                0,
                "Registrasi berhasil silahkan login",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<GenericResponse<?>> login(@RequestBody @Valid LoginRequest loginRequest){

        String token = authService.login(loginRequest);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(token);

        GenericResponse<?> response = new GenericResponse<>(0, "Login Sukses", jwtAuthResponse);

        return ResponseEntity.ok(response);

    }

    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/profile")
    public ResponseEntity<GenericResponse<ProfileResponse>> getProfile() {

        String email = SecurityUtils.getCurrentUserEmail();
        ProfileResponse profile = profileService.getProfileByEmail(email);
        GenericResponse<ProfileResponse> response = new GenericResponse<>(
                0,
                "Sukses",
                profile
        );
        return ResponseEntity.ok(response);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/profile/update")
    public ResponseEntity<GenericResponse<ProfileResponse>> updateUserProfile(@RequestBody ProfileUpdateRequest
            profileUpdateRequest){
        String email = SecurityUtils.getCurrentUserEmail();
        ProfileResponse updatedProfile = profileService.updateUserProfileByEmail(email, profileUpdateRequest);

        GenericResponse<ProfileResponse> response = new GenericResponse<>(
                0,
                "Update Profile berhasil",
                updatedProfile
        );
        return ResponseEntity.ok(response);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping(value = "/profile/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<GenericResponse<ProfileResponse>> uploadFileUpload( @RequestParam("file") MultipartFile file) {
        try {
            String contentType = file.getContentType();
            if (!contentType.equals("image/jpeg") && !contentType.equals("image/png")) {
                return ResponseEntity.badRequest().body(
                        new GenericResponse<>(102, "Format Image tidak sesuai", null)
                );
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            ProfileUpdateRequest profileUpdateRequest = new ProfileUpdateRequest();
            profileUpdateRequest.setProfileImage(file.getOriginalFilename());
            String email = SecurityUtils.getCurrentUserEmail();
            ProfileResponse updatedProfile = profileService.updateUserProfileByEmail(email, profileUpdateRequest);
            return ResponseEntity.ok(new GenericResponse<>(0, "Update Profile Image berhasil", updatedProfile));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(
                    new GenericResponse<>(500, "File upload failed: " + e.getMessage(), null)
            );
        }
    }
}
