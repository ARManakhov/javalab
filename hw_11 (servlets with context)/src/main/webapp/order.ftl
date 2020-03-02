<html>

<link rel="stylesheet" type="text/css" href="style.css">
<head><title>Shop</title></head>
<body>

<h4>Order id:${order.id}</h4>
<h4>Order address:${order.dtoAddress.description}</h4>
<h4>Order status:${order.status}</h4>
<h4>Order products:</h4>
<table class="datatable">
    <tr>
        <th>name</th>
        <th>description</th>
        <th>cost</th>
    </tr>
    <#list order.dtoProductList as product>
        <tr>
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td>${product.cost}</td>
        </tr>
    </#list>
</table>

</body>
</html>