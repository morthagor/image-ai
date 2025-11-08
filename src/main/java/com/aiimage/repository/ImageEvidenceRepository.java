package com.aiimage.repository;

import com.aiimage.domain.ImageEvidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageEvidenceRepository extends JpaRepository<ImageEvidence, Long> {
    List<ImageEvidence> findByServiceOrder_Id(Long serviceOrderId);
}
