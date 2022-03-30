//Source file: D:\\com\\cgtsi\\receiptspayments\\PaymentList.java

package com.cgtsi.receiptspayments;
import java.util.Date;

//Two parameters paymentId, instrumentNo and one constructer PaymentList(String memberId, String memberName, String paymentId, String instrumentNo, Date instrumentDt) added by sukant PathInfotech for paymentMadeSummery
//On 17/01/2007

public class PaymentList 
{
   private String memberID;
   private String memberName;
   private String paymentId;
   private Date instrumentDt;
   private String instrumentNo;
   private int payAmount;
   private int allocatedAmt;
   private int allocatedCases;
   private int inwardAmount;
   private String inwardId;
   
   /**
    * @roseuid 39B9CCDB0119
    */
   public PaymentList() 
   {
    
   }

/**
   * 
   * @param inwardId
   * @param memberName
   * @param instrumentDt
   * @param instrumentNo
   * @param inwardAmount
   */
  public PaymentList(String inwardId, String memberName, Date instrumentDt,String instrumentNo,int inwardAmount ) 
   {
   		this.inwardId = inwardId ;
   		this.memberName = memberName ;
   		this.instrumentDt = instrumentDt ;
      this.instrumentNo = instrumentNo;
      this.inwardAmount = inwardAmount;
      
   }

   /**
	* @roseuid 39B9CCDB0119
	*/
   public PaymentList(String memberId, String memberName, String paymentId) 
   {
   		this.memberID = memberId ;
   		this.memberName = memberName ;
   		this.paymentId = paymentId ;
      
   }
   /**
	* @roseuid 39B9CCDB0119
	*/
   public PaymentList(String memberId, String memberName, String paymentId, String instrumentNo, Date instrumentDt) 
   {
   		this.memberID = memberId ;
   		this.memberName = memberName ;
   		this.paymentId = paymentId ;
      this.instrumentDt=instrumentDt;
      this.instrumentNo=instrumentNo;
   }

    /**
	* @roseuid 39B9CCDB0119
	*/
   public PaymentList(String memberId, String memberName, String paymentId, String instrumentNo, Date instrumentDt,int payAmount) 
   {
   		this.memberID = memberId ;
   		this.memberName = memberName ;
   		this.paymentId = paymentId ;
      this.instrumentDt=instrumentDt;
      this.instrumentNo=instrumentNo;
      this.payAmount = payAmount;
   }
   /**
   * 
   * @author sukumar@path for batch appropriation for GF
   * @param memberId
   * @param memberName
   * @param paymentId
   * @param instrumentNo
   * @param instrumentDt
   * @param payAmount
   * @param inwardAmount
   */
   public PaymentList(String memberId, String memberName, String paymentId, String instrumentNo, Date instrumentDt,int payAmount,int inwardAmount) 
   {
   		this.memberID = memberId ;
   		this.memberName = memberName ;
   		this.paymentId = paymentId ;
      this.instrumentDt=instrumentDt;
      this.instrumentNo=instrumentNo;
      this.payAmount = payAmount;
      this.inwardAmount=inwardAmount;
   }
   /**
   * added by sukumar@pth for display Inward Amount in Batch Appropriation for GF
   * @param memberId
   * @param memberName
   * @param paymentId
   * @param instrumentNo
   * @param instrumentDt
   * @param payAmount
   * @param allocatedAmt
   * @param allocatedCases
   * @param inwardAmount
   */
   public PaymentList(String memberId, String memberName, String paymentId, String instrumentNo, Date instrumentDt,int payAmount,int allocatedAmt,int allocatedCases,int inwardAmount) 
   {
   		this.memberID = memberId ;
   		this.memberName = memberName ;
   		this.paymentId = paymentId ;
      this.instrumentDt=instrumentDt;
      this.instrumentNo=instrumentNo;
      this.payAmount = payAmount;
      this.allocatedAmt = allocatedAmt;
      this.allocatedCases = allocatedCases;
      this.inwardAmount=inwardAmount;
   }
   /**
   * added this method by sukumar@pathinfotech for providing Instrument Amount,Allocated Amount and Allocated Cases in Appropriation Screen
   * @param memberId
   * @param memberName
   * @param paymentId
   * @param instrumentNo
   * @param instrumentDt
   * @param payAmount
   * @param allocatedAmt
   * @param allocatedCases
   */
    public PaymentList(String memberId, String memberName, String paymentId, String instrumentNo, Date instrumentDt,int payAmount,int allocatedAmt,int allocatedCases) 
   {
   		this.memberID = memberId ;
   		this.memberName = memberName ;
   		this.paymentId = paymentId ;
      this.instrumentDt=instrumentDt;
      this.instrumentNo=instrumentNo;
      this.payAmount = payAmount;
      this.allocatedAmt = allocatedAmt;
      this.allocatedCases = allocatedCases;
      
   }
   
   
   /* added by sukumar@path for providing Pay Amount In Appropriation Screen */
     /**
    * return payAmount
    */
   public int getPayAmount()
   {
    return this.payAmount;
   }
   /**
   * 
   * @param payAmount
   */
   public void setPayAmount(int payAmount)
   {
    this.payAmount = payAmount;
   }
   /**
    * Access method for the memberID property.
    * 
    * @return   the current value of the memberID property
    */
   public String getMemberID() 
   {
      return memberID;
   }
   
