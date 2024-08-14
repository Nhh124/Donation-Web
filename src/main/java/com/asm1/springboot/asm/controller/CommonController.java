package com.asm1.springboot.asm.controller;

import com.asm1.springboot.asm.entity.Donation;
import com.asm1.springboot.asm.entity.User;
import com.asm1.springboot.asm.entity.UserDonation;
import com.asm1.springboot.asm.service.DonationService;
import com.asm1.springboot.asm.service.UserDonationService;
import com.asm1.springboot.asm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CommonController {
    @Autowired
    DonationService donationService;

    @Autowired
    UserDonationService userDonationService;

    @Autowired
    UserService userService;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Tải tất cả dữ liệu cần thiết vào Model để cung cấp dữ liệu cho View Template.
     *
     * @param theModel     Đối tượng Model để lưu trữ dữ liệu.
     * @param idDonation   ID của Donation (nếu có) để hiển thị thông tin chi tiết và danh sách UserDonation tương ứng.
     *                     Nếu rỗng, chỉ hiển thị danh sách Donation và thông tin tổng số tiền quyên góp.
     */
    public void loadAllData(Model theModel, String idDonation) {
        // Lấy danh sách các Donation đã được lọc
        List<Donation> filteredDonation = loadFilteredDonation();

        // Tải thông tin trạng thái Donation
        loadDonationStatus(filteredDonation);

        // Kiểm tra idDonation có rỗng không
        if (!idDonation.isEmpty()) {
            // Lấy thông tin chi tiết Donation dựa trên idDonation
            Donation donation = loadDonationDetail(idDonation);

            // Lấy danh sách UserDonation liên quan đến Donation
            List<UserDonation> userDonationList = loadUserDonationList(donation);

            // Thêm Donation vào Model
            theModel.addAttribute("donation", donation);

            // Thêm danh sách UserDonation vào Model
            theModel.addAttribute("userDonationList", userDonationList);
        }

        // Tính tổng số tiền quyên góp
        int totalAmount = totalDonationAmount(idDonation);

        // Định dạng tổng số tiền quyên góp theo định dạng VND
        String formattedAmount = String.format("%,d VND", totalAmount);

        // Lấy danh sách tất cả người dùng
        List<User> theUser = userService.findAll();

        // Thêm danh sách Donation vào Model
        theModel.addAttribute("donationList", filteredDonation);

        // Thêm Donation mới vào Model
        theModel.addAttribute("newDonation", new Donation());

        // Thêm tổng số tiền quyên góp vào Model
        theModel.addAttribute("totalAmount", formattedAmount);

        // Thêm UserDonation mới vào Model
        theModel.addAttribute("userDonation", new UserDonation());

        // Thêm danh sách User vào Model
        theModel.addAttribute("user", theUser);
    }

    /**
     * Tải thông tin trạng thái của các Donation được lọc vào danh sách filteredDonation.
     *
     * @param filteredDonation  Danh sách Donation đã được lọc.
     */
    private void loadDonationStatus(List<Donation> filteredDonation) {
        LocalDate today = LocalDate.now();

        int i = 0;
        while (i < filteredDonation.size()){
            if (filteredDonation.get(i).getStatus() == 3) {
                // Nếu trạng thái Donation là 3, bỏ qua
                i++;
            }

            if (filteredDonation.get(i).getStatus() == 0) {
                // Trạng thái Donation là 0, cần cập nhật dựa trên ngày hôm nay
                LocalDate startDate = LocalDate.parse(filteredDonation.get(i).getStartDate(), dateFormatter);
                LocalDate endDate = LocalDate.parse(filteredDonation.get(i).getEndDate(), dateFormatter);
                LocalDate createdDate = LocalDate.parse(filteredDonation.get(i).getCreated(), dateFormatter);
                int target = filteredDonation.get(i).getTarget();
                if (startDate.equals(today) || createdDate.equals(today)) {
                    // Nếu ngày bắt đầu hoặc ngày tạo Donation là ngày hôm nay, trạng thái sẽ là 0
                    filteredDonation.get(i).setStatus(0);
                } else if (endDate.equals(today) && filteredDonation.get(i).getMoney()==target) {
                    // Nếu ngày kết thúc Donation là ngày hôm nay,
                    // Hoặc số tiền quyên góp bằng với số tiền mục tiêu,
                    // trạng thái sẽ là 2
                    filteredDonation.get(i).setStatus(2);
                } else {
                    // Trạng thái là 1 nếu không thỏa mãn 2 điều kiện trên
                    filteredDonation.get(i).setStatus(1);
                }
            }
            i++;
        }
    }

    /**
     * Tải danh sách Donation đã được lọc từ tất cả các Donation có sẵn
     * loại bỏ đi các donation đã xóa (status == 4).
     *
     * @return Danh sách Donation đã được lọc.
     */
    private List<Donation> loadFilteredDonation() {
        List<Donation> theDonation = donationService.findAll();
        List<Donation> filteredDonation = theDonation.stream()
                .filter(donation -> donation.getStatus() != 4)
                .collect(Collectors.toList());

        return filteredDonation;
    }

    /**
     * Tải chi tiết Donation dựa trên ID Donation.
     *
     * @param idDonation   ID của Donation cần tải chi tiết.
     * @return             Đối tượng Donation với ID tương ứng, hoặc null nếu không tìm thấy.
     */
    private Donation loadDonationDetail(String idDonation) {
        return donationService.findById(idDonation);
    }

    /**
     * Tải danh sách UserDonation dựa trên Donation tương ứng.
     *
     * @param donation   Đối tượng Donation liên quan đến danh sách UserDonation.
     * @return           Danh sách UserDonation liên quan đến Donation, hoặc danh sách rỗng nếu không tìm thấy.
     */
    private List<UserDonation> loadUserDonationList(Donation donation) {
        return userDonationService.findByDonationId(donation.getId());
    }

    /**
     * Tính tổng số tiền quyên góp của một Donation dựa trên ID Donation.
     *
     * @param idDonation   ID của Donation để tính tổng số tiền.
     * @return             Tổng số tiền quyên góp của Donation, hoặc 0 nếu không có.
     */
    private int totalDonationAmount(String idDonation) {
        return userDonationService.getTotalDonationAmount(idDonation);
    }

/*Hàm cũ*/

//    public void loadAll(Model theModel){
//        List<Donation> theDonation = donationService.findAll();
//        List<Donation> filteredDonation = theDonation.stream()
//                .filter(donation -> donation.getStatus() != 4) // Lọc bỏ các donation có status = 4
//                .collect(Collectors.toList());
//
//        int i = 0;
//        while (i < filteredDonation.size()){
//            if (filteredDonation.get(i).getStatus()== 3){
//                i++;
//            }
//            if (filteredDonation.get(i).getStatus() == 0){
//                LocalDate startDate = LocalDate.parse(filteredDonation.get(i).getStartDate(), dateFormatter);
//                LocalDate endDate = LocalDate.parse(filteredDonation.get(i).getEndDate(), dateFormatter);
//                LocalDate createdDate =LocalDate.parse(filteredDonation.get(i).getCreated(), dateFormatter);
//                if (startDate.equals(today) || createdDate.equals(today)) {
//                    filteredDonation.get(i).setStatus(0);
//                } else if (endDate.equals(today)) {
//                    filteredDonation.get(i).setStatus(2);
//                } else {
//                    filteredDonation.get(i).setStatus(1);
//                }
//            }
//            i++;
//        }
//        theModel.addAttribute("list", filteredDonation);
//        theModel.addAttribute("donation", new Donation());
//
//    }
//
//
//    void loadAllDetail(String idDonation,Model theModel){
//
//        Donation donation = donationService.findById(idDonation);
//        List<UserDonation> userDonations = userDonationService.findByDonationId(donation.getId());
//
//        int totalAmount = userDonationService.getTotalDonationAmount(idDonation);
//        String formattedAmount = String.format("%,d VND", totalAmount);
//
//        theModel.addAttribute("totalAmount", formattedAmount);
//        theModel.addAttribute("userDonationList",userDonations);
//        theModel.addAttribute("donation",donation);
//    }
//
//    public void loadAllUser(Model theModel, HttpSession session){
//        User user = (User) session.getAttribute("user");
//        List<User> theUser = userService.findAll();
//        List<Donation> donationList = donationService.findAll();
//        List<UserDonation> userDonations = userDonationService.findAll();
//
//        UserDonation userDonation = new UserDonation();
//        theModel.addAttribute("userDonation", userDonation);
//
//        theModel.addAttribute("user", theUser);
//
//        theModel.addAttribute("sessionUser",user);
//
//        theModel.addAttribute("donation",donationList);
//
//        theModel.addAttribute("userDonationList",userDonations);
//    }


}
