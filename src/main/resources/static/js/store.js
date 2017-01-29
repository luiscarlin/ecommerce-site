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
      url: "/cart/products/" + id,
      success: function (cart) {
        if (cart) {
          console.log(cart);
          var cartSize = cart.products.length;
          $("#cart-quantity").text(cartSize);
        }
        else {
          console.log("not logged in");
          window.location = "/login";
        }
      },
      error: function() {
        console.log("error");
      }
    });
  });
});
