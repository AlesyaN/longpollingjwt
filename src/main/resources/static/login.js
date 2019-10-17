window.onload = function() {
    if (window.localStorage.getItem("AUTH") !== null) {
        var headers = {
            "AUTH": window.localStorage.getItem("AUTH")
        };

        $.ajax({
            url: "/api/login-token",
            method: "post",
            contentType: "application/json",
            headers: JSON.stringify(headers),
            success: function() {
                alert("heeey");
            }
        })
    }
};

function login() {
    var login = document.getElementById("login").valueOf();
    var password = document.getElementById("password").valueOf();
    // var body = {
    //     login: login,
    //     password: password
    // };
    $.ajax({
        url: "/api/login-cred",
        method: "get",
        contentType: "application/json",
        data: {
            "login": login,
            "password": password
        },
        success: function (token) {
            window.localStorage.setItem("AUTH", token);
            alert(token);
        },
        error: function (msg) {
            console.log("error " + msg);
        }
    })
}