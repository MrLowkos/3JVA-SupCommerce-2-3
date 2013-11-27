package com.supinfo.supcommerce.controler.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class AuthenticateFilter Check authentication
 * 
 * @author Elka
 * @version 2.3
 * @since SupCommerce 2.2
 */
@WebFilter(displayName = "Authenticate", description = "Filter To Check Authentication", urlPatterns = "/auth/*")
public class AuthenticateFilter implements Filter {
	
	private FilterConfig		filterConfig;
	private static final String	PARAM_USERNAME_SESSION	= "username";
	private static final String	LOGIN_VIEW				= "/login.html";
	private static final String	ROOT_VIEW				= "/";
	
	/**
	 * Default constructor.
	 */
	public AuthenticateFilter() {
	}
	
	/**
	 * @see Filter#destroy()
	 */
	@Override
	public void destroy() {
	}
	
	/**
	 * Filter requested url bind on /auth/...
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		
		// Http protocol only
		HttpServletRequest httpRequest = null;
		HttpServletResponse httpResponse = null;
		
		// Legitimate cast
		if (request instanceof HttpServletRequest)
			httpRequest = (HttpServletRequest) request;
		if (response instanceof HttpServletResponse)
			httpResponse = (HttpServletResponse) response;
		// Unlegitimate cast (original request or response type is not Http)
		// to force redirection to root page
		if (httpRequest == null || httpResponse == null)
			((HttpServletResponse) response).sendRedirect(this.getFilterConfig().getServletContext().getContextPath()
					+ ROOT_VIEW);
		
		// Retrieve username session parameter
		final Object usernameParam = httpRequest.getSession().getAttribute(PARAM_USERNAME_SESSION);
		
		// Username exist in session, user is proprely logged :s password ... MD5 ... session token ???
		if (usernameParam != null && usernameParam instanceof String)
			// Redirect to next requested chain element
			chain.doFilter(request, response);
		else
			// Redirect to login page
			httpResponse.sendRedirect(httpRequest.getContextPath() + LOGIN_VIEW);
		
	}
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.setFilterConfig(fConfig);
	}
	
	public FilterConfig getFilterConfig() {
		return filterConfig;
	}
	
	public void setFilterConfig(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	}
	
}
