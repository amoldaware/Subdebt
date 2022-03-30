/*
 * Created on Feb 10, 2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.admin;

//import java.sql.CallableStatement;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.sql.Types;

//import com.cgtsi.common.DatabaseException;
//import com.cgtsi.common.Log;
//import com.cgtsi.util.DBConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 * @author RT14509
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ScheduledProcess {
	
	
	/**
	 * 
	 */
	public ScheduledProcess() {

	}


	public static void main(String a[]) throws Exception
	{
		HttpURLConnection connection=getServletConnection(new URL("http://CKR3PCS049:8080/CGTSI/"),"UpdateServlet");
		
		OutputStream output=connection.getOutputStream();
		
		output.write(1);
		
		output.flush();
		
		output.close();
		
		InputStream inputStream=connection.getInputStream();
		
		inputStream.read();
		
		inputStream.close();
		

	}
 
	
	private static HttpURLConnection getServletConnection(URL loCodeBase, String lsServletName) throws Exception
	{

		HttpURLConnection loURLConnection = null;

		try
		{
			URL loUrl = new URL(loCodeBase, lsServletName);

			loURLConnection = (HttpURLConnection) loUrl.openConnection();
			System.out.println("Establishing Connection");

			loURLConnection.setRequestMethod("POST");
			loURLConnection.setDoInput(true);
			loURLConnection.setDoOutput(true);
			loURLConnection.connect();
			System.out.println("Connection Established");
		}
		catch(java.net.MalformedURLException loMalformedException)
		{
			Exception loException = new Exception("Malformed URL: " + loMalformedException.getMessage());

			throw(loException);
		}
		catch(java.io.IOException loIOException)
		{
			Exception loException = new Exception("IOException : " + loIOException.getMessage());

			throw(loException);
		}
		catch(Throwable loException)
		{
			Exception loExceptionNew = new Exception("Exception :" + loException.getMessage());

			throw(loExceptionNew);
		}

		return loURLConnection;
	}

	
}
