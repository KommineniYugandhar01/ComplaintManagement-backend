package com.complaint.management.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.complaint.management.entity.Complaint;
import com.complaint.management.entity.User;
import com.complaint.management.repository.ComplaintRepository;
import com.complaint.management.repository.UserRepository;

@Service
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    public ComplaintService(ComplaintRepository complaintRepository,
                            UserRepository userRepository) {
        this.complaintRepository = complaintRepository;
        this.userRepository = userRepository;
    }

    // Submit a new complaint
    public Complaint submitComplaint(Complaint complaint, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        complaint.setUser(user);
        complaint.setStatus("PENDING");
        complaint.setCreatedDate(LocalDateTime.now());
        complaint.setUpdatedDate(LocalDateTime.now());

        return complaintRepository.save(complaint);
    }

    // Get all complaints submitted by a particular user
    public List<Complaint> getComplaintsByUser(Long userId) {

        return complaintRepository.findByUserId(userId);
    }

    // Get all complaints - Admin
    public List<Complaint> getAllComplaints() {

        return complaintRepository.findAll();
    }

    // Get complaint by ID
    public Complaint getComplaintById(Long id) {

        return complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
    }

    // Update complaint status and admin remarks
    public Complaint updateComplaintStatus(
            Long complaintId,
            String status,
            String adminRemarks) {

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        complaint.setStatus(status);
        complaint.setAdminRemarks(adminRemarks);
        complaint.setUpdatedDate(LocalDateTime.now());

        return complaintRepository.save(complaint);
    }
}