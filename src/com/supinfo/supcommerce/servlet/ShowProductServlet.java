package com.supinfo.supcommerce.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.sun.supcommerce.bo.SupProduct;
import com.supinfo.sun.supcommerce.doa.SupProductDao;
import com.supinfo.sun.supcommerce.exception.UnknownProductException;

/**
 * Servlet implementation class ShowProductServlet
 * 
 * Show product registered in memory (via SupCommerce.jar)
 * by "id" <code>GET</code> parameter
 * use contextPath/showProduct?id=
 * 
 * @author Elka
 * @version 2.3
 * @since SupCommerce 2.1
 */
@WebServlet(description = "Servlet To Show A Registered Product By ID", urlPatterns = "/showProduct")
public class ShowProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String PARAM_ID_GET = "id";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShowProductServlet() {
        super();
    }

    /**
	 * Handles <code>GET</code> HTTP method
	 * 
	 * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// String format for product price (optional)
		final DecimalFormat priceFormat = new DecimalFormat("0.00 €");
		
		// Recover id parameter through url 
		final String productId = request.getParameter(PARAM_ID_GET);
		
		// Set MIME type and Charset
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		
		// HTML5 response (optional)
 		out.println("<!DOCTYPE html>"
 				+ "<html lang=\"en\">"
 				
 				+ "<head>"
 					+ "<meta charset=\"UTF-8\">"
 					+ "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\">"
 					+ "<meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\">"
 					+ "<title>ShowProduct - Servlet</title>"
 					// CSS bootstrap 3.0
 					+ "<link rel=\"stylesheet\" href=\"//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css\">"
 					+ "<link rel=\"stylesheet\" href=\"//netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap-theme.min.css\">"					
 				+ "</head>"
 					
 				+ "<body style=\"padding-top: 70px;\">"
 				
 					+ "<div id=\"header\" class=\"navbar navbar-fixed-top navbar-inverse\">"
 						+ "<div class=\"container\">"
 							+ "<header class=\"navbar-header\">"
 								+ "<button class=\"navbar-toggle collapsed\" data-target=\".navbar-collapse\" data-toggle=\"collapse\" type=\"button\">"
 									+ "<span class=\"icon-bar\"></span>"
 									+ "<span class=\"icon-bar\"></span>"
 									+ "<span class=\"icon-bar\"></span>"
 								+ "</button>"
 								+ "<a class=\"navbar-brand\" href=\"/SupCommerce-2-3\">SupCommerce 2.3</a>"
 							+ "</header>"
 							+ "<nav class=\"navbar-collapse collapse\" style=\"height: 0.8px;\">"
 								+ "<ul class=\"nav navbar-nav\">"
 									+ "<li><a href=\"/SupCommerce-2-3\"><span class=\"glyphicon glyphicon-home\"></span>&nbsp; Home</a></li>"
 									+ "<li><a href=\"/SupCommerce-2-3/listProduct\"><span class=\"glyphicon glyphicon-list\"></span>&nbsp; Products List</a></li>"
 									+ "<li><a href=\"/SupCommerce-2-3/auth/basicInsert\"><span class=\"glyphicon glyphicon-random\"></span>&nbsp; Random Product</a></li>"
 								+ "</ul>"
 							+ "</nav>"
 						+ "</div>"
 					+ "</div>"
		
					+ "<section id=\"main-container\" class=\"container\">"
						+ "<div class=\"row\">"
						+ "<h1 class=\"col-sx-12 col-sm-12 col-md-12 col-lg-12\">Product Details</h1>");

				        // If product Id parameter exists
						if(productId != null) {
							/// We try to found the product with matching id and next print its information
							try {
								SupProduct product = SupProductDao.findProductById(Long.parseLong(productId));										
								
								out.println(
										"<div class=\"col-sx-12 col-sm-6 col-md-4 col-lg-3\">"
										+ "<article class=\"panel panel-primary\">"
											+ "<header class=\"panel-heading\">"
												// Long.toString() - optional
												+ "<h3 style=\"color: #FFF;><span class=\"glyphicon glyphicon-tag\"></span>&nbsp; Product Id " + Long.toString(product.getId()) + "</h3>"
											+ "</header>"
											+ "<section class=\"panel-body\">"
												+ "<p>Name: " + product.getName() + "</p>"
												+ "<p>Description: " + product.getContent() + "</p>"
												+ "<p>Price: " + priceFormat.format(product.getPrice())  + "</p>"
											+ "</section>"
										+ "<article>"
									+ "</div>");
							}
							/// Product with id parameter doesn't exist
							catch(UnknownProductException e) {
								out.println("<div class=\"alert alert-danger col-sx-12 col-sm-12 col-md-12 col-lg-12\"><h3><span class=\"glyphicon glyphicon-warning-sign\"></span>&nbsp; No product found for this ID !</h3></div>");
							}
							/// Id parameter doesn't have correct type and/or format
							catch(NumberFormatException e) {
								out.println("<div class=\"alert alert-danger col-sx-12 col-sm-12 col-md-12 col-lg-12\"><h3><span class=\"glyphicon glyphicon-warning-sign\"></span>&nbsp; A correct ID's type (Long) is needed !</h3></div>");
							}
						}
						// No id parameter in URI
						else {
							out.println("<div class=\"alert alert-danger col-sx-12 col-sm-12 col-md-12 col-lg-12\"><h3><span class=\"glyphicon glyphicon-warning-sign\"></span>&nbsp; A Product ID's parameter is needed in URI !</h3></div>");
						}

				out.println(
					  "</div>"
				+ "</section>"

			+ "<script src=\"//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\"></script>"
			+ "<script src=\"//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js\"></script>"
		+ "</body>"

		+ "</html>");        
	out.close();
        
	}

}
