package com.pkrause.project2.domain;

import com.pkrause.project2.common.StringHelper;
import com.pkrause.project2.result.IResult;
import com.pkrause.project2.result.SingleResult;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@Entity(name = "subject")
@NamedQuery(name = "SubjectEntity.deleteAll", query = "DELETE FROM subject WHERE 1=1")
public class SubjectEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String range;
    private Timestamp created;
    private Timestamp updated;
    private boolean removed;

    @OneToMany(mappedBy = "subject", fetch = FetchType.EAGER)
    private List<GradeEntity> grades;

    //region GET SET
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
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
        SubjectEntity that = (SubjectEntity) o;
        return removed == that.removed &&
                id.equals(that.id) &&
                name.equals(that.name) &&
                range.equals(that.range) &&
                created.equals(that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, range, created, updated, removed);
    }

    @Override
    public String toString() {
        return "SubjectEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", range='" + range + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", removed=" + removed +
                '}';
    }

    public IResult<SubjectEntity> isValid() {
        StringBuilder msgBuilder = new StringBuilder();

        if (StringHelper.isNullOrEmpty(this.name)) {
            msgBuilder.append("Name cannot be blank.")
                    .append("\n");
        }

        if (StringHelper.isNullOrEmpty(this.range)) {
            msgBuilder.append("Range cannot be blank.")
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

