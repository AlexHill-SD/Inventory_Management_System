/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.AccountService;

/**
 *
 * @author BritishWaldo
 */
public class forgotPasswordServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        if (request.getParameter("uuid") == null)
        {
            getServletContext().getRequestDispatcher("/WEB-INF/forgotPassword.jsp").forward(request, response);
            return;
        }
        else
        {
            getServletContext().getRequestDispatcher("/WEB-INF/resetPassword.jsp").forward(request, response);
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        
        if (request.getParameter("resetIdentifier") != null)
        {
            String baseURL = request.getRequestURL().toString();
            String resetIdentifier = request.getParameter("resetIdentifier");
            String webINFPath = getServletContext().getRealPath("/WEB-INF");
            
            boolean identifierIsEmail = false;
            if(resetIdentifier.contains("@") && resetIdentifier.contains("."))
            {
                identifierIsEmail = true;
            }
            
            new AccountService().sendPasswordResetEmail(resetIdentifier, identifierIsEmail, webINFPath, baseURL);
            
            session.setAttribute("server_message", "If the e-mail or username you entered is valid please wait a few moments for password reset e-mail to arrive."
                                                + "<br>If you don't see it check the junk folder of your e-mail account.");
            
            response.sendRedirect("login");
            return;
        }
        else if (request.getParameter("uuid") != null)
        {
            String uuid = request.getParameter("uuid");
            String resetPassword = request.getParameter("resetPassword");
            String resetPasswordConfirm = request.getParameter("resetPasswordConfirm");
            
            
            if (!resetPassword.equals(resetPasswordConfirm))
            {
                request.setAttribute("uuid", uuid);
                request.setAttribute("server_message", "The inputted passwords do not match, please type them in again.");
                
                getServletContext().getRequestDispatcher("/WEB-INF/resetPassword.jsp").forward(request, response);
                return;
            }

            boolean resetSuccess = new AccountService().resetPassword(uuid, resetPassword);
            
            if (resetSuccess)
            {
                session.setAttribute("server_message", "Password successfully changed, please log in with your username and new password.");
            
                response.sendRedirect("login");
                return;
            }
            else
            {
                response.sendRedirect("login");
                return;
            }
        }
        else
        {
            response.sendRedirect("login");
            return;
        }
    }
}
