//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\claim\\CPHelper.java

package com.cgtsi.claim;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * This is a helper class used by ClaimsProcessor class to accompalish certain
 * needs such as generation of ids etc
 */
public class CPHelper
{

   /**
    * @roseuid 39B875CD00C6
    */
   public CPHelper()
   {

   }

   /**
    * This method generates the claim reference number if a claim application is
    * approved by CGTSI user.
    * @param claimApp
    * @return String
    * @roseuid 387C844803BA
    */
   public String generateClaimRefNo(int claimNo,String cgbid)
   {
	   /*
   		if(cgbid==null || claimNo>ClaimConstants.SECOND_INSTALLMENT
   				 || claimNo<ClaimConstants.FIRST_INSTALLMENT)
   		{
   			return null;
   		}
   		else
   		{
			SimpleDateFormat format=new SimpleDateFormat("yy");
			String year=format.format(new Date(),new StringBuffer(),new FieldPosition(0)).toString();

   			return "CL0"+claimNo+year+cgbid;
   		}
        */
        return null;
   }

   /**
    * This method generates Claim Settlement Advice number for a claim reference
    * number
    * @return String
    * @roseuid 39BB55D50022
    */
   public String generateCSA()
   {
    return null;
   }

   /**
    * This method generates a File containing the claim application details. This
    * file is eventually exported to client selected location.
    * @param claimApps
    * @return java.io.File
    * @roseuid 39BB5B4101D7
    */
   public java.io.File generateExportFile(ArrayList claimApps)
   {
    return null;
   }
}
