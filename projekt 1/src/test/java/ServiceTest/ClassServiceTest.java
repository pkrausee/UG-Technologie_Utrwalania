package ServiceTest;

import Fixture.EntityFixture;
import domain.ClassEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import repository.IRepository;
import response.IResult;
import services.ClassService;
import validator.ClassValidator;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ClassServiceTest {
    private static final EntityFixture entityFixture = new EntityFixture();

    @InjectMocks
    ClassService service;

    @Mock
    ClassValidator validatorMock;

    @Mock
    IRepository<ClassEntity, Integer> repositoryMock;

    @Test
    public void GivenTryCreate_WhenCannotValidateEntity_ReturnsFail() {
        ClassEntity classEntity = entityFixture.invalidClassEntity();

        when(validatorMock.validateFields(classEntity)).thenReturn(false);

        IResult<ClassEntity> result = service.tryCreate(classEntity);

        assertFalse(result.success());
        assertEquals(result.getMessage(), "Cannot validate the fields");
        assertNull(result.result());
    }

    @Test
    public void GivenTryCreate_WhenCannotCreateEntity_ReturnsFail() {
        ClassEntity classEntity = entityFixture.invalidClassEntity();

        when(validatorMock.validateFields(classEntity)).thenReturn(true);
        when(repositoryMock.create(classEntity)).thenReturn(false);

        IResult<ClassEntity> result = service.tryCreate(classEntity);

        assertFalse(result.success());
        assertEquals(result.getMessage(), "Cannot create the entities");
        assertNull(result.result());
    }

    @Test
    public void GivenTryCreate_WhenGivenValidEntity_ReturnsSuccess() {
        ClassEntity classEntity = entityFixture.invalidClassEntity();

        when(validatorMock.validateFields(classEntity)).thenReturn(true);
        when(repositoryMock.create(classEntity)).thenReturn(true);

        IResult<ClassEntity> result = service.tryCreate(classEntity);

        assertTrue(result.success());
        assertEquals(result.getMessage(), "Success");
        assertNotNull(result.result());
    }

    @Test
    public void GivenTryCreateMany_WhenCannotValidateEntity_ReturnsFail() {
        Connection connectionMock = Mockito.mock(Connection.class);
        when(repositoryMock.getConnection()).thenReturn(connectionMock);

        ClassEntity validClassEntity = entityFixture.validClassEntity();
        ClassEntity invalidClassEntity = entityFixture.invalidClassEntity();

        List<ClassEntity> classEntities = new ArrayList<ClassEntity>(
                Arrays.asList(validClassEntity, invalidClassEntity));

        when(validatorMock.validateFields(any(ClassEntity.class))).thenReturn(false);

        IResult<List<ClassEntity>> result = service.tryCreateMany(classEntities);

        assertFalse(result.success());
        assertEquals(result.getMessage(), "Cannot validate the fields");
        assertNull(result.result());
    }

    @Test
    public void GivenTryCreateMany_WhenCannotCreateEntities_ReturnsFail() {
        Connection connectionMock = Mockito.mock(Connection.class);
        when(repositoryMock.getConnection()).thenReturn(connectionMock);

        ClassEntity validClassEntity = entityFixture.validClassEntity();
        ClassEntity invalidClassEntity = entityFixture.invalidClassEntity();

        List<ClassEntity> classEntities = new ArrayList<ClassEntity>(
                Arrays.asList(validClassEntity, invalidClassEntity));

        when(validatorMock.validateFields(any(ClassEntity.class))).thenReturn(true);

        when(repositoryMock.create(any(ClassEntity.class))).thenReturn(false);

        IResult<List<ClassEntity>> result = service.tryCreateMany(classEntities);

        assertFalse(result.success());
        assertEquals(result.getMessage(), "Cannot create the entities");
        assertNull(result.result());
    }

    @Test
    public void GivenTryCreateMany_WhenGivenValidEntities_ReturnsSuccess() {
        Connection connectionMock = Mockito.mock(Connection.class);
        when(repositoryMock.getConnection()).thenReturn(connectionMock);

        ClassEntity validClassEntity = entityFixture.validClassEntity();
        ClassEntity invalidClassEntity = entityFixture.invalidClassEntity();

        List<ClassEntity> classEntities = new ArrayList<ClassEntity>(
                Arrays.asList(validClassEntity, invalidClassEntity));

        when(validatorMock.validateFields(any(ClassEntity.class))).thenReturn(true);

        when(repositoryMock.create(any(ClassEntity.class))).thenReturn(true);

        IResult<List<ClassEntity>> result = service.tryCreateMany(classEntities);

        assertTrue(result.success());
        assertEquals(result.getMessage(), "Success");
        assertNotNull(result.result());
    }

    @Test
    public void GivenReadByKey_WhenGivenValidId_ReturnsSuccess() {
        when(repositoryMock.readByKey(0)).thenReturn(new ClassEntity());

        IResult<ClassEntity> result = service.readByKey(0);

        assertTrue(result.success());
        assertEquals(result.getMessage(), "Success");
        assertNotNull(result.result());
    }

    @Test
    public void GivenReadByKey_WhenGivenInvalidId_ReturnsFail() {
        when(repositoryMock.readByKey(0)).thenReturn(null);

        IResult<ClassEntity> result = service.readByKey(0);

        assertFalse(result.success());
        assertEquals(result.getMessage(), "Cannot find given id");
        assertNull(result.result());
    }

    @Test
    public void GivenReadAll_WhenThereAreAnyEntities_ReturnsFail() {
        when(repositoryMock.readAll()).thenReturn(new ArrayList<ClassEntity>(Collections.singletonList(entityFixture.validClassEntity())));

        IResult<List<ClassEntity>> result = service.readAll();

        assertTrue(result.success());
        assertEquals(result.getMessage(), "Success");
        assertFalse((result.result()).isEmpty());
    }

    @Test
    public void GivenReadAll_WhenThereAreNoEntities_ReturnsFail() {
        when(repositoryMock.readAll()).thenReturn(new ArrayList<ClassEntity>());

        IResult<List<ClassEntity>> result = service.readAll();

        assertFalse(result.success());
        assertEquals(result.getMessage(), "Cannot find any entities");
        assertNull(result.result());
    }

    @Test
    public void GivenTryUpdate_WhenCannotValidateEntity_ReturnsFail() {
        ClassEntity classEntity = entityFixture.invalidClassEntity();

        when(validatorMock.validateFields(classEntity)).thenReturn(false);

        IResult<ClassEntity> result = service.tryUpdate(0, classEntity);

        assertFalse(result.success());
        assertEquals(result.getMessage(), "Cannot validate the fields");
        assertNull(result.result());
    }

    @Test
    public void GivenTryUpdate_WhenCannotUpdateEntity_ReturnsFail() {
        ClassEntity classEntity = entityFixture.invalidClassEntity();

        when(validatorMock.validateFields(classEntity)).thenReturn(true);
        when(repositoryMock.update(0, classEntity)).thenReturn(false);

        IResult<ClassEntity> result = service.tryUpdate(0, classEntity);

        assertFalse(result.success());
        assertEquals(result.getMessage(), "Cannot update the entity");
        assertNull(result.result());
    }

    @Test
    public void GivenTryUpdate_WhenGivenValidEntity_ReturnsSuccess() {
        ClassEntity classEntity = entityFixture.invalidClassEntity();

        when(validatorMock.validateFields(classEntity)).thenReturn(true);
        when(repositoryMock.update(0, classEntity)).thenReturn(true);

        IResult<ClassEntity> result = service.tryUpdate(0, classEntity);

        assertTrue(result.success());
        assertEquals(result.getMessage(), "Success");
        assertNotNull(result.result());
    }

    @Test
    public void GivenTryDelete_WhenCannotDeleteEntity_ReturnsFail() {
        when(repositoryMock.delete(0)).thenReturn(false);

        IResult<ClassEntity> result = service.tryDelete(0);

        assertFalse(result.success());
        assertEquals(result.getMessage(), "Cannot delete given id");
    }

    @Test
    public void GivenTryDelete_WhenGivenValidId_ReturnsSuccess() {
        when(repositoryMock.delete(0)).thenReturn(true);

        IResult<ClassEntity> result = service.tryDelete(0);

        assertTrue(result.success());
        assertEquals(result.getMessage(), "Success");
    }
}

