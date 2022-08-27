<%-- 
    Document   : Admin
    Created on : 11-Nov-2021, 14:36:06
    Author     : BritishWaldo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/userManager_style.css">
        <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Round" rel="stylesheet">
        <title>Home nVentory</title>
    </head>
    <body>
        <main>
            <ul id="navBar">
                <li><a class="logout"  href="../login">Logout</a></li>
                <li><a class="active" href="../admin">Admin</a></li>
                <li><a href="../inventory">Inventory</a></li>
                <li id="menuRight"><a href="account" class="account">Welcome, ${username}</a></li>
            </ul>

            <h1>User Management Dashboard</h1>
            
            <h2>${server_message}</h2>

            <table id='outerTable'>
                <tr><td>
                <c:if test="${showEditForm}">
                    <c:out  value=   "<table id='editTable'><form action='' method='post' autocomplete='off'><tr><th class='addFormHeader'>Edit User  <input type='submit' name='closeForm' value='close' class='material-icons-round rightIconAlign plainIcon'/></th></tr>"
                            escapeXml = "false">
                    </c:out>
                </c:if>
                <c:if test="${showAddForm}">
                    <c:out  value=   "<table id='newTable'><form action='' method='post' autocomplete='off'><tr><th class='addFormHeader'>Add User <input type='submit' name='closeForm' value='close' class='material-icons-round rightIconAlign plainIcon'/></th>"
                            escapeXml = "false">
                    </c:out>
                </c:if>
                <c:if test="${!showAddForm and !showEditForm}">
                    <c:out  value=   ""
                            escapeXml = "false">
                    </c:out>
                </c:if>
                <c:if test="${showAddForm}">
                    <c:out value=   "
                                        <tr>
                                                            <td>
                                                                <input type='text' name='newUsername' id='newUsername' value='${newUsername}' placeHolder='Username' autocomplete='nope'/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <input type='text' name='newPassword' id='newPassword' placeholder='Password' autocomplete='nope'>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <input type='text' name='newEmail' id='newEmail' placeholder='E-mail Address' autocomplete='nope'>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <input type='text' name='newFirstName' id='newFirstName' placeholder='First Name' autocomplete='nope'>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <input type='text' name='newLastName' id='newLastName' placeholder='Last Name' autocomplete='nope'>
                                                            </td>
                                                        </tr>                         
                                                        <tr>
                                                            <td>
                                                                <select name='newStatus' id='newStatus'>
                                                                    <option value='Active'>Active</option>
                                                                    <option value='Inactive'>Inactive</option>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <select name='newRole' id='newRole'>
                                                                    <option value='Regular User' ${newRole.equals('Regular User') ? 'selected' : ''}>Regular User</option>
                                                                    <option value='System Administrator' ${newRole.equals('System Administrator') ? 'selected' : ''}>System Administrator</option>
                                                                </select>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <input type='submit' value='Add User' class='modifyButton'>
                                                                <input type='hidden' name='action' value='addUser'>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </form>
                                            </td>
                                        </tr>
                                        " 
                            escapeXml = "false">
                        </c:out>
                    </c:if>                
                    <c:if test="${showEditForm}">
                        <c:out value=   "
                                        <tr>
                                                                <td>
                                                                    <input type='text' name='editUsername' id='editUsername' value='${editUsername}' autocomplete='nope'/>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <input type='password' name='editPassword' id='editPassword' value='${editPassword}' placeholder='New Password'/>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <input type='text' name='editEmail' id='editEmail' value='${editEmail}' autocomplete='nope'/>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <input type='text' name='editFirstName' id='editFirstName' value='${editFirstName}' autocomplete='nope'/>
                                                                </td> 
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <input type='text' name='editLastName' id='editLastName' value='${editLastName}' autocomplete='nope'/>
                                                                </td>
                                                            </tr>                         
                                                            <tr>
                                                                <td>
                                                                    <select name='editStatus' id='editStatus'>
                                                                            <option value='Active' ${selectedUser.getActive() ? 'selected' : ''}>Active</option>
                                                                            <option value='Inactive' ${!selectedUser.getActive() ? 'selected' : ''}>Inactive</option>
                                                                    </select>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <select name='editRole' id='editRole'>
                                                                            <option value='Regular User' ${selectedUser.getRole().getRoleId() == 2 ? 'selected' : ''}>Standard User</option>          
                                                                            <option value='System Administrator' ${selectedUser.getRole().getRoleId() == 1 ? 'selected' : ''}>System Administrator</option>
                                                                    </select>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <input type='reset' name='resetUpdate' value='Reset' class='resetButton'/>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <input type='submit' value='Update User' class='modifyButton'/>
                                                                    <input type='hidden' name='action' value='updateUser'/>
                                                                </td>
                                                            </tr>
                                                    </table>
                                                </form>
                                            </td>
                                        </tr>
                                        " 
                            escapeXml = "false">
                        </c:out>
                    </c:if>  
                    </td></tr>
                <tr class='smallSpacerRow'></tr>
                <tr>    
                    <td>
                        <table id='displayTable'>
                            <tr class="largeFont" >    
                                <th class="headerRow" colspan="8">Current Users</th> 
                            </tr>
                            <tr class="headerRow">
                                <th>
                                    <form action="" method="get">
                                        <input type="submit" name="columnSort" id="columnSortActive" value="Active" class="plainText"/><!--<label for="columnSortActive" class="material-icons-round textAligned">sort_by_alpha</label>-->
                                    </form>
                                </th>
                                <th>
                                    <form action="" method="get">
                                        <input type="submit" name="columnSort" id="columnSortUsername" value="Username" class="plainText"/><!--<label for="columnSortUsername" class="material-icons-round textAligned">sort_by_alpha</label>-->
                                    </form>
                                </th>
                                <th>
                                    <form action="" method="get">
                                        <input type="submit" name="columnSort" id="columnSortEmail" value="E-mail Address" class="plainText"/><!--<label for="columnSortEmail" class="material-icons-round textAligned">sort_by_alpha</label>-->
                                    </form>
                                </th>
                                <th>
                                    <form action="" method="get">
                                        <input type="submit" name="columnSort" id="columnSortFirstName" value="First Name" class="plainText"/><!--<label for="columnSortFirstName" class="material-icons-round textAligned">sort_by_alpha</label>-->
                                    </form>
                                </th>
                                <th>
                                    <form action="" method="get">
                                        <input type="submit" name="columnSort" id="columnSortLastName" value="Last Name" class="plainText"/><!--<label for="columnSortLastName" class="material-icons-round textAligned">sort_by_alpha</label>-->
                                    </form>
                                </th>
                                <th>
                                    <form action="" method="get">
                                        <input type="submit" name="columnSort" id="columnSortRole" value="User Role" class="plainText"/><!--<label for="columnSortRole" class="material-icons-round textAligned">sort_by_alpha</label>-->
                                    </form>
                                </th>
                                <th>Edit</th>
                                <th>Delete</th>
                            </tr>
                            <c:if test="${!showAddForm}">
                                <c:out value=   "
                                                <tr class='secondHeaderRow'>
                                                        <td colspan='8'>
                                                                <form action='' method='post'>
                                                                        <label for='displayAddForm' class='material-icons textAligned largeIcon'>person_add</label>
                                                                        <input type='submit' id='displayAddForm' name='displayAddForm' value ='Add User' class='plainText'>
                                                                        <input type='hidden' name='action' value='displayAddForm'>
                                                                </form>
                                                        </td>
                                                    </tr>
                                                " 
                                    escapeXml = "false">
                                </c:out>
                            </c:if>
                            <c:forEach items="${userList}" var="user"  varStatus="userCount">
                                <c:out value=   " 
                                                    <tr>
                                                        <form action='' method='post'>    
                                                            <td>
                                                " 
                                                escapeXml = "false">
                                </c:out>
                                <c:if test='${user.getActive()}'> 
                                    <c:out value =  "
                                                        <span class='material-icons green'>check</span>
                                                    " 
                                                    escapeXml = "false">
                                    </c:out>
                                </c:if>
                                <c:if test='${!user.getActive()}'> 
                                    <c:out value =  "
                                                        <span class='material-icons red'>clear</span
                                                    " 
                                                    escapeXml = "false">
                                    </c:out>
                                </c:if>
                                    <c:out value =  "                               
                                                        </td>
                                                        <td>${user.getUsername()}</td>
                                                        <td>${user.getEmail()}</td>
                                                        <td>${user.getFirstName()}</td>
                                                        <td>${user.getLastName()}</td>
                                                        <td>${user.getRole().getRoleName()}</td>
                                                        <td>
                                                            <input type='submit' name='action' id='edit' class='material-icons' value='edit'>
                                                        </td><td>
                                                    "
                                                    escapeXml = "false">
                                    </c:out>
                                <c:if test='${!user.getUsername().equals(username)}'>
                                    <c:out value = "
                                                            <input type='submit' name='action' id='delete' value='delete' class='material-icons red'>
                                                            <input type='hidden' name='selectedUser' id='selectedUser' value='${user.getUsername()}'>
                                                    "
                                        escapeXml = "false">
                                    </c:out>
                                </c:if>
                                <c:out value = "
                                                        </td>
                                                        
                                                    </form>
                                                </tr>
                                            "
                                    escapeXml = "false">
                                </c:out>
                            </c:forEach>
                        </table>
                    </td>
                </tr>
            </table>
        </main>
        <footer class="centered">
            *Click on table header to toggle between sort options.
        </footer>
    </body>
</html>

