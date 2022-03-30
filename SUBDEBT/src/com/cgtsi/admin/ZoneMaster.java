//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\StateMaster.java


package com.cgtsi.admin;
import com.cgtsi.common.DatabaseException;


/**
 * This value object encapsulates the state master details.
 */
public class ZoneMaster extends Master 
{
   private String zoneName;
   private String zoneDesc;
   private String createdBy;
   
   /**
    * @roseuid 39B875C5001A
    */
   public ZoneMaster() 
   {
    
   }
   
   /**
    * Access method for the stateName property.
    * 
    * @return   the current value of the stateName property
    */
   
   /**
  * Updates the state details by calling the DBClass in AdminDAO
  */
   public void updateMaster() throws DatabaseException
   {
		AdminDAO adminDAO=new AdminDAO();
		adminDAO.updateZoneMaster(this,getCreatedBy());
    }

/**
 * @return
 */
public String getZoneName() {
	return zoneName;
}

/**
 * @param string
 */
public void setZoneName(String string) {
	zoneName = string;
}

/**
 * @return
 */
public String getZoneDesc() {
	return zoneDesc;
}

/**
 * @param string
 */
public void setZoneDesc(String string) {
	zoneDesc = string;
}

/**
 * @return
 */
public String getCreatedBy() {
	return createdBy;
}

/**
 * @param string
 */
public void setCreatedBy(String string) {
	createdBy = string;
}

}
