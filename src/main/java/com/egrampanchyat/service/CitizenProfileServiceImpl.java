package com.egrampanchyat.service;

import org.springframework.stereotype.Service;

import com.egrampanchyat.dto.CitizenProfileRequestDto;
import com.egrampanchyat.entity.CitizenProfile;
import com.egrampanchyat.entity.Role;
import com.egrampanchyat.entity.User;
import com.egrampanchyat.exception.ResourceAlreadyExistsException;
import com.egrampanchyat.exception.ResourceNotFoundException;
import com.egrampanchyat.repository.CitizenProfileRepository;
import com.egrampanchyat.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CitizenProfileServiceImpl implements CitizenProfileService {

    private final CitizenProfileRepository citizenRepo;
    private final UserRepository userRepo;

    @Override
    public CitizenProfile createProfile(String email, CitizenProfileRequestDto dto) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getRole() != Role.ROLE_CITIZEN) {
            throw new RuntimeException("Only citizens can create profile");
        }

        if (citizenRepo.findByUser(user).isPresent()) {
            throw new ResourceAlreadyExistsException("Citizen profile already exists");
        }

        if (citizenRepo.existsByAadhaarNo(dto.getAadhaarNo())) {
            throw new ResourceAlreadyExistsException("Aadhaar already registered");
        }

        CitizenProfile profile = CitizenProfile.builder()
                .user(user)
                .address(dto.getAddress())
                .wardNo(dto.getWardNo())
                .aadhaarNo(dto.getAadhaarNo())
                .dob(dto.getDob())
                .gender(dto.getGender())
                .build();

        return citizenRepo.save(profile);
    }

    @Override
    public CitizenProfile getMyProfile(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return citizenRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
    }

    // ✅ UPDATE PROFILE
    @Override
    public CitizenProfile updateProfile(String email, CitizenProfileRequestDto dto) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        CitizenProfile profile = citizenRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        profile.setAddress(dto.getAddress());
        profile.setWardNo(dto.getWardNo());
        profile.setAadhaarNo(dto.getAadhaarNo());
        profile.setDob(dto.getDob());
        profile.setGender(dto.getGender());

        return citizenRepo.save(profile);
    }
}
