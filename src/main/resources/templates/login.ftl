<html>
<head>
    <title>Long polling</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
</head>
<body>
<input type="text" name="login" id="login"><br>
<input type="password" name="password" id="password"><br>
<input onclick="login()" type="submit" value="Sign in"><br>
<a href="/register">Don't have an account? Register!</a>
<script>
    window.onload = function () {
        var authString = window.localStorage.getItem("AUTH");
        if (authString !== null) {
            var auth = JSON.parse(authString);
            if (new Date(auth.accessExpiresIn) < new Date ) {
                $.ajax({
                    url: "/api/refresh-token",
                    method: "post",
                    data: {
                        "refreshToken": auth.refreshToken
                    },
                    success: function (token) {
                        window.localStorage.setItem("AUTH", JSON.stringify(token));
                    }
                })
            }
            $.ajax({
                url: "/api/login-token",
                method: "get",
                contentType: "application/json",
                headers: {
                    "AUTH": auth
                },
                success: function () {
                    window.location.href = '/chat'
                }
            })
        }
    };

    function login() {
        var login = document.getElementById("login").value;
        var password = document.getElementById("password").value;
        $.ajax({
            url: "/api/login-cred",
            method: "get",
            data: {
                login: login,
                password: password
            },
            success: function (token) {
                window.localStorage.setItem("AUTH", JSON.stringify(token));
                window.location.href = '/chat'
            }
        });
    }
</script>
</body>
</html>