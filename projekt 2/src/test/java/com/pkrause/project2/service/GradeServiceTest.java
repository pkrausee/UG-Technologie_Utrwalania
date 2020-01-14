package com.pkrause.project2.service;

import com.pkrause.project2.domain.GradeEntity;
import com.pkrause.project2.domain.StudentEntity;
import com.pkrause.project2.domain.SubjectEntity;
import com.pkrause.project2.fixture.EntityFixture;
import com.pkrause.project2.repository.GradeRepository;
import com.pkrause.project2.result.MultipleResult;
import com.pkrause.project2.result.SingleResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {
    @InjectMocks
    GradeService service;
    @Mock
    GradeRepository repository;

    private EntityFixture entityFixture = new EntityFixture();

    @Test
    public void GivenCreate_WhenGivenInvalidEntity_ReturnsFailedResult() {
        GradeEntity invalidGradeEntity = this.entityFixture.invalidGradeEntity;

        SingleResult<GradeEntity> result = service.create(invalidGradeEntity);

        assertFalse(result.isSuccess());
        assertEquals("Invalid grade.", result.getMessage());
    }

    @Test
    public void GivenCreate_WhenGivenValidEntity_ReturnsSuccessResult() {
        GradeEntity validStudentEntity = this.entityFixture.validGradeEntity(null, null);

        SingleResult<GradeEntity> result = service.create(validStudentEntity);

        assertTrue(result.isSuccess());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenRead_WhenThereAreNoEntities_ReturnsFailedResult() {
        MultipleResult<GradeEntity> result = service.read();

        assertFalse(result.isSuccess());
        assertEquals("No grades found.", result.getMessage());
    }

    @Test
    public void GivenRead_WhenThereIsOneEntity_ReturnsSuccessResultWithOneEntity() {
        when(repository.findAll()).thenReturn(Collections.singletonList(this.entityFixture.validGradeEntity(null, null)));

        MultipleResult<GradeEntity> result = service.read();

        assertTrue(result.isSuccess());
        assertEquals(1, result.getResult().size());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenReadWithId_WhenThereAreNoMatchingEntities_ReturnsFailedResult() {
        SingleResult<GradeEntity> result = service.read(UUID.randomUUID());

        assertFalse(result.isSuccess());
        assertEquals("No grade found with given id.", result.getMessage());
    }

    @Test
    public void GivenReadWithId_WhenThereIsMatchingEntity_ReturnsSuccessResultWithOneEntity() {
        UUID id = UUID.randomUUID();

        GradeEntity gradeEntity = this.entityFixture.validGradeEntity(null, null);
        gradeEntity.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(gradeEntity));

        SingleResult<GradeEntity> result = service.read(id);

        assertTrue(result.isSuccess());
        assertEquals(gradeEntity, result.getResult());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenReadWithStudentSubjectCreated_WhenThereAreNoMatchingEntities_ReturnsFailedResult() {
        MultipleResult<GradeEntity> result = service.read(
                this.entityFixture.validStudentEntity(null),
                this.entityFixture.validSubjectEntity(null),
                EntityFixture.mockTimestamp);

        assertFalse(result.isSuccess());
        assertEquals("No grade found with given values.", result.getMessage());
    }

    @Test
    public void GivenReadWithStudentSubjectCreated_WhenThereIsMatchingEntity_ReturnsSuccessResultWithOneEntity() {
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        GradeEntity gradeEntity = this.entityFixture.validGradeEntity(studentEntity, subjectEntity);

        when(repository.findAllByStudentAndSubjectAndCreatedAfter(studentEntity, subjectEntity, gradeEntity.getCreated()))
                .thenReturn(Collections.singletonList(gradeEntity));

        MultipleResult<GradeEntity> result = service.read(studentEntity, subjectEntity, gradeEntity.getCreated());

        assertTrue(result.isSuccess());
        assertEquals(1, result.getResult().size());
        assertEquals(gradeEntity, result.getResult().get(0));
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenReadAvg_WhenThereAreNoMatchingEntities_ReturnsFailedResult() {
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        when(repository.averageGrade(studentEntity, subjectEntity)).thenReturn(null);

        SingleResult<Double> result = service.readAvg(studentEntity, subjectEntity);

        assertFalse(result.isSuccess());
        assertEquals("No grade found with given values.", result.getMessage());
    }

    @Test
    public void GivenReadAvg_WhenThereIsMatchingEntity_ReturnsSuccessResultWithOneEntity() {
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        when(repository.averageGrade(studentEntity, subjectEntity)).thenReturn(3.0);

        SingleResult<Double> result = service.readAvg(studentEntity, subjectEntity);

        assertTrue(result.isSuccess());
        assertEquals(3.0, result.getResult(), 0.0);
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenUpdate_WhenThereIsNoEntityWithGivenId_ReturnsFailedResult() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        SingleResult<GradeEntity> result = service.update(id, this.entityFixture.invalidGradeEntity);

        assertFalse(result.isSuccess());
        assertEquals("No grade found with given id.", result.getMessage());
    }

    @Test
    public void GivenUpdate_WhenGivenInvalidId_ReturnsFailedResult() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.of(this.entityFixture.validGradeEntity(null, null)));

        SingleResult<GradeEntity> result = service.update(id, this.entityFixture.invalidGradeEntity);

        assertFalse(result.isSuccess());
        assertEquals("Invalid grade.", result.getMessage());
    }

    @Test
    public void GivenUpdate_WhenGivenValidId_ReturnsFailedResult() {
        UUID id = UUID.randomUUID();
        GradeEntity gradeEntity = this.entityFixture.validGradeEntity(null, null);

        when(repository.findById(id)).thenReturn(Optional.of(gradeEntity));

        SingleResult<GradeEntity> result = service.update(id, gradeEntity);

        assertTrue(result.isSuccess());
        assertEquals(gradeEntity, result.getResult());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenDelete_WhenThereIsNoEntityWithGivenId_ReturnsFailedResult() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        SingleResult<GradeEntity> result = service.delete(id);

        assertFalse(result.isSuccess());
        assertEquals("No grade found with given id.", result.getMessage());
    }

    @Test
    public void GivenDelete_WhenGivenValidId_ReturnsSuccessResultWithDeletedEntity() {
        UUID id = UUID.randomUUID();
        GradeEntity gradeEntity = this.entityFixture.validGradeEntity(null, null);

        when(repository.findById(id)).thenReturn(Optional.of(gradeEntity));

        SingleResult<GradeEntity> result = service.delete(id);

        assertTrue(result.isSuccess());
        assertEquals(gradeEntity, result.getResult());
        assertEquals("Success.", result.getMessage());
    }
}
