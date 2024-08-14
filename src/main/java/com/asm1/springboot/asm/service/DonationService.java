package com.asm1.springboot.asm.service;

import com.asm1.springboot.asm.entity.Donation;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Chịu trách nhiệm xử lý logic kinh doanh liên quan đến Donation.
 *
 * Interface này cung cấp các phương thức để thực hiện các tác vụ tương ứng với Donation, như tìm kiếm, thêm mới, cập nhật, xóa, và kiểm tra sự tồn tại của ID.
 */
@Service
public interface DonationService {

    List<Donation> findAll();

    Donation findById(String id);

    Donation add(Donation donation);

    Donation update(Donation donation);

    void deleteById(String id);

    boolean isIdExists(String id);
}
