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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/categoryManager_style.css">
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

            <h1>Category Management Dashboard</h1>
            
            <h2>${server_message}</h2>

            <table id='outerTable'>
                <tr><td>
                <c:if test="${showEditForm}">
                    <c:out  value=   "<table id='editTable'><form action='' method='post' autocomplete='off'><tr><th class='addFormHeader'>Edit Category  <input type='submit' name='closeForm' value='close' class='material-icons-round rightIconAlign plainIcon'/></th></tr>"
                            escapeXml = "false">
                    </c:out>
                </c:if>
                <c:if test="${showAddForm}">
                    <c:out  value=   "<table id='newTable'><form action='' method='post' autocomplete='off'><tr><th class='addFormHeader'>Add Category <input type='submit' name='closeForm' value='close' class='material-icons-round rightIconAlign plainIcon'/></th></tr>"
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
                                                                <input type='text' name='newName' id='newName' value='${newName}' placeHolder='Category Name' autocomplete='nope'/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <input type='submit' value='Add Category' class='modifyButton'>
                                                                <input type='hidden' name='action' value='addCategory'>
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
                                                                    <input type='text' name='editName' id='editName' value='${editName}' autocomplete='nope'/>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <input type='reset' name='resetUpdate' value='Reset' class='resetButton'/>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td>
                                                                    <input type='submit' value='Update Category' class='modifyButton'/>
                                                                    <input type='hidden' name='action' value='updateCategory'/>
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
                            <tr class="largeFont">    
                                <th class="headerRow" colspan="8">Current Categories</th> 
                            </tr>
                            <tr class="headerRow">
                                <th>
                                    <form action="" method="get">
                                        <input type="submit" name="columnSort" id="columnSortName" value="Category Name" class="plainText"/><!--<label for="columnSortActive" class="material-icons-round textAligned">sort_by_alpha</label>-->
                                    </form>
                                </th>
                                <th>Edit</th>
                            </tr>
                            <c:if test="${!showAddForm}">
                                <c:out value=   "
                                                <tr class='secondHeaderRow'>
                                                        <td colspan='8'>
                                                                <form action='' method='post'>
                                                                        <label for='displayAddForm' class='material-icons textAligned largeIcon'>add_circle_outline</label>
                                                                        <input type='submit' id='addCategoryButton' name='displayAddForm' class='plainText' value ='Add Category'>
                                                                        <input type='hidden' name='action' value='displayAddForm'>
                                                                </form>
                                                        </td>
                                                    </tr>
                                                " 
                                    escapeXml = "false">
                                </c:out>
                            </c:if>
                            <c:forEach items="${categoryList}" var="category">
                                <c:out value=   " 
                                                <tr>
                                                    <form action='' method='post'>    
                                                    <td>${category.getCategoryName()}</td>
                                                    <td>
                                                        <input type='submit' name='action' id='edit' class='material-icons' value='edit'>
                                                        <input type='hidden' name='selectedCategoryID' id='selectedCategoryID' value='${category.getCategoryID()}'>
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

