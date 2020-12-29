<!DOCTYPE html>
<html lang="en">
<head>
    <title>Профиль</title>
    <#include "head.ftlh">
    <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU&amp;apikey=a209516e-9382-4f85-b52d-5ab690627f51" type="text/javascript"></script>
    <script src="/js/map_mov.js"  type="text/javascript"></script>
</head>
<body onload="setpoints([[${movement.from.cordX},${movement.from.cordY}],[${movement.to.cordX},${movement.to.cordY}]])">
<#include "navbar.ftlh">
<div>Место отбытия : ${movement.from.name}</div>
<div>Место прибытия : ${movement.to.name }</div>
<div>Время отбытия: ${movement.departureDate}</div>
<div>Время прибытия: ${movement.arrivalDate}</div>
<div>Тип транспорта: ${movement.transport.transportType}</div>
<div>Модель: ${movement.transport.model}</div>
<div>Описание: ${movement.transport.description}</div>
<div>Цена: ${movement.price}</div>
<div id = "map" style="height: 300px; width: 600px"></div>
<#if movement.expired == false && navUser?exists >
    <div>
        <button class="float-right btn btn-outline-primary mt-1"  id="book">Забронировать</button>
    </div>

    <div hidden id="msg_1">
        Уже забронированно
    </div>
    <div hidden id="msg_2">
        Забронированно
    </div>

    <script>
        $('#book').click(function () {
            $.ajax
            ({
                url: '/booking/add/${movement.id}',
                type: 'post',
                function(rawData){
                    let data = JSON.parse(rawData);

                }
            });
        });
    </script>

</#if>
</body>
</html>