/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author BritishWaldo
 */
public class AdminSplashServlet extends HttpServlet
{    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        getServletContext().getRequestDispatcher("/WEB-INF/adminSplashPage.jsp").forward(request, response);
        return;
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        String destinationPage = request.getParameter("adminSubmit");
        
        switch (destinationPage)
        {
            case "User Management":         response.sendRedirect("admin/userManager");
                                            break;
            case "Role Management":         response.sendRedirect("admin/roleManager");
                                            break;
            case "Category Management":     response.sendRedirect("admin/categoryManager");
                                            break;
            default:                        getServletContext().getRequestDispatcher("/WEB-INF/adminSplashPage.jsp").forward(request, response);
        }
    }
}