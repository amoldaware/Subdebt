<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>
<%@ page import="com.cgtsi.claim.ClaimDetail"%>
<%@ page import = "com.cgtsi.claim.ClaimSummaryDtls"%>
<%@ page import = "com.cgtsi.claim.RecoveryDetails"%>
<%@ page import = "com.cgtsi.action.BaseAction"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("#########0");%>
<% session.setAttribute("CurrentPage","displayClaimRefNumberDtlsMod.do?method=displayClaimRefNumberDtlsMod");%>
<%
      ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm") ;
      String falgforCasesafet=claimForm.getFalgforCasesafet();
 
 %>
<script>
function calltotalServiceFee()
{
//alert("calltotalServiceFee entered");
var asfDeductableforTC=document.getElementById('asfDeductableforTC').innerHTML;;
//alert(asfDeductableforTC);
var asfDeductableforWC=document.getElementById('asfDeductableforWC').innerHTML;;
//alert(asfDeductableforWC);
//alert("calltotalServiceFee exited");
}

function calculatePsTotal()
{
//alert("calculating nggng");
  var tempservicefee=0;
  var asfDeductableforTC=0;
  var asfDeductableforWC=0;
	asfDeductableforTC=findObj("asfDeductableforTC");	
  
	if(asfDeductableforTC!=null && asfDeductableforTC!="")
	{
	var tcsFee=asfDeductableforTC.value;
 // alert(tcsFee);
	}
	if (!(isNaN(tcsFee)) && tcsFee!="")
	{
		tempservicefee+=parseFloat(tcsFee);	
	}

	asfDeductableforWC=findObj("asfDeductableforWC");	
	if(asfDeductableforWC!=null && asfDeductableforWC!="")
	{
	var wcSfee=asfDeductableforWC.value;
 // alert(wcSfee);
	}
	if (!(isNaN(wcSfee)) && wcSfee!="")
	{
		tempservicefee+=parseFloat(wcSfee);	
	}
  var servicefee=document.getElementById('servicefee');
	//servicefee.innerHTML=tempservicefee; 
	document.forms[0].servicefee.value = tempservicefee;
  //alert(servicefee.value);
}

function calculateOsTotal()
{
	var tempOsAmt=0;
  var tcOsAmt=0;
  var wcOsAmt=0;
	tcOsAmt=findObj("claimdetail.totalTCOSAmountAsOnNPA");	  
	if(tcOsAmt!=null && tcOsAmt!="")
	{
	var tcOutstanding=tcOsAmt.value;
 // alert(tcOutstanding);
	}
	if (!(isNaN(tcOutstanding)) && tcOutstanding!="")
	{
		tempOsAmt+=parseFloat(tcOutstanding);	
	}
	wcOsAmt=findObj("claimdetail.totalWCOSAmountAsOnNPA");	
	if(wcOsAmt!=null && wcOsAmt!="")
	{
	var wcOutstanding=wcOsAmt.value;
//  alert(wcOutstanding);
	}
	if (!(isNaN(wcOutstanding)) && wcOutstanding!="")
	{
		tempOsAmt+=parseFloat(wcOutstanding);	
	}
  var totalOsAmntAsOnNPA=document.getElementById('totalOsAmntAsOnNPA');
	totalOsAmntAsOnNPA.innerHTML=tempOsAmt; 
 // alert(totalOsAmntAsOnNPA.value);
}

function calculateRecoveryTotal()
{
	var tempRecovery=0;
  var tcRecoveryMade=0;
  var wcRecoveryMade=0;
	tcRecoveryMade=findObj("tcrecovery");	
  
	if(tcRecoveryMade!=null && tcRecoveryMade!="")
	{
	var tcrecovery=tcRecoveryMade.value;
//  alert(tcrecovery);
	}
	if (!(isNaN(tcrecovery)) && tcrecovery!="")
	{
		tempRecovery+=parseFloat(tcrecovery);	
	}

	wcRecoveryMade=findObj("wcrecovery");	
	if(wcRecoveryMade!=null && wcRecoveryMade!="")
	{
	var wcrecovery=wcRecoveryMade.value;
 // alert(wcrecovery);
	}
	if (!(isNaN(wcrecovery)) && wcrecovery!="")
	{
		tempRecovery+=parseFloat(wcrecovery);	
	}
  var totalRecovery=document.getElementById('totalRecovery');
  
	totalRecovery.innerHTML=Math.round(tempRecovery); 
  //alert(servicefee.value);
}

function calltotalAmt(tci,wci)
{
//alert("calltotalAmt entered");
//alert(tci);
//alert(wci);
var totalTCOSAmountAsOnNPA=document.getElementById('claimdetail.totalTCOSAmountAsOnNPA');
var totalWCOSAmountAsOnNPA=document.getElementById('claimdetail.totalWCOSAmountAsOnNPA');

var tcrecovery=document.getElementById('tcrecovery');
var wcrecovery=document.getElementById('wcrecovery');

var tcNetOutstanding = document.getElementById('tcNetOutstanding');
var wcNetOutstanding = document.getElementById('wcNetOutstanding');

var totalNetOutstanding = document.getElementById('totalNetOutstanding');

var tcClaimEligibleAmt = document.getElementById('tcClaimEligibleAmt');
var wcClaimEligibleAmt = document.getElementById('wcClaimEligibleAmt');
var totalClaimEligibleAmt = document.getElementById('totalClaimEligibleAmt');

//var tcFirstInstallment = document.getElementById('tcFirstInstallment');
var tcFirstInstallment = document.forms[0].tcFirstInstallment;
//var wcFirstInstallment = document.getElementById('wcFirstInstallment');
var wcFirstInstallment = document.forms[0].wcFirstInstallment;
var totalFirstInstalment = document.getElementById('totalFirstInstalment');

var testFirstInstalment = document.getElementById('testFirstInstalment');

var asfDeductableforTC = document.getElementById('asfDeductableforTC');
var asfDeductableforWC = document.getElementById('asfDeductableforWC');

var totalAmntPayableNow = document.getElementById('totalAmntPayableNow');

var tctempvalue = 0;
var wctempvalue = 0;
var totaltempvalue=0;

var tcclaimeligble = 0;
var wcclaimeligible = 0;
var totalclaimeligible = 0;

var tcFirstInst = 0;
var wcFirstInst = 0;
var totalFirstInst = 0;

var testFirstInst = 0;


var totalAsfdeducatble = 0;
var totalAmtPayable = 0;
var tcasf = 0;
var wcasf = 0;

/*alert(totalTCOSAmountAsOnNPA.value);
alert(totalWCOSAmountAsOnNPA.value);
alert(tcrecovery.value);
alert(wcrecovery.value);
alert(totalTCOSAmountAsOnNPA.value-tcrecovery.value);
alert(totalWCOSAmountAsOnNPA.value-wcrecovery.value);
*/
tctempvalue = Math.min(tci,totalTCOSAmountAsOnNPA.value) - tcrecovery.value;
//alert("tctempvalue:"+tctempvalue);
if(tctempvalue < 0 ){
 tctempvalue=0;
 //alert("tctempvalue:"+tctempvalue);
}
wctempvalue = Math.min(wci,totalWCOSAmountAsOnNPA.value) - wcrecovery.value;
//alert("wctempvalue:"+wctempvalue);
if(wctempvalue < 0){
 wctempvalue = 0;
// alert("wctempvalue:"+wctempvalue);
}
totaltempvalue = tctempvalue+wctempvalue;
//alert("totaltempvalue:"+totaltempvalue);

//alert(tctempvalue);
//alert(wctempvalue);
//alert(totaltempvalue);
if(tci+wci<=500000){
tcclaimeligble = Math.round(tctempvalue * 0.80);
}else{
tcclaimeligble = Math.round(tctempvalue * 0.75);
}
//alert(tcclaimeligble);
if(tci+wci<=500000){
wcclaimeligible = Math.round(wctempvalue * 0.80);
}
else{
wcclaimeligible = Math.round(wctempvalue * 0.75);
}
//alert(wcclaimeligible);
totalclaimeligible = tcclaimeligble+wcclaimeligible;
//alert(tcclaimeligble);
//alert(wcclaimeligible);
//alert(totalclaimeligible);
tcFirstInst = Math.round(tcclaimeligble * 0.75);
wcFirstInst = Math.round(wcclaimeligible * 0.75);
totalFirstInst =  tcFirstInst+wcFirstInst;

tcasf = asfDeductableforTC.value;
//alert(tcasf);
wcasf = asfDeductableforWC.value;
//alert(wcasf);
totalAsfdeducatble = tcasf+wcasf;
//alert(totalAsfdeducatble);
//alert(tcFirstInst+wcFirstInst-tcasf-wcasf);
totalAmtPayable = tcFirstInst+wcFirstInst-tcasf-wcasf;
//testFirstInst = tcFirstInst+wcFirstInst;
//alert("testFirstInst:"+testFirstInst);
tcNetOutstanding.innerHTML=tctempvalue; 
wcNetOutstanding.innerHTML=wctempvalue;
totalNetOutstanding.innerHTML=totaltempvalue;
tcClaimEligibleAmt.innerHTML=tcclaimeligble;
wcClaimEligibleAmt.innerHTML=wcclaimeligible;
totalClaimEligibleAmt.innerHTML=totalclaimeligible;
//tcFirstInstallment.innerHTML=tcFirstInst;
document.forms[0].tcFirstInstallment.value = tcFirstInst;
//wcFirstInstallment.innerHTML=wcFirstInst;
document.forms[0].wcFirstInstallment.value = wcFirstInst;
totalFirstInstalment.innerHTML=totalFirstInst;
//document.forms[0].totalFirstInstalment.value = totalFirstInst;
totalAmntPayableNow.innerHTML=totalAmtPayable;
//testFirstInstalment.innerHTML=testFirstInst;
}

