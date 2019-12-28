<html>

<link rel="stylesheet" type="text/css" href="style.css">
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
        <#if isAdmin>
            <th>delete</th>
        </#if>

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
            <#if isAdmin>
                <td>
                    <form action="delProduct?id=${product.id}" method="post">
                        <button>delete</button>
                    </form>
                </td>
            </#if>
        </tr>
    </#list>
</table>

<h2><a href="/?page=${prevPage}"> < </a>${curentPage} / ${maxPage}<a href="/?page=${nextPage}"> > </a></h2>
<#if isAdmin>
    <h1>New Product</h1>
    <form action="/newproduct" method="post">
        <div>
            <label> Name
                <input name="name">
            </label>
        </div>
        <div>
            <label> Description
                <input name="description">
            </label>
        </div>
        <div>
            <label>Cost
                <input name="cost">
            </label>
        </div>
        <div>
            <input type="submit">
        </div>
    </form>
</#if>
</body>
</html>