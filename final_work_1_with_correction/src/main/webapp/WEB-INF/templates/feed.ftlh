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
            <div><@spring.message "feed.subscriptions"/></div>
            <#if posts?size != 0>

                <#list posts as post>
                    <div>
                        <h1>${post.header}</h1>
                        <div>${post.text}</div>
                        <div>${post.author.name}</div>
                        <#if post.author.id == navUser.id>
                            <div>
                                <form method="post" action="/post_delete/${post.id}">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="submit" value="<@spring.message "post.delete"/>">
                                </form>
                            </div>
                        </#if>
                    </div>
                    <div><@spring.message "post.comments"/></div>
                    <#list post.comments as comment>
                        <div>${comment.text}</div>
                        <div>${comment.author.name}</div>
                        <div>-</div>

                    </#list>
                    <form action="/post_comment/${post.id}" method="post">
                        <!-- inlcude csrf token -->
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div>
                            <input name="text" placeholder="<@spring.message "comment.comment"/>">
                            <input type="submit" value="<@spring.message "profile.send"/>">
                        </div>
                    </form>
                    <div>-</div>
                </#list>

            <#else>
                <div>
                    <@spring.message "post.noposts"/>
                </div>
            </#if>

        </td>
    </tr>
</table>
</body>
</html>