package RepositoryTest;

import Fixture.EntityFixture;
import domain.ClassEntity;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import repository.ClassRepository;
import repository.StudentRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class ClassRepositoryTest {
    private static final EntityFixture entityFixture = new EntityFixture();
    private static Connection connection;

    @BeforeClass
    public static void setUp() {
        try {
            connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost:9001/hsqldb;user=sa;password=sa");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void tearDown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @After
    public void afterTest() {
        // Clean up after tests

        ClassRepository classRepository = new ClassRepository(connection);
        StudentRepository studentRepository = new StudentRepository(connection);

        classRepository.drop();
        studentRepository.drop();
    }

        @Test
    public void GivenCreate_AddsRecordToDatabaseAndReturnsTrue() {
        ClassRepository classRepository = new ClassRepository(connection);

        ClassEntity classEntity = entityFixture.validClassEntity();
        assertTrue(classRepository.create(classEntity));

        List<ClassEntity> classEntities = classRepository.readAll();
        assertFalse(classEntities.isEmpty());
    }

    @Test
    public void GivenReadByKey_WhenGivenValidId_ReturnsValidEntity() {
        ClassRepository classRepository = new ClassRepository(connection);

        ClassEntity classEntity = entityFixture.validClassEntity();
        assertTrue(classRepository.create(classEntity));

        List<ClassEntity> classEntities = classRepository.readAll();
        assertFalse(classEntities.isEmpty());

        assertEquals(classRepository.readByKey(0), classEntity);
    }

    @Test
    public void GivenReadAll_WhenCannotFindAnyRecords_ReturnsEmptyList() {
        ClassRepository classRepository = new ClassRepository(connection);

        List<ClassEntity> classEntities = classRepository.readAll();
        assertTrue(classEntities.isEmpty());
    }

    @Test
    public void GivenReadAll_WhenThereAreAnyRecords_ReturnsValidList() {
        ClassRepository classRepository = new ClassRepository(connection);

        ClassEntity classEntity = entityFixture.validClassEntity();
        assertTrue(classRepository.create(classEntity));

        List<ClassEntity> classEntities = classRepository.readAll();
        assertFalse(classEntities.isEmpty());
    }

    @Test
    public void GivenReadByKey_WhenCannotFindRecord_ReturnsNull() {
        ClassRepository classRepository = new ClassRepository(connection);

        List<ClassEntity> classEntities = classRepository.readAll();
        assertTrue(classEntities.isEmpty());

        assertNull(classRepository.readByKey(0));
    }

    @Test
    public void GivenUpdate_WhenGivenValidId_UpdatesRecordAndReturnsTrue() {
        ClassRepository classRepository = new ClassRepository(connection);

        ClassEntity classEntity = entityFixture.validClassEntity();
        assertTrue(classRepository.create(classEntity));

        classEntity.setId(0);
        classEntity.setProfile("Other");
        assertTrue(classRepository.update(0, classEntity));

        List<ClassEntity> classEntities = classRepository.readAll();
        assertTrue(entityFixture.contains(classEntities, classEntity));
    }

    @Test
    public void GivenUpdate_WhenGivenInvalidId_DoesntUpdateRecordAndReturnsTrue() {
        ClassRepository classRepository = new ClassRepository(connection);

        ClassEntity classEntity = entityFixture.validClassEntity();
        assertTrue(classRepository.create(classEntity));

        classEntity.setId(0);
        classEntity.setProfile("Other");
        assertTrue(classRepository.update(1, classEntity));

        List<ClassEntity> classEntities = classRepository.readAll();
        assertFalse(entityFixture.contains(classEntities, classEntity));
    }

    @Test
    public void GivenDelete_WhenGivenValidId_DeletesRecordAndReturnsTrue() {
        ClassRepository classRepository = new ClassRepository(connection);

        ClassEntity classEntity = entityFixture.validClassEntity();
        assertTrue(classRepository.create(classEntity));

        assertTrue(classRepository.delete(0));

        List<ClassEntity> classEntities = classRepository.readAll();
        assertFalse(entityFixture.contains(classEntities, classEntity));
    }

    @Test
    public void GivenDelete_WhenGivenInvalidId_DoesntDeleteRecordAndReturnsTrue() {
        ClassRepository classRepository = new ClassRepository(connection);

        ClassEntity classEntity = entityFixture.validClassEntity();
        assertTrue(classRepository.create(classEntity));

        assertTrue(classRepository.delete(1));

        List<ClassEntity> classEntities = classRepository.readAll();
        assertTrue(entityFixture.contains(classEntities, classEntity));
    }
}
