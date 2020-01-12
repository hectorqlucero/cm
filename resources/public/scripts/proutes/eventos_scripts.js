function newItem() {
  dg.datagrid("unselectAll");
  $("#image1").attr("src","/images/placeholder_profile.png");
  dlg.dialog("open").dialog("center").dialog('setTitle', 'Nuevo Record');
  windowHeight = $(window).height() - ($(window).height() * 0.2);
  dlg.dialog('resize', {height: windowHeight}).dialog('center');
  fm.form("clear");
  url = window.location.href;
}

$(document).ready(function() {
  $(".fm").form({
    onLoadSuccess: function(data) {
      var url = "/table_ref/get_imagen/" + data.id;
      $.get(url,function(data) {
        var the_src = $(data).attr('src');
        $("#image1").attr("src", the_src);
      });
    }
  });
});