function calltotalAmtNew(tci,wci,microFlag,womenOwned,nerFlag,schName)
{

var falgforCasesafet="<%=falgforCasesafet%>";

//alert("calltotalAmtNew entered");
//alert(tci);
//alert(wci);
//alert(microFlag);
var schemeName=schName.value;
var totalTCOSAmountAsOnNPA=document.getElementById('claimdetail.totalTCOSAmountAsOnNPA');
var totalWCOSAmountAsOnNPA=document.getElementById('claimdetail.totalWCOSAmountAsOnNPA');

var tcrecovery=document.getElementById('tcrecovery');
var wcrecovery=document.getElementById('wcrecovery');

var tcNetOutstanding = document.getElementById('tcNetOutstanding');
var wcNetOutstanding = document.getElementById('wcNetOutstanding');
var totalNetOutstanding = document.getElementById('totalNetOutstanding');

var tcClaimEligibleAmt = document.getElementById('tcClaimEligibleAmt');
var wcClaimEligibleAmt = document.getElementById('wcClaimEligibleAmt');
var totalClaimEligibleAmt = document.getElementById('totalClaimEligibleAmt');

var tcFirstInstallment = document.getElementById('tcFirstInstallment');
//var tcFirstInstallment = document.forms[0].tcFirstInstallment;
var wcFirstInstallment = document.getElementById('wcFirstInstallment');
//var wcFirstInstallment = document.forms[0].wcFirstInstallment;
var totalFirstInstalment = document.getElementById('totalFirstInstalment');

//var asfDeductableforTC = document.getElementById('asfDeductableforTC');
//var asfDeductableforWC = document.getElementById('asfDeductableforWC');
var asfDeductableforTC = document.forms[0].asfDeductableforTC;
var asfDeductableforWC = document.forms[0].asfDeductableforWC;

if(asfDeductableforTC==null)
{
asfDeductableforTC=0;
}
if(asfDeductableforWC==null)
{
asfDeductableforWC=0;
}
var totalAmntPayableNow = document.getElementById('totalAmntPayableNow');
var testFirstInstalment = document.getElementById('testFirstInstalment');
var tctempvalue = 0;
var wctempvalue = 0;
var totaltempvalue=0;

var tcclaimeligble = 0;
var wcclaimeligible = 0;
var totalclaimeligible = 0;

var tcFirstInst = 0;
var wcFirstInst = 0;
var totalFirstInst = 0;

var testFirstInst = 0;

var totalAsfdeducatble = 0;
var totalAmtPayable = 0;
var tcasf = 0;
var wcasf = 0;
var rate2=0.75;
var rate3=0.50;
var rate4=0.80;
/*alert(totalTCOSAmountAsOnNPA.value);
alert(totalWCOSAmountAsOnNPA.value);
alert(tcrecovery.value);
alert(wcrecovery.value);
alert(totalTCOSAmountAsOnNPA.value-tcrecovery.value);
alert(totalWCOSAmountAsOnNPA.value-wcrecovery.value);
*/
tctempvalue = Math.min(tci,totalTCOSAmountAsOnNPA.value) - tcrecovery.value;
if(tctempvalue < 0){
tctempvalue = 0;
}
wctempvalue = Math.min(wci,totalWCOSAmountAsOnNPA.value) - wcrecovery.value;

if(wctempvalue < 0){
wctempvalue = 0;
}
totaltempvalue = Math.round(tctempvalue+wctempvalue);


//alert(tctempvalue);
//alert(tctempvalue.toFixed(2));
//alert(wctempvalue);
//alert(totaltempvalue);
if(schemeName=="RSF")
                 {
                 
                 
                tcclaimeligble =  Math.round(tctempvalue * 0.50);
                wcclaimeligible =  Math.round(wctempvalue * 0.50); 
                
                               }
else if((tci+wci<=500000)&&(microFlag=="Y")){

       if (falgforCasesafet=="Y")
          {
          tcclaimeligble = Math.round(tctempvalue * 0.85);
          }
          else 
          {
        tcclaimeligble = Math.round(tctempvalue * 0.80);
          }
          
}
else if((tci+wci<=5000000)&&(womenOwned=="F" || nerFlag=="Y")){
tcclaimeligble = Math.round(tctempvalue * 0.80);
 
}
else if((tci+wci<=500000)&&(microFlag=="N")){
tcclaimeligble = Math.round(tctempvalue * 0.75);
 
}
else 
{
 
tcclaimeligble = Math.round(tctempvalue * 0.75);
}
//alert(tcclaimeligble);

if(schemeName=="RSF")
                 {
           wcclaimeligible =  Math.round(wctempvalue * 0.50); 
                   }
else if((tci+wci<=500000)&&(microFlag=="Y")){

 
 if (falgforCasesafet=="Y")
          {
        
        wcclaimeligible = Math.round(wctempvalue * 0.85);
          }
          else 
          {
    
         wcclaimeligible = Math.round(wctempvalue * 0.80);
          }
} else if((tci+wci<=5000000)&&(womenOwned=="F" || nerFlag=="Y")){
wcclaimeligible = Math.round(wctempvalue * 0.80);

}
else if((tci+wci<=500000)&&(microFlag=="N")){
 

wcclaimeligible = Math.round(wctempvalue * 0.75);
}
else if(((schemeName=="CGFSI")&&(tci+wci) > 5000000&&(nerFlag=="Y"))||((schemeName=="CGFSI")&&(womenOwned=="F")&&(tci+wci) > 5000000&&(nerFlag=="N")))
               {
                    var percentage = ((tci+wci-5000000)/(tci+wci)) ;
             var newnumber = new Number(percentage+'').toFixed(parseInt(9));

	var r1 =  parseFloat(newnumber); 
      
        var percentage1 = ((5000000)/(tci+wci)) ;
             var newnumber1 = new Number(percentage1+'').toFixed(parseInt(9));
	var r2 =  parseFloat(newnumber1); 
           
                   var wcClaimEligible1 =  Math.round(wctempvalue * rate3*(r1));
                   
          var wcClaimEligible2 =  Math.round(wctempvalue * rate4*(r2));
         
          var tcClaimEligible1 =  Math.round(tctempvalue * rate3*(r1));
          var tcClaimEligible2 =  Math.round(tctempvalue * rate4*(r2));
          
           wcclaimeligible=  wcClaimEligible1+wcClaimEligible2;
           tcclaimeligble=  tcClaimEligible1+tcClaimEligible2;
      
                }
               else if((schemeName=="CGFSI")&&(womenOwned="M")&&(tci+wci) > 5000000&&(nerFlag="N"))
               {
            
                     // var newnumber = new Number(number+'').toFixed(parseInt(decimals));
             var percentage = ((tci+wci-5000000)/(tci+wci)) ;
             var newnumber = new Number(percentage+'').toFixed(parseInt(9));

	var r1 =  parseFloat(newnumber); 
      
        var percentage1 = ((5000000)/(tci+wci)) ;
             var newnumber1 = new Number(percentage1+'').toFixed(parseInt(9));
	var r2 =  parseFloat(newnumber1); 
           
                   var wcClaimEligible1 =  Math.round(wctempvalue * rate3*(r1));
                   
          var wcClaimEligible2 =  Math.round(wctempvalue *rate2*(r2));
         
          var tcClaimEligible1 =  Math.round(tctempvalue * rate3*(r1));
          var tcClaimEligible2 =  Math.round(tctempvalue * rate2*(r2));
          
           wcclaimeligible=  wcClaimEligible1+wcClaimEligible2;
           tcclaimeligble=  tcClaimEligible1+tcClaimEligible2;
           
                        /* tcClaimEligible =  Math.round(tctempvalue * 0.80);
                 wcClaimEligible =  Math.round(tctempvalue * 0.80); 
                 alert(tcClaimEligible);*/
                  }
else{
 
wcclaimeligible = Math.round(wctempvalue * 0.75);
}
//alert(wcclaimeligible);


totalclaimeligible = tcclaimeligble+wcclaimeligible;
tcFirstInst = Math.round(tcclaimeligble * 0.75);
wcFirstInst = Math.round(wcclaimeligible * 0.75);
totalFirstInst =  tcFirstInst+wcFirstInst;
testFirstInst = tcFirstInst+wcFirstInst;
tcasf = asfDeductableforTC.value;

wcasf = asfDeductableforWC.value;
//alert(wcasf);
totalAsfdeducatble = parseFloat(tcasf)+parseFloat(wcasf);
//alert(totalAsfdeducatble);

//alert(tcFirstInst+wcFirstInst-tcasf-wcasf);
//totalAmtPayable = tcFirstInst+wcFirstInst-tcasf-wcasf;

var claimProceedingFlag = document.getElementById('proceedingFlag').value;

if(claimProceedingFlag == 'Y'){

	document.forms[0].servicefee.value = totalAsfdeducatble;
	if(document.forms[0].refundFlag.checked){
		totalAmtPayable = document.getElementById('totalAmntPayableNow').innerHTML;
	}else{
		totalAmtPayable = tcFirstInst+wcFirstInst-(totalAsfdeducatble);
	}

	//alert(tctempvalue);
	tcNetOutstanding.innerHTML=tctempvalue; 
	wcNetOutstanding.innerHTML=wctempvalue;
	totalNetOutstanding.innerHTML=totaltempvalue;
	tcClaimEligibleAmt.innerHTML=tcclaimeligble;
	wcClaimEligibleAmt.innerHTML=wcclaimeligible;
	totalClaimEligibleAmt.innerHTML=totalclaimeligible;
	//tcFirstInstallment.innerHTML=tcFirstInst;
	document.forms[0].tcFirstInstallment.value = tcFirstInst;
	//wcFirstInstallment.innerHTML=wcFirstInst;
	document.forms[0].wcFirstInstallment.value = wcFirstInst;
	totalFirstInstalment.innerHTML=totalFirstInst;
	//document.forms[0].totalFirstInstalment.value = totalFirstInst;
	//testFirstInstalment.innerHTML=testFirstInst;
	totalAmntPayableNow.innerHTML=totalAmtPayable;
}else{
	tcNetOutstanding.innerHTML=tctempvalue; 
	wcNetOutstanding.innerHTML=wctempvalue;
	totalNetOutstanding.innerHTML=totaltempvalue;
	tcClaimEligibleAmt.innerHTML=tcclaimeligble;
	wcClaimEligibleAmt.innerHTML=wcclaimeligible;
	totalClaimEligibleAmt.innerHTML=totalclaimeligible;
}

calculateRecoveryTotal();
calculateOsTotal();
}
function enableClaimAmounts()
{
var totalFirstInstalment;
//alert("enableClaimAmounts entered");
if(document.forms[0].isClaimProceedings[0].checked){
// alert("Claim To be Processed");
 calculatePsTotal();
 calltotalAmtNew(tci,wci,microFlag,womenOwned,nerFlag,schemeName);
} else {
   //alert("Claim To be Rejected");   
   document.forms[0].tcFirstInstallment.value=0;
   document.forms[0].wcFirstInstallment.value=0;     
   document.forms[0].totalFirstInstalment.value=0;  
   document.forms[0].testFirstInstalment.value=0;
   document.forms[0].asfDeductableforTC.value=0;
   document.forms[0].asfDeductableforWC.value=0;   
   document.forms[0].servicefee.value=0;
   document.forms[0].totalAmntPayableNow.value=0;
      }
}

