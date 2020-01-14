package domain;

import java.sql.Date;

public class StudentEntity implements IEntity {
    private Integer id;
    private Integer classId;
    private String name;
    private String secondName;
    private String surname;
    private String parentNames;
    private String phoneNumber;
    private Date birthDate;
    private String pesel;
    private String street;
    private String houseNumber;

    public StudentEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
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

    public String getParentNames() {
        return parentNames;
    }

    public void setParentNames(String parentNames) {
        this.parentNames = parentNames;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public boolean equals(Object studentE) {
        if (!(studentE instanceof StudentEntity)) {
            return false;
        }

        StudentEntity studentEntity = (StudentEntity) studentE;

        return this.classId.equals(studentEntity.getClassId()) &&
                this.name.equals(studentEntity.getName()) &&
                this.secondName.equals(studentEntity.getSecondName()) &&
                this.surname.equals(studentEntity.getSurname()) &&
                this.birthDate.toString().equals(studentEntity.getBirthDate().toString()) &&
                this.pesel.equals(studentEntity.getPesel()) &&
                this.street.equals(studentEntity.getStreet()) &&
                this.houseNumber.equals(studentEntity.getHouseNumber());
    }
}
