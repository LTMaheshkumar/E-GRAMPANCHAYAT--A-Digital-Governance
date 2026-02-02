package com.egrampanchyat.service;

import java.util.List;

import com.egrampanchyat.dto.ComplaintRequestDto;
import com.egrampanchyat.entity.Complaint;
import com.egrampanchyat.entity.ComplaintStatus;

public interface ComplaintService {

    Complaint raiseComplaint(String email, ComplaintRequestDto dto);

    List<Complaint> myComplaints(String email);

    // Admin
    List<Complaint> getAllComplaints();

    Complaint updateStatus(Long id, ComplaintStatus status, String remarks);
}

