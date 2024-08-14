package com.asm1.springboot.asm.controller;

import com.asm1.springboot.asm.entity.User;
import com.asm1.springboot.asm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
/**
 * Vì 1 số lỗi liên quan đến gửi email của google nên chưa thể thực hiện gửi email được
 * tham khảo và update sau....
 *
 *
 *
 * */

@Controller
public class MailController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserService userService;

    @PostMapping("/admin/sendMail")
    public String sendMail(@RequestParam("idUser") int userId,
                           @RequestParam("note") String note,
                            Model theModel) {
        // Lấy thông tin người dùng từ userId
        User user = userService.findById(userId);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail()); // Đặt địa chỉ email người nhận ở đây
            message.setSubject("Subject of Email"); // Đặt chủ đề email ở đây
            message.setText(note); // Đặt nội dung email ở đây
            javaMailSender.send(message);
            System.out.println("gửi thành công tới email: " + Arrays.toString(message.getTo()) + "nội dung" + note);
        } catch (MailException e) {
            theModel.addAttribute("errorMessage", "Lỗi xảy ra khi gửi email: " + e.getMessage());
        }

        return "redirect:/admin/home";
    }
}