package com.asm1.springboot.asm.entity;

import jakarta.persistence.*;


/**
 * Đại diện cho một UserDonation trong hệ thống.
 * Bao gồm các thuộc tính trong cơ sở dữ liệu
 */
@Entity
@Table(name = "user_donation")
public class UserDonation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "created")
    private String created;

    @Column(name = "money")
    private int money;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private int status;

    @Column(name = "text")
    private String text;

    /**
     * User tồn tại trong Donation.
     *
     * Mối quan hệ Many-to-One với entity User thông qua trường user_id trong bảng donation.
     * Sử dụng chiến lược fetch LAZY để tải lazily thông tin User khi cần thiết.
     * Sử dụng trường user_id để tham chiếu tới primary key trong bảng User.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Thuộc tính donation

    /**
     *
     * Mối quan hệ Many-to-One với entity Donation thông qua trường donation_id trong bảng donation.
     * Sử dụng chiến lược fetch LAZY để tải lazily thông tin Donation khi cần thiết.
     * Sử dụng trường donation_id để tham chiếu tới primary key trong bảng Donation.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donation_id")
    private Donation donation;

    public UserDonation() {
    }

    public UserDonation(int id, String created, int money,
                        String name, int status, String text,
                        User user, Donation donation) {
        this.id = id;
        this.created = created;
        this.money = money;
        this.name = name;
        this.status = status;
        this.text = text;
        this.user = user;
        this.donation = donation;
    }

    // Các phương thức getters và setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Donation getDonation() {
        return donation;
    }

    public void setDonation(Donation donation) {
        this.donation = donation;
    }

    @Override
    public String toString() {
        return "UserDonation{" +
                "id=" + id +
                ", created='" + created + '\'' +
                ", money=" + money +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", donation=" + donation +
                '}';
    }
}
