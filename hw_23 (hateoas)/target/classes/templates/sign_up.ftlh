<!DOCTYPE html>
<html lang="en">
<head>
    <title>Регистрация</title>
    <#include "head.ftlh">
</head>
<body>
<#include "navbar.ftlh">


<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card">
            <header class="card-header">
                <h4 class="card-title mt-2">Регистрация</h4>
            </header>
            <article class="card-body">
                <form method="post" action="/sign_up">
                    <div class="form-row">
                        <div class="col form-group">
                            <label>Логин</label>
                            <input type="text" name="username" class="form-control" placeholder="">
                        </div>
                        <div class="col form-group">
                            <label>Mail</label>
                            <input type="text" name="mail" class="form-control" placeholder=" ">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col form-group">
                            <label>Пароль</label>
                            <input type="password" name="password" class="form-control" placeholder="">
                        </div>
                        <div class="col form-group">
                            <label>Повторите пароль</label>
                            <input type="password" name="passwordCheck" class="form-control" placeholder=" ">
                        </div>
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary btn-block"> Зарегестсрироваться</button>
                    </div>
                </form>
            </article>
            <div class="border-top card-body text-center">Уже есть аккаунт? <a href="/sign_in">Войти</a></div>
        </div>
    </div>
</div>
</body>
</html>