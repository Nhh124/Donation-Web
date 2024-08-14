package com.asm1.springboot.asm.entity;

import jakarta.persistence.*;


/**
 * Đại diện cho một User trong hệ thống.
 * Bao gồm các thuộc tính trong cơ sở dữ liệu
 */
@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "email")
	private String email;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "note")
	private String note;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "created")
	private String created;

	@Column(name = "status")
	private int status;

	@Column(name="address")
	private String address;

	/**
	 * Khóa ngoại giữa 2 bảng User và Role.
	 *
	 * Mối quan hệ Many-to-One với entity Role thông qua trường role_id trong bảng donation.
	 * Sử dụng chiến lược fetch LAZY để tải lazily thông tin Role khi cần thiết.
	 * Sử dụng trường role_id để tham chiếu tới primary key trong bảng Role.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role role;

//	private int IdRole;
//
//	public Integer getRoleId() {
//		return IdRole;
//	}
//
//	public void setRoleId(Integer IdRole) {
//		this.IdRole = IdRole;
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User() {
		super();
	}

	public User(int id, String email, String fullName,
				String note, String phoneNumber,
				String userName, String password,
				String created, int status, String address, Role role) {
		this.id = id;
		this.email = email;
		this.fullName = fullName;
		this.note = note;
		this.phoneNumber = phoneNumber;
		this.userName = userName;
		this.password = password;
		this.created = created;
		this.status = status;
		this.address = address;
		this.role = role;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", email='" + email + '\'' +
				", fullName='" + fullName + '\'' +
				", note='" + note + '\'' +
				", phoneNumber='" + phoneNumber + '\'' +
				", userName='" + userName + '\'' +
				", password='" + password + '\'' +
				", created='" + created + '\'' +
				", status=" + status +
				", address=" + address +
				", role=" + role +
				'}';
	}
}
