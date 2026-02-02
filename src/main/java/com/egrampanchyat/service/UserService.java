package com.egrampanchyat.service;

import com.egrampanchyat.dto.LoginRequestDto;
import com.egrampanchyat.dto.LoginResponseDto;
import com.egrampanchyat.dto.RegisterRequestDto;
import com.egrampanchyat.entity.User;

public interface UserService {

    User register(RegisterRequestDto dto);

    LoginResponseDto login(LoginRequestDto dto);
}
