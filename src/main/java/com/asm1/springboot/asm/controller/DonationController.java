package com.asm1.springboot.asm.controller;

import com.asm1.springboot.asm.entity.Donation;

import com.asm1.springboot.asm.service.DonationService;
import com.asm1.springboot.asm.service.UserDonationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;


import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Controller
@RequestMapping("/admin")
public class DonationController {

    @Autowired
    DonationService donationService;

    @Autowired
    UserDonationService userDonationService;

    private CommonController commonController;

    public DonationController(CommonController commonController) {
        this.commonController = commonController;
    }

    private final LocalDate today = LocalDate.now();

    /**
     * Xử lý trang quản lý Donation.
     *
     * @param theModel  Đối tượng Model để truyền dữ liệu cho View.
     * @return          Tên View để hiển thị trang quản lý Donation.
     */
    @GetMapping("/donation")
    public String processDonation(Model theModel){

        commonController.loadAllData(theModel,"");

        return "admin/donation";
    }

    /**
     * Thêm Donation mới vào hệ thống.
     *
     * @param donation   Đối tượng Donation để thêm vào hệ thống.
     * @param theModel   Đối tượng Model để truyền dữ liệu cho View.
     * @return           Chuỗi URL chuyển hướng đến trang quản lý Donation sau khi thêm thành công hoặc xảy ra lỗi.
     */
    @PostMapping("/saveDonation")
    public String addDonation(@ModelAttribute("donation") Donation donation, Model theModel) {
        // Kiểm tra xem ID đã tồn tại chưa
        boolean isIdExists = donationService.isIdExists(donation.getId());

        if (isIdExists) {
            // Nếu ID đã tồn tại, chuyển hướng đến trang quản lý Donation với thông báo lỗi
            return "redirect:/admin/donation?error=true";
        } else {
            // Nếu ID chưa tồn tại
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter dateFormatte = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate startDate = LocalDate.parse(donation.getStartDate(), dateFormatter);
            String formattedStartDate = startDate.format(dateFormatte);

            LocalDate endDate = LocalDate.parse(donation.getEndDate(), dateFormatter);
            String formattedEndDate = endDate.format(dateFormatte);

            // Đặt ngày bắt đầu, ngày kết thúc và ngày tạo định dạng theo đúng định dạng ngày tháng
            donation.setStartDate(formattedStartDate);
            donation.setEndDate(formattedEndDate);
            donation.setCreated(today.format(dateFormatte));

            // Thêm Donation vào hệ thống
            donationService.add(donation);
            return "redirect:/admin/donation?success=true";
        }

    }

    /**
     * Cập nhật thông tin của một Donation trong hệ thống.
     *
     * @param donationId  ID của Donation để cập nhật thông tin.
     * @param theModel    Đối tượng Model để truyền dữ liệu cho View.
     * @return            Chuỗi URL chuyển hướng đến trang quản lý Donation sau khi cập nhật thành công.
     */
    @PostMapping("/updateDonation")
    public String updateDonation(@RequestParam("idDonation") String donationId, Model theModel){

        Donation donation = donationService.findById(donationId);
        if (donation.getStatus() < 3){
            // Nếu trạng thái của Donation nhỏ hơn 3, cập nhật thông tin Donation trong hệ thống
            donationService.update(donation);
        }
        return "redirect:/admin/donation?updateDonation=true";
    }

    /**
     * Cập nhật thông tin của một Donation trong hệ thống.
     *
     * @param donationId  ID của Donation để cập nhật thông tin.
     * @param theModel    Đối tượng Model để truyền dữ liệu cho View.
     * @return            Chuỗi URL chuyển hướng đến trang quản lý Donation sau khi cập nhật thành công.
     */
    @PostMapping("/deleteDonation")
    public String deleteDonation(@RequestParam("idDonation") String donationId, Model theModel){

       Donation donation = donationService.findById(donationId);

       //Khi thực hiện nút xóa đặt trạng thái là 4 đồng thời ẩn khi tải lại trang
       donation.setStatus(4);

       commonController.loadAllData(theModel,donationId);
       return "redirect:/admin/donation?deleteDonation=true";
    }


    @PostMapping("/donation/end")
    public String endDonation(@RequestParam("idE") String donationId, Model theModel) {
        Donation donation = donationService.findById(donationId);

        //Cập nhập ngày kết thúc là hôm này đồng thời kết thúc đợt quyên góp
        donation.setEndDate(today.toString());

        donationService.update(donation);
        return "redirect:/admin/donation?endDonation=true";
    }

    @PostMapping("/donation/close")
    public String closeDonation(@RequestParam("idC") String donationId, Model theModel) {
        Donation donation = donationService.findById(donationId);
        //Cập nhập status = 3 đồng thời đóng đợt quyên góp
        donation.setStatus(3);
        donationService.update(donation);

        return "redirect:/admin/donation/lockDonation=true";
    }


}
