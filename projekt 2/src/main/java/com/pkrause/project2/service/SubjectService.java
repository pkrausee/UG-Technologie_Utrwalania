package com.pkrause.project2.service;

import com.pkrause.project2.domain.SubjectEntity;
import com.pkrause.project2.repository.SubjectRepository;
import com.pkrause.project2.result.MultipleResult;
import com.pkrause.project2.result.SingleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    private SubjectRepository repository;

    @Autowired
    public SubjectService(SubjectRepository repository) {
        this.repository = repository;
    }

    public SingleResult<SubjectEntity> create(SubjectEntity subject) {
        if (!subject.isValid().isSuccess()) {
            return new SingleResult<>(subject, false, "Invalid subject.");
        }

        subject.setId(null);
        this.repository.save(subject);

        return new SingleResult<>(subject, true, "Success.");
    }

    @Transactional(rollbackFor = Exception.class)
    public MultipleResult<SubjectEntity> create(List<SubjectEntity> subjects) throws Exception {
        for (SubjectEntity student : subjects) {
            if (!student.isValid().isSuccess()) {
                throw new Exception();
            }

            this.repository.save(student);
        }

        return new MultipleResult<>(subjects, true, "Success.");
    }

    public MultipleResult<SubjectEntity> read() {
        List<SubjectEntity> findAllResult = this.repository.findAll();

        if (findAllResult.isEmpty()) {
            return new MultipleResult<>(new ArrayList<>(), false, "No subjects found.");
        }

        return new MultipleResult<>(findAllResult, true, "Success.");
    }

    public SingleResult<SubjectEntity> read(Long id) {
        Optional<SubjectEntity> findResult = this.repository.findById(id);

        if (findResult.orElse(null) == null) {
            return new SingleResult<>(null, false, "No subject found with given id.");
        }

        return new SingleResult<>(findResult.get(), true, "Success.");
    }

    public MultipleResult<SubjectEntity> read(String name, String range, boolean removed) {
        List<SubjectEntity> findResult = this.repository.findAllByNameAndRangeAndRemovedEquals(name, range, removed);

        if (findResult.isEmpty()) {
            return new MultipleResult<>(null, false, "No subject found with given values.");
        }

        return new MultipleResult<>(findResult, true, "Success.");
    }

    public MultipleResult<SubjectEntity> read(String range, Timestamp created, boolean removed) {
        List<SubjectEntity> findResult = this.repository.findAllByRangeAndCreatedAfterAndRemovedEquals(range, created, removed);

        if (findResult.isEmpty()) {
            return new MultipleResult<>(null, false, "No subject found with given values.");
        }

        return new MultipleResult<>(findResult, true, "Success.");
    }

    public MultipleResult<SubjectEntity> readRemoved() {
        List<SubjectEntity> findAllResult = this.repository.removedSubjects();

        if (findAllResult.isEmpty()) {
            return new MultipleResult<>(null, false, "No removed subjects found.");
        }

        return new MultipleResult<>(findAllResult, true, "Success.");
    }

    public SingleResult<SubjectEntity> update(Long id, SubjectEntity subject) {
        Optional<SubjectEntity> findResult = this.repository.findById(id);
        if (findResult.orElse(null) == null) {
            return new SingleResult<>(null, false, "No subject found with given id.");
        }

        if (!subject.isValid().isSuccess()) {
            return new SingleResult<>(subject, false, "Invalid subject.");
        }

        subject.setId(id);
        this.repository.save(subject);

        return new SingleResult<>(subject, true, "Success.");
    }

    public SingleResult<SubjectEntity> delete(Long id) {
        Optional<SubjectEntity> findResult = this.repository.findById(id);

        if (findResult.orElse(null) == null) {
            return new SingleResult<>(null, false, "No subject found with given id.");
        }

        this.repository.deleteById(id);

        return new SingleResult<>(findResult.get(), true, "Success.");
    }
}
