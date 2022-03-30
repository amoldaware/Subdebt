 // Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
 // Available From: http://www.reflections.ath.cx
 // Decompiler options: packimports(3) 
 // Source File Name:   Voucher.java

 package com.cgtsi.receiptspayments;

 import java.io.Serializable;

 public class Voucher
     implements Serializable
 {

     public Voucher()
     {
     }

     public String getAcCode()
     {
         return acCode;
     }

     public String getAdvDate()
     {
         return advDate;
     }

     public String getAdvNo()
     {
         return advNo;
     }

     public String getDebitOrCredit()
     {
         return debitOrCredit;
     }

     public String getPaidTo()
     {
         return paidTo;
     }

     public void setAcCode(String string)
     {
         acCode = string;
     }

     public void setAdvDate(String string)
     {
         advDate = string;
     }

     public void setAdvNo(String string)
     {
         advNo = string;
     }

     public void setAmountInRs(String string)
     {
         amountInRs = string;
     }

     public void setDebitOrCredit(String string)
     {
         debitOrCredit = string;
     }

     public void setPaidTo(String string)
     {
         paidTo = string;
     }

     public String getInstrumentDate()
     {
         return instrumentDate;
     }

     public String getInstrumentNo()
     {
         return instrumentNo;
     }

     public String getInstrumentType()
     {
         return instrumentType;
     }

     public void setInstrumentDate(String string)
     {
         instrumentDate = string;
     }

     public void setInstrumentNo(String string)
     {
         instrumentNo = string;
     }

     public void setInstrumentType(String string)
     {
         instrumentType = string;
     }

     public String getAmountInRs()
     {
         return amountInRs;
     }

     private String acCode;
     private String paidTo;
     private String amountInRs;
     private String debitOrCredit;
     private String advNo;
     private String advDate;
     private String instrumentType;
     private String instrumentDate;
     private String instrumentNo;
 }
