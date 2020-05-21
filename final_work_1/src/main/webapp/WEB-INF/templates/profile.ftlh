<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
<table>
    <tr>
        <td>
            <div>
                <a href="/">Home </a>
            </div>
            <div>
                <a href="/quit">Выйти </a>
            </div>
        </td>
        <td>
            <div>Имя : ${user.name}</div>
            <div>email : ${user.email}</div>
            <div>Зарегестрирован : ${user.createdAt}</div>
            <#if client?has_content >
                <#if client == user>
                    <div>
                        <div>Добавить пост</div>
                        <form action="/post" method="post">
                            <div>
                                <input name="header" placeholder="загаловок">
                            </div>
                            <div>
                                <input name="text" placeholder="text">
                            </div>
                            <div>
                                <input type="submit" value="отправить">
                            </div>
                        </form>
                    </div>
                </#if>
            </#if>
            <#if user.posts?size != 0>
                <div>
                    ${user.posts?size}
                </div>
                <#list user.posts as post>
                    <div>
                        <h1>${post.header}</h1>
                        <div>${post.text}</div>
                        <div>${post.author.name}</div>
                    </div>
                    <div>Коментарии</div>
                    <#list post.comments as comment>
                        <div>${comment.text}</div>
                        <div>${comment.author.name}</div>
                        <div>-</div>

                    </#list>
                    <form action="/post_comment/${post.id}" method="post">
                        <div>
                            <input name="text" placeholder="коментарий">
                            <input type="submit" value="отправить">
                        </div>
                    </form>
                    <div>-</div>
                </#list>

            <#else>
                <div>
                    Здесь пока нет постов
                </div>
            </#if>

        </td>
    </tr>
</table>
</body>
</html>