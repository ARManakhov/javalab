<html>
<link rel="stylesheet" type="text/css" href="style.css">
<head><title>Shop</title></head>
<body>
<h1>Cart</h1>
<table class="datatable">
    <tr>
        <th>description</th>
        <th>delete</th>
    </tr>
    <#list addresses as addres>
        <tr>
            <td>${addres.description}</td>

            <td>
                <form action="/deleteaddress?id=${addres.id}" method="post">
                    <button>delete</button>
                </form>
            </td>
        </tr>
    </#list>
</table>
<form action="/newaddress" method="post">
    <input type="text" name="description">
    <button>add</button>
</form>

</body>
</html>