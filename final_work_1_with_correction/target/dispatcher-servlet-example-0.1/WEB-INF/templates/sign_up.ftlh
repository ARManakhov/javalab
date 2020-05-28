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
        <#include "sidePanel.ftlh">
        <td>
            <@spring.message "auth.signup"/>
            <div>
                <form action="/signUp" method="post">
                    <!-- inlcude csrf token -->
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div>
                        <input name="name" placeholder="<@spring.message "auth.username"/>">
                    </div>
                    <div>
                        <input name="email" placeholder="<@spring.message "auth.mail"/>">
                    </div>
                    <div>
                        <input type="password" name="password" placeholder="<@spring.message "auth.pass"/>">
                    </div>
                    <div>
                        <input type="password" name="passwordCheck" placeholder="<@spring.message "auth.passcheck"/>">
                    </div>
                    <div>
                        <input type="submit" value="<@spring.message "auth.signupbutton"/>">
                    </div>
                    <a href="/signIn"> <@spring.message "auth.hasaccountbutton"/></a>
                </form>
            </div>
        </td>
    </tr>
</table>
</body>
</html>