<!DOCTYPE html>
<html lang="en">
<head>
    <title>Вход</title>
    <#include "head.ftlh">
</head>
<body>
<#include "navbar.ftlh">


<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <header class="card-header">
                <h4 class="card-title mt-2">Вход</h4>
            </header>
            <article class="card-body">
                <form method="post" action="/auth">
                    <div class="form-row">
                        <div class="col form-group">
                            <label>Логин</label>
                            <input type="text" name="username" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col form-group">
                            <label>Пароль</label>
                            <input type="password" name="password" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col form-row">
                            <button type="submit" class="btn btn-primary btn-block"> Войти</button>
                            <a type="submit" class="btn btn-primary btn-block" href="/sign_in_mail"> Вход через почту</a>
                        </div>
                    </div>
                </form>
            </article>
            <div class="border-top card-body text-center">Нет аккаунта? <a href="/sign_up">Регистрация</a></div>
        </div>
    </div>
</div>
</body>
</html>