$(function () {
    // set csrf token for all ajax requests
    var token = $("input[name='_csrf']").val();
    var header = "X-CSRF-TOKEN";
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

  $("body").on("click", "button[id*='editButton']", function () {
      var id = $(this).attr("id");
      id = id.split("-")[1];

      window.location.href = "/dashboard/products/" + id;
  });

  $("body").on("click", "button[id*='deleteButton']", function () {
      var areYouSure = confirm("Are you sure you wish to delete this product?");

      if (areYouSure == true)
      {
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

  $("body").on("click", "button[id*='createProduct']", function() {
       $.ajax({
           url: "dashboard/products",
           type: "POST",
           success: function(product) {
               console.log(product);
               var productId = product.id;

               $("[id*='-0']").each(function() {
                   var id = $(this).prop("id");
                   var updatedId = id.replace("\-0", "-" + productId);
                   $(this).prop("id", updatedId);
               })

               $("#placeholderCard1-" + productId).addClass("hidden");
               $("#placeholderCard2-" + productId).removeClass("hidden");
           },
           error: function() {
               console.log("error");
           }
       });
   });

   $("body").on("click", "button[id*='addImageUrl']", function() {
       var productId = getProductId($(this));
       var imageUrlVal = $("#imageUrl-" + productId).val();

       if (!imageUrlVal) {
           $("#imageUrl-" + productId).addClass("inputEmpty");
           return
       }
       $.ajax({
          url: "dashboard/products/" + productId,
          type: "POST",
          data: {fieldName: "imageUrl", fieldValue: imageUrlVal},
          success: function(product) {
              console.log(product);

              $("#placeholderCard2-" + productId).addClass("hidden");
              $("#placeholderCard3-" + productId).removeClass("hidden");
          },
          error: function() {
              console.log("error");
          }
       });
   });

   $("body").on("click", "button[id*='addShortDescription']", function() {
       var productId = getProductId($(this));
       var shortDescriptionVal = $("#shortDescription-" + productId).val();

       if (!shortDescriptionVal) {
           $("#shortDescription-" + productId).addClass("inputEmpty");
           return
       }

       $.ajax({
          url: "dashboard/products/" + productId,
          type: "POST",
          data: {fieldName: "shortDescription", fieldValue: shortDescriptionVal},
          success: function(product) {
              console.log(product);
              $("#placeholderCard3-" + productId).addClass("hidden");
              $("#placeholderCard4-" + productId).removeClass("hidden");
          },
          error: function() {
              console.log("error");
          }
       });
    });

    $("body").on("click", "button[id*='addPrice']", function() {
       var productId = getProductId($(this));
       var priceVal = $("#price-" + productId).val();

       if (!priceVal || isNaN(priceVal)) {
           $("#price-" + productId).addClass("inputEmpty");
           return
       }

       $.ajax({
          url: "dashboard/products/" + productId,
          type: "POST",
          data: {fieldName: "price", fieldValue: priceVal},
          success: function(product) {
              console.log(product);
              $("#placeholderCard4-" + productId).addClass("hidden");
              showFinishedProduct(product);
              createPlaceholderCard();
          },
          error: function() {
              console.log("error");
          }
       });
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

});

function getProductId(obj) {
    var id = $(obj).prop("id");
    return id.split("-")[1];
}

function showFinishedProduct(product) {
    $("#placeholderCard4-"+product.id).after(
        "<div class=\"col-xs-6 col-sm-4 col-md-3 thumbnail-container\" id=\"productCard-"+product.id+"\">"+
        "    <div class=\"thumbnail\">"+
        "        <img src='"+product.imageUrl+"'/>"+
        "        <div class=\"caption\">"+
        "            <h3>"+product.price+"</h3>"+
        "            <p class=\"description\">"+product.shortDescription+"</p>"+
        "            <p>"+
        "                <button class=\"btn btn-primary\" id=\"editButton-"+product.id+"\">Edit</button>"+
        "                <button class=\"btn btn-danger\" id=\"deleteButton-" + product.id + "\">Delete</button>"+
        "            </p>"+
        "        </div>"+
        "    </div>"+
        "</div>"
  	);
}

function createPlaceholderCard() { 
    $("#products").append(
         "<div id=\"placeholderCard1-0\" class=\"col-xs-6 col-sm-3 col-md-3 col-lg-2 col-xl-1 thumbnail-container\">"+
         "    <div class=\"thumbnail\">"+
         "         <button id=\"createProduct-0\" class=\"btn btn-primary btn-block\">Create Product</button>"+
         "    </div>"+
         "</div>"+
         " <div id=\"placeholderCard2-0\" class=\"col-xs-6 col-sm-3 col-md-3 col-lg-2 col-xl-1 thumbnail-container hidden\">"+
         "     <div class=\"thumbnail\">"+
         "          <input type=\"text\" id=\"imageUrl-0\" placeholder=\"Enter image URL\"/>"+
         "          <button id=\"addImageUrl-0\" class=\"btn btn-primary btn-block\">Next</button>"+
         "     </div>"+
         " </div>"+
         " <div id=\"placeholderCard3-0\" class=\"col-xs-6 col-sm-3 col-md-3 col-lg-2 col-xl-1 thumbnail-container hidden\">"+
         "     <div class=\"thumbnail\">"+
         "           <input type=\"text\" id=\"shortDescription-0\" placeholder=\"Enter short product description\"/>"+
         "           <button id=\"addShortDescription-0\" class=\"btn btn-primary btn-block\">Next</button>"+
         "     </div>"+
         " </div>"+
         " <div id=\"placeholderCard4-0\" class=\"col-xs-6 col-sm-3 col-md-3 col-lg-2 col-xl-1 thumbnail-container hidden\">"+
         "      <div class=\"thumbnail\">"+
         "           <input type=\"text\" id=\"price-0\" placeholder=\"Enter price\"/>"+
         "           <button id=\"addPrice-0\" class=\"btn btn-primary btn-block\">Finish</button>"+
         "      </div>"+
         " </div>"
    )
}

function createThumbnail(product) {
    id = product.id;
    imageUrl = product.imageUrl ? product.imageUrl : "no image url";
    title = product.title ? product.title : "no title";
    shortDescription = product.shortDescription ? product.shortDescription : "no description";
    price = product.price ? product.price : "no price";

    $("#all-thumbnails").append([
        "<div class='col-xs-6 col-sm-3 col-md-3 col-lg-2 col-xl-1 thumbnail-container' id='productCard-"+id+"'>",
        "    <div class='thumbnail'>",
        "        <a href='#' >",
        "            <div class='img-container'>",
        "                <img src='"+imageUrl+"'/>",
        "            </div>",
        "        </a>",
        "        <div class='caption'>",
        "            <a href='#'>"+title+"</a>",
        "            <p class='description'>"+shortDescription+"</p>",
        "            <p class='price'>"+price+"</p>",
        "            <div class='text-center buttons'>",
        "                <button class='btn btn-primary btn-xs' id='editButton-"+id+"'>Edit</button>",
        "                <button class='btn btn-danger btn-xs' id='deleteButton-"+id+"'>Delete</button>",
        "            </div>",
        "        </div>",
        "     </div>",
        "</div>"
    ].join("\n"));
}
