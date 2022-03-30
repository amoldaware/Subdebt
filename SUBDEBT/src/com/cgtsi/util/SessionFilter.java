/*
 * Created on Sep 16, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author VT8150
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SessionFilter implements Filter {
	FilterConfig config=null;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
		config=null;

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(
		ServletRequest request,
		ServletResponse response,
		FilterChain chain)
		throws IOException, ServletException {
		//
		HttpServletRequest httpRequest=(HttpServletRequest)request;
		HttpServletResponse httpResponse=(HttpServletResponse)response;
		HttpSession session=httpRequest.getSession(false);
		
		//System.out.println("request context URI "+httpRequest.getRequestURI());
		//System.out.println("Filter ...session is "+session);
		String uri=httpRequest.getRequestURI();
		//System.out.println("uri is: "+uri);
		if(session!=null)
		{
			//System.out.println("Session is already exists");
			
			//System.out.println("Is it a new session? "+session.isNew());
			//session already exists.
			String userId=httpRequest.getParameter("userId");
			
			//System.out.println("uri is: "+uri);
			
			//System.out.println("user id from session "+session.getAttribute("user"));
			
			//System.out.println("user id from request "+userId);
			
			if(session.getAttribute(SessionConstants.USER)==null && (userId==null || userId.equals("")) 
				&& (!uri.equals(httpRequest.getContextPath()+"/jsp/Home.jsp") 
				&&		!uri.equals(httpRequest.getContextPath()+"/jsp/Login.jsp")
				&& !uri.equals(httpRequest.getContextPath()+"/jsp/SessionExpired.jsp"))
				&& !uri.equals(httpRequest.getContextPath()+"/login.do")
				&& !uri.equals(httpRequest.getContextPath()+"/showLogin.do")
				&& !uri.equals(httpRequest.getContextPath()+"/getHintQuestion.do")
				&& !uri.equals(httpRequest.getContextPath()+"/answerHintQuestion.do")) 
			{
				//Session expired. 
				//Invalid session.
					//System.out.println("Invalid session.. ");
					session.invalidate();
					//httpResponse.sendRedirect(httpRequest.getContextPath()+"/jsp/Home.jsp");
				httpResponse.sendRedirect(httpRequest.getContextPath()+"/jsp/SessionExpired.jsp");	
			}
			else
			{
				chain.doFilter(request,response);
			}
		}
		else
		{
			String sessionIdRequested=httpRequest.getRequestedSessionId();
			
			//System.out.println("sessionIdRequested "+sessionIdRequested);
			
			
			//System.out.println("Forwarded to index.jsp page");
			//httpResponse.setHeader("Refresh","0;"+httpRequest.getContextPath()+"/jsp/Index.jsp");
			///*
			if(sessionIdRequested!=null && !uri.equals(httpRequest.getContextPath()+"/jsp/Home.jsp"))
			{
				//The created session is expired. So, show the session expired page.
				RequestDispatcher requestDispatcher=request.getRequestDispatcher("/jsp/SessionExpired.jsp");
				requestDispatcher.forward(request,response);
				//httpResponse.sendRedirect(httpRequest.getContextPath()+"/jsp/SessionExpired.jsp");
			}
			else
			{
				session=httpRequest.getSession(true);
				//*/
				//System.out.println("redirected  to index.jsp page");
				httpResponse.sendRedirect(httpRequest.getContextPath()+"/jsp/Home.jsp");			
			}
			
			//httpRequest.getRequestDispatcher
			//(httpResponse.encodeURL("/jsp/Index.jsp")).forward(request,response);			
		}
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig filterConfig) throws ServletException {
		config=filterConfig;

	}

}
