package com.complaint.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.complaint.management.entity.Complaint;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    List<Complaint> findByUserId(Long userId);

    List<Complaint> findByStatus(String status);
}