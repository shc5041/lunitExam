package com.example.lunitexam.repository;

import com.example.lunitexam.model.dao.GridAnalysis;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface GridAnalysisRepository extends JpaRepository<GridAnalysis, Long> {
    Page<GridAnalysis> findByAnalysisDecision_Idx(Long idx, Pageable pageable); //부모의 mapped 이름 + 부모의 key 값으로 찾아야 한다.
    Page<GridAnalysis> findByUserId(String userId, Pageable pageable );

    Page<GridAnalysis> findByUserIdAndCreatedDateBetween(String userId, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable);
}
