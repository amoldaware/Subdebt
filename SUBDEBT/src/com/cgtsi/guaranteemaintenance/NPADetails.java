//Source file: D:\\com\\cgtsi\\guaranteemaintenance\\NPADetails.java

package com.cgtsi.guaranteemaintenance;

import java.util.Date;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * This class provides data members and methods to manipulate details regarding
 * NPA details. The data members are precisely the user inputs.
 */
public class NPADetails implements Serializable
{
   private String npaId;
   private String cgbid;
   private String reference;
   private Date npaDate;
   private double osAmtOnNPA;
   private String whetherNPAReported;
   private Date reportingDate;
   private String npaReason;
   private String willfulDefaulter;
   private String effortsTaken;

   private int noOfActions;
   private LegalSuitDetail legalSuitDetail;
   private ArrayList recoveryProcedure ;

   private String isRecoveryInitiated;
   private Date effortsConclusionDate;
   private String mliCommentOnFinPosition;
   private String detailsOfFinAssistance;
   private String creditSupport;
   private String bankFacilityDetail;
   private String placeUnderWatchList;
   private String remarksOnNpa;
   

   // Properties to be captured in Claim Second Installment
   private java.util.Date dtOfConclusionOfRecProc;
   private String whetherWrittenOff;
   private java.util.Date dtOnWhichAccntWrittenOff;
   private String cgtsiReportingMode;

   /**
    * @roseuid 39B9CCD9032C
    */
   public NPADetails()
   {

   }

   /**
    * Access method for the npaId property.
    *
    * @return   the current value of the npaId property
    */
   public String getNpaId()
   {
      return npaId;
   }

   /**
    * Sets the value of the npaId property.
    *
    * @param aNpaId the new value of the npaId property
    */
   public void setNpaId(String aNpaId)
   {
      npaId = aNpaId;
   }

   /**
    * Access method for the cgbid property.
    *
    * @return   the current value of the cgbid property
    */
   public String getCgbid()
   {
      return cgbid;
   }

   /**
    * Sets the value of the cgbid property.
    *
    * @param aCgbid the new value of the cgbid property
    */
   public void setCgbid(String aCgbid)
   {
      cgbid = aCgbid;
   }

   /**
    * Access method for the reference property.
    *
    * @return   the current value of the reference property
    */
   public String getReference()
   {
      return reference;
   }

   /**
    * Sets the value of the reference property.
    *
    * @param aReference the new value of the reference property
    */
   public void setReference(String aReference)
   {
      reference = aReference;
   }

   /**
    * Access method for the reason property.
    *
    * @return   the current value of the reason property
    */
   public String getNpaReason()
   {
      return npaReason;
   }

   /**
    * Sets the value of the reason property.
    *
    * @param aReason the new value of the reason property
    */
   public void setNpaReason(String aNPAReason)
   {
		npaReason = aNPAReason;
   }




   /**
    * Access method for the detailsOfFinAssistance property.
    *
    * @return   the current value of the detailsOfFinAssistance property
    */
   public String getDetailsOfFinAssistance()
   {
      return detailsOfFinAssistance;
   }

   /**
    * Sets the value of the detailsOfFinAssistance property.
    *
    * @param aDetailsOfFinAssistance the new value of the detailsOfFinAssistance property
    */
   public void setDetailsOfFinAssistance(String aDetailsOfFinAssistance)
   {
      detailsOfFinAssistance = aDetailsOfFinAssistance;
   }

   /**
    * Access method for the creditSupport property.
    *
    * @return   the current value of the creditSupport property
    */
   public String getCreditSupport()
   {
      return creditSupport;
   }

   /**
    * Sets the value of the creditSupport property.
    *
    * @param aCreditSupport the new value of the creditSupport property
    */
   public void setCreditSupport(String aCreditSupport)
   {
      creditSupport = aCreditSupport;
   }

   /**
    * Access method for the bankFacilityDetail property.
    *
    * @return   the current value of the bankFacilityDetail property
    */
   public String getBankFacilityDetail()
   {
      return bankFacilityDetail;
   }

   /**
    * Sets the value of the bankFacilityDetail property.
    *
    * @param aBankFacilityDetail the new value of the bankFacilityDetail property
    */
   public void setBankFacilityDetail(String aBankFacilityDetail)
   {
      bankFacilityDetail = aBankFacilityDetail;
   }


