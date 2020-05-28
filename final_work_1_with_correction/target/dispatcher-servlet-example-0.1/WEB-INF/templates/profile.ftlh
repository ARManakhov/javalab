<!doctype html>
<html lang="en" >
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
<table>
    <tr>
        <#include "sidePanel.ftlh">
        <td>
            <div><@spring.message "profile.name"/>${user.name}</div>
            <div><@spring.message "profile.email"/>${user.email}</div>
            <div><@spring.message "profile.registrated"/> ${user.createdAt}</div>
            <#if navUser?has_content>
                <#if navUser.id == user.id>
                    <div>
                        <div><@spring.message "profile.addpost"/></div>
                        <form action="/post" method="post">
                            <!-- inlcude csrf token -->
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <div>
                                <input name="header" placeholder="<@spring.message "post.header"/>">
                            </div>
                            <div>
                                <input name="text" placeholder="<@spring.message "post.text"/>">
                            </div>
                            <div>
                                <input type="submit" value="<@spring.message "profile.send"/>">
                            </div>
                        </form>
                    </div>
                <#else>
                    <form action="/subscribe/${user.id}" method="post">
                        <!-- inlcude csrf token -->
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button><@spring.message "profile.subscribe"/></button>
                    </form>
                </#if>

            </#if>
            <#if user.posts?size != 0>
                <#list user.posts as post>
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