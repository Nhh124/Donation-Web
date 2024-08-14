package com.asm1.springboot.asm.controller;

import com.asm1.springboot.asm.entity.Donation;
import com.asm1.springboot.asm.entity.Role;
import com.asm1.springboot.asm.entity.User;
import com.asm1.springboot.asm.entity.UserDonation;
import com.asm1.springboot.asm.service.DonationService;
import com.asm1.springboot.asm.service.RoleService;
import com.asm1.springboot.asm.service.UserDonationService;
import com.asm1.springboot.asm.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/public")
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserDonationService userDonationService;

	@Autowired
	private DonationService donationService;

	private CommonController commonController;

	public UserController(CommonController commonController) {
		this.commonController = commonController;
	}

	/**
	 * Hiển thị trang chủ của ứng dụng.
	 *
	 * @param theModel  Đối tượng Model để truyền dữ liệu cho View.
	 * @return          Tên View để hiển thị trang chủ.
	 */
	@GetMapping("/home")
	public String listUser(Model theModel) {
		commonController.loadAllData(theModel,"");
		return "public/home";
	}

	/**
	 * Thêm UserDonation mới vào hệ thống.
	 *
	 * @param userDonation  Đối tượng UserDonation để thêm vào hệ thống.
	 * @param theModel      Đối tượng Model để truyền dữ liệu cho View.
	 * @param idDonation    ID của Donation để kết nối với UserDonation.
	 * @param session       Đối tượng HttpSession để lưu trữ thông tin người dùng.
	 * @param idUser        ID của User để kết nối với UserDonation.
	 * @return              Tên View để hiển thị trang chủ sau khi thêm UserDonation thành công.
	 */
	@PostMapping("/save")
	public String addUserDonation(@ModelAttribute("userDonation")UserDonation userDonation,
							  Model theModel,
							  @RequestParam("idDonation") String idDonation,
							  HttpSession session,
							  @RequestParam("idUser") int idUser){
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate today = LocalDate.now();

		// Đặt ngày tạo của UserDonation thành ngày hiện tại theo đúng định dạng
		userDonation.setCreated(dateFormatter.format(today));

		// Đặt trạng thái của UserDonation thành 0 (chưa xử lý)
		userDonation.setStatus(0);

		// Kết nối UserDonation với Donation dựa trên ID Donation
		userDonation.setDonation(donationService.findById(idDonation));

		// Lấy thông tin User từ ID User
		User user = userService.findById(idUser);

		// Kết nối UserDonation với User
		userDonation.setUser(user);

		// Lưu UserDonation vào hệ thống
		userDonationService.save(userDonation);

		// Lưu thông tin người dùng vào session
		session.setAttribute("user", user);

		// Lấy thông tin người dùng từ session
		User sessionUser = (User) session.getAttribute("user");

		// Đặt thuộc tính sessionUser cho theModel để truyền thông tin người dùng tới View
		theModel.addAttribute("sessionUser",sessionUser);

		// Đặt thuộc tính msg cho theModel để hiển thị thông báo "Donate Successfully!"
		theModel.addAttribute("msg", "Donate Successfully!");

		// Gọi phương thức loadAllData() từ commonController để tải dữ liệu chung cho trang chủ
		commonController.loadAllData(theModel, "");

		return "public/home";
	}

	/**
	 * Hiển thị chi tiết Donation cho một User.
	 *
	 * @param donationId  ID của Donation để hiển thị chi tiết.
	 * @param userId      ID của User để hiển thị thông tin người dùng.
	 * @param session     Đối tượng HttpSession để lưu trữ thông tin người dùng.
	 * @param theModel    Đối tượng Model để truyền dữ liệu cho View.
	 * @return            Tên View để hiển thị chi tiết Donation.
	 */
	@GetMapping("detail/{donationId}/{userId}")
	public String viewDonationDetail(@PathVariable("donationId") String donationId,
									 @PathVariable("userId") int userId,
									 HttpSession session,
									 Model theModel) {
		// Lấy thông tin User từ ID User
		User user = userService.findById(userId);

		// Lưu thông tin User vào session
		session.setAttribute("user", user);

		// Lấy thông tin người dùng từ session
		User sessionUser = (User) session.getAttribute("user");

		// Đặt thuộc tính sessionUser cho theModel để truyền thông tin người dùng tới View
		theModel.addAttribute("sessionUser", sessionUser);

		// Đặt thuộc tính userM cho theModel để truyền thông tin User tới View
		theModel.addAttribute("userM", user);

		// Gọi phương thức loadAllData() từ commonController để tải dữ liệu chung cho trang chi tiết Donation
		commonController.loadAllData(theModel, donationId);
		return "public/detail";
	}

	/**
	 * Đăng xuất người dùng admin.
	 *
	 * @param session  Đối tượng HttpSession để xóa thông tin đăng nhập admin khỏi session.
	 * @return         Tên View để hiển thị trang đăng nhập admin sau khi đăng xuất thành công.
	 */
	@GetMapping("/logout")
	public String adminLogout(HttpSession session) {
		// Xóa thông tin đăng nhập admin khỏi session
		session.removeAttribute("sessionUser");

		return "admin/login";
	}

}
