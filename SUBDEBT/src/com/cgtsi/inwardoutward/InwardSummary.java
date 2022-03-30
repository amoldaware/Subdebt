
package com.cgtsi.inwardoutward;


public class InwardSummary
{
private String inwardId;	 
private  String documentType;
private String processedBy;
//private Date time
private java.util.ArrayList mappedOutwardID;


  public InwardSummary()
   {

   }


    public java.lang.String getInwardId()
   {
      return inwardId;
   }
   /**
    * Sets the value of the inwardId property.
    *
    * @param aInwardId the new value of the inwardId property
    */
   public void setInwardId(java.lang.String aInwardId)
   {
      inwardId = aInwardId;
   }



    public java.lang.String getDocumentType()
   {
      return documentType;
   }
   /**
    * Sets the value of the documentType property.
    *
    * @param aDocumentType the new value of the documentType property
    */
   public void setDocumentType(java.lang.String aDocumentType)
   {
      documentType = aDocumentType;
   }



    public java.lang.String getProcessedBy()
   {
      return processedBy;
   }
   /**
    * Sets the value of the processedBy property.
    *
    * @param aProcessedBy the new value of the processedBy property
    */
   public void setProcessedBy(java.lang.String aProcessedBy)
   {
      processedBy = aProcessedBy;
   }

 public java.util.ArrayList getMappedOutwardID()
   {
      return mappedOutwardID;
   }
   /**
    * Sets the value of the outwardId property.
    *
    * @param aOutwardId the new value of the inwardId property
    */
   public void setMappedOutwardID(java.util.ArrayList aMappedOutwardID)
   {
      mappedOutwardID = aMappedOutwardID;
   }

}



