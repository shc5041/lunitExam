package com.example.lunitexam.service;

import com.example.lunitexam.model.dao.AnalysisDecision;
import com.example.lunitexam.model.dao.GridAnalysis;
import com.example.lunitexam.repository.GridAnalysisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class GridAnalysisService {

    private final GridAnalysisRepository gridAnalysisRepository;
    @Value("${slice.min}")
    private int sliceMin;

    @Value("${slice.max}")
    private int sliceMax;

    public Page<GridAnalysis> findAll(Pageable pageable) {
        return gridAnalysisRepository.findAll(pageable);
    }

    public Page<GridAnalysis> findByUserId(String userId, Pageable pageable) {
        return gridAnalysisRepository.findByUserId(userId, pageable);
    }

    public Page<GridAnalysis> findByUserIdAndCreatedDateBetween(String userId, LocalDateTime startDateTime, LocalDateTime endDateTime, Pageable pageable ){
        return gridAnalysisRepository.findByUserIdAndCreatedDateBetween(userId, startDateTime, endDateTime,  pageable);
    }

    public Page<GridAnalysis> findByAnalysisDecisionIdx(Long Idx, Pageable pageable) {
        return gridAnalysisRepository.findByAnalysisDecision_Idx(Idx, pageable);
    }



    @Transactional
    public List<GridAnalysis> requestGridAnalysis(AnalysisDecision analysisDecision) {
        int count = getRandomCount();
        List<GridAnalysis> gridAnalysisList = new ArrayList<>();
        for (int i = 0; i < count; i++) {

            float intratumoralMin = getRandomFloat(0.0F);
            float intratumoralMax = getRandomFloat(intratumoralMin);
            float intratumoralAvg = (intratumoralMin + intratumoralMax) / 2;

            float stromalMin = getRandomFloat(0.0F);
            float stromalMax = getRandomFloat(stromalMin);
            float stromalAvg = (stromalMin + stromalMax) / 2;

            GridAnalysis gridAnalysis = GridAnalysis.builder()
                    .analysisDecision(analysisDecision)
                    .intratumoralMin(intratumoralMin)
                    .intratumoralAvg(intratumoralAvg)
                    .intratumoralMax(intratumoralMax)
                    .stromalMin(stromalMin)
                    .stromalAvg(stromalAvg)
                    .stromalMax(stromalMax)
                    .originFileName(analysisDecision.getOriginFileName())
                    .userId(analysisDecision.getUserId())
                    .build();
            gridAnalysisList.add(gridAnalysisRepository.save(gridAnalysis));
        }
        return gridAnalysisList;
    }

    private int getRandomCount() {
        Random random = new Random();
        // 17부터 50까지의 난수 생성 (0이 나올수 있으므로 max+1을 해주고 또 초기값을 설정해야 하므로 sliceMin을 설정함.
        int randomNumber = random.nextInt(sliceMax + 1 - sliceMin) + sliceMin;
        return randomNumber;
    }
    private Float getRandomFloat(Float value) {
        Random random = new Random();

        if (value == null || value < 0.1F) {
            return random.nextFloat();
        } else {
            return value + random.nextFloat() * (1.0f - value);
        }
    }
}
