 <%@ page language="java"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>  
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","RecoveryAfterOTS.do?method=getRecoveryAfterOTS");%>

<!--<body onload="processForwardedToFirst(),processForwardedToSecond()"> -->
<% 
   
   ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpRecoveryOTS") ;
   
   //getLoopobjtData
    ArrayList ar=claimForm.getLoopobjtData();
   ClaimActionForm claimActionForm =(ClaimActionForm)ar.get(0) ; 
   
   
   //claimForm.getLoop//
   //getClaimActionforstore
   double laiblepercent= claimActionForm.getCp_ots_liablePercent();
   laiblepercent=laiblepercent/100;
   System.out.println("The Laiable Percentage is:--------------------------------------------------->"+laiblepercent);
   boolean microflag=claimForm.isCp_ots_microfalg();
   System.out.println("The Laiable Percentage is:--------------------------------------------------->"+microflag);
   String retGender = claimForm.getCp_ots_gender();
   System.out.println("The Laiable Percentage is:--------------------------------------------------->"+retGender);
  
//   
%>
 <SCRIPT LANGUAGE="JavaScript">
  function calSum(tableID)
  {

   var table = document.getElementById(tableID);  
     var rowCount = table.rows.length; 
     var rowc=rowCount - 1;
     
      var minval=0;
        var netout=0;
        var claimeligi=0;
        var gaurnteIssutot=0;
        var firstInstalAmt=0;
        var amtDefaoult=0;
        var secintallmentAmt=0;
        var finalPayout=0;   
        
          var totAmtASNPA=0;
         var totRecAmtNPA=0;
         var totNetOut=0;
         var totClaimelgi=0;
         var totFirstInstalAmt=0;
         var totamtDefaoult =0;
         var totsecintallmentAmt=0;
         var totfinalPayout=0;

 var laiblepercent = '<%=laiblepercent%>';
     var microflag = '<%=microflag%>';
     var retGender = '<%=retGender%>';     

      
      for (var i=rowc;i<rowCount;i++)
     {
      
      var row = table.rows[i]; 
       gaurnteIssutot=row.cells[1].childNodes[0].value;
      //gaurnteIssutot=row.cells[1].childNodes[0].value;
    //  alert("Hiiiiiiii"+gaurnteIssutot);
   //  alert ("The Gaurantee Total is :--->"+rowCount-1);
     }
      var rowf=rowCount - 1;
      
      
        for (var i=1;i<rowf;i++)
      {
       var row = table.rows[i]; 
       
       var gaurnteIssu= row.cells[1].childNodes[0].value;
       var npaOutAmt= row.cells[2].childNodes[0].value;
       var recoAmt = row.cells[3].childNodes[0].value;
       minval =Math.min(parseFloat(row.cells[1].childNodes[0].value), parseFloat(row.cells[2].childNodes[0].value));
       
       netout = parseFloat(minval)- parseFloat(recoAmt);
       //alert ("liable percent:----->"+laiblepercent);
       row.cells[4].childNodes[0].value=netout;
       
            // alert ("liable percent:----->"+netout);
      
      //remaiming code for 85% of upto 50 Lack and 50% of above 50 Lack for gaurantee amt greater then 50 lac
      // code for above 50 Lac
      if (gaurnteIssutot > 5000000)
      {
                                
                var amtforPer=(netout * 5000000)/gaurnteIssutot;
                var peroffirsthalf=amtforPer*laiblepercent;
               var  amtforsecondpersent= netout - amtforPer;
               var persecondhalf =amtforsecondpersent / 2;
               claimeligi=Math.round(peroffirsthalf + persecondhalf);
               
               row.cells[5].childNodes[0].value=claimeligi;
               //code for above 50 lac over
      }else{
      
            claimeligi = Math.round(netout *laiblepercent)
             row.cells[5].childNodes[0].value=claimeligi;
             }
      //      alert ("liable percent:----->"+claimeligi);
             firstInstalAmt = Math.round(claimeligi  * 0.75);
             row.cells[6].childNodes[0].value=firstInstalAmt; 
       var netRecov = row.cells[10].childNodes[0].value;
       amtDefaoult=parseFloat(netout)- parseFloat(netRecov);
        row.cells[11].childNodes[0].value=amtDefaoult;
       //10
      //remaiming code for 85% of upto 50 Lack and 50% of above 50 Lack for gaurantee amt greater then 50 lac
      secintallmentAmt = (amtDefaoult*laiblepercent) - firstInstalAmt;
      
       row.cells[12].childNodes[0].value=secintallmentAmt;
             var amtRecoDeduct = row.cells[9].childNodes[0].value;
    finalPayout = (parseFloat(secintallmentAmt) + parseFloat(recoAmt)) + parseFloat(amtRecoDeduct);
    row.cells[13].childNodes[0].value=finalPayout;
    
    //for calculating total
     if (!((isNaN(parseFloat(npaOutAmt))) && npaOutAmt!="")
      ||  ((isNaN(parseFloat(recoAmt))) && recoAmt!="")
      ||  ((isNaN(parseFloat(netout))) && netout!="")
      ||  ((isNaN(parseFloat(claimeligi))) && claimeligi!="") 
      ||  ((isNaN(parseFloat(firstInstalAmt))) && firstInstalAmt!="")
      ||  ((isNaN(parseFloat(amtDefaoult))) && amtDefaoult!="")
      ||  ((isNaN(parseFloat(secintallmentAmt))) && secintallmentAmt!="")
      ||  ((isNaN(parseFloat(finalPayout))) && finalPayout!=""))
      {
         totAmtASNPA+=parseFloat(npaOutAmt);
         totRecAmtNPA+=parseFloat(recoAmt);
         totNetOut+=parseFloat(netout);
         totClaimelgi+=parseFloat(claimeligi);
         totFirstInstalAmt+=parseFloat(firstInstalAmt);
         totamtDefaoult +=parseFloat(amtDefaoult);
         totsecintallmentAmt+=parseFloat(secintallmentAmt);
         totfinalPayout+=parseFloat(finalPayout);
         //finalPayout
      }
       var row1 = table.rows[rowCount-1]; 
   
       row1.cells[2].childNodes[0].value=totAmtASNPA;
       row1.cells[3].childNodes[0].value=totRecAmtNPA;
       row1.cells[4].childNodes[0].value=totNetOut;
       row1.cells[5].childNodes[0].value=totClaimelgi;
       row1.cells[6].childNodes[0].value=totFirstInstalAmt;
       row1.cells[11].childNodes[0].value=totamtDefaoult;
       row1.cells[12].childNodes[0].value=totsecintallmentAmt;
       row1.cells[13].childNodes[0].value=totfinalPayout
           }//for loop over
           
  
  }
 </SCRIPT>

 <SCRIPT LANGUAGE="JavaScript">
  function calSum1(tableID)
    {
    //alert ("The id is :-->"+tableID)
     var table = document.getElementById(tableID);  
     var rowCount = table.rows.length; 
     var netOut=0;
     var cgLia=0;
     
     var  firstInstalPaid=0;
     var recovAfterFirst=0;
     var deductAmt=0;
     var notDeductAmt=0;
     var netRecovAmt=0;
     var  netAmtIndefaoult=0;
     var secInstallAmt=0;
     var finalPayoutAmt=0;

     
      for (var i=1;i<rowCount-1;i++)
      {
       var row = table.rows[i]; 
       var fi4 = row.cells[4].childNodes[0].value;
       var fi5 = row.cells[5].childNodes[0].value;
       
       var fi6 = row.cells[6].childNodes[0].value;
       var fi7 = row.cells[7].childNodes[0].value;
       var fi8 = row.cells[8].childNodes[0].value;
       var fi9 = row.cells[9].childNodes[0].value;
       var fi10 = row.cells[10].childNodes[0].value;
       var fi11 = row.cells[11].childNodes[0].value;
       var fi12 = row.cells[12].childNodes[0].value;
       var fi13 = row.cells[13].childNodes[0].value;
       

      // alert("the value is :-->"+fi4);
       // alert("the value is :-->"+fi5);
        if (!((isNaN(parseFloat(fi4))) && fi4!="") || ((isNaN(parseFloat(fi5))) && fi5!="")
        || ((isNaN(parseFloat(fi6))) && fi6!="")
        || ((isNaN(parseFloat(fi7))) && fi7!="")
        || ((isNaN(parseFloat(fi8))) && fi8!="")
        || ((isNaN(parseFloat(fi9))) && fi9!="")
        || ((isNaN(parseFloat(fi10))) && fi10!="")
        || ((isNaN(parseFloat(fi11))) && fi11!="")
        || ((isNaN(parseFloat(fi12))) && fi12!="")
        || ((isNaN(parseFloat(fi13))) && fi13!="")
        )
        {
      netOut+=parseFloat(fi4);
      cgLia+=parseFloat(fi5);
      
      firstInstalPaid+=parseFloat(fi6);
      recovAfterFirst+=parseFloat(fi7);
      deductAmt+=parseFloat(fi8);
      notDeductAmt+=parseFloat(fi9);
      netRecovAmt+=parseFloat(fi10);
      netAmtIndefaoult+=parseFloat(fi11);
      secInstallAmt+=parseFloat(fi12);
      finalPayoutAmt+=parseFloat(fi13);
      
        }
     
      }
    // alert ("the netOut total is :-->"+netOut);
     // alert ("the cgLia total is :-->"+cgLia);
     
     var row1 =rowc;
     //alert ("The Row count is :-->"+rowCount);
       row1.cells[4].childNodes[0].value=netOut;
       row1.cells[5].childNodes[0].value=cgLia;
       
       row1.cells[6].childNodes[0].value=firstInstalPaid;
       row1.cells[7].childNodes[0].value=recovAfterFirst;
       row1.cells[8].childNodes[0].value=deductAmt;
       row1.cells[9].childNodes[0].value=notDeductAmt;
       row1.cells[10].childNodes[0].value=netRecovAmt;
       row1.cells[11].childNodes[0].value=netAmtIndefaoult;
       row1.cells[12].childNodes[0].value=secInstallAmt;
       row1.cells[13].childNodes[0].value=finalPayoutAmt;
       
       
     // document.form[0].cp_ots_netOutstandingTotal.value=sum;
      }
        function Validation()
        {
      
//        document.forms[0].target ="_self";
//        document.forms[0].method="POST";
//        document.forms[0].action="/cgtsi-ViewController-context-root/RecoveryAfterOTS.do?method=saveOTSData";
//        document.forms[0].submit();
        document.forms[0].target ="_self";
        document.forms[0].method="POST";
        document.forms[0].action="/cgtsi-ViewController-context-root/RecoveryAfterOTS.do?method=farwordData";
        document.forms[0].submit();
       	
        }
        </SCRIPT>

 