   /**
    * Access method for the placeUnderWatchList property.
    *
    * @return   the current value of the placeUnderWatchList property
    */
   public String getPlaceUnderWatchList()
   {
      return placeUnderWatchList;
   }

   /**
    * Sets the value of the placeUnderWatchList property.
    *
    * @param aPlaceUnderWatchList the new value of the placeUnderWatchList property
    */
   public void setPlaceUnderWatchList(String aPlaceUnderWatchList)
   {
      placeUnderWatchList = aPlaceUnderWatchList;
   }



   /**
    * Access method for the NPADate property.
    *
    * @return   the current value of the NPADate property
    */
   public Date getNpaDate()
   {
      return npaDate;
   }

   /**
    * Sets the value of the NPADate property.
    *
    * @param aNPADate the new value of the NPADate property
    */
   public void setNpaDate(Date aNPADate)
   {
      npaDate = aNPADate;
   }


   /**
    * Access method for the WillfulDefaulter property.
    *
    * @return   the current value of the WillfulDefaulter property
    */
   public String getWillfulDefaulter()
   {
      return willfulDefaulter;
   }

   /**
    * Sets the value of the WillfulDefaulter property.
    *
    * @param aWillfulDefaulter the new value of the WillfulDefaulter property
    */
   public void setWillfulDefaulter(String aWillfulDefaulter)
   {
      willfulDefaulter = aWillfulDefaulter;
   }

   /**
    * Access method for the EffortsTaken property.
    *
    * @return   the current value of the EffortsTaken property
    */
   public String getEffortsTaken()
   {
      return effortsTaken;
   }

   /**
    * Sets the value of the EffortsTaken property.
    *
    * @param aEffortsTaken the new value of the EffortsTaken property
    */
   public void setEffortsTaken(String aEffortsTaken)
   {
      effortsTaken = aEffortsTaken;
   }
   /**
	   * Access method for the remarksOnNpa property.
	   *
	   * @return   the current value of the remarksOnNpa property
	   */
	  public String getRemarksOnNpa()
	  {
		 return remarksOnNpa;
	  }

	  /**
	   * Sets the value of the remarksOnNpa property.
	   *
	   * @param aRemarksOnNpa the new value of the remarksOnNpa property
	   */
	  public void setRemarksOnNpa(String aRemarksOnNpa)
	  {
		 remarksOnNpa = aRemarksOnNpa;
	  }


/**
 * @return
 */
public Date getEffortsConclusionDate() {
	return effortsConclusionDate;
}

/**
 * @return
 */
public String getMliCommentOnFinPosition() {
	return mliCommentOnFinPosition;
}

/**
 * @return
 */
public double getOsAmtOnNPA() {
	return osAmtOnNPA;
}



/**
 * @return
 */
public Date getReportingDate() {
	return reportingDate;
}



/**
 * @return
 */
public String getWhetherNPAReported() {
	return whetherNPAReported;
}



/**
 * @param date
 */
public void setEffortsConclusionDate(Date aEffortsConclusionDate) {
	effortsConclusionDate = aEffortsConclusionDate;
}



/**
 * @param string
 */
public void setMliCommentOnFinPosition(String aMliCommentOnFinPosition) {
	mliCommentOnFinPosition = aMliCommentOnFinPosition;
}

/**
 * @param d
 */
public void setOsAmtOnNPA(double aOsAmtOnNPA) {
	osAmtOnNPA = aOsAmtOnNPA;
}


/**
 * @param date
 */
public void setReportingDate(Date aReportingDate) {
	reportingDate = aReportingDate;
}


/**
 * @param string
 */
public void setWhetherNPAReported(String aWhetherNPAReported) {
	whetherNPAReported = aWhetherNPAReported;
}

/**
 * @return
 */
public String getIsRecoveryInitiated() {
	return isRecoveryInitiated;
}

/**
 * @param string
 */
public void setIsRecoveryInitiated(String aIsRecoveryInitiated) {
	isRecoveryInitiated = aIsRecoveryInitiated;
}

public LegalSuitDetail getLegalSuitDetail()
	{
		return legalSuitDetail;
	}

public void setLegalSuitDetail(LegalSuitDetail aLegalSuitDetail)
	{
		legalSuitDetail = aLegalSuitDetail;
	}

public ArrayList getRecoveryProcedure()
	{
		return recoveryProcedure;
	}

public void setRecoveryProcedure(ArrayList aRecoveryProcedure)
	{
		recoveryProcedure = aRecoveryProcedure;
	}

/**
 * @return
 */
public int getNoOfActions() {
	return noOfActions;
}

/**
 * @param i
 */
public void setNoOfActions(int i) {
	noOfActions = i;
}

