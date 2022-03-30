 /*
 * Created on Nov 22, 2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.cgtsi.reports;
import com.cgtsi.claim.CPDAO;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.Constants;
import com.cgtsi.claim.ClaimConstants;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;
import com.cgtsi.claim.ClaimApplication;

public class NewReportManager
{
	private NewReportDAO newreportDao = null;
	private CPDAO cpDao;

	public NewReportManager()
	{
		newreportDao = new NewReportDAO();		
		cpDao = null; 		
	}

	
 

}
