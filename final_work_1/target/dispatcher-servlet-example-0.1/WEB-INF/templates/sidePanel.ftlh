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
</td>