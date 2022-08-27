<%-- 
    Document   : inventory
    Created on : 11-Nov-2021, 14:37:55
    Author     : BritishWaldo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/inventory_style.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons|Material+Icons+Round">
        <title>Home nVentory</title>
    </head>
    <body>
        <ul id="navBar">
            <li><a class="logout" href="login">Logout</a></li>
            <c:if test="${showAdminLink}">
                <c:out value = "
            <li><a href='admin'>Admin</a></li>
                                " escapeXml = "false">
                </c:out>
            </c:if>
            <li><a class="active" href="inventory">Inventory</a></li>
            <li id="menuRight"><a href="account" class="account">Welcome, ${username}</a></li>
        </ul>

        <h1>Home nVentory</h1>
        
        <h2>${server_message}</h2>
        
        <table id='outerTable'>
            <tr><td>
            <c:if test="${showEditForm}">
                <c:out  value=   "<table id='editTable'><form action='' method='post' autocomplete='off'><tr><th class='addFormHeader'>Edit Item  <input type='submit' name='closeForm' value='close' class='material-icons-round rightIconAlign plainIcon'/></th></tr>"
                        escapeXml = "false">
                </c:out>
            </c:if>
            <c:if test="${showAddForm}">
                <c:out  value=   "<table id='newTable'><form action='' method='post' autocomplete='off'><tr><th class='addFormHeader'>Add Item <input type='submit' name='closeForm' value='close' class='material-icons-round rightIconAlign plainIcon'/></th></tr>"
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
                                                            <input type='text' name='newItemName' id='newItemName' value='${newItemName}' placeHolder='Item Name' autocomplete='nope'/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <input type='number' name='newItemPrice' id='newItemPrice' value='${newItemPrice}' placeHolder='Item Price' step='0.01'/>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <select name='newCategory' id='newCategory'>
                                "
                                escapeXml = "false">
                </c:out>
                                        <c:forEach items="${categoryList}" var="category">
                                            <c:out value =  "
                                                                <option value='${category.getCategoryID()}'>${category.getCategoryName()}</option>
                                                            " 
                                                            escapeXml = "false">
                                            </c:out>
                                        </c:forEach>
                <c:out value =  "
                                                            </select>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td>
                                                            <input type='submit' value='Add Item' class='modifyButton'>
                                                            <input type='hidden' name='action' value='addItem'>
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
                    <fmt:formatNumber var = "selectedItemPrice" type = "currency" value = "${selectedItem.getPrice()}" currencySymbol="${userLocaleCurrencySymbol}"/>
                    <c:out value=   "
                                    <tr>
                                                            <td>
                                                                <input type='text' name='editItemName' id='editItemName' value='${editItemName}'>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <input type='number' name='editItemPrice' id='editItemPrice' value='${editItemPrice}'step='0.01'>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <select name='editCategory' id='editCategory'>
                                    " 
                                    escapeXml = "false">
                    </c:out>
                                            <c:forEach items="${categoryList}" var="category">
                                                <c:out value =  "
                                                                    <option value='${category.getCategoryID()}' ${selectedItem.getCategory().getCategoryID() == category.getCategoryID() ? 'selected' : ''}>${category.getCategoryName()}</option>
                                                                " 
                                                                escapeXml = "false">
                                                </c:out>
                                            </c:forEach>
                    <c:out value = "
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
                                                                <input type='submit' value='Update Item' class='modifyButton'/>
                                                                <input type='hidden' name='action' value='updateItem'/>
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
                            <th class="headerRow" colspan="8">Personal Inventory</th> 
                        </tr>
                        <tr class="headerRow">
                            <th class="mediumWidth">
                                <form action="" method="get">
                                    <input type="submit" name="columnSort" id="columnSortCategory" value="Category" class="plainText"/><!--<label for="columnSortActive" class="material-icons-round textAligned">sort_by_alpha</label>-->
                                </form>
                            </th>
                            <th class="mediumWidth">
                                <form action="" method="get">
                                    <input type="submit" name="columnSort" id="columnSortItemName" value="Item Name" class="plainText"/><!--<label for="columnSortActive" class="material-icons-round textAligned">sort_by_alpha</label>-->
                                </form>
                            </th>
                            <th class="largeWidth">
                                <form action="" method="get">
                                    <input type="submit" name="columnSort" id="columnSortPrice" value="Price" class="plainText"/><!--<label for="columnSortActive" class="material-icons-round textAligned">sort_by_alpha</label>-->
                                </form>
                            </th>
                            <th class="smallWidth">Edit</th>
                            <th class="smallWidth">Delete</th>
                        </tr>
                        <c:if test="${!showAddForm}">
                            <c:out value=   "
                                               <tr class='secondHeaderRow'>
                                                    <td colspan='5'>
                                                            <form action='' method='post'>
                                                                    <label for='addItemButton' class='material-icons textAligned largeIcon'>add_circle_outline</label>
                                                                    <input type='submit' id='addItemButton' name='addItemButton' value ='Add Item'>
                                                                    <input type='hidden' name='action' value='displayAddForm'>
                                                            </form>
                                                    </td>
                                                </tr>
                                            " 
                                   escapeXml = "false">
                            </c:out>
                        </c:if>
                        <c:forEach items="${itemList}" var="item">
                            <fmt:formatNumber var = "itemPrice" type = "currency" value = "${item.getPrice()}" currencySymbol="${userLocaleCurrencySymbol}"/>
                            <c:out value=   " 
                                                <tr>
                                                    <form action='' method='post'>                               
                                                    <td>${item.getCategory().getCategoryName()}</td>
                                                    <td>${item.getItemName()}</td>
                                                    <td>
                                                        ${itemPrice}
                                                        
                                                    </td>
                                                    <td>
                                                        <input type='submit' name='action' id='edit' class='material-icons' value='edit'>
                                                    </td>
                                                    <td>
                                                        <input type='submit' name='action' id='delete' value='delete' class='material-icons red'>
                                                        <input type='hidden' name='selectedItem' id='selectedItem' value='${item.getItemID()}'>
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
    </body>
</html>