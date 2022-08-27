<%-- 
    Document   : confirmDeactivation
    Created on : 11-Nov-2021, 14:37:48
    Author     : BritishWaldo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/confirmDeactivation_style.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <title>Home nVentory</title>
    </head>
    <body>
        <h1>Home nVentory</h1>

        <h2>${server_message}</h2>

        <form action="" method="post">
            <table id='deactivateTable'>
                <tr class="headerRow">
                    <th>
                        Deactivate your account. 
                    </th>
                </tr>
                <tr>
                    <td>
                        <input type="password" name="deactivatePassword" id="deactivatePassword" placeholder="Enter password to deactivate account"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="Deactivate Account" class="deactivateButton"/>
                        <input type="hidden" name="action" id="action" value="deactivateConfirm"/>
                    </td>
                </tr>         
            </table>
        </form>
    </body>
</html>