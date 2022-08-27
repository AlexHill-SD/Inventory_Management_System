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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/login_style.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <title>Home nVentory</title>
    </head>
    <body>
        <h1>Home nVentory</h1>

        <h2>${server_message}</h2>

        <form action="" method="post">
            <table id='loginTable'>
                <tr class="headerRow">
                    <th>
                        Login
                    </th>
                </tr>
                <tr>
                    <td>
                        <input type='text' name='loginUsername' id='loginUsername' placeholder='Username'/>
                    </td>
                </tr>
                
                <tr>
                    <td>
                        <input type='password' name='loginPassword' id='loginPassword' placeholder='Password'/>
                    </td>
                </tr>
                <tr class="bigSpacerRow"></tr>
                <tr>
                    <td>
                        <input type="submit" value="Log in" class="loginButton"/>
                    </td>
                </tr>
                <tr class="bigSpacerRow"></tr>
                <tr>
                    <td>
                        <input type="reset" value="Reset" class='resetButton'/>
                    </td>
                </tr>
                <tr class="smallSpacerRow"></tr>
                <tr>
                    <td>
                        <a href="forgotpassword"><span class="material-icons">help</span>Forgotten your password?</a>
                    </td>
                </tr>
                <tr class="smallSpacerRow"></tr>
                <tr>
                    <td>
                        <a href="register"><span class="material-icons">person_add</span>Register a new account</a>
                    </td>
                </tr>
                
            </table>
        </form>
    </body>
</html>