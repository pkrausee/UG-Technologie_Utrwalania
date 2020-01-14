package com.pkrause.project2.service;

import com.pkrause.project2.domain.GradeEntity;
import com.pkrause.project2.domain.StudentEntity;
import com.pkrause.project2.domain.SubjectEntity;
import com.pkrause.project2.repository.GradeRepository;
import com.pkrause.project2.result.MultipleResult;
import com.pkrause.project2.result.SingleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GradeService {
    private GradeRepository repository;

    @Autowired
    public GradeService(GradeRepository repository) {
        this.repository = repository;
    }

    public SingleResult<GradeEntity> create(GradeEntity grade) {
        if (!grade.isValid().isSuccess()) {
            return new SingleResult<>(grade, false, "Invalid grade.");
        }

        grade.setId(null);
        this.repository.save(grade);

        return new SingleResult<>(grade, true, "Success.");
    }

    @Transactional(rollbackFor = Exception.class)
    public MultipleResult<GradeEntity> create(List<GradeEntity> grades) throws Exception {
        for (GradeEntity grade : grades) {
            if (!grade.isValid().isSuccess()) {
                throw new Exception();
            }

            this.repository.save(grade);
        }

        return new MultipleResult<>(grades, true, "Success.");
    }

    public MultipleResult<GradeEntity> read() {
        List<GradeEntity> findAllResult = this.repository.findAll();

        if (findAllResult.isEmpty()) {
            return new MultipleResult<>(new ArrayList<>(), false, "No grades found.");
        }

        return new MultipleResult<>(findAllResult, true, "Success.");
    }

    public SingleResult<GradeEntity> read(UUID id) {
        Optional<GradeEntity> findResult = this.repository.findById(id);

        if (findResult.orElse(null) == null) {
            return new SingleResult<>(null, false, "No grade found with given id.");
        }

        return new SingleResult<>(findResult.get(), true, "Success.");
    }

    public MultipleResult<GradeEntity> read(StudentEntity studentEntity, SubjectEntity subjectEntity, Timestamp created) {
        List<GradeEntity> findResult = this.repository.findAllByStudentAndSubjectAndCreatedAfter(studentEntity, subjectEntity, created);

        if (findResult.isEmpty()) {
            return new MultipleResult<>(null, false, "No grade found with given values.");
        }

        return new MultipleResult<>(findResult, true, "Success.");
    }

    public SingleResult<Double> readAvg(StudentEntity studentEntity, SubjectEntity subjectEntity) {
        Double findResult = this.repository.averageGrade(studentEntity, subjectEntity);

        if (findResult == null) {
            return new SingleResult<>(null, false, "No grade found with given values.");
        }

        return new SingleResult<>(findResult, true, "Success.");
    }

    public SingleResult<GradeEntity> update(UUID id, GradeEntity gradeEntity) {
        Optional<GradeEntity> findResult = this.repository.findById(id);
        if (findResult.orElse(null) == null) {
            return new SingleResult<>(null, false, "No grade found with given id.");
        }

        if (!gradeEntity.isValid().isSuccess()) {
            return new SingleResult<>(gradeEntity, false, "Invalid grade.");
        }

        gradeEntity.setId(id);
        this.repository.save(gradeEntity);

        return new SingleResult<>(gradeEntity, true, "Success.");
    }

    public SingleResult<GradeEntity> delete(UUID id) {
        Optional<GradeEntity> findResult = this.repository.findById(id);

        if (findResult.orElse(null) == null) {
            return new SingleResult<>(null, false, "No grade found with given id.");
        }

        this.repository.deleteById(id);

        return new SingleResult<>(findResult.get(), true, "Success.");
    }
}
