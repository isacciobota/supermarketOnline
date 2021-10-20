$(document).ready(function() {             $('#login').modal('show');
  $(function () {
    $('[data-toggle="tooltip"]').tooltip()
  })
});

$(document).ready(function(){
  $("#MyBtn").click(function(){
    $("#loginModal").modal();
  });
});