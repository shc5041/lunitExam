package com.example.lunitexam.model.dao;

import com.example.lunitexam.model.dto.AnalysisDecisionDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = "gridAnalyses")
@Table(
        indexes = { @Index(name = "idx_user_id", columnList = "userId")}
)
@EntityListeners(AuditingEntityListener.class)
public class AnalysisDecision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String originFileName;

    private String path;
    private String userId;

    private Boolean decision;

    private Float score;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "analysisDecision")
    private List<GridAnalysis> gridAnalyses;

    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;


    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;

    public AnalysisDecisionDto toEntity(){
        return AnalysisDecisionDto.builder()
                .idx(idx)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .gridAnalyses(gridAnalyses)
                .originFileName(originFileName)
                .decision(decision)
                .score(score)
                .path(path)
                .userId(userId)
                .build();

    }

}
