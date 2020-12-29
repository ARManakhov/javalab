<!DOCTYPE html>
<html lang="en">
<head>
    <title>Профиль</title>
    <#include "head.ftlh">
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="/js/profile_websocket.js"></script>
</head>
<body onload="connect()">
<#include "navbar.ftlh">
<div>Логин : ${user.name}</div>
<div>Email : ${user.mail}</div>
<div>Зарегестрирован : ${user.mail}</div>

<#if navUser.role == "ADMIN">
    <div>Статус : ${user.state}</div>
    <div>Роль : ${user.role}</div>
</#if>
<#if navUser.id == user.id || navUser.role=="ADMIN">
    <div>Забронированные поездки</div>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">из</th>
            <th scope="col">в</th>
            <th scope="col">время отправления</th>
            <th scope="col">время прибытия</th>
            <th scope="col">тип транспорта</th>
            <th scope="col">цена</th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody id="table_1">
        <#list user.bookings as booking>
            <#if booking.movement.expired == false>
                <tr id="movement_${booking.movement.id}">
                    <th scope="col">${booking.movement.from.name}</th>
                    <th scope="col">${booking.movement.to.name}</th>
                    <th scope="col">${booking.movement.departureDate}</th>
                    <th scope="col">${booking.movement.arrivalDate}</th>
                    <th scope="col">${booking.movement.transport.transportType}</th>
                    <th scope="col">${booking.movement.price}</th>

                    <th scope="col">
                        <button onclick="

                                $.post
                                ({
                                url: '/booking/delete/${booking.id}',
                                function(){}
                                });

                                " class="float-right btn btn-outline-primary mt-1">снять бронь
                        </button>
                    </th>
                    <th scope="col"><a class="float-right btn btn-outline-primary mt-1"
                                       href="/movement/${booking.movement.id}">Подробнее</a></th>
                </tr>
            </#if>
        </#list>
        </tbody>
    </table>

    <div>Истекшие поездки</div>
    <table class="table">
        <thead>
        <tr>
            <th scope="col">из</th>
            <th scope="col">в</th>
            <th scope="col">время отправления</th>
            <th scope="col">время прибытия</th>
            <th scope="col">тип транспорта</th>
            <th scope="col">цена</th>
            <th scope="col"></th>
            <th scope="col"></th>

        </tr>
        </thead>
        <tbody id="table_2">
        <#list user.bookings as booking>
            <#if booking.movement.expired == true>
                <tr id="movement_${booking.movement.id}">
                    <th scope="col">${booking.movement.from.name}</th>
                    <th scope="col">${booking.movement.to.name}</th>
                    <th scope="col">${booking.movement.departureDate}</th>
                    <th scope="col">${booking.movement.arrivalDate}</th>
                    <th scope="col">${booking.movement.transport.transportType}</th>
                    <th scope="col">${booking.movement.price}</th>
                    <th scope="col"><a class="float-right btn btn-outline-primary mt-1"
                                       href="/movement/${booking.movement.id}">Подробнее</a></th>
                </tr>
            </#if>
        </#list>
        </tbody>
    </table>
</#if>

</body>
</html>