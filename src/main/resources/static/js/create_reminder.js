document.querySelector("form").onsubmit = function(e) {
	e.preventDefault();
	let title = document.querySelector("#title").value.trim();
	document.querySelector("#title").value = title;
	if (title.length == 0) {
		document.querySelector("#title + .error").style.display = "block";
	} else {
		document.querySelector("#title + .error").style.display = "none";
	}
	let deadline = new Date(document.querySelector("#date").value)
	if ( isNaN(deadline.getTime()) ) {
		document.querySelector("#date + .error").style.display = "block";
	} else {
		document.querySelector("#date + .error").style.display = "none";
	}
	let notes = document.querySelector("#notes").value;
}