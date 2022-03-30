package com.cgtsi.actionform;

import java.util.ArrayList;
import java.util.Date;

import java.util.Map;

import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorActionForm;

public class TestDyna extends DynaValidatorActionForm {
    public TestDyna() {
    }
    
    
    private String cgpan1;
    private Date guarStartDt1;
    private Date sanctionDt1;
    private Date firstDisbDt1;
    private Date lastDisbDt1;
    private Date firstInstDt1;
    private double totalDisbAmt1;
    private int moratoriumPrincipal1;
    private int moratoriumInterest1;
    private double approvedAmount1;
    private double repayPrincipal1;
    private double repayInterest1;
    private double outstandingPrincipal1;
    private double outstandingInterest1;
    private double interestRate1;
    
    private String cgpan2;
    private Date guarStartDt2;
    private Date sanctionDt2;
    private Date firstDisbDt2;
    private Date lastDisbDt2;
    private Date firstInstDt2;
    private double totalDisbAmt2;
    private int moratoriumPrincipal2;
    private int moratoriumInterest2;
    private double approvedAmount2;
    private double repayPrincipal2;
    private double repayInterest2;
    private double outstandingPrincipal2;
    private double outstandingInterest2;
    private double interestRate2;
    
    private String cgpan3;
    private Date guarStartDt3;
    private Date sanctionDt3;
    private Date firstDisbDt3;
    private Date lastDisbDt3;
    private Date firstInstDt3;
    private double totalDisbAmt3;
    private int moratoriumPrincipal3;
    private int moratoriumInterest3;
    private double approvedAmount3;
    private double repayPrincipal3;
    private double repayInterest3;
    private double outstandingPrincipal3;
    private double outstandingInterest3;
    private double interestRate3;
    
    private String cgpan4;
    private Date guarStartDt4;
    private Date sanctionDt4;
    private Date firstDisbDt4;
    private Date lastDisbDt4;
    private Date firstInstDt4;
    private double totalDisbAmt4;
    private int moratoriumPrincipal4;
    private int moratoriumInterest4;
    private double approvedAmount4;
    private double repayPrincipal4;
    private double repayInterest4;
    private double outstandingPrincipal4;
    private double outstandingInterest4;
    private double interestRate4;
    
    private String cgpan5;
    private Date guarStartDt5;
    private Date sanctionDt5;
    private Date firstDisbDt5;
    private Date lastDisbDt5;
    private Date firstInstDt5;
    private double totalDisbAmt5;
    private int moratoriumPrincipal5;
    private int moratoriumInterest5;
    private double approvedAmount5;
    private double repayPrincipal5;
    private double repayInterest5;
    private double outstandingPrincipal5;
    private double outstandingInterest5;
    private double interestRate5;
    
    private Map securityAsOnSancDt;
    private Map securityAsOnNpaDt;
    private double networthAsOnSancDt;
    private double networthAsOnNpaDt;
    private String reasonForReductionAsOnSancDt;
    private String reasonForReductionAsOnNpaDt;
    private Vector cgpansVector;
    
    private String memberId;
    private String borrowerId;
    private String borrowerName;
    private String cgpan;
    private ArrayList borrowerIds;
    private String unitName;
    private String operationType;
    private int size;
    private double totalApprovedAmount;
    private double totalSecurityAsOnSanc;
    private double totalSecurityAsOnNpa;
    
    private String npaId;
    private Date npaDt = null;
    private String isAsPerRBI;
    private String npaConfirm;
    private String npaReason;
    private String effortsTaken;
    private String isAcctReconstructed;
    private String subsidyFlag;
    private String isSubsidyRcvd;
    private String isSubsidyAdjusted;
    private double subsidyLastRcvdAmt;
    private Date subsidyLastRcvdDt = null;
    private Date lastInspectionDt = null;
    
    public void resetDyna(ActionMapping mapping,HttpServletRequest request){
        npaId = "";
        npaDt = null;
        isAsPerRBI = "";
        npaConfirm = "";
        npaConfirm = "";
        npaConfirm = "";
        npaConfirm = "";
        npaReason = "";
        effortsTaken = "";
        isAcctReconstructed = "";
        subsidyFlag = "";
        isSubsidyRcvd = "";
        isSubsidyAdjusted = "";
        subsidyLastRcvdAmt = 0;
        subsidyLastRcvdDt = null;
        lastInspectionDt = null;
    }
}
