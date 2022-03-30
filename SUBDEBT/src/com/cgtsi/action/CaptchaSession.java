package com.cgtsi.action;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


public class CaptchaSession {
	
	static Logger logger = Logger.getRootLogger();
	public static String getCaptchaSession(HttpServletRequest request)
    {
		String ch = null;
        try
        {
        	Random r = new Random();
      	  	String token = Long.toString(Math.abs(r.nextLong()), 36);
      	  	ch = token.substring(0,10);
      	  	HttpSession session = request.getSession(true);
      	  	session.setAttribute("cptchaString", ch);
      	  	////System.out.println("chchchch: - " + ch);
      	  	
        }
        catch (Exception e)
        {
               logger.error(" Exception in com.captcha.CaptchaSession::getCaptchaSession()::"+e); 
        }
        return ch;
    }
	
	public static String getCaptchaSessionLgOt(HttpServletRequest request)
    {
		String ch = null;
        try
        {
        	Random r = new Random();
      	  	String token = Long.toString(Math.abs(r.nextLong()), 36);
      	  	ch = token.substring(0,6);
      	  	HttpSession session = request.getSession(true);
      	  	session.setAttribute("cptchaStringLgOt", ch);
      	  	////System.out.println("chchchch: - " + ch);
      	  	
        }
        catch (Exception e)
        {
            
            logger.error(" Exception in com.captcha.CaptchaSession::getCaptchaSessionLgOt()::"+e); 
        }
        return ch;
    }
}
