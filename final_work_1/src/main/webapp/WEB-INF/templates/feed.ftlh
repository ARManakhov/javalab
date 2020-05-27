<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
<table>
    <tr>
        <#include "sidePanel.ftlh">
        <td>
            <div>Подписки</div>
            <#if posts?size != 0>
                <div>
                    ${posts?size}
                </div>
                <#list posts as post>
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