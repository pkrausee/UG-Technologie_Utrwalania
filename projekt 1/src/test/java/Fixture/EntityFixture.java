package Fixture;

import domain.ClassEntity;
import domain.StudentEntity;

import java.sql.Date;
import java.util.List;

public class EntityFixture {

    // TODO: Mock Connection Create method (maybe useful)

    public StudentEntity validStudentEntity() {
        StudentEntity studentEntity = new StudentEntity();
        studentEntity.setClassId(0);
        studentEntity.setName("Test");
        studentEntity.setSecondName("Tester");
        studentEntity.setSurname("Testowy");
        studentEntity.setPhoneNumber("123456789");
        studentEntity.setBirthDate(new Date(System.currentTimeMillis()));
        studentEntity.setPesel("11111111111");
        studentEntity.setStreet("Testowa");
        studentEntity.setHouseNumber("11");

        return studentEntity;
    }

    public StudentEntity invalidStudentEntity() {
        return new StudentEntity();
    }

    public ClassEntity validClassEntity() {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setPresidentId(-1);
        classEntity.setStartDate(new Date(System.currentTimeMillis()));
        classEntity.setEndDate(new Date(System.currentTimeMillis()));
        classEntity.setProfile("Test");
        classEntity.setSpecial(true);

        return classEntity;
    }

    public ClassEntity invalidClassEntity() {
        return new ClassEntity();
    }

    public <T> boolean contains(List<T> list, T element) {
        for (T e : list) {
            if (e.equals(element)) {
                return true;
            }
        }
        return false;
    }
}
