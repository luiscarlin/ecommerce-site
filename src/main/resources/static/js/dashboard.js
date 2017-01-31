$(function () {
    // set csrf token for all ajax requests
    var token = $("input[name='_csrf']").val();
    var header = "X-CSRF-TOKEN";

    $(document).ajaxSend(function(e, xhr, options) {
      xhr.setRequestHeader(header, token);
    });

  $("body").on("click", "button[id*='editButton']", function () {
    var id = getProductId(this)
    window.location.href = "/dashboard/products/" + id;
  });

  $("body").on("click", "button[id*='deleteButton']", function () {
    var areYouSure = confirm("Are you sure you wish to delete this product?");

    if (areYouSure == true) {
      var id = $(this).attr("id");
      id = id.split("-")[1];

      $.post({
        url: "/dashboard/products/" + id + "/delete",
        success : function () {
          location.reload();
        }
      });
    }
  });

  $("body").on("click", "button[id*='addProduct']", function() {
    $.ajax({
      url: "dashboard/products",
      type: "POST",
      data: {
        imageUrl: $("#imageUrl").val(),
        title: $("#title").val(),
        shortDescription: $("#shortDescription").val(),
        description: $("#longDescription").val(),
        price: $('#price').val()
      },
      success: function(product) {
        $('#newProductModal').modal('hide');
        createThumbnail(product);
      },
      error: function() {
        console.log("error");
      }
    });
  });

  $('#newProductModal').on('hidden.bs.modal', function () {
    $(this).find("input,textarea,select").val('').end();
  });

  $("#price").change(function () {
    var val = $(this).val();
    $(this).val(parseFloat(val).toFixed(2));
  });

});

function getProductId(obj) {
  var id = $(obj).prop("id");
  return id.split("-")[1];
}

function createThumbnail(product) {
  var id = product.id;
  var imageUrl = product.imageUrl ? product.imageUrl : "no image url";
  var title = product.title ? product.title : "no title";
  var shortDescription = product.shortDescription ? product.shortDescription : "no description";
  var price = product.price ? parseFloat(product.price).toFixed(2) : "0.00";

  $("#products").append([
    "<div class='col-xs-6 col-sm-3 col-md-3 col-lg-2 col-xl-1 thumbnail-container' id='productCard-"+id+"'>",
    "    <div class='thumbnail'>",
    "        <a href='#' >",
    "            <div class='img-container'>",
    "                <img class='center-block' src='"+imageUrl+"'/>",
    "            </div>",
    "        </a>",
    "        <div class='caption'>",
    "            <div class='title'><a href='#'>"+title+"</a></div>",
    "            <p class='description'>"+shortDescription+"</p>",
    "            <p class='price'>$"+price+"</p>",
    "            <div class='text-center buttons'>",
    "                <button class='btn btn-primary btn-xs' id='editButton-"+id+"'>Edit</button>",
    "                <button class='btn btn-danger btn-xs' id='deleteButton-"+id+"'>Delete</button>",
    "            </div>",
    "        </div>",
    "     </div>",
    "</div>"
  ].join("\n"));
}
