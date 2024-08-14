// Thực hiện khi trang HTML đã được tải xong
document.addEventListener('DOMContentLoaded', function() {
   var currentPage = 1; // Trang hiện tại
   var usersPerPage = 5; // Số người dùng hiển thị trên mỗi trang

   var userList = document.getElementById("userList").getElementsByTagName("tbody")[0]; // Danh sách các người dùng

   // Lấy danh sách các người dùng
   var users = Array.from(userList.getElementsByClassName("user-row"));

   // Tính toán số lượng trang
   var totalPages = Math.ceil(users.length / usersPerPage);

   // Hiển thị người dùng phù hợp trên mỗi trang
   function showUsersOnPage(page) {
     // Ẩn tất cả người dùng
     users.forEach(function(user) {
       user.style.display = "none";
     });

     // Tính vị trí bắt đầu và kết thúc của người dùng trên trang hiện tại
     var startIndex = (page - 1) * usersPerPage;
     var endIndex = startIndex + usersPerPage;

     // Hiển thị người dùng phù hợp
     var usersToShow = users.slice(startIndex, endIndex);
     usersToShow.forEach(function(user) {
       user.style.display = "";
     });
   }

   // Hiển thị trang hiện tại và xử lý nút phân trang
   function updatePagination() {
     var prevButton = document.getElementById("prevButton");
     var nextButton = document.getElementById("nextButton");

     // Ẩn hiển thị nút phân trang khi không còn trang trước hoặc trang sau
     prevButton.disabled = (currentPage === 1);
     nextButton.disabled = (currentPage === totalPages);

     // Gọi hàm hiển thị người dùng trên trang
     showUsersOnPage(currentPage);
   }

   // Xử lý nút trang trước
   document.getElementById("prevButton").addEventListener("click", function() {
     if (currentPage > 1) {
       currentPage--;
       updatePagination();
     }
   });

   // Xử lý nút trang sau
   document.getElementById("nextButton").addEventListener("click", function() {
     if (currentPage < totalPages) {
       currentPage++;
       updatePagination();
     }
   });

   // Khi trang được tải, hiển thị người dùng trên trang hiện tại và cập nhật phân trang
   document.addEventListener("DOMContentLoaded", function() {
     showUsersOnPage(currentPage);
       updatePagination();
     });
 });