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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/forgotPassword_Style.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <title>Home nVentory</title>
    </head>
    <body>
        <h1>Home nVentory</h1>
        
        <h3>
            Please enter a new password for your account and then click the "Change Password" button.
        </h3>
        
        <form action="" method="post">
            <table id='resetTable'>
                <tr class="headerRow">
                    <th>
                        Password Change
                    </th>
                </tr>
                <tr>
                    <td>
                        <input type='password' name='resetPassword' id='resetPassword' placeholder='Enter new password here'/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type='password' name='resetPasswordConfirm' id='resetPasswordConfirm' placeholder='Confirm new password here'/>
                    </td>
                </tr>
                <tr class="bigSpacerRow"></tr>
                <tr>
                    <td>
                        <input type="submit" value="Change Password" class="resetPasswordButton"/>
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
                <input type="hidden" name="uuid" id="uuid" value="${uuid}"/>
            </table>
        </form>
    </body>
</html>