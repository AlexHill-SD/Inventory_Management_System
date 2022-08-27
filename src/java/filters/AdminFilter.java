/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import services.AccountService;

/**
 *
 * @author BritishWaldo
 */
public class AdminFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
        
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        //before dofilter = request
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession httpSession = httpRequest.getSession();
        
        String username = (String) httpSession.getAttribute("username");
        
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        //if not admin then send to notes page
        //if user isn't even logged in they don't reach this point.
        if (!AccountService.userIsAdmin(username))
        {
            //deals with posibility that user might try to access deeper sections of the admin functionality
            //if there is more than one forward slash in the requested url then it boots them back a level to access inventory
            //solves issue with redirect pointing to a none existent inventory url within the admin section
            
            String requestURI = httpRequest.getRequestURI();
            requestURI = requestURI.substring(1);
            
            //requestURI = requestURI.substring(requestURI.indexOf('/'));

            int firstSlashIndex = requestURI.indexOf('/');
            int lastSlashIndex = requestURI.lastIndexOf('/');
            
            if (firstSlashIndex != lastSlashIndex)
            {
                httpResponse.sendRedirect("../inventory");
                return;
            }
            else
            {
                httpResponse.sendRedirect("inventory");
            return;
            }
        }
        
        chain.doFilter(request, response);
    }

    @Override
    public void destroy()
    {

    }
}
