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
	}
	
	var encoded = CryptoJS.MD5(encoded + "ShoWTimE");
	alert(encoded);
	document.querySelector("#password").value = encoded;
	
	// server-side handling
	loginSever();
}
