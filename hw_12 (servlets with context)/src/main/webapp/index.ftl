<html>
<head><title>ViralPatel.net - FreeMarker Hello World</title>

<body>
<h1><a href="/auth">SignIn</a></h1>

<table class="datatable">
    <tr>
        <th>name</th>  <th>description</th><th>cost</th><th>addToCart</th>
    </tr>
    <#list products as product>
        <tr>
            <td>${product.name}</td> <td>${product.description}</td> <td>${product.cost}</td>
        </tr>
    </#list>
</table>
<h2>${curentPage} / ${maxPage} </h2>
</body>
</html>