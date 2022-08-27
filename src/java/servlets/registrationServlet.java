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
import models.User;
import services.AccountService;
import services.UserService;

/**
 *
 * @author BritishWaldo
 */
public class registrationServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        if (request.getParameter("uuid") != null)
        {
            String hashedUUID = request.getParameter("uuid");
            
            boolean activationSuccess = new AccountService().activateAccount(hashedUUID);
            
            if (activationSuccess)
            {
                request.setAttribute("server_message", "Thank you for activating your account"
                                                    + ", please click the button below to be redirected to the login page.");
                
                getServletContext().getRequestDispatcher("/WEB-INF/activation.jsp").forward(request, response);
                return;
            }
            else
            {
                request.setAttribute("server_message", "That doesn't seem to be a valid activation link"
                                                        + ", please check that you clicked the right link.");
                
                getServletContext().getRequestDispatcher("/WEB-INF/activation.jsp").forward(request, response);
                return;
            }
        }
        else
        {
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String email = request.getParameter("registerEmail");
        String username = request.getParameter("registerUsername");
        String firstName = request.getParameter("registerFirstName");
        String lastName = request.getParameter("registerLastName");
        
        try
        {
            User alreadyExists = new UserService().get(username);
            if (alreadyExists != null)
            {
                request.setAttribute("registerEmail", email);
                request.setAttribute("registerFirstName", firstName);
                request.setAttribute("registerLastName", lastName);
                request.setAttribute("server_message", "A user already exists with this username"
                                                        + ", please choose another username");
                
                getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
                return;
            }
            
            alreadyExists = new UserService().getByEmail(email);
            
            if (alreadyExists != null)
            {
                request.setAttribute("registerEmail", email);
                request.setAttribute("registerFirstName", firstName);
                request.setAttribute("registerLastName", lastName);
                request.setAttribute("server_message", "An account has already been registered to this e-mail address"
                                                        + ", if you've forgotten your password pelase <a href='forgotpassword'>click here</a>");
                
                getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
                return;
            } 
        }
        catch (Exception ex)
        {
            Logger.getLogger(registrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        String password = request.getParameter("registerPassword");
        String confirmPassword = request.getParameter("registerConfirmPassword");
        
        if (!password.equals(confirmPassword))
        {
            request.setAttribute("registerEmail", email);
            request.setAttribute("registerUsername", username);
            request.setAttribute("registerFirstName", firstName);
            request.setAttribute("registerLastName",lastName);
            request.setAttribute("server_message", "Passwords don't match, please re-enter them into the form.");
            
            getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
            return;
        }
        
        try
        {
            new UserService().insertNewUser(username, password, email, firstName, lastName);
        }
        catch (Exception ex)
        {
            Logger.getLogger(registrationServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String baseURL = request.getRequestURL().toString();
        String webINFPath = getServletContext().getRealPath("/WEB-INF");
        
        new AccountService().sendAccountActivationEmail(email, webINFPath, baseURL);
        
        request.setAttribute("server_message", "Thank you for registering for Home nVentory"
                                                + ", please follow the link sent to the e-mail you registered with to activate your account.");
        
        getServletContext().getRequestDispatcher("/WEB-INF/register.jsp").forward(request, response);
        return;
    }
}
