package com.supinfo.supcommerce.controler.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supinfo.sun.supcommerce.bo.SupProduct;
import com.supinfo.sun.supcommerce.doa.SupProductDao;

/**
 * Servlet implementation class InsertSomeProductServlet
 * 
 * Register a random generated product in memory (via SupCommerce.jar)
 * 
 * @author Elka
 * @version 2.3
 * @since SupCommerce 2.1
 */
@WebServlet(description = "Servlet To Insert Random Product", urlPatterns = "/auth/basicInsert")
public class InsertSomeProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertSomeProductServlet() {
        super();
    }

	/**
	 * Handles all HTTP methods (GET, POST, PUT, DELETE, ...).
	 * 
	 * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Random generator + protect the reference (optional)
		final Random rand = new Random();
		
		// String format for product price (optional - see DecimalFormat doc to use it)
		final DecimalFormat priceFormat = new DecimalFormat("0.00 €");
		
		// New product's creation
		// final keyword is to protect the reference of new product (best practice)
		final SupProduct product = new SupProduct();
		// Set product properties randomly 
		product.setName("Product-" + rand.nextInt(100));
		product.setContent("Product's Information of " + product.getName());
		product.setPrice(rand.nextFloat() + rand.nextInt(1000));
		
		// Generate ID and add product in memory
		SupProductDao.addProduct(product);
		
		/*!! This following part is just for testing, later we remove it and redirect to listProduct  !!*/
		
		// Set MIME type and charset (optional, many other HTTP header fields can be set)
		response.setContentType("text/html;charset=utf-8");
		
		PrintWriter out = response.getWriter();
		
		// HTML5 response (optional)
		out.println("<!DOCTYPE html>"
				+ "<html lang=\"en\">"
				
				+ "<head>"
					+ "<meta charset=\"UTF-8\">"
					+ "<meta content=\"IE=edge\" http-equiv=\"X-UA-Compatible\">"
					+ "<meta content=\"width=device-width, initial-scale=1.0\" name=\"viewport\">"
					+ "<title>InsertSomeProduct - Servlet</title>"
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
									+ "<li class=\"active\"><a href=\"/SupCommerce-2-3/auth/basicInsert\"><span class=\"glyphicon glyphicon-random\"></span>&nbsp; Random Product</a></li>"
								+ "</ul>"
							+ "</nav>"
						+ "</div>"
					+ "</div>"

					+ "<section id=\"main-container\" class=\"container\">"
						+ "<div class=\"row\">"
							+ "<h1 class=\"col-sx-12 col-sm-12 col-md-12 col-lg-12\">New Random Product added !</h1>"
							+ "<div class=\"col-sx-12 col-sm-6 col-md-4 col-lg-3\">"
								+ "<article class=\"panel panel-primary\">"
									+ "<header class=\"panel-heading\">"
										+ "<h3><a style=\"color: #FFF;\" href=\"/SupCommerce-2-3/showProduct?id=" + Long.toString(product.getId()) + "\"><span class=\"glyphicon glyphicon-tag\"></span>&nbsp; Product Id " + Long.toString(product.getId()) + "</a></h3>"
									+ "</header>"
									+ "<section class=\"panel-body\">"
										+ "<p>Name: " + product.getName() + "</p>"
										+ "<p>Description: " + product.getContent() + "</p>"
										+ "<p>Price: " + priceFormat.format(product.getPrice())  + "</p>"
									+ "</section>"
								+ "<article>"
							+ "</div>"
						+ "</div>"
					+ "</section>"
					
					+ "<script src=\"//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js\"></script>"
					+ "<script src=\"//netdna.bootstrapcdn.com/bootstrap/3.0.2/js/bootstrap.min.js\"></script>"
				+ "</body>"
					
				+ "</html>");        
        out.close();
	}

}
