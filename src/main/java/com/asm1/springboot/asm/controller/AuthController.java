package com.asm1.springboot.asm.controller;

import com.asm1.springboot.asm.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.asm1.springboot.asm.service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {
	@Autowired
	private UserService userService;

	private CommonController commonController;

	/**
	 * Lớp AuthController để xử lý các yêu cầu liên quan đến xác thực người dùng.
	 *
	 * @param commonController Đối tượng CommonController để sử dụng các phương thức chung.
	 */

	public AuthController(CommonController commonController) {
		this.commonController = commonController;
	}


	/**
	 * Xử lý yêu cầu GET gửi đến "/login" để truy cập trang đăng nhập admin.
	 *
	 * @param theModel Đối tượng Model để cung cấp dữ liệu cho View Template.
	 * @return Trả về tên của View Template "admin/login".
	 *         Đặt giá trị ban đầu của thuộc tính "error" trong theModel là false.
	 */
	@GetMapping("/login")
	public String home(Model theModel) {

		theModel.addAttribute("error", false); // Đặt giá trị error ban đầu là false

		return "admin/login";

	}

	/**
	 * Xử lý yêu cầu POST gửi đến "/login" để xác thực thông tin đăng nhập và điều hướng người dùng đến các trang tương ứng.
	 *
	 * @param email     Email đăng nhập được cung cấp trong yêu cầu POST.
	 * @param password  Mật khẩu đăng nhập được cung cấp trong yêu cầu POST.
	 * @param session   Đối tượng HttpSession để lưu trữ dữ liệu trạng thái người dùng.
	 * @param theModel  Đối tượng Model để cung cấp dữ liệu cho View Template.
	 * @return Trả về tên của View Template tương ứng với trang hợp lệ sau khi xử lý yêu cầu đăng nhập.
	 *         Nếu đăng nhập thành công với vai trò là "admin", chuyển hướng người dùng đến "admin/home".
	 *         Nếu đăng nhập thành công với vai trò là người dùng, chuyển hướng người dùng đến "public/home".
	 *         Nếu thông tin đăng nhập không hợp lệ hoặc người dùng bị khóa, trả về trang "admin/login" với thông báo lỗi tương ứng.
	 *         Trước khi chuyển hướng, tải dữ liệu người dùng và vai trò sử dụng phương thức loadAllData() trong commonController.
	 */
	@PostMapping("/login")
	public String processLogin(@RequestParam("email") String email,
							   @RequestParam("password") String password,
								HttpSession session,
							    Model theModel
							  ) {
		User user = userService.getUserByEmail(email);

		if (user.getStatus() == 1){
			if (user.getPassword().equals(password)) {
				if (user.getRole().getRoleName().equals("admin")) {
					session.setAttribute("admin",user);
					User sessionAdmin = (User) session.getAttribute("admin");
					theModel.addAttribute("sessionAdmin",sessionAdmin);
					return "admin/home";
				} else {
					session.setAttribute("user", user);
					User sessionUser = (User) session.getAttribute("user");
					theModel.addAttribute("sessionUser",sessionUser);
					commonController.loadAllData(theModel,"");
					return "public/home";
				}
			} else {
				theModel.addAttribute("error", true);
				return "admin/login";
			}
		} else {
			theModel.addAttribute("errorLock", true);
			return "admin/login";
		}
	}

	/**Xử lý yêu cầu GET gửi đến "/logout"
	 *   để đăng xuất người dùng admin bằng cách xóa thuộc tính "admin" trong session
	 *   và chuyển hướng người dùng đến trang đăng nhập "admin/login".
	 */
	@GetMapping("/logout")
	public String logout(){
		return "admin/login";
	}

}
