<%-- 
    Document   : java_error_general
    Created on : 24-Nov-2021, 20:36:35
    Author     : BritishWaldo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home nVentory</title>
    </head>
    <body>
        <h1>Oops!</h1>
        
        <h3>
            Something appears to have gone wrong.
            <br>
            Here are some helpful links to get you back on track:
            <ul>
                <li><a href="login">Login Page</a></li>
                <li><a href="inventory">Inventory Page</a></li>
                <li><a href="account">Account Page</a></li>
            </ul>
        </h3>

        <h4>
            The source of the problem was a ${pageContext.errorData.statusCode} error when you tried to access ${pageContext.errorData.requestURI} :(
        </h4>
    </body>
</html>
