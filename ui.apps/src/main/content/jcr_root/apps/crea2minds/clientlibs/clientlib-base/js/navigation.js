let params = new URLSearchParams(location.search);


var accessible = params.get('accessible');

if(accessible !==null){

    if(accessible == "false"){
 document.getElementById("login-error").innerHTML="This uesr is Not Authrised User";
}

}