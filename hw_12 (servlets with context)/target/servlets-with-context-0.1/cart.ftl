<html>

<link rel="stylesheet" type="text/css" href="style.css">
<head><title>Shop</title></head>
<body>
<h1>Cart</h1>
<table class="datatable">
    <tr>
        <th>name</th>
        <th>description</th>
        <th>cost</th>
    </tr>
    <#list products as product>
        <tr>
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td>${product.cost}</td>
        </tr>
    </#list>
</table>
<form method="get" action="/makeOrder">
    <button>MakeOrder</button>
</form>
</body>
</html>