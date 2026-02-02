package com.egrampanchyat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.egrampanchyat.dto.ComplaintStatusRequestDto;
import com.egrampanchyat.entity.Complaint;
import com.egrampanchyat.service.ComplaintService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/complaints")
@RequiredArgsConstructor
public class ComplaintAdminController {

    private final ComplaintService service;

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CLERK')")
    public ResponseEntity<List<Complaint>> all() {
        return ResponseEntity.ok(service.getAllComplaints());
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_CLERK')")
    public ResponseEntity<Complaint> update(
            @PathVariable Long id,
            @RequestBody ComplaintStatusRequestDto dto) {

        return ResponseEntity.ok(
                service.updateStatus(id, dto.getStatus(), dto.getRemarks()));
    }
}
