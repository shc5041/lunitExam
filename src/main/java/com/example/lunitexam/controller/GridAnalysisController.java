package com.example.lunitexam.controller;

import com.example.lunitexam.model.dao.GridAnalysis;
import com.example.lunitexam.service.GridAnalysisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "3. Slide Grid 관련 API Controller", description = "Slide Grid Decision 관련 API Controller")
@RequestMapping("/api/grid")
public class GridAnalysisController {

    private final GridAnalysisService gridAnalysisService;

    @GetMapping("/all")
    @Operation(description = "분석된 Grid 데이터를 반환 합니다. pageable의 default 값들은 page:0, size:20, sort: idx 필드로 됩니다. ")
    public ResponseEntity<Page<GridAnalysis>> findAll(Pageable pageable) {
        return new ResponseEntity<> (  gridAnalysisService.findAll(pageable), HttpStatus.OK);
    }
    @GetMapping("/{userId}")
    @Operation(description = "분석을 한 사용자 userId로 분석된 Grid 데이터를 반환 합니다. pageable의 default 값들은 page:0, size:20, sort: idx 필드로 됩니다. ")
    public ResponseEntity<Page<GridAnalysis>> findByUserId(@PathVariable String userId, Pageable pageable) {
        return new ResponseEntity<> ( gridAnalysisService.findByUserId(userId, pageable), HttpStatus.OK);
    }
    @GetMapping("/{analysisDecisionIdx}")
    @Operation(description = "Analysis Decision key로 하위로 분석된 Grid 데이터를 반환 합니다. pageable의 default 값들은 page:0, size:20, sort: idx 필드로 됩니다. ")
    public ResponseEntity<Page<GridAnalysis>> findByAnalysisDecisionIdx(@PathVariable Long analysisDecisionIdx, Pageable pageable) {
        return new ResponseEntity<> ( gridAnalysisService.findByAnalysisDecisionIdx(analysisDecisionIdx, pageable), HttpStatus.OK);
    }
}
