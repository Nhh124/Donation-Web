package com.asm1.springboot.asm.repo;

import com.asm1.springboot.asm.entity.Role;
import com.asm1.springboot.asm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Chịu trách nhiệm xử lý dữ liệu liên quan đến Donation trong cơ sở dữ liệu.
 *
 * Sử dụng JpaRepository để cung cấp các phương thức cơ bản để tương tác với cơ sở dữ liệu.
 * Đối tượng DonationRepository được ánh xạ với bảng Role trong cơ sở dữ liệu.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findById(int id);
}