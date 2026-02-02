package com.egrampanchyat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egrampanchyat.entity.CitizenProfile;
import com.egrampanchyat.entity.Complaint;
import com.egrampanchyat.entity.ComplaintStatus;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByCitizenProfile(CitizenProfile profile);
    
 // ✅ FOR ADMIN DASHBOARD COUNT
    long countByStatus(ComplaintStatus status);
}

