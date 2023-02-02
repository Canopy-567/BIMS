package com.example.bims;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Repository
public interface ApplicantsRepository extends JpaRepository<Applicants, Long> {

    @Query("from Applicants a where a.priority_date between ?1 and ?2")
    Set<Applicants> findByPriority_DateBetween(LocalDateTime from, LocalDateTime to);
}
