package com.example.lunitexam.repository;

import com.example.lunitexam.model.dao.AnalysisDecision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisDecisionRepository extends JpaRepository<AnalysisDecision, Long> {
    Page<AnalysisDecision> findByUserId(String userId, Pageable pageable);
    Page<AnalysisDecision> findByOriginFileName(String originFileName, Pageable pageable);

}
