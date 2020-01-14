package RepositoryTest;

import Fixture.EntityFixture;
import domain.StudentEntity;
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

public class StudentRepositoryTest {
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
        StudentRepository studentRepository = new StudentRepository(connection);

        StudentEntity studentEntity = entityFixture.validStudentEntity();
        assertTrue(studentRepository.create(studentEntity));

        List<StudentEntity> studentEntities = studentRepository.readAll();
        assertFalse(studentEntities.isEmpty());
    }

    @Test
    public void GivenReadByKey_WhenGivenValidId_ReturnsValidEntity() {
        StudentRepository studentRepository = new StudentRepository(connection);

        StudentEntity studentEntity = entityFixture.validStudentEntity();
        assertTrue(studentRepository.create(studentEntity));

        List<StudentEntity> studentEntities = studentRepository.readAll();
        assertFalse(studentEntities.isEmpty());

        assertEquals(studentEntity, studentRepository.readByKey(0));
    }

    @Test
    public void GivenReadAll_WhenCannotFindAnyRecords_ReturnsEmptyList() {
        StudentRepository studentRepository = new StudentRepository(connection);

        List<StudentEntity> studentEntities = studentRepository.readAll();
        assertTrue(studentEntities.isEmpty());
    }

    @Test
    public void GivenReadAll_WhenThereAreAnyRecords_ReturnsValidList() {
        StudentRepository studentRepository = new StudentRepository(connection);

        StudentEntity studentEntity = entityFixture.validStudentEntity();
        assertTrue(studentRepository.create(studentEntity));

        List<StudentEntity> studentEntities = studentRepository.readAll();
        assertFalse(studentEntities.isEmpty());
    }

    @Test
    public void GivenReadByKey_WhenCannotFindRecord_ReturnsNull() {
        StudentRepository studentRepository = new StudentRepository(connection);

        List<StudentEntity> studentEntities = studentRepository.readAll();
        assertTrue(studentEntities.isEmpty());

        assertNull(studentRepository.readByKey(0));
    }

    @Test
    public void GivenUpdate_WhenGivenValidId_UpdatesRecordAndReturnsTrue() {
        StudentRepository studentRepository = new StudentRepository(connection);

        StudentEntity studentEntity = entityFixture.validStudentEntity();
        assertTrue(studentRepository.create(studentEntity));

        studentEntity.setId(0);
        studentEntity.setName("Other");
        assertTrue(studentRepository.update(0, studentEntity));

        List<StudentEntity> studentEntities = studentRepository.readAll();
        assertTrue(entityFixture.contains(studentEntities, studentEntity));
    }

    @Test
    public void GivenUpdate_WhenGivenInvalidId_DoesntUpdateRecordAndReturnsTrue() {
        StudentRepository studentRepository = new StudentRepository(connection);

        StudentEntity studentEntity = entityFixture.validStudentEntity();
        assertTrue(studentRepository.create(studentEntity));

        studentEntity.setId(0);
        studentEntity.setName("Other");
        assertTrue(studentRepository.update(1, studentEntity));

        List<StudentEntity> studentEntities = studentRepository.readAll();
        assertFalse(entityFixture.contains(studentEntities, studentEntity));
    }

    @Test
    public void GivenDelete_WhenGivenValidId_DeletesRecordAndReturnsTrue() {
        StudentRepository studentRepository = new StudentRepository(connection);

        StudentEntity studentEntity = entityFixture.validStudentEntity();
        assertTrue(studentRepository.create(studentEntity));

        assertTrue(studentRepository.delete(0));

        List<StudentEntity> classEntities = studentRepository.readAll();
        assertFalse(entityFixture.contains(classEntities, studentEntity));
    }

    @Test
    public void GivenDelete_WhenGivenInvalidId_DoesntDeleteRecordAndReturnsTrue() {
        StudentRepository studentRepository = new StudentRepository(connection);

        StudentEntity studentEntity = entityFixture.validStudentEntity();
        assertTrue(studentRepository.create(studentEntity));

        assertTrue(studentRepository.delete(1));

        List<StudentEntity> classEntities = studentRepository.readAll();
        assertTrue(entityFixture.contains(classEntities, studentEntity));
    }
}
