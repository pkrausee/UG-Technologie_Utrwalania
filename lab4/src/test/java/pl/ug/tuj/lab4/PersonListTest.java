package pl.ug.tuj.lab4;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonListTest {

    @Autowired
    private PersonList personList;

    @After
    public void afterTest() {
        this.personList.clear();
    }

    @Test
    void GivenGet_WhenGivenValidId_ReturnsObject() {
        personList.add(new Person(1, "Joe", "Doe"));

        Person result = personList.get(1);

        assertNotNull(result);
    }

    @Test
    void GivenGet_WhenGivenInvalidId_ReturnsNull() {
        Person result = personList.get(1);

        assertNull(result);
    }

    @Test
    void GivenDelete_WhenGivenValidId_ReturnsTrueAndRemovesObject() {
        personList.add(new Person(1, "Joe", "Doe"));

        assertTrue(personList.delete(1));
        assertNull(personList.get(1));
    }

    @Test
    void GivenDelete_WhenGivenInvalidId_ReturnsFalse() {
        assertFalse(personList.delete(1));
    }
}
