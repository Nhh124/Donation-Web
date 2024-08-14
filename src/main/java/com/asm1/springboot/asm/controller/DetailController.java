package com.asm1.springboot.asm.controller;


import com.asm1.springboot.asm.entity.Donation;
import com.asm1.springboot.asm.entity.UserDonation;
import com.asm1.springboot.asm.repo.UserDonationRepository;
import com.asm1.springboot.asm.service.DonationService;
import com.asm1.springboot.asm.service.UserDonationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class DetailController {

    @Autowired
    private UserDonationService userDonationService;

    @Autowired
    private DonationService donationService;

    private CommonController commonController;

    public DetailController(CommonController commonController) {
        this.commonController = commonController;
    }

    /**
     * Xác nhận việc chấp nhận quyên góp từ UserDonation và cập nhật thông tin Donation tương ứng.
     *
     * @param idUDonation        ID của UserDonation.
     * @param idDonation         ID của Donation.
     * @param theModel           Đối tượng Model để truyền dữ liệu cho View.
     * @param totalAmountString  Chuỗi đại diện cho tổng số tiền quyên góp.
     * @return                   Chuỗi URL chuyển hướng đến trang hiển thị chi tiết Donation sau khi chấp nhận quyên góp.
     */
    @PostMapping("/showDetail/accept")
    public String acceptDonation(@RequestParam("idUserDonation") int idUDonation,
                                 @RequestParam("idDonation") String idDonation,
                                 Model theModel,
                                 @RequestParam("sendTotalAmount") String totalAmountString) {
        // Loại bỏ dấu phẩy và chữ 'VND'
        String cleanedTotalAmount = totalAmountString.replaceAll("\\D", "");

        // Chuyển đổi thành kiểu int
        int totalAmount = Integer.parseInt(cleanedTotalAmount);

        // Lấy thông tin UserDonation dựa trên ID
        UserDonation userDonation = userDonationService.findById(idUDonation);

        // Cập nhật trạng thái UserDonation thành đã chấp nhận (1)
        userDonation.setStatus(1);

        // Lấy thông tin Donation dựa trên ID UserDonation
        Donation donation = donationService.findById(userDonation.getDonation().getId());

        // Cập nhật số tiền quyên góp của Donation
        donation.setMoney(totalAmount);

        // Đặt thuộc tính idDnt cho theModel
        theModel.addAttribute("idDnt", idDonation);

        // Cập nhật thông tin Donation và UserDonation trong cơ sở dữ liệu
        donationService.update(donation);
        userDonationService.updateById(userDonation);

        return "redirect:/admin/showDetail?idDonation=" + idDonation + "&accept=true";
    }

    /**
     * Từ chối việc chấp nhận quyên góp từ UserDonation và cập nhật trạng thái tương ứng.
     *
     * @param idUDonation  ID của UserDonation.
     * @param idDonation   ID của Donation.
     * @param theModel     Đối tượng Model để truyền dữ liệu cho View.
     * @return             Chuỗi URL chuyển hướng đến trang hiển thị chi tiết Donation sau khi từ chối.
     */
    @PostMapping("/showDetail/denied")
    public String deniedDonation(@RequestParam("idUserDonation") int idUDonation,
                                 @RequestParam("idDonation") String idDonation
                                 ,Model theModel) {

        // Lấy thông tin UserDonation từ ID
        UserDonation userDonation = userDonationService.findById(idUDonation);

        // Cập nhật trạng thái UserDonation thành đã từ chối (2)
        userDonation.setStatus(2);

        // Cập nhật thông tin UserDonation trong cơ sở dữ liệu
        userDonationService.updateById(userDonation);

        // Đặt thuộc tính idDnt cho theModel
        theModel.addAttribute("idDnt", idDonation);

        return "redirect:/admin/showDetail?idDonation=" + idDonation + "&denied=true";
    }

    /**
     * Hiển thị trang chi tiết Donation dựa trên ID Donation.
     *
     * @param idDonation  ID của Donation để hiển thị chi tiết.
     * @param theModel    Đối tượng Model để truyền dữ liệu cho View.
     * @return            Tên View để hiển thị trang chi tiết Donation.
     */
    @GetMapping("/showDetail")
    public String showDetail(@RequestParam("idDonation") String idDonation, Model theModel){

        commonController.loadAllData(theModel,idDonation);
        theModel.addAttribute("idDnt",idDonation);

        return "admin/detail";
    }
}







