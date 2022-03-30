//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\StateMaster.java


package com.cgtsi.admin;
import com.cgtsi.common.DatabaseException;


/**
 * This value object encapsulates the state master details.
 */
public class RegionMaster extends Master 
{
   private String regionName;
   private String zoneName;
   private String regionDesc;
   private String createdBy;
   
   /**
    * @roseuid 39B875C5001A
    */
   public RegionMaster() 
   {
    
   }
   
   
   /**
  * Updates the state details by calling the DBClass in AdminDAO
  */
   public void updateMaster() throws DatabaseException
   {	
	AdminDAO adminDAO=new AdminDAO();
	adminDAO.updateRegionMaster(this,getCreatedBy());
	
    }

/**
 * @return
 */
public String getRegionName() {
	return regionName;
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
public void setRegionName(String string) {
	regionName = string;
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
public String getRegionDesc() {
	return regionDesc;
}

/**
 * @param string
 */
public void setRegionDesc(String string) {
	regionDesc = string;
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
