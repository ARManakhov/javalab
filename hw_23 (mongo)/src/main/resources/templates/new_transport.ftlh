<!DOCTYPE html>
<html lang="en">
<head>
    <title>Профиль</title>
    <#include "head.ftlh">
</head>
<body>
<#include "navbar.ftlh">
<form method="post" action="new_transport">
    <label>Модель
        <input type="text" name="model">
    </label>
    <label>Описание
        <input type="text" name="description">
    </label>
    <label for="type">Тип транспорта
    </label>
    <select id="type" name="transportType">
        <option value="BUS">Автобус</option>
        <option value="TRAIN" >Поезд</option>
        <option value="PLANE">Самолет</option>
    </select>
    <button type="submit"> Сохранить</button>
</form>
</body>
</html>