function addRefundFee(){
	//var totFirstInst = document.forms[0].totalFirstInstalment.value; 
	var tcFirstInstallment = document.forms[0].tcFirstInstallment.value;
    var wcFirstInstallment = document.forms[0].wcFirstInstallment.value;   
	var totRefundFee = document.forms[0].totalServiceFeeRefund.value;
	//var serviceFee = document.forms[0].servicefee.value;
	//var totFirstInst = document.getElementById('totalFirstInstalment').innerHTML;
	//var totRefundFee = document.getElementById('totalServiceFeeRefund').innerHTML;
	//alert("totFirstInst"+totFirstInst);
	//alert("totRefundFee"+totRefundFee);
	//var amountPayable = document.forms[0].totalAmntPayableNow.value;
	//document.forms[0].totalAmntPayableNow.value = parseFloat(totFirstInst) + parseFloat(totRefundFee);
	document.getElementById('totalAmntPayableNow').innerHTML = parseFloat(tcFirstInstallment) + parseFloat(wcFirstInstallment) + parseFloat(totRefundFee);
}

function addServiceFee(){
	//var totFirstInst = document.forms[0].totalFirstInstalment.value; 
	var tcFirstInstallment = document.forms[0].tcFirstInstallment.value;
    var wcFirstInstallment = document.forms[0].wcFirstInstallment.value; 
	//var totRefundFee = document.forms[0].totalServiceFeeRefund.value;
	var serviceFee = document.forms[0].servicefee.value;
	//var totFirstInst = document.getElementById('totalFirstInstalment').innerHTML;
	//var serviceFee = document.getElementById('servicefee').innerHTML;
	//alert("totFirstInst"+totFirstInst);
	//alert("serviceFee"+serviceFee);
	//var amountPayable = document.forms[0].totalAmntPayableNow.value;
	//document.forms[0].totalAmntPayableNow.value = parseFloat(totFirstInst) - parseFloat(serviceFee);
	document.getElementById('totalAmntPayableNow').innerHTML = parseFloat(tcFirstInstallment) + parseFloat(wcFirstInstallment) - parseFloat(serviceFee);
}

function calTotRefund(){
		var tcRF = document.forms[0].serviceFeeForOneYearDiffforTC.value;
		var wcRF = document.forms[0].serviceFeeForOneYearDiffforWC.value;
		var tcFirstInstallment = document.forms[0].tcFirstInstallment.value;
		var wcFirstInstallment = document.forms[0].wcFirstInstallment.value; 
	//	var totFirstInst = document.forms[0].totalFirstInstalment.value; 
	//	var totFirstInst = document.getElementById('totalFirstInstalment').innerHTML;
		if (!(isNaN(tcRF)) && tcRF!="")
		{
			tcRF=parseFloat(tcRF);	
		}else{
			tcRF = 0.0;
		}
		if (!(isNaN(wcRF)) && wcRF!="")
		{
			wcRF=parseFloat(wcRF);	
		}else{
			wcRF = 0.0;
		}
		document.forms[0].totalServiceFeeRefund.value = tcRF + wcRF;
		//document.getElementById('totalServiceFeeRefund').innerHTML = tcRF + wcRF;
		if(document.forms[0].refundFlag.checked){
			//document.forms[0].totalAmntPayableNow.value = parseFloat(totFirstInst) + tcRF + wcRF;
			document.getElementById('totalAmntPayableNow').innerHTML = parseFloat(tcFirstInstallment) + parseFloat(wcFirstInstallment) + tcRF + wcRF;
		}
}
</script>
<%
String bid = null;
ClaimDetail clmDtl = null;
String type = null;
double tcIssued = 0.0;
double wcIssued = 0.0;
double tcRecoveryMade = 0.0;
double wcRecoveryMade = 0.0;
double totalNetOutstanding = 0.0;
double tcClaimApplied = 0.0;
double wcClaimApplied = 0.0;
double tcNetOutstanding = 0.0;
double wcNetOutstanding = 0.0;
double tcServiceFee = 0.0;
double wcServiceFee = 0.0;
double totalServiceFee = 0.0;


double tcRefundFee = 0.0;
double wcRefundFee = 0.0;
double totalRefundFee = 0.0;


double amt=0.0;
double amt2=0.0;

double rate = 0.75;
double rate1=0.50;
double r2=0.0;   
double r1=0.0;
String microFlag = "N";

String userId = null;

String womenOperated = "M";
String typeofActivity = "";
String schemeName = "";
String nerFlag = "N";
String stateName = "";

String claimreferencenumber = request.getParameter("ClaimRefNumber");
// System.out.println("Claim Reference Number : "+claimreferencenumber);
//String remarks =" The MLI has adhered to all stipulated conditions such as classified the account as NPA, filed suit with the competent authority and the lock-in period is also over. As all the other  terms and conditions are fulfilled by the MLI, it is proposed that we may consider the claim for settlement.";
String remarks="";


%>



