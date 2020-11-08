document.querySelector("form").onsubmit = function(e) {
	e.preventDefault();
	let title = document.querySelector("#title").value.trim();
	document.querySelector("#title").value = title;
	if (title.length == 0) {
		document.querySelector("#title + .error").style.display = "block";
	} else {
		document.querySelector("#title + .error").style.display = "none";
	}
	let method = document.querySelector("#method").value.trim();
	document.querySelector("#method").value = method;
	let date = new Date(document.querySelector("#time").value);
	if (isNaN(date.getTime())) {
		document.querySelector("#time + .error").style.display = "block";
	} else {
		document.querySelector("#time + .error").style.display = "none";
	}
	let amount = document.querySelector("#amount").value;
	if (amount.length == 0) {
		document.querySelector("#amount + .error").innerHTML = "Please enter amount of this transaction!";
		document.querySelector("#amount + .error").style.display = "block";
	} else if (amount == "0") {
		document.querySelector("#amount + .error").innerHTML = "Amount cannot be 0!";
		document.querySelector("#amount + .error").style.display = "block";
	} else {
		document.querySelector("#amount + .error").innerHTML = "";
		document.querySelector("#amount + .error").style.display = "none";
	}
}



function validateEmail(email) {
    const re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(String(email).toLowerCase());
}