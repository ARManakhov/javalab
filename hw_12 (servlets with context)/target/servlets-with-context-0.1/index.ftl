<html>

<head><title>Shop</title></head>
<body>
<#if username=="">
    <h1><a href="/auth">SignIn</a></h1>
<#else>
    <h1>Username: ${username}</h1>
    <h1><a href="/getcart">Cart</a></h1>
    <h1><a href="/orders">Orders</a></h1>
    <h1><a href="/addresses">Addresses</a></h1>

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
            <td>
                <form action="addtocart?id=${product.id}" method="post">
                    <button>add to cart</button>
                </form>
            </td>
        </tr>
    </#list>
</table>

<h2><a href="/?page=${prevPage}"> < </a>${curentPage} / ${maxPage}<a href="/?page=${nextPage}"> > </a> </h2>

</body>
</html>