package com.tech.assessment.repository;

import com.tech.assessment.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TestRepository extends JpaRepository<Test, UUID> {
    @Query("SELECT DISTINCT t FROM Test t " +
            "LEFT JOIN FETCH t.questions " +
            "LEFT JOIN FETCH t.results")
    List<Test> findAllWithChildren();

    @Query("""
    SELECT t FROM Test t
    LEFT JOIN FETCH t.questions
    LEFT JOIN FETCH t.results
    WHERE t.id = :id
    """)
    Optional<Test> findByIdWithChildren(UUID id);


}