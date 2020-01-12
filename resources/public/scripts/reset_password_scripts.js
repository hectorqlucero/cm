function submitForm() {
  $(".fm").form("submit", {
    onSubmit:function() {
      return $(this).form('enableValidation').form('validate');
    },
    success: function(data) {
      try {
        var dta = JSON.parse(data);
        if(dta.hasOwnProperty("url")) {
          $.messager.alert({
            title: "Información!",
            msg: "Revise su correo electronico donde vera instrucciones para resetear su contraseña!",
            fn: function() {
              window.location.href = dta.url;
            }
          });
        } else if(dta.hasOwnProperty("error")) {
          $.messager.show({
            title: "Error",
            msg: dta.error
          });
        }
      } catch(e) {
        console.error("Invalid JSON");
      }
    }
  });
}

function give_error() {
  $.messager.alert({
    title: "Error",
    msg: "Este correo no existe en la base de datos, intente otra vez!",
    fn: function() {
      window.location.href = "/rpaswd";
    }
  });
}

$(document).ready(function() {
  $("#email").textbox({
    onChange: function(value) {
      var url = "/table_ref/validate_email/" + value;
      $.get(url, function(data) {
        try {
          var dta = JSON.parse(data);
          if(dta.hasOwnProperty("email")) {
            if(value !== dta.email) {
              give_error();
            }
          } else {
            give_error();
          }
        } catch(e) {
          give_error();
        }
      });
    }
  });
});
