package com.supinfo.supcommerce.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 * 
 * Create a new session and a "username" attribute in this one.
 * 
 * @author Elka
 * @version 2.3
 * @since SupCommerce 2.2
 */
@WebServlet(description = "Servlet To Control Login", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PARAM_USERNAME_POST = "username";
    private static final String LIST_PRODUCT_SERVLET = "/listProduct";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
    }

    /**
	 * Handles <code>POST</code> HTTP method
	 * 
	 * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Retrieve form parameter
		final String usernameParam = request.getParameter(PARAM_USERNAME_POST);
		
		// Insert it in session attribute
		request.getSession().setAttribute(PARAM_USERNAME_POST, usernameParam);
		
		//Redirect to products list
		response.sendRedirect(request.getServletContext().getContextPath() + LIST_PRODUCT_SERVLET);
	}

}
