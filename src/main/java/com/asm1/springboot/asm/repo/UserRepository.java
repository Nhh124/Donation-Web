package com.asm1.springboot.asm.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.asm1.springboot.asm.entity.User;


/**
 * Chịu trách nhiệm xử lý dữ liệu liên quan đến Donation trong cơ sở dữ liệu.
 *
 * Sử dụng JpaRepository để cung cấp các phương thức cơ bản để tương tác với cơ sở dữ liệu.
 * Đối tượng DonationRepository được ánh xạ với bảng User trong cơ sở dữ liệu.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);
    User findById(int id);
}
