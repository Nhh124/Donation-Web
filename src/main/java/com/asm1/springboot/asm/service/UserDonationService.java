package com.asm1.springboot.asm.service;

import com.asm1.springboot.asm.entity.UserDonation;
import com.asm1.springboot.asm.repo.UserDonationRepository;
import jakarta.persistence.Query;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Chịu trách nhiệm xử lý logic kinh doanh liên quan đến UserDonation.
 *
 * Interface này cung cấp các phương thức để thực hiện các tác vụ tương ứng với UserDonation, như tìm kiếm, thêm mới, cập nhật, xóa, và kiểm tra sự tồn tại của ID.
 */
@Service
public interface UserDonationService {

    List<UserDonation> findAll();

    UserDonation findById(int id);

     int getTotalDonationAmount(String id);

    List<UserDonation> findByDonationId(String id);

    UserDonation updateById(UserDonation userDonation);

    UserDonation save(UserDonation userDonation);
}
