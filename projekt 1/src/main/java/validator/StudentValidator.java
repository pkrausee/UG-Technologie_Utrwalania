package validator;

import domain.ClassEntity;
import domain.StudentEntity;
import services.IService;
import utilities.StringUtils;

public class StudentValidator {
    private IService<ClassEntity, Integer> service;

    public StudentValidator(IService<ClassEntity, Integer> service) {
        this.service = service;
    }

    public boolean validateFields(StudentEntity student) {
        // TODO: This should output IResult instead of boolean

        if (student.getClassId() == null || !service.readByKey(student.getClassId()).success()) {
            return false;
        }

        if (StringUtils.isNullOrEmpty(student.getName())) {
            return false;
        }

        if (StringUtils.isNullOrEmpty(student.getSurname())) {
            return false;
        }

        if (StringUtils.isNullOrEmpty(student.getPhoneNumber()) || student.getPhoneNumber().length() != 9) {
            return false;
        }

        if (student.getBirthDate() == null) {
            return false;
        }

        if (StringUtils.isNullOrEmpty(student.getPesel()) || student.getPesel().length() != 11) {
            return false;
        }

        if (StringUtils.isNullOrEmpty(student.getStreet()) || StringUtils.isNullOrEmpty(student.getHouseNumber())) {
            return false;
        }

        return true;
    }
}
