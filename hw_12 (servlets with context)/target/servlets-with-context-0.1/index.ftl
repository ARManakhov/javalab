<html>
<head><title>ViralPatel.net - FreeMarker Hello World</title>

<body>
<#if username=="">
    <h1><a href="/auth">SignIn</a></h1>
<#else>
    <h1>Username: ${username}</h1>
</#if>
<table class="datatable">
    <tr>
        <th>name</th>
        <th>description</th>
        <th>cost</th>
        <th>addToCart</th>
    </tr>
    <#list products as product>
        <tr>
            <td>${product.name}</td>
            <td>${product.description}</td>
            <td>${product.cost}</td>
        </tr>
    </#list>
</table>
<h2>${curentPage} / ${maxPage} </h2>
</body>
</html>