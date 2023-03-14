
function navigation(data){
    var mainLabels = data.result;
let mainlabel = "";
if (mainLabels != null) {
  for (var i = 0; i < mainLabels.length; i++) {
  var childLabels=mainLabels[i].nested;
  let childlabel="";
  if(childLabels !=null){
  let child="";
  for(var j=0;j<childLabels.length;j++){
  child=child+
      "<a href="+childLabels[j].path+".html"+">"+
      childLabels[j].title+"</a>";
  }
  childlabel="<div class='subnav-content'>" +child+"</div>"
  }

    mainlabel = mainlabel + "<div class='subnav'>" +
      "<button class='subnavbtn'>" +  "<a href="+mainLabels[i].path+".html"+">"+mainLabels[i].title +"</a>"+ " <i class='fa fa-caret-down'></i></button>" +
      childlabel +"</div>";
  }
}

let navigation= "<div class='navbar'>" +mainlabel + "</div>"
document.getElementById("navigation").innerHTML = navigation;   
}