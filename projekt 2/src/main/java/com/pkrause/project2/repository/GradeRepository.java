package com.pkrause.project2.repository;

import com.pkrause.project2.domain.GradeEntity;
import com.pkrause.project2.domain.StudentEntity;
import com.pkrause.project2.domain.SubjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Repository
public interface GradeRepository extends JpaRepository<GradeEntity, UUID> {

    @Modifying
    void deleteAll();

    @Query(value = "SELECT COUNT(g) FROM grade g")
    Long countGrades();

    @Query(value = "SELECT SUM(g.grade) / COUNT(g) FROM grade g WHERE g.student = :student AND g.subject = :subject")
    Double averageGrade(@Param("student") StudentEntity student, @Param("subject") SubjectEntity subject);

    List<GradeEntity> findAllByStudentAndSubjectAndCreatedAfter(StudentEntity student, SubjectEntity subject, Timestamp created);

}
