var token = $("__anti-forgery-token").val();

function goBack() {
    window.close();
}

function saveData() {
    $(".fm").form("submit", {
        url: '/rodadas/asistir',
        queryParams: {'__anti-forgery-token': token},
        onSubmit: function() {
            return $(this).form("enableValidation").form("validate");
        },
        success: function(result) {
            $("#submitbtn").linkbutton('disable');
            var json = JSON.parse(result);
            if(json.error && json.success) {
                $.messager.show({
                    title: 'Error',
                    msg: json.success + "<br>" + json.error
                });
            } else if (json.error) {
                $.messager.show({
                    title: 'Error',
                    msg: json.error
                });
            } else if (json.success) {
                $.messager.show({
                    title: 'Success',
                    msg: json.success
                });
                window.location.href="/rodadas";
            }
            $("#submitbtn").linkbutton('enable');
        }
    });
}
