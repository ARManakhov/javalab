<#import "/spring.ftl" as spring/>
<td>
    <div>
        <a href="/">Home </a>
    </div>
    <#if navUser?has_content>
        <div>
            <a href="/profile">профиль </a>
        </div>
        <div>
            <a href="/logout">Выйти </a>
        </div>
    <#else>
        <div>
            <a href="/signIn">вход</a>
        </div>
        <div>
            <a href="/signUp">регистрация</a>
        </div>
    </#if>
    <div>
        <a href="?locale=ru">Русский </a>
    </div>
    <div>
        <a href="?locale=us">English</a>
    </div>
</td>