package com.asm1.springboot.asm.controller;

import com.asm1.springboot.asm.entity.Role;
import com.asm1.springboot.asm.entity.User;
import com.asm1.springboot.asm.service.RoleService;
import com.asm1.springboot.asm.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    /** Xử lý yêu cầu GET gửi đến "/" và chuyển hướng người dùng đến trang "admin/home". */
    @GetMapping("/")
    public String adminHome() {

        return "admin/home";
    }

    /** Xử lý yêu cầu GET gửi đến "/home" và chuyển hướng người dùng đến trang "admin/home".*/
    @GetMapping("/home")
    public String home(){
        return "admin/home";
    }

    /**Xử lý yêu cầu GET gửi đến "/logout"
     *   để đăng xuất người dùng admin bằng cách xóa thuộc tính "admin" trong session
     *   và chuyển hướng người dùng đến trang đăng nhập "admin/login".
     */
    @GetMapping("/logout")
    public String adminLogout(HttpSession session) {
        session.removeAttribute("admin");

        return "admin/login";
    }

    /**
     * Xử lý yêu cầu GET gửi đến "/account" để xử lý tài khoản admin,
     * bao gồm tải dữ liệu và chuyển đến trang "admin/account" để hiển thị.*/
    @GetMapping("/account")
    public String processAccount(Model theModel){

       loadAll(theModel);

        return "admin/account";
    }

    /**
     * Xử lý yêu cầu POST gửi đến "/save" để thêm người dùng vào hệ thống.
     *
     * @param user Đối tượng User chứa thông tin người dùng cần thêm.
     * @return Chuyển hướng người dùng đến trang "admin/account" với thông báo thành công hoặc thất bại.
     *         Nếu email hoặc số điện thoại đã tồn tại, chuyển hướng đến "admin/account?error=true".
     *         Ngược lại, thêm người dùng vào hệ thống, đặt trạng thái là 1 và chuyển hướng đến "admin/account?success=true".
     */
    @PostMapping("/save")
    public String addUser(@ModelAttribute("user") User user) {

        if (userService.isEmailExists(user.getEmail()) && userService.isPhoneNumberExists(user.getPhoneNumber())) {
            return "redirect:/admin/account?error=true";
        } else {
            user.setStatus(1);
            userService.addUser(user);
            return "redirect:/admin/account?success=true";
        }
    }

    /**
     * Xử lý yêu cầu POST gửi đến "/update" để cập nhật thông tin người dùng trong hệ thống.
     *
     * @param userId   ID của người dùng cần cập nhật.
     * @param user     Đối tượng User chứa thông tin người dùng cần cập nhật.
     * @param theModel Đối tượng Model để cung cấp dữ liệu cho View Template.
     * @return Chuyển hướng người dùng đến trang "admin/account" với thông báo cập nhật thành công.
     *         Trước khi cập nhật, tìm và gán vai trò tương ứng cho người dùng.
     *         Sau đó, cập nhật thông tin người dùng và tải lại dữ liệu người dùng.
     */
    @PostMapping("/update")
    public String updateUser(@RequestParam("idUser") int userId,
                             @ModelAttribute("user") User user,
                             Model theModel) {

        Role role = roleService.findById(user.getRole().getId());

        user.setId(userId);

        user.setRole(role);
        
        userService.updateUser(user);

        loadAll(theModel);

        return "redirect:/admin/account?updateSuccess=true";
    }

    /**
     * Xử lý yêu cầu POST gửi đến "/delete" để xóa người dùng khỏi hệ thống.
     *
     * @param userId   ID của người dùng cần xóa.
     * @param theModel Đối tượng Model để cung cấp dữ liệu cho View Template.
     * @return Chuyển hướng người dùng đến trang "admin/account" với thông báo xóa người dùng thành công.
     *         Xóa người dùng khỏi hệ thống và tải lại dữ liệu người dùng.
     */
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("idUser") int userId, Model theModel) {


        userService.deleteUserById(userId);

        loadAll(theModel);

        return "redirect:/admin/account?deleteUser=true";
    }

    /**
     * Xử lý yêu cầu POST gửi đến "/lock" để khóa người dùng trong hệ thống.
     *
     * @param userId   ID của người dùng cần khóa.
     * @param theModel Đối tượng Model để cung cấp dữ liệu cho View Template.
     * @return Chuyển hướng người dùng đến trang "admin/account" với thông báo khóa người dùng thành công.
     *         Tìm người dùng dựa trên ID, đặt trạng thái của người dùng thành 0 để khóa,
     *         sau đó cập nhật thông tin người dùng và tải lại dữ liệu người dùng.
     */
    @PostMapping("/lock")
    public String lockUser(@RequestParam("idUser") int userId, Model theModel) {
        User user = userService.findById(userId);
        user.setStatus(0);
        userService.updateUser(user);

        loadAll(theModel);

        return "redirect:/admin/account?unlock=true";
    }

    /**
     * Xử lý yêu cầu POST gửi đến "/unlock" để khóa người dùng trong hệ thống.
     *
     * @param userId   ID của người dùng cần khóa.
     * @param theModel Đối tượng Model để cung cấp dữ liệu cho View Template.
     * @return Chuyển hướng người dùng đến trang "admin/account" với thông báo khóa người dùng thành công.
     *         Tìm người dùng dựa trên ID, đặt trạng thái của người dùng thành 1 để mở khóa,
     *         sau đó cập nhật thông tin người dùng và tải lại dữ liệu người dùng.
     */
    @PostMapping("/unlock")
    public String unlockUser(@RequestParam("idUser") int userId, Model theModel) {
        User user = userService.findById(userId);
        user.setStatus(1);
        userService.updateUser(user);
        theModel.addAttribute("status",user.getStatus());
        loadAll(theModel);

        return "redirect:/admin/account?unlock=false";
    }


    /**
     * Tải tất cả dữ liệu người dùng và vai trò từ các dịch vụ tương ứng
     * và thêm chúng vào đối tượng Model để cung cấp dữ liệu cho View Template.
     *
     * @param theModel Đối tượng Model để lưu trữ dữ liệu người dùng và vai trò.
     */
    public void loadAll(Model theModel){

        List<User> theUser = userService.findAll();

        List<Role> theRole = roleService.findAll();

        theModel.addAttribute("user",new User());

        theModel.addAttribute("userList", theUser);

        theModel.addAttribute("role",theRole);

    }

}
