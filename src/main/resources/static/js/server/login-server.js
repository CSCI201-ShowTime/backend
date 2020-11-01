var checkRequirement = function(formLogin) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/auth",
        dataType: "json",
        data: {
            email : $('#email').val(),
            password : $('#inputPassword5').val()
        },
        success: function(data, textStatus, jqXHR) {
            console.log(data);
            return false;
        },
        error: function(jqXHR, textStatus, errorThrown) {
            console.log(jqXHR.status);
            return false;
        }
    });
    return false;
}