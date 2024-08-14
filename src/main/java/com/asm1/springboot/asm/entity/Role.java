package com.asm1.springboot.asm.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Đại diện cho một Role trong hệ thống.
 * Bao gồm các thuộc tính trong cơ sở dữ liệu
 */
@Entity
@Table(name = "role")
public class Role {

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "role_name")
	private String roleName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "role", cascade = CascadeType.ALL)
	List<User> users;

	public Role() {
		super();
	}

	public Role(int id, String roleName) {
		super();
		this.id = id;
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", roleName=" + roleName + "]";
	}

}
