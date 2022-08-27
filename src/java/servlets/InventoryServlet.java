/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import models.Item;
import services.CategoryService;
import services.ItemService;
import services.UserService;

/**
 *
 * @author BritishWaldo
 */
public class InventoryServlet extends HttpServlet
{
    private List<Item> itemList = null;
    private List<Category> categoryList = null;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute("username");
        
        String columnSort = "";
        
        if(request.getParameter("columnSort") != null)
        {
            columnSort = request.getParameter("columnSort");
        }
        
        String sortOrder = null;
        
        if (session.getAttribute("sortOrder") != null)
        {
            String previousOrder = (String) session.getAttribute("sortOrder");
            
            if (previousOrder.equals(" DESC"))
            {
                sortOrder = " ASC";
            }
            else if (previousOrder.equals(" ASC"))
            {
                sortOrder = " DESC";
            }
        }
        else
        {
            sortOrder = " DESC";
        }
        
        session.setAttribute("sortOrder", sortOrder);
        
        try
        {
            switch (columnSort)
            {
                case "Category":        this.itemList = new ItemService().getAllSortedForUser(currentUser.toLowerCase(), "category.categoryName" + sortOrder);
                                        break;
                case "Item Name":       this.itemList = new ItemService().getAllSortedForUser(currentUser.toLowerCase(), "itemName" + sortOrder);
                                        break;
                case "Price":           this.itemList = new ItemService().getAllSortedForUser(currentUser.toLowerCase(), "price" + sortOrder);
                                        break;
                default:                this.itemList = new ItemService().getAllSortedForUser(currentUser.toLowerCase(), "category.categoryName" + sortOrder);
                                        break;
            }

            session.setAttribute("itemList", this.itemList);
            
            this.categoryList = new CategoryService().getAll();
            session.setAttribute("categoryList", this.categoryList);
        }
        catch (Exception ex)
        {
            Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        String currentUser = (String) session.getAttribute("username");
        
        try
        {
            this.itemList = new UserService().get(currentUser).getItemList();
            session.setAttribute("itemList", this.itemList);
            
            this.categoryList = new CategoryService().getAll();
            session.setAttribute("categoryList", this.categoryList);
        }
        catch (Exception ex)
        {
            Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String action = request.getParameter("action");
        
        boolean changesMade = false;
        
        switch (action)
        {
            case "addItem": 
                                        if (request.getParameter("closeForm") != null)
                                        {
                                            // DO NOTHING IF THE USER RESET THE FORM AND JUST RELOAD THE PAGE.
                                            // USE THIS TO CLOSE THE FORM WHEN YOU IMPLEMENT THE x BUTTON
                                            
                                            request.setAttribute("showAddForm", false);
                                            break;
                                        }
                                        String inputItemName = request.getParameter("newItemName");
                                        String inputItemPriceRaw = request.getParameter("newItemPrice");
                                        String inputItemCategoryRaw = request.getParameter("newCategory");
                                        
                                        ArrayList<String> errorChecking = new ArrayList<String>();
                                        
                                        errorChecking.add(inputItemName);
                                        errorChecking.add(inputItemPriceRaw);
                                        errorChecking.add(inputItemCategoryRaw);

                                        for(String input: errorChecking)
                                        {
                                            if (input.equals("") || input == null)
                                            {
                                                request.setAttribute("server_message", "All fields must be filled out in order to an item to your personal inventory.<br>Please fill out all available fields in the add item form.");
                                                
                                                request.setAttribute("showAddForm", true);
                                                
                                                getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
                                                return;
                                            }
                                        }
                                        
                                        double convertedItemPrice = Double.parseDouble(inputItemPriceRaw);
                                        
                                        int convertedItemCategory = Integer.parseInt(inputItemCategoryRaw);

                                        try
                                        {
                                            new ItemService().insert(inputItemName, convertedItemPrice, convertedItemCategory, currentUser);
                                            
                                            request.setAttribute("server_message", inputItemName + " was successfully added to your personal inventory.");

                                            changesMade = true;
                                        }
                                        catch (Exception ex)
                                        {
                                            if (ex.getMessage().contains("for key 'PRIMARY'"))
                                            {
                                                request.setAttribute("server_message", "The website went fubar, contact the creators please.");
                                            }
                                            Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        break;
            case "updateItem":
                                        if (session.getAttribute("selectedItem") == null)
                                        {
                                            request.setAttribute("server_message", "Use the edit button within the Personal Inventory pane to update an item.");
                                        }
                                        else if (request.getParameter("closeForm") != null)
                                        {
                                            // DO NOTHING IF THE USER RESET THE FORM AND JUST RELOAD THE PAGE.
                                            // USE THIS TO CLOSE THE FORM WHEN YOU IMPLEMENT THE x BUTTON
                                            
                                            request.setAttribute("showEditForm", false);
                                            break;
                                        }
                                        else
                                        {
                                            Item itemToUpdate = (Item) session.getAttribute("selectedItem");
                                            
                                            if (!itemToUpdate.getOwner().getUsername().equalsIgnoreCase((String) session.getAttribute("username")))
                                            {
                                                request.setAttribute("server_message", "Current user does not own the item being modified.<br>Please don't try to modify other users items.");
                                                break;
                                            }
                                            
                                            String updatedItemName = request.getParameter("editItemName");
                                            String updatedItemPriceRaw = request.getParameter("editItemPrice");
                                            String updatedItemCategoryRaw = request.getParameter("editCategory");
                                            
                                            double convertedUpdatedPrice = -1.1;
                                            
                                            int convertedUpdatedCategory = -1;

                                            if (updatedItemName.equals("") || updatedItemName == null)
                                            {
                                                updatedItemName = itemToUpdate.getItemName();
                                            }

                                            if (updatedItemPriceRaw.equals("") || updatedItemPriceRaw == null)
                                            {
                                                convertedUpdatedPrice = itemToUpdate.getPrice();
                                            }
                                            else
                                            {
                                                convertedUpdatedPrice = Double.parseDouble(updatedItemPriceRaw);
                                            }

                                            if (updatedItemCategoryRaw.equals("") || updatedItemCategoryRaw == null)
                                            {
                                                convertedUpdatedCategory = itemToUpdate.getCategory().getCategoryID();
                                            }
                                            else
                                            {
                                                convertedUpdatedCategory = Integer.parseInt(updatedItemCategoryRaw);
                                            }
                                        
                                            try
                                            {
                                                new ItemService().update(itemToUpdate.getItemID(), updatedItemName, convertedUpdatedPrice, convertedUpdatedCategory);

                                                request.setAttribute("server_message", itemToUpdate.getItemName() + " was successfully updated in your personal inventory.");

                                                changesMade = true;
                                            }
                                            catch (Exception ex)
                                            {
                                                Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                        
                                        session.removeAttribute("selectedItem");
                                        break;
            case "edit":
                                        String selectedItemToEdit = request.getParameter("selectedItem");
                                        try
                                        {
                                            Item itemToEdit = new ItemService().get(Integer.parseInt(selectedItemToEdit));

                                            session.setAttribute("selectedItem", itemToEdit);
                                            request.setAttribute("showEditForm", true);
                                            request.setAttribute("editItemName", itemToEdit.getItemName());
                                            
                                            DecimalFormat decimalFormatter = new DecimalFormat("0.00");
                                            
                                            request.setAttribute("editItemPrice", decimalFormatter.format(itemToEdit.getPrice()));
                                            
                                        }
                                        catch (Exception ex)
                                        {
                                            Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        break;
            case "delete":
                                        String selectedItemToDelete = request.getParameter("selectedItem");
                                        
                                        try
                                        {
                                            Item itemToDelete = new ItemService().get(Integer.parseInt(selectedItemToDelete));
                                            
                                            if (!itemToDelete.getOwner().getUsername().equalsIgnoreCase((String) session.getAttribute("username")))
                                            {
                                                request.setAttribute("server_message", "Current user does not own the item selected for deletion.<br>Please don't try to delete other users items.");
                                            }
                                            else
                                            {
                                                new ItemService().delete(itemToDelete.getItemID());
                                                
                                                request.setAttribute("server_message", itemToDelete.getItemName() + " was successfully deleted from your personal inventory.");

                                                changesMade = true;
                                            }
                                        }
                                        catch (Exception ex)
                                        {
                                            Logger.getLogger(InventoryServlet.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        break;
            case "displayAddForm":
                                        request.setAttribute("showAddForm", true);
                                        break;
        }

        if (changesMade)
        {
            try
            {
                this.itemList = new UserService().get(currentUser).getItemList();
                session.setAttribute("itemList", this.itemList);

                this.categoryList = new CategoryService().getAll();
                session.setAttribute("categoryList", this.categoryList);
                
                getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
                return;
            }
            catch (Exception ex)
            {
                Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            getServletContext().getRequestDispatcher("/WEB-INF/inventory.jsp").forward(request, response);
            return;
        }
    }
}
