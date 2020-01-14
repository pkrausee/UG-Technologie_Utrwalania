package com.pkrause.project2.repository;

import com.pkrause.project2.domain.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {

    @Modifying
    void deleteAll();

    @Query(value = "SELECT COUNT(s) FROM subject s")
    Long countSubjects();

    @Query(value = "SELECT s FROM subject s WHERE s.removed = true")
    List<SubjectEntity> removedSubjects();

    List<SubjectEntity> findAllByNameAndRangeAndRemovedEquals(String name, String range, boolean removed);

    List<SubjectEntity> findAllByRangeAndCreatedAfterAndRemovedEquals(String range, Timestamp created, boolean removed);
}
