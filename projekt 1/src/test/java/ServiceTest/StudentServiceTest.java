package ServiceTest;

import Fixture.EntityFixture;
import domain.StudentEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import repository.IRepository;
import response.IResult;
import services.StudentService;
import validator.StudentValidator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class StudentServiceTest {
    private static final EntityFixture entityFixture = new EntityFixture();

    @InjectMocks
    StudentService service;

    @Mock
    StudentValidator validatorMock;

    @Mock
    IRepository<StudentEntity, Integer> repositoryMock;

    @Test
    public void GivenTryCreate_WhenCannotValidateEntity_ReturnsFail() {
        StudentEntity studentEntity = entityFixture.invalidStudentEntity();

        when(validatorMock.validateFields(studentEntity)).thenReturn(false);

        IResult<StudentEntity> result = service.tryCreate(studentEntity);

        assertFalse(result.success());
        assertEquals("Cannot validate the fields", result.getMessage());
        assertNull(result.result());
    }

    @Test
    public void GivenTryCreate_WhenCannotCreateEntity_ReturnsFail() {
        StudentEntity studentEntity = entityFixture.invalidStudentEntity();

        when(validatorMock.validateFields(studentEntity)).thenReturn(true);
        when(repositoryMock.create(studentEntity)).thenReturn(false);

        IResult<StudentEntity> result = service.tryCreate(studentEntity);

        assertFalse(result.success());
        assertEquals("Cannot create the entities", result.getMessage());
        assertNull(result.result());
    }

    @Test
    public void GivenTryCreate_WhenGivenValidEntity_ReturnsSuccess() {
        StudentEntity studentEntity = entityFixture.invalidStudentEntity();

        when(validatorMock.validateFields(studentEntity)).thenReturn(true);
        when(repositoryMock.create(studentEntity)).thenReturn(true);

        IResult<StudentEntity> result = service.tryCreate(studentEntity);

        assertTrue(result.success());
        assertEquals("Success", result.getMessage());
        assertNotNull(result.result());
    }

    @Test
    public void GivenTryCreateMany_WhenCannotValidateEntity_ReturnsFail() {
        Connection connectionMock = Mockito.mock(Connection.class);
        when(repositoryMock.getConnection()).thenReturn(connectionMock);

        StudentEntity validStudentEntity = entityFixture.validStudentEntity();
        StudentEntity invalidStudentEntity = entityFixture.invalidStudentEntity();

        List<StudentEntity> studentEntities = new ArrayList<StudentEntity>(
                Arrays.asList(validStudentEntity, invalidStudentEntity));

        when(validatorMock.validateFields(any(StudentEntity.class))).thenReturn(false);

        IResult<List<StudentEntity>> result = service.tryCreateMany(studentEntities);

        assertFalse(result.success());
        assertEquals("Cannot validate the fields", result.getMessage());
        assertNull(result.result());
    }

    @Test
    public void GivenTryCreateMany_WhenCannotCreateEntities_ReturnsFail() {
        Connection connectionMock = Mockito.mock(Connection.class);
        when(repositoryMock.getConnection()).thenReturn(connectionMock);

        StudentEntity validStudentEntity = entityFixture.validStudentEntity();
        StudentEntity invalidStudentEntity = entityFixture.invalidStudentEntity();

        List<StudentEntity> studentEntities = new ArrayList<StudentEntity>(
                Arrays.asList(validStudentEntity, invalidStudentEntity));

        when(validatorMock.validateFields(any(StudentEntity.class))).thenReturn(true);

        when(repositoryMock.create(any(StudentEntity.class))).thenReturn(false);

        IResult<List<StudentEntity>> result = service.tryCreateMany(studentEntities);

        assertFalse(result.success());
        assertEquals("Cannot create the entities", result.getMessage());
        assertNull(result.result());
    }

    @Test
    public void GivenTryCreateMany_WhenGivenValidEntities_ReturnsSuccess() {
        Connection connectionMock = Mockito.mock(Connection.class);
        when(repositoryMock.getConnection()).thenReturn(connectionMock);

        StudentEntity validStudentEntity = entityFixture.validStudentEntity();
        StudentEntity invalidStudentEntity = entityFixture.invalidStudentEntity();

        List<StudentEntity> studentEntities = new ArrayList<StudentEntity>(
                Arrays.asList(validStudentEntity, invalidStudentEntity));

        when(validatorMock.validateFields(any(StudentEntity.class))).thenReturn(true);

        when(repositoryMock.create(any(StudentEntity.class))).thenReturn(true);

        IResult<List<StudentEntity>> result = service.tryCreateMany(studentEntities);

        assertTrue(result.success());
        assertEquals("Success", result.getMessage());
        assertNotNull(result.result());
    }

    @Test
    public void GivenReadByKey_WhenGivenValidId_ReturnsSuccess() {
        when(repositoryMock.readByKey(0)).thenReturn(new StudentEntity());

        IResult<StudentEntity> result = service.readByKey(0);

        assertTrue(result.success());
        assertEquals("Success", result.getMessage());
        assertNotNull(result.result());
    }

    @Test
    public void GivenReadByKey_WhenGivenInvalidId_ReturnsFail() {
        when(repositoryMock.readByKey(0)).thenReturn(null);

        IResult<StudentEntity> result = service.readByKey(0);

        assertFalse(result.success());
        assertEquals("Cannot find given id", result.getMessage());
        assertNull(result.result());
    }

    @Test
    public void GivenReadAll_WhenThereAreAnyEntities_ReturnsFail() {
        when(repositoryMock.readAll()).thenReturn(new ArrayList<StudentEntity>(Collections.singletonList(entityFixture.validStudentEntity())));

        IResult<List<StudentEntity>> result = service.readAll();

        assertTrue(result.success());
        assertEquals("Success", result.getMessage());
        assertFalse((result.result()).isEmpty());
    }

    @Test
    public void GivenReadAll_WhenThereAreNoEntities_ReturnsFail() {
        when(repositoryMock.readAll()).thenReturn(new ArrayList<StudentEntity>());

        IResult<List<StudentEntity>> result = service.readAll();

        assertFalse(result.success());
        assertEquals("Cannot find any entities", result.getMessage());
        assertNull(result.result());
    }

    @Test
    public void GivenTryUpdate_WhenCannotValidateEntity_ReturnsFail() {
        StudentEntity studentEntity = entityFixture.invalidStudentEntity();

        when(validatorMock.validateFields(studentEntity)).thenReturn(false);

        IResult<StudentEntity> result = service.tryUpdate(0, studentEntity);

        assertFalse(result.success());
        assertEquals("Cannot validate the fields", result.getMessage());
        assertNull(result.result());
    }

    @Test
    public void GivenTryUpdate_WhenCannotUpdateEntity_ReturnsFail() {
        StudentEntity studentEntity = entityFixture.invalidStudentEntity();

        when(validatorMock.validateFields(studentEntity)).thenReturn(true);
        when(repositoryMock.update(0, studentEntity)).thenReturn(false);

        IResult<StudentEntity> result = service.tryUpdate(0, studentEntity);

        assertFalse(result.success());
        assertEquals("Cannot update the entity", result.getMessage());
        assertNull(result.result());
    }

    @Test
    public void GivenTryUpdate_WhenGivenValidEntity_ReturnsSuccess() {
        StudentEntity studentEntity = entityFixture.invalidStudentEntity();

        when(validatorMock.validateFields(studentEntity)).thenReturn(true);
        when(repositoryMock.update(0, studentEntity)).thenReturn(true);

        IResult<StudentEntity> result = service.tryUpdate(0, studentEntity);

        assertTrue(result.success());
        assertEquals("Success", result.getMessage());
        assertNotNull(result.result());
    }

    @Test
    public void GivenTryDelete_WhenCannotDeleteEntity_ReturnsFail() {
        when(repositoryMock.delete(0)).thenReturn(false);

        IResult<StudentEntity> result = service.tryDelete(0);

        assertFalse(result.success());
        assertEquals("Cannot delete given id", result.getMessage());
    }

    @Test
    public void GivenTryDelete_WhenGivenValidId_ReturnsSuccess() {
        when(repositoryMock.delete(0)).thenReturn(true);

        IResult<StudentEntity> result = service.tryDelete(0);

        assertTrue(result.success());
        assertEquals("Success", result.getMessage());
    }
}

