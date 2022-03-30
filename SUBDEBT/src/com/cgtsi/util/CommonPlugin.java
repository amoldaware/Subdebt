/*
 * Created on Oct 1, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.util; 

import javax.servlet.ServletException;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

/**
 * @author VT8150
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CommonPlugin implements PlugIn {

	/* (non-Javadoc)
	 * @see org.apache.struts.action.PlugIn#destroy()
	 */
	public void destroy() {
		//close all opened connections and do
		// the necessary clean up activities.
		DBConnection.destroy();
		ConvertUtils.deregister(DateConverter.class);

	}

	/* (non-Javadoc)
	 * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet, org.apache.struts.config.ModuleConfig)
	 */
	public void init(ActionServlet servlet, ModuleConfig config)
		throws ServletException {
			try
			{
				//System.out.println("******************************");
				//Load the configuration properties.
				String realPath=servlet.getServletContext().getRealPath("");
				//System.out.println("Real Path "+realPath);
				
				ConvertUtils.register(new DateConverter(null),java.util.Date.class);
				//ConvertUtils.register(new IntegerConverter(null),java.lang.Integer.class);
				//ConvertUtils.register(new DateConverter(),CustomisedDate.class);
				//ConvertUtils.register(new DateConverter(),StringDate.class);
				
				
				PropertyLoader.loadProperties(realPath);
				
				//Start the connection pool.
				DBConnection.startConnectionPool();
				
				//System.out.println("******************************");
			}
			catch(Exception loe)
			{
				throw new ServletException(loe.getMessage());
			}
	}

}
