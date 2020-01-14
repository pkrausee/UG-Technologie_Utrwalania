package com.pkrause.project2.service;

import com.pkrause.project2.domain.StudentEntity;
import com.pkrause.project2.fixture.EntityFixture;
import com.pkrause.project2.repository.StudentRepository;
import com.pkrause.project2.result.MultipleResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class StudentServiceTransactionTest {
    private EntityFixture entityFixture = new EntityFixture();

    private StudentRepository repository;
    private StudentService service;

    @Autowired
    public StudentServiceTransactionTest(StudentRepository repository, StudentService service) {
        this.repository = repository;
        this.service = service;
    }

    @AfterEach
    public void afterTest() {
        this.repository.deleteAll();
    }

    @Test
    public void GivenCreate_WhenGivenInvalidEntities_ReturnsFailedResult() {
        StudentEntity validStudentEntity = this.entityFixture.validStudentEntity(null);
        StudentEntity invalidStudentEntity = this.entityFixture.invalidStudentEntity;

        List<StudentEntity> students = Arrays.asList(validStudentEntity, invalidStudentEntity);

        try {
            service.create(students);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MultipleResult<StudentEntity> readResult = this.service.read();

        assertFalse(readResult.isSuccess());
        assertEquals(0, readResult.getResult().size());
    }

    @Test
    public void GivenCreate_WhenGivenValidEntity_ReturnsFailedResult() {
        StudentEntity validStudentEntity1 = this.entityFixture.validStudentEntity(null);
        StudentEntity validStudentEntity2 = this.entityFixture.validStudentEntity(null);

        List<StudentEntity> students = Arrays.asList(validStudentEntity1, validStudentEntity2);

        try {
            service.create(students);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MultipleResult<StudentEntity> readResult = this.service.read();

        assertTrue(readResult.isSuccess());
        assertEquals(2, readResult.getResult().size());
    }
}
