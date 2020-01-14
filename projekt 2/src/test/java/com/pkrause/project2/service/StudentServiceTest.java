package com.pkrause.project2.service;

import com.pkrause.project2.domain.StudentEntity;
import com.pkrause.project2.fixture.EntityFixture;
import com.pkrause.project2.repository.StudentRepository;
import com.pkrause.project2.result.MultipleResult;
import com.pkrause.project2.result.SingleResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Date;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {
    @InjectMocks
    StudentService service;
    @Mock
    StudentRepository repository;

    private EntityFixture entityFixture = new EntityFixture();

    @Test
    public void GivenCreate_WhenGivenInvalidEntity_ReturnsFailedResult() {
        StudentEntity invalidStudentEntity = this.entityFixture.invalidStudentEntity;

        SingleResult<StudentEntity> result = service.create(invalidStudentEntity);

        assertFalse(result.isSuccess());
        assertEquals("Invalid student.", result.getMessage());
    }

    @Test
    public void GivenCreate_WhenGivenValidEntity_ReturnsSuccessResult() {
        StudentEntity validStudentEntity = this.entityFixture.validStudentEntity(null);

        SingleResult<StudentEntity> result = service.create(validStudentEntity);

        assertTrue(result.isSuccess());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenRead_WhenThereAreNoEntities_ReturnsFailedResult() {
        MultipleResult<StudentEntity> result = service.read();

        assertFalse(result.isSuccess());
        assertEquals("No students found.", result.getMessage());
    }

    @Test
    public void GivenRead_WhenThereIsOneEntity_ReturnsSuccessResultWithOneEntity() {
        when(repository.findAll()).thenReturn(Collections.singletonList(this.entityFixture.validStudentEntity(null)));

        MultipleResult<StudentEntity> result = service.read();

        assertTrue(result.isSuccess());
        assertEquals(1, result.getResult().size());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenReadWithId_WhenThereAreNoMatchingEntities_ReturnsFailedResult() {
        SingleResult<StudentEntity> result = service.read(UUID.randomUUID());

        assertFalse(result.isSuccess());
        assertEquals("No student found with given id.", result.getMessage());
    }

    @Test
    public void GivenReadWithId_WhenThereIsMatchingEntity_ReturnsSuccessResultWithOneEntity() {
        UUID id = UUID.randomUUID();

        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);
        studentEntity.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(studentEntity));

        SingleResult<StudentEntity> result = service.read(id);

        assertTrue(result.isSuccess());
        assertEquals(studentEntity, result.getResult());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenReadWithNameSurnameSecondName_WhenThereAreNoMatchingEntities_ReturnsFailedResult() {
        SingleResult<StudentEntity> result = service.read(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());

        assertFalse(result.isSuccess());
        assertEquals("No student found with given values.", result.getMessage());
    }

    @Test
    public void GivenReadWithNameSurnameSecondName_WhenThereIsMatchingEntity_ReturnsSuccessResultWithOneEntity() {
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);

        when(repository.findByNameAndSecondNameAndSurname(studentEntity.getName(), studentEntity.getSecondName(), studentEntity.getSurname()))
                .thenReturn(Optional.of(studentEntity));

        SingleResult<StudentEntity> result = service.read(studentEntity.getName(), studentEntity.getSecondName(), studentEntity.getSurname());

        assertTrue(result.isSuccess());
        assertEquals(studentEntity, result.getResult());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenReadBornAfter_WhenThereAreNoMatchingEntities_ReturnsFailedResult() {
        MultipleResult<StudentEntity> result = service.readBornAfter(Date.valueOf("2000-01-01"));

        assertFalse(result.isSuccess());
        assertEquals("No students born after given date found.", result.getMessage());
    }

    @Test
    public void GivenReadBornAfter_WhenThereIsMatchingEntity_ReturnsSuccessResultWithOneEntity() {
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);

        when(repository.bornAfter(studentEntity.getBirthday())).thenReturn(Collections.singletonList(studentEntity));

        MultipleResult<StudentEntity> result = service.readBornAfter(studentEntity.getBirthday());

        assertTrue(result.isSuccess());
        assertEquals(1, result.getResult().size());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenReadBornBefore_WhenThereAreNoMatchingEntities_ReturnsFailedResult() {
        MultipleResult<StudentEntity> result = service.readBornBefore(Date.valueOf("2000-01-01"));

        assertFalse(result.isSuccess());
        assertEquals("No students born before given date found.", result.getMessage());
    }

    @Test
    public void GivenReadBornBefore_WhenThereIsMatchingEntity_ReturnsSuccessResultWithOneEntity() {
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);

        when(repository.bornBefore(studentEntity.getBirthday())).thenReturn(Collections.singletonList(studentEntity));

        MultipleResult<StudentEntity> result = service.readBornBefore(studentEntity.getBirthday());

        assertTrue(result.isSuccess());
        assertEquals(1, result.getResult().size());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenUpdate_WhenThereIsNoEntityWithGivenId_ReturnsFailedResult() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        SingleResult<StudentEntity> result = service.update(id, this.entityFixture.invalidStudentEntity);

        assertFalse(result.isSuccess());
        assertEquals("No student found with given id.", result.getMessage());
    }

    @Test
    public void GivenUpdate_WhenGivenInvalidId_ReturnsFailedResult() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.of(this.entityFixture.validStudentEntity(null)));

        SingleResult<StudentEntity> result = service.update(id, this.entityFixture.invalidStudentEntity);

        assertFalse(result.isSuccess());
        assertEquals("Invalid student.", result.getMessage());
    }

    @Test
    public void GivenUpdate_WhenGivenValidId_ReturnsFailedResult() {
        UUID id = UUID.randomUUID();
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);

        when(repository.findById(id)).thenReturn(Optional.of(studentEntity));

        SingleResult<StudentEntity> result = service.update(id, studentEntity);

        assertTrue(result.isSuccess());
        assertEquals(studentEntity, result.getResult());
        assertEquals("Success.", result.getMessage());
    }

    @Test
    public void GivenDelete_WhenThereIsNoEntityWithGivenId_ReturnsFailedResult() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        SingleResult<StudentEntity> result = service.delete(id);

        assertFalse(result.isSuccess());
        assertEquals("No student found with given id.", result.getMessage());
    }

    @Test
    public void GivenDelete_WhenGivenValidId_ReturnsSuccessResultWithDeletedEntity() {
        UUID id = UUID.randomUUID();
        StudentEntity studentEntity = this.entityFixture.validStudentEntity(null);

        when(repository.findById(id)).thenReturn(Optional.of(studentEntity));

        SingleResult<StudentEntity> result = service.delete(id);

        assertTrue(result.isSuccess());
        assertEquals(studentEntity, result.getResult());
        assertEquals("Success.", result.getMessage());
    }
}
