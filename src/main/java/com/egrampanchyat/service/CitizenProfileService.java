package com.egrampanchyat.service;

import com.egrampanchyat.dto.CitizenProfileRequestDto;
import com.egrampanchyat.entity.CitizenProfile;

public interface CitizenProfileService {

    CitizenProfile createProfile(String email, CitizenProfileRequestDto dto);

    CitizenProfile getMyProfile(String email);
    CitizenProfile updateProfile(String email, CitizenProfileRequestDto dto);

}

