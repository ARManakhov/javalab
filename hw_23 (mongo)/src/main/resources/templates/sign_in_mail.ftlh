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
                <h4 class="card-title mt-2">Вход через почту</h4>
            </header>
            <article class="card-body">
                <form method="post" action="/signInByMail">
                    <div class="form-row">
                        <div class="col form-group">
                            <label>Email</label>
                            <input type="text" name="mail" class="form-control" placeholder="">
                        </div>
                    </div>
                    <div class="form-row">
                        <div class="col form-row">
                            <button type="submit" class="btn btn-primary btn-block"> Войти</button>
                            <a type="submit" class="btn btn-primary btn-block" href="/sign_in"> Обычный вход</a>
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