<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, userSimple-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
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
            <#if user.posts?size != 0>
                <#list user.posts as post>
                    <div>
                        <h1>${post.header}</h1>
                        <div>${post.text}</div>
                        <div>${post.author.name}</div>
                    </div>
                        <#list post.comments as comment>
                            <div>${comment.text}</div>
                            <div>${comment.author.name}</div>
                        </#list>
                    <form action="/post_comment/${post.id}" method="post">
                        <div>
                            <input name="text" placeholder="коментарий">
                            <input type="submit" value="отправить">
                        </div>
                        <a href="/signUp"> У меня нет аккаунта</a>
                    </form>
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