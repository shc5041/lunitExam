package com.example.lunitexam.repository;

import com.example.lunitexam.model.dao.SlideInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SlideInfoRepository extends JpaRepository<SlideInfo, Long> {

    Optional<SlideInfo> findByPath(String path);

    Page<SlideInfo> findByUserId(String userId, Pageable pageable);

    Page<SlideInfo> findByUserIdAndOriginFileName(String userId, String originFileName, Pageable pageable);

    Optional<SlideInfo> findByIdxAndOriginFileName(Long idx, String originFileName);

    Page<SlideInfo> findByUserIdAndCreatedDateBetween(String userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
