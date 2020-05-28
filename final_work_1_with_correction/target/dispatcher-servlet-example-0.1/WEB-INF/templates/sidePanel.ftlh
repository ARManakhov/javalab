<#import "/spring.ftl" as spring/>
<td>
    <div>
        <a href="/"><@spring.message "sidebar.home"/></a>
    </div>
    <#if navUser?has_content>
        <div>
            <a href="/profile"><@spring.message "sidebar.profile"/> </a>
        </div>
        <div>
            <form method="post" action="/logout">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="submit" value="<@spring.message "sidebar.quit"/>">
            </form>
        </div>
    <#else>
        <div>
            <a href="/signIn"><@spring.message "sidebar.signin"/></a>
        </div>
        <div>
            <a href="/signUp"><@spring.message "sidebar.signup"/></a>
        </div>
    </#if>
    <div>
        <a href="?locale=ru"><@spring.message "sidebar.ru"/></a>
    </div>
    <div>
        <a href="?locale=us"><@spring.message "sidebar.en"/></a>
    </div>
</td>