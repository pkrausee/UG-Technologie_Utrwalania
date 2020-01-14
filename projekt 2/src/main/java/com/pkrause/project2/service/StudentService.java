package com.pkrause.project2.service;

import com.pkrause.project2.domain.StudentEntity;
import com.pkrause.project2.repository.StudentRepository;
import com.pkrause.project2.result.MultipleResult;
import com.pkrause.project2.result.SingleResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class StudentService {
    private StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public SingleResult<StudentEntity> create(StudentEntity student) {
        if (!student.isValid().isSuccess()) {
            return new SingleResult<>(student, false, "Invalid student.");
        }

        student.setId(null);
        this.repository.save(student);

        return new SingleResult<>(student, true, "Success.");
    }

    @Transactional(rollbackFor = Exception.class)
    public MultipleResult<StudentEntity> create(List<StudentEntity> students) throws Exception {
        for (StudentEntity student : students) {
            if (!student.isValid().isSuccess()) {
                throw new Exception();
            }

            this.repository.save(student);
        }

        return new MultipleResult<>(students, true, "Success.");
    }

    public MultipleResult<StudentEntity> read() {
        List<StudentEntity> findAllResult = this.repository.findAll();

        if (findAllResult.isEmpty()) {
            return new MultipleResult<>(new ArrayList<>(), false, "No students found.");
        }

        return new MultipleResult<>(findAllResult, true, "Success.");
    }

    public SingleResult<StudentEntity> read(UUID id) {
        Optional<StudentEntity> findResult = this.repository.findById(id);

        if (findResult.orElse(null) == null) {
            return new SingleResult<>(null, false, "No student found with given id.");
        }

        return new SingleResult<>(findResult.get(), true, "Success.");
    }

    public SingleResult<StudentEntity> read(String name, String surname, String secondName) {
        Optional<StudentEntity> findResult = this.repository.findByNameAndSecondNameAndSurname(name, surname, secondName);

        if (findResult.orElse(null) == null) {
            return new SingleResult<StudentEntity>(null, false, "No student found with given values.");
        }

        return new SingleResult<>(findResult.get(), true, "Success.");
    }

    public MultipleResult<StudentEntity> readBornAfter(Date date) {
        List<StudentEntity> findResult = this.repository.bornAfter(date);

        if (findResult.isEmpty()) {
            return new MultipleResult<>(null, false, "No students born after given date found.");
        }

        return new MultipleResult<>(findResult, true, "Success.");
    }

    public MultipleResult<StudentEntity> readBornBefore(Date date) {
        List<StudentEntity> findResult = this.repository.bornBefore(date);

        if (findResult.isEmpty()) {
            return new MultipleResult<>(null, false, "No students born before given date found.");
        }

        return new MultipleResult<>(findResult, true, "Success.");
    }

    public SingleResult<StudentEntity> update(UUID id, StudentEntity student) {
        Optional<StudentEntity> findResult = this.repository.findById(id);
        if (findResult.orElse(null) == null) {
            return new SingleResult<>(null, false, "No student found with given id.");
        }

        if (!student.isValid().isSuccess()) {
            return new SingleResult<>(student, false, "Invalid student.");
        }

        student.setId(id);
        this.repository.save(student);

        return new SingleResult<>(student, true, "Success.");
    }

    public SingleResult<StudentEntity> delete(UUID id) {
        Optional<StudentEntity> findResult = this.repository.findById(id);

        if (findResult.orElse(null) == null) {
            return new SingleResult<>(null, false, "No student found with given id.");
        }

        this.repository.deleteById(id);

        return new SingleResult<>(findResult.get(), true, "Success.");
    }
}
