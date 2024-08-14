package com.asm1.springboot.asm.repo;

import com.asm1.springboot.asm.entity.Donation;
import com.asm1.springboot.asm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Chịu trách nhiệm xử lý dữ liệu liên quan đến Donation trong cơ sở dữ liệu.
 *
 * Sử dụng JpaRepository để cung cấp các phương thức cơ bản để tương tác với cơ sở dữ liệu.
 * Đối tượng DonationRepository được ánh xạ với bảng Donation trong cơ sở dữ liệu.
 */
@Repository
public interface DonationRepository extends JpaRepository<Donation, Integer>  {

    Donation findById(String id);

    Donation deleteById(String id);

}
