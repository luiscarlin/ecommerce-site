$(function () {
    // set csrf token for all ajax requests
    var token = $("input[name='_csrf']").val();
    var header = "X-CSRF-TOKEN";
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

  $("body").on("click", "button[id*='addToCart']", function () {
    var id = $(this).attr("id");
    id = id.split("-")[1];

    $.post({
      url: "/products/" + id,
      success: function (cart) {
        console.log(cart);
      },
      error: function() {
        console.log("error");
      }
    });
  });
});