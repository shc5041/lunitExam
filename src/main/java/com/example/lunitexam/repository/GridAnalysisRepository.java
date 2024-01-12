package com.example.lunitexam.repository;

import com.example.lunitexam.model.dao.AnalysisDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GridAnalysisRepository extends JpaRepository<AnalysisDecision, Long> {

}
