package services;

import domain.ClassEntity;
import repository.IRepository;
import response.ManyResults;
import response.SingleResult;
import validator.ClassValidator;

import java.sql.SQLException;
import java.util.List;

public class ClassService implements IService<ClassEntity, Integer> {
    private ClassValidator validator;
    private IRepository<ClassEntity, Integer> repository;

    public ClassService(IRepository<ClassEntity, Integer> repository, ClassValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public SingleResult<ClassEntity> tryCreate(ClassEntity classEntity) {
        if (!validator.validateFields(classEntity)) {
            return new SingleResult<ClassEntity>(false, "Cannot validate the fields");
        }

        if (this.repository.create(classEntity)) {
            return new SingleResult<ClassEntity>(true, "Success", classEntity);
        }

        return new SingleResult<ClassEntity>(false, "Cannot create the entities");
    }

    public ManyResults<ClassEntity> tryCreateMany(List<ClassEntity> classEntities) {
        try {
            this.repository.getConnection().setAutoCommit(false);

            for (ClassEntity classEntity : classEntities) {
                if (!validator.validateFields(classEntity)) {
                    return new ManyResults<ClassEntity>(false, "Cannot validate the fields");
                }

                if (!this.repository.create(classEntity)) {
                    return new ManyResults<ClassEntity>(false, "Cannot create the entities");
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

        return new ManyResults<ClassEntity>(true, "Success", classEntities);
    }

    public SingleResult<ClassEntity> readByKey(Integer key) {
        ClassEntity readResult = this.repository.readByKey(key);

        if (readResult != null) {
            return new SingleResult<ClassEntity>(true, "Success", readResult);
        } else {
            return new SingleResult<ClassEntity>(false, "Cannot find given id", null);
        }
    }

    public ManyResults<ClassEntity> readAll() {
        List<ClassEntity> readResult = this.repository.readAll();

        if (readResult.size() > 0) {
            return new ManyResults<ClassEntity>(true, "Success", readResult);
        } else {
            return new ManyResults<ClassEntity>(false, "Cannot find any entities", null);
        }
    }

    public SingleResult<ClassEntity> tryUpdate(Integer id, ClassEntity classEntity) {
        if (!validator.validateFields(classEntity)) {
            return new SingleResult<ClassEntity>(false, "Cannot validate the fields");
        }

        if (this.repository.update(id, classEntity)) {
            classEntity.setId(id);

            return new SingleResult<ClassEntity>(true, "Success", classEntity);
        }

        return new SingleResult<ClassEntity>(false, "Cannot update the entity");
    }

    public SingleResult<ClassEntity> tryDelete(Integer id) {
        if (this.repository.delete(id)) {
            return new SingleResult<ClassEntity>(true, "Success");
        }

        return new SingleResult<ClassEntity>(false, "Cannot delete given id");
    }

    public void drop() {
        this.repository.drop();
    }
}
