<%-- 
    Document   : updatePassword
    Created on : 26-Nov-2021, 12:44:56
    Author     : BritishWaldo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/updatePassword_style.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <title>Home nVentory</title>
    </head>
    <body>
        <h1>Home nVentory</h1>

        <h2>${server_message}</h2>

        <form action="" method="post">
            <table id='passwordTable'>
                <tr class="headerRow">
                    <th>
                        Update Account Password 
                    </th>
                </tr>
                <tr>
                    <td>
                        <input type="password" name="updatePasswordCurrent" id="updatePasswordCurrent" placeholder="Enter your current password"/>
                    </td>
                </tr>
                <tr class="bigSpacerRow"></tr>
                <tr>
                    <td>
                        <input type="password" name="updatePasswordNew" id="updatePasswordNew" placeholder="Enter your new password"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="password" name="updatePasswordConfirm" id="updatePasswordConfirm" placeholder="Confirm your new password"/>
                    </td>
                </tr>
                <tr class="giantSpacerRow"></tr>
                <tr>
                    <td>
                        <input type="submit" value="Update Account Password" class="updateButton"/>
                        <input type="hidden" name="action" id="action" value="updatePassword"/>
                    </td>
                </tr>         
            </table>
        </form>
    </body>
</html>
