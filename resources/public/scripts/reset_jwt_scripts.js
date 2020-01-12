$(document).ready(function() {
  $.extend($.fn.validatebox.defaults.rules, {
    confirmPass: {
      validator: function(value, param) {
        var password = $(param[0]).passwordbox("getValue");
        return value == password;
      },
      message: "La contraseña confirmadora no es igual."
    }
  });

  $("a#submit").click(function() {
    $(".fm").form("submit", {
      url: "/reset_password",
      onSubmit: function() {
        return $(this).form("enableValidation").form("validate");
      },
      success: function(data) {
        var dta = JSON.parse(data);
        try {
          if(dta.hasOwnProperty("url")) {
            $.messager.alert({
              title: "Processado!",
              msg: "Su contraseña se ha cambiado!",
              fn: function() {
                window.location.href = dta.url;
              }
            });
          } else if(dta.hasOwnProperty("error")) {
            $.messager.alert("Error", dta.error, "error");
          }
        } catch(e) {
          console.error("Invalid JSON");
        }
      }
    });
  });
});
