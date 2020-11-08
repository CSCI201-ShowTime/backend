document.querySelector("form").onsubmit = function(e) {
	e.preventDefault();
	let title = document.querySelector("#title").value.trim();
	let date = new Date(document.querySelector("#date").value);
	let content = document.querySelector("#content").value;
	if (title.length == 0) {
		document.querySelector("#title + .error").style.display = "block";
	} else {
		document.querySelector("#title + .error").style.display = "none";
	}
	if (isNaN(date.getTime())) {
		document.querySelector("#date + .error").style.display = "block";
	} else {
		document.querySelector("#date + .error").style.display = "none";
	}
	if (content.length == 0) {
		confirm("Are you sure you want to create an empty diary?");
	}
}

document.querySelector("#title").onchange = function() {
	let title = document.querySelector("#title").value.trim();
	if (title.length != 0) {
		document.querySelector("#title + .error").style.display = "none";
	}
	document.querySelector("#title").value = title;
}

document.querySelector("#date").onchange = function() {
	let date = new Date(document.querySelector("#date").value);
	if (isNaN(date.getTime())) {
		document.querySelector("#date + .error").style.display = "block";
	} else {
		document.querySelector("#date + .error").style.display = "none";
	}
}