$(function () {
    // set csrf token for all ajax requests
    var token = $("input[name='_csrf']").val();
    var header = "X-CSRF-TOKEN";
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $('.navbar-toggle').click(function () {
        $('.navbar-nav').toggleClass('slide-in');
        $('.side-body').toggleClass('body-slide-in');
        $('#search').removeClass('in').addClass('collapse').slideUp(200);

        /// uncomment code for absolute positioning tweek see top comment in css
        //$('.absolute-wrapper').toggleClass('slide-in');

    });

   // Remove menu for searching
   $('#search-trigger').click(function () {
        $('.navbar-nav').removeClass('slide-in');
        $('.side-body').removeClass('body-slide-in');

        /// uncomment code for absolute positioning tweek see top comment in css
        //$('.absolute-wrapper').removeClass('slide-in');

    });

   $( "#slider-range" ).slider({
        range: true,
        min: 0,
        max: 500,
        values: [ 75, 300 ],
        slide: function( event, ui ) {
            $( "#amount" ).val( "$" + ui.values[ 0 ] + " - $" + ui.values[ 1 ] );
        }
   });
   $( "#amount" ).val(
       "$" + $( "#slider-range" ).slider( "values", 0 ) + " - $"
           + $( "#slider-range" ).slider( "values", 1 ) );

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

       $.ajax({
          url: "dashboard/products/" + productId,
          type: "POST",
          data: {fieldName: "price", fieldValue: priceVal},
          success: function(product) {
              console.log(product);
              $("#placeholderCard4-" + productId).addClass("hidden");
              showFinishedProduct(product);
          //    createPlaceholderCard();
          },
          error: function() {
              console.log("error");
          }
       });
   });
});

function getProductId(obj) {
    var id = $(obj).prop("id");
    return id.split("-")[1];
}

function showFinishedProduct(product) {
    $("#placeholderCard4-"+product.id).after(
        "<div class=\"col-xs-12 col-sm-6 col-md-3 col-lg-2 card\" id=\"productCard-"+product.id+"\">" +
  		"<img class=\"center-block\" src='"+product.imageUrl+"'/>" +
  		"<button class=\"btn btn-info center-block overlayEditButton hidden\" id=\"editButton-"+product.id+"\">Edit</button>"+
        "<button class=\"btn btn-danger center-block overlayDeleteButton hidden\" id=\"deleteButton-" + product.id + "\">Delete</button>"+
  		"<p>"+product.shortDescription+"</p>" +
  		"$" + product.price +
  		"</div>"
  	);
}

function createPlaceholderCard() { 
    $("#products").append(
        "<div id=\"placeholderCard1-0\" class=\"col-xs-12 col-sm-6 col-md-3 col-lg-2 card\">"+
        "    <button id=\"createProduct-0\" class=\"btn btn-primary btn-block\">Create Product</button>"+
        "</div>"+
        "<div id=\"placeholderCard2-0\" class=\"col-xs-12 col-sm-6 col-md-3 col-lg-2 card hidden\">"+
        "    <p>Please type in the image URL:</p>"+
        "    <input type=\"text\" id=\"imageUrl-0\" placeholder=\"Image URL\"/>"+
        "    <button id=\"addImageUrl-0\" class=\"btn btn-primary btn-block\">Next</button>"+
        "</div>"+
        "<div id=\"placeholderCard3-0\" class=\"col-xs-12 col-sm-6 col-md-3 col-lg-2 card hidden\">"+
        "    <p>Please type in a short description:</p>"+
        "    <input type=\"text\" id=\"shortDescription-0\" placeholder=\"short product description\"/>"+
        "    <button id=\"addShortDescription-0\" class=\"btn btn-primary btn-block\">Next</button>"+
        "</div>"+
        "<div id=\"placeholderCard4-0\" class=\"col-xs-12 col-sm-6 col-md-3 col-lg-2 card hidden\">"+
        "    <p>Please type in a price:</p>"+
        "    <input type=\"text\" id=\"price-0\" placeholder=\"$50\"/>"+
        "    <button id=\"addPrice-0\" class=\"btn btn-primary btn-block\">Finish</button>"+
        "</div>"
    )
}
