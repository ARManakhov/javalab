<html>
<head><title>ViralPatel.net - FreeMarker Hello World</title>

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