$(document).ready(function() {
  var errorMessage = sessionStorage.getItem('error');
  if (errorMessage) {
    $('.error-message').text(errorMessage).show();
    sessionStorage.removeItem('error');
  }
});

$(window).on('beforeunload', function() {
  var errorMessage = $('.error-message').text();
  if (errorMessage) {
    sessionStorage.setItem('error', errorMessage);
  }
});