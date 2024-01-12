package com.example.lunitexam.model.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString(exclude = "analysisDecision")
@Table(
        indexes = { @Index(name = "idx_user_id", columnList = "userId")}
)
@EntityListeners(AuditingEntityListener.class)
public class GridAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    // @ManyToOne의 fetch 기본전략은 EAGER
    @JsonIgnore
    @ManyToOne(optional = false, fetch = FetchType.LAZY) //inner join ,
    @JoinColumn(name = "analysisDecisionId")
    private AnalysisDecision analysisDecision;

    private String originFileName;
    private String userId;

    /**
     * Intratumoral TIL density min, avg, max : Float
     * Stromal TIL density min, avg, max : Float
     */
    private Float intratumoralMin;
    private Float intratumoralAvg;
    private Float intratumoralMax;

    private Float stromalMin;
    private Float stromalAvg;
    private Float stromalMax;

    @CreatedDate
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;


    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedDate;

}
