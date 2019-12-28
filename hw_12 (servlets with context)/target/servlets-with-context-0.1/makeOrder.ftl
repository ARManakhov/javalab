<html>

<head><title>Shop</title></head>
<body>
<h1>Cart</h1>
<table class="datatable">
    <tr>
        <th>description</th>
        <th>make button</th>
    </tr>
    <#list addresses as addres>
        <tr>
            <td>${addres.description}</td>

            <td>
                <form action="/makeOrder?id=${addres.id}" method="post">
                    <button>to this address</button>
                </form>
            </td>
        </tr>
    </#list>
</table>
<form method="get" action="/makeOrder">
    <button>MakeOrder</button>
</form>
</body>
</html>