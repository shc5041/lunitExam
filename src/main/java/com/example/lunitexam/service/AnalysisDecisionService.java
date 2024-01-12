package com.example.lunitexam.service;

import com.example.lunitexam.exception.ErrorCode;
import com.example.lunitexam.exception.LunitExamException;
import com.example.lunitexam.model.dao.AnalysisDecision;
import com.example.lunitexam.model.dao.GridAnalysis;
import com.example.lunitexam.model.dao.SlideInfo;
import com.example.lunitexam.model.dto.AnalysisDecisionDto;
import com.example.lunitexam.repository.AnalysisDecisionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnalysisDecisionService {

    private final AnalysisDecisionRepository analysisDecisionRepository;
    private final GridAnalysisService gridAnalysisService;

    private final SlideInfoService slideInfoService;

    public Page<AnalysisDecision> findAll(Pageable pageable) {
        return analysisDecisionRepository.findAll(pageable);
    }
    public Page<AnalysisDecision> findByUserId(String userId, Pageable pageable) {
        return analysisDecisionRepository.findByUserId(userId, pageable);
    }
    public Page<AnalysisDecision> findByOriginFileName(String originFileName, Pageable pageable) {
        return analysisDecisionRepository.findByOriginFileName(originFileName, pageable);
    }

    /**
     * 분석 요청이 들어오면 gridAnalysis로 분석 요청을 한다.
     * 그 결과를 가지고 score 및 decision를 결정한다.
     * 또, grid data가 20개 미만이면 exception을 발생시킨다.
     * @param userId
     * @param slideInfoIdx
     * @param originFileName
     */
    @Transactional
    public AnalysisDecisionDto requestGridAnalysis(String userId,Long slideInfoIdx, String originFileName){
        Optional<SlideInfo> slideInfoOptional = slideInfoService.findByIdxAndOriginFileName(slideInfoIdx,originFileName);
        if(slideInfoOptional.isPresent()){
            //file 이 존재 하면 먼저 AnalysisDecision에 insert를 한후 이후에 grid 데이터 저장후 update를 해야한다.
            SlideInfo slideInfo = slideInfoOptional.get();
            if(Files.exists(Paths.get(slideInfo.getPath()))){
                AnalysisDecision analysisDecision = AnalysisDecision.builder()
                                .path(slideInfo.getPath())
                                .originFileName(originFileName)
                                .userId(userId)
                        .build();
                analysisDecision = analysisDecisionRepository.save(analysisDecision);
                List<GridAnalysis> gridAnalysisList = gridAnalysisService.requestGridAnalysis(analysisDecision);
                analysisDecision.setGridAnalyses(gridAnalysisList);
                analysisDecision.setScore(getRandomFloat());
                analysisDecision.setDecision(getRandomBoolean());
                AnalysisDecisionDto analysisDecisionDto = analysisDecision.toEntity(analysisDecision);
                analysisDecisionRepository.save(analysisDecision);
                if(gridAnalysisList.size() < 20) {
                    throw new LunitExamException(ErrorCode.BAD_GRID_RESULT_DATA);
                }else {
                    return analysisDecisionDto;
                }

            }else {
                throw new LunitExamException(ErrorCode.NOT_FOUND);
            }

        }else {
            throw new LunitExamException(ErrorCode.NOT_FOUND);
        }
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
