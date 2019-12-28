<html>

<head><title>Shop</title></head>
<body>
<table class="datatable">
    <tr>
        <th>id</th>
        <th>address</th>
        <th>status</th>
        <th>view</th>
    </tr>
    <#list orders as order>
        <tr>
            <td>${order.id}</td>
            <td>${order.dtoAddress.description}</td>
            <td>${order.status}</td>
            <td><a href="order?id=${order.id}"><button type="submit">View</button></a></td>
        </tr>
    </#list>
</table>

</body>
</html>