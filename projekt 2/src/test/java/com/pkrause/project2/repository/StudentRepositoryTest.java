package com.pkrause.project2.repository;

import com.pkrause.project2.domain.StudentEntity;
import com.pkrause.project2.fixture.EntityFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentRepositoryTest {
    private EntityFixture entityFixture = new EntityFixture();
    private StudentRepository repository;

    @Autowired
    public StudentRepositoryTest(StudentRepository repository) {
        this.repository = repository;
    }

    @AfterEach
    public void afterTest() {
        this.repository.deleteAll();
    }

    @Test
    public void GivenCount_WhenThereIsOneEntityInTable_Returns1() {
        this.repository.deleteAll();

        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);
        this.repository.save(studentEntity);

        assertEquals(1, this.repository.countStudents());
    }

    @Test
    public void GivenCount_WhenTableIsEmpty_Returns0() {
        this.repository.deleteAll();

        assertEquals(0, this.repository.countStudents());
    }

    @Test
    public void GivenBornAfter_WhenThereIsOneMatching_ReturnsOneEntity() {
        this.repository.deleteAll();

        StudentEntity matchingStudentEntity = this.entityFixture.validStudentEntity(Date.valueOf("2019-02-02"));
        StudentEntity notMatchingStudentEntity = this.entityFixture.validStudentEntity(Date.valueOf("2019-01-01"));

        this.repository.save(matchingStudentEntity);
        this.repository.save(notMatchingStudentEntity);

        List<StudentEntity> result = this.repository.bornAfter(Date.valueOf("2019-02-01"));

        assertEquals(1, result.size());
        assertEquals(matchingStudentEntity, result.get(0));
    }

    @Test
    public void GivenBornAfter_WhenThereIsNoMatchingEntities_ReturnsEmptyList() {
        this.repository.deleteAll();

        StudentEntity notMatchingStudentEntity = this.entityFixture.validStudentEntity(Date.valueOf("2019-01-01"));

        this.repository.save(notMatchingStudentEntity);

        List<StudentEntity> result = this.repository.bornAfter(Date.valueOf("2019-02-01"));

        assertEquals(0, result.size());
    }

    @Test
    public void GivenBornBefore_WhenThereIsOneMatching_ReturnsOneEntity() {
        this.repository.deleteAll();

        StudentEntity matchingStudentEntity = this.entityFixture.validStudentEntity(Date.valueOf("2019-01-01"));
        StudentEntity notMatchingStudentEntity = this.entityFixture.validStudentEntity(Date.valueOf("2019-02-02"));

        this.repository.save(matchingStudentEntity);
        this.repository.save(notMatchingStudentEntity);

        List<StudentEntity> result = this.repository.bornBefore(Date.valueOf("2019-02-01"));

        assertEquals(1, result.size());
        assertEquals(matchingStudentEntity, result.get(0));
    }

    @Test
    public void GivenBornBefore_WhenThereIsNoMatchingEntities_ReturnsEmptyList() {
        this.repository.deleteAll();

        StudentEntity notMatchingStudentEntity = this.entityFixture.validStudentEntity(Date.valueOf("2019-02-02"));

        this.repository.save(notMatchingStudentEntity);

        List<StudentEntity> result = this.repository.bornBefore(Date.valueOf("2019-02-01"));

        assertEquals(0, result.size());
    }

    @Test
    public void GivenFindByNameAndSecondNameAndSurname_WhenThereIsOneMatching_ReturnsOneEntity() {
        this.repository.deleteAll();

        String mockName = EntityFixture.mockName;

        StudentEntity matchingStudentEntity = this.entityFixture.validStudentEntity(null);
        StudentEntity notMatchingStudentEntity = this.entityFixture.validStudentEntity(null);
        notMatchingStudentEntity.setName("Mock Name");

        this.repository.save(matchingStudentEntity);
        this.repository.save(notMatchingStudentEntity);

        Optional<StudentEntity> result = this.repository.findByNameAndSecondNameAndSurname(mockName, null, mockName);

        assertTrue(result.isPresent());
        assertEquals(matchingStudentEntity, result.get());
    }

    @Test
    public void GivenFindByNameAndSecondNameAndSurname_WhenThereIsNoMatchingEntities_ReturnsEmptyList() {
        this.repository.deleteAll();

        String mockName = EntityFixture.mockName;

        StudentEntity notMatchingStudentEntity = this.entityFixture.validStudentEntity(null);
        notMatchingStudentEntity.setName("Mock Name");

        this.repository.save(notMatchingStudentEntity);

        Optional<StudentEntity> result = this.repository.findByNameAndSecondNameAndSurname(mockName, null, mockName);

        assertFalse(result.isPresent());
    }
}
