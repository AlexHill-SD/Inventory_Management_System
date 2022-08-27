/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.Currency;
import java.util.Locale;
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
public class LoginServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        
        if (session.getAttribute("server_message") != null)
        {
            request.setAttribute("server_message", (String) session.getAttribute("server_message"));
        }
        
        session.invalidate(); // just by going to the login page the user is logged out :-) 
        
        getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        
        String inputUsername = request.getParameter("loginUsername");
        String inputPassword = request.getParameter("loginPassword");

        String validatedUsername = AccountService.accountLogin(inputUsername, inputPassword);

        if (validatedUsername == null) 
        {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
            return;
        }
        
        boolean isAdmin = AccountService.userIsAdmin(validatedUsername);
        
        if (isAdmin == true)
        {
            session.setAttribute("showAdminLink", true);          
        }
        
        //capitalise the first letter in the username, for better display.
        validatedUsername = validatedUsername.substring(0,1).toUpperCase() + validatedUsername.substring(1);
        
        session.setAttribute("username", validatedUsername);
        
        Locale currentLocale = request.getLocale();
        
        Currency testCurrency = Currency.getInstance(currentLocale);
        
        session.setAttribute("userLocaleCurrencySymbol", testCurrency.getSymbol(currentLocale));        
        
        response.sendRedirect("admin");
        return;
    }
}
