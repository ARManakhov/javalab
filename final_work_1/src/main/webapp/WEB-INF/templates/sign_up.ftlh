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
<h1>Регистрация</h1>
<div>
    <form action="/signUp" method="post">
        <div>
            <input name="name" placeholder="Имя">
        </div>
        <div>
            <input name="email" placeholder="Email">
        </div>
        <div>
            <input type="password" name="password" placeholder="Пароль">
        </div>
        <div>
            <input type="password" name="passwordCheck" placeholder="Повтор пароля">
        </div>
        <div>
            <input type="submit" value="зарегистрироватся">
        </div>
        <a href="/signIn"> Уже есть аккаунт</a>
    </form>
</div>
</body>
</html>