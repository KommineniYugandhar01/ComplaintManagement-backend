package com.complaint.management.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.complaint.management.entity.Complaint;
import com.complaint.management.entity.User;
import com.complaint.management.service.ComplaintService;

@RestController
@RequestMapping("/api/complaints")
@CrossOrigin(origins = {
    "http://localhost:5173",
    "http://localhost:5174",
    "https://complaint-management-frontend-rose.vercel.app"
})
public class ComplaintController {

    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    // Submit complaint using the user ID from the URL
    @PostMapping("/user/{userId}")
    public ResponseEntity<Complaint> submitComplaint(
            @PathVariable Long userId,
            @RequestBody Complaint complaint) {

        Complaint savedComplaint =
                complaintService.submitComplaint(complaint, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedComplaint);
    }

    // Alternative endpoint for existing clients using POST /api/complaints
    @PostMapping
    public ResponseEntity<Complaint> createComplaint(
            @RequestBody Complaint complaint) {

        if (complaint.getUser() == null
                || complaint.getUser().getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User ID is required"
            );
        }

        Long userId = complaint.getUser().getId();

        Complaint savedComplaint =
                complaintService.submitComplaint(complaint, userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedComplaint);
    }

    // Get all complaints for admin
    @GetMapping
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(
                complaintService.getAllComplaints()
        );
    }

    // Get a specific complaint
    @GetMapping("/{id}")
    public ResponseEntity<Complaint> getComplaintById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                complaintService.getComplaintById(id)
        );
    }

    // Get complaints belonging to a user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Complaint>> getComplaintsByUser(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                complaintService.getComplaintsByUser(userId)
        );
    }

    // Update complaint status and admin remarks
    @PutMapping("/{id}/status")
    public ResponseEntity<Complaint> updateComplaintStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> request) {

        String status = request.get("status");
        String adminRemarks = request.get("adminRemarks");

        if (status == null || status.isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Status is required"
            );
        }

        Complaint updatedComplaint =
                complaintService.updateComplaintStatus(
                        id,
                        status,
                        adminRemarks
                );

        return ResponseEntity.ok(updatedComplaint);
    }
}