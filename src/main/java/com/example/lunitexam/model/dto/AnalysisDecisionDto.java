package com.example.lunitexam.model.dto;

import com.example.lunitexam.model.dao.GridAnalysis;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class AnalysisDecisionDto {
    private Long idx;

    private String originFileName;

    private String path;
    private String userId;

    private Boolean decision;

    private Float score;

    private List<GridAnalysis> gridAnalyses;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;


}