<html:errors />
<body onload ="javascript:calculateAmountPayable()">
<html:form action="displayClaimApprovalMod.do?method=displayClaimApprovalMod" method="POST" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">    
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
    <td align="right" valign="bottom"><img src="images/TableLeftTop.gif" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
    <td align="right" valign="top" background="images/TableBackground1.gif"> 
      <div align="right"></div></td>
    <td align="left" valign="bottom"><img src="images/TableRightTop.gif" height="31"></td>
  </tr>
  <tr> 
    <td background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="0">
                                                            
                <tr> 
                  <td colspan="8"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td height="10" class="Heading">&nbsp;<bean:message key="claimdetails"/> for  <%=claimreferencenumber%>&nbsp;</td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
		<%
		      // ClaimActionForm 
           claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm") ;
          // System.out.println("claimForm.getIsClaimProceedings():"+claimForm.getIsClaimProceedings());
           userId =(String)claimForm.getUserId();
		       clmDtl = claimForm.getClaimdetail();
		       bid = clmDtl.getBorrowerId();
           clmDtl.setStandardRemarks(remarks);
		       // System.out.println("Borrower Id of the Claim Application :" +bid);
		       // System.out.println("Claim Ref Number of the Claim Application :" +clmDtl.getClaimRefNum());
		%>                
                <tr> 
		     <td class="ColumnBackground" colspan="1"> <div align="left">&nbsp; <bean:message key="cpmliname"/></div></td> 		
		     <td class="TableData" colspan="8"> <div align="left"> &nbsp;<bean:write property="claimdetail.mliName" name="cpTcDetailsForm"/></div></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground" colspan="1"> <div align="left">&nbsp; <bean:message key="memberId"/></div></td> 

                  <td class="TableData" colspan="8"> <div align="left"> &nbsp;<bean:write property="claimdetail.mliId" name="cpTcDetailsForm"/></div></td>
                </tr>
               
                <tr> 
	           <td class="ColumnBackground" colspan="1"> <div align="left">&nbsp; <bean:message key="cpssiunitname"/></div></td> 
	       
	           <td class="TableData" colspan="8"> <div align="left"> &nbsp;<bean:write property="claimdetail.ssiUnitName" name="cpTcDetailsForm"/></div></td>
                </tr>
             <!--  <tr>
               <td class="ColumnBackground" colspan="1">&nbsp;<bean:message key="whetherrecprocconcluded"/></td>
	       	    <td class="TableData" colspan="8">Yes</td>
		         </tr> -->
 <!--  <tr>
		     <td class="ColumnBackground" colspan="1">&nbsp;<bean:message key="haslegalproceedingsbeeninitiated"/></td>
		     <td class="TableData" colspan="8">Yes</td>
		</tr> -->
				
                
             <!--   <tr> 
                  <td class="ColumnBackground" colspan="1"> <div align="left">&nbsp;<bean:message key="dateofissueofrecallnotice"/></div></td>
                  <td class="TableData" colspan="8"> <div align="left"><bean:write property="claimdetail.dateOfIssueOfRecallNoticeStr" name="cpTcDetailsForm"/></div></td>
                </tr> -->
		
		<tr>
			<td><br></td>
		</tr>								
		
                <tr> 
                  <td colspan="4" class="SubHeading">&nbsp;<bean:message key="cgpandetails"/></td>
                </tr>
				<tr>
                  <td colspan="8" class="SubHeading">
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                      <tr>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="cgpan"/></div>
                        </td>
                        <td class="ColumnBackground" colspan="2"><div align="center">&nbsp;<bean:message key="creditsanctionedbymli"/></div>
                        </td>
                        <td class="ColumnBackground" colspan="2"><div align="center">&nbsp;<bean:message key="cpguaranteeissued"/></div>
                        </td>
                        <td class="ColumnBackground" rowspan="2"><div align="center" > &nbsp;<bean:message key="cpamntdisbursedfortc"/></div>
                        </td>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="dtoflastdsbrsmntfortc"/></div>
                        </td>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="dtofreleaseofwc"/></div>
			</td>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="cpguaranteestartdate"/></div></td>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="cplockinperiodendson"/></div></td>
			
		<!--	<td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="cpservicefee"/></td>
			<td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="cpdtofpaymentofservicefee"/></td> -->
			</tr>			
			<tr class="ColumnBackground">
			<td><div align="center">&nbsp;<bean:message key="cptermcredit"/></div></td>
			<td><div align="center">&nbsp;<bean:message key="cpworkingcapital"/></div></td>			
			<td><div align="center">&nbsp;<bean:message key="cptermcredit"/></div></td>
			<td><div align="center">&nbsp;<bean:message key="cpworkingcapital"/></div></td>			
			</tr>			
			<logic:iterate property="claimdetail.cgpanDetails" name="cpTcDetailsForm" id="object">
			<%
			java.util.HashMap hashmap = (java.util.HashMap)object;
			String cgpan = (String)hashmap.get(ClaimConstants.CLM_CGPAN);
      
			double tcSanctionedAmnt = ((Double)hashmap.get(ClaimConstants.CLM_TC_SANCTIONED_AMNT)).doubleValue();
			double wcFBSanctionedAmnt = ((Double)hashmap.get(ClaimConstants.CLM_WC_FB_SANCTIONED_AMNT)).doubleValue();
			double wcNFBSanctionedAmnt = ((Double)hashmap.get(ClaimConstants.CLM_WC_NFB_SANCTIONED_AMNT)).doubleValue();						
			double totalWCSanctionedAmnt = wcFBSanctionedAmnt + wcNFBSanctionedAmnt;
			double tcApprovedAmnt = 0.0;
			double wcApprovedAmnt = 0.0;
			java.util.Date lastDisbursementDt = null;
			String lastDisbursementDtStr = null;
			double totalDisbursedAmount = 0.0;
			java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
			String loanType = (String)hashmap.get(ClaimConstants.CGPAN_LOAN_TYPE);
      type = loanType;
    //  System.out.println("type:"+type);
			if(loanType.equals(ClaimConstants.CGPAN_TC_LOAN_TYPE)||loanType.equals(ClaimConstants.CGPAN_CC_LOAN_TYPE))
			{
			    tcApprovedAmnt = ((Double)hashmap.get(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT)).doubleValue(); 
		
          tcIssued = tcIssued+tcApprovedAmnt;
		  
		  
		 // out.println("tcIssued----------:"+tcIssued);
          totalDisbursedAmount = ((Double)hashmap.get(ClaimConstants.CLM_TOTAL_DISBURSEMENT_AMNT)).doubleValue();
			    lastDisbursementDt = (java.util.Date)hashmap.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
			    // lastDisbursementDtStr = lastDisbursementDt.toString();
			    if(lastDisbursementDt != null)
			    {
			    	lastDisbursementDtStr = sdf.format(lastDisbursementDt);
			    }
          
			}
			else if(loanType.equals(ClaimConstants.CGPAN_WC_LOAN_TYPE))
			{
			    wcApprovedAmnt = ((Double)hashmap.get(ClaimConstants.CLM_APPLICATION_APPRVD_AMNT)).doubleValue();
			    wcIssued = wcIssued+wcApprovedAmnt;
          totalDisbursedAmount = 0.0;
			    lastDisbursementDtStr = "-";
          
			}
			java.util.Date guaranteeStartDt = (java.util.Date)hashmap.get(ClaimConstants.CLM_GUARANTEE_START_DT);
			String guaranteeStartDtStr = null;
			if(guaranteeStartDt != null)
			{
			    // guaranteeStartDtStr = guaranteeStartDt.toString();
			    guaranteeStartDtStr = sdf.format(guaranteeStartDt);
			}
			else
			{
			    guaranteeStartDtStr = "-";
			}
			java.util.Date dtOfReleaseOfWC = (java.util.Date)hashmap.get(ClaimConstants.CLM_DT_OF_RELEASE_OF_WC);
			String dtOfReleaseOfWCStr = null;
			if(dtOfReleaseOfWC != null)
			{
			    // dtOfReleaseOfWCStr = dtOfReleaseOfWC.toString();
			    dtOfReleaseOfWCStr = sdf.format(dtOfReleaseOfWC);
			}
			else
			{
			    dtOfReleaseOfWCStr = "-";
			}
			Double serviceFee = (Double)hashmap.get(ClaimConstants.CLM_RP_AMOUNT_RAISED);
			String serviceFeeStr = null;
			if(serviceFee != null)
			{
			    serviceFeeStr = serviceFee.toString();
			}
			else
			{
			    serviceFeeStr = "";
			}
			
			java.util.Date lastServiceFeeDate = (java.util.Date)hashmap.get(ClaimConstants.CLM_RP_APPROPRIATION_DT);
			String lastServiceFeeDateStr = null;
			if(lastServiceFeeDate != null)
			{
			    // lastServiceFeeDateStr =  lastServiceFeeDate.toString();
			    lastServiceFeeDateStr = sdf.format(lastServiceFeeDate);
			}
			else
			{
			     lastServiceFeeDateStr = "";
			}
			java.util.Date lockInPeriodEndDate = (java.util.Date)hashmap.get(ClaimConstants.CLM_LOCK_IN_PERIOD_END_DATE);
			String lockInPeriodEndDateStr = null;
			if(lockInPeriodEndDate != null)
			{
			   // lockInPeriodEndDateStr =  lockInPeriodEndDate.toString();
			   lockInPeriodEndDateStr = sdf.format(lockInPeriodEndDate);
			}
			else
			{
			   lockInPeriodEndDateStr = "-";
			}
			%>
			<tr class="TableData">
			<td><%=cgpan%></td>
			<td><div align="center"><%=tcSanctionedAmnt%></div></td>
			<td><div align="center">
<%if(Double.toString(totalWCSanctionedAmnt).equals("1.0E7"))
{%>10000000<%
}
else
{%><%=totalWCSanctionedAmnt%><%
}

%>





</div></td>
			<td><div align="center"><%=tcApprovedAmnt%></div></td>
			<td><div align="center">


<%if(Double.toString(wcApprovedAmnt).equals("1.0E7"))
{%>10000000<%
}
else
{%><%=wcApprovedAmnt%><%
}

%>


