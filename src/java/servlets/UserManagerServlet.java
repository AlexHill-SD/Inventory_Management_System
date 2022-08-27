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
import models.User;
import services.RoleService;
import services.UserService;

/**
 *
 * @author BritishWaldo
 */
public class UserManagerServlet extends HttpServlet
{
    private List<User> userList = null;
    
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
                case "Active":          this.userList = new UserService().getAllSorted("active" + sortOrder);
                                        break;
                case "Username":        this.userList = new UserService().getAllSorted("username" + sortOrder);
                                        break;
                case "E-mail Address":  this.userList = new UserService().getAllSorted("email" + sortOrder);
                                        break;
                case "First Name":      this.userList = new UserService().getAllSorted("firstName" + sortOrder);
                                        break;
                case "Last Name":       this.userList = new UserService().getAllSorted("lastName" + sortOrder);
                                        break;
                case "User Role":       this.userList = new UserService().getAllSorted("role" + sortOrder);
                                        break;
                default:                this.userList = new UserService().getAllSorted("username" + " ASC");
                                        break;
            }

            session.setAttribute("userList", this.userList);
        }
        catch (Exception ex)
        {
            Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/userManager.jsp").forward(request, response);
        return;
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        try
        {
            this.userList = new UserService().getAllSorted("username" + " ASC");

            session.setAttribute("userList", this.userList);
        }
        catch (Exception ex)
        {
            Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String action = request.getParameter("action");
        
        boolean changesMade = false;
        Object[] returnArray = null;
        
        switch (action)
        {
            case "addUser":
                                        returnArray = this.addUser(request, response, session);
                                        
                                        request = (HttpServletRequest) returnArray[0];
                                        response = (HttpServletResponse) returnArray[1];
                                        session =  (HttpSession) returnArray[2];
                                        changesMade = (boolean) returnArray[3];
                                        break;
            case "updateUser":
                                        returnArray = this.updateUser(request, response, session);
                                        
                                        request = (HttpServletRequest) returnArray[0];
                                        response = (HttpServletResponse) returnArray[1];
                                        session =  (HttpSession) returnArray[2];
                                        changesMade = (boolean) returnArray[3];
                                        break;
            case "edit":
                                        returnArray = this.editUser(request, response, session);
                                        
                                        request = (HttpServletRequest) returnArray[0];
                                        response = (HttpServletResponse) returnArray[1];
                                        session =  (HttpSession) returnArray[2];
                                        changesMade = (boolean) returnArray[3];
                                        break;
            case "delete":
                                        returnArray = this.deleteUser(request, response, session);
                                        
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
                this.userList = new UserService().getAllSorted("username" + " ASC");

                session.setAttribute("userList", this.userList);
                
                getServletContext().getRequestDispatcher("/WEB-INF/userManager.jsp").forward(request, response);
                return;
            }
            catch (Exception ex)
            {
                Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            getServletContext().getRequestDispatcher("/WEB-INF/userManager.jsp").forward(request, response);
            return;
        }
    }
    
    /* * * * * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * *                  User Admin                   * * 
     * * * * * * * * * * * * * * * * * * * * * * * * * * *
     * * * * * * * * * * * * * * * * * * * * * * * * * * */
    private Object[] addUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException
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
        
        String inputUsername = request.getParameter("newUsername");
        String inputEmail = request.getParameter("newEmail");
        String inputFirstName = request.getParameter("newFirstName");
        String inputLastName = request.getParameter("newLastName");
        String inputPassword = request.getParameter("newPassword");
        String rawActive = request.getParameter("newStatus");
        String rawUserRole = request.getParameter("newRole");

        ArrayList<String> errorChecking = new ArrayList<String>();

        errorChecking.add(inputUsername);
        errorChecking.add(inputEmail);
        errorChecking.add(inputFirstName);
        errorChecking.add(inputLastName);
        errorChecking.add(inputPassword);

        for(String input: errorChecking)
        {
            if (input.equals("") || input == null)
            {
                request.setAttribute("server_message", "All fields must be filled out in order to add a user to the database.<br>Please fill out all the add user fields.");

                request.setAttribute("showAddForm", true);

                getServletContext().getRequestDispatcher("/WEB-INF/userManager.jsp").forward(request, response);
            }
        }

        boolean inputActive = false;
        if (rawActive.equals("Active"))
        {
            inputActive = true;
        }

        int inputUserRole = -1;

        try
        {
            inputUserRole = new RoleService().roleIDLookup(rawUserRole);
        }
        catch (Exception ex)
        {
            Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        try
        {                                                    
            new UserService().insert(inputUsername, inputPassword, inputEmail, inputFirstName, inputLastName, inputActive, inputUserRole);

            request.setAttribute("server_message", "User " + inputUsername + " was successfully added to the database.");

            changesMade = true;
        }
        catch (Exception ex)
        {
            if (ex.getMessage().contains("for key 'PRIMARY'"))
            {
                request.setAttribute("server_message", "User " + inputUsername + " already exists within the database");
            }
            Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Object[] returnArray = {request, response, session, changesMade};
        
        return returnArray;
    }
    
    private Object[] updateUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException
    {
        boolean changesMade = false;
        
        if (session.getAttribute("selectedUser") == null)
        {
            request.setAttribute("server_message", "Use the edit button within the Current Users pane to update a user.");
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
            User userToUpdate = (User) session.getAttribute("selectedUser");

            String updatedUsername = request.getParameter("editUsername");
            String updatedPassword = request.getParameter("editPassword");
            String updatedEmail = request.getParameter("editEmail");
            String updatedFirstName = request.getParameter("editFirstName");
            String updatedLastName = request.getParameter("editLastName");
            String rawStatus = request.getParameter("editStatus");
            String rawRole = request.getParameter("editRole");

            if (updatedUsername.equals("") || updatedUsername == null)
            {
                updatedUsername = userToUpdate.getUsername();
            }

            if (updatedEmail.equals("") || updatedEmail == null)
            {
                updatedEmail = userToUpdate.getEmail();
            }

            if (updatedFirstName.equals("") || updatedFirstName == null)
            {
                updatedFirstName = userToUpdate.getFirstName();
            }

            if (updatedLastName.equals("") || updatedLastName == null)
            {
                updatedLastName = userToUpdate.getLastName();
            }

            boolean updatedActive = false;
            if (rawStatus.equals("Active"))
            {
                updatedActive = true;
            }

            int updatedRoleID = -1;

            try
            {
                updatedRoleID = new RoleService().roleIDLookup(rawRole);
            }
            catch (Exception ex)
            {
                Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            try
            {
                if (updatedPassword == null)
                {
                    new UserService().update(userToUpdate.getUsername(), updatedUsername, updatedEmail, updatedFirstName, updatedLastName, updatedActive, updatedRoleID);
                }
                else
                {
                    new UserService().update(userToUpdate.getUsername(), updatedUsername, updatedPassword, updatedEmail, updatedFirstName, updatedLastName, updatedActive, updatedRoleID);
                }

                request.setAttribute("server_message", "User " + userToUpdate.getUsername() + " was successfully updated in the database.");

                changesMade = true;
            }
            catch (Exception ex)
            {
                if (ex.getMessage().contains("for key 'PRIMARY'"))
                {
                    request.setAttribute("server_message", "User " + updatedUsername + " already exists within the database, please choose another e-mail to assign to " + userToUpdate.getEmail());
                }

                Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        session.removeAttribute("selectedUser");
        
        Object[] returnArray = {request, response, session, changesMade};
        
        return returnArray;
    }
    
    private Object[] editUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException
    {
        boolean changesMade = false;
        
        String selectedUser = request.getParameter("selectedUser");
        try
        {
            User userToEdit = new UserService().get(selectedUser);

            session.setAttribute("selectedUser", userToEdit);
            request.setAttribute("showEditForm", true);
            request.setAttribute("editUsername", userToEdit.getUsername());
            request.setAttribute("editEmail", userToEdit.getEmail());
            request.setAttribute("editFirstName", userToEdit.getFirstName());
            request.setAttribute("editLastName", userToEdit.getLastName());
        }
        catch (Exception ex)
        {
            Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Object[] returnArray = {request, response, session, changesMade};
        
        return returnArray;
    }
    
    private Object[] deleteUser(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException
    {
        boolean changesMade = false;
        
        String userToDelete = request.getParameter("selectedUser");
                                        
        if(userToDelete.equals(session.getAttribute("username")))
        {
            request.setAttribute("server_message", "User " + userToDelete + " cannont be deleted from the database as it is actively logged in.");
        }
        else
        {
           try
            {
                new UserService().delete(userToDelete);

                request.setAttribute("server_message", "User " + userToDelete + " was successfully deleted from the database.");

                changesMade = true;
            }
            catch (Exception ex)
            {
                Logger.getLogger(UserManagerServlet.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
        
        session.removeAttribute("selectedUser");
        
        Object[] returnArray = {request, response, session, changesMade};
        
        return returnArray;
    }
}
