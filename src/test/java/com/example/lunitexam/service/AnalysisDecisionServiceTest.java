package com.example.lunitexam.service;

import com.example.lunitexam.model.dao.AnalysisDecision;
import com.example.lunitexam.model.dto.AnalysisDecisionDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class AnalysisDecisionServiceTest {

    @Autowired
    private AnalysisDecisionService analysisDecisionService;
    @Test
    void requestGridAnalysis() throws JsonProcessingException {
        AnalysisDecisionDto analysisDecision = analysisDecisionService.requestGridAnalysis("test",1L);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(analysisDecision));
    }

    @Test
    void findAll() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("idx").descending());
        Page<AnalysisDecision> analysisDecisionPage = analysisDecisionService.findAll(pageable);
        assertEquals(analysisDecisionPage.getTotalElements(), 7);
    }

    @Test
    void findByUserId() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("idx").descending());
        Page<AnalysisDecision> analysisDecisionPage = analysisDecisionService.findByUserId("test", pageable);
        assertEquals(analysisDecisionPage.getTotalElements(), 7);
    }

    @Test
    void findByOriginFileName() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("idx").descending());
        Page<AnalysisDecision> analysisDecisionPage = analysisDecisionService.findByOriginFileName("CMU-1-JP2K-33005 (1).svs", pageable);
        assertEquals(analysisDecisionPage.getTotalElements(), 7);
    }
}