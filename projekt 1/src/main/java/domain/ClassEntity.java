package domain;

import java.sql.Date;

public class ClassEntity implements IEntity {
    private Integer id;
    private Integer presidentId;
    private Date startDate;
    private Date endDate;
    private String profile;
    private Boolean special;

    public ClassEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public Integer getPresidentId() {
        return presidentId;
    }

    public void setPresidentId(Integer president) {
        this.presidentId = president;
    }

    public Boolean isSpecial() {
        return special;
    }

    public void setSpecial(Boolean special) {
        this.special = special;
    }

    @Override
    public boolean equals(Object classE) {
        if (!(classE instanceof ClassEntity)) {
            return false;
        }

        ClassEntity classEntity = (ClassEntity) classE;

        return this.startDate.toString().compareTo(classEntity.getStartDate().toString()) == 0 &&
                this.endDate.toString().compareTo(classEntity.getEndDate().toString()) == 0 &&
                this.profile.compareTo(classEntity.getProfile()) == 0 &&
                this.presidentId.compareTo(classEntity.getPresidentId()) == 0 &&
                this.special == classEntity.isSpecial();
    }
}
