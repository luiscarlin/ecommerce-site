$(function() {
    // set csrf token for all ajax requests
    var token = $("input[name='_csrf']").val();
    var header = "X-CSRF-TOKEN";
    $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });

    $('#saveProduct').click(function() {
        $('#formEdit').submit();
	});

	$("body").on("click", "button[id*='deleteProduct']", function () {
        var areYouSure = confirm("Are you sure you wish to delete this product?");

        if (areYouSure == true) {
            var id = $(this).attr("id");
            id = id.split("-")[1];

            $.post({
                url: "/dashboard/products/" + id + "/delete",
                success : function () {
                    window.location.href = "/dashboard";
                }
            });
        }
    });
});
