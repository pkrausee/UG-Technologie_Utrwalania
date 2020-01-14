package com.pkrause.project2.domain;

import com.pkrause.project2.common.StringHelper;
import com.pkrause.project2.result.IResult;
import com.pkrause.project2.result.SingleResult;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@Entity(name = "grade")
@NamedQuery(name = "GradeEntity.deleteAll", query = "DELETE FROM grade WHERE 1=1")
public class GradeEntity {
    @Id
    @GeneratedValue
    private UUID id;

    private double grade;
    private double wage;
    private String description;
    private Timestamp created;

    @ManyToOne(fetch = FetchType.EAGER)
    private StudentEntity student;

    @ManyToOne(fetch = FetchType.EAGER)
    private SubjectEntity subject;

    //region GET SET
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getWage() {
        return wage;
    }

    public void setWage(double wage) {
        this.wage = wage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public SubjectEntity getSubject() {
        return subject;
    }

    public void setSubject(SubjectEntity subject) {
        this.subject = subject;
    }
    //endregion

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GradeEntity that = (GradeEntity) o;
        return that.grade == grade &&
                that.wage == wage &&
                id.equals(that.id) &&
                description.equals(that.description) &&
                created.equals(that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, grade, wage, description, created);
    }

    @Override
    public String toString() {
        return "GradeEntity{" +
                "id=" + id +
                ", grade=" + grade +
                ", wage=" + wage +
                ", description='" + description + '\'' +
                ", created=" + created +
                '}';
    }

    public IResult<GradeEntity> isValid() {
        StringBuilder msgBuilder = new StringBuilder();

        if (this.grade <= 1.0 || this.grade >= 6.0) {
            msgBuilder.append("Grade must be between 1.0 and 6.0.")
                    .append("\n");
        }

        if (this.wage <= 0.0) {
            msgBuilder.append("Wage must be greater than 0.0.")
                    .append("\n");
        }

        if (StringHelper.isNullOrEmpty(this.description)) {
            msgBuilder.append("Description cannot be blank.")
                    .append("\n");
        }

        if (this.created == null) {
            msgBuilder.append("Creation date is required.")
                    .append("\n");
        }

        boolean success = msgBuilder.length() == 0;
        String message = msgBuilder.length() > 0 ? msgBuilder.toString() : "Valid";

        return new SingleResult<>(this, success, message);
    }
}
