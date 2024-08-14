package com.asm1.springboot.asm.service;

import java.util.List;

import com.asm1.springboot.asm.controller.CommonController;
import com.asm1.springboot.asm.entity.Donation;
import com.asm1.springboot.asm.entity.UserDonation;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asm1.springboot.asm.entity.User;
import com.asm1.springboot.asm.repo.UserRepository;
import org.springframework.ui.Model;


/**
 * Implement Service cho  UserService.
 *
 * Class này triển khai các phương thức được định nghĩa trong interface  UserService
 * và  UserRepository
 * để xử lý logic kinh doanh liên quan đến Donation.
 */
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDonationService userDonationService;

	@Autowired
	private DonationService donationService;


	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User addUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public void deleteUserById(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public User findById(int userId) {
		return userRepository.findById(userId);
	}

	@Override
	public boolean isEmailExists(String email) {
		List<User> users = findAll();
		for (User user : users){
			if (user.getEmail().equals(email)){
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isPhoneNumberExists(String phoneNumber) {
		List<User> users = findAll();
		for (User user : users){
			if (user.getPhoneNumber().equals(phoneNumber)){
				return true;
			}
		}
		return false;
	}


}
