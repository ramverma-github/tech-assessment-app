package com.tech.assessment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "test_results")
@NoArgsConstructor
@AllArgsConstructor
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "score")
    private int score;

    @Column(name = "correct")
    private int correct;

    @Column(name = "wrong")
    private int wrong;

    @Column(name = "submitted_at")
    private LocalDateTime submittedAt;

}
