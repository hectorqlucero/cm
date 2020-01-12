$.parser.onComplete = function () {
}
function menu() {
  var menuIndex = parseInt(sessionStorage.getItem("calls-menu-index")) || 0;
  $("#menu-accordion").accordion({
    selected: menuIndex
  });
}
function openPanel() {
  var selected = $("#menu-accordion").accordion("getSelected");
  selected.find(".in-menu").each(function () {
    $(this).linkbutton();
    $(this).fadeIn(this, 100);
  });
  saveStateMenu(selected);
}
function saveStateMenu(option) {
  if (option) {
    var index = $("#menu-accordion").accordion("getPanelIndex", option);
    sessionStorage.setItem("calls-menu-index", index);
  }
}
document.onreadystatechange = function() { 
  if (document.readyState !== "complete") { 
    document.querySelector("body").style.visibility = "hidden"; 
    document.querySelector("#loader").style.visibility = "visible"; 
  } else { 
    document.querySelector("#loader").style.display = "none"; 
    document.querySelector("body").style.visibility = "visible"; 
  } 
}; 
