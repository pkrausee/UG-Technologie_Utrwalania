package ValidatorTest;

import Fixture.EntityFixture;
import domain.StudentEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import response.SingleResult;
import services.IService;
import validator.ClassValidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ClassValidatorTest {
    private static final EntityFixture entityFixture = new EntityFixture();

    @InjectMocks
    ClassValidator validator;

    @Mock
    IService<StudentEntity, Integer> service;

    @Test
    public void GivenValidateFields_WhenInvalidEntity_ReturnsFalse() {
        assertFalse(validator.validateFields(entityFixture.invalidClassEntity()));
    }

    @Test
    public void GivenValidateFields_WhenValidEntity_ReturnsTrue() {
        when(service.readByKey(-1)).thenReturn(new SingleResult<StudentEntity>(true, ""));

        assertTrue(validator.validateFields(entityFixture.validClassEntity()));
    }
}
