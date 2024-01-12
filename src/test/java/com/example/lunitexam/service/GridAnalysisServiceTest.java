package com.example.lunitexam.service;

import com.example.lunitexam.model.dao.GridAnalysis;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GridAnalysisServiceTest {

    @Autowired
    private GridAnalysisService gridAnalysisService;
    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("idx").descending());
        Page<GridAnalysis> gridAnalysisPage = gridAnalysisService.findAll(pageable);
        assertEquals(gridAnalysisPage.getTotalElements(), 262);
    }

    @Test
    void findByUserId() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("idx").descending());
        Page<GridAnalysis> gridAnalysisPage = gridAnalysisService.findByUserId("test",pageable);
        assertEquals(gridAnalysisPage.getTotalElements(), 262);
    }

    @Test
    void findByAnalysisDecisionIdx() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("idx").descending());
        Page<GridAnalysis> gridAnalysisPage = gridAnalysisService.findByAnalysisDecisionIdx(3L,pageable);
        assertEquals(gridAnalysisPage.getTotalElements(), 49);
    }

    @Test
    void testFindByUserId() {
    }
}