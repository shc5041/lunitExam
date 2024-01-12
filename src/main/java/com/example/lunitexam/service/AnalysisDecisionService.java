package com.example.lunitexam.service;

import com.example.lunitexam.repository.AnalysisDecisionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnalysisDecisionService {

    private final AnalysisDecisionRepository analysisDecisionRepository;
    private final GridAnalysisService gridAnalysisService;

    /**
     * 분석 요청이 들어오면 gridAnalysis로 분석 요청을 한다.
     * 그 결과를 가지고 score 및 decision를 결정한다.
     * @param userId
     * @param path
     */
    public void requestGridAnalysis(String userId, String path){

    }
    private Boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }

    private Float getRandomFloat() {
        Random random = new Random();
        return random.nextFloat();
    }
}
