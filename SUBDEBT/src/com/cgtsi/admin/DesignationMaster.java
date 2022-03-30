//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\DesignationMaster.java

package com.cgtsi.admin;
import com.cgtsi.common.DatabaseException;


/**
 * This value object encapsulates the designation master details.
 */
public class DesignationMaster extends Master 
{
   private String desigName;
   private String desigDesc;
   private String newDesigName;
   AdminDAO adminDAO;
   
   /**
    * @roseuid 39B875C70167
    */
   public DesignationMaster() 
   {
	   adminDAO=new AdminDAO();
    
   }
   
   
   /**
   * Updates the designation details by calling the DBClass in AdminDAO
   */
	 public void updateMaster() throws DatabaseException
	{		
		adminDAO.updateDesignationMaster(this);
	}



/**
 * @return
 */
public String getDesigDesc() {
	return desigDesc;
}

/**
 * @return
 */
public String getDesigName() {
	return desigName;
}

/**
 * @return
 */
public String getNewDesigName() {
	return newDesigName;
}

/**
 * @param string
 */
public void setDesigDesc(String string) {
	desigDesc = string;
}

/**
 * @param string
 */
public void setDesigName(String string) {
	desigName = string;
}

/**
 * @param string
 */
public void setNewDesigName(String string) {
	newDesigName = string;
}

}
