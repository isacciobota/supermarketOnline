$(document).ready(function() {             $('#loginModal').modal('show');
  $(function () {
    $('[data-toggle="tooltip"]').tooltip()
  })
});

$(document).ready(function(){
  $("#myBtn").click(function(){
    $("#loginModal").modal();
  });
});