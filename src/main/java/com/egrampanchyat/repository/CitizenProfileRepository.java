package com.egrampanchyat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egrampanchyat.entity.CitizenProfile;
import com.egrampanchyat.entity.User;

public interface CitizenProfileRepository
        extends JpaRepository<CitizenProfile, Long> {

    // ================= EXISTING USAGE =================
    Optional<CitizenProfile> findByUser(User user);

    boolean existsByAadhaarNo(String aadhaarNo);

    // ================= ADMIN USAGE =================
    // findAll() already provided by JpaRepository
}
