package com.example.lunitexam.service;

import com.example.lunitexam.repository.GridAnalysisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
    public void requestGridAnalysis(String userId, String originFileName){

    }

    private int getRandomCount() {
        Random random = new Random();
        // 20부터 50까지의 난수 생성 (0이 나올수 있으므로 max+1을 해주고 또 초기값을 설정해야 하므로 sliceMin을 설정함.
        int randomNumber = random.nextInt(sliceMax+1 - sliceMin) + sliceMin;
        return randomNumber;
    }


}
