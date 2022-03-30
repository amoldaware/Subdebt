<%@ page language="java"%>
<%@ page import = "com.cgtsi.claim.ClaimConstants"%>
<%@ page import = "com.cgtsi.claim.TermLoanCapitalLoanDetail"%>
<%@ page import = "com.cgtsi.claim.WorkingCapitalDetail"%>
<%@ page import = "com.cgtsi.claim.RecoveryDetails"%>
<%@ page import = "com.cgtsi.claim.ClaimSummaryDtls"%>
<%@ page import = "com.cgtsi.claim.ClaimApplication"%>
<%@ page import = "com.cgtsi.claim.SecurityAndPersonalGuaranteeDtls"%>
<%@ page import = "com.cgtsi.claim.DtlsAsOnDateOfSanction"%>
<%@ page import = "com.cgtsi.claim.DtlsAsOnDateOfNPA"%>
<%@ page import = "com.cgtsi.claim.DtlsAsOnLogdementOfClaim"%>
<%@ page import="java.text.SimpleDateFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","proceedFromRecoveryFilterPage.do?method=proceedFromRecoveryFilterPage");%>
<%
String npaClassifiedDt = null;
String npaReportingDt = null;
String reasonForTurningNPA = null;
String cgpan = null;
String hiddencgpan = null;
String dsbrsmntdt = null;
String principal = null;
String interestCharges = null;
String claimreferencenumber = "";
String osAsOnNpa = null;
String osAsStatedinCivilSuit = null;
String osAsOnLodgementOfClm = null;
String wccgpan = null;
String hidencgpan = null;
String wcAsOnNPA = null;
String wcOsAsOnInCivilSuit = null;
String wcOsAtLdgmntClm = null;
String landAsOnDtOfSnctnDtl = null;
String netwrthAsOnDtOfSnctn = null;
String reasonReductionDtSnctn = null;
String bldgAsOnDtOfSnctn = null;
String machinecAsOnDtOfSnctn = null;
String otherAssetsAsOnDtOfSnctn = null;
String currAssetsAsOnDtOfSnctn = null;
String otherValAsOnDtOfSnctn = null;
String landAsOnDtOfNPA = null;
String netwrthAsOnDtOfNPA = null;
String rsnRdctnDtSnctnAsOnNPA = null;
String bldgAsOnDtOfNPA = null;
String machinecAsOnDtOfNPA = null;
String otherAssetsAsOnDtOfNPA = null;
String currAssetsAsOnDtOfNPA = null;
String otherValAsOnDtOfNPA = null;
String landAsOnLodgemnt = null;
String netwrthAsOnLodgemnt = null;
String rsnRdctnDtSnctnAsOnLodgemnt = null;
String bldgAsOnDtOfLodgemnt = null;
String machinecAsOnLodgemnt = null;
String otherAssetsAsOnLodgemnt = null;
String currAssetsAsOnLodgemnt = null;
String otherValAsOnLodgemnt = null;
String cgpantodisplay = null;
String tcPrincipal1 = null;
String tcInterestCharges1 = null;
String wcAmount1 = null;
String wcOtherCharges1 = null;
String recMode = null;
String cgpn = null;
String amountClaimed = null;
java.util.HashMap hashmap = null;
java.util.Date lastDsbrsmntDt = null;
double principalRepayment = 0.0;
double interestAndOtherCharges = 0.0;
double outstandingAsOnDateOfNPA = 0.0;
double outstandingAsOnDateOfLodgement = 0.0;
double outstandingStatedInCivilSuit = 0.0;
String modeOfRecovery = null;
double tcPrincipal = 0.0;
double tcInterestAndOtherCharges = 0.0;
double wcAmount = 0.0;
double wcOtherCharges = 0.0;
double clmAppliedAmnt = 0.0;
String lastDsbrsmntDtStr;
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

claimreferencenumber = request.getParameter("ClaimRefNumber");
System.out.println("Claim Reference Number"+claimreferencenumber);
System.out.println("First claim");
%>