</div></td>
			<td><div align="center"><%=totalDisbursedAmount%></div></td>

			<td><div align="center"><%=lastDisbursementDtStr%></div></td>
			<td><div align="center"><%=dtOfReleaseOfWCStr%></div></td>
			<td><div align="center"><%=guaranteeStartDtStr%></div></td>
			<td><div align="center"><%=lockInPeriodEndDateStr%></div></td>			
		<!--	<td><div align="center"><%=serviceFeeStr%></div></td>
			<td><div align="center"><%=lastServiceFeeDateStr%></div></td> -->
			</tr>
      
			</logic:iterate>
			</table></td>
                </tr>
                
                
    <tr> 
		   <td colspan="7" class="SubHeading">&nbsp;Dan Summary Report Details </td>
    </tr>
    
      <TR>
       <TD align="left" class="ColumnBackground"><bean:message key="dan"/></TD>
                  <TD width = "15%" align="left" class="ColumnBackground"> 
                  &nbsp;CGPAN</TD>
                  <TD  align="right" class="ColumnBackground"> 
								  &nbsp;DAN Amt</TD>
                  <TD width = "20%" align="left" class="ColumnBackground"> 
								&nbsp;<bean:message key="payId"/></TD>
                <TD width = "10%" align="left" class="ColumnBackground"> 
								&nbsp;Realised Date</TD>
                <TD width = "10%" align="left" class="ColumnBackground"> 
								&nbsp;Appr Date</TD>
                <TD align="left" class="ColumnBackground"> 
									&nbsp;DD No.</TD>
                   <TD align="left" class="ColumnBackground"> 
									&nbsp;Expiry Dt.</TD> 
       <!--       <TD width = "10%" align="left" class="ColumnBackground"> 
								&nbsp;Expiry Date
									</TD>
                    <TD align="left" class="ColumnBackground"> 
									&nbsp;<bean:message key="Closure"/>
									</TD> -->
              		</TR>
                  <tr>
                <logic:iterate property="claimdetail.cgpanDetails" name="cpTcDetailsForm" id="object">
                  <%
                  java.util.HashMap hashmap = (java.util.HashMap)object;
			            String cgpan = (String)hashmap.get(ClaimConstants.CLM_CGPAN);
            //      System.out.println("CGPAN:"+cgpan);
                  java.util.ArrayList danSummary = (java.util.ArrayList)claimForm.getDanSummaryReportDetails(cgpan);
                  int danSummarysize=danSummary.size();
                  com.cgtsi.reports.ApplicationReport dReport =  null;
                  
                  for (int j=0; j<danSummarysize;j++)
									{
										dReport=(com.cgtsi.reports.ApplicationReport)danSummary.get(j);
									 String danId = dReport.getDan();
                   String cgpanNew = dReport.getCgpan();
                   String payId = dReport.getPayId();
                   String danRaised = dReport.getAppGuaranteeFee();
                   java.util.Date utilDate3=dReport.getAppGuarStartDateTime();
													String formatedDate3 = null;
													if(utilDate3 != null)
													{
														 formatedDate3=dateFormat.format(utilDate3);
													}
													else
													{
														 formatedDate3 = "Not Appr";
													}
                          java.util.Date utilDate5=dReport.getStartDate();
													String formatedDate5 = null;
													if(utilDate5 != null)
													{
														 formatedDate5=dateFormat.format(utilDate5);
													}
													else
													{
														 formatedDate5 = "";
													}
                           java.util.Date utilDate6=dReport.getExpiryDate();
													String formatedDate6 = null;
													if(utilDate6 != null)
													{
														 formatedDate6=dateFormat.format(utilDate6);
													}
													else
													{
														 formatedDate6 = "";
													}
                      String ddNum = dReport.getDdNum();
                   // System.out.println(danId);
                   %>
                  <tr>
                  <TD  align="left" valign="top" class="ColumnBackground1"> <%=danId%></td>
                  <TD  align="left" valign="top" class="ColumnBackground1"> <%=cgpanNew%></td>
                  <TD  align="right" valign="top" class="ColumnBackground1"> <%=danRaised%></td>
                  <TD  align="left" valign="top" class="ColumnBackground1"> <%=payId%></td>
                  <TD  align="left" valign="top" class="ColumnBackground1"> <%=formatedDate3%></td> 
                  <TD  align="left" valign="top" class="ColumnBackground1"> <%=formatedDate5%></td> 
                  <TD  align="left" valign="top" class="ColumnBackground1"> <%=ddNum%></td>
                 <TD  align="left" valign="top" class="ColumnBackground1"> <%=formatedDate6%></td> 
                  </tr>
                 
                  <%
                	 }
                  %>
                  </logic:iterate>
                </tr> 
                
                <tr> 
		   <td colspan="4" class="SubHeading">&nbsp;NPA Details</td>
                </tr>
                
                <tr> 
		  <td class="ColumnBackground" colspan="1"> <div align="left">&nbsp;<bean:message key="dateofnpa"/></div></td>
		  <td class="TableData" colspan="8"> <div align="left"> &nbsp;<bean:write property="claimdetail.npaDateStr" name="cpTcDetailsForm"/></div></td>
		</tr>
		<tr> 
		  <td class="ColumnBackground" colspan="1"> <div align="left">&nbsp;<bean:message key="dateofreporting"/></div></td>
		  <td class="TableData" colspan="8"> <div align="left"> &nbsp;<bean:write property="claimdetail.dtOfNPAReportedToCGTSIStr" name="cpTcDetailsForm"/></div></td>
		</tr>
		<tr> 
		  <td class="ColumnBackground" colspan="1"> <div align="left">&nbsp;<bean:message key="reasonfornpa"/></div></td>
		  <td class="TableData" colspan="8"> <div align="left"> &nbsp;<bean:write property="claimdetail.reasonForTurningNPA" name="cpTcDetailsForm"/></div></td>
    </tr>
    <tr> 
    <td class="ColumnBackground" colspan="1"> <div align="left">&nbsp;<bean:message key="dateofissueofrecallnotice"/></div></td>
    <td class="TableData" colspan="8"> <div align="left"><bean:write property="claimdetail.dateOfIssueOfRecallNoticeStr" name="cpTcDetailsForm"/></div></td>
   </tr>
   <tr>
		<td class="ColumnBackground" colspan="1">&nbsp;<bean:message key="haslegalproceedingsbeeninitiated"/></td>
		<td class="TableData" colspan="8">Yes</td>
		</tr> 
    <tr>
    <td class="ColumnBackground" colspan="1">&nbsp;<bean:message key="whetherrecprocconcluded"/></td>
	  <td class="TableData" colspan="8"><bean:write property="claimapplication.legalProceedingsDetails.isRecoveryProceedingsConcluded" name="cpTcDetailsForm"/></td>
		</tr>
     <!-- added by sukumar@path on 14-01-2010 for displayed Micro flag or not and  calculating rate -->
    <tr><td class="ColumnBackground" colspan="1">&nbsp;Whether the Unit Assisted is an MICRO as per the MSMED Act 2006 definition of MSE:</td>
		<td class="TableData" colspan="8"> <div align="left"> &nbsp;<bean:write property="claimapplication.microCategory" name="cpTcDetailsForm"/></div></td>
    </tr>
    
    
    <tr>
    <td class="ColumnBackground" colspan="1">&nbsp;Date of filing of suit/ legal proceedings like DRT,Revenue recovery,SERFASI etc</td>
	  <td class="TableData" colspan="8"><div align="left">
	  <% java.util.Date legalDate = clmDtl.getLegalFilingDate();
                          String legalSuitNo = clmDtl.getLegalSuitNumber();
                          String legalForum = clmDtl.getLegalForum();
                          String legalForumName = clmDtl.getLegalForumName();
                          String legalLocation = clmDtl.getLegalLocation();
                          String formatedDate8 = null;
                          womenOperated = clmDtl.getWomenOperated();
                          nerFlag = clmDtl.getNerFlag();
                          stateName = clmDtl.getStateName();
                          typeofActivity = clmDtl.getTypeofActivity();
                          schemeName = clmDtl.getSchemeName();
                          if(typeofActivity==null ||typeofActivity.equals("")){
                            typeofActivity  = "";
                          }
                           if(schemeName==null ||schemeName.equals("")){
                            schemeName  = "";
                          }
                          
                        //  System.out.println("Women Operated:"+womenOperated);
                        //  System.out.println("NER Flag:"+nerFlag);
													if(legalDate != null)
													{
														 formatedDate8=dateFormat.format(legalDate);
													}
													else
													{
														 formatedDate8 = "";
													}
    %>
Legal Forum Name:&nbsp;<%=legalForumName%> at <%=legalLocation%>&nbsp;Legal Suit Number <%=legalSuitNo%>&nbsp;Dated &nbsp;<%=formatedDate8%> </div></td>
		</tr>
      <tr>
    <td class="ColumnBackground" colspan="1">&nbsp;Gender of Chief Promoter:</td>
	  <td class="TableData" colspan="8"><%=womenOperated%></td>
		</tr>
     <tr>
    <td class="ColumnBackground" colspan="1">&nbsp; Borrower State Name</td>
	  <td class="TableData" colspan="8"><%=stateName%></td>
		</tr>
    
    <tr>
    <td class="ColumnBackground" colspan="1">&nbsp;<bean:message key="activitytype"/></td>
	  <td class="TableData" colspan="8"><%=typeofActivity%></td>
		</tr>
    <tr>
    <td class="ColumnBackground" colspan="1">&nbsp;<bean:message key="schemeName"/></td>
	  <td class="TableData" colspan="8"><%=schemeName%> <input type="hidden" name="schemeName" value="<%=schemeName%>"  maxlength="10"/></td>
		</tr>
     <tr>
    <td class="ColumnBackground">&nbsp;Date of Receipt of complete information: </td>
    <td class="ColumnBackground" colspan="7">&nbsp;<html:text property="dateofReceipt" size="15" alt="dateofReceipt" name="cpTcDetailsForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dateofReceipt')" align="center">
							
    </td>
	  </tr>
   <!--  <tr>
    <td class="ColumnBackground" colspan="1">&nbsp;Is Claim to be Settled &nbsp;</td>
	  <td class="TableData" colspan="8"><html:radio name="cpTcDetailsForm" value="Y" property="isClaimProceedings" onclick="enableClaimAmounts()"></html:radio>
    <bean:message key="yes" />&nbsp;&nbsp;
    <html:radio name="cpTcDetailsForm" value="N" property="isClaimProceedings" onclick="enableClaimAmounts()"></html:radio>
    <bean:message key="no" />
    </td>
		</tr>    -->
		<tr>
    <td class="ColumnBackground" colspan="1">&nbsp;DC(HANDICRAFT) FLAG</td>
	  <td class="TableData" colspan="8">	<bean:write property="claimdetail.handicraft" name="cpTcDetailsForm"/>				  								
				  					</td>
		</tr>
                
                 <tr>
    <td class="ColumnBackground" colspan="1">&nbsp;DC(HANDICRAFT) REIMBURSEMENT FLAG</td>
	  <td class="TableData" colspan="8">	<bean:write property="claimdetail.dcHandicraft"  name="cpTcDetailsForm"/>				  								
				  					</td>
		</tr>

		
                
              
             <!-- added for testing -->  
   <tr style="page-break-before: always"><td>&nbsp;</td></tr> 
 <!--  <tr class="break"><td>&nbsp;</td></tr> -->
  
  

