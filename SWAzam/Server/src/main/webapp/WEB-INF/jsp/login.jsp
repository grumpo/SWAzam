<%@    taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<title>SWAzam Administration</title>
<link href="css/stylesheet.css" rel="stylesheet" type="text/css" media="screen" />
</head>
<body>
    <form:form id="login" action="login.do" method="post" commandName="loginDetails">
	    <h1>Login</h1>
	    <fieldset id="inputs">
	        <form:input path="userName" id="username" type="text" placeholder="Username" required="true"></form:input> 
	        <form:input path="password" id="password" type="password" placeholder="Password" required="true"></form:input>
	    </fieldset>
	    <fieldset id="actions">
	        <input type="submit" id="submit" value="Login">
	        <a href="">Create Account</a>
	    </fieldset>
	    
	    <fieldset>
	    	<form:errors id="error"/>
	    </fieldset>
    </form:form>
</body>
</html>