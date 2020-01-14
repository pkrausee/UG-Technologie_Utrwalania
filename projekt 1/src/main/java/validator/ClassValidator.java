package validator;

import domain.ClassEntity;
import domain.StudentEntity;
import services.IService;
import utilities.StringUtils;

public class ClassValidator {
    private IService<StudentEntity, Integer> service;

    public ClassValidator(IService<StudentEntity, Integer> service) {
        this.service = service;
    }

    public boolean validateFields(ClassEntity classEntity) {
        // TODO: This should output IResult instead of boolean

        if (classEntity.getPresidentId() != null && !service.readByKey(classEntity.getPresidentId()).success()) {
            return false;
        }

        if (classEntity.getStartDate() == null || classEntity.getEndDate() == null || classEntity.isSpecial() == null) {
            return false;
        }

        if (classEntity.getStartDate().compareTo(classEntity.getEndDate()) < 0) {
            return false;
        }

        if (StringUtils.isNullOrEmpty(classEntity.getProfile())) {
            return false;
        }

        return true;
    }
}
