      <%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>
<%@ page import="com.cgtsi.claim.ClaimDetail"%>
<%@ page import = "com.cgtsi.claim.ClaimSummaryDtls"%>
<%@ page import = "com.cgtsi.claim.RecoveryDetails"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.cgtsi.util.DBConnection"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="java.sql.Statement"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<% session.setAttribute("CurrentPage","displayClaimRefNumberDtls.do?method=displayClaimRefNumberDtls");%>
<%
      ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm") ;
          
    String falgforCasesafet=claimForm.getFalgforCasesafet();
    
     String nerfalg=claimForm.getNewNERFlag();
     String womenOprator=claimForm.getNewChipParmoGender();
 %>
   <script language = "JavaScript">
                
                var nerfalg = '<%=nerfalg%>';
                var womenOprator = '<%=womenOprator%>';
                
                </script>
              
                </tr>
               
<script>

  function calTotal(tableID)
    {
     //alert ("The id is :-->"+tableID)
     var table = document.getElementById(tableID);  
     var rowCount = table.rows.length; 
//    alert ("The nerfalg Are :->"+nerfalg);
//    alert ("The womenOprator Are :->"+womenOprator);
   
     var  totAmtASNPA=0;
     var totRecAmtNPA=0;
     var totNetOut=0;
     var minval=0;
     var netout=0;
     var falgforCasesafet="<%=falgforCasesafet%>";
      var gaurnteIssutot=0;
      var claimeligi=0;
      var totClaimelgi=0;
      var firstInstalAmt=0;
      var totFirstInstalAmt=0;
       var mlideduct=0;
       var totmlideduct=0;
       var paybleAmt=0;
       var totpaybleAmt=0;
     for (var i=rowCount-1;i<rowCount;i++)
     {
      var row = table.rows[i]; 
      gaurnteIssutot= row.cells[1].childNodes[0].value;
     }
     //alert ("the Total is :-->"+gaurnteIssutot);
     
     for (var i=1;i<rowCount-1;i++)
      {
       var row = table.rows[i]; 
       var gaurnteIssu= row.cells[1].childNodes[0].value;
      // alert ("Teh Gaurante Isuue Amt:-->"+gaurnteIssu);
       var npaOutAmt = row.cells[2].childNodes[0].value;
       var recoAmt = row.cells[3].childNodes[0].value;
       
        
       minval =Math.min(parseFloat(row.cells[1].childNodes[0].value), parseFloat(row.cells[2].childNodes[0].value))
        //alert ("the min is :-->"+minval)
         netout = parseFloat(minval)- parseFloat(recoAmt);
        //alert("the Net out--->"+netout);
         row.cells[4].childNodes[0].value=netout;
        
        
         //calculating calim elgible amount 
                         if((gaurnteIssutot<=500000)&&(microFlag=="Y")){
                
                      if (falgforCasesafet=="Y")
                          {
                          claimeligi = Math.round(netout * 0.85);
                          }
                          else 
                          {
                        claimeligi = Math.round(netout * 0.80);
                          }
                  }
                /*else if((gaurnteIssutot <=500000)&&(microFlag=="N")){
                 if ( falgforCasesafet =="Y" && (nerfalg=="Y" || womenOprator=="F") ){
                          
                claimeligi = Math.round(netout * 0.80);          
                          }
                          else{
                claimeligi = Math.round(netout * 0.75);
                }
                }*/
                else if(gaurnteIssutot <= 5000000 &&(womenOprator=="F" || nerfalg=="Y")){
               
                claimeligi = Math.round(netout * 0.80);
               }
               else if(gaurnteIssutot <=500000 &&(microFlag=="N")){
               claimeligi = Math.round(tctempvalue * 0.75);
                 }
                
               else
                  {
                    claimeligi = Math.round(netout * 0.75);
                   }
                
               row.cells[6].childNodes[0].value=claimeligi; 
               
         //calculating calim elgible amount over 
       //calculating first Inslament 
       firstInstalAmt = Math.round(claimeligi  * 0.75);
       row.cells[7].childNodes[0].value=firstInstalAmt; 
       //calculating First Installment Over 
       //calculating amount payble now  amtPaybleNow=firstInstallmentAmt-AmtDeductedByMLIService;
         
            mlideduct = row.cells[8].childNodes[0].value;
           
       //calculating amount payble now  over amtPaybleNow=firstInstallmentAmt-AmtDeductedByMLIService;
       // payable amount 
       // paybleAmt=parseFloat(firstInstalAmt)- parseFloat(mlideduct);
//       alert ("first inayll-->"+parseFloat(row.cells[7].childNodes[0].value));
//       alert ("MLI Deduct-->"+parseFloat(row.cells[8].childNodes[0].value));
//       alert ("pyaout-->"+row.cells[9].childNodes[0].value);
//       
       
       paybleAmt=parseFloat(row.cells[7].childNodes[0].value)- parseFloat(row.cells[8].childNodes[0].value);
     //  alert ("payout after---> "+paybleAmt);
       // paybleAmt=firstInstalAmt- mlideduct;
         row.cells[9].childNodes[0].value=paybleAmt;
       //  alert ("payout valu=>"+ row.cells[9].childNodes[0].value);
         
       //payble amount over 
       
       
       
      if (!((isNaN(parseFloat(npaOutAmt))) && npaOutAmt!="")
      ||  ((isNaN(parseFloat(recoAmt))) && recoAmt!="")
      ||  ((isNaN(parseFloat(netout))) && netout!="")
      ||  ((isNaN(parseFloat(claimeligi))) && claimeligi!="") 
      ||  ((isNaN(parseFloat(firstInstalAmt))) && firstInstalAmt!="")
      ||  ((isNaN(parseFloat(mlideduct))) && mlideduct!="")
      ||  ((isNaN(parseFloat(paybleAmt))) && paybleAmt!=""))
      {
         totAmtASNPA+=parseFloat(npaOutAmt);
         totRecAmtNPA+=parseFloat(recoAmt);
         totNetOut+=parseFloat(netout);
         totClaimelgi+=parseFloat(claimeligi);
         totFirstInstalAmt+=parseFloat(firstInstalAmt);
         totmlideduct+=parseFloat(mlideduct);
         totpaybleAmt+=parseFloat(paybleAmt);
      }
      
      }
        var row1 = table.rows[rowCount-1]; 
   
       row1.cells[2].childNodes[0].value=totAmtASNPA;
       row1.cells[3].childNodes[0].value=totRecAmtNPA;
       row1.cells[4].childNodes[0].value=totNetOut;
       row1.cells[6].childNodes[0].value=totClaimelgi;
       row1.cells[7].childNodes[0].value=totFirstInstalAmt;
       row1.cells[8].childNodes[0].value=totmlideduct;
       row1.cells[9].childNodes[0].value=totpaybleAmt;
       
       
       
    }
