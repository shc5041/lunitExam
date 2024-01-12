package com.example.lunitexam.controller;

import com.example.lunitexam.model.dao.AnalysisDecision;
import com.example.lunitexam.model.dao.SlideInfo;
import com.example.lunitexam.model.dto.AnalysisDecisionDto;
import com.example.lunitexam.service.AnalysisDecisionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@Slf4j
@RequiredArgsConstructor
@Tag(name = "2. Slide Analysis Decision 관련 API Controller", description = "Slide Analysis Decision 관련 API Controller")
@RequestMapping("/api/analysis")
public class AnalysisDecisionController {
    private final AnalysisDecisionService analysisDecisionService;

    @GetMapping("/all")
    @Operation(description = "분석된 파일의 decision과 score를 모두 반환 합니다. pageable의 default 값들은 page:0, size:20, sort: idx 필드로 됩니다. ")
    public ResponseEntity<Page<AnalysisDecision>> findAll(@ParameterObject @PageableDefault(sort = {"idx"}, value = 20) Pageable pageable) {
        return new ResponseEntity<> ( analysisDecisionService.findAll(pageable), HttpStatus.OK);
    }
    @GetMapping("/userId/{userId}")
    @Operation(description = "분석된 파일의 decision과 score를 userId 별로 반환 합니다. pageable의 default 값들은 page:0, size:20, sort: idx 필드로 됩니다. ")
    public ResponseEntity<?> findByUserId(@PathVariable  String userId,

                                                               @Schema(defaultValue = "2024-01-01 00:00:00")
                                                               @RequestParam(value = "startDateTime", required = false)
                                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDateTime,

                                                               @Schema(defaultValue = "2024-12-31 23:59:59")
                                                               @RequestParam(value = "endDateTime", required = false)
                                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDateTime,
                                                               @ParameterObject @PageableDefault(sort = {"idx"}, value = 20)
                                                               Pageable pageable) {
        if (startDateTime != null && endDateTime != null) {
            return new ResponseEntity<>(analysisDecisionService.findByUserIdAndCreatedDateBetween(userId, startDateTime, endDateTime, pageable), HttpStatus.OK);
        } else if (startDateTime == null && endDateTime != null) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        } else if (startDateTime != null && endDateTime == null) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<> (analysisDecisionService.findByUserId(userId, pageable), HttpStatus.OK);
    }
    @GetMapping("/fileName/{fileName}")
    @Operation(description = "분석된 파일의 decision과 score를 fileName 별로 반환 합니다. pageable의 default 값들은 page:0, size:20, sort: idx 필드로 됩니다. ")
    public ResponseEntity<Page<AnalysisDecision>> findByOriginFileName(@PathVariable  String fileName,
                                                                       @ParameterObject @PageableDefault(sort = {"idx"}, value = 20)
                                                                       Pageable pageable) {
        return new ResponseEntity<>(analysisDecisionService.findByOriginFileName(fileName, pageable), HttpStatus.OK);
    }

    @PostMapping("/decision/slideInfoIdx/{idx}/userId/{userId}")
    @Operation(description = "Upload된 파일을 분석 요청을 시작 합니다. 파일의 분석 결과는 json형태로 제공되며, grid분석 데이터는 gridAnalyses 객체의 배열 (20~50개 사이) 형태로 전달 됩니다. " +
            "또, grid 데이터의 개수가 20개 미만일 경우 오류가 발생되고 오류 내용은 'invalid grid data length' 입니다. ")
    public ResponseEntity<AnalysisDecisionDto> requestGridAnalysis(@PathVariable Long idx, @PathVariable String userId) {
        return new ResponseEntity<>(analysisDecisionService.requestGridAnalysis(userId, idx), HttpStatus.OK);
    }

}
