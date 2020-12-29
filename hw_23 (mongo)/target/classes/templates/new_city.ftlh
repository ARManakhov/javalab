<!DOCTYPE html>
<html lang="en">
<head>
    <title>Профиль</title>
    <#include "head.ftlh">
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU&amp;apikey=a209516e-9382-4f85-b52d-5ab690627f51" type="text/javascript"></script>
    <script src="/js/map_new.js"  type="text/javascript"></script>
</head>
<body>
<#include "navbar.ftlh">
<form method="post" action="new_city">
    <label>Название
        <input type="text" name="name">
    </label>
    <input type="hidden" name="cordX" id="cord_X" value="">
    <input type="hidden" name="cordY" id="cord_Y" value="">
    <div id = "map" style="height: 300px; width: 600px"></div>
    <button type="submit"> Сохранить</button>
</form>
</body>
</html>