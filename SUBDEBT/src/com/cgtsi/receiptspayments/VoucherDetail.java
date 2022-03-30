 // Decompiled Using: FrontEnd Plus v2.03 and the JAD Engine
 // Available From: http://www.reflections.ath.cx
 // Decompiler options: packimports(3) 
 // Source File Name:   VoucherDetail.java

 package com.cgtsi.receiptspayments;

 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Date;

 public class VoucherDetail
     implements Serializable
 {

     public VoucherDetail()
     {
         voucherType = null;
     }

     public double getAmount()
     {
         return amount;
     }

     public String getAmountInFigure()
     {
         return amountInFigure;
     }

     public String getBankGLCode()
     {
         return bankGLCode;
     }

     public String getBankGLName()
     {
         return bankGLName;
     }

     public String getDeptCode()
     {
         return deptCode;
     }

     public String getNarration()
     {
         return narration;
     }

     public void setAmount(double d)
     {
         amount = d;
     }

     public void setAmountInFigure(String string)
     {
         amountInFigure = string;
     }

     public void setBankGLCode(String string)
     {
         bankGLCode = string;
     }

     public void setBankGLName(String string)
     {
         bankGLName = string;
     }

     public void setDeptCode(String string)
     {
         deptCode = string;
     }

     public void setNarration(String string)
     {
         narration = string;
     }

     public ArrayList getVouchers()
     {
         return vouchers;
     }

     public void setVouchers(ArrayList list)
     {
         vouchers = list;
     }

     public String getVoucherType()
     {
         return voucherType;
     }

     public void setVoucherType(String string)
     {
         voucherType = string;
     }

     public String getAsstManager()
     {
         return asstManager;
     }

     public String getManager()
     {
         return manager;
     }

     public void setAsstManager(String string)
     {
         asstManager = string;
     }

     public void setManager(String string)
     {
         manager = string;
     }

     public Date getPaymentDt()
     {
         return paymentDt;
     }

     public void setPaymentDt(Date payDate)
     {
         paymentDt = payDate;
     }

     private String bankGLCode;
     private String bankGLName;
     private String deptCode;
     private double amount;
     private String amountInFigure;
     private String narration;
     private String manager;
     private String asstManager;
     private Date paymentDt;
     private String voucherType;
     private ArrayList vouchers;
 }
