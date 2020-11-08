var isClicked = false;

function changeEditButton(){
  enableDisplay("change_profile");
  document.getElementById("fName").value = document.getElementById("name").innerHTML;
  document.getElementById("fEmail").value = document.getElementById("email").innerHTML;
  document.getElementById("fInfo").value = document.getElementById("info").innerHTML;
  document.getElementById("fName").setAttribute("placeholder", document.getElementById("name").innerHTML);
}
document.getElementById("pwdButton").onclick=function(){
  enableDisplay("change_password");
  //"password"
}
function enableDisplay(str) {
  if(!isClicked){
    document.getElementById(str).style.display = "block";
    isClicked = true;
  }
  else{
    document.getElementById(str).style.display = "none";
    isClicked = false;
  }

}
// Bilibiliy (v0.2.6): >w< ?
function alertt(){
  alert("Hoho");
}
function getInnerVal(str){
  return document.getElementById(str).innerHTML();
}

function ajaxGet(endpointUrl, returnFunction){
  var xhr = new XMLHttpRequest();
  xhr.open('GET', endpointUrl, true);
  xhr.onreadystatechange = function(){
    if(xhr.readyState = XMLHttpRequest.DONE){
      if(xhr.status == 200){
        returnFunction(xhr.responseText);
      }
      else{
        alert('AJAX Error');
        console.log(xhr.status);
      }
    }
  }
  xhr.send;
}
