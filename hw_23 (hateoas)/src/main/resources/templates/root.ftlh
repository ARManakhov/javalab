<!DOCTYPE html>
<html lang="en">
<head>
    <title>Главная</title>
    <#include "head.ftlh">
    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
    <script src="/js/root_websocket.js"></script>
</head>
<body onload="connect()">
<#include "navbar.ftlh">
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
    </tr>
    </thead>
    <tbody id="table">
    <#list movements as movement>
        <#if movement.expired == false>
            <tr id="movement_${movement.id}">
                <th scope="col">${movement.from.name}</th>
                <th scope="col">${movement.to.name}</th>
                <th scope="col">${movement.departureDate}</th>
                <th scope="col">${movement.arrivalDate}</th>
                <th scope="col">${movement.transport.transportType}</th>
                <th scope="col">${movement.price}</th>
                <th scope="col"><a class="float-right btn btn-outline-primary mt-1" href="/movement/${movement.id}">Подробнее</a>
                </th>
            </tr>
        </#if>
    </#list>
    </tbody>
</table>
</body>
</html>