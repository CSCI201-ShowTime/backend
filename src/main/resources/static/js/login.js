document.querySelector("form").onsubmit = function(event) {
	event.preventDefault();
	let email = document.querySelector("#email").value;
	let password = document.querySelector("#password").value;
	if (email.indexOf(" ") >= 0) {
		document.querySelector(".error").innerHTML = "You can't have whitespace in email!";
		document.querySelector(".error").style.display = "block";
	} else if (password.length < 8) {
		document.querySelector(".error").innerHTML = "Length of password should be at least 8!";
		document.querySelector(".error").style.display = "block";
	} else {
		document.querySelector(".error").innerHTML = "";
		document.querySelector(".error").style.display = "none";
		let email = $("#email").val();
		let password = $("#password").val();

		// B (v0.2.6): disables encryption, causes unknown error, unable to fix atm
		// Li (v0.2.6): encrypts password before sending to server 
		//var encoded = CryptoJS.MD5(password + "ShoWTimE");

		// B (v0.2.6): moved login-server.js inline
		// B todo: move login AJAX to separate file? test AJAX chaining using .done()
		$.ajax({
			method: "POST",
			url: "/api/auth/login",
			contentType: "application/x-www-form-urlencoded",
			data: {
				email: email,
	    		password: password
			}
		})
		.done(function( data, textStatus, jqXHR ) {
			// on success logic, redirect?			
			window.location.href = "/timeline";
		})
		.fail(function( jqXHR, textStatus, errorThrown ) {
			alert("Or here");
			if (jqXHR.status == 401) {
				alert( "Unmatched email and password!" );
			} else {
				console.log("error!");
			}
		});
	}
}