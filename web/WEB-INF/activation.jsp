<%-- 
    Document   : login
    Created on : 11-Nov-2021, 14:37:48
    Author     : BritishWaldo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/activation_style.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <title>Home nVentory</title>
    </head>
    <body>
        <h1>Home nVentory</h1>

        <h2>${server_message}</h2>

        <form action="login" method="post">
            <table id='activationTable'>
                <tr>
                    <td>
                        <input type="submit" name="activationButton" id="activationButton" value="Go to Login page"/>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>