<!-- end part here -->
          <tr colspan="8"> <td colspan="7" class="SubHeading">&nbsp;</td><td colspan="7" class="SubHeading" align="right"><b><font size="2" color="red">Annexure-I </font></b></td></tr>
                <tr colspan="8"> 
		           <td colspan="2" class="SubHeading">&nbsp;<!--<bean:message key="cpnpasummry"/>-->Claim Settlement Details for  </td>
              <td colspan="6" class="SubHeading"><bean:write property="claimdetail.ssiUnitName" name="cpTcDetailsForm"/>
	<logic:iterate property="claimdetail.cgpanDetails" name="cpTcDetailsForm" id="object">
			<%
			java.util.HashMap hashmap = (java.util.HashMap)object;
			String cgpan = (String)hashmap.get(ClaimConstants.CLM_CGPAN);
			%><font  color="blue">&nbsp;&nbsp;( <%=cgpan%>)</font>
    </logic:iterate>
               </td> 
                </tr>
                <tr>
                <td class="ColumnBackground" colspan="2">&nbsp;</td>
                <td class="ColumnBackground" colspan="2">&nbsp;Term Loan</td>
                <td class="ColumnBackground" colspan="2">&nbsp;Working Capital</td>
                <td class="ColumnBackground" colspan="2"><div align="center">Total</div></td>
                </tr>
                <%
                 claimForm.setTcIssued(tcIssued);
                 claimForm.setWcIssued(wcIssued);
                %>
                <tr><td class="ColumnBackground" colspan="2">&nbsp;Guarantee Issued</td>
               <!-- <td class="ColumnBackground" colspan="2">&nbsp;<bean:write property="tcIssued" name="cpTcDetailsForm" /></td>
                <td class="ColumnBackground" colspan="2">&nbsp;<bean:write property="wcIssued" name="cpTcDetailsForm" /></td> -->


 <td class="ColumnBackground" colspan="2">&nbsp;<%=decimalFormat.format(tcIssued)%></td>
                <td class="ColumnBackground" colspan="2">&nbsp;<%=decimalFormat.format(wcIssued)%></td>
                <logic:equal property="claimapplication.microCategory" value="Y" name="cpTcDetailsForm">	
                 <%
                    microFlag="Y";
                   
              //      System.out.println("Micro Flag:"+microFlag);
                  %>
                </logic:equal>
            <!-- <logic:notEqual  property="claimapplication.microCategory" value="Y" name="cpTcDetailsForm">	
            <%
                 microFlag="N";
              //   System.out.println("Micro Flag:"+microFlag);
             %>
            </logic:notEqual> -->
            
                <script language = "JavaScript" type="text/javascript">
                var tci = <%=tcIssued%>;
                var wci = <%=wcIssued%>;
                var microFlag = '<%=microFlag%>';
                var womenOwned = '<%=womenOperated%>';
                var nerFlag = '<%=nerFlag%>';
                </script>
                <td class="ColumnBackground" colspan="2">&nbsp;<%=decimalFormat.format(tcIssued+wcIssued)%></td>
                </tr>
                
                <tr><td class="ColumnBackground" colspan="2">&nbsp;Amount Outstanding as on date of NPA</td>
             <td class="ColumnBackground" colspan="2">&nbsp;<html:text property="claimdetail.totalTCOSAmountAsOnNPA" name="cpTcDetailsForm" onchange="calltotalAmtNew(tci,wci,microFlag,womenOwned,nerFlag,schemeName)"   maxlength="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
             <td class="ColumnBackground" colspan="2">&nbsp;<html:text property="claimdetail.totalWCOSAmountAsOnNPA" name="cpTcDetailsForm" onchange="calltotalAmtNew(tci,wci,microFlag,womenOwned,nerFlag,schemeName)"   maxlength="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td> 
            <% 
             claimForm.setMicroCategory(microFlag);
             claimForm.setWomenOperated(womenOperated);
             claimForm.setNerFlag(nerFlag);
             
            double tcOutstandingasonNPA = clmDtl.getTotalTCOSAmountAsOnNPA();
            //   System.out.println("tcOutstandingasonNPA:"+tcOutstandingasonNPA);           
               double wcOutstandingasonNPA =  clmDtl.getTotalWCOSAmountAsOnNPA();
          //      System.out.println("wcOutstandingasonNPA:"+wcOutstandingasonNPA);
              double totalAmntAsOnNPA = tcOutstandingasonNPA+wcOutstandingasonNPA;
          //    System.out.println("totalOsAmntAsOnNPA:"+totalAmntAsOnNPA);
              claimForm.setTotalOsAmntAsOnNPA(totalAmntAsOnNPA);           
            %>
             <td class="ColumnBackground" colspan="2" id="totalOsAmntAsOnNPA">&nbsp;<bean:write property="totalOsAmntAsOnNPA" name="cpTcDetailsForm" /> </td>
          <!-- <td class="ColumnBackground" colspan="2">&nbsp;<bean:write property="claimdetail.totalTCOSAmountAsOnNPA" name="cpTcDetailsForm" /></td>
                <td class="ColumnBackground" colspan="2">&nbsp;<bean:write property="claimdetail.totalWCOSAmountAsOnNPA" name="cpTcDetailsForm" /></td> -->
           
        <!--        <td class="ColumnBackground" colspan="2">&nbsp;<%=totalAmntAsOnNPA%> --><!--<html:text property="totalAmntAsOnNPA" size="25" alt="totalAmntAsOnNPA" name="cpTcDetailsForm" maxlength="10" /></td> -->
                </tr>
            	<logic:iterate property="claimapplication.recoveryDetails" id="object" name="cpTcDetailsForm">
				      <%
  					    RecoveryDetails recDtl = (RecoveryDetails)object; 
                double	  tcInterestAndOtherCharges = recDtl.getTcInterestAndOtherCharges();
					  // System.out.println("TC Interest Charges :" + tcInterestAndOtherCharges);
                double  tcPrincipal = recDtl.getTcPrincipal();
                tcRecoveryMade = tcRecoveryMade+tcInterestAndOtherCharges + tcPrincipal;
					 //     System.out.println("tcRecoveryMade :" + tcRecoveryMade);
			          double		  wcAmount = recDtl.getWcAmount();
					  // System.out.println("WC Amount :" + wcAmount);
					      double  wcOtherCharges = recDtl.getWcOtherCharges();
                wcRecoveryMade = wcRecoveryMade+wcAmount + wcOtherCharges; 
           //    System.out.println("wcRecoveryMade:"+wcRecoveryMade);
               claimForm.setTcrecovery(tcRecoveryMade); 
               claimForm.setWcrecovery(wcRecoveryMade);
               claimForm.setTotalRecovery(Double.parseDouble(decimalFormat.format(tcRecoveryMade+wcRecoveryMade)));
                 %>
              </logic:iterate>
                 <tr>
                 <td class="ColumnBackground" colspan="2">&nbsp;Amount Recovered after NPA</td>
                 <td class="ColumnBackground" colspan="2">&nbsp;<html:text property="tcrecovery" name="cpTcDetailsForm" onchange="calltotalAmtNew(tci,wci,microFlag,womenOwned,nerFlag,schemeName)"    maxlength="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
                 <td class="ColumnBackground" colspan="2">&nbsp;<html:text property="wcrecovery" name="cpTcDetailsForm" onchange="calltotalAmtNew(tci,wci,microFlag,womenOwned,nerFlag,schemeName)"    maxlength="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
                 <td class="ColumnBackground" colspan="2" id="totalRecovery">&nbsp;<bean:write name="cpTcDetailsForm" property="totalRecovery"/></td>
       <!--  <td class="ColumnBackground" colspan="2">&nbsp;<%=tcRecoveryMade%></td>
                <td class="ColumnBackground" colspan="2">&nbsp;<%=wcRecoveryMade%></td>
                <td class="ColumnBackground" colspan="2">&nbsp;<%=(tcRecoveryMade+wcRecoveryMade)%></td> -->
               </tr>
              <%
                tcNetOutstanding = Math.min(tcIssued,tcOutstandingasonNPA)-(tcRecoveryMade);
            //    System.out.println("tcNetOutstanding:"+tcNetOutstanding);
                if(tcNetOutstanding < 0){
                 tcNetOutstanding = 0;
                }
                claimForm.setTcNetOutstanding(tcNetOutstanding);
                wcNetOutstanding = Math.min(wcIssued,wcOutstandingasonNPA)-(wcRecoveryMade);
            //    System.out.println("wcNetOutstanding:"+wcNetOutstanding);
                if(wcNetOutstanding < 0){
                 wcNetOutstanding = 0;
                }
                claimForm.setWcNetOutstanding(wcNetOutstanding);
                totalNetOutstanding = Double.parseDouble(decimalFormat.format(tcNetOutstanding+wcNetOutstanding));
                claimForm.setTotalNetOutstanding(totalNetOutstanding);
               %> 
                <tr><td class="ColumnBackground" colspan="2">&nbsp;Net Outstanding (Amount in default admissible by CGTMSE) </td>
        <td class="ColumnBackground" colspan="2" id="tcNetOutstanding">&nbsp;<html:text  name="cpTcDetailsForm" property="tcNetOutstanding" onchange="calltotalAmtNew(tci,wci,microFlag,womenOwned,nerFlag,schemeName)"  maxlength="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
        <td class="ColumnBackground" colspan="2" id="wcNetOutstanding">&nbsp;<html:text name="cpTcDetailsForm" property="wcNetOutstanding" onchange="calltotalAmtNew(tci,wci,microFlag,womenOwned,nerFlag,schemeName)" maxlength="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" /></td>
        <td class="ColumnBackground" colspan="2" id="totalNetOutstanding">&nbsp;<bean:write name="cpTcDetailsForm" property="totalNetOutstanding"/></td>
           <!--    <td class="ColumnBackground" colspan="2">&nbsp;<%=tcNetOutstanding%></td>
                <td class="ColumnBackground" colspan="2">&nbsp;<%=wcNetOutstanding%></td>
                <td class="ColumnBackground" colspan="2">&nbsp;<%=totalNetOutstanding%></td> -->
               </tr>
              <%int m=1;%>
           	<logic:iterate property="claimapplication.claimSummaryDtls" name="cpTcDetailsForm" id="object">
  						<%
  						ClaimSummaryDtls clmSummaryDtl = (ClaimSummaryDtls)object;             
  						String loanApprvd = clmSummaryDtl.getLimitCoveredUnderCGFSI();
              String cgpan = clmSummaryDtl.getCgpan();
              //String panType = cgpan.substring(11,13);
		int length = cgpan.length();
           String panType = "";
              if(length==13){
                panType = cgpan.substring(11,13);
              } else {
                 panType = cgpan.substring(13,15);
              }
  					//	clmAppliedAmnt = clmSummaryDtl.getAmount();
            if(panType.equals(ClaimConstants.CGPAN_TC_LOAN_TYPE)){
            
                     tcClaimApplied = tcClaimApplied+clmSummaryDtl.getAmount();
              //  System.out.println("wcClaimApplied:"+wcClaimApplied);
             }
              if(panType.equals(ClaimConstants.CGPAN_WC_LOAN_TYPE)){
              
              
                  wcClaimApplied = wcClaimApplied+clmSummaryDtl.getAmount();
                //   System.out.println("wcClaimApplied:"+wcClaimApplied);
                }
  						%>
            </logic:iterate>
            <%m++;%>
                <tr><td class="ColumnBackground" colspan="2">&nbsp;Amount Claimed by MLI </td>
                <td class="ColumnBackground" colspan="2">&nbsp;<%=decimalFormat.format(tcClaimApplied)%></td>
                <td class="ColumnBackground" colspan="2">&nbsp;<%=decimalFormat.format(wcClaimApplied)%></td>
                <td class="ColumnBackground" colspan="2">&nbsp;<%=decimalFormat.format(tcClaimApplied+wcClaimApplied)%></td>
               </tr>
              
             
               <%
               double tcClaimEligible = 0.0;
               double wcClaimEligible = 0.0;
               double tcFirstInstallment = 0.0;
               double wcFirstInstallment = 0.0;
               double totalFirstInstalment = 0.0;
               double testFirstInstalment = 0.0;
               double tcClaimEligible1=0.0;
                double tcClaimEligible2=0.0;
                double wcClaimEligible1=0.0;
                 double wcClaimEligible2=0.0;
               if(schemeName.equals("RSF"))
                 {
                   rate=0.50;
               tcClaimEligible =  Math.round(tcNetOutstanding * rate);
               wcClaimEligible =  Math.round(wcNetOutstanding * rate); 
                    }
              else if((tcIssued+wcIssued)<=500000 && microFlag.equals("Y")){
                 rate = 0.80;
               //  falgforCasesafet = "N";
                 if (falgforCasesafet.equals("Y"))
                       {
                       rate=0.85;
                        tcClaimEligible =  Math.round(tcNetOutstanding * rate);
                        wcClaimEligible =  Math.round(wcNetOutstanding * rate);      
                       }else{
               //jai 2
                 tcClaimEligible =  Math.round(tcNetOutstanding * rate);
                 wcClaimEligible =  Math.round(wcNetOutstanding * rate);   
                 } 
               //  tcClaimEligible =  Math.round(tcNetOutstanding * rate);
              //   wcClaimEligible =  Math.round(wcNetOutstanding * rate); 
                 
                 
               }else if((tcIssued+wcIssued <=5000000) && (womenOperated.equals("F")|| nerFlag.equals("Y"))){
               
                    rate = 0.80;
                    tcClaimEligible =  Math.round(tcNetOutstanding * rate);
                    wcClaimEligible =  Math.round(wcNetOutstanding * rate);                 
               }
               else if((tcIssued+wcIssued)<=500000 && microFlag.equals("N")){
                 tcClaimEligible =  Math.round(tcNetOutstanding * rate);
                  wcClaimEligible =  Math.round(wcNetOutstanding * rate);  
            
               }
               else if(((schemeName.equals("CGFSI"))&&(tcIssued+wcIssued) > 5000000&&(nerFlag.equals("Y")))||((schemeName.equals("CGFSI"))&&(womenOperated.equals("F"))&&(tcIssued+wcIssued) > 5000000&&(nerFlag.equals("N"))))
               {
                rate=0.80;
                      
                     
               long l = (int)Math.round(((tcIssued+wcIssued-5000000)/(tcIssued+wcIssued)) * 1000000000); // truncates 
               r2 = l / 1000000000.0; 
              long l2 = (int)Math.round(((5000000)/(tcIssued+wcIssued)) * 1000000000); // truncates 
              r1 = l2 / 1000000000.0; 
              tcClaimEligible1 =  Math.round(tcNetOutstanding * rate*(r1));
              tcClaimEligible2 =  Math.round(tcNetOutstanding * rate1*(r2));
            tcClaimEligible=tcClaimEligible1+tcClaimEligible2;
               wcClaimEligible1 =  Math.round(wcNetOutstanding * rate*(r1));
              wcClaimEligible2 =  Math.round(wcNetOutstanding * rate1*(r2));
               wcClaimEligible=  wcClaimEligible1+wcClaimEligible2;   
            
                //  tcClaimEligible =  Math.round(tcNetOutstanding * rate);
                //  wcClaimEligible =  Math.round(wcNetOutstanding * rate);   
                }
               else if((schemeName.equals("CGFSI"))&&(womenOperated.equals("M"))&&(tcIssued+wcIssued) > 5000000&&(nerFlag.equals("N")))
               {
                   
              rate=0.75;  
              long l = (int)Math.round(((tcIssued+wcIssued-5000000)/(tcIssued+wcIssued)) * 1000000000); // truncates 
              r2 = l / 1000000000.0; 
              long l2 = (int)Math.round(((5000000)/(tcIssued+wcIssued)) * 1000000000); // truncates 
              r1 = l2 / 1000000000.0; 
               tcClaimEligible1 =  Math.round(tcNetOutstanding * rate*(r1));
               tcClaimEligible2 =  Math.round(tcNetOutstanding * rate1*(r2));
              tcClaimEligible=tcClaimEligible1+tcClaimEligible2;
               wcClaimEligible1 =  Math.round(wcNetOutstanding * rate1*(r2));
                wcClaimEligible2 =  Math.round(wcNetOutstanding * rate*(r1));
              wcClaimEligible=  wcClaimEligible1+wcClaimEligible2;            
         
                 }
