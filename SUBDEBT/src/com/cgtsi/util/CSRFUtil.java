package com.cgtsi.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cgtsi.action.CaptchaSession;

public class CSRFUtil {
	public static boolean getCSRFflag(String csrfCode, HttpSession session, HttpServletRequest request){
        String CSRFcodeSes = (String)session.getAttribute("cptchaString");
        if(!csrfCode.equals(CSRFcodeSes) ){
                        return false;
        }
        else{
	        session.setAttribute("cptchaString",CaptchaSession.getCaptchaSession(request).toString());
	        return true;
        }
}

}
