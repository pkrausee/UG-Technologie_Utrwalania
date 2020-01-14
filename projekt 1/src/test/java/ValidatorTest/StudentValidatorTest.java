package ValidatorTest;

import Fixture.EntityFixture;
import domain.ClassEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import response.SingleResult;
import services.IService;
import validator.StudentValidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StudentValidatorTest {
    private static final EntityFixture entityFixture = new EntityFixture();

    @InjectMocks
    StudentValidator validator;

    @Mock
    IService<ClassEntity, Integer> service;

    @Test
    public void GivenValidateFields_WhenInvalidEntity_ReturnsFalse() {
        assertFalse(validator.validateFields(entityFixture.invalidStudentEntity()));
    }

    @Test
    public void GivenValidateFields_WhenValidEntity_ReturnsTrue() {
        when(service.readByKey(0)).thenReturn(new SingleResult<ClassEntity>(true, ""));

        assertTrue(validator.validateFields(entityFixture.validStudentEntity()));
    }
}