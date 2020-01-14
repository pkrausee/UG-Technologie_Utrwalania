package com.pkrause.project2.repository;

import com.pkrause.project2.domain.SubjectEntity;
import com.pkrause.project2.fixture.EntityFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SubjectRepositoryTest {
    private EntityFixture entityFixture = new EntityFixture();
    private SubjectRepository repository;

    @Autowired
    public SubjectRepositoryTest(SubjectRepository repository) {
        this.repository = repository;
    }

    @AfterEach
    public void afterTest() {
        this.repository.deleteAll();
    }

    @Test
    public void GivenCount_WhenThereIsOneEntityInTable_Returns1() {
        this.repository.deleteAll();

        SubjectEntity studentEntity = this.entityFixture.validSubjectEntity(null);
        this.repository.save(studentEntity);

        assertEquals(1, this.repository.countSubjects());
    }

    @Test
    public void GivenCount_WhenTableIsEmpty_Returns0() {
        this.repository.deleteAll();

        assertEquals(0, this.repository.countSubjects());
    }

    @Test
    public void GivenRemovedEntities_WhenThereIsOneMatching_ReturnsOneEntity() {
        this.repository.deleteAll();

        SubjectEntity matchingStudentEntity = this.entityFixture.validSubjectEntity(null);
        matchingStudentEntity.setRemoved(true);

        SubjectEntity notMatchingStudentEntity = this.entityFixture.validSubjectEntity(null);

        this.repository.save(matchingStudentEntity);
        this.repository.save(notMatchingStudentEntity);

        List<SubjectEntity> result = this.repository.removedSubjects();

        assertEquals(1, result.size());
        assertEquals(matchingStudentEntity, result.get(0));
    }

    @Test
    public void GivenRemovedEntities_WhenThereIsNoMatchingEntities_ReturnsEmptyList() {
        this.repository.deleteAll();

        SubjectEntity notMatchingStudentEntity = this.entityFixture.validSubjectEntity(null);

        this.repository.save(notMatchingStudentEntity);

        List<SubjectEntity> result = this.repository.removedSubjects();

        assertEquals(0, result.size());
    }

    @Test
    public void GivenFindAllByNameAndRangeAndRemoved_WhenThereIsOneMatching_ReturnsOneEntity() {
        this.repository.deleteAll();

        String mockName = EntityFixture.mockName;

        SubjectEntity matchingStudentEntity = this.entityFixture.validSubjectEntity(null);
        matchingStudentEntity.setRemoved(true);

        SubjectEntity notMatchingStudentEntity = this.entityFixture.validSubjectEntity(null);

        this.repository.save(matchingStudentEntity);
        this.repository.save(notMatchingStudentEntity);

        List<SubjectEntity> result = this.repository.findAllByNameAndRangeAndRemovedEquals(mockName, mockName, true);

        assertEquals(1, result.size());
        assertEquals(matchingStudentEntity, result.get(0));
    }

    @Test
    public void GivenFindAllByNameAndRangeAndRemoved_WhenThereIsNoMatchingEntities_ReturnsEmptyList() {
        this.repository.deleteAll();

        String mockName = EntityFixture.mockName;

        SubjectEntity notMatchingStudentEntity = this.entityFixture.validSubjectEntity(null);

        this.repository.save(notMatchingStudentEntity);

        List<SubjectEntity> result = this.repository.findAllByNameAndRangeAndRemovedEquals(mockName, mockName, true);

        assertEquals(0, result.size());
    }

    @Test
    public void GivenFindAllByRangeAndCreatedAfterAndRemovedEquals_WhenThereIsOneMatching_ReturnsOneEntity() {
        this.repository.deleteAll();

        String mockName = EntityFixture.mockName;

        SubjectEntity matchingStudentEntity = this.entityFixture.validSubjectEntity(Timestamp.valueOf("2019-02-02 00:00:00"));
        matchingStudentEntity.setRemoved(true);

        SubjectEntity notMatchingStudentEntity = this.entityFixture.validSubjectEntity(Timestamp.valueOf("2019-01-01 00:00:00"));

        this.repository.save(matchingStudentEntity);
        this.repository.save(notMatchingStudentEntity);

        List<SubjectEntity> result = this.repository.findAllByRangeAndCreatedAfterAndRemovedEquals(mockName, Timestamp.valueOf("2019-01-02 00:00:00"), true);

        assertEquals(1, result.size());
        assertEquals(matchingStudentEntity, result.get(0));
    }

    @Test
    public void GivenFindAllByRangeAndCreatedAfterAndRemovedEquals_WhenThereIsNoMatchingEntities_ReturnsEmptyList() {
        this.repository.deleteAll();

        String mockName = EntityFixture.mockName;

        SubjectEntity notMatchingStudentEntity = this.entityFixture.validSubjectEntity(Timestamp.valueOf("2019-01-01 00:00:00"));

        this.repository.save(notMatchingStudentEntity);

        List<SubjectEntity> result = this.repository.findAllByRangeAndCreatedAfterAndRemovedEquals(mockName, Timestamp.valueOf("2019-01-02 00:00:00"), true);

        assertEquals(0, result.size());
    }
}
