package com.pkrause.project2.domain;

import com.pkrause.project2.fixture.EntityFixture;
import com.pkrause.project2.repository.GradeRepository;
import com.pkrause.project2.repository.StudentRepository;
import com.pkrause.project2.repository.SubjectRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class StudentEntityTest {
    private EntityFixture entityFixture = new EntityFixture();

    private GradeRepository gradeRepository;
    private StudentRepository studentRepository;
    private SubjectRepository subjectRepository;

    @Autowired
    public StudentEntityTest(GradeRepository gradeRepository, StudentRepository studentRepository, SubjectRepository subjectRepository) {
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
    public void GivenGetGrades_WhenThereAreTwoGrades_ReturnsListWithTwoGrades() {
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        GradeEntity gradeEntity1 = this.entityFixture.validGradeEntity(studentEntity, subjectEntity);
        GradeEntity gradeEntity2 = this.entityFixture.validGradeEntity(studentEntity, subjectEntity);

        studentRepository.save(studentEntity);
        subjectRepository.save(subjectEntity);

        gradeRepository.save(gradeEntity1);
        gradeRepository.save(gradeEntity2);

        studentEntity = this.studentRepository.findById(studentEntity.getId()).orElse(null);

        assertEquals(2, studentEntity.getGrades().size());
    }
}
