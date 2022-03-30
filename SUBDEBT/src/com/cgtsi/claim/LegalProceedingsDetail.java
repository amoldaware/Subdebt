//Source file: F:\\CGTSI\\Design\\DesignModel\\com\\cgtsi\\claim\\LegalProceedingsDetail.java

package com.cgtsi.claim;

import java.util.Date;
import java.text.SimpleDateFormat;

public class LegalProceedingsDetail implements java.io.Serializable
{
   private String borrowerId;
   private String forumRecoveryProceedingsInitiated;
   private String suitCaseRegNumber;
   private String nameOfForum;
   private String currentStatusRemarks;
   private String isRecoveryProceedingsConcluded;
   private double amountClaimed;
   private String location;
   private Date filingDate;
   private boolean areLegalProceedingsDetailsAvl;
   private java.util.Date dateOfConclusionOfRecoveryProceedings;
   private String filingDateStr;
   private SimpleDateFormat sdf = null;
   private String dateOfConclusionOfRecoveryProceedingsStr;
   private String commHeadedByOfficerOrAbove;
   
   public String getCommHeadedByOfficerOrAbove() {
	return commHeadedByOfficerOrAbove;
}

public void setCommHeadedByOfficerOrAbove(String commHeadedByOfficerOrAbove) {
	this.commHeadedByOfficerOrAbove = commHeadedByOfficerOrAbove;
}

public LegalProceedingsDetail()
   {

   }

   public String getBorrowerId()
   {
	   return borrowerId;
   }

   public void setBorrowerId(String aBorrowerId)
   {
	   borrowerId = aBorrowerId;
   }

   /**
    * Access method for the forumRecoveryProceedingsInitiated property.
    *
    * @return   the current value of the forumRecoveryProceedingsInitiated property
    */
   public String getForumRecoveryProceedingsInitiated()
   {
      return forumRecoveryProceedingsInitiated;
   }

   /**
    * Sets the value of the forumRecoveryProceedingsInitiated property.
    *
    * @param aForumRecoveryProceedingsInitiated the new value of the forumRecoveryProceedingsInitiated property
    */
   public void setForumRecoveryProceedingsInitiated(String aForumRecoveryProceedingsInitiated)
   {
      forumRecoveryProceedingsInitiated = aForumRecoveryProceedingsInitiated;
   }

   /**
    * Access method for the suitCaseRegNumber property.
    *
    * @return   the current value of the suitCaseRegNumber property
    */
   public String getSuitCaseRegNumber()
   {
      return suitCaseRegNumber;
   }

   /**
    * Sets the value of the suitCaseRegNumber property.
    *
    * @param aSuitCaseRegNumber the new value of the suitCaseRegNumber property
    */
   public void setSuitCaseRegNumber(String aSuitCaseRegNumber)
   {
      suitCaseRegNumber = aSuitCaseRegNumber;
   }

   /**
    * Access method for the nameOfForum property.
    *
    * @return   the current value of the nameOfForum property
    */
   public String getNameOfForum()
   {
      return nameOfForum;
   }

   /**
    * Sets the value of the nameOfForum property.
    *
    * @param aNameOfForum the new value of the nameOfForum property
    */
   public void setNameOfForum(String aNameOfForum)
   {
      nameOfForum = aNameOfForum;
   }

   /**
    * Access method for the currentStatusRemarks property.
    *
    * @return   the current value of the currentStatusRemarks property
    */
   public String getCurrentStatusRemarks()
   {
      return currentStatusRemarks;
   }

   /**
    * Sets the value of the currentStatusRemarks property.
    *
    * @param aCurrentStatusRemarks the new value of the currentStatusRemarks property
    */
   public void setCurrentStatusRemarks(String aCurrentStatusRemarks)
   {
      currentStatusRemarks = aCurrentStatusRemarks;
   }

   /**
    * Determines if the isRecoveryProceedingsConcluded property is true.
    *
    * @return   <code>true<code> if the isRecoveryProceedingsConcluded property is true
    */
   public String getIsRecoveryProceedingsConcluded()
   {
      return isRecoveryProceedingsConcluded;
   }

   /**
    * Sets the value of the isRecoveryProceedingsConcluded property.
    *
    * @param aIsRecoveryProceedingsConcluded the new value of the isRecoveryProceedingsConcluded property
    */
   public void setIsRecoveryProceedingsConcluded(String aIsRecoveryProceedingsConcluded)
   {
      isRecoveryProceedingsConcluded = aIsRecoveryProceedingsConcluded;
   }

   public double getAmountClaimed()
   {
	   return amountClaimed;
   }

   public void setAmountClaimed(double aAmountClaimed)
   {
	   amountClaimed = aAmountClaimed;
   }

   public String getLocation()
   {
	   return location;
   }

   public void setLocation(String aLocation)
   {
	   location = aLocation;
   }

   public Date getFilingDate()
   {
   	   return filingDate;
   }

   public void setFilingDate(Date aFilingDate)
   {
   	   filingDate = aFilingDate;
   }

   public boolean getAreLegalProceedingsDetailsAvl()
   {
	   return this.areLegalProceedingsDetailsAvl;
   }

   public void setAreLegalProceedingsDetailsAvl(boolean flag)
   {
	   this.areLegalProceedingsDetailsAvl = flag;
   }

   public java.util.Date getDateOfConclusionOfRecoveryProceedings()
   {
	   return this.dateOfConclusionOfRecoveryProceedings;
   }

   public void setDateOfConclusionOfRecoveryProceedings(java.util.Date date)
   {
	   this.dateOfConclusionOfRecoveryProceedings = date;
   }

   public String getFilingDateStr()
   {
	   sdf = new SimpleDateFormat("dd/MM/yyyy");
	   if(filingDate != null)
	   {
		   filingDateStr = sdf.format(filingDate);
	   }
	   return this.filingDateStr;
   }

   public void setFilingDateStr(String str)
   {
	   this.filingDateStr = str;
   }

   public String getDateOfConclusionOfRecoveryProceedingsStr()
   {
	   sdf = new SimpleDateFormat("dd/MM/yyyy");
	   if(dateOfConclusionOfRecoveryProceedings != null)
	   {
		   dateOfConclusionOfRecoveryProceedingsStr = sdf.format(dateOfConclusionOfRecoveryProceedings);
	   }
	   return this.dateOfConclusionOfRecoveryProceedingsStr;
   }

   public void setDateOfConclusionOfRecoveryProceedings(String str)
   {
	   this.dateOfConclusionOfRecoveryProceedingsStr = str;
   }
}
