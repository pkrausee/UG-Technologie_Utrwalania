package com.pkrause.project2.service;

import com.pkrause.project2.domain.SubjectEntity;
import com.pkrause.project2.fixture.EntityFixture;
import com.pkrause.project2.repository.SubjectRepository;
import com.pkrause.project2.result.MultipleResult;
import com.pkrause.project2.result.SingleResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SubjectServiceTest {
    @InjectMocks
    SubjectService service;
    @Mock
    SubjectRepository repository;

    private EntityFixture entityFixture = new EntityFixture();

    @Test
    public void GivenCreate_WhenGivenInvalidEntity_ReturnsFailedResult() {
        SubjectEntity invalidSubjectEntity = this.entityFixture.invalidSubjectEntity;

        SingleResult<SubjectEntity> result = service.create(invalidSubjectEntity);

        assertFalse(result.isSuccess());
        assertEquals("Invalid subject.", result.getMessage());
    }

    @Test
    public void GivenCreate_WhenGivenValidEntity_ReturnsSuccessResult() {
        SubjectEntity validSubjectEntity = this.entityFixture.validSubjectEntity(null);

        SingleResult<SubjectEntity> result = service.create(validSubjectEntity);

        assertTrue(result.isSuccess());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenRead_WhenThereAreNoEntities_ReturnsFailedResult() {
        MultipleResult<SubjectEntity> result = service.read();

        assertFalse(result.isSuccess());
        assertEquals("No subjects found.", result.getMessage());
    }

    @Test
    public void GivenRead_WhenThereIsOneEntity_ReturnsSuccessResultWithOneEntity() {
        when(repository.findAll()).thenReturn(Collections.singletonList(this.entityFixture.validSubjectEntity(null)));

        MultipleResult<SubjectEntity> result = service.read();

        assertTrue(result.isSuccess());
        assertEquals(1, result.getResult().size());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenReadWithId_WhenThereAreNoMatchingEntities_ReturnsFailedResult() {
        SingleResult<SubjectEntity> result = service.read(Mockito.anyLong());

        assertFalse(result.isSuccess());
        assertEquals("No subject found with given id.", result.getMessage());
    }

    @Test
    public void GivenReadWithId_WhenThereIsMatchingEntity_ReturnsSuccessResultWithOneEntity() {
        Long id = Mockito.anyLong();

        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);
        subjectEntity.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(subjectEntity));

        SingleResult<SubjectEntity> result = service.read(id);

        assertTrue(result.isSuccess());
        assertEquals(subjectEntity, result.getResult());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenReadWithNameRangeRemoved_WhenThereAreNoMatchingEntities_ReturnsFailedResult() {
        MultipleResult<SubjectEntity> result = service.read(Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean());

        assertFalse(result.isSuccess());
        assertEquals("No subject found with given values.", result.getMessage());
    }

    @Test
    public void GivenReadWithNameRangeRemoved_WhenThereIsMatchingEntity_ReturnsSuccessResultWithOneEntity() {
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        when(repository.findAllByNameAndRangeAndRemovedEquals(subjectEntity.getName(), subjectEntity.getRange(), subjectEntity.isRemoved()))
                .thenReturn(Collections.singletonList(subjectEntity));

        MultipleResult<SubjectEntity> result = service.read(subjectEntity.getName(), subjectEntity.getRange(), subjectEntity.isRemoved());

        assertTrue(result.isSuccess());
        assertEquals(1, result.getResult().size());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenReadWithRangeCreatedRemoved_WhenThereAreNoMatchingEntities_ReturnsFailedResult() {
        MultipleResult<SubjectEntity> result = service.read(EntityFixture.mockName, EntityFixture.mockTimestamp, true);

        assertFalse(result.isSuccess());
        assertEquals("No subject found with given values.", result.getMessage());
    }

    @Test
    public void GivenReadWithRangeCreatedRemoved_WhenThereIsMatchingEntity_ReturnsSuccessResultWithOneEntity() {
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        when(repository.findAllByRangeAndCreatedAfterAndRemovedEquals(subjectEntity.getRange(), subjectEntity.getCreated(), subjectEntity.isRemoved()))
                .thenReturn(Collections.singletonList(subjectEntity));

        MultipleResult<SubjectEntity> result = service.read(subjectEntity.getRange(), subjectEntity.getCreated(), subjectEntity.isRemoved());

        assertTrue(result.isSuccess());
        assertEquals(1, result.getResult().size());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenReadRemoved_WhenThereAreNoMatchingEntities_ReturnsFailedResult() {
        MultipleResult<SubjectEntity> result = service.readRemoved();

        assertFalse(result.isSuccess());
        assertEquals("No removed subjects found.", result.getMessage());
    }

    @Test
    public void GivenReadRemoved_WhenThereIsMatchingEntity_ReturnsSuccessResultWithOneEntity() {
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        when(repository.removedSubjects()).thenReturn(Collections.singletonList(subjectEntity));

        MultipleResult<SubjectEntity> result = service.readRemoved();

        assertTrue(result.isSuccess());
        assertEquals(1, result.getResult().size());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenUpdate_WhenThereIsNoEntityWithGivenId_ReturnsFailedResult() {
        Long id = Mockito.anyLong();

        when(repository.findById(id)).thenReturn(Optional.empty());

        SingleResult<SubjectEntity> result = service.update(id, this.entityFixture.invalidSubjectEntity);

        assertFalse(result.isSuccess());
        assertEquals("No subject found with given id.", result.getMessage());
    }

    @Test
    public void GivenUpdate_WhenGivenInvalidId_ReturnsFailedResult() {
        Long id = Mockito.anyLong();

        when(repository.findById(id)).thenReturn(Optional.of(this.entityFixture.validSubjectEntity(null)));

        SingleResult<SubjectEntity> result = service.update(id, this.entityFixture.invalidSubjectEntity);

        assertFalse(result.isSuccess());
        assertEquals("Invalid subject.", result.getMessage());
    }

    @Test
    public void GivenUpdate_WhenGivenValidId_ReturnsFailedResult() {
        Long id = Mockito.anyLong();
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        when(repository.findById(id)).thenReturn(Optional.of(subjectEntity));

        SingleResult<SubjectEntity> result = service.update(id, subjectEntity);

        assertTrue(result.isSuccess());
        assertEquals(subjectEntity, result.getResult());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenDelete_WhenThereIsNoEntityWithGivenId_ReturnsFailedResult() {
        Long id = Mockito.anyLong();

        when(repository.findById(id)).thenReturn(Optional.empty());

        SingleResult<SubjectEntity> result = service.delete(id);

        assertFalse(result.isSuccess());
        assertEquals("No subject found with given id.", result.getMessage());
    }

    @Test
    public void GivenDelete_WhenGivenValidId_ReturnsSuccessResultWithDeletedEntity() {
        Long id = Mockito.anyLong();
        SubjectEntity subjectEntity = this.entityFixture.validSubjectEntity(null);

        when(repository.findById(id)).thenReturn(Optional.of(subjectEntity));

        SingleResult<SubjectEntity> result = service.delete(id);

        assertTrue(result.isSuccess());
        assertEquals(subjectEntity, result.getResult());
        assertEquals("Success.", result.getMessage());
    }
}
