package com.pkrause.project2.domain;

import com.pkrause.project2.common.StringHelper;
import com.pkrause.project2.result.IResult;
import com.pkrause.project2.result.SingleResult;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@Entity(name = "student")
@NamedQuery(name = "StudentEntity.deleteAll", query = "DELETE FROM student WHERE 1=1")
public class StudentEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String secondName;
    private String surname;
    private Date birthday;
    private boolean removed;

    @OneToMany(mappedBy = "student", fetch = FetchType.EAGER)
    private List<GradeEntity> grades;

    //region GET SET
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public List<GradeEntity> getGrades() {
        return grades;
    }

    public void setGrades(List<GradeEntity> grades) {
        this.grades = grades;
    }
    //endregion

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEntity that = (StudentEntity) o;
        return removed == that.removed &&
                id.equals(that.id) &&
                name.equals(that.name) &&
                ((secondName == null && that.getSecondName() == null) || secondName.equals(that.secondName)) &&
                surname.equals(that.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, secondName, surname, birthday, removed);
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday=" + birthday +
                ", removed=" + removed +
                '}';
    }

    public IResult<StudentEntity> isValid() {
        StringBuilder msgBuilder = new StringBuilder();

        if (StringHelper.isNullOrEmpty(this.name)) {
            msgBuilder.append("Name cannot be blank.")
                    .append("\n");
        }

        if (StringHelper.isNullOrEmpty(this.surname)) {
            msgBuilder.append("Surname cannot be blank.")
                    .append("\n");
        }

        if (this.birthday == null) {
            msgBuilder.append("Birthday is required.")
                    .append("\n");
        }

        boolean success = msgBuilder.length() == 0;
        String message = msgBuilder.length() > 0 ? msgBuilder.toString() : "Valid";

        return new SingleResult<>(this, success, message);
    }
}
