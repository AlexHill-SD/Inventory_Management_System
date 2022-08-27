<%-- 
    Document   : register
    Created on : 11-Nov-2021, 14:37:48
    Author     : BritishWaldo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/register_style.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <title>Home nVentory</title>
    </head>
    <body>
        <h1>Home nVentory</h1>

        <h2>${server_message}</h2>

        <form action="" method="post">
            <table id='registerTable'>
                <tr class="headerRow">
                    <th>
                        Register 
                    </th>
                </tr>
                <tr>
                    <td>
                        <input type="email" name="registerEmail" id="registerEmail" placeholder="E-mail Address" value="${registerEmail}"/>
                    </td>
                </tr>
                <tr class="bigSpacerRow"></tr>
                <tr>
                    <td>
                        <input type='text' name='registerUsername' id='registerUsername' placeholder='Username' value="${registerUsername}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type='password' name='registerPassword' id='registerPassword' placeholder='Password'/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type='password' name='registerConfirmPassword' id='registerConfirmPassword' placeholder='Confirm Password'/>
                    </td>
                </tr>
                <tr class="bigSpacerRow"></tr>
                <tr>
                    <td>
                        <input type="text" name="registerFirstName" id="registerFirstName" placeholder="First Name" value="${registerFirstName}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type="text" name="registerLastName" id="registerLastName" placeholder="Last Name" value="${registerLastName}"/>
                    </td>
                </tr>
                <tr class="bigSpacerRow"></tr>
                <tr>
                    <td>
                        <input type="submit" value="Register New Account" class="registerButton"/>
                    </td>
                </tr>
                <tr class="bigSpacerRow"></tr>
                <tr>
                    <td>
                        <input type="reset" value="Reset" class='resetButton'/>
                    </td>
                </tr>             
            </table>
        </form>
    </body>
</html>