/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;
import services.UserService;

/**
 *
 * @author BritishWaldo
 */
public class accountServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Object[] returnArray = this.populateAccountInformation(request, response, session);
        
        request = (HttpServletRequest) returnArray[0];
        response = (HttpServletResponse) returnArray[1];
        session =  (HttpSession) returnArray[2];
        
        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Object[] returnArray = this.populateAccountInformation(request, response, session);
        
        request = (HttpServletRequest) returnArray[0];
        response = (HttpServletResponse) returnArray[1];
        session =  (HttpSession) returnArray[2];
        
        String action = request.getParameter("action");
        
        switch(action)
        {
            case "deactivate":          request.setAttribute("server_message", "<span class='material-icons'>warning</span>Warning<br>once deactivated your account can only be reactivated by a System Administrator."
                                                                                + "<br>Enter your password below and then click the button to deactivate your account.");
            
                                        getServletContext().getRequestDispatcher("/WEB-INF/confirmDeactivation.jsp").forward(request, response);
                                        break;
                                        
            case "deactivateConfirm":   String username = (String) session.getAttribute("username");
                                        String password = request.getParameter("deactivatePassword");

                                        String confirmedUsername = new AccountService().accountLogin(username, password);
                                        
                                        System.out.println(confirmedUsername);
                                        System.out.println(username);

                                        if (confirmedUsername.equalsIgnoreCase(username))
                                        {
                                            User userToDeactivate;

                                            try 
                                            {
                                                userToDeactivate = new UserService().get(username);
                                                userToDeactivate.setActive(false);

                                                new UserService().updateActiveStatus(userToDeactivate);
                                            }
                                            catch (Exception ex) {
                                                Logger.getLogger(accountServlet.class.getName()).log(Level.SEVERE, null, ex);
                                            }

                                            response.sendRedirect("login");
                                            return;
                                        }
                                        else
                                        {
                                            request.setAttribute("server_message", "<span class='material-icons'>warning</span>Warning<br>once deactivated your account can only be reactivated by a System Administrator."
                                                                                + "<br>Enter your password below and then click the button to deactivate your account."
                                                                                + "<br><br><br><span class='red'>Incorrect Password, please retype your password.</span>");

                                            getServletContext().getRequestDispatcher("/WEB-INF/confirmDeactivation.jsp").forward(request, response);
                                        }
                                        break;
                                        
            case "accountChange":
                                        String updatedEmail = request.getParameter("accountEmail");
                                        try
                                        {
                                            if (updatedEmail != null && new UserService().getByEmail(updatedEmail) != null)
                                            {
                                                request.setAttribute("server_message", "Invalid e-mail, please try again.");
                                                
                                                getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
                                                return;
                                            }
                                            
                                            String updatedFirstName = request.getParameter("accountFirstName");
                                            String updatedLastName = request.getParameter("accountLastName");
                                            
                                            User currentUser = new UserService().get((String) session.getAttribute("username"));
                                            
                                            if (updatedFirstName != null && !updatedFirstName.equals("") && !updatedFirstName.equals(" "))
                                            {
                                                currentUser.setFirstName(updatedFirstName);
                                            }
                                            
                                            if (updatedLastName != null && !updatedLastName.equals("") && !updatedLastName.equals(" "))
                                            {
                                                currentUser.setLastName(updatedLastName);
                                            }
                                            
                                            if (updatedEmail != null && updatedEmail.contains("@") && updatedEmail.contains("."))
                                            {
                                                currentUser.setEmail(updatedEmail);
                                            }
                                            
                                            new UserService().update(currentUser);
                                            
                                            request.setAttribute("server_message", "User information successfully updated.");
                                        }
                                        catch (Exception ex)
                                        {
                                            Logger.getLogger(accountServlet.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                        
                                        returnArray = this.populateAccountInformation(request, response, session);
        
                                        request = (HttpServletRequest) returnArray[0];
                                        response = (HttpServletResponse) returnArray[1];
                                        session =  (HttpSession) returnArray[2];
                                        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
                                        break;
            
            case "passwordChange":      
                                        request.setAttribute("server_message", "To change your account password please complete the below form.");
                                        getServletContext().getRequestDispatcher("/WEB-INF/updatePassword.jsp").forward(request, response);
                                        break;
            
            case "updatePassword":
                                        String currentPassword = request.getParameter("updatePasswordCurrent");
                                        String currentUsername = (String) session.getAttribute("username");
                                        
                                        String verifiedUsername = new AccountService().accountLogin(currentUsername, currentPassword);
                                        
                                        if (verifiedUsername == null)
                                        {
                                            request.setAttribute("server_message", "To change your account password please complete the below form."
                                                                                    + "<br><br><br><span class='red'>Incorrect current password</span>");
                                            
                                            getServletContext().getRequestDispatcher("/WEB-INF/updatePassword.jsp").forward(request, response);
                                            return;
                                        }
                                        else
                                        {
                                            String newPassword = request.getParameter("updatePasswordNew");
                                            String newPasswordConfirm = request.getParameter("updatePasswordConfirm");
                                            
                                            if (!newPassword.equals(newPasswordConfirm))
                                            {
                                                request.setAttribute("server_message", "To change your account password please complete the below form."
                                                                                    + "<br><br><br><span class='red'>New passwords don't match.</span>");
                                            
                                                getServletContext().getRequestDispatcher("/WEB-INF/updatePassword.jsp").forward(request, response);
                                                return;
                                            }
                                            else
                                            {
                                                try
                                                {
                                                    User userToUpdate = new UserService().get(verifiedUsername);
                                                    
                                                    new UserService().updatePassword(userToUpdate.getUsername(), newPassword);
                                                    
                                                    request.setAttribute("server_message", "Account password successfully updated. All future logins will require the use of the new password.");
                                                    
                                                    
                                                    getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
                                                    return;
                                                }
                                                catch (Exception ex)
                                                {
                                                    Logger.getLogger(accountServlet.class.getName()).log(Level.SEVERE, null, ex);
                                                }
                                            }
                                        }
                                        
                                        break;
                                        
            default:
                                        getServletContext().getRequestDispatcher("/WEB-INF/account.jsp").forward(request, response);
                                        break;
        }
    }
    
    private Object[] populateAccountInformation(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws ServletException, IOException
    {
        try
        {
            User currentUser = new UserService().get((String) session.getAttribute("username"));
            
            request.setAttribute("email", currentUser.getEmail());
            request.setAttribute("firstName", currentUser.getFirstName());
            request.setAttribute("lastName", currentUser.getLastName());
            
        }
        catch (Exception ex)
        {
            Logger.getLogger(accountServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Object[] returnArray = {request, response, session};
        
        return returnArray;
    }
}
