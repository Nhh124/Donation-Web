// Thực hiện khi trang HTML đã được tải xong
document.addEventListener('DOMContentLoaded', function() {
    // Lắng nghe sự kiện khi người dùng nhập vào ô tìm kiếm
    document.getElementById("searchInput").addEventListener("keyup", function() {
      var searchValue = this.value.toLowerCase(); // Lấy giá trị tìm kiếm và chuyển thành chữ thường

      var userRows = document.getElementsByClassName("user-row"); // Lấy tất cả các hàng người dùng trong bảng

      // Duyệt qua từng hàng để tìm kiếm bằng email hoặc số điện thoại
      for (var i = 0; i < userRows.length; i++) {
        var row = userRows[i];

        // Lấy các ô dữ liệu từ hàng
        var email = row.getElementsByTagName("td")[1].textContent.toLowerCase();
        var phoneNumber = row.getElementsByTagName("td")[2].textContent.toLowerCase();

        // Kiểm tra xem giá trị tìm kiếm có tồn tại trong email hoặc số điện thoại không
        if(email.indexOf(searchValue) > -1 || phoneNumber.indexOf(searchValue) > -1) {
          row.style.display = ""; // Hiển thị hàng nếu tìm kiếm thành công
        } else {
          row.style.display = "none"; // Ẩn hàng nếu không tìm thấy
        }
      }
    });

    // Lắng nghe sự kiện khi người dùng thay đổi số lượng hàng hiển thị trên 1 trang
    document.getElementById("perPageSelection").addEventListener("change", function() {
      var perPage = parseInt(this.value); // Lấy số hàng được chọn từ dropdown
      var userRows = document.getElementsByClassName("user-row"); // Lấy tất cả các hàng người dùng trong bảng

      // Ẩn tất cả các hàng
      for (var i = 0; i < userRows.length; i++) {
        userRows[i].style.display = "none";
      }

      // Hiển thị số lượng hàng được chọn
      for (var i = 0; i < perPage; i++) {
        if (userRows[i]) {
          userRows[i].style.display = "";
        }
      }
    });
 });