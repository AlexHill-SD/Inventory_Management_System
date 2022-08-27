/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
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
import services.CategoryService;

/**
 *
 * @author BritishWaldo
 */
public class CategoryManagerServlet extends HttpServlet
{
private List<Category> categoryList = null;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        
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
                case "Category Name":   this.categoryList = new CategoryService().getAllSorted("categoryName" + sortOrder);
                                        break;
                default:                this.categoryList = new CategoryService().getAll();
                                        break;
            }

            session.setAttribute("categoryList", this.categoryList);
        }
        catch (Exception ex)
        {
            Logger.getLogger(CategoryManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/categoryManager.jsp").forward(request, response);
        return;
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        try
        {
            this.categoryList = new CategoryService().getAll();

            session.setAttribute("categoryList", this.categoryList);
        }
        catch (Exception ex)
        {
            Logger.getLogger(CategoryManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String action = request.getParameter("action");
        
        boolean changesMade = false;
        Object[] returnArray = null;
        
        switch (action)
        {
            case "addCategory":
                                        returnArray = this.addCategory(request, response, session);
                                        
                                        request = (HttpServletRequest) returnArray[0];
                                        response = (HttpServletResponse) returnArray[1];
                                        session =  (HttpSession) returnArray[2];
                                        changesMade = (boolean) returnArray[3];
                                        break;
            case "updateCategory":
                                        returnArray = this.updateCategory(request, response, session);
                                        
                                        request = (HttpServletRequest) returnArray[0];
                                        response = (HttpServletResponse) returnArray[1];
                                        session =  (HttpSession) returnArray[2];
                                        changesMade = (boolean) returnArray[3];
                                        break;
            case "edit":
                                        returnArray = this.editCategory(request, response, session);
                                        
                                        request = (HttpServletRequest) returnArray[0];
                                        response = (HttpServletResponse) returnArray[1];
                                        session =  (HttpSession) returnArray[2];
                                        changesMade = (boolean) returnArray[3];
                                        break;
            case "delete":
                                        returnArray = this.deleteCategory(request, response, session);
                                        
                                        request = (HttpServletRequest) returnArray[0];
                                        response = (HttpServletResponse) returnArray[1];
                                        session =  (HttpSession) returnArray[2];
                                        changesMade = (boolean) returnArray[3];
                                        break;
            case "displayAddForm":
                                        request.setAttribute("showAddForm", true);
                                        break;
        }

        if (changesMade)
        {
            try
            {
                this.categoryList = new CategoryService().getAll();

                session.setAttribute("categoryList", this.categoryList);
                
                getServletContext().getRequestDispatcher("/WEB-INF/categoryManager.jsp").forward(request, response);
                return;
            }
            catch (Exception ex)
            {
                Logger.getLogger(CategoryManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            getServletContext().getRequestDispatcher("/WEB-INF/categoryManager.jsp").forward(request, response);
            return;
        }
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * *                Category Admin                 * * 
     * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private Object[] addCategory(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException
    {
        boolean changesMade = false;        
        
        if (request.getParameter("closeForm") != null)
        {
            // DO NOTHING IF THE USER RESET THE FORM AND JUST RELOAD THE PAGE.
            // USE THIS TO CLOSE THE FORM WHEN YOU IMPLEMENT THE x BUTTON

            request.setAttribute("showAddForm", false);
            
            Object[] returnArray = {request, response, session, changesMade};
        
            return returnArray;
        }
        
        String inputCategoryName = request.getParameter("newName");

        ArrayList<String> errorChecking = new ArrayList<String>();

        errorChecking.add(inputCategoryName);

        for(String input: errorChecking)
        {
            if (input.equals("") || input == null)
            {
                request.setAttribute("server_message", "All fields must be filled out in order to add a category to the database.<br>Please fill out all the add category fields.");

                request.setAttribute("showAddForm", true);

                getServletContext().getRequestDispatcher("/WEB-INF/categoryManager.jsp").forward(request, response);
            }
        }

        try
        {                                                    
            new CategoryService().insert(inputCategoryName);

            request.setAttribute("server_message", "The Category  \'" + inputCategoryName + "\' was successfully added to the database.");

            changesMade = true;
        }
        catch (Exception ex)
        {
            /*
            *if (ex.getMessage().contains("for key 'PRIMARY'"))
            *{
            *    request.setAttribute("server_message", "The category \'" + inputCategoryName + "\' already exists within the database");
            *}
            */
            Logger.getLogger(CategoryManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Object[] returnArray = {request, response, session, changesMade};
        
        return returnArray;
    }
    
    private Object[] updateCategory(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException
    {
        boolean changesMade = false;
        
        if (session.getAttribute("selectedCategory") == null)
        {
            request.setAttribute("server_message", "Use the edit button within the Current Categories panel to update a category.");
        }
        else if (request.getParameter("closeForm") != null)
        {
            // DO NOTHING IF THE USER RESET THE FORM AND JUST RELOAD THE PAGE.
            // USE THIS TO CLOSE THE FORM WHEN YOU IMPLEMENT THE x BUTTON

            request.setAttribute("showEditForm", false);
            
            Object[] returnArray = {request, response, session, changesMade};
        
            return returnArray;
        }
        else
        {
            Category categoryToUpdate = (Category) session.getAttribute("selectedCategory");

            String updatedCategoryName = request.getParameter("editName");

            if (updatedCategoryName.equals("") || updatedCategoryName == null)
            {
                updatedCategoryName = categoryToUpdate.getCategoryName();
            }

            try
            {
                new CategoryService().update(categoryToUpdate.getCategoryID(), updatedCategoryName);

                request.setAttribute("server_message", "The Category  \'" + updatedCategoryName + "\' was successfully updated in the database.");

                changesMade = true;
            }
            catch (Exception ex)
            {
                /*
                *if (ex.getMessage().contains("for key 'PRIMARY'"))
                *{
                *    request.setAttribute("server_message", "The Category  \'" + updatedCategoryName + "\' already exists within the database.");
                *}
                */
                Logger.getLogger(CategoryManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        Object[] returnArray = {request, response, session, changesMade};
        
        return returnArray;
    }
    
    private Object[] editCategory(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException
    {
        boolean changesMade = false;
        
        String selectedCategoryRaw = request.getParameter("selectedCategoryID");
        
        int selectedCategoryID = Integer.parseInt(selectedCategoryRaw);
        
        try
        {
            Category categoryToEdit = new CategoryService().get(selectedCategoryID);

            session.setAttribute("selectedCategory", categoryToEdit);
            request.setAttribute("showEditForm", true);
            request.setAttribute("editName", categoryToEdit.getCategoryName());
        }
        catch (Exception ex)
        {
            Logger.getLogger(CategoryManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Object[] returnArray = {request, response, session, changesMade};
        
        return returnArray;
    }
    
    private Object[] deleteCategory(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException
    {
        boolean changesMade = false;
        
        String categoryIDToDeleteRaw = request.getParameter("selectedCategoryID");
        
        int selectedCategoryID = Integer.parseInt(categoryIDToDeleteRaw);

        try
         {
             String selectedCategoryName = new CategoryService().get(selectedCategoryID).getCategoryName();
             
             new CategoryService().delete(selectedCategoryID);

             request.setAttribute("server_message", "The category \'" + selectedCategoryName + "\' was successfully deleted from the database.");

             changesMade = true;
         }
         catch (Exception ex)
         {
             Logger.getLogger(CategoryManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
         } 
        
        Object[] returnArray = {request, response, session, changesMade};
        
        return returnArray;
    }
}