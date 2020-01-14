package com.pkrause.project2.repository;

import com.pkrause.project2.domain.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, UUID> {

    @Modifying
    void deleteAll();

    @Query(value = "SELECT COUNT(s) FROM student s")
    Long countStudents();

    @Query(value = "SELECT s FROM student s WHERE s.birthday > :date")
    List<StudentEntity> bornAfter(@Param("date") Date date);

    @Query(value = "SELECT s FROM student s WHERE s.birthday < :date")
    List<StudentEntity> bornBefore(@Param("date") Date date);

    Optional<StudentEntity> findByNameAndSecondNameAndSurname(String name, String secondName, String surname);

}