function calltotalServiceFee()
{
//alert("calltotalServiceFee entered");
var asfDeductableforTC=document.getElementById('asfDeductableforTC').innerHTML;;
alert(asfDeductableforTC);
var asfDeductableforWC=document.getElementById('asfDeductableforWC').innerHTML;;
alert(asfDeductableforWC);
alert("calltotalServiceFee exited");
}

function calculatePsTotal()
{
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
	servicefee.innerHTML=tempservicefee; 
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
	totalRecovery.innerHTML=tempRecovery; 
  //alert(servicefee.value);
}

function calltotalAmt(tci,wci)
{
alert("calltotalAmt entered");
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

var tcFirstInstallment = document.getElementById('tcFirstInstallment');
var wcFirstInstallment = document.getElementById('wcFirstInstallment');
var totalFirstInstalment = document.getElementById('totalFirstInstalment');

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
wctempvalue = Math.min(wci,totalWCOSAmountAsOnNPA.value) - wcrecovery.value;
totaltempvalue = tctempvalue+wctempvalue;


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

tcNetOutstanding.innerHTML=tctempvalue; 
wcNetOutstanding.innerHTML=wctempvalue;
totalNetOutstanding.innerHTML=totaltempvalue;
tcClaimEligibleAmt.innerHTML=tcclaimeligble;
wcClaimEligibleAmt.innerHTML=wcclaimeligible;
totalClaimEligibleAmt.innerHTML=totalclaimeligible;
tcFirstInstallment.innerHTML=tcFirstInst;
wcFirstInstallment.innerHTML=wcFirstInst;
totalFirstInstalment.innerHTML=totalFirstInst;
totalAmntPayableNow.innerHTML=totalAmtPayable;
}

