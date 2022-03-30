//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\DistrictMaster.java

package com.cgtsi.admin;
import com.cgtsi.common.DatabaseException;


/**
 * This value object encapsulates the district master details.
 */
public class DistrictMaster extends Master 
{
   private String stateName;
   private String districtName;
   private String createdBy;
   
   AdminDAO adminDAO;
   
   /**
    * @roseuid 39B875C50060
    */
   public DistrictMaster() 
   {
   		adminDAO=new AdminDAO();
    
   }
   
   /**
    * Access method for the stateName property.
    * 
    * @return   the current value of the stateName property
    */
   public String getStateName() 
   {
      return stateName;
   }
   
   /**
    * Sets the value of the stateName property.
    * 
    * @param aStateName the new value of the stateName property
    */
   public void setStateName(String aStateName) 
   {
      stateName = aStateName;
   }
   
   /**
    * Access method for the districtName property.
    * 
    * @return   the current value of the districtName property
    */
   public String getDistrictName() 
   {
      return districtName;
   }
   
   /**
    * Sets the value of the districtName property.
    * 
    * @param aDistrictName the new value of the districtName property
    */
   public void setDistrictName(String aDistrictName) 
   {
      districtName = aDistrictName;
   }
   
   /**
   * Updates the district name by calling the DBClass in AdminDAO
   */

   public void updateMaster() throws DatabaseException
   {	
		adminDAO.updateDistrictMaster(this,getCreatedBy());
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
