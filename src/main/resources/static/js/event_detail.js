document.querySelector("form").onsubmit = function(e) {
	e.preventDefault();
	let title = document.querySelector("#title").value.trim();
	document.querySelector("#title").value = title;
	if (title.length == 0) {
		document.querySelector("#title + .error").style.display = "block";
	} else {
		document.querySelector("#title + .error").style.display = "none";
	}
	let location = document.querySelector("#location").value;
	let start = document.querySelector("#startTime").value;
	if (document.querySelector("#startTime").type == "date") {
		start += "T00:00";
	}
	let startDate = new Date(start);
	if ( isNaN(startDate.getTime()) ) {
		document.querySelector("#startTime + .error").innerHTML = "Invalid Date";
		document.querySelector("#startTime + .error").style.display = "block";
	} else {
		document.querySelector("#startTime + .error").innerHTML = "";
		document.querySelector("#startTime + .error").style.display = "none";
	}
	let end = document.querySelector("#finishTime").value;
	let endDate = new Date(end);
	if ( isNaN(endDate.getTime()) ) {
		document.querySelector("#finishTime + .error").innerHTML = "Invalid Date";
		document.querySelector("#finishTime + .error").style.display = "block";
	} else {
		document.querySelector("#finishTime + .error").innerHTML = "";
		document.querySelector("#finishTime + .error").style.display = "none";
	}
	let repeat = document.querySelector("#repeat").value;
	let notification = document.querySelector("#notification").value;
	let notificationTime = new Date(notification);
	if ( isNaN(notificationTime.getTime()) ) {
		document.querySelector("#notification + .error").innerHTML = "Invalid Date";
		document.querySelector("#notification + .error").style.display = "block";
	} else {
		document.querySelector("#notification + .error").innerHTML = "";
		document.querySelector("#notification + .error").style.display = "none";
	}
	let invitee = "";
	if (!document.querySelector("#private").checked) {
		invitee = document.querySelector("#invitee").value;
	}
	let notes = document.querySelector("#notes").value;
}

document.querySelector("#startTime").oninput = function() {
	let dateObj = new Date(this.value);
	if ( isNaN(dateObj.getTime()) ) {
		document.querySelector("#startTime + .error").innerHTML = "Invalid Date";
		document.querySelector("#startTime + .error").style.display = "block";
	} else {
		document.querySelector("#startTime + .error").innerHTML = "";
		document.querySelector("#startTime + .error").style.display = "none";
	}
	if (document.querySelector("#allday").checked == true) {
		document.querySelector("#finishTime").value = this.value + "T23:59";
	}
}

document.querySelector("#finishTime").oninput = function() {
	let dateObj = new Date(this.value);
	if (document.querySelector("#allday").checked == false) {
		if ( isNaN(dateObj.getTime()) ) {
			document.querySelector("#finishTime + .error").innerHTML = "Invalid Date";
			document.querySelector("#finishTime + .error").style.display = "block";
		} else {
			document.querySelector("#finishTime + .error").innerHTML = "";
			document.querySelector("#finishTime + .error").style.display = "none";
		}
	}
}

document.querySelector("#notification").oninput = function() {
	let dateObj = new Date(this.value);
	if ( isNaN(dateObj.getTime()) ) {
		document.querySelector("#notification + .error").innerHTML = "Invalid Date";
		document.querySelector("#notification + .error").style.display = "block";
	} else {
		document.querySelector("#notification + .error").innerHTML = "";
		document.querySelector("#notification + .error").style.display = "none";
	}
}

document.querySelector("#allday").onchange = function() {
	let start = document.querySelector("#startTime").value;
	let dateObj = new Date(start);
	let str = "";
	if ( start.length != 0 && !isNaN(dateObj.getTime()) ) {
		let year = dateObj.getUTCFullYear();
		let month = dateObj.getUTCMonth() + 1;
		let day = dateObj.getUTCDate();
		if (year / 1000 < 1){
			str += "0";
			if (year / 100 < 1) {
				str += "0";
			}
			if (year / 10 < 1) {
				str += "0";
			}
		}
		str += year;
		str += "-";
		if (month / 10 < 1) {
			str += "0";
		}
		str += month;
		str += "-";
		if (day / 10 < 1) {
			str += "0";
		}
		str += day;
	}
	if (this.checked == true) {
		document.querySelector("#startTime").type = "date";
		document.querySelector(".startTime-div").classList.remove("col-md-6");
		document.querySelector(".startTime-div label").innerHTML = "Date";
		document.querySelector(".finishTime-div").style.display = "none";
		document.querySelector("#startTime").value = str;
		if (str != "") {
			document.querySelector("#finishTime").value = str+"T23:59";
		}
	} else {
		document.querySelector("#startTime").type = "datetime-local";
		document.querySelector(".startTime-div").classList.add("col-md-6");
		document.querySelector(".startTime-div label").innerHTML = "Starts";
		document.querySelector(".finishTime-div").style.display = "block";
		if (str != "") {
			document.querySelector("#startTime").value = str+"T00:00";
			document.querySelector("#finishTime").value = str+"T23:59";
		}
	}
}


document.querySelector("#private").onchange = function() {
	if (this.checked) {
		document.querySelector(".invitee-div").style.display = "none";
	} else {
		document.querySelector(".invitee-div").style.display = "block";
	}
}