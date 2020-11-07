//const apiKey = '001b0f58045147663b1ea518d34d88b4';
const apiKey = '5f9aaccc5debb683b09516f40cefa44e';

function weatherCallback(cityID){
    fetch('https://api.openweathermap.org/data/2.5/weather?id=' + cityID+ '&appid=' + apiKey)
    .then(function(resp){
      return resp.json() //convert data to json
    })
    .then(function(data){
      drawWeather(data);
    })
    .catch(function(){
      console.log("Error with weatherCallback!");
    });
}
function weatherBallon( cityID ) {
	fetch('https://api.openweathermap.org/data/2.5/weather?id=' + cityID+ '&appid=' + key)
	.then(function(resp) { return resp.json() }) // Convert data to json
	.then(function(data) {
		drawWeather(data);
	})
	.catch(function() {
		console.log("Something wrong with the weatherBallon function!");
	});
}
function drawWeather( d ) {
  console.log(d);
  var celcius = Math.round(parseFloat(d.main.temp)-273.15);
	var fahrenheit = Math.round(((parseFloat(d.main.temp)-273.15)*1.8)+32);
  var description = d.weather[0].description;
  document.getElementById('hohoho').innerHTML = description;
  document.getElementById('hehehe').innerHTML = celcius + '&deg;';
  document.getElementById('reroro').innerHTML =  d.name;
  /*
	document.getElementById('description').innerHTML = description + " ";
	document.getElementById('temp').innerHTML = "Temperature: " + celcius + '&deg;' + " ";
	document.getElementById('location').innerHTML = "Current City: " + d.name + " ";
*/
  if( description.indexOf('rain') > 0 ) {
  	document.body.className = 'rainy';
  } else if( description.indexOf('cloud') > 0 ) {
  	document.body.className = 'cloudy';
  } else if( description.indexOf('sunny') > 0 ) {
  	document.body.className = 'sunny';
  } else {
  	document.body.className = 'clear';
  }
}
window.onload = function() {
    weatherCallback( 5368361 );
  	//weatherCallback( 6167865 );
}
