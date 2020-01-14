package services;

import domain.StudentEntity;
import repository.IRepository;
import response.ManyResults;
import response.SingleResult;
import validator.StudentValidator;

import java.sql.SQLException;
import java.util.List;

public class StudentService implements IService<StudentEntity, Integer> {
    private StudentValidator validator;
    private IRepository<StudentEntity, Integer> repository;

    public StudentService(IRepository<StudentEntity, Integer> repository, StudentValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public SingleResult<StudentEntity> tryCreate(StudentEntity student) {
        if (!validator.validateFields(student)) {
            return new SingleResult<StudentEntity>(false, "Cannot validate the fields");
        }

        if (this.repository.create(student)) {
            return new SingleResult<StudentEntity>(true, "Success", student);
        }

        return new SingleResult<StudentEntity>(false, "Cannot create the entities");
    }

    public ManyResults<StudentEntity> tryCreateMany(List<StudentEntity> studentEntities) {
        try {
            this.repository.getConnection().setAutoCommit(false);

            for (StudentEntity classEntity : studentEntities) {
                if (!validator.validateFields(classEntity)) {
                    return new ManyResults<StudentEntity>(false, "Cannot validate the fields");
                }

                if (!this.repository.create(classEntity)) {
                    return new ManyResults<StudentEntity>(false, "Cannot create the entities");
                }
            }

            this.repository.getConnection().commit();
        } catch (SQLException e) {
            try {
                this.repository.getConnection().rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                this.repository.getConnection().setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return new ManyResults<StudentEntity>(true, "Success", studentEntities);
    }

    public SingleResult<StudentEntity> readByKey(Integer key) {
        StudentEntity readResult = this.repository.readByKey(key);

        if (readResult != null) {
            return new SingleResult<StudentEntity>(true, "Success", readResult);
        } else {
            return new SingleResult<StudentEntity>(false, "Cannot find given id", null);
        }
    }

    public ManyResults<StudentEntity> readAll() {
        List<StudentEntity> readResult = this.repository.readAll();

        if (readResult.size() > 0) {
            return new ManyResults<StudentEntity>(true, "Success", readResult);
        } else {
            return new ManyResults<StudentEntity>(false, "Cannot find any entities", null);
        }
    }

    public SingleResult<StudentEntity> tryUpdate(Integer id, StudentEntity student) {
        if (!validator.validateFields(student)) {
            return new SingleResult<StudentEntity>(false, "Cannot validate the fields");
        }

        if (this.repository.update(id, student)) {
            student.setId(id);

            return new SingleResult<StudentEntity>(true, "Success", student);
        }

        return new SingleResult<StudentEntity>(false, "Cannot update the entity");
    }

    public SingleResult<StudentEntity> tryDelete(Integer id) {
        if (this.repository.delete(id)) {
            return new SingleResult<StudentEntity>(true, "Success");
        }

        return new SingleResult<StudentEntity>(false, "Cannot delete given id");
    }

    public void drop() {

    }
}
