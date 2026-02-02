package com.egrampanchyat.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.egrampanchyat.dto.ComplaintRequestDto;
import com.egrampanchyat.entity.CitizenProfile;
import com.egrampanchyat.entity.Complaint;
import com.egrampanchyat.entity.ComplaintStatus;
import com.egrampanchyat.entity.User;
import com.egrampanchyat.exception.ResourceNotFoundException;
import com.egrampanchyat.repository.CitizenProfileRepository;
import com.egrampanchyat.repository.ComplaintRepository;
import com.egrampanchyat.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ComplaintServiceImpl implements ComplaintService {

    private final UserRepository userRepo;
    private final CitizenProfileRepository citizenRepo;
    private final ComplaintRepository complaintRepo;

    @Override
    public Complaint raiseComplaint(String email, ComplaintRequestDto dto) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        CitizenProfile profile = citizenRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen profile not found"));

        Complaint complaint = Complaint.builder()
                .citizenProfile(profile)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(ComplaintStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        return complaintRepo.save(complaint);
    }

    @Override
    public List<Complaint> myComplaints(String email) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        CitizenProfile profile = citizenRepo.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Citizen profile not found"));

        return complaintRepo.findByCitizenProfile(profile);
    }

    @Override
    public List<Complaint> getAllComplaints() {
        return complaintRepo.findAll();
    }

    @Override
    public Complaint updateStatus(Long id, ComplaintStatus status, String remarks) {

        Complaint complaint = complaintRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Complaint not found"));

        complaint.setStatus(status);
        complaint.setRemarks(remarks);

        return complaintRepo.save(complaint);
    }
}

