<form method="${form.method}" action="${form.action}">
    <#list form.inputs as input>
        <input type="${input.type}" name="${input.name}" placeholder="${input.placeholder}">
    </#list>
</form>