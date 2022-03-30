package com.cgtsi.risk;
import java.util.Date;

/*************************************************************
   *
   * Name of the class: UserLimits
   * This class encapsulates the various parameters for user limits setting. This 
   * class has setter and getter methods to set and retrieve the attributes.
   * 
   * @author : Nithyalakshmi P
   * @version:  
   * @since: 
   **************************************************************/

public class UserLimits  implements java.io.Serializable
{
   private String designation;
   private double upperApplicationApprovalLimit;
   private Date applicationLimitValidFrom;				//included 10-09-2003
   private Date applicationLimitValidTo;				//included 10-09-2003
   private double upperClaimsApprovalLimit;
   private Date claimsLimitValidFrom;					//included 10-09-2003
   private Date claimsLimitValidTo;						//included 10-09-2003
   private String createdBy;
   private Date createdDate;
   
   public UserLimits() 
   {
    
   }
   
   /**
    * Access method for the designation property.
    * 
    * @return   the current value of the designation property
    */
   public String getDesignation() 
   {
      return designation;    
   }
   
   /**
    * Sets the value of the designation property.
    * 
    * @param aDesignation the new value of the designation property
    */
   public void setDesignation(String aDesignation) 
   {
      designation = aDesignation;
   }

   /**
    * Access method for the upperApplicationApprovalLimit property.
    * 
    * @return   the current value of the upperApplicationApprovalLimit property
    */
   public double getUpperApplicationApprovalLimit() 
   {
      return upperApplicationApprovalLimit;    
   }
   
   /**
    * Sets the value of the upperApplicationApprovalLimit property.
    * 
    * @param aUpperApplicationApprovalLimit the new value of the upperApplicationApprovalLimit property
    */
   public void setUpperApplicationApprovalLimit(double aUpperApplicationApprovalLimit) 
   {
      upperApplicationApprovalLimit = aUpperApplicationApprovalLimit;
   }

   /**
    * Access method for the applicationLimitValidFrom property.
    * 
    * @return   the current value of the applicationLimitValidFrom property
    */
   public Date getApplicationLimitValidFrom() 
   {
      return applicationLimitValidFrom;    
   }
   
	/**
    * Sets the value of the applicationLimitValidFrom property.
    * 
    * @param aApplicationLimitValidFrom the new value of the applicationLimitValidFrom property
    */
   public void setApplicationLimitValidFrom(Date aApplicationLimitValidFrom) 
   {
      applicationLimitValidFrom = aApplicationLimitValidFrom;
   }

    /**
    * Access method for the applicationLimitValidTo property.
    * 
    * @return   the current value of the applicationLimitValidTo property
    */
   public Date getApplicationLimitValidTo() 
   {
      return applicationLimitValidTo;    
   }
   
   /**
    * Sets the value of the applicationLimitValidTo property.
    * 
    * @param aApplicationLimitValidTo the new value of the applicationLimitValidTo property
    */
   public void setApplicationLimitValidTo(Date aApplicationLimitValidTo) 
   {
      applicationLimitValidTo = aApplicationLimitValidTo;
   }
   
   /**
    * Access method for the upperClaimsApprovalLimit property.
    * 
    * @return   the current value of the upperClaimsApprovalLimit property
    */
   public double getUpperClaimsApprovalLimit() 
   {
      return upperClaimsApprovalLimit;    
   }
   
   /**
    * Sets the value of the upperClaimsApprovalLimit property.
    * 
    * @param aUpperClaimsApprovalLimit the new value of the upperClaimsApprovalLimit property
    */
   public void setUpperClaimsApprovalLimit(double aUpperClaimsApprovalLimit) 
   {
      upperClaimsApprovalLimit = aUpperClaimsApprovalLimit;
   }

    /**
    * Access method for the claimsLimitValidFrom property.
    * 
    * @return   the current value of the claimsLimitValidFrom property
    */
   public Date getClaimsLimitValidFrom() 
   {
      return claimsLimitValidFrom;    
   }
   
	/**
    * Sets the value of the claimsLimitValidFrom property.
    * 
    * @param aClaimsLimitValidFrom the new value of the claimsLimitValidFrom property
    */
   public void setClaimsLimitValidFrom(Date aClaimsLimitValidFrom) 
   {
      claimsLimitValidFrom = aClaimsLimitValidFrom;
   }

    /**
    * Access method for the claimsLimitValidTo property.
    * 
    * @return   the current value of the claimsLimitValidTo property
    */
   public Date getClaimsLimitValidTo() 
   {
      return claimsLimitValidTo;    
   }
   
   /**
    * Sets the value of the claimsLimitValidTo property.
    * 
    * @param aClaimsLimitValidTo the new value of the claimsLimitValidTo property
    */
   public void setClaimsLimitValidTo(Date aClaimsLimitValidTo) 
   {
      claimsLimitValidTo = aClaimsLimitValidTo;
   }
   
   /**
    * Access method for the createdBy property.
    * 
    * @return   the current value of the createdBy property
    */
   public String getCreatedBy() 
   {
      return createdBy;    
   }
   
   /**
    * Sets the value of the createdBy property.
    * 
    * @param aCreatedBy the new value of the createdBy property
    */
   public void setCreatedBy(String aCreatedBy) 
   {
      createdBy = aCreatedBy;
   }
   
   /**
    * Access method for the createdDate property.
    * 
    * @return   the current value of the createdDate property
    */
   public Date getCreatedDate() 
   {
      return createdDate;    
   }
   
   /**
    * Sets the value of the createdDate property.
    * 
    * @param aCreatedDate the new value of the createdDate property
    */
   public void setCreatedDate(Date aCreatedDate) 
   {
      createdDate = aCreatedDate;
   }
}
