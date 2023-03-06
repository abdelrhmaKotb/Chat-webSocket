<html>

<head>
    <title>Login page</title>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" href="presentation/style/login.css">
</head>

<body>
    <h1 style="text-align: center;">Login</h1>
    <br>
    <div class="div1">
        <div class="div2">
            <form name="getText" method="post" action="presentation/views/chat.jsp">
                <label for="username"><strong>Username</strong></label> <br>
                <input type="text" name="username" id="username" placeholder="any@example.com" required> <br> <br>

                male <input type="radio" name="gender" value="male">
                female <input type="radio" name="gender" value="female">

                <input type="submit" value="Connect" name="log_btn" id="log_btn" onblur="">

            </form>
        </div>

    </div>

</body>

</html>

