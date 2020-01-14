package com.pkrause.project2.service;

import com.pkrause.project2.domain.GradeEntity;
import com.pkrause.project2.domain.StudentEntity;
import com.pkrause.project2.domain.SubjectEntity;
import com.pkrause.project2.fixture.EntityFixture;
import com.pkrause.project2.repository.GradeRepository;
import com.pkrause.project2.repository.StudentRepository;
import com.pkrause.project2.repository.SubjectRepository;
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
public class GradeServiceTransactionTest {
    private EntityFixture entityFixture = new EntityFixture();

    private GradeService gradeService;
    private GradeRepository gradeRepository;
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;

    @Autowired
    public GradeServiceTransactionTest(GradeService gradeService, GradeRepository gradeRepository, StudentRepository studentRepository, SubjectRepository subjectRepository) {
        this.gradeService = gradeService;
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
    }

    @AfterEach
    public void afterTest() {
        this.gradeRepository.deleteAll();
        this.studentRepository.deleteAll();
        this.subjectRepository.deleteAll();
    }

    @Test
    public void GivenCreate_WhenGivenInvalidEntities_ReturnsFailedResult() {
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        studentRepository.save(studentEntity);
        subjectRepository.save(subjectEntity);

        GradeEntity validGradeEntity = this.entityFixture.validGradeEntity(studentEntity, subjectEntity);
        GradeEntity invalidGradeEntity = this.entityFixture.invalidGradeEntity;

        List<GradeEntity> grades = Arrays.asList(validGradeEntity, invalidGradeEntity);

        try {
            gradeService.create(grades);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MultipleResult<GradeEntity> readResult = this.gradeService.read();

        assertFalse(readResult.isSuccess());
        assertEquals(0, readResult.getResult().size());
    }

    @Test
    public void GivenCreate_WhenGivenValidEntity_ReturnsFailedResult() {
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        studentRepository.save(studentEntity);
        subjectRepository.save(subjectEntity);

        GradeEntity validGradeEntity1 = this.entityFixture.validGradeEntity(studentEntity, subjectEntity);
        GradeEntity validGradeEntity2 = this.entityFixture.validGradeEntity(studentEntity, subjectEntity);

        List<GradeEntity> grades = Arrays.asList(validGradeEntity1, validGradeEntity2);

        try {
            gradeService.create(grades);
        } catch (Exception e) {
            e.printStackTrace();
        }

        MultipleResult<GradeEntity> readResult = this.gradeService.read();

        assertTrue(readResult.isSuccess());
        assertEquals(2, readResult.getResult().size());
    }
}
