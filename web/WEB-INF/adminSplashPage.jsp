<%-- 
    Document   : adminSplashPage
    Created on : 21-Nov-2021, 10:44:30
    Author     : BritishWaldo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/admin_style.css">
        <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Round" rel="stylesheet">
        <title>Home nVentory</title>
    </head>
    <body>
        <main>
            <ul id="navBar">
                    <li><a class="logout"  href="login">Logout</a></li>
                    <li><a class="active" href="admin">Admin</a></li>
                    <li><a href="inventory">Inventory</a></li>
                    <li id="menuRight"><a href="account" class="account">Welcome, ${username}</a></li>
            </ul>

            <h1>Administrative Dashboard</h1>

            <table>
                <tr>
                    <td>
                        <form method="post" action="">
                            <input type="submit" name="adminSubmit" id="adminSubmit" value ="User Management"/>
                        </form>
                    </td>
                </tr>
                <tr class="bigSpacerRow"></tr>
                <tr>
                    <td>
                        <form method="post" action="">
                            <input type="submit" name="adminSubmit" id="adminSubmit" value ="Role Management" disabled/>
                        </form>
                    </td>
                </tr>
                <tr class="bigSpacerRow"></tr>
                <tr>
                    <td>
                        <form method="post" action="">
                            <input type="submit" name="adminSubmit" id="adminSubmit" value ="Category Management"/>
                        </form>
                    </td>
                </tr>
            </table>
        </main>
        <footer class="centered">
            *Role management has not yet been implemented.
        </footer>
    </body>
</html>