   /**
    * Sets the value of the memberID property.
    * 
    * @param aMemberID the new value of the memberID property
    */
   public void setMemberID(String aMemberID) 
   {
      memberID = aMemberID;
   }
   
   /**
    * Access method for the memberName property.
    * 
    * @return   the current value of the memberName property
    */
   public String getMemberName() 
   {
      return memberName;
   }
   
   /**
    * Sets the value of the memberName property.
    * 
    * @param aMemberName the new value of the memberName property
    */
   public void setMemberName(String aMemberName) 
   {
      memberName = aMemberName;
   }
   
   /**
    * Access method for the paymentId property.
    * 
    * @return   the current value of the paymentId property
    */
   public String getPaymentId() 
   {
      return paymentId;
   }
   
   /**
    * Sets the value of the paymentId property.
    * 
    * @param aPaymentId the new value of the paymentId property
    */
   public void setPaymentId(String aPaymentId) 
   {
      paymentId = aPaymentId;
   }


  public void setInstrumentDt(Date date)
  {
    this.instrumentDt = instrumentDt;
  }


  public Date getInstrumentDt()
  {
    return instrumentDt;
  }


  public void setInstrumentNo(String instrumentNo)
  {
    this.instrumentNo = instrumentNo;
  }


  public String getInstrumentNo()
  {
    return instrumentNo;
  }
  /**
   * 
   * @param allocatedAmt
   */
  public void setAllocatedAmt(int allocatedAmt)
  {
   this.allocatedAmt = allocatedAmt;
  }
  /**
   * 
   * @return allocatedAmt
   */
  public int getAllocatedAmt()
  {
   return this.allocatedAmt;
  }
  /**
   * 
   * @param allocatedCases
   */
  public void setAllocatedCases(int allocatedCases)
  {
   this.allocatedCases = allocatedCases;
  }
  /**
   * 
   * @return allocatedCases
   */
  public int getAllocatedCases()
  {
   return this.allocatedCases;
  }
  /**
   * 
   * @param inwardAmount
   */
  public void setInwardAmount(int inwardAmount)
  {
   this.inwardAmount=inwardAmount;
  }
  /**
   * 
   * @return inwardAmount
   */
  public int getInwardAmount()
  {
   return this.inwardAmount;
  }
  /**
   * 
   * @param inwardId
   */
  public void setInwardId(String inwardId)
  {
   this.inwardId =inwardId;
  }
  /**
   * 
   * @return inwardId
   */
  public String getInwardId()
  {
   return this.inwardId;
  }
}
