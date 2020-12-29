<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Пошло/поехало</a>


    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
        </ul>
        <#if navUser?has_content>
            <a class="float-right btn btn-outline-primary mt-1" href="/profile/${navUser.id}">${navUser.name} </a>
            <#if navUser.role == "ADMIN">
                            <a class="float-right btn btn-outline-primary mt-1" href="/admin">Админка </a>
            </#if>
        <#else>
            <a class="float-right btn btn-outline-primary mt-1" href="/sign_in">Вход</a>
            <a class="float-right btn btn-outline-primary mt-1" href="/sign_up">Регистраия</a>
        </#if>
    </div>
</nav>