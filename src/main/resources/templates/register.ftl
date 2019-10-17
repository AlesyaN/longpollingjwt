<html>
<head>
    <title>Long polling</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
</head>
<body>
<form action="/register" method="post">
    <input type="text" name="login" id="login"><br>
    <input type="password" name="password" id="password"><br>
    <input type="submit" value="Register"><br>
    <a href="/login">Already have an account? Log in!</a>
</form>
<script>
    window.onload = function () {
        if (window.localStorage.getItem("AUTH") !== null) {
            $.ajax({
                url: "/api/login-token",
                method: "get",
                contentType: "application/json",
                headers: {
                    "AUTH": window.localStorage.getItem("AUTH")
                },
                success: function () {
                    window.location.href = '/chat'
                }
            })
        }
    }
</script>
</body>
</html>