<%-- 
    Document   : account
    Created on : 25-Nov-2021, 22:30:43
    Author     : BritishWaldo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/account_style.css">
        <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Round" rel="stylesheet">
        <title>Home nVentory</title>
    </head>
    <body>
        <main>
            <ul id="navBar">
                    <li><a class="logout"  href="login">Logout</a></li>
                    <c:if test="${showAdminLink}">
                        <c:out value = "
                                        <li><a href='admin'>Admin</a></li>
                                        " escapeXml = "false">
                        </c:out>
                    </c:if>
                    <li><a href="inventory">Inventory</a></li>
                    <li id="menuRight"><a class="account">Welcome, ${username}</a></li>
            </ul>

            <h1>Account Dashboard</h1>
            
            <h2>${server_message}</h2>
            
            <table id="informationTable">
                <tr>
                    <th colspan="2">
                        Account information
                    </th>
                </tr>
                <tr>
                    <td class="rightAlign rightAlign">
                        <b>Username:</b> 
                    </td>
                    <td class="leftAlign leftCell">
                        ${username}
                    </td>
                </tr>
                <tr>
                    <td class="rightAlign rightAlign">
                        <b>E-mail Address:</b>
                    </td>
                    <td class="leftAlign leftCell">
                        ${email}
                    </td>
                </tr>
                <tr>
                    <td class="rightAlign rightAlign">
                        <b>First Name:</b>
                    </td>
                    <td class="leftAlign leftCell">
                        ${firstName}
                    </td>
                </tr>
                <tr>
                    <td class="rightAlign rightAlign">
                        <b>Last Name:</b>
                    </td>
                    <td class="leftAlign leftCell">
                        ${lastName}
                    </td>
                </tr>
            </table>

            <table>
                <tr>
                    <th>
                        Update Account information
                    </th>
                </tr>
                <form action="" method="post">
                <tr>
                    <td>
                        <input type="email" name="accountEmail" id="accountEmail" placeholder="New E-mail Address"/>
                    </td>
                </tr>
                <tr class="smallSpacerRow"></tr>
                <tr>
                    <td>
                        <input type="text" name="accountFirstName" id="accountFirstName" placeholder="New First Name"/>
                    </td>
                </tr>
                <tr class="smallSpacerRow"></tr>
                <tr>
                    <td>
                        <input type="text" name="accountLastName" id="accountLastName" placeholder="New Last Name"/>
                    </td>
                </tr>
                <tr class="smallSpacerRow"></tr>
                <tr>
                    <td>
                        <input type="submit" name="accountChangeSubmit" id="accountChangeSubmit" value="Update Account Information"/>
                        <input type="hidden" name="action" id="action" value="accountChange"/>
                    </td>
                </tr>
                </form>
                <tr class="giantSpacerRow"></tr>
                <tr>
                    <form method="post" action="">
                        <td>
                            <input type="submit" name="passwordChangeSubmit" id="passwordChangeSubmit" value ="Change Password"/>
                            <input type="hidden" name="action" id="action" value="passwordChange"/>
                        </td>
                    </form>
                </tr>
                <tr class="giantSpacerRow"></tr>
                <tr>
                    <form method="post" action="">
                        <td>
                            <input type="submit" name="deactivateSubmit" id="deactivateSubmit" value ="Deactivate Account"/>
                            <input type="hidden" name="action" id="action" value="deactivate"/>
                        </td>
                    </form>
                </tr>
            </table>
        </main>
    </body>
</html>