 public java.util.Date getDtOfConclusionOfRecProc()
 {
	 return this.dtOfConclusionOfRecProc;
 }

 public void setDtOfConclusionOfRecProc(java.util.Date aDate)
 {
	 this.dtOfConclusionOfRecProc = aDate;
 }

 public String getWhetherWrittenOff()
 {
	 return this.whetherWrittenOff;
 }

 public void setWhetherWrittenOff(String flag)
 {
	 this.whetherWrittenOff = flag;
 }

 public java.util.Date getDtOnWhichAccntWrittenOff()
 {
	 return this.dtOnWhichAccntWrittenOff;
 }

 public void setDtOnWhichAccntWrittenOff(java.util.Date aDate)
 {
	 this.dtOnWhichAccntWrittenOff = aDate;
 }

 public String getCgtsiReportingMode()
 {
	 return this.cgtsiReportingMode;
 }

 public void setCgtsiReportingMode(String flag)
 {
	 this.cgtsiReportingMode = flag;
 }
 
 
// Added by upchar@path on 20-03-2013
    private String isAsPerRBI;
    private String npaConfirm;
    private String npareason;
    private String effortstaken;
    private String isAcctReconstructed;
    private String subsidyFlag;
    private String isSubsidyRcvd;
    private String isSubsidyAdjusted;
    private double subsidyLastRcvdAmt;
    private Date subsidyLastRcvdDt;
    private Date lastInspectionDt;
    private Date npaCreatedDate;

    public void setIsAsPerRBI(String isAsPerRBI) {
        this.isAsPerRBI = isAsPerRBI;
    }

    public String getIsAsPerRBI() {
        return isAsPerRBI;
    }

    public void setNpaConfirm(String npaConfirm) {
        this.npaConfirm = npaConfirm;
    }

    public String getNpaConfirm() {
        return npaConfirm;
    }

    public void setNpareason(String npareason) {
        this.npareason = npareason;
    }

    public String getNpareason() {
        return npareason;
    }

    public void setEffortstaken(String effortstaken) {
        this.effortstaken = effortstaken;
    }

    public String getEffortstaken() {
        return effortstaken;
    }

    public void setIsAcctReconstructed(String isAcctReconstructed) {
        this.isAcctReconstructed = isAcctReconstructed;
    }

    public String getIsAcctReconstructed() {
        return isAcctReconstructed;
    }

    public void setSubsidyFlag(String subsidyFlag) {
        this.subsidyFlag = subsidyFlag;
    }

    public String getSubsidyFlag() {
        return subsidyFlag;
    }

    public void setIsSubsidyRcvd(String isSubsidyRcvd) {
        this.isSubsidyRcvd = isSubsidyRcvd;
    }

    public String getIsSubsidyRcvd() {
        return isSubsidyRcvd;
    }

    public void setIsSubsidyAdjusted(String isSubsidyAdjusted) {
        this.isSubsidyAdjusted = isSubsidyAdjusted;
    }

    public String getIsSubsidyAdjusted() {
        return isSubsidyAdjusted;
    }

    public void setSubsidyLastRcvdAmt(double subsidyLastRcvdAmt) {
        this.subsidyLastRcvdAmt = subsidyLastRcvdAmt;
    }

    public double getSubsidyLastRcvdAmt() {
        return subsidyLastRcvdAmt;
    }

    public void setSubsidyLastRcvdDt(Date subsidyLastRcvdDt) {
        this.subsidyLastRcvdDt = subsidyLastRcvdDt;
    }

    public Date getSubsidyLastRcvdDt() {
        return subsidyLastRcvdDt;
    }

    public void setLastInspectionDt(Date lastInspectionDt) {
        this.lastInspectionDt = lastInspectionDt;
    }

    public Date getLastInspectionDt() {
        return lastInspectionDt;
    }

    public void setNpaCreatedDate(Date npaCreatedDate) {
        this.npaCreatedDate = npaCreatedDate;
    }

    public Date getNpaCreatedDate() {
        return npaCreatedDate;
    }
}
