<#-- @ftlvariable name="_csrf" type="org.springframework.security.web.csrf.CsrfToken" -->
<#-- @ftlvariable name="form" type="com.ecommerce.domain.User" -->
<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Create a new user</title>
    </head>

    <body>
    <h1>Register a new user</h1>

    <form role="form" name="form" action="" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div>
            <label for="email">Email address</label>
            <input type="email" name="email" id="email" required autofocus/>
        </div>
        <div>
            <label for="password">Password</label>
            <input type="password" name="password" id="password" required/>
        </div>
        <div>
            <label for="passwordConfirm">Repeat</label>
            <input type="password" name="passwordConfirm" id="passwordConfirm" required/>
        </div>
        <button type="submit">Save</button>
    </form>

    <@spring.bind "form" />
    <#if spring.status.error>
        <ul>
            <#list spring.status.errorMessages as error>
                <li>${error}</li>
            </#list>
        </ul>
    </#if>

    </body>
</html>