function calltotalAmtNew(tci,wci,microFlag)
{
var falgforCasesafet="<%=falgforCasesafet%>";
//alert("falg for checvking case after 2009 :-->"+falgforCasesafet);
//alert ("falg :-->"+falgforCasesafet);
//alert(tci);
//alert(wci);
//alert(microFlag);
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
var wcFirstInstallment = document.getElementById('wcFirstInstallment');
var totalFirstInstalment = document.getElementById('totalFirstInstalment');

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
wctempvalue = Math.min(wci,totalWCOSAmountAsOnNPA.value) - wcrecovery.value;
totaltempvalue = tctempvalue+wctempvalue;


//alert(tctempvalue);
//alert(wctempvalue);
//alert(totaltempvalue);
if((tci+wci<=500000)&&(microFlag=="Y")){

      if (falgforCasesafet=="Y")
          {
          tcclaimeligble = Math.round(tctempvalue * 0.85);
          }
          else 
          {
        tcclaimeligble = Math.round(tctempvalue * 0.80);
          }

  }
else if((tci+wci<=500000)&&(microFlag=="N")){
tcclaimeligble = Math.round(tctempvalue * 0.75);
}
else 
{
tcclaimeligble = Math.round(tctempvalue * 0.75);
}
//alert(tcclaimeligble);
if((tci+wci<=500000)&&(microFlag=="Y")){
 
   if (falgforCasesafet=="Y")
          {
        wcclaimeligible = Math.round(wctempvalue * 0.85);
          }
          else 
          {
         wcclaimeligible = Math.round(wctempvalue * 0.80);
          }

} else if((tci+wci<=500000)&&(microFlag=="N")){
wcclaimeligible = Math.round(wctempvalue * 0.75);
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

tcNetOutstanding.innerHTML=tctempvalue; 
wcNetOutstanding.innerHTML=wctempvalue;
totalNetOutstanding.innerHTML=totaltempvalue;
tcClaimEligibleAmt.innerHTML=tcclaimeligble;
wcClaimEligibleAmt.innerHTML=wcclaimeligible;
totalClaimEligibleAmt.innerHTML=totalclaimeligible;
tcFirstInstallment.innerHTML=tcFirstInst;
wcFirstInstallment.innerHTML=wcFirstInst;
totalFirstInstalment.innerHTML=totalFirstInst;
totalAmntPayableNow.innerHTML=totalAmtPayable;
}

function enableClaimAmounts()
{
var totalFirstInstalment;
//alert("enableClaimAmounts entered");
if(document.forms[0].isClaimProceedings[0].checked){
 alert("Claim To be Processed");
 calculatePsTotal();
 calltotalAmtNew(tci,wci,microFlag);
} else {
   alert("Claim To be Rejected");   
   document.forms[0].tcFirstInstallment.value=0;
   document.forms[0].wcFirstInstallment.value=0;     
   document.forms[0].totalFirstInstalment.value=0;  
   document.forms[0].asfDeductableforTC.value=0;
   document.forms[0].asfDeductableforWC.value=0;   
   document.forms[0].servicefee.value=0;
   document.forms[0].totalAmntPayableNow.value=0;
      }
}

</script>
<% 
// System.out.println("The Insiallly The value is --->"+falgforCasesafet);
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
double rate = 0.75;
String microFlag = "N";
 
%>

<html:errors />
<body onload ="javascript:calculateAmountPayable()">
<html:form action="displayClaimApprovalMod.do?method=displayClaimApprovalMod" method="POST" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">    
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="80%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
    <td align="right" valign="bottom"><img src="images/TableLeftTop.gif" height="31" width="20"></td>
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
                        <td height="20" class="Heading">&nbsp;<bean:message key="claimdetails"/></td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
		<%
		       //ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm") ;
        //   System.out.println("claimForm.getIsClaimProceedings():"+claimForm.getIsClaimProceedings());
		       clmDtl = claimForm.getClaimdetail();
		       bid = clmDtl.getBorrowerId();
             //      System.out.println("Borrower Id of the Claim Application :" +bid);
		      //  System.out.println("Claim Ref Number of the Claim Application :" +clmDtl.getClaimRefNum());
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
			<td><br></tr>
		</tr>								
		
                <tr> 
                  <td colspan="4" class="SubHeading">&nbsp;<bean:message key="cgpandetails"/></td>
                </tr>
				<tr>
                  <td colspan="8" class="SubHeading">
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                      <tr>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="cgpan"/>
                        </td>
                        <td class="ColumnBackground" colspan="2"><div align="center">&nbsp;<bean:message key="creditsanctionedbymli"/>
                        </td>
                        <td class="ColumnBackground" colspan="2"><div align="center">&nbsp;<bean:message key="cpguaranteeissued"/>
                        </td>
                        <td class="ColumnBackground" rowspan="2"><div align="center" > &nbsp;<bean:message key="cpamntdisbursedfortc"/>
                        </td>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="dtoflastdsbrsmntfortc"/>
                        </td>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="dtofreleaseofwc"/>
			</td>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="cpguaranteestartdate"/></div></td>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="cplockinperiodendson"/></div></td>
			
		<!--	<td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="cpservicefee"/></td>
			<td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="cpdtofpaymentofservicefee"/></td> -->
			</tr>			
			<tr class="ColumnBackground">
			<td div align="center">&nbsp;<bean:message key="cptermcredit"/></div></td>
			<td div align="center">&nbsp;<bean:message key="cpworkingcapital"/></div></td>			
			<td div align="center">&nbsp;<bean:message key="cptermcredit"/></div></td>
			<td div align="center">&nbsp;<bean:message key="cpworkingcapital"/></div></td>			
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
			 //   System.out.println("tcApprovedAmnt:"+tcApprovedAmnt);
          tcIssued = tcIssued+tcApprovedAmnt;
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
			<td><div align="center"><%=totalWCSanctionedAmnt%></div></td>
			<td><div align="center"><%=tcApprovedAmnt%></div></td>
			<td><div align="center"><%=wcApprovedAmnt%></div></td>
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
			<td><br></tr>
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
                 <!--  <TD align="left" class="ColumnBackground"> 
									&nbsp;Expiry Dt.</TD> -->
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
               <!--   <TD  align="left" valign="top" class="ColumnBackground1"> <%=formatedDate6%></td> -->
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
		  <td class="ColumnBackground" colspan="1"> <div align="left">&nbsp;NPA Date:</div></td>
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
    <td class="ColumnBackground" colspan="1">&nbsp;Details of Legal Proceedings.</td>
	  <td class="TableData" colspan="8"><div align="left"><% java.util.Date legalDate = clmDtl.getLegalFilingDate();
                          String legalSuitNo = clmDtl.getLegalSuitNumber();
                          String legalForum = clmDtl.getLegalForum();
                          String legalForumName = clmDtl.getLegalForumName();
                          String legalLocation = clmDtl.getLegalLocation();
                          String formatedDate8 = null;
                          
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
    <td class="ColumnBackground">&nbsp;Gender Of Chief Promoter: </td>
 
    <td  class="TableData" colspan="8">&nbsp <%=%><bean:write property="newChipParmoGender" name="cpTcDetailsForm"/>
			
							
    </td>
	  </tr>
   <tr>
    <td class="ColumnBackground">&nbsp;Borrower State Name: </td>
   <td  class="TableData" colspan="8">&nbsp <%=%><bean:write property="newborowerState" name="cpTcDetailsForm"/>
   </td>
	  </tr>
     <tr>
    <td class="ColumnBackground">&nbsp;Type Of Activity: </td>
   <td  class="TableData" colspan="8">&nbsp <%=%><bean:write property="newTypeActivity" name="cpTcDetailsForm"/>
				
    </td>
	  </tr>
     <tr>
    <td class="ColumnBackground">&nbsp;Scheme Name: </td>
    <td  class="TableData" colspan="8">&nbsp <%=%><bean:write property="newSchemName" name="cpTcDetailsForm"/>
    </td>
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
		 
               
                  
                  
                </td>  
    </tr>
     
		</tr>
                 <!-- jai code-->
             
         
                
   <tr>
   <td class="TableData" colspan="8">
  
   <br>
    <logic:equal property="claimapplication.microCategory" value="Y" name="cpTcDetailsForm">	
                 <%
                    microFlag="Y";
                   
              //      System.out.println("Micro Flag:"+microFlag);
                  %>
                </logic:equal>
             <%  double guarnteIssueAmtTotal=0.0;
             String percentAmt="";
             %>
              <logic:iterate id="object2" name="cpTcDetailsForm" property="forCGPANWiseDataArray" indexId="index">
                     <%
                     ClaimActionForm claimFirstInstallFoRTot1=(ClaimActionForm)object2;
                      guarnteIssueAmtTotal=guarnteIssueAmtTotal + claimFirstInstallFoRTot1.getNewGuarnteeIssueAmt(); %>
                      </logic:iterate>
                <%
                
                if((guarnteIssueAmtTotal)<=500000 && microFlag.equals("Y")){
                                     rate = 0.80;
                                     if (falgforCasesafet.equals("Y"))
                                        {
                                           rate=0.85;
                                           percentAmt="85%";
                                        }else{
                                   percentAmt="80%";
                                  }
                                  }
                     /*  else if((guarnteIssueAmtTotal)<=500000 && microFlag.equals("N")){
                              percentAmt="75%";
                           }
                  if((guarnteIssueAmtTotal)>500000){
                  percentAmt="75%";
                 }*/
                 
                 else if( guarnteIssueAmtTotal <= 5000000 && (womenOprator.equals("F") || nerfalg.equals("Y")))
                 {
                  percentAmt="80%";
                 }
                  else if(guarnteIssueAmtTotal <=500000 && microFlag.equals("N")){
                  percentAmt="75%";
                 }
               else
                  {
                  percentAmt="75%";
                   }
                %>
               
              <style>p.page { page-break-after: always; }</style>
                <p class="page"></p>
                  <TABLE width="100%" border="0" cellspacing="1"
                         cellpadding="0"  >
                         <TR>
                  <td colspan="3" class="SubHeading">&nbsp;<!--<bean:message key="cpnpasummry"/>-->Claim Settlement Details</td>
                  </TR>
                  </TABLE>
    <table width="100%" border="0" cellspacing="1"
                         cellpadding="0" id="dataTable" >
       
                    <tr class="TableData">
                      <td valign="middle" class="HeadingBg">
                        <div align="center">
                          <strong>&nbsp;CGPAN<br/>
                             </strong>
                        </div>
                      </td>
                                        <td valign="middle" class="HeadingBg" >
                        <div align="center">
                          <strong>Guarantee Issued <br/>
                             </strong>
                        </div>
                      </td>
					  
                         <td valign="middle" class="HeadingBg" >
                         <div align="center">
                          <strong>Amount Outstanding as on date of NPA <br/>
                             </strong>
                         </div>
                         </td>
                         <td valign="middle" class="HeadingBg" >
                         <div align="center">
                          <strong>Amount Recovered after NPA <br/>
                             </strong>
                         </div>
                         </td>
                         <td valign="middle" class="HeadingBg">
                         <div align="center">
                          <strong>Net Outstanding (Amount in default admissible by CGTMSE) <br/>
                             </strong>
                         </div>
                         </td>
 						 <td valign="middle" class="HeadingBg" width="0%">
            
            <!--             <div align="center">
                          <strong>Amount Claimed by MLI 
                             </strong>
                         </div> -->
                         </td>
                          <logic:equal  property="isClaimProceedings" value="Y" name="cpTcDetailsForm">	
                <td  valign="middle" class="HeadingBg">
                 <div align="center">
                <bean:message key="clmeligibilityamnt"/>
                </div>
                </td>
                
                 <td valign="middle" class="HeadingBg" >
                         <div align="center">
                          <strong> Amount Payable in First Installment <br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg">
                         <div align="center">
                          <strong>Amount Deductable from MLI, if any<br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" >
                         <div align="center">
                          <strong>Amount Payable Now<br/>
                             </strong>
                         </div>
                         </td>
                 </logic:equal>
						<!-- <td valign="middle" class="HeadingBg">
                         <div align="center">
                          <strong>Claim Eligibility Amount <%=percentAmt%><br/>
                             </strong>
                         </div>
                         </td> -->
                          <logic:notEqual  property="isClaimProceedings" value="Y" name="cpTcDetailsForm">	
                <td valign="middle" class="HeadingBg">
                <div align="center">
                <font color="red">Claim Amount to be Rejected&nbsp;</font>
                </div>
                </td>
                 </logic:notEqual>
             
						
                        
									
                    </tr>
                    <%  
                  
                    double totaloutsnadingasOnNPA=0.0;
                    double totalRecoveryAfterNPA=0.0;
                    double totalNetOutStanding=0.0;
                    double totalClaimAmtByMLI=0.0;
                    double totalNewClaimEligibleAmt=0.0;
                    double totalAmtpayInFirstInstal=0.0;
                    double totalAmountDeductedByMli=0.0;
                    double totalAmtPaybleNow=0.0;
                    %>
                     
                    
                      
                     <%System.out.println("the Gaurntee issued amount Total is :---->"+guarnteIssueAmtTotal);
                    //cpTcDetailsForm.getNewNERFlag()
                    
                                         
                     %>
                     <logic:iterate id="object1" name="cpTcDetailsForm" property="forCGPANWiseDataArray" indexId="index">
                      <%
                                
                                 ClaimActionForm claimFirstInstallFoRTot=(ClaimActionForm)object1; 
                                
                                 
                                totaloutsnadingasOnNPA=totaloutsnadingasOnNPA +claimFirstInstallFoRTot.getNewAmtOutstandAsOnNPA();
                              totalRecoveryAfterNPA=totalRecoveryAfterNPA + claimFirstInstallFoRTot.getNewAmtRecoverAfterNPA();
                              totalNetOutStanding=totalNetOutStanding + (Math.min( claimFirstInstallFoRTot.getNewGuarnteeIssueAmt(),claimFirstInstallFoRTot.getNewAmtOutstandAsOnNPA())-(claimFirstInstallFoRTot.getNewAmtRecoverAfterNPA()));
                             totalClaimAmtByMLI=totalClaimAmtByMLI + claimFirstInstallFoRTot.getNewAmtClaimByMli();
                             //calclating calimeligibal Amt
                                        //  System.out.println("The falgforCasesafet is JJJJJJJJJJJ:----->"+falgforCasesafet);  
                                      //       System.out.println("The microFlag is JJJJJJJJJJJ:----->"+microFlag); 
                                      //       System.out.println("The nerfalg is JJJJJJJJJJJ:----->"+nerfalg); 
                                     //        System.out.println("The womenOprator is JJJJJJJJJJJ:----->"+womenOprator); 
                                            
                                 if((guarnteIssueAmtTotal)<=500000 && microFlag.equals("Y")){
                                     rate = 0.80;
                                    if (falgforCasesafet.equals("Y"))
                                        {
                                           rate=0.85;
                                            totalNewClaimEligibleAmt =totalNewClaimEligibleAmt +  Math.round((Math.min( claimFirstInstallFoRTot.getNewGuarnteeIssueAmt(),claimFirstInstallFoRTot.getNewAmtOutstandAsOnNPA())-(claimFirstInstallFoRTot.getNewAmtRecoverAfterNPA())) * rate);
                                      //   System.out.println("The total ClaimEligible amount is :---->"+totalNewClaimEligibleAmt);
                                           }else{
                                   //jai 2
                                     totalNewClaimEligibleAmt =totalNewClaimEligibleAmt +  Math.round((Math.min( claimFirstInstallFoRTot.getNewGuarnteeIssueAmt(),claimFirstInstallFoRTot.getNewAmtOutstandAsOnNPA())-(claimFirstInstallFoRTot.getNewAmtRecoverAfterNPA())) * rate);
                                  
                                    }
                                  }
                    /*   else if((guarnteIssueAmtTotal)<= 5000000 && microFlag.equals("N")){
                          //code for woment& ner 
                          //nerfalg
                          //womenOprator
                          if ( falgforCasesafet.equals("Y") && (nerfalg.equals("Y") || womenOprator.equals("F")) ){
                          rate=0.80;
                           totalNewClaimEligibleAmt =totalNewClaimEligibleAmt +  Math.round((Math.min( claimFirstInstallFoRTot.getNewGuarnteeIssueAmt(),claimFirstInstallFoRTot.getNewAmtOutstandAsOnNPA())-(claimFirstInstallFoRTot.getNewAmtRecoverAfterNPA())) * rate);
                            }
                       else {
                            totalNewClaimEligibleAmt =totalNewClaimEligibleAmt +  Math.round((Math.min( claimFirstInstallFoRTot.getNewGuarnteeIssueAmt(),claimFirstInstallFoRTot.getNewAmtOutstandAsOnNPA())-(claimFirstInstallFoRTot.getNewAmtRecoverAfterNPA())) * rate);
                      }
                   }
                   */
               else  if((guarnteIssueAmtTotal) <= 5000000 && (womenOprator.equals("F")|| nerfalg.equals("Y"))){
               
               rate=0.80;
               totalNewClaimEligibleAmt = totalNewClaimEligibleAmt +  Math.round((Math.min( claimFirstInstallFoRTot.getNewGuarnteeIssueAmt(),claimFirstInstallFoRTot.getNewAmtOutstandAsOnNPA())-(claimFirstInstallFoRTot.getNewAmtRecoverAfterNPA())) * rate);
               }
                 else if (guarnteIssueAmtTotal <= 500000  && microFlag.equals("N"))
                 {
                 rate=0.75;
                  totalNewClaimEligibleAmt = totalNewClaimEligibleAmt +  Math.round((Math.min( claimFirstInstallFoRTot.getNewGuarnteeIssueAmt(),claimFirstInstallFoRTot.getNewAmtOutstandAsOnNPA())-(claimFirstInstallFoRTot.getNewAmtRecoverAfterNPA())) * rate);
                 }
                  else{
                  totalNewClaimEligibleAmt = totalNewClaimEligibleAmt +  Math.round((Math.min( claimFirstInstallFoRTot.getNewGuarnteeIssueAmt(),claimFirstInstallFoRTot.getNewAmtOutstandAsOnNPA())-(claimFirstInstallFoRTot.getNewAmtRecoverAfterNPA())) * rate);
                 }
                 
                 
                 //calculating total amount deducted by MLI
                 totalAmountDeductedByMli=totalAmountDeductedByMli + claimFirstInstallFoRTot.getNewAmtDeductedFromMli();
                 
                             
                              %>
                     </logic:iterate>
                    <% 
                    //calculating amount payable in first Installment
                    totalAmtpayInFirstInstal = Math.round(totalNewClaimEligibleAmt  * 0.75);
                    // total amount payble now 
                       totalAmtPaybleNow=totalAmtpayInFirstInstal-totalAmountDeductedByMli;
                    
                  //  System.out.println("The Gaurantee Total From JSP page is :--->"+guarnteIssueAmtTotal);
                 //   System.out.println("The Total Net outstanong is :--->"+totalNetOutStanding);
                    %>
					    <logic:iterate id="object" name="cpTcDetailsForm" property="forCGPANWiseDataArray" indexId="index">
                                
                                 <%
                                 ClaimActionForm claimFirstInstall=(ClaimActionForm)object; 
                                
                              //   System.out.println("the value of objctes is jsp is :-->"+claimFirstInstall);
                              //   System.out.println("the amount retrive is :---> "+claimFirstInstall.getNewAmtClaimByMli());
                              double guarnteIssueAmt= claimFirstInstall.getNewGuarnteeIssueAmt();
                              double outstandingASOnNPAAmt= claimFirstInstall.getNewAmtOutstandAsOnNPA();
                              double recovryafterNPAAmt = claimFirstInstall.getNewAmtRecoverAfterNPA();
                                double netOutstandingAmt= Math.min(guarnteIssueAmt,outstandingASOnNPAAmt)-(recovryafterNPAAmt);
                                 //for calculating claim eligible amount
                                
                                double claimEligibleAmt=0.0;
                                double firstInstallmentAmt=0.0;
                                   double AmtDeductedByMLIService=0.0;
                                   double amtPaybleNow=0.0;
                                  if((guarnteIssueAmtTotal)<=500000 && microFlag.equals("Y")){
                                     rate = 0.80;
                             //jai  1
                                     if (falgforCasesafet.equals("Y"))
                                        {
                                           rate=0.85;
                                            claimEligibleAmt =  Math.round(netOutstandingAmt * rate);
                                          
                                           }else{
                                   //jai 2
                                     claimEligibleAmt =  Math.round(netOutstandingAmt * rate);
                                    }
                                  }
                               /*    else if((guarnteIssueAmtTotal)<=500000 && microFlag.equals("N")){
                                   
                                    //nerfalg
                          //womenOprator
                          System.out.println("inside not micro flag...!");
                          if ( falgforCasesafet.equals("Y") && (nerfalg.equals("Y") || womenOprator.equals("F")) ){
                          System.out.println("inside not YYF condtion...!");
                          rate=0.80;
                            claimEligibleAmt =  Math.round(netOutstandingAmt * rate);
                            }
                       else {
                       System.out.println("outside YYF Condition...!");
                           claimEligibleAmt =  Math.round(netOutstandingAmt * rate);
                      }
                                   
               // claimEligibleAmt =  Math.round(netOutstandingAmt * rate);
                  
               }
               */
            else  if((guarnteIssueAmtTotal) <= 5000000 && (womenOprator.equals("F") || nerfalg.equals("Y"))){
               
               rate=0.80;
                claimEligibleAmt =  Math.round(netOutstandingAmt * rate);
               }
               else  if((guarnteIssueAmtTotal) <= 500000 &&  microFlag.equals("N"))
               {
               rate=0.75;
               claimEligibleAmt =  Math.round(netOutstandingAmt * rate);
               }
               else {
                 claimEligibleAmt =  Math.round(netOutstandingAmt * rate);
               }
               
               
                firstInstallmentAmt = Math.round(claimEligibleAmt  * 0.75);
                 AmtDeductedByMLIService=claimFirstInstall.getNewAmtDeductedFromMli();
                 amtPaybleNow=firstInstallmentAmt-AmtDeductedByMLIService;
                              double recAmt=0.0;
                              double npaOutAmt=0.0;
                              double deductedByMilAmt=0.0;
                               recAmt=claimFirstInstall.getNewAmtRecoverAfterNPA();
                                 npaOutAmt=claimFirstInstall.getNewAmtOutstandAsOnNPA();
                                   deductedByMilAmt=claimFirstInstall.getNewAmtDeductedFromMli();
                                 %>
                             
                              
                                <tr class="TableData">  
                                <td width="12">
                                <%=claimFirstInstall.getNewCGPAN()%>
                                 <input size="12" type="HIDDEN" name="hidcgpan" value=" <%=claimFirstInstall.getNewCGPAN()%>"  /> 
                                 </td>
                               <td width="12">
                                <input size="12" type="HIDDEN" name="hidgaurIssue" value=" <%=claimFirstInstall.getNewGuarnteeIssueAmt()%>"  /> 
                              <%=claimFirstInstall.getNewGuarnteeIssueAmt()%>
                             
                              </td>
                              
                               <td width="5%">
                               <input size="12" type="text" name="outstandingAsOnNPA" value="<%=Math.round(npaOutAmt)%>" onkeypress="return decimalOnly(this, event,13)"   maxlength="16" onkeyup="isValidDecimal(this)" onblur="calTotal('dataTable')" /> 
                              </td>
                               <td width="5%">
                               <input size="12" type="text" name="recoverafterNPA" value="<%=Math.round(recAmt)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)"  onblur="calTotal('dataTable')"/> 
                              </td>
                               <td width="5%">
                              <input size="12" type="text" name="netOutsandingAmt" value="<%=Math.round(netOutstandingAmt)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" onblur="calTotal('dataTable')" /> 
                             
                              </td>
                               <td>
                              <!--<%=claimFirstInstall.getNewAmtClaimByMli()%>-->
                               <input type="HIDDEN" name="hidclaimbymliamt" value="<%=claimFirstInstall.getNewAmtClaimByMli()%>"  /> 
                             </td>
                               <td width="5%">
                              <input size="12" type="text" name="claimEligibleAmt" value="<%=Math.round(claimEligibleAmt)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" /> 
                              </td>
                                <logic:equal  property="isClaimProceedings" value="Y" name="cpTcDetailsForm">	
               
              
                               <td width="5%">
                              <input size="12" type="text" name="firstInstallAmt" value="<%=Math.round(firstInstallmentAmt)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" /> 
                             </td>
                               <td>
                             
                             
                              <input size="12" type="text" name="dedecutByMliIfAny" value="<%=Math.round(deductedByMilAmt)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" onblur="calTotal('dataTable')"/> 
                             
                              </td>
                               <td width="5%">
                              <input size="12"  type="text" name="paybleAmt" value="<%=Math.round(amtPaybleNow)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" readonly="readonly" /> 
                              </td>
                                 </logic:equal>
                                 <logic:equal  property="isClaimProceedings" value="N" name="cpTcDetailsForm">	
                                  <td width="5%">
                              <input size="12" type="HIDDEN" name="firstInstallAmt" value="<%=Math.round(firstInstallmentAmt)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" /> 
                             </td>
                               <td>
                             
                             
                              <input size="12" type="HIDDEN" name="dedecutByMliIfAny" value="<%=Math.round(deductedByMilAmt)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" onblur="calTotal('dataTable')"/> 
                             
                              </td>
                               <td width="5%">
                              <input size="12"  type="HIDDEN" name="paybleAmt" value="<%=Math.round(amtPaybleNow)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" readonly="readonly" /> 
                              </td>
                                 </logic:equal>
                                  
                              </tr>
		      	</logic:iterate>
                        <tr class="TableData">
                         <td width="5%">
                                        <div align="center">
                                        Total
                                        <%//=claimFirstInstall.getNewCGPAN()%>
                                        </div>
                                        </td>
                               <td width="2%">
                             <input type="HIDDEN" name="garAmt" value="<%=guarnteIssueAmtTotal%>"  /> 
                                <%=guarnteIssueAmtTotal%>
                              <%//=claimFirstInstall.getNewGuarnteeIssueAmt()%>
                             
                              </td>
                              
                               <td width="5%">
                              <input size="12" type="text" name="totaloutsnadingasOnNPA" value="<%=Math.round(totaloutsnadingasOnNPA)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                              <!--<input size="12" type="text" name="totaloutsnadingasOnNPA" value="<%=totaloutsnadingasOnNPA%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/> -->
                            
                              <%//=claimFirstInstall.getNewAmtOutstandAsOnNPA()%>
                           
                              </td>
                               <td width="5%">
                              
                               <input size="12" type="text" name="totalRecoveryAfterNPA" value="<%=Math.round(totalRecoveryAfterNPA)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                              
                              </td>
                               <td width="5%">
                               <input size="12" type="text" name="totalNetOutStanding" value="<%=Math.round(totalNetOutStanding)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                              </td>
                               <td>                                 
                              <% //=totalClaimAmtByMLI%>
                              </td>
                               <td width="5%">
                                <input size="12" type="text" name="totalNewClaimEligibleAmt" value="<%=Math.round(totalNewClaimEligibleAmt)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                              <%//=totalNewClaimEligibleAmt%>
                              </td>
                                <logic:equal  property="isClaimProceedings" value="Y" name="cpTcDetailsForm">	
                               <td width="5%">
                              
                             <input size="12" type="text" name="totalAmtpayInFirstInstal" value="<%=Math.round(totalAmtpayInFirstInstal)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                              
                              </td>
                               <td width="5%">
                               <input size="12" type="text" name="totalAmountDeductedByMli" value="<%=Math.round(totalAmountDeductedByMli)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                              </td>   
                               <td width="5%">
                      
                              
                              <input size="12" type="text" name="totalAmtPaybleNow" value="<%=Math.round(totalAmtPaybleNow)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                      
                      
                              </td>
                                </logic:equal>
                                 <logic:equal  property="isClaimProceedings" value="N" name="cpTcDetailsForm">	
                                  <td width="5%">
                              
                             <input size="12" type="HIDDEN" name="totalAmtpayInFirstInstal" value="<%=Math.round(totalAmtpayInFirstInstal)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                              
                              </td>
                               <td width="5%">
                               <input size="12" type="HIDDEN" name="totalAmountDeductedByMli" value="<%=Math.round(totalAmountDeductedByMli)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                              </td>   
                               <td width="5%">
                      
                              
                              <input size="12" type="HIDDEN" name="totalAmtPaybleNow" value="<%=Math.round(totalAmtPaybleNow)%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                      
                      
                              </td>
                                 
                                   </logic:equal>
                                
                                
                                
                                
            </tr>
                  
                </table>
               
            </td>    
                
      </tr>
   <!-- jai code-->
               
                
                
                <tr>
               
            <!-- <logic:notEqual  property="claimapplication.microCategory" value="Y" name="cpTcDetailsForm">	
            <%
                 microFlag="N";
                 System.out.println("Micro Flag:"+microFlag);
             %>
            </logic:notEqual> -->
            
                <script language = "JavaScript">
                var tci = <%=tcIssued%>;
                var wci = <%=wcIssued%>;
                var microFlag = '<%=microFlag%>';
                </script>
              
                </tr>
                
              
            <% 
             claimForm.setMicroCategory(microFlag);
                 
            %>
           
        
   
   
   	<html:hidden property="clmRefDtlSet" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_YES_FLAG%>"/>
	  <tr><td class="TableData">Comments</td>
   <td align="left" valign="top" class="TableData" colspan="7">
      <html:textarea cols="100" rows="9" style="font-size:14;"  property="claimdetail.comments" name="cpTcDetailsForm"></html:textarea>	
 
  <!--  <html:textarea cols="100" rows="8"  property="claimdetail.comments" name="cpTcDetailsForm"/>		-->
   <!-- <html:text property="claimdetail.comments"  size="100" alt="comments" name="cpTcDetailsForm"/> -->
	 </td>
    </tr>
    
   
              </table>
           </td>
        </tr>
      </table></td>
    <td background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center">          
          <A href="javascript:submitForm('displayClaimApprovalMod.do?method=displayClaimApprovalMod')"><img src="images/OK.gif" alt="Save" width="49" height="37" border="0"></a>
          </div>
      </div></td>
      <td align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>
</html>
