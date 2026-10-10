package com.complaint.management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.complaint.management.entity.Complaint;
import com.complaint.management.service.ComplaintService;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "http://localhost:5174"
})
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    // ==========================================
    // CREATE / SUBMIT COMPLAINT
    // ==========================================

    @PostMapping
    public ResponseEntity<Complaint> createComplaint(
            @RequestBody Complaint complaint) {

        Long userId = complaint.getUser().getId();

        Complaint savedComplaint =
                complaintService.submitComplaint(complaint, userId);

        return ResponseEntity.ok(savedComplaint);
    }

    // ==========================================
    // GET ALL COMPLAINTS - ADMIN
    // ==========================================

    @GetMapping
    public ResponseEntity<List<Complaint>> getAllComplaints() {

        List<Complaint> complaints =
                complaintService.getAllComplaints();

        return ResponseEntity.ok(complaints);
    }

    // ==========================================
    // GET COMPLAINT BY ID
    // ==========================================

    @GetMapping("/{id}")
    public ResponseEntity<Complaint> getComplaintById(
            @PathVariable Long id) {

        Complaint complaint =
                complaintService.getComplaintById(id);

        return ResponseEntity.ok(complaint);
    }

    // ==========================================
    // GET COMPLAINTS BY USER
    // ==========================================

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Complaint>> getComplaintsByUser(
            @PathVariable Long userId) {

        List<Complaint> complaints =
                complaintService.getComplaintsByUser(userId);

        return ResponseEntity.ok(complaints);
    }

    // ==========================================
    // UPDATE COMPLAINT STATUS + ADMIN REMARKS
    // ==========================================

    @PutMapping("/{id}/status")
    public ResponseEntity<Complaint> updateComplaintStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        String status = request.get("status");
        String adminRemarks = request.get("adminRemarks");

        Complaint updatedComplaint =
                complaintService.updateComplaintStatus(
                        id,
                        status,
                        adminRemarks
                );

        return ResponseEntity.ok(updatedComplaint);
    }
}