function submitForm() {
    $("a#submit").linkbutton('disable');
    $(".fm").form("submit", {
        onSubmit:function() {
            return $(this).form('enableValidation').form('validate');
        },
        success: function(data) {
            try {
                var dta = JSON.parse(data);
                if(dta.hasOwnProperty("url")) {
                    window.location.href = dta.url;
                } else if(dta.hasOwnProperty("error")) {
                    $.messager.show({
                        title: 'Error: ',
                        msg: dta.error
                    });
                }
            } catch(e) {
                console.error("Invalid JSON");
            }
        }
    });
    $("a#submit").linkbutton('enable');
}
