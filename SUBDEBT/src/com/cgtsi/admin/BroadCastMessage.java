//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\admin\\BroadCastMessage.java

package com.cgtsi.admin;
import java.util.ArrayList;

/**
 * This class holds the information related to broadcaste message.
 */
public class BroadCastMessage 
{
   private boolean allMembers;
   private boolean allHos;
   private boolean memberOfBank;
   private boolean membersOfZoOrRO;
   private boolean membersOfBranch;
   private boolean noOfBank;
   private boolean noOfZonesRegions;
   private boolean noOfBranches;
   private String bankName;
   private String[] zoneRegions;
   private String[] branchNames;
   private String message;
   private ArrayList userIds;
   
   /**
    * @roseuid 399E5AF5031F
    */
   public BroadCastMessage() 
   {
    
   }
   
   /**
    * Access method for the allHos property.
    * 
    * @return   the current value of the allHos property
    */
   public boolean getAllHos() 
   {
      return allHos;
   }
   
   /**
    * Sets the value of the allHos property.
    * 
    * @param aAllHos the new value of the allHos property
    */
   public void setAllHos(boolean aAllHos) 
   {
      allHos = aAllHos;
   }
   
   /**
    * Access method for the memberOfBank property.
    * 
    * @return   the current value of the memberOfBank property
    */
   public boolean getMemberOfBank() 
   {
      return memberOfBank;
   }
   
   /**
    * Sets the value of the memberOfBank property.
    * 
    * @param aMemberOfBank the new value of the memberOfBank property
    */
   public void setMemberOfBank(boolean aMemberOfBank) 
   {
      memberOfBank = aMemberOfBank;
   }
   
   /**
    * Access method for the membersOfZoOrRO property.
    * 
    * @return   the current value of the membersOfZoOrRO property
    */
   public boolean getMembersOfZoOrRO() 
   {
      return membersOfZoOrRO;
   }
   
   /**
    * Sets the value of the membersOfZoOrRO property.
    * 
    * @param aMembersOfZoOrRO the new value of the membersOfZoOrRO property
    */
   public void setMembersOfZoOrRO(boolean aMembersOfZoOrRO) 
   {
      membersOfZoOrRO = aMembersOfZoOrRO;
   }
   
   /**
    * Access method for the membersOfBranch property.
    * 
    * @return   the current value of the membersOfBranch property
    */
   public boolean getMembersOfBranch() 
   {
      return membersOfBranch;
   }
   
   /**
    * Sets the value of the membersOfBranch property.
    * 
    * @param aMembersOfBranch the new value of the membersOfBranch property
    */
   public void setMembersOfBranch(boolean aMembersOfBranch) 
   {
      membersOfBranch = aMembersOfBranch;
   }
   
   /**
    * Access method for the noOfBank property.
    * 
    * @return   the current value of the noOfBank property
    */
   public boolean getNoOfBank() 
   {
      return noOfBank;
   }
   
   /**
    * Sets the value of the noOfBank property.
    * 
    * @param aNoOfBank the new value of the noOfBank property
    */
   public void setNoOfBank(boolean aNoOfBank) 
   {
      noOfBank = aNoOfBank;
   }
   
   /**
    * Access method for the noOfZonesRegions property.
    * 
    * @return   the current value of the noOfZonesRegions property
    */
   public boolean getNoOfZonesRegions() 
   {
      return noOfZonesRegions;
   }
   
   /**
    * Sets the value of the noOfZonesRegions property.
    * 
    * @param aNoOfZonesRegions the new value of the noOfZonesRegions property
    */
   public void setNoOfZonesRegions(boolean aNoOfZonesRegions) 
   {
      noOfZonesRegions = aNoOfZonesRegions;
   }
   
   /**
    * Access method for the noOfBranches property.
    * 
    * @return   the current value of the noOfBranches property
    */
   public boolean getNoOfBranches() 
   {
      return noOfBranches;
   }
   
   /**
    * Sets the value of the noOfBranches property.
    * 
    * @param aNoOfBranches the new value of the noOfBranches property
    */
   public void setNoOfBranches(boolean aNoOfBranches) 
   {
      noOfBranches = aNoOfBranches;
   }
   
   /**
    * Access method for the bankName property.
    * 
    * @return   the current value of the bankName property
    */
   public String getBankName() 
   {
      return bankName;
   }
   
   /**
    * Sets the value of the bankName property.
    * 
    * @param aBankName the new value of the bankName property
    */
   public void setBankName(String aBankName) 
   {
      bankName = aBankName;
   }
   
   /**
    * Access method for the zoneRegions property.
    * 
    * @return   the current value of the zoneRegions property
    */
   public String[] getZoneRegions() 
   {
      return zoneRegions;
   }
   
   /**
    * Sets the value of the zoneRegions property.
    * 
    * @param aZoneRegions the new value of the zoneRegions property
    */
   public void setZoneRegions(String[] aZoneRegions) 
   {
      zoneRegions = aZoneRegions;
   }
   
   /**
    * Access method for the branchNames property.
    * 
    * @return   the current value of the branchNames property
    */
   public String[]getBranchNames() 
   {
      return branchNames;
   }
   
   /**
    * Sets the value of the branchNames property.
    * 
    * @param aBranchNames the new value of the branchNames property
    */
   public void setBranchNames(String[] aBranchNames) 
   {
      branchNames = aBranchNames;
   }
   
   /**
    * Access method for the message property.
    * 
    * @return   the current value of the message property
    */
   public String getMessage() 
   {
      return message;
   }
   
   /**
    * Sets the value of the message property.
    * 
    * @param aMessage the new value of the message property
    */
   public void setMessage(String aMessage) 
   {
      message = aMessage;
   }
/**
 * @return
 */
public ArrayList getUserIds() {
	return userIds;
}

/**
 * @param list
 */
public void setUserIds(ArrayList list) {
	userIds = list;
}

/**
 * @return
 */
public boolean isAllMembers() {
	return allMembers;
}

/**
 * @param b
 */
public void setAllMembers(boolean b) {
	allMembers = b;
}

}
