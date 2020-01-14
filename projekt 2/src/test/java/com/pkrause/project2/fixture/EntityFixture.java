package com.pkrause.project2.fixture;

import com.pkrause.project2.domain.GradeEntity;
import com.pkrause.project2.domain.StudentEntity;
import com.pkrause.project2.domain.SubjectEntity;

import java.sql.Date;
import java.sql.Timestamp;

public class EntityFixture {
    public static final String mockName = "Mock";
    public static final Date mockDate = new Date(System.currentTimeMillis());
    public static final Timestamp mockTimestamp = new Timestamp(System.currentTimeMillis());

    public StudentEntity invalidStudentEntity = new StudentEntity();
    public SubjectEntity invalidSubjectEntity = new SubjectEntity();
    public GradeEntity invalidGradeEntity = new GradeEntity();

    public StudentEntity validStudentEntity(Date birthday) {
        StudentEntity entity = new StudentEntity();

        entity.setName(mockName);
        entity.setSurname(mockName);
        entity.setBirthday(birthday == null ? mockDate : birthday);

        return entity;
    }

    public SubjectEntity validSubjectEntity(Timestamp created) {
        SubjectEntity entity = new SubjectEntity();

        entity.setName(mockName);
        entity.setRange(mockName);
        entity.setCreated(created == null ? mockTimestamp : created);

        return entity;
    }

    public GradeEntity validGradeEntity(StudentEntity studentEntity, SubjectEntity subjectEntity) {
        GradeEntity gradeEntity = new GradeEntity();

        gradeEntity.setGrade(3.0);
        gradeEntity.setWage(1.0);
        gradeEntity.setDescription(mockName);
        gradeEntity.setCreated(mockTimestamp);
        gradeEntity.setStudent(studentEntity);
        gradeEntity.setSubject(subjectEntity);

        return gradeEntity;
    }
}