else if((tcIssued+wcIssued) > 500000){
                 tcClaimEligible =  Math.round(tcNetOutstanding * rate);
                 wcClaimEligible =  Math.round(wcNetOutstanding * rate);                 
               }
                 
           //     tcClaimEligible =  Math.round(tcNetOutstanding * rate);
           //     wcClaimEligible =  Math.round(wcNetOutstanding * rate);                 
      
               claimForm.setTcClaimEligibleAmt(tcClaimEligible);
               claimForm.setWcClaimEligibleAmt(wcClaimEligible);
               claimForm.setTotalClaimEligibleAmt(tcClaimEligible+wcClaimEligible);
               
               tcFirstInstallment = Math.round(tcClaimEligible  * 0.75);
               wcFirstInstallment  = Math.round(wcClaimEligible * 0.75);
               totalFirstInstalment = tcFirstInstallment + wcFirstInstallment;
               testFirstInstalment = tcFirstInstallment + wcFirstInstallment;
               claimForm.setTcFirstInstallment(tcFirstInstallment);
               claimForm.setWcFirstInstallment(wcFirstInstallment);
               claimForm.setTotalFirstInstalment(tcFirstInstallment+wcFirstInstallment);
               claimForm.setTestFirstInstalment(tcFirstInstallment+wcFirstInstallment);               %> 
               
                <logic:equal  property="isClaimProceedings" value="Y" name="cpTcDetailsForm">	
                <tr><td class="ColumnBackground" colspan="2">&nbsp;<bean:message key="clmeligibilityamnt"/>&nbsp; (<%=Math.round(rate*100)%>&nbsp;%&nbsp;)</td>
                 </logic:equal>
               <logic:notEqual  property="isClaimProceedings" value="Y" name="cpTcDetailsForm">	
                <tr><td class="ColumnBackground" colspan="2">&nbsp;<font color="red">Claim Amount to be Rejected&nbsp;</font></td>
                 </logic:notEqual>
                 <tr>
           <td class="ColumnBackground" colspan="2" id="tcClaimEligibleAmt">&nbsp;<html:text name="cpTcDetailsForm" property="tcClaimEligibleAmt"    onchange="calltotalAmtNew(tci,wci,microFlag,womenOwned,nerFlag,schemeName)" maxlength="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" /></td>
           <td class="ColumnBackground" colspan="2" id="wcClaimEligibleAmt">&nbsp;<html:text name="cpTcDetailsForm" property="wcClaimEligibleAmt"  onchange="calltotalAmtNew(tci,wci,microFlag,womenOwned,nerFlag,schemeName)" maxlength="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" /></td>
           <td class="ColumnBackground" colspan="2" id="totalClaimEligibleAmt">&nbsp;<bean:write name="cpTcDetailsForm" property="totalClaimEligibleAmt" /></td>
      
             <!--   <td class="ColumnBackground" colspan="2">&nbsp;<bean:write property="tcClaimEligibleAmt" name="cpTcDetailsForm"/></td>
                <td class="ColumnBackground" colspan="2">&nbsp;<bean:write property="wcClaimEligibleAmt" name="cpTcDetailsForm"/></td>
                <td class="ColumnBackground" colspan="2">&nbsp;<%=tcClaimEligible+wcClaimEligible%></td> -->
               </tr>
             
                <logic:equal property="isClaimProceedings" value="Y" name="cpTcDetailsForm">	
               
                <tr><td class="ColumnBackground" colspan="2">&nbsp;Amount Payable in First Installment</td>
               <td class="ColumnBackground" colspan="2" id="tcFirstInstallment">&nbsp;<html:text name="cpTcDetailsForm" property="tcFirstInstallment" onchange="calltotalAmtNew(tci,wci,microFlag,womenOwned,nerFlag,schemeName)" maxlength="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" /></td>
               <td class="ColumnBackground" colspan="2" id="wcFirstInstallment">&nbsp;<html:text name="cpTcDetailsForm" property="wcFirstInstallment" onchange="calltotalAmtNew(tci,wci,microFlag,womenOwned,nerFlag,schemeName)" maxlength="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
               <td class="ColumnBackground" colspan="2" id="totalFirstInstalment">&nbsp;<html:text name="cpTcDetailsForm" property="totalFirstInstalment"/></td>
      
              <!--    <td class="ColumnBackground" colspan="2">&nbsp;<bean:write property="tcFirstInstallment" name="cpTcDetailsForm"/></td>
                <td class="ColumnBackground" colspan="2">&nbsp;<bean:write property="wcFirstInstallment" name="cpTcDetailsForm"/></td>
                <% clmDtl.setEligibleClaimAmt(tcFirstInstallment+wcFirstInstallment); %>
                <td class="ColumnBackground" colspan="2">&nbsp;<bean:write property="claimdetail.eligibleClaimAmt" name="cpTcDetailsForm"/></td> -->
             </tr>
                </logic:equal>
			
            
             
              
      <logic:iterate property="claimdetail.cgpanDetails" name="cpTcDetailsForm" id="object">
			<%
			java.util.HashMap hashmap = (java.util.HashMap)object;
       String cgpan = (String)hashmap.get(ClaimConstants.CLM_CGPAN);
	   
	   // out.println("cgpan"+cgpan); 
      // String panType = cgpan.substring(11,13);  changed by koteswar
	   
	    // String panType = cgpan.substring(13,15);
		 
		  String panType = cgpan.substring(cgpan.length()-2);
	   // out.println("panType"+panType);
  					//	clmAppliedAmnt = clmSummaryDtl.getAmount();
            if(panType.equals(ClaimConstants.CGPAN_TC_LOAN_TYPE)){
                 tcServiceFee = tcServiceFee+((Double)hashmap.get(ClaimConstants.CLM_TOTAL_SERVICE_FEE)).doubleValue();
              //   totalServiceFee = totalServiceFee+tcServiceFee;
                 // out.println("tcServiceFee1"+tcServiceFee);
             }
              if(panType.equals(ClaimConstants.CGPAN_WC_LOAN_TYPE)){
                  wcServiceFee = wcServiceFee+((Double)hashmap.get(ClaimConstants.CLM_TOTAL_SERVICE_FEE)).doubleValue();
               //    out.println("wcServiceFee1"+wcServiceFee);
              //   totalServiceFee = totalServiceFee+wcServiceFee;
                }
                 totalServiceFee = tcServiceFee+wcServiceFee;
				
				 
				 
				 
				 
				// String panType = cgpan.substring(cgpan.length()-2);
	  //  out.println("panType"+panType);
  					//	clmAppliedAmnt = clmSummaryDtl.getAmount();
            if(panType.equals(ClaimConstants.CGPAN_TC_LOAN_TYPE)){
                 tcRefundFee = tcRefundFee+((Double)hashmap.get(ClaimConstants.CLM_TOTAL_SERVICE_FEE_FOR_ONE_YEAR)).doubleValue();
              //   totalServiceFee = totalServiceFee+tcServiceFee;
                 // out.println("tcServiceFee1"+tcServiceFee);
             }
              if(panType.equals(ClaimConstants.CGPAN_WC_LOAN_TYPE)){
                  wcRefundFee = wcRefundFee+((Double)hashmap.get(ClaimConstants.CLM_TOTAL_SERVICE_FEE_FOR_ONE_YEAR)).doubleValue();
             //      out.println("wcServiceFee1"+wcServiceFee);
              //   totalServiceFee = totalServiceFee+wcServiceFee;
                }
                 totalRefundFee = tcRefundFee+wcRefundFee;
				
				 
				 
				 
				
				 	
				 
				 //amt=(Double)hashmap.get(ClaimConstants.CLM_TOTAL_SERVICE_FEE_FOR_ONE_YEAR_TC);
				// amt2=(Double)hashmap.get(ClaimConstants.CLM_TOTAL_SERVICE_FEE_FOR_ONE_YEAR_WC);
				
				 
				 // out.println("amt for tc"+amt);
				  
				   //out.println("tcServiceFee2"+tcServiceFee);
				 
				 
				// out.println("tcServiceFee2"+tcServiceFee);
				 
				 // out.println("totalServiceFee2"+totalServiceFee);
				 
                claimForm.setAsfDeductableforTC(tcServiceFee);
                claimForm.setAsfDeductableforWC(wcServiceFee);
                claimForm.setServicefee(Double.toString(totalServiceFee));
		//claimForm.setServiceFeeForOneYearDiff(Double.toString(tcRefundFee));
		
	//	claimForm.setServiceFeeForOneYearDiffforTC(Double.toString(tcRefundFee));
	//	claimForm.setServiceFeeForOneYearDiffforWC(Double.toString(wcRefundFee));
	//	claimForm.setTotalServiceFeeRefund(Double.toString(totalRefundFee));
      %>
       </logic:iterate>
	   
	   
	 
	   
	   
       <logic:equal property="isClaimProceedings" value="Y" name="cpTcDetailsForm">	
       <tr>
		   <td class="ColumnBackground" colspan="2">&nbsp;Amount Deductable from MLI, if any</td>
           <td class="ColumnBackground" colspan="2">&nbsp;
		   <html:text property="asfDeductableforTC" name="cpTcDetailsForm" onchange="calltotalAmtNew(tci,wci,microFlag,womenOwned,nerFlag,schemeName)"   maxlength="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
           <td class="ColumnBackground" colspan="2">&nbsp;
		   <html:text property="asfDeductableforWC" name="cpTcDetailsForm"  onchange="calltotalAmtNew(tci,wci,microFlag,womenOwned,nerFlag,schemeName)"  maxlength="10" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
           <td class="ColumnBackground" colspan="2" id="servicefee">&nbsp;
		   <html:text name="cpTcDetailsForm" property="servicefee"/></td>
      </tr>
				<%                
                String tcInterestChargeForThisBorrower = "tcInterestCharge("+bid+com.cgtsi.claim.ClaimConstants.CLM_AS_ON_NPA+")";
                %>
              <!--    
			  <%
                String tcprncplrecafternpa = "tcprincipal("+bid+com.cgtsi.claim.ClaimConstants.CLM_RECOVERIES_AFTER_NPA+")";
                String tcintrstchrgsrecafternpa = "tcInterestCharge("+bid+com.cgtsi.claim.ClaimConstants.CLM_RECOVERIES_AFTER_NPA+")";
                %>
             --> 
		

        <tr> 
		   <td colspan="10" class="SubHeading">&nbsp; <!-- <bean:message key="cpclmsummary"/> --></td>
                </tr>
                <%
                String clmAppliedStr = claimForm.getAmountclaimed();
                %>
	
		<html:hidden property="clmRefDtlSet" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_YES_FLAG%>"/>
		</logic:equal>
		
  
       <logic:equal property="isClaimProceedings" value="Y" name="cpTcDetailsForm">	
		<tr>
			<td class="ColumnBackground" colspan="2">&nbsp;Amount Refundable , if any</td>
           <td class="ColumnBackground" colspan="2" id="serviceFeeForOneYearDiffforTC">&nbsp;
		  <html:text name="cpTcDetailsForm" property="serviceFeeForOneYearDiffforTC" onchange="calTotRefund();" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
		  <td class="ColumnBackground" colspan="2" id="serviceFeeForOneYearDiffforWC">&nbsp;
		  <html:text name="cpTcDetailsForm" property="serviceFeeForOneYearDiffforWC" onchange="calTotRefund();" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
			<td class="ColumnBackground" colspan="2" id="totalServiceFeeRefund">&nbsp;<html:text name="cpTcDetailsForm" property="totalServiceFeeRefund"/></td>
		</tr>
	    </logic:equal>
		
		<logic:equal property="isClaimProceedings" value="Y" name="cpTcDetailsForm">	
		<tr><td class="ColumnBackground" colspan="4">&nbsp;Is Refund applicable</td>
           
          <td class="ColumnBackground" colspan="4" id="serviceFeeForOneYearDiffforTC">&nbsp;
				<html:checkbox name="cpTcDetailsForm" property="refundFlag" onclick="if(this.checked){addRefundFee();}else{addServiceFee();}"/>
		  </td>
		</tr>
		
		<tr> 
		  <td class="ColumnBackground" colspan="6"> <div align="left">&nbsp;<bean:message key="cpamntpayablenow"/></div></td>
		  <% 
			clmDtl.setTotalAmtPayNow(Double.toString(totalFirstInstalment-totalServiceFee));
			claimForm.setTotalAmntPayableNow(Double.toString(totalFirstInstalment-totalServiceFee));
			
		  %>
		<td class="TableData" colspan="2" id="totalAmntPayableNow"> <div align="left">&nbsp;
		<html:text property="totalAmntPayableNow" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />  
		</div></td>
		  <td>
				<input type="hidden" name="proceedingFlag" id="proceedingFlag" value="Y"/>
		   </td>
		</tr>
	</logic:equal>
	<logic:notEqual property="isClaimProceedings" value="Y" name="cpTcDetailsForm">	
		   <tr><td>
				<input type="hidden" name="proceedingFlag" id="proceedingFlag" value="N"/>
		   </td></tr>
		</logic:notEqual>	
   
   
   	<html:hidden property="clmRefDtlSet" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_YES_FLAG%>"/>
	  
    <tr><td><p>
            &nbsp;
          </p>&nbsp;</td></tr>
    
    
     <logic:equal  property="isClaimProceedings" value="Y" name="cpTcDetailsForm">
      <%  clmDtl.setStandardRemarks(" The MLI has adhered to all stipulated conditions such as classified the account as NPA, filed suit with the competent authority and the lock-in period is also over. As all the other  terms and conditions are fulfilled by the MLI, it is proposed that we may consider the claim for settlement."); %>	
         <tr><td class="TableData">Comments&nbsp;</td>
             <td align="left" valign="top" class="TableData" colspan="7"><font size="2"> <bean:write property="claimdetail.standardRemarks" name="cpTcDetailsForm"/></font></td>
          </tr>
     </logic:equal>
    
	
	

    <!-- <logic:equal  property="isClaimProceedings" value="N" name="cpTcDetailsForm">	
            
           clmDtl.setStandardRemarks("");   
     </logic:equal> -->
             
   
    
    <tr><td><p>
            &nbsp;
          </p>&nbsp;</td></tr>
    <tr><td class="TableData"><bean:message key="remarks"/>&nbsp;</td>
   <td align="left" valign="top" class="TableData" colspan="7">
      <html:textarea cols="100" rows="9" style="font-size:14;"  property="claimdetail.comments" name="cpTcDetailsForm"></html:textarea>	
 
  <!--  <html:textarea cols="100" rows="8"  property="claimdetail.comments" name="cpTcDetailsForm"/>		-->
   <!-- <html:text property="claimdetail.comments"  size="100" alt="comments" name="cpTcDetailsForm"/> -->
	 </td>
	 
	 
    </tr>
     
   <%-- <p>&nbsp;</p>
    <tr><td align="left" valign="top">&nbsp;</td></tr>
    <tr><td align="left" valign="top">&nbsp;</td></tr>
    <tr><td align="left" valign="top">&nbsp;</td></tr>
    <tr><td align="left" valign="top">&nbsp;</td></tr>
    <tr><td align="left" valign="top">&nbsp;</td></tr>    
    <tr><td align="left" valign="top"><%=userId%></td></tr> --%>
              </table>
           </td>
        </tr>
      </table></td>
    <td background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td align="right" valign="bottom"><img src="images/TableLeftBottom.gif" alt="" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center">          
          <A href="javascript:submitForm('displayClaimApprovalMod.do?method=displayClaimApprovalMod')"><img src="images/OK.gif" alt="Save" width="49" height="37" border="0"></a>
          </div>
      </div></td>
      <td align="right" valign="bottom"><img src="images/TableRightBottom.gif" alt="" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>
