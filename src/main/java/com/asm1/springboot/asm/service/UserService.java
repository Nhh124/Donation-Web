package com.asm1.springboot.asm.service;

import java.util.List;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import com.asm1.springboot.asm.entity.User;
import org.springframework.ui.Model;


/**
 * Chịu trách nhiệm xử lý logic kinh doanh liên quan đến User.
 *
 * Interface này cung cấp các phương thức để thực hiện các tác vụ tương ứng với User, như tìm kiếm, thêm mới, cập nhật, xóa, và kiểm tra sự tồn tại của ID.
 */
@Service
public interface UserService {
	List<User> findAll();

	User getUserByEmail(String email);

	 User addUser(User user);

	 User updateUser(User user);

	void deleteUserById(int id);

	User findById(int userId);

    boolean isEmailExists(String email);

	boolean isPhoneNumberExists(String phoneNumber);

}
