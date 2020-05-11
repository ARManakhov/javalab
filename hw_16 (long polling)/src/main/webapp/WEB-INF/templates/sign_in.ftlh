<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, userSimple-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<h1>вход</h1>
<div>
    <form action="/authentication" method="post">
        <div>
            <input name="username" placeholder="Email">
        </div>
        <div>
            <input type="password" name="password" placeholder="Пароль">
        </div>
        <div>
            <input type="submit" value="войти">
        </div>
        <a href="/signUp"> У меня нет аккаунта</a>
    </form>
</div>
</body>
</html>