<%-- 
    Document   : forgotPassword
    Created on : 11-Nov-2021, 14:37:48
    Author     : BritishWaldo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/forgotPassword_Style.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <title>Home nVentory</title>
    </head>
    <body>
        <h1>Home nVentory</h1>
        
        <h3>
            Please enter the username or e-mail address associated with your account then click the "Reset Password" button.
        </h3>
        
        <form action="" method="post">
            <table id='resetTable'>
                <tr class="headerRow">
                    <th>
                        Password Reset
                    </th>
                </tr>
                <tr>
                    <td>
                        <input type='text' name='resetIdentifier' id='resetIdentifier' placeholder='Username or E-mail Address'/>
                    </td>
                </tr>
                <tr class="bigSpacerRow"></tr>
                <tr>
                    <td>
                        <input type="submit" value="Reset Password" class="resetPasswordButton"/>
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
                        <a href="login">Return to login page</a>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>