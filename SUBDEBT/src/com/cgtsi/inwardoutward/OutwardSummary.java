
package com.cgtsi.inwardoutward;


public class OutwardSummary
{
private String outwardId;	 
private  String documentType;
private String processedBy;
//private Date time
private java.util.ArrayList mappedInwardID;


  public OutwardSummary()
   {

   }


    public java.lang.String getOutwardId()
   {
      return outwardId;
   }
   /**
    * Sets the value of the inwardId property.
    *
    * @param aInwardId the new value of the inwardId property
    */
   public void setOutwardId(java.lang.String aOutwardId)
   {
      outwardId = aOutwardId;
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

 public java.util.ArrayList getMappedInwardID()
   {
      return mappedInwardID;
   }
   /**
    * Sets the value of the outwardId property.
    *
    * @param aOutwardId the new value of the inwardId property
    */
   public void setMappedInwardID(java.util.ArrayList aMappedInwardID)
   {
      mappedInwardID = aMappedInwardID;
   }




}



