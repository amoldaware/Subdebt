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
<%@ page import = "java.text.SimpleDateFormat"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","proceedFromRecoveryFilterPage.do?method=proceedFromRecoveryFilterPage");%>
<%
String lastDsbrsmntDtStr;
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
String settDtStr;
%>

<html:errors/>
<html:form action="displayDisclaimerForSecInstallment.do?method=displayDisclaimerForSecInstallment" method="POST" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
    <td align="right" valign="bottom">
    <img src="images/TableLeftTop.gif" height="31"></td>
      <td background="images/TableBackground1.gif">
      <img src="images/ClaimsProcessingHeading.gif" height="25"></td>
    <td align="right" valign="top" background="images/TableBackground1.gif"> 
      <div align="right"></div></td>
    <td align="left" valign="bottom">
    <img src="images/TableRightTop.gif" height="31"></td>
  </tr>

  <tr> 
    <td background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>
		<table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="2" class="SubHeading">&nbsp;
                  <p>&nbsp; <div align="right"><bean:message key="subheading1a"/></div></td>
                </tr>
                <tr> 
                  <td colspan="2" class="SubHeading"><br> <div align="center"><bean:message key="subheading2"/></div></td>
                </tr>
                <tr> 
                  <td colspan="2" class="SubHeading"><br> <div align="center"><bean:message key="subheading3"/></div></td>
                </tr>
                <tr> 
                  <td colspan="2" class="SubHeading"><br><bean:message key="subheading4"/><br><br></td>
                </tr>
                <tr> 
                  <td colspan="2">
                  <table width="100%" border="0" cellspacing="0" cellpadding="0" height="25">
                      <tr> 
                        <td height="20" class="Heading">&nbsp;<bean:message key="claimtitle"/></td>
                        <td align="left" valign="bottom" height="20">
                        <img src="images/TriangleSubhead.gif" height="19"></td>
                      </tr>
                      <tr> 
                        <td colspan="2" class="Heading" height="5">
                        <img src="images/Clear.gif" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
                  <td class="ColumnBackground"  > <div align="left">&nbsp; 
                    <bean:message key="cpclaimrefnumber"/></div></td>
                    <td colspan="2">
                    <table width="100%" border="0" cellspacing="1" cellpadding="0">
                    <tr>
                  <td  class="TableData"> <div align="left"> &nbsp;
                    <bean:write property="claimapplication.claimRefNumber" name="cpTcDetailsForm"/></div></td>
                  <td class="ColumnBackground" >&nbsp;<bean:message key="date"/></td>
                  <td class="TableData"> &nbsp;<bean:write property="claimapplication.claimApprovedDtStr" name="cpTcDetailsForm"/> 
                  </td>
                  </tr>
                  </table></td>
                </tr>                
                <tr>
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="cgclannumber"/></div></td>
                  <td class="TableData" colspan="2"> <div align="left"> &nbsp;
                    <bean:write property="claimapplication.cgclan" name="cpTcDetailsForm"/></div></td>
                </tr>
                <tr> 
                  <td class="SubHeading" colspan="2">&nbsp;<bean:message key="dtlsOfOperatingOfficeAndLendingBranch"/></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground" > <div align="left">&nbsp;<bean:message key="memberId"/></div></td>
                  <td class="TableData" colspan="2"> <div align="left"> &nbsp;
                    <bean:write property="claimapplication.memberDetails.memberId" name="cpTcDetailsForm"/></div></td>
                </tr>
                
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="lendingbranchname"/></div></td>
                  <td colspan="2">
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                  <td class="TableData"> <div align="left"> &nbsp; 
                    <bean:write property="claimapplication.memberDetails.memberBankName" name="cpTcDetailsForm"/></div></td>
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="villagetown"/></div></td>
                  <td class="TableData"> <div align="left"> &nbsp;<bean:write property="claimapplication.memberDetails.city" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  </table>
                  </td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="district"/></div></td>
                  <td colspan="2">
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                  <td class="TableData"> <div align="left"> &nbsp;
                    <bean:write property="claimapplication.memberDetails.district" name="cpTcDetailsForm"/></div></td>
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="state"/></div></td>
                  <td class="TableData"> <div align="left"> &nbsp;<bean:write property="claimapplication.memberDetails.state" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  </table>
                  </td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="telephonenumber"/> </div></td>
                  <td class="TableData" colspan="2"> <div align="left"> &nbsp;
                    <bean:write property="claimapplication.memberDetails.telephone" name="cpTcDetailsForm"/></div></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="cpemail"/></div></td>
                  <td class="TableData" colspan="2"> <div align="left"> &nbsp; 
                    <bean:write property="claimapplication.memberDetails.email" name="cpTcDetailsForm"/></div></td>
                </tr>
		<tr> 
                  <td class="SubHeading" colspan="2"> &nbsp;<bean:message key="borrower/unitdetails"/></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="name"/></div></td>
                  <td class="TableData" colspan="2"> <div align="left"> &nbsp; 
                    <bean:write property="claimapplication.borrowerDetails.borrowerName" name="cpTcDetailsForm"/></div></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="completeaddress"/></div></td>
                  <td class="TableData" colspan="2"> <div align="left"> &nbsp;
                    <bean:write property="claimapplication.borrowerDetails.address" name="cpTcDetailsForm"/></div></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="district"/></div></td>
                  <td>
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                  <td class="TableData"> <div align="left"> &nbsp;
                    <bean:write property="claimapplication.borrowerDetails.district" name="cpTcDetailsForm"/></div></td>
                  <td class="ColumnBackground" colspan="2"> <div align="left">&nbsp;<bean:message key="state"/></div></td>
                  <td class="TableData"> <div align="left"> &nbsp;<bean:write property="claimapplication.borrowerDetails.state" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  </table></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="telephonenumber"/> </div></td>
                  <td class="TableData" colspan="2"> <div align="left"> &nbsp;
                    <bean:write property="claimapplication.borrowerDetails.telephoneNumber" name="cpTcDetailsForm"/></div></td>
                </tr>
				<tr> 
                  <td class="SubHeading" colspan="4"> &nbsp;<bean:message key="statusofaccounts"/></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dateofnpa"/></div></td>
                  <td class="TableData" colspan="3"> <div align="left"> &nbsp;
					  <bean:write property="claimapplication.dateOnWhichAccountClassifiedNPAStr" name="cpTcDetailsForm"/> </div></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dateofreporting"/></div></td>
                  <td class="TableData" colspan="3"> <div align="left"> &nbsp;
					  <bean:write property="claimapplication.dateOfReportingNpaToCgtsiStr" name="cpTcDetailsForm"/> </div></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="reasonfornpa"/></div></td>
                  <td class="TableData" colspan="3"> <div align="left"> &nbsp;
							<bean:write property="claimapplication.reasonsForAccountTurningNPA" name="cpTcDetailsForm"/></div></td>
                </tr>
                <tr>
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="whetherborroweriswiffuldefaulter"/></div></td>
                  <td class="TableData" colspan="3"> <div align="left">                  
					  &nbsp;<bean:write property="claimapplication.whetherBorrowerIsWilfulDefaulter" name="cpTcDetailsForm"/></div></td>
                </tr>
                <tr>
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dateofissueofrecallnotice"/></div></td>
                  <td class="TableData" colspan="3"> <div align="left">
					  &nbsp;
					  <bean:write property="claimapplication.dateOfIssueOfRecallNoticeStr" name="cpTcDetailsForm"/>
				  </div>
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
                  <td colspan="4" class="SubHeading">&nbsp;<bean:message key="cpdtlsofrecovryproceddings"/></td>
                </tr>
                <tr>
                  <td colspan="3" class="SubHeading">
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
					<tr>
						<td class="ColumnBackground">&nbsp;<bean:message key="forumthruwhichlegalproceedingsinitiated"/></td>
						<td class="TableData" colspan="3"> 
						<bean:write property="claimapplication.legalProceedingsDetails.forumRecoveryProceedingsInitiated" name="cpTcDetailsForm"/>                                                
						</td>
					</tr>
					<tr>
                        <td class="ColumnBackground">&nbsp;<bean:message key="suit/caseregnumber"/></td>
						<td class="TableData">
						<bean:write property="claimapplication.legalProceedingsDetails.suitCaseRegNumber" name="cpTcDetailsForm"/>
						</td>
						<td class="ColumnBackground">&nbsp;<bean:message key="cpdtoflegalproceedings"/></td>
						<td class="TableData"><bean:write property="claimapplication.legalProceedingsDetails.filingDateStr" name="cpTcDetailsForm"/>
						</td>
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
                        <td class="ColumnBackground">&nbsp;<bean:message key="amountClaimed"/></td>
						<td class="TableData" colspan="3">
							<div align="right"><bean:write property="claimapplication.legalProceedingsDetails.amountClaimed" name="cpTcDetailsForm"/> in Rs.</div>
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
						<td class="TableData" colspan="3">
							<bean:write property="claimapplication.legalProceedingsDetails.currentStatusRemarks" name="cpTcDetailsForm"/>
						</td>
					</tr>
					<tr>
                        <td class="ColumnBackground">&nbsp;<bean:message key="recoveryProceedingsConcluded"/></td>
						<td class="TableData" colspan="3">
                                                <bean:write property="claimapplication.legalProceedingsDetails.isRecoveryProceedingsConcluded" name="cpTcDetailsForm"/>
                                                </td>
					</tr>
                    <tr>
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dtofconclusionofrecoveryproceedings"/></div></td>
                  <td class="TableData" colspan="3"> <div align="left">
					  &nbsp;
					  <bean:write property="claimapplication.legalProceedingsDetails.dateOfConclusionOfRecoveryProceedingsStr" name="cpTcDetailsForm"/>
				  </div></td>
                    </tr>
                    <tr>
                  <td class="ColumnBackground"> <div align="left">&nbsp; 
                    <bean:message key="whtheraccwrittenoffbooks"/></div></td>
                  <td class="TableData" colspan="3"> 
                         <bean:write property="claimapplication.whetherAccntWrittenOffFromBooksOfMLI" name="cpTcDetailsForm"/>
				 </td>
                    </tr>
                    <tr>
                  <td class="ColumnBackground"> <div align="left">&nbsp; &nbsp;<bean:message key="dtonwhichaccwaswrittenoff"/></div></td>
                  <td class="TableData" colspan="3"> <div align="left">
					  &nbsp;
					  <bean:write property="claimapplication.dtOnWhichAccntWrittenOffStr" name="cpTcDetailsForm"/>
				  </div></td>
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
                  <td colspan="4" class="SubHeading">
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                      <tr>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="SerialNumber"/></td>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="cgpan"/></td>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="dateoflastdisbursement"/></td>
                        <td class="ColumnBackground" colspan="2"><div align="center" > &nbsp;<bean:message key="repayments"/></td>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="outstandingasondateofnpa"/></td>
						<td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="outstandingstatedinthecivilsuit"/></td>
						<td class="ColumnBackground" colspan="2"><div align="center">&nbsp;<bean:message key="osAsOnLodgementOfClaim"/></td>
					  </tr>
					  <tr>
						<td class="ColumnBackground"><div align="center"><bean:message key="principal"/></div></td>
						<td class="ColumnBackground"><div align="center"><bean:message key="interestandothercharges"/></div></td>
						<td class="ColumnBackground"><bean:message key="firstinstallment"/></td>
						<td class="ColumnBackground"><bean:message key="secondinstallment"/></td>
					  </tr>
					  <% int i=1;%>
  					  <logic:iterate property="claimapplication.termCapitalDtls" id="object" name="cpTcDetailsForm" scope="session">			
  					  <%
  					     TermLoanCapitalLoanDetail tlcldtl = (TermLoanCapitalLoanDetail)object;   					     
  					     String cgpan = tlcldtl.getCgpan();
  					     java.util.Date lastDsbrsmntDt = tlcldtl.getLastDisbursementDate();
					     if(lastDsbrsmntDt != null)
					     {
						 lastDsbrsmntDtStr = sdf.format(lastDsbrsmntDt);
					     }
					     else
					     {  					     
						 lastDsbrsmntDtStr = "";
					     }  					     
  					     double principalRepayment = tlcldtl.getPrincipalRepayment();
  					     double interestAndOtherCharges = tlcldtl.getInterestAndOtherCharges();
  					     double outstandingAsOnDateOfNPA = tlcldtl.getOutstandingAsOnDateOfNPA();
  					     double outstandingStatedInCivilSuit = tlcldtl.getOutstandingStatedInCivilSuit();
  					     double outstandingAsOnDateOfLodgement = tlcldtl.getOutstandingAsOnDateOfLodgement();
  					     double outstandingAsOnDateOfSecLodgement = tlcldtl.getOsAsOnDateOfLodgementOfClmForSecInstllmnt();
  					     
  					  %>  					          
					  <tr>
						  <td class="TableData">&nbsp;<%=i%></td>
						  <td class="TableData">
							&nbsp;<%=cgpan%></td>
						  <td class="TableData">
							  <%=lastDsbrsmntDtStr%>
  						  </td>
						  <td class="TableData">
							<div align="right"><%=principalRepayment%></div>
						  </td>
						  <td class="TableData">
							<div align="right"><%=interestAndOtherCharges%></div>
						  </td>
						  <td class="TableData">
							<div align="right"><%=outstandingAsOnDateOfNPA%></div>
						  </td>
						  <td class="TableData">
							<div align="right"><%=outstandingStatedInCivilSuit%></div>
						  </td>
						  <td class="TableData">
							<div align="right"><%=outstandingAsOnDateOfLodgement%></div>
						  </td>
						  <td class="TableData">
							<div align="right"><%=outstandingAsOnDateOfSecLodgement%></div>
						  </td>
					  </tr>	
					  <%i++;%>
  					  </logic:iterate>
					  </table></td>
                </tr>
				<tr>
					<td colspan="4" class="SubHeading">
						&nbsp;<bean:message key="mentiononlyprincipaloutstanding"/>
					</td>
				</tr>
				<tr>
				<td ><br></td>
				</tr>
	            <tr> 
                  <td colspan="4" class="SubHeading">&nbsp;<bean:message key="workingcapitaldetails"/></td>
                </tr>
				<tr>
                  <td colspan="4" class="SubHeading">
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                      <tr>
                        <td class="ColumnBackground" rowspan="2"><div align="center">&nbsp;<bean:message key="SerialNumber"/></td>
                        <td class="ColumnBackground"  rowspan="2"><div align="center">&nbsp;<bean:message key="cgpan"/></td>
                        <td class="ColumnBackground"  rowspan="2" ><div align="center">&nbsp;<bean:message key="oswcasonthedateofnpa"/></td>
						<td class="ColumnBackground"  rowspan="2" ><div align="center">&nbsp;<bean:message key="oswcstatedinthecivilsuit"/></td>
						
						<td class="ColumnBackground"  colspan="2" ><div align="center">&nbsp;<bean:message key="oswcasonthedateoflodgementofclaim"/></td>
					  </tr>
                      <tr>
						<td class="ColumnBackground" ><bean:message key="firstinstallment"/></td>
						<td class="ColumnBackground"><bean:message key="secondinstallment"/></td>
						
					  </tr>
					  <% int j = 1; %>
  					  <logic:iterate property="claimapplication.workingCapitalDtls" id="object" name="cpTcDetailsForm" scope="session">
  					  <% 
  					     WorkingCapitalDetail wcdtl = (WorkingCapitalDetail)object; 
  					     String cgpan = wcdtl.getCgpan();
  					     double outstandingAsOnDateOfNPA = wcdtl.getOutstandingAsOnDateOfNPA();
  					     double outstandingStatedInCivilSuit = wcdtl.getOutstandingStatedInCivilSuit();
  					     double outstandingAsOnDateOfLodgement = wcdtl.getOutstandingAsOnDateOfLodgement(); 
  					     double outstandingAsOnDateOfSecLodgement = wcdtl.getOsAsOnDateOfLodgementOfClmForSecInstllmnt();
  					  %>  					  			  		  
					  <tr>
						  <td class="TableData">&nbsp;<%=j%></td>
						  <td class="TableData">&nbsp;<%=cgpan%></td>
						  <td class="TableData"><div align="right">
							<%=outstandingAsOnDateOfNPA%></div>
						  </td>
						  <td class="TableData"><div align="right">
							<%=outstandingStatedInCivilSuit%></div>
						  </td>
						  <td class="TableData">
							<div align="right"><%=outstandingAsOnDateOfLodgement%></div>
						  <td class="TableData">
							<div align="right"><%=outstandingAsOnDateOfSecLodgement%></div>
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
				<td>&nbsp;</td>
				</tr>
                <tr>
                  <td colspan="4" class="SubHeading">&nbsp;<bean:message key="recoverymadefromborrower"/></td>
                </tr>
                <tr>
					<td colspan="4">
                    <table width="100%" border="0" cellspacing="1" cellpadding="0">
						<tr class="ColumnBackground">
							<td rowspan="2"><div align="center"><bean:message key="SerialNumber"/></div></td>
							<td rowspan="2"><div align="center"><bean:message key="cgpan"/></div></td>
							<td colspan="2"><div align="center"><bean:message key="termloancompositloaninrs"/></div></td>
							<td colspan="2"><div align="center"><bean:message key="workingcapitalinrs"/></div></td>
							<td rowspan="2"><div align="center"><bean:message key="modeofrecovery"/></div></td>
						</tr>
						<tr class="ColumnBackground">
							<td><div align="center"><bean:message key="principal"/></div></td>
							<td><div align="center"><bean:message key="interestandothercharges"/></div></td>
							<td ><div align="center"><bean:message key="amountincludinginterest"/></div></td>
							<td><div align="center"><bean:message key="othercharges"/></div></td>
						</tr>
						<%int k = 1; %>
					<logic:iterate property="claimapplication.recoveryDetails" id="object" name="cpTcDetailsForm">
					<% 
					  RecoveryDetails recDtl = (RecoveryDetails)object; 
					  String cgpan = recDtl.getCgpan();
					  // System.out.println("CGPAN :" + cgpan);
					  String modeOfRecovery = recDtl.getModeOfRecovery(); 
					  // System.out.println("Mode of Recovery :" + modeOfRecovery);
					  double tcPrincipal = recDtl.getTcPrincipal();
					  // System.out.println("TCPrincipal :" + tcPrincipal);
					  double tcInterestAndOtherCharges = recDtl.getTcInterestAndOtherCharges();
					  // System.out.println("TC Interest Charges :" + tcInterestAndOtherCharges);
					  double wcAmount = recDtl.getWcAmount();
					  // System.out.println("WC Amount :" + wcAmount);
					  double wcOtherCharges = recDtl.getWcOtherCharges();
					  // System.out.println("WC Other Charges :" + wcOtherCharges);
					%>  					
					  <tr>
						<td class="TableData"><div align="center">&nbsp;<%=k%></div></td>
						<td class="TableData"><div align="center">&nbsp;<%=cgpan%></div></td>
						<td class="TableData"><div align="center">
							<%=tcPrincipal%>
						</div></td>
						<td class="TableData"><div align="right">
							<%=tcInterestAndOtherCharges%>
						</div></td>
						<td class="TableData"><div align="right">
							<%=wcAmount%>
						</div></td>
						<td class="TableData" ><div align="right">
							<%=wcOtherCharges%>
						</div></td>
						<td class="TableData"><div align="right">
						    <%=modeOfRecovery%>
						</div></td>
					  </tr>
                                          <%k++;%>
  					  </logic:iterate>
					</table></td>
                </tr>
		<tr>
			<td colspan="4" class="SubHeading">
				&nbsp;<bean:message key="noteaboutmodeofrecovery"/>
			</td>
		</tr>

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
                    <td colspan="4" class="SubHeading">&nbsp;<bean:message key="securityandpersonalguaranteedtls"/></td>
                  </tr>
                  <tr> 
                    <td colspan="5"><table width="100%" border="0" cellspacing="1">
  					<tr>
  						<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="particulars"/></div></td>
  						<td class="ColumnBackground" colspan="2"><div align="center"><bean:message key="security"/></div></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="amntrealizedthrudisposalofsec"/></div></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="netwothofguarantors"/></div></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="amntrealizedthruinvocationofperguarantees"/></div></td>
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
  						<td rowspan="6" class="TableData">&nbsp;</td>
  						<td class="TableData" rowspan="6"><div align="right">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfSanction.networthOfGuarantors" name="cpTcDetailsForm"/>
  						</div></td>
  						<td rowspan="6" class="TableData">&nbsp;</td>
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
  						<td rowspan="6" class="TableData">&nbsp;</td>
  						<td class="TableData" rowspan="6"><div align="right">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfNPA.networthOfGuarantors" name="cpTcDetailsForm"/>						
  						</div></td>
  						<td rowspan="6" class="TableData">&nbsp;</td>
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
  						<td class="TableData" rowspan="6"><div align="center"><bean:message key="asondateoflodgementoffirstclaim"/></div></td>
  						<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
  						<td class="TableData"><div align="center">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfClaim.valueOfLand" name="cpTcDetailsForm"/>
  						</div></td>
  						<td rowspan="6" class="TableData">&nbsp;</td>
  						<td class="TableData" rowspan="6"><div align="right">
  						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfClaim.networthOfGuarantors" name="cpTcDetailsForm"/>
  						</div></td>
  						<td rowspan="6" class="TableData">&nbsp;</td>
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
  					  					  					  					  					  				
					<tr>
						<td class="TableData" rowspan="6"><div align="center"><bean:message key="asondateoflodgementofsecondclaim"/></div></td>
						<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
						<td class="TableData"><div align="center">
						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.valueOfLand" name="cpTcDetailsForm"/>						
						</div></td>
						<td class="TableData">
						<div align="right"><bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.amtRealisedLand" name="cpTcDetailsForm"/></div>
						</td>
						<td class="TableData" rowspan="6"><div align="right">
						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.networthOfGuarantors" name="cpTcDetailsForm"/>
						</div></td>						
						<td rowspan="6" class="TableData">&nbsp;<div align="right"><bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.amtRealisedPersonalGuarantee" name="cpTcDetailsForm"/></div></td>
						<td class="TableData"><div align="center">
						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.reasonsForReductionLand" name="cpTcDetailsForm"/>
						</div></td>
					</tr>
					<tr>
						<td class="TableData"><div align="center"><bean:message key="bldg"/></div></td>
						<td class="TableData"><div align="center">
						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.valueOfBuilding" name="cpTcDetailsForm"/>
						</div></td>
						<td class="TableData">
						<div align="right"><bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.amtRealisedBuilding" name="cpTcDetailsForm"/></div>
						</td>
						<td class="TableData"><div align="center">
						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.reasonsForReductionBuilding" name="cpTcDetailsForm"/>
						</div></td>
					</tr>
					<tr>
						<td class="TableData"><div align="center"><bean:message key="machine"/></div></td>
						<td class="TableData"><div align="center">
						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.valueOfMachine" name="cpTcDetailsForm"/>
						</div></td>
						<td class="TableData">
						<div align="right"><bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.amtRealisedMachine" name="cpTcDetailsForm"/></div>
						</td>
						<td class="TableData"><div align="center">
						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.reasonsForReductionMachine" name="cpTcDetailsForm"/>
						</div></td>
					</tr>
					<tr>
						<td class="TableData"><div align="center"><bean:message key="assets"/></div></td>
						<td class="TableData"><div align="center">
						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.valueOfOtherFixedMovableAssets" name="cpTcDetailsForm"/>
						</div></td>
						<td class="TableData">
						<div align="right"><bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.amtRealisedFixed" name="cpTcDetailsForm"/></div>
						</td>
						<td class="TableData"><div align="center">
						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.reasonsForReductionFixed" name="cpTcDetailsForm"/>
						</div></td>
					</tr>
					<tr>
						<td class="TableData"><div align="center"><bean:message key="currentAssets"/></div></td>
						<td class="TableData"><div align="center">
						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.valueOfCurrentAssets" name="cpTcDetailsForm"/>
						</div></td>
						<td class="TableData">
						<div align="right"><bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.amtRealisedCurrent" name="cpTcDetailsForm"/></div>
						</td>
						<td class="TableData"><div align="center">
						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.reasonsForReductionCurrent" name="cpTcDetailsForm"/>
						</div></td>
					</tr>
					<tr>
						<td class="TableData"><div align="center"><bean:message key="psOthers"/></div></td>
						<td class="TableData"><div align="center">
						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.valueOfOthers" name="cpTcDetailsForm"/>
						</div></td>
						<td class="TableData">
						<div align="right"><bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.amtRealisedOthers" name="cpTcDetailsForm"/></div>
						</td>
						<td class="TableData"><div align="center">
						<bean:write property="claimapplication.securityAndPersonalGuaranteeDtls.detailsAsOnDateOfLodgementOfSecondClaim.reasonsForReductionOthers" name="cpTcDetailsForm"/>
						</div></td>
					</tr>  					  					  					  					  					  					  					  					
                    </table></td>
                  </tr>
				<tr>
				<td><br></tr>
				</tr>
	            <tr> 
                  <td  class="SubHeading">&nbsp;<bean:message key="totalamountforwhichguaranteeispreferred"/></td>
                  <td><i><div align="right">(All amounts to be in Rs.)</div></i></td>
                </tr>
				<tr> 
					<td colspan="4">
                    <table width="100%" border="0" cellspacing="1" cellpadding="0">
						<tr class="ColumnBackground">
							<td><div align="center"><bean:message key="SerialNumber"/></div></td>
							<td><div align="center"><bean:message key="cgpan"/></div></td>
							<td><div align="center">Type of facility (TL/WC)</div></td>
							<td><div align="center"><bean:message key="loanlimitcoveredundercgfsi"/></div></td>
							<td><div align="center">Amount Claimed in first installment of Claim</div></td>
							<td><div align="center">Amount settled by CGTSI, if any</div></td>
							<td><div align="center">Date of Settlement of first installment of claim by CGTSI, if any</div></td>							
							<td><div align="center">Amount Claimed in second and final installment</div></td>
						</tr>
						<%int m=1;%>
						<logic:iterate property="updatedClaimDtls" name="cpTcDetailsForm" id="object">
						<%
						java.util.HashMap hashmap =(java.util.HashMap)object;
						
						String cgpn = (String)hashmap.get(ClaimConstants.CLM_CGPAN);						
						String loanType = (String)hashmap.get(ClaimConstants.CGPAN_LOAN_TYPE);
						double loanLimit = ((Double)hashmap.get(ClaimConstants.CGPAN_APPRVD_AMNT)).doubleValue();																		
						double appliedClmAmnt = 0.0;
						if(hashmap.containsKey(ClaimConstants.CGPAN_CLM_APPLIED_AMNT))
						{
							appliedClmAmnt = ((Double)hashmap.get(ClaimConstants.CGPAN_CLM_APPLIED_AMNT)).doubleValue();
						}
						double settlementAmnt = ((Double)hashmap.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_AMNT)).doubleValue();
						java.util.Date settlementDt = (java.util.Date)hashmap.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT);
						if(settlementDt != null)
						{
						      settDtStr = sdf.format(settlementDt);
						}
						else
						{
						      settDtStr = "";
						}
						double secClmAppAmt = ((Double)hashmap.get(ClaimConstants.CGPAN_SEC_CLM_APPLIED_AMNT)).doubleValue();
						%>						
						<tr>
							<td class="TableData"><div align="center">&nbsp;<%=m%></div></td>
							<td class="TableData"><div align="center">
                                                         &nbsp;<%=cgpn%></div></td>
							<td class="TableData"><div align="center">
								<%=loanType%></div></td>
                                                        <td class="TableData"><div align="right">
								<%=loanLimit%></div></td>								
							<td class="TableData"><div align="right">
								<%=appliedClmAmnt%></div></td>
							<td class="TableData"><div align="right">
								<%=settlementAmnt%></div></td>
							<td class="TableData"><div align="center">
								<%=settDtStr%></div></td>								
							<td class="TableData"><div align="right">
							    <%=secClmAppAmt%>
							</div></td>
						</tr>				
						<%m++;%>
  						</logic:iterate>
					</table></td>
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
    <td  background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td align="right" valign="bottom">
      <img src="images/TableLeftBottom.gif" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center">
          <a href="javascript:submitForm('displayListOfClaimRefNumbers.do?method=displayListOfClaimRefNumbers')"><img src="images/Back.gif" alt="Submit" width="49" height="37" border="0"></a>            
          </div>
      </div></td>
      <td  align="right" valign="bottom">
      <img src="images/TableRightBottom.gif" height="51"></td>
  </tr>
</table>
</html:form>
</body>
</html>