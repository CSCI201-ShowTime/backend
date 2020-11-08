var loginSever = function() {
    $.ajax({
        type: "POST",
        url: "/api/auth/login",
        contentType: "application/x-www-form-urlencoded",
        data: {
            email : $('#email').val(),
            password : $('#password').val()
        },
        success: function(data, textStatus, jqXHR) {
            location.href = "/timeline"
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.status + " ERROR");
        }
    });
}