package com.asm1.springboot.asm.entity;

import jakarta.persistence.*;


/**
 * Đại diện cho một Donation trong hệ thống.
 * Bao gồm các thuộc tính trong cơ sở dữ liệu
 */

@Entity
@Table(name = "donation")
public class Donation {
    @Id
    private String id;

    @Column(name = "created")
    private String created;

    @Column(name = "description")
    private String description;

    @Column(name = "end_date")
    private String endDate;

    @Column(name = "start_date")
    private String startDate;

    @Column(name = "money")
    private int money;

    @Column(name = "status")
    private int status;

    @Column(name = "organization_name")
    private String organizationName;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "target")
    private int target;

    public Donation() {
    }
    public Donation(String id, String created, String description,
                    String endDate, String startDate, int money,
                    int status, String organizationName, String name,
                    String phoneNumber,int target) {
        this.id = id;
        this.created = created;
        this.description = description;
        this.endDate = endDate;
        this.startDate = startDate;
        this.money = money;
        this.status = status;
        this.organizationName = organizationName;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.target = target;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }



    @Override
    public String toString() {
        return "Donation{" +
                "id=" + id +
                ", created='" + created + '\'' +
                ", description='" + description + '\'' +
                ", endDate='" + endDate + '\'' +
                ", startDate='" + startDate + '\'' +
                ", money=" + money +
                ", status=" + status +
                ", organizationName='" + organizationName + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