<html:errors/>

<html:form action="RecoveryAfterOTS.do?method=farwordData" method="POST" enctype="multipart/form-data">
  <table id="mainTable" width="100%" border="0" cellspacing="0" cellpadding="0">
   
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
   <tr>
    <td align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
    <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr>
    <td background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td>
      <DIV align="right">			
      		<A HREF="javascript:submitForm('helpProcessClaims.do')">
      	        HELP</A>
      </DIV>
      
      <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <tr>
              <td colspan="4">
              <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                                                                
                         <tr> <td colspan="6">&nbsp;</td></tr>
                          <TR>
                             <TD width="22%" class="Heading"><bean:message key="ots_details" /></TD>
                             </td>
                             <TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
                             </TR>
                    <TR>
                            <TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
                    </TR>

                </TABLE>
              
                </td>
            </tr>

              <tr>
                <td colspan="4" class="SubHeading">
                  &nbsp;
                 OTS Details Info
                </td>
              </tr>
             
             
              <tr>
                <td colspan="4">
                 
                  <table width="100%" border="0" cellspacing="1"
                         cellpadding="0">
                        <!-- <logic:iterate id="object" name="cpRecoveryOTS" property="claimdeatilforOts" indexId="index">
                          </logic:iterate> -->
                          <tr class="TableData">
                    
                        
		                      <td valign="middle" class="HeadingBg">
		                        <div align="center">
		                          <strong>&nbsp;Member Id<br/>
		                             </strong>
		                        </div>
		                      </td>
		                    
		                      <td >
                                      <div align="center">
                                     
                                     <bean:write name="cpRecoveryOTS" property="cp_ots_enterMember" scope="session"/>
                                       </div>
                                       </td>
		                      
		                      <td class="HeadingBg">
		                        <div align="center">
		                          MLI Name.
		                        </div>
		                      </td>
		                      
		                      <td>
                                      <div align="center">
                                     <bean:write name="cpRecoveryOTS" property="cp_ots_mliName" scope="session"/>
		                   
                                      </div>
		                      </td>
      				                 
      				</tr>
       
                    <tr class="TableData">  
			                      		<td class="HeadingBg">
				                       	 <div align="center">
                                                        <strong>
			                          	Unit Name &nbsp;
				                        </strong>
                                                         </div>
				                        </td>
			                          
			                           <td>
                                                      <div align="center">
                                                      <bean:write name="cpRecoveryOTS" property="cp_ots_unitName" scope="session"/>
                                                        </div>
			                           </td>
			                           
				                      <td class="HeadingBg">
				                        <div align="center">
				                          <strong>Clain Ref Number</strong><br/>
				                        </div>
				                      </td>
				                      
				                       <td>
				                        <div align="center">
                                                       <bean:write name="cpRecoveryOTS" property="cp_ots_appRefNo" scope="session"/>
                                                        </div>
				                      </td>
			                      
			        </tr>
              <tr class="TableData">  
			                      		<td class="HeadingBg">
				                       	 <div align="center">
                                                        <strong>
			                          	 Unit Associated by MSE. &nbsp;
				                        </strong>
                                                         </div>
				                        </td>
                                                        <td>
                                                        <div align="center">
                                                      <bean:write name="cpRecoveryOTS" property="cp_ots_UnitAssitByMSE" scope="session"/>
                                                        </div>
                                                        </td>
			                           
				                      <td class="HeadingBg">
				                        <div align="center">
				                          <strong>Gender.</strong><br/>
				                        </div>
				                      </td>
				                      
				                       <td>
				                        <div align="center">
                                                      <bean:write name="cpRecoveryOTS" property="cp_ots_gender" scope="session"/>
                                                        </div>
				                      </td>
			        </tr>
                                 <tr class="TableData">  
			                      		<td class="HeadingBg">
				                       	 <div align="center">
                                                        <strong>
			                          	 NPA Date. &nbsp;
				                        </strong>
                                                         </div>
				                        </td>
                                                        <td>
                                                        <div align="center">
                                                     <bean:write name="cpRecoveryOTS" property="cp_ots_npaDate" scope="session"/>
                                                         </div>
                                                        </td>
			                           
				                      <td class="HeadingBg">
				                        <div align="center">
				                          <strong>First Installment Date</strong><br/>
				                        </div>
				                      </td>
				                      
				                       <td>
				                        <div align="center">
                              <bean:write name="cpRecoveryOTS" property="cp_ots_clmappDate" scope="session"/>
                                                        </div>
				                      </td>
			        </tr>
                                  
              </tr>
                         
                       
                 </table>
                 
                 </td>
                 </tr>
                  <tr>
               
              </tr>
                 <tr>
                 <td>
                  </td>
                 </tr>
                 <tr>
                <td colspan="4" class="SubHeading">
                  &nbsp;
                 CGTMSE CALCULATION SHEET FOR CLAIM INSTALLMENT.
                </td>
              </tr>
              <tr>
              <td>
               <table width="100%" border="0" cellspacing="1"
                         cellpadding="0" id="dataTable" >
       
                    <tr class="TableData">
                      <td valign="middle" class="HeadingBg" width="7%">
                        <div align="center">
                          <strong>&nbsp;CGPAN<br/>
                             </strong>
                        </div>
                      </td>
                                        <td valign="middle" class="HeadingBg" width="60%">
                        <div align="center">
                          <strong>(A).Guarantee Amount <br/>
                             </strong>
                        </div>
                      </td>
					  
                         <td valign="middle" class="HeadingBg" width="10%">
                         <div align="center">
                          <strong>(B). Amount in Default on NPA Date <br/>
                             </strong>
                         </div>
                         </td>
                         <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>(C)Recovery from Primary Security remitted to CGTMSE at the time of claim lodgment <br/>
                             </strong>
                         </div>
                         </td>
                         <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>(D)Net Outstanding.<br/>
                             </strong>
                         </div>
                         </td>
                         <%
                         String clmLiabletxt="";
                         String seconInsatlText="";
                         %>
 <logic:iterate id="object" name="cpRecoveryOTS" property="loopobjtData" indexId="index">
                                 <%ClaimActionForm claim=(ClaimActionForm)object; 
                                 System.out.println("the value of objctes is jsp is :-->"+claim);
                                 clmLiabletxt= claim.getCp_ots_liabtext();
                                 seconInsatlText=claim.getCp_ots_seconInsatlText();
                                 %>
                                 </logic:iterate>

						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>(E)
                          <!--CGTMSE's Liablility -->
                          
                          <%=clmLiabletxt%>
                          <!--<bean:write name="cpRecoveryOTS" property="cp_ots_liabtext" scope="session"/><br/>-->
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>(F)First installment paid by CGTMSE.<br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong> (G)Recovery from Primary Security remitted to CGTMSE after claim lodgment <br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>(H)Legal Expenses if Not Deducted <br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>(I) Legal Expenses if Deducted <br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>(J)Net Recovery after claim lodgement<br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong> (K)Net Amount in Default <br/>
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>(L)
                          Second Installment Paid by CGTMSE.
                          <% //=seconInsatlText%>
                          <!--<bean:write name="cpRecoveryOTS" property="cp_ots_seconInsatlText" scope="session"/> <br/>-->
                             </strong>
                         </div>
                         </td>
						 <td valign="middle" class="HeadingBg" width="12%">
                         <div align="center">
                          <strong>(M)Final payout to MLI <br/>
                             </strong>
                         </div>
                         </td>
			
                    </tr>
					 <logic:iterate id="object" name="cpRecoveryOTS" property="loopobjtData" indexId="index">
                                 <%
                                 ClaimActionForm claimdeclarion=(ClaimActionForm)object; 
                                
                                 System.out.println("the value of objctes is jsp is :-->"+claimdeclarion);
                                 System.out.println("the amount retrive is :---> "+claimdeclarion.getCp_ots_cgpanGaurnteeAmt());
                                 %>
                                <tr class="TableData">  
                                        <td width="30%">
                                        <div align="center">
                                        <%=claimdeclarion.getCp_ots_enterCgpan()%>
                                         <input type="HIDDEN" name="cgpanHidd" value="<%=claimdeclarion.getCp_ots_enterCgpan()%>"  /> 
                                        </div>
                                        </td>
                                         <td width="35%">
                                         <input  size="12" type="text" name="cgpanGaurnteeAmtHidd" value="<%=claimdeclarion.getCp_ots_cgpanGaurnteeAmt()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled" />
                                         <input type="HIDDEN" name="cgpanGaurnteeAmtHidd" value="<%=claimdeclarion.getCp_ots_cgpanGaurnteeAmt()%>"  /> 
                                        <%//=claimdeclarion.getCp_ots_cgpanGaurnteeAmt()%>
                                        
                                        </td>
                                        <td width="35%">
                                        
                                         <input  size="12" type="text" name="npaAmountHidd" value="<%=claimdeclarion.getCp_ots_npaAmount()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" onblur="calSum('dataTable')"/> 
                                         <!--<input type="HIDDEN" name="npaAmountHidd" value="<%=claimdeclarion.getCp_ots_npaAmount()%>"  /> -->
                                        <% //=claimdeclarion.getCp_ots_npaAmount()%>
                                        
                                        </td>
                                         <td width="35%">
                                       
                                         <input  size="12" type="text" name="recoveryAmtHidd" value="<%=claimdeclarion.getCp_ots_recoveryAmt()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" onblur="calSum('dataTable')"/> 
                                         <!--<input type="HIDDEN" name="recoveryAmtHidd" value="<%=claimdeclarion.getCp_ots_recoveryAmt()%>"  /> -->
                                        <%//=claimdeclarion.getCp_ots_recoveryAmt()%>
                                       
                                        </td>
                                        <% double  netOutstand =claimdeclarion.getCp_ots_netOutstanding();
                                        
                                        %>
                                         <td width="35%">
                                    
                                         <input  size="12" type="text" name="netOutstandAmt" value="<%=netOutstand%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" onblur="calSum('dataTable')"/> 
                                    
                                        </td>
                                        <td width="35%">
                                        
                                        <% double  laibleAmt = claimdeclarion.getCp_ots_liableamount();%>
                                         <input  size="12" type="text" name="laibleAmount" value="<%=laibleAmt%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)"  onblur="calSum('dataTable')"/> 
                                        
                                        
                                        </td>
                                        <td width="35%">
                                        
                                        <% double  firstInstalPaid = claimdeclarion.getCp_ots_firstIntalpaidAmount();%>
                                         <input  size="12" type="text" name="firstInstalPaidAmt" value="<%=firstInstalPaid%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" onblur="calSum('dataTable')"/> 
                                       
                                        
                                        </td>
                                        <td width="35%">
                                       
                                        <% double  recovAfterFirst = claimdeclarion.getCp_ots_totRecAftFirInst();%>
                                         <input  size="12" type="text" name="recAfterFirstInstall" value="<%=recovAfterFirst%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled" /> 
                                        <input type="HIDDEN" name="recAfterFirstInstall" value="<%=recovAfterFirst%>"  /> 
                                       
                                        </td>
                                        <td width="35%"> 
                                       
                                        <% double  DedctAmt =claimdeclarion.getCp_ots_totDedtctAmt();%>
                                         <input  size="12" type="text" name="deductAmt" value="<%=DedctAmt%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled" /> 
                                        
                                        <input type="HIDDEN" name="deductAmt" value="<%=DedctAmt%>"  /> 
                                        </td>
                                        <td width="35%">
                                      
                                        <% double  notDedctAmt =claimdeclarion.getCp_ots_totNotDedtctAmt();%>
                                         <input  size="12" type="text" name="notDeductAmt" value="<%=notDedctAmt%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/> 
                                        
                                         <input type="HIDDEN" name="notDeductAmt" value="<%=notDedctAmt%>"  /> 
                                        </td>
                                          <td width="35%">
                                        
                                          <% double  netRecov=claimdeclarion.getCp_ots_netRecovAmt();%>
                                         <input  size="12" type="text" name="netRecovAmt" value="<%=netRecov%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled" /> 
                                       <input type="HIDDEN" name="netRecovAmt" value="<%=netRecov%>"  /> 
                                        
                                        </td>
                                        <td width="35%">
                                        
                                        <% double  netAmtInDefaolut=claimdeclarion.getCp_ots_netAmountInDefoault();%>
                                         <input  size="12" type="text" name="netAmtIndefaoult" value="<%=netAmtInDefaolut%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" onblur="calSum('dataTable')"/> 
                                        
                                        
                                        </td>
                                        <td width="35%">
                                        
                                         <% double  secInstalAmt =claimdeclarion.getCp_ots_secIntalAMt();%>
                                         <input  size="12" type="text" name="secInstallAmt" value="<%=secInstalAmt%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" onblur="calSum('dataTable')"/> 
                                       
                                        
                                        </td>
                                        <td width="35%">
                                       
                                        <% double  finalPay=claimdeclarion.getCp_ots_finalPayout();%>
                                         <input  size="12" type="text" name="finalPayoutAmt" value="<%=finalPay%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" onblur="calSum('dataTable')"/> 
                                        
                                       
                                        </td>
                                </tr>
                                
                                </logic:iterate>
                               
                               <% ClaimActionForm clmform=(ClaimActionForm)session.getAttribute("cpRecoveryOTS");
                               //double acb=clmform.getCp_ots_npatotalAmt();
                                %>
                                <tr class="TableData">
                                      <td width="30%">
                                       
                                       Total
                                        
                                        </td>
                                         <td width="35%">
                                           <input  size="12" type="text" name="cp_ots_guarnteetotal" value=" <%=clmform.getCp_ots_guarnteetotal()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                                      <input type="HIDDEN" name="cp_ots_guarnteetotal" value="<%=clmform.getCp_ots_guarnteetotal()%>"  /> 
                                         <!--<bean:write name="cpRecoveryOTS" property="cp_ots_guarnteetotal" scope="session"/> -->
                                      
                                        </td>
                                      
                                         <td width="35%">
                                        
                                       <input  size="12" type="text" name="cp_ots_npatotalAmt" value=" <%=clmform.getCp_ots_npatotalAmt()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>                                     
                                    <input type="HIDDEN" name="cp_ots_npatotalAmt" value="<%=clmform.getCp_ots_npatotalAmt()%>"  /> 
                                   <!--   <bean:write name="cpRecoveryOTS" property="cp_ots_npatotalAmt" scope="session"/>-->
                                        
                                        </td>
                                         <td width="35%">
                                       
                                     <input  size="12" type="text" name="cp_ots_recovTotal" value=" <%=clmform.getCp_ots_recovTotal()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>                                       
                                      <input type="HIDDEN" name="cp_ots_recovTotal" value="<%=clmform.getCp_ots_recovTotal()%>"  />
                                      <!--<bean:write name="cpRecoveryOTS" property="cp_ots_recovTotal" scope="session"/> -->
                                       
                                        </td>
                                         <td width="35%">
                                        
                                        <input  size="12" type="text" name="cp_ots_netOutstandingTotal" value="<%=clmform.getCp_ots_netOutstandingTotal()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                                          <input type="HIDDEN" name="cp_ots_netOutstandingTotal" value="<%=clmform.getCp_ots_netOutstandingTotal()%>"  />
                                        </td>
                                        
                                        
                                         <td width="35%">
                                       
                                        
                                     <input  size="12" type="text" name="cp_ots_liavleAmtTotal" value="<%=clmform.getCp_ots_liavleAmtTotal()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                                        <input type="HIDDEN" name="cp_ots_liavleAmtTotal" value="<%=clmform.getCp_ots_liavleAmtTotal()%>"  />
                                        </td>
                                        <td width="35%">
                                         <input  size="12" type="text" name="cp_ots_firstInstallmentPaidTotal" value="<%=clmform.getCp_ots_firstInstallmentPaidTotal()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                                         <input type="HIDDEN" name="cp_ots_firstInstallmentPaidTotal" value="<%=clmform.getCp_ots_firstInstallmentPaidTotal()%>"  />
                                        </td>
                                        
                                          <td width="35%">
                                        
                                         
                                      <input  size="12" type="text" name="cp_ots_recafterfirstinstallTotal" value="<%=clmform.getCp_ots_recafterfirstinstallTotal()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                                         <input type="HIDDEN" name="cp_ots_recafterfirstinstallTotal" value="<%=clmform.getCp_ots_recafterfirstinstallTotal()%>"  />
                                        </td>
                                          <td width="35%">
                                        
                                          
                                       <input  size="12" type="text" name="cp_ots_totaDecdectAMt" value="<%=clmform.getCp_ots_totaDecdectAMt()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                                         <input type="HIDDEN" name="cp_ots_totaDecdectAMt" value="<%=clmform.getCp_ots_totaDecdectAMt()%>"  />
                                        </td>
                                          <td width="35%">
                                        
                                         
                                        <input  size="12" type="text" name="cp_ots_totNotdedctAmt" value="<%=clmform.getCp_ots_totNotdedctAmt()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                                         <input type="HIDDEN" name="cp_ots_totNotdedctAmt" value="<%=clmform.getCp_ots_totNotdedctAmt()%>"  />
                                        </td>
                                          <td width="35%">
                                       
                                       
                                <input  size="12" type="text" name="cp_ots_totnetRecovAmt" value="<%=clmform.getCp_ots_totnetRecovAmt()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                                        <input type="HIDDEN" name="cp_ots_totnetRecovAmt" value="<%=clmform.getCp_ots_totnetRecovAmt()%>"  />
                                        </td>
                                          <td width="35%">
                                       
                                         
                                   <input  size="12" type="text" name="cp_ots_netAmtindefoultTotal" value="<%=clmform.getCp_ots_netAmtindefoultTotal()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                                        <input type="HIDDEN" name="cp_ots_netAmtindefoultTotal" value="<%=clmform.getCp_ots_netAmtindefoultTotal()%>"  />
                                        </td>
                                          <td width="35%">
                                        
                                         
                                    <input  size="12" type="text" name="cp_ots_secinstamentAmtTotal" value="<%=clmform.getCp_ots_secinstamentAmtTotal()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                                        <input type="HIDDEN" name="cp_ots_secinstamentAmtTotal" value="<%=clmform.getCp_ots_secinstamentAmtTotal()%>"  />
                                        </td>
                                          <td width="35%">
                                        
                                            
                                  <input  size="12" type="text" name="cp_ots_finalPayoutAmtTotal" value="<%=clmform.getCp_ots_finalPayoutAmtTotal()%>" onkeypress="return decimalOnly(this, event,13)" maxlength="16" onkeyup="isValidDecimal(this)" disabled="disabled"/>
                                      <input type="HIDDEN" name="cp_ots_finalPayoutAmtTotal" value="<%=clmform.getCp_ots_finalPayoutAmtTotal()%>"  />  
                                        </td>
                                </tr>
                    
                                
                   
                </table>
                 </td>
              </tr>
              <tr>
              <td>
              <table width="100%" border="0" cellspacing="1" 
                         cellpadding="0">
              <tr class="TableData">
               <td width="10%">
               <div align="center">
              <bean:message key="remarks"/>
              </div>
              </td>
              
              <td width="90%">
               <div align="left"><html:textarea property="cp_ots_remarks2" cols="75" name="cpRecoveryOTS" rows="4"/></div>
              </td>
                
              </tr>
              </table>
              </td>
              </tr>
                  </table>
                 
                 
                                
          <table width="100%" border="0" cellspacing="1" cellpadding="0">
          <tr align="center" valign="baseline">
            <td colspan="4"> 
            <div align="center">  
                       <!-- <a href="javascript:submitForm('declartionDetails.do?method=SaveDeclartaionDetailData')"> -->
              <a href="javascript:Validation()">
             <img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></a>
            <a href="javascript:document.cpRecoveryOTS.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>
            <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a></div></td>
          </tr>
        </table>
    <td background="images/TableVerticalRightBG.gif">&nbsp;</td>
  
   <tr>
    <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
    <td background="images/TableBackground2.gif"><div align="center"></div></td>
    <td align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
  </tr>
</table>
</html:form>





 
