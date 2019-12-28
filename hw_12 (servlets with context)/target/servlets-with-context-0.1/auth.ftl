<html>
<head><title>Auth</title>

<body>
<h1>${err}</h1>
<form name="user" action="/logIn" method="post">
    Login: <input type="text" name="username" />	<br/>
    Password: <input type="text" name="password" />		<br/>
    <input type="submit" value="login" />
</form>


<form name="user" action="/register" method="post">
    Login: <input type="text" name="login" />	<br/>
    Password: <input type="text" name="username" />		<br/>
    <input type="submit" value="register" />
</form>

</body>
</html>