<html:errors />
<html:form action="addFirstClaimsPageDetails.do?method=addFirstClaimsPageDetails" method="POST" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">    


      <tr>
        <td class="FontStyle">&nbsp;</td>
      </tr>
    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
        <td width="248" background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
      <td align="right" valign="top" background="images/TableBackground1.gif"> 
        <div align="right"></div></td>
      <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
    <tr> 
      <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
      <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
  		<table width="100%" border="0" cellspacing="1" cellpadding="0">
                  
                  <tr> 
                    <td colspan="8"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                        <tr> 
                          <td width="15%" height="20" class="Heading">&nbsp;<bean:message key="claimtitle"/></td>
                          <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        </tr>
                        <tr> 
                          <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                        </tr>
                      </table></td>
                  </tr>
                  <tr> 
                    <td class="SubHeading" colspan="4">&nbsp;<bean:message key="dtlsOfOperatingOfficeAndLendingBranch"/></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp; <bean:message key="memberId"/></div></td> 
  
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="claimapplication.memberDetails.memberId" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp; <bean:message key="lendingbankname"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="claimapplication.memberDetails.memberBankName" name="cpTcDetailsForm"/></div></td>
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="city"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="claimapplication.memberDetails.city" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="district"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="claimapplication.memberDetails.district" name="cpTcDetailsForm"/></div></td>
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="state"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="claimapplication.memberDetails.state" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="telephone"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="claimapplication.memberDetails.telephone" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="eMail"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp; <bean:write property="claimapplication.memberDetails.email" name="cpTcDetailsForm"/></div></td>
                  </tr>
  				<tr> 
                    <td class="SubHeading" colspan="4"> &nbsp;<bean:message key="borrower/unitdetails"/></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="name"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="claimapplication.borrowerDetails.borrowerName" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="completeaddress"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="claimapplication.borrowerDetails.address" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="district"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="claimapplication.borrowerDetails.district" name="cpTcDetailsForm"/></div></td>
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="state"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="claimapplication.borrowerDetails.state" name="cpTcDetailsForm"/></div></td>
                    <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="pin"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="claimapplication.borrowerDetails.pinCode" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  </tr>                
  				<tr> 
                    <td class="SubHeading" colspan="4"> &nbsp;<bean:message key="statusofaccounts"/></td>
                  </tr>                  
                  <tr> 
                  
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dateofnpa"/></div></td>                    
                    <%                    
                    npaClassifiedDt = "claimapplication.npaDetails.".trim() +com.cgtsi.claim.ClaimConstants.NPA_CLASSIFIED_DT;
                    %>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="claimapplication.dateOnWhichAccountClassifiedNPAStr" name="cpTcDetailsForm"/> </div></td>
                  </tr>
                  <%
                  npaReportingDt = "npaDetails.".trim() + com.cgtsi.claim.ClaimConstants.NPA_REPORTING_DT;
                  %>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dateofreporting"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp; <bean:write property="claimapplication.dateOfReportingNpaToCgtsiStr" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <%
                  reasonForTurningNPA = "npaDetails.".trim() + com.cgtsi.claim.ClaimConstants.REASONS_FOR_TURNING_NPA;
                  %>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="reasonfornpa"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp; <bean:write property="claimapplication.reasonsForAccountTurningNPA" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  
                  
                  <tr> 
		                      <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dateofissueofrecallnotice"/></div></td>
		                      <td class="TableData" colspan="3">
		    		  	<bean:write property="claimapplication.dateOfIssueOfRecallNoticeStr" name="cpTcDetailsForm"/>
		    		  	<%
		    		  	if(request.getAttribute("recallNoticeAttachment")!=null)
		    		  	{
		    		  	%>
		    		  	<a href="<%=request.getAttribute("recallNoticeAttachment")%>"> 
		    		  	View Attachment 
		    		  	</a>
		    		  	<%
		    		  	}
		    		  	%>
		    		  </td>
		    		  
                  </tr>
                  
                  
  				<tr>
  				<td><br></tr>
  				</tr>
  				
  				<tr>
				                    <td colspan="4" class="SubHeading">&nbsp;<bean:message key="detailsoflegalproceedings"/></td>
				                  </tr>
				                  <tr>
				                    <td colspan="4" class="SubHeading"><table width="100%" border="0" cellspacing="1" cellpadding="0">
				  					<tr>
				  						<td class="ColumnBackground">						
				  						&nbsp;<bean:message key="forumthruwhichlegalproceedingsinitiated"/></td>						
				  						<td class="TableData">
				  						<bean:write property="claimapplication.legalProceedingsDetails.forumRecoveryProceedingsInitiated" name="cpTcDetailsForm"/>				  								
				  						</td>						
				  						<td class="ColumnBackground">&nbsp;<bean:message key="otherforum"/></td>
				  						<td class="TableData"></td>
				  					</tr>
				  					<tr>
				                          <td class="ColumnBackground">&nbsp;<bean:message key="suit/caseregnumber"/></td>
				  						<td class="TableData" >						
				  						<bean:write property="claimapplication.legalProceedingsDetails.suitCaseRegNumber" name="cpTcDetailsForm"/></td>
				  						
				  						<td class="ColumnBackground">&nbsp;<bean:message key="filingdate"/></td>
				  						<td class="TableData"  width="20%"><bean:write property="claimapplication.legalProceedingsDetails.filingDateStr" name="cpTcDetailsForm"/></td>
				  					</tr>
				  					<tr>
				                          <td class="ColumnBackground">&nbsp;<bean:message key="nameoftheforum"/></td>
				  						<td class="TableData">
				  							<bean:write property="claimapplication.legalProceedingsDetails.nameOfForum" name="cpTcDetailsForm"/>
				  						</td>
				                          <td class="ColumnBackground">&nbsp;<bean:message key="location"/></td>
				  						<td class="TableData">
				  						<bean:write property="claimapplication.legalProceedingsDetails.location" name="cpTcDetailsForm"/>
				  						</td>
				  					</tr>
				  					<tr>
				                          <td class="ColumnBackground">&nbsp;<bean:message key="amntClaimedInTheSuit"/></td>
				  						<td class="TableData" colspan="3">
				  							<div align="right"><bean:write property="claimapplication.legalProceedingsDetails.amountClaimed" name="cpTcDetailsForm"/></div>
				  						
										<%
										if(request.getAttribute("legalDetailsAttachment")!=null)
										{
										%>
										<a href="<%=request.getAttribute("legalDetailsAttachment")%>"> 
										View Attachment 
										</a>
										<%
										}
										%>
										</td>
				  					</tr>
				  					<tr>
				                          <td class="ColumnBackground">&nbsp;<bean:message key="currentStatus"/></td>
				  						<td class="TableData" width="20%" colspan="3">
				  							<bean:write property="claimapplication.legalProceedingsDetails.currentStatusRemarks" name="cpTcDetailsForm"/>
				  						</td>
				  					</tr>
				  					<tr>
				                          <td class="ColumnBackground">&nbsp;<bean:message key="recoveryProceedingsConcluded"/></td>
				  						<td class="TableData" width="20%" colspan="3">
				  							<bean:write property="claimapplication.legalProceedingsDetails.isRecoveryProceedingsConcluded" name="cpTcDetailsForm"/>
				  						</td>
				  					</tr>
				                      </table></td>
                  </tr>
  				
  				<tr>
  				<td><br></td>
  				</tr>

  	            <tr> 
                    <td colspan="4" class="SubHeading">&nbsp;<bean:message key="termloancompositeloandtls"/></td>
                  </tr>
  				<tr>
                    <td colspan="8" class="SubHeading"><table width="100%" border="0" cellspacing="1" cellpadding="0">
                        <tr>
                          <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="sNo"/></td>
                          <td class="ColumnBackground" rowspan="2" width="15%"><div align="center">&nbsp;<bean:message key="cgpan"/></td>
                          <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="dateoflastdisbursement"/></td>
                          <td class="ColumnBackground" colspan="2"><div align="center" > &nbsp;<bean:message key="repayments"/></td>
                          <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="outstandingasondateofnpa"/></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="outstandingstatedinthecivilsuit"/></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="osAsOnLodgementOfClaim"/></td>
  					  </tr>
  					  <tr>
  						<td class="ColumnBackground" width="15%"><div align="center"><bean:message key="principal"/></div></td>
  						<td class="ColumnBackground" width="15%"><div align="center"><bean:message key="interestandothercharges"/></div></td>
  					  </tr>
  					  <% int i=1;%>
  					  <logic:iterate property="claimapplication.termCapitalDtls" id="object" name="cpTcDetailsForm" scope="session">			
  					  <%
  					     TermLoanCapitalLoanDetail tlcldtl = (TermLoanCapitalLoanDetail)object;   					     
  					     cgpan = tlcldtl.getCgpan();
  					     lastDsbrsmntDt = tlcldtl.getLastDisbursementDate();
					     if(lastDsbrsmntDt != null)
					     {
						 lastDsbrsmntDtStr = sdf.format(lastDsbrsmntDt);
					     }
					     else
					     {  					     
						 lastDsbrsmntDtStr = "";
					     }
  					     principalRepayment = tlcldtl.getPrincipalRepayment();
  					     interestAndOtherCharges = tlcldtl.getInterestAndOtherCharges();
  					     outstandingAsOnDateOfNPA = tlcldtl.getOutstandingAsOnDateOfNPA();
  					     outstandingStatedInCivilSuit = tlcldtl.getOutstandingStatedInCivilSuit();
  					     outstandingAsOnDateOfLodgement = tlcldtl.getOutstandingAsOnDateOfLodgement();
  					  %>  					          
  					  <tr>					  
  						  <td class="TableData">&nbsp;<%=i%></td>
  						  <td class="TableData">&nbsp;<%= cgpan %></td>
  						  <td class="TableData">  						  
  						  <%=lastDsbrsmntDtStr%>
  						  </td>
  						  <%
  						     principal="tcprincipal(key-"+i+")";						  
  						  %>
  						  <td class="TableData">
  							<div align="right"><%=principalRepayment%></div>
  						  </td>
  						  <%
  						    interestCharges="tcInterestCharge(key-"+i+")";
  						  %>
  						  <td class="TableData">
  							<div align="right"><%=interestAndOtherCharges%></div>
  						  </td>
  						  <%
  						   osAsOnNpa="tcOsAsOnDateOfNPA(key-"+i+")";
  						  %>
  						  <td class="TableData">
  							<div align="right"><%=outstandingAsOnDateOfNPA%></div>
  						  </td>
  						  <%
  						   osAsStatedinCivilSuit="tcOsAsStatedInCivilSuit(key-"+i+")";
  						  %>
  						  <td class="TableData">
  							<div align="right"><%=outstandingStatedInCivilSuit%></div>
  						  </td>  						  
  						  <td class="TableData">
  							<div align="right"><%=outstandingAsOnDateOfLodgement%></div>
    </td>
  					  </tr>
  					  <%i++;%>
  					  </logic:iterate>
  					  </table></td>
                  </tr>
  				<tr>
  					<td colspan="4" class="SubHeading">
  						&nbsp;<bean:message key="mentiononlyprincipaloutstanding"/></td>
  				</tr>
  				<tr>
  				<td><br></td>
  				</tr>
  	            <tr> 
                    <td colspan="4" class="SubHeading">&nbsp;<bean:message key="workingcapitaldetails"/></td>
                  </tr>
  				<tr>
                    <td colspan="5" class="SubHeading"><table width="100%" border="0" cellspacing="1" cellpadding="0">
                        <tr>
                          <td class="ColumnBackground"><div align="center">&nbsp;<bean:message key="sNo"/></td>
                          <td class="ColumnBackground" width="15%"><div align="center">&nbsp;<bean:message key="cgpan"/></td>
                          <td class="ColumnBackground" ><div align="center">&nbsp;<bean:message key="oswcasonthedateofnpa"/></td>
  						<td class="ColumnBackground" ><div align="center">&nbsp;<bean:message key="oswcstatedinthecivilsuit"/></td>
  						<td class="ColumnBackground" ><div align="center">&nbsp;<bean:message key="oswcasonthedateoflodgementofclaim"/></td>
  					  </tr>
  					  <% int j = 1; %>
  					  <logic:iterate property="claimapplication.workingCapitalDtls" id="object" name="cpTcDetailsForm" scope="session">
  					  <% 
  					     WorkingCapitalDetail wcdtl = (WorkingCapitalDetail)object; 
  					     cgpan = wcdtl.getCgpan();
  					     outstandingAsOnDateOfNPA = wcdtl.getOutstandingAsOnDateOfNPA();
  					     outstandingStatedInCivilSuit = wcdtl.getOutstandingStatedInCivilSuit();
  					     outstandingAsOnDateOfLodgement = wcdtl.getOutstandingAsOnDateOfLodgement();  					     				  					  
  					  %>  					  
  					  <tr>
  						  <td class="TableData">&nbsp;<%=j%></td>
  						  <td class="TableData">&nbsp;<%=cgpan%></td>
  						  <td class="TableData"><div align="center">
  						  <%
  						    wcAsOnNPA = "wcOsAsOnDateOfNPA(key-"+j+")";
  						  %>
  						   <div align="right"><%=outstandingAsOnDateOfNPA%></div>
  						  </td>
  						  <td class="TableData"><div align="center">
  						  <%
  						    wcOsAsOnInCivilSuit = "wcOsAsStatedInCivilSuit(key-"+j+")";
  						  %>
  						  <div align="right"><%=outstandingStatedInCivilSuit%></div>
  						  </td>
  						  <td class="TableData"><div align="center">
  						  <%
  						    wcOsAtLdgmntClm= "wcOsAsOnLodgementOfClaim(key-"+j+")";
  						  %>
  						  <div align="right"><%=outstandingAsOnDateOfLodgement%>
  						  </div>
  						  </td>
  					  </tr>
  					  <%j++;%>
  					  </logic:iterate>
  					  </table></td>
                  </tr>
  				<tr>
  					<td colspan="4" class="SubHeading">
  						&nbsp;<bean:message key="mentionamountincludinginterest"/>
  					</td>
  				</tr>
  				<tr>
  					<td class="ColumnBackground">&nbsp;<bean:message key="dateofreleaseofwc"/></td>
  					<td class="TableData" colspan="3">
  						<bean:write property="claimapplication.dateOfReleaseOfWCStr" name="cpTcDetailsForm"/>
  					</td>
  				</tr>
  				<tr>
  				<td><br></tr>
  				</tr>
                  <tr> 
                    <td colspan="4" class="SubHeading">&nbsp;<bean:message key="securityandpersonalguaranteedtls"/></td>
                  </tr>
                  <tr> 
                    <td colspan="5"><table width="100%" border="0" cellspacing="1">
  					<tr>
  						<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="particulars"/></div></td>
  						<td class="ColumnBackground" colspan="2"><div align="center"><bean:message key="security"/></div></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="netwothofguarantors"/></div></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="reasonsforreductioninsecurity"/></div></td>
  					</tr>
  					<tr>
  						<td class="ColumnBackground"><div align="center"><bean:message key="nature"/></div></td>
  						<td class="ColumnBackground"><div align="center"><bean:message key="value"/></div></td>
  					</tr>
  					<tr>
  						<td class="TableData" rowspan="6"><div align="center"><bean:message key="asondateofsanctionofcredit"/></div></td>
  						<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfSanction.valueOfLand" name="cpTcDetailsForm"/>							
  						</div></td>
  						
  						<td class="TableData" rowspan="6"><div align="right">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfSanction.networthOfGuarantors" name="cpTcDetailsForm"/>
  						</div></td>
  						
  						<td class="TableData" rowspan="6"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfSanction.reasonsForReduction" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  				        <td class="TableData"><div align="center"><bean:message key="bldg"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfSanction.valueOfBuilding" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="machine"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfSanction.valueOfMachine" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="assets"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfSanction.valueOfOtherFixedMovableAssets" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="currentAssets"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfSanction.valueOfCurrentAssets" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="psOthers"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfSanction.valueOfOthers" name="cpTcDetailsForm"/>
  							
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData" rowspan="6"><div align="center"><bean:message key="asonthedateofnpa"/></div></td>
  						<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfNPA.valueOfLand" name="cpTcDetailsForm"/>
  						</div></td>
  						
  						<td class="TableData" rowspan="6"><div align="right">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfNPA.networthOfGuarantors" name="cpTcDetailsForm"/>						
  						</div></td>
  						
  						<td class="TableData" rowspan="6"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfNPA.reasonsForReduction" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="bldg"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfNPA.valueOfBuilding" name="cpTcDetailsForm"/>						
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="machine"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfNPA.valueOfMachine" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="assets"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfNPA.valueOfOtherFixedMovableAssets" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="currentAssets"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfNPA.valueOfCurrentAssets" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="psOthers"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfNPA.valueOfOthers" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData" rowspan="6"><div align="center"><bean:message key="asondateoflodgementofcredit"/></div></td>
  						<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfClaim.valueOfLand" name="cpTcDetailsForm"/>
  						</div></td>
  						
  						<td class="TableData" rowspan="6"><div align="right">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfClaim.networthOfGuarantors" name="cpTcDetailsForm"/>
  						</div></td>
  						
  						<td class="TableData" rowspan="6"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfClaim.reasonsForReduction" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="bldg"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfClaim.valueOfBuilding" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="machine"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfClaim.valueOfMachine" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="assets"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfClaim.valueOfOtherFixedMovableAssets" name="cpTcDetailsForm"/>						
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="currentAssets"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfClaim.valueOfCurrentAssets" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="psOthers"/></div></td>
  						
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfClaim.valueOfOthers" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
                    </table></td>
                  </tr>
  				<tr>
  				<td><br></td>
  				</tr>
  	            
  	       <tr> 
		 <td colspan="4" class="SubHeading">&nbsp;<bean:message key="recoverymadefromborrower"/></td>
	      </tr>
	      			<tr> 
	      				<td colspan="7"><table width="100%" border="0" cellspacing="1" cellpadding="0">
	      				<tr class="ColumnBackground">
	      					<td rowspan="2"><div align="center"><bean:message key="SerialNumber"/></div></td>
	      					<td rowspan="2" width="15%"><div align="center"><bean:message key="cgpan"/></div></td>
	      					<td colspan="2"><div align="center"><bean:message key="termloancompositloaninrs"/></div></td>
	      					<td colspan="2"><div align="center"><bean:message key="workingcapitalinrs"/></div></td>
	      					<td rowspan="2"><div align="center"><bean:message key="modeofrecovery"/></div></td>
	      				</tr>
	      				<tr class="ColumnBackground">
						<td><div align="center"><bean:message key="principal"/></div></td>
						<td><div align="center"><bean:message key="interestandothercharges"/></div></td>
						<td><div align="center"><bean:message key="amountincludinginterest"/></div></td>
						<td><div align="center"><bean:message key="othercharges"/></div></td>
					</tr>
					
					
					<%
					  int k = 1; 
					%>
					<logic:iterate property="claimapplication.recoveryDetails" id="object" name="cpTcDetailsForm">
					<% 
					  RecoveryDetails recDtl = (RecoveryDetails)object; 
					  cgpan = recDtl.getCgpan();
					  // System.out.println("CGPAN :" + cgpan);
					  modeOfRecovery = recDtl.getModeOfRecovery(); 
					  // System.out.println("Mode of Recovery :" + modeOfRecovery);
					  tcPrincipal = recDtl.getTcPrincipal();
					  // System.out.println("TCPrincipal :" + tcPrincipal);
					  tcInterestAndOtherCharges = recDtl.getTcInterestAndOtherCharges();
					  // System.out.println("TC Interest Charges :" + tcInterestAndOtherCharges);
					  wcAmount = recDtl.getWcAmount();
					  // System.out.println("WC Amount :" + wcAmount);
					  wcOtherCharges = recDtl.getWcOtherCharges();
					  // System.out.println("WC Other Charges :" + wcOtherCharges);
					%>  					
					  <tr>
						<td class="TableData"><div align="center">&nbsp;<%=k%></div></td>
						<td class="TableData"><div align="center">&nbsp;<%=cgpan%></div></td>

						<td class="TableData"><div align="right">
						<%=tcPrincipal%>
						</div></td>

						<td class="TableData"><div align="right">
						<%=tcInterestAndOtherCharges%>
						</div></td>

						<td class="TableData"><div align="right">
						<%=wcAmount%>TOT
						</div></td>

						<td class="TableData"><div align="right">
						<%=wcOtherCharges%>
						</div></td>

						<td class="TableData"><div align="center"><%=modeOfRecovery%>
						</div></td>
					  </tr>
					  <%k++;%>
					  </logic:iterate>
					
					
	      				</table></td>
  				<tr>
  					<td class="ColumnBackground">&nbsp;<bean:message key="ifrecoverydonethruots"/></td>
  					<td class="TableData" colspan="3">
  						<bean:write property="claimapplication.dateOfSeekingOTSStr" name="cpTcDetailsForm"/>
  					</td>
  				</tr>											
  				<tr>
  				<td><br></tr>
  				</tr>
  	            <tr> 
                    <td colspan="4" class="SubHeading">&nbsp;<bean:message key="totalamountforwhichguaranteeispreferred"/></td>
                  </tr>
  				<tr> 
  					<td colspan="4"><table width="100%" border="0" cellspacing="1" cellpadding="0">
  						<tr class="ColumnBackground">
  							<td><div align="center"><bean:message key="SerialNumber"/></div></td>
  							<td><div align="center"><bean:message key="cgpan"/></div></td>
  							<td><div align="center"><bean:message key="loanlimitcoveredundercgfsi"/></div></td>
  							<td><div align="center"><bean:message key="amountclaimedinrs"/></div></td>
  						</tr>
  						<%int m=1;%>
  						<logic:iterate property="claimapplication.claimSummaryDtls" name="cpTcDetailsForm" id="object">
  						<%
  						ClaimSummaryDtls clmSummaryDtl = (ClaimSummaryDtls)object;
             
  						cgpan = clmSummaryDtl.getCgpan();
  						String loanApprvd = clmSummaryDtl.getLimitCoveredUnderCGFSI();
  						clmAppliedAmnt = clmSummaryDtl.getAmount();
  						%>						
  						<tr>
  							<td class="TableData"><div align="center">&nbsp;<%=m%></div></td>
  							<td class="TableData"><div align="center">&nbsp;<%=cgpan%></div></td>
  							<td class="TableData"><div align="center">&nbsp;<%=loanApprvd%></div></td>
  							
  							<td class="TableData"><div align="right"><%=clmAppliedAmnt%></div></td>													
  						</tr>
  						<%m++;%>
             
  						</logic:iterate>
  					</table></td>
                  </tr>
                  <tr>
		  <td><br></td>
		  </tr>
		  <tr>
			<td class="ColumnBackground">&nbsp;<bean:message key="nameofofficial"/></td>
			<td colspan="3" class="TableData"><bean:write property="claimapplication.nameOfOfficial" name="cpTcDetailsForm"/></td>
		  </tr>                
		<tr>
		<td class="ColumnBackground">&nbsp;<bean:message key="designationOfOfficial"/></td>
		<td colspan="3" class="TableData"><bean:write property="claimapplication.designationOfOfficial" name="cpTcDetailsForm"/></td>
		</tr>
		<tr>
		<td class="ColumnBackground">&nbsp;<bean:message key="dateofclaimfiling"/></td>
		<td colspan="3" class="TableData" align="center"><bean:write property="claimapplication.claimSubmittedDateStr" name="cpTcDetailsForm"/></td>
		</tr>
		<tr>
		<td class="ColumnBackground">&nbsp;<bean:message key="place"/></td>
		<td colspan="3" class="TableData"><bean:write property="claimapplication.place"  name="cpTcDetailsForm"/></td>
		</tr>
		</table>
             </td>
          </tr>
        </table></td>
      <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
    </tr>


    <tr> 
        <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
        <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
          <div>
            <div align="center">  
            <% System.out.println("Final Claim Ref Number:"+claimreferencenumber); 
            String url = "displayClaimRefNumberDtls.do?method=displayClaimRefNumberDtls&"+ClaimConstants.CLM_CLAIM_REF_NUMBER+"="+claimreferencenumber; %>
            <A href="<%=url%>">
      <!--    <a href="javascript:submitForm('displayClaimRefNumberDtls.do?method=displayClaimRefNumberDtls&ClaimRefNumber="<%=claimreferencenumber%>"')">   -->
          <IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>            
            <A href="javascript:printpage()">
									<IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>
        </div></td>
        <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
    </tr>
</table>
</html:form>
</body>
</html>
