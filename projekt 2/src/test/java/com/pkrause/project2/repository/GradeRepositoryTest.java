package com.pkrause.project2.repository;

import com.pkrause.project2.domain.GradeEntity;
import com.pkrause.project2.domain.StudentEntity;
import com.pkrause.project2.domain.SubjectEntity;
import com.pkrause.project2.fixture.EntityFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GradeRepositoryTest {
    private EntityFixture entityFixture = new EntityFixture();

    private GradeRepository gradeRepository;
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;

    @Autowired
    public GradeRepositoryTest(GradeRepository gradeRepository, StudentRepository studentRepository, SubjectRepository subjectRepository) {
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
    public void GivenCount_WhenThereIsOneEntityInTable_Returns1() {
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        studentRepository.save(studentEntity);
        subjectRepository.save(subjectEntity);

        GradeEntity gradeEntity = this.entityFixture.validGradeEntity(studentEntity, subjectEntity);

        gradeRepository.save(gradeEntity);

        assertEquals(1, this.gradeRepository.countGrades());
    }

    @Test
    public void GivenCount_WhenTableIsEmpty_Returns0() {
        assertEquals(0, this.gradeRepository.countGrades());
    }

    @Test
    public void GivenAverageGrade_WhenThereIsOneEntityInTable_ReturnsAverageGrade() {
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        studentRepository.save(studentEntity);
        subjectRepository.save(subjectEntity);

        GradeEntity gradeEntity1 = this.entityFixture.validGradeEntity(studentEntity, subjectEntity);
        gradeEntity1.setGrade(3.0);

        GradeEntity gradeEntity2 = this.entityFixture.validGradeEntity(studentEntity, subjectEntity);
        gradeEntity2.setGrade(4.0);

        gradeRepository.save(gradeEntity1);
        gradeRepository.save(gradeEntity2);

        assertEquals(3.5, this.gradeRepository.averageGrade(studentEntity, subjectEntity));
    }

    @Test
    public void GivenAverageGrade_WhenTableIsEmpty_Returns0() {
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        studentRepository.save(studentEntity);
        subjectRepository.save(subjectEntity);

        assertNull(this.gradeRepository.averageGrade(studentEntity, subjectEntity));
    }

    @Test
    public void GivenFindAllByStudentAndSubjectAndCreatedAfter_WhenThereIsOneMatching_ReturnsOneEntity() {
        Timestamp createDate = Timestamp.valueOf("2000-01-02 00:00:00");
        Timestamp searchDate = Timestamp.valueOf("2000-01-01 00:00:00");

        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        studentRepository.save(studentEntity);
        subjectRepository.save(subjectEntity);

        GradeEntity gradeEntity = this.entityFixture.validGradeEntity(studentEntity, subjectEntity);
        gradeEntity.setCreated(createDate);
        gradeRepository.save(gradeEntity);

        List<GradeEntity> result = this.gradeRepository.findAllByStudentAndSubjectAndCreatedAfter(studentEntity, subjectEntity, searchDate);

        assertEquals(1, result.size());
        assertEquals(gradeEntity, result.get(0));
    }

    @Test
    public void GivenFindAllByStudentAndSubjectAndCreatedAfter_WhenThereIsNoMatchingEntities_ReturnsEmptyList() {
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        studentRepository.save(studentEntity);
        subjectRepository.save(subjectEntity);

        List<GradeEntity> result = this.gradeRepository.findAllByStudentAndSubjectAndCreatedAfter(studentEntity, subjectEntity, EntityFixture.mockTimestamp);

        assertTrue(result.isEmpty());
    }
}
