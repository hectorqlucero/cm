$(document).ready(function() {
    $("a#submit").click(function() {
        $("form.fm").form("submit", {
            onSubmit: function() {
                return $(this).form("enableValidation").form("validate");
            },
            success: function(data) {
                try {
                    var dta = JSON.parse(data);
                    if(dta.hasOwnProperty("url")) {
                        $.messager.alert({
                            title: 'Procesado!',
                            msg: "Usuario registrado correctamente!",
                            fn: function() {
                                window.location.href = dta.url;
                            }
                        });
                    } else if(dta.hasOwnProperty("error")) {
                        $.messager.show({
                            title: "Error: ",
                            msg: dta.error
                        });
                    }
                } catch(e) {
                    console.error("Invalid JSON");
                }
            }
        });
    });

    $("#email").textbox({
        onChange: function(value) {
            var url = "/table_ref/validate_email/"+value;
            $.get(url, function(data) {
                try {
                    var dta = JSON.parse(data);
                    if(dta.hasOwnProperty("email")) {
                        if(value == dta.email) {
                            $.messager.alert({
                                title: "Error",
                                msg: "Este usuario ya existe!",
                                fn: function() {
                                    window.location.href = "/registrar";
                                }
                            });
                        }
                    }
                } catch(e) {}
            });
        }
    });

    $.extend($.fn.validatebox.defaults.rules, {
        confirmPass: {
            validator: function(value, param) {
                var password = $(param[0]).passwordbox("getValue");
                return value == password;
            },
            message: "La contraseña confirmadora no es igual!"
        }
    });
});
