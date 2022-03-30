<%@ page language="java"%>
<%@ page import="com.cgtsi.claim.ClaimConstants"%>
<%@ page import = "com.cgtsi.actionform.ClaimActionForm"%>
<%@ page import = "java.util.HashMap"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","proceedFromRecoveryFilterPage.do?method=proceedFromRecoveryFilterPage");%>
<%
String claimRefNumber ="clmDtlForFirstInstllmnt.".trim() + ClaimConstants.CLM_CLAIM_REF_NUMBER;
String cgclanNumber = "clmDtlForFirstInstllmnt.".trim() + ClaimConstants.CLM_CGCLAN;
String firstSettlmntDt = "clmDtlForFirstInstllmnt.".trim() + ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT;
String cgpan = "";
String hiddencgpan = "";
String dsbrsmntdt = "";
String principal = "";
String interestCharges = "";
String osAsOnNpa = "";
String osAsStatedinCivilSuit = "";
String osAsOnLodgementOfClm = "";
String osAsOnLodgementOfSecondClm = "";
String osAsOnNPADate = null;
String osAsInCivilSuit = null;
String osAsInFirstClmLodgement = null;
String wcOsAtLdgmntClm = "";
String wcOsAtLdgmntOfSecondClm = "";
String wcOsAsOnInCivilSuit = "";
String wcAsOnNPA = "";
String wccgpan = "";
String hidencgpan = "";
String tcPrincipal1 = "";
String tcInterestCharges1 = "";
String wcAmount1 = "";
String wcOtherCharges1 = "";
String recMode = "";
String cgpantodisplay = "";
String landAsOnDtOfSnctnDtl = "";
String netwrthAsOnDtOfSnctn = "";
String reasonReductionDtSnctn = "";
String bldgAsOnDtOfSnctn = "";
String machinecAsOnDtOfSnctn = "";
String otherAssetsAsOnDtOfSnctn = "";
String currAssetsAsOnDtOfSnctn = "";
String otherValAsOnDtOfSnctn = "";
String landAsOnDtOfNPA = "";
String netwrthAsOnDtOfNPA = "";
String rsnRdctnDtSnctnAsOnNPA = "";
String bldgAsOnDtOfNPA = "";
String machinecAsOnDtOfNPA = "";
String otherAssetsAsOnDtOfNPA = "";
String currAssetsAsOnDtOfNPA = "";
String otherValAsOnDtOfNPA = "";
String landAsOnLodgemnt = "";
String netwrthAsOnLodgemnt = "";
String rsnRdctnDtSnctnAsOnLodgemnt = "";
String bldgAsOnDtOfLodgemnt = "";
String machinecAsOnLodgemnt = "";
String otherAssetsAsOnLodgemnt = "";
String currAssetsAsOnLodgemnt = "";
String otherValAsOnLodgemnt = "";
String landAsOnLodgemntOfSecClm = "";
String netwrthAsOnLodgemntOfSecClm = "";
String rsnRdctnDtSnctnAsOnLodgemntOfSecClm = "";
String bldgAsOnDtOfLodgemntOfSecClm = "";
String machinecAsOnLodgemntOfSecClm = "";
String otherAssetsAsOnLodgemntOfSecClm = "";
String currAssetsAsOnLodgemntOfSecClm = "";
String otherValAsOnLodgemntOfSecClm = "";
java.util.HashMap hashmap = null;
String cgpn = "";
String loanType = "";
double loanLimit = 0.0;
double appliedClmAmnt = 0.0;
String amountClaimed = "";
double settlementAmnt = 0.0;
java.util.Date settlementDt = null;
String reasonForTurningNPA = "";
String npaReportingDt = "";
String npaClassifiedDt = "";
String amntRealizedThruDisposalOfSecurityStr = "";
String settlementDtStr = "";
java.util.Date dsbrsDt = null;
String repaidStr = "";
String tcfield = "TC".trim();
String wcfield = "WC".trim();
%>
<body onLoad="setCPOthersEnabled()"/>
<%
String focusField="dateOfRecallNotice";
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
    focusField="test";
}
%>
<html:form action="displayDisclaimerForSecInstallment.do?method=displayDisclaimerForSecInstallment" method="POST" focus="<%=focusField%>" enctype="multipart/form-data">
<html:errors />
<html:hidden name="cpTcDetailsForm" property="test"/>
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
    <DIV align="right">			
      		<A HREF="javascript:submitForm('helpEnterClaimSecondApplication.do')">
      	        HELP</A>
      </DIV>        
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
                    <bean:write property="<%=claimRefNumber%>" name="cpTcDetailsForm"/></div></td>
                  <td class="ColumnBackground" >&nbsp;<bean:message key="date"/></td>
                  <td class="TableData"> &nbsp;<bean:write property="<%=firstSettlmntDt%>" name="cpTcDetailsForm"/> 
                  </td>
                  </tr>
                  </table></td>
                </tr>                
                <tr>
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="cgclannumber"/></div></td>
                  <td class="TableData" colspan="2"> <div align="left"> &nbsp;
                    <bean:write property="<%=cgclanNumber%>" name="cpTcDetailsForm"/></div></td>
                </tr>
                <tr> 
                  <td class="SubHeading" colspan="2">&nbsp;<bean:message key="dtlsOfOperatingOfficeAndLendingBranch"/></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground" > <div align="left">&nbsp;<bean:message key="memberId"/></div></td>
                  <td class="TableData" colspan="2"> <div align="left"> &nbsp;
                    <bean:write property="memberDetails.memberId" name="cpTcDetailsForm"/></div></td>
                </tr>
                
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="lendingbranchname"/></div></td>
                  <td colspan="2">
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                  <td class="TableData"> <div align="left"> &nbsp; 
                    <bean:write property="memberDetails.memberBankName" name="cpTcDetailsForm"/></div></td>
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="villagetown"/></div></td>
                  <td class="TableData"> <div align="left"> &nbsp;<bean:write property="memberDetails.city" name="cpTcDetailsForm"/></div></td>
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
                    <bean:write property="memberDetails.district" name="cpTcDetailsForm"/></div></td>
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="state"/></div></td>
                  <td class="TableData"> <div align="left"> &nbsp;<bean:write property="memberDetails.state" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  </table>
                  </td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="telephonenumber"/> </div></td>
                  <td class="TableData" colspan="2"> <div align="left"> &nbsp;
                    <bean:write property="memberDetails.telephone" name="cpTcDetailsForm"/></div></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="cpemail"/></div></td>
                  <td class="TableData" colspan="2"> <div align="left"> &nbsp; 
                    <bean:write property="memberDetails.email" name="cpTcDetailsForm"/></div></td>
                </tr>
		<tr> 
                  <td class="SubHeading" colspan="2"> &nbsp;<bean:message key="borrower/unitdetails"/></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="name"/></div></td>
                  <td class="TableData" colspan="2"> <div align="left"> &nbsp; 
                    <bean:write property="borrowerDetails.borrowerName" name="cpTcDetailsForm"/></div></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="completeaddress"/></div></td>
                  <td class="TableData" colspan="2"> <div align="left"> &nbsp;
                    <bean:write property="borrowerDetails.address" name="cpTcDetailsForm"/></div></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="district"/></div></td>
                  <td>
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                  <td class="TableData"> <div align="left"> &nbsp;
                    <bean:write property="borrowerDetails.district" name="cpTcDetailsForm"/></div></td>
                  <td class="ColumnBackground" colspan="2"> <div align="left">&nbsp;<bean:message key="state"/></div></td>
                  <td class="TableData"> <div align="left"> &nbsp;<bean:write property="borrowerDetails.state" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  </table></td>
                </tr>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="telephonenumber"/> </div></td>
                  <td class="TableData" colspan="2"> <div align="left"> &nbsp;
                    <bean:write property="borrowerDetails.telephoneNumber" name="cpTcDetailsForm"/></div></td>
                </tr>
				<tr> 
                  <td class="SubHeading" colspan="4"> &nbsp;<bean:message key="statusofaccounts"/></td>
                </tr>
		  <%                    
		    npaClassifiedDt = "npaDetails.".trim() +ClaimConstants.NPA_CLASSIFIED_DT;
		  %>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dateofnpa"/></div></td>
                  <td class="TableData" colspan="3"> <div align="left"> &nbsp;
					  <bean:write property="<%=npaClassifiedDt%>" name="cpTcDetailsForm"/> </div></td>
                </tr>
                 <%
		   npaReportingDt = "npaDetails.".trim() + ClaimConstants.NPA_REPORTING_DT;
                 %>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dateofreporting"/></div></td>
                  <td class="TableData" colspan="3"> <div align="left"> &nbsp;
					  <bean:write property="<%=npaReportingDt%>" name="cpTcDetailsForm"/> </div></td>
                </tr>
                <%
		  reasonForTurningNPA = "npaDetails.".trim() + ClaimConstants.REASONS_FOR_TURNING_NPA;
                %>
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="reasonfornpa"/></div></td>
                  <td class="TableData" colspan="3"> <div align="left"> &nbsp;
							<bean:write property="<%=reasonForTurningNPA%>" name="cpTcDetailsForm"/></div></td>
                </tr>
                <tr>
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="whetherborroweriswiffuldefaulter"/></div></td>
                  <td class="TableData" colspan="3"> <div align="left">
					  &nbsp;<html:radio property="wilfullDefaulter" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_YES_FLAG%>"><bean:message key="otsyes"/></html:radio>
	  <html:radio property="wilfullDefaulter" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_NO_FLAG%>"><bean:message key="otsno"/></html:radio></div></td>
                </tr>
                
                 <!--                                
                <tr>
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dateofissueofrecallnotice"/></div></td>
                  <td class="TableData" colspan="3"> <div align="left">
					  &nbsp;
					  <html:text property="dateOfRecallNotice" name="cpTcDetailsForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dateOfRecallNotice')" align="center">
				  </div></td>
                </tr>
                -->                                                               
                <tr> 
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dateofissueofrecallnotice"/></div></td>
                  <td>
                  <table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr>
                  <td class="TableData"> <div align="left"> &nbsp;
                    <html:text property="dateOfRecallNotice" name="cpTcDetailsForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dateOfRecallNotice')" align="center"></div></td>
                  <td class="ColumnBackground" colspan="2"> <div align="left">&nbsp;<bean:message key="uploadfile"/></div></td>
                  <td class="TableData"> <div align="left"> &nbsp;<html:file property="recallnoticefilepath" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  </table></td>
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
						<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="forumthruwhichlegalproceedingsinitiated"/></td>
						<td class="TableData" colspan="3"> 
							<html:select property="forumthrulegalinitiated" name="cpTcDetailsForm" onchange="setCPOthersEnabled()">
								<html:option value="">Select</html:option>
								<html:option value="Civil Court">Civil Court</html:option>
								<html:option value="DRT">DRT</html:option>
								<html:option value="LokAdalat">Lok Adalat</html:option>
								<html:option value="Revenue Recovery Autority">Revenue Recovery Autority</html:option>
								<html:option value="Securitisation Act ">Securitisation Act, 2002</html:option>
								<html:option value="Others">Others</html:option>
							</html:select>&nbsp;&nbsp;&nbsp;<bean:message key="others"/><html:text property="otherforums" name="cpTcDetailsForm" disabled="true"/>
						</td>
					</tr>
					<tr>
                        <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="suit/caseregnumber"/></td>
						<td class="TableData"><html:text property="caseregnumber" name="cpTcDetailsForm"/></td>
						<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="cpdtoflegalproceedings"/></td>
						<td class="TableData"><html:text property="legaldate" name="cpTcDetailsForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.legaldate')" align="center"></td>
					</tr>
					<tr>
                        <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="nameoftheforum"/></td>
						<td class="TableData">
							<html:text property="nameofforum" name="cpTcDetailsForm" maxlength="50"/>
						</td>
                        <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="location"/></td>
						<td class="TableData">
							<html:text property="location" name="cpTcDetailsForm" maxlength="50"/>
						</td>
					</tr>
					<tr>
                        <td class="ColumnBackground">&nbsp;<bean:message key="amountClaimed"/></td>
						<td class="TableData">
							<html:text property="amountclaimed" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/> in Rs.
						</td>
							  <td class="ColumnBackground">&nbsp;Any Attachments</td>
										<td class="TableData">
											<html:file property="legalAttachmentPath" name="cpTcDetailsForm"/>
										</td>												
					</tr>
					<tr>
                        <td class="ColumnBackground">&nbsp;<bean:message key="currentStatus"/></td>
						<td class="TableData" colspan="3">
							<html:text property="currentstatusremarks" name="cpTcDetailsForm" maxlength="4000"/>
						</td>
					</tr>
					<tr>
                        <td class="ColumnBackground">&nbsp;<bean:message key="recoveryProceedingsConcluded"/></td>
						<td class="TableData" colspan="3">
							<html:radio property="proceedingsConcluded"  name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_YES_FLAG%>" onclick="javascript:disableDtOfRecProc(this)"><bean:message key="otsyes"/></html:radio> 
				  			<html:radio property="proceedingsConcluded" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_NO_FLAG%>" onclick="javascript:disableDtOfRecProc(this)"><bean:message key="otsno"/></html:radio>
						</td>
					</tr>
                    <tr>
                  <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dtofconclusionofrecoveryproceedings"/></div></td>
                  <td class="TableData" colspan="3"> <div align="left">
					  &nbsp;
					  <html:text property="dtOfConclusionOfRecoveryProc" name="cpTcDetailsForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dtOfConclusionOfRecoveryProc')" align="center">
				  </div></td>
                    </tr>
                    <tr>
                  <td class="ColumnBackground"> <div align="left">&nbsp; 
                    <bean:message key="whtheraccwrittenoffbooks"/></div></td>
                  <td class="TableData" colspan="3"> 
				<html:radio property="whetherAccntWasWrittenOffBooks"  name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_YES_FLAG%>" onclick="javascript:disableDtOfAccWrittenOff(this)"><bean:message key="otsyes"/></html:radio> 
				  			<html:radio property="whetherAccntWasWrittenOffBooks" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_NO_FLAG%>" onclick="javascript:disableDtOfAccWrittenOff(this)"><bean:message key="otsno"/></html:radio> </td>
                    </tr>
                    <tr>
                  <td class="ColumnBackground"> <div align="left">&nbsp; &nbsp;<bean:message key="dtonwhichaccwaswrittenoff"/></div></td>
                  <td class="TableData" colspan="3"> <div align="left">
					  &nbsp;
					  <html:text property="dtOnWhichAccntWrittenOff" name="cpTcDetailsForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dtOnWhichAccntWrittenOff')" align="center">
				  </div></td>
                    </tr>
                    </table></td>
                </tr>
				<tr>
				<td><br></td>
				</tr>
		 <logic:notEqual property="tcCounter" value="0" name="cpTcDetailsForm">
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
					  <logic:iterate property="tcCgpansVector" id="object" name="cpTcDetailsForm" scope="session">			
					  <% java.util.HashMap mp = (java.util.HashMap)object;  					  
  					     cgpan = (java.lang.String)mp.get(ClaimConstants.CLM_CGPAN); 
  					     dsbrsDt = (java.util.Date)mp.get(ClaimConstants.CLM_LAST_DISBURSEMENT_DT);
  					     java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
  					     String dateStr = "";
  					     if(dsbrsDt != null)
  					     {
  					          dateStr = sdf.format(dsbrsDt);
  					     }
  					     Double repaidAmnt = (Double)mp.get(ClaimConstants.TOTAL_AMNT_REPAID);
  					     if(repaidAmnt != null)
  					     {
  					     	repaidStr = repaidAmnt.toString();
  					     }
                                             else
                                             {
                                                repaidStr = "0.0";
                                             }
					     hiddencgpan = "cgpandetails(key-"+i+")";					  					  
					     osAsOnNPADate = ((Double)mp.get(ClaimConstants.CLM_OS_AS_ON_NPA)).toString();
					     osAsInCivilSuit = ((Double)mp.get(ClaimConstants.CLM_OS_AS_ON_CIVIL_SUIT)).toString();
					     osAsInFirstClmLodgement = ((Double)mp.get(ClaimConstants.CLM_OS_AS_IN_CLM_LODGMNT)).toString();
					  %>
					  <html:hidden property="<%=hiddencgpan%>" name="cpTcDetailsForm" value="<%=cgpan%>"/>
					  <tr>
						  <td class="TableData">&nbsp;<%=i%></td>
						  <td class="TableData">
							&nbsp;<%=cgpan%></td>
						  <%
    						  dsbrsmntdt = "lastDisbursementDate(key-"+i+")";						  
  						  %>
						  <td class="TableData">
							  <html:text property="<%=dsbrsmntdt%>" value="<%=dateStr%>" name="cpTcDetailsForm" maxlength="10"/><!-- <img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.<%=dsbrsmntdt%>')" align="center"> -->
  						  </td>
  						  <%
						  principal="tcprincipal(key-"+i+")";						  
  						  %>
						  <td class="TableData">
							<html:text property="<%=principal%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						  </td>
						  <%
						  interestCharges="tcInterestCharge(key-"+i+")";
  						  %>
						  <td class="TableData">
							<html:text property="<%=interestCharges%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						  </td>
						  <%
						  osAsOnNpa="tcOsAsOnDateOfNPA(key-"+i+")";
  						  %>
						  <td class="TableData">
							<html:text property="<%=osAsOnNpa%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						  </td>
						  <%
						  osAsStatedinCivilSuit="tcOsAsStatedInCivilSuit(key-"+i+")";
  						  %>
						  <td class="TableData">
							<html:text property="<%=osAsStatedinCivilSuit%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						  </td>
						  <%
						  osAsOnLodgementOfClm="tcOsAsOnLodgementOfClaim(key-"+i+")";
  						  %>
						  <td class="TableData">
							<html:text property="<%=osAsOnLodgementOfClm%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
						  <%
						  osAsOnLodgementOfSecondClm = "tcOsAsOnLodgementOfSecondClaim(key-"+i+")";
						  %>
						  <td class="TableData">
							<html:text property="<%=osAsOnLodgementOfSecondClm%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
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
		</logic:notEqual>
				<tr>
				<td ><br></td>
				</tr>
		<logic:notEqual property="wcCounter" value="0" name="cpTcDetailsForm">
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
					  <logic:iterate property="wcCgpansVector" id="object" name="cpTcDetailsForm" scope="session">
					  <% 
					     // wccgpan = (java.lang.String)object; 
					     hidencgpan = "wcCgpan(key-"+j+")";					  					  
					     java.util.HashMap mp = (java.util.HashMap)object;
					     wccgpan = (String)mp.get(ClaimConstants.CLM_CGPAN);
					     osAsOnNPADate = ((Double)mp.get(ClaimConstants.CLM_OS_AS_ON_NPA)).toString();
					     osAsInCivilSuit = ((Double)mp.get(ClaimConstants.CLM_OS_AS_ON_CIVIL_SUIT)).toString();
					     osAsInFirstClmLodgement = ((Double)mp.get(ClaimConstants.CLM_OS_AS_IN_CLM_LODGMNT)).toString();					     
					  %>
			  		  <html:hidden property="<%=hidencgpan%>" name="cpTcDetailsForm" value="<%=wccgpan%>"/>
					  <tr>
						  <td class="TableData">&nbsp;<%=j%></td>
						  <td class="TableData">&nbsp;<%=wccgpan%></td>
						  <%
						      wcAsOnNPA = "wcOsAsOnDateOfNPA(key-"+j+")";
  						  %>
						  <td class="TableData"><div align="center">
							<html:text property="<%=wcAsOnNPA%>" name="cpTcDetailsForm" value="<%=osAsOnNPADate%>" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></div>
						  </td>
						  <%
						      wcOsAsOnInCivilSuit = "wcOsAsStatedInCivilSuit(key-"+j+")";
  						  %>
						  <td class="TableData"><div align="center">
							<html:text property="<%=wcOsAsOnInCivilSuit%>" name="cpTcDetailsForm" value="<%=osAsInCivilSuit%>" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></div>
						  </td>
						  <%
						      wcOsAtLdgmntClm= "wcOsAsOnLodgementOfClaim(key-"+j+")";
  						  %>
						  <td class="TableData">
							<html:text property="<%=wcOsAtLdgmntClm%>" name="cpTcDetailsForm" value="<%=osAsInFirstClmLodgement%>" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
						  <%
						      wcOsAtLdgmntOfSecondClm = "wcOsAsOnLodgementOfSecondClaim(key-"+j+")";
						  %>
						  <td class="TableData">
							<html:text property="<%=wcOsAtLdgmntOfSecondClm%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>						  
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
						<html:text property="dateOfReleaseOfWC" name="cpTcDetailsForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dateOfReleaseOfWC')" align="center" >
					</td>
				</tr>
				</logic:notEqual>
				<tr>
				<td>&nbsp;</td>
				</tr>
                <tr>
                  <td colspan="4" class="SubHeading">&nbsp;<bean:message key="recoverymadefromborrower"/></td>
                </tr>
                <%
		   ClaimActionForm claimForm1 = (ClaimActionForm)session.getAttribute("cpTcDetailsForm");           			
		   // System.out.println("Printing the ClaimActionForm :" + claimForm1);                
		   java.util.Map mp = claimForm1.getCgpandetails();
		   // System.out.println("Printing the Recovery Details :" + mp);
                %>
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
						<logic:iterate property="cgpansVector" id="object" name="cpTcDetailsForm" scope="session">
						<% 
						  cgpantodisplay = (java.lang.String)object; 					
						  hiddencgpan = "cgpandetails(recovery#key-"+k+")";					  					  
						%>
						<html:hidden property="<%=hiddencgpan%>" name="cpTcDetailsForm" value="<%=cgpantodisplay%>"/>
					  <tr>
						<td class="TableData"><div align="center">&nbsp;<%=k%></div></td>
						<td class="TableData"><div align="center">&nbsp;<%=cgpantodisplay%></div></td>
						<%
						     tcPrincipal1="cgpandetails(tcprincipal$recovery#key-"+k+")";
  						%>
						<td class="TableData"><div align="center">
						<%
						if((cgpantodisplay.indexOf(tcfield)) >= 0)
						{
						%>
							<html:text property="<%=tcPrincipal1%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						<%
						}
						%>
						</div></td>
						<%
						     tcInterestCharges1 = "cgpandetails(tcInterestCharges$recovery#key-"+k+")";
  						%>
						<td class="TableData"><div align="center">
						<%
						if((cgpantodisplay.indexOf(tcfield)) >= 0)
						{
						%>						
							<html:text property="<%=tcInterestCharges1%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						<%
						}
						%>
						</div></td>
						<%
						     wcAmount1 = "cgpandetails(wcAmount$recovery#key-"+k+")";
  						%>
						<td class="TableData"><div align="center">
						<%
						if((cgpantodisplay.indexOf(wcfield)) >= 0)
						{
						%>						
							<html:text property="<%=wcAmount1%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						<%
						}
						%>
						</div></td>
						<%
						     wcOtherCharges1="cgpandetails(wcOtherCharges$recovery#key-"+k+")";
  						%>
						<td class="TableData" ><div align="center">
						<%
						if((cgpantodisplay.indexOf(wcfield)) >= 0)
						{
						%>						
							<html:text property="<%=wcOtherCharges1%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
                                                <%
                                                }
                                                %>
						</div></td>
						<%
						    recMode = "cgpandetails(recoveryMode$recovery#key-"+k+")";
  						%>
						<td class="TableData"><div align="center">
						  <html:select property="<%=recMode%>" name="cpTcDetailsForm">
							<html:option value="">Select</html:option>
							<html:options property="recoveryModes" name="cpTcDetailsForm"/>
                                                  </html:select>
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
						<html:text property="dateOfSeekingOTS" name="cpTcDetailsForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dateOfSeekingOTS')" align="center" >
					</td>
				</tr>
				<tr>
				<td><br></tr>
				</tr>
                  <tr> 
                    <td colspan="4" class="SubHeading">&nbsp;<bean:message key="securityandpersonalguaranteedtls"/></td>
                  </tr>
           			<%
           			/*
           			   String totalNetworthStr = "";
           			   String totalLandValueStr = "";
           			   String totalMachineValueStr = "";
           			   String totalBldgValueStr = "";
           			   String totalOFMAValueStr = "";
           			   String totalCurrAssetsValueStr = "";
           			   String totalOthersValueStr = "";
                                   ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm");           			
                                   // System.out.println("Printing the ClaimActionForm :" + claimForm);
                                   HashMap details = (HashMap)claimForm.getSecurityDetails();
                                   Double totalNetworthDouble = (Double)details.get(ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR);
                                   if(totalNetworthDouble != null)
                                   {
                                       if(totalNetworthDouble.doubleValue() > 0.0)
                                       {
                                           totalNetworthStr = totalNetworthDouble.toString();
                                       }
                                       else
                                       {
                                           totalNetworthStr = "";
                                       }
                                   }
                                   Double totalLandValDouble = (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_LAND);
                                   if(totalLandValDouble != null)
                                   {
                                       if(totalLandValDouble.doubleValue() > 0.0)
                                       {
                                           totalLandValueStr = totalLandValDouble.toString();
                                       }
                                       else
                                       {
                                           totalLandValueStr = "";
                                       }
                                   }
                                   Double totalMachineValDouble = (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_MC);
                                   if(totalMachineValDouble != null)
                                   {
                                       if(totalMachineValDouble.doubleValue() > 0.0)
                                       {
                                           totalMachineValueStr = totalMachineValDouble.toString();
                                       }
                                       else
                                       {
                                           totalMachineValueStr = "";
                                       }
                                   }
                                   Double totalBldgValueDouble = (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG);
                                   if(totalBldgValueDouble != null)
                                   {
                                       if(totalBldgValueDouble.doubleValue() > 0.0)
                                       {
                                           totalBldgValueStr = totalBldgValueDouble.toString();
                                       }
                                       else
                                       {
                                           totalBldgValueStr = "";
                                       }
                                   }
                                   Double totalOFMAValDouble = (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS);
                                   if(totalOFMAValDouble != null)
                                   {
                                       if(totalOFMAValDouble.doubleValue() > 0.0)
                                       {
                                           totalOFMAValueStr = totalOFMAValDouble.toString();
                                       }
                                       else
                                       {
                                           totalOFMAValueStr = "";
                                       }
                                   }
                                   Double totalCurrAssetsValDouble = (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS);
                                   if(totalCurrAssetsValDouble != null)
                                   {
                                       if(totalCurrAssetsValDouble.doubleValue() > 0.0)
                                       {
                                           totalCurrAssetsValueStr = totalCurrAssetsValDouble.toString();
                                       }
                                       else
                                       {
                                           totalCurrAssetsValueStr = "";
                                       }
                                   }
                                   Double totalOthersValDouble = (Double)details.get(ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS);
                                   if(totalOthersValDouble != null)
                                   {
                                       if(totalOthersValDouble.doubleValue() > 0.0)
                                       {
                                           totalOthersValueStr = totalOthersValDouble.toString();
                                       }
                                       else
                                       {
                                           totalOthersValueStr = "";
                                       }
                                   }
                                   */
           			%>                  
                  <tr> 
                    <td colspan="5"><table width="100%" border="0" cellspacing="1">
  					<tr>
  						<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="particulars"/></div></td>
  						<td class="ColumnBackground" colspan="2"><div align="center"><bean:message key="security"/></div></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="amntrealizedthrudisposalofsec"/><br><bean:message key="inRs"/></div></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="netwothofguarantors"/></div></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="amntrealizedthruinvocationofperguarantees"/><br><bean:message key="inRs"/></div></td>
  						<td class="ColumnBackground" rowspan="2"><div align="center"><bean:message key="reasonsforreductioninsecurity"/></div></td>
  						
  					</tr>
  					<tr>
  						<td class="ColumnBackground"><div align="center"><bean:message key="nature"/></div></td>
  						<td class="ColumnBackground"><div align="center"><bean:message key="value"/><br><bean:message key="inRs"/></div></td>
  					</tr>
  					<tr>
  						<td class="TableData" rowspan="6"><div align="center"><bean:message key="asondateofsanctionofcredit"/></div></td>
  						<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
  						<%  						
  						   landAsOnDtOfSnctnDtl = "asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_PARTICULAR_LAND+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=landAsOnDtOfSnctnDtl%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>							
  						</div></td>
  						<td rowspan="6" class="TableData">&nbsp;</td>
  						<%  						
  						   netwrthAsOnDtOfSnctn ="asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  						<html:text property="<%=netwrthAsOnDtOfSnctn%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						<td rowspan="6" class="TableData">&nbsp;</td>
  						<%  						
  						   reasonReductionDtSnctn="asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  						<html:textarea property="<%=reasonReductionDtSnctn%>" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  				        <td class="TableData"><div align="center"><bean:message key="bldg"/></div></td>
  						<%  						
  						   bldgAsOnDtOfSnctn ="asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG+")";
  					        %>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=bldgAsOnDtOfSnctn%>"
  						name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						
  						
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="machine"/></div></td>
  						<% 
  						   machinecAsOnDtOfSnctn ="asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_PARTICULAR_MC+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=machinecAsOnDtOfSnctn%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="assets"/></div></td>
  						<% 
  						   otherAssetsAsOnDtOfSnctn = "asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS+")";						
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=otherAssetsAsOnDtOfSnctn%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="currentAssets"/></div></td>
  						<%  						
  						   currAssetsAsOnDtOfSnctn="asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS +")";						
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=currAssetsAsOnDtOfSnctn%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="psOthers"/></div></td>
  						<%  						
  						   otherValAsOnDtOfSnctn="asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=otherValAsOnDtOfSnctn%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>  							
  						</div></td>
  						
  					</tr>
  					<tr>
  						<td class="TableData" rowspan="6"><div align="center"><bean:message key="asonthedateofnpa"/></div></td>
  						<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
  						<%
  						   landAsOnDtOfNPA = "asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_PARTICULAR_LAND+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=landAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						<td rowspan="6" class="TableData">&nbsp;</td>
  						<%
  						   netwrthAsOnDtOfNPA ="asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  						<html:text property="<%=netwrthAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>						
  						</div></td>
  						<td rowspan="6" class="TableData">&nbsp;</td>
  						<%
  						   rsnRdctnDtSnctnAsOnNPA="asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  						<html:textarea property="<%=rsnRdctnDtSnctnAsOnNPA%>" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="bldg"/></div></td>
  						<%  						
  						    bldgAsOnDtOfNPA ="asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG+")";
  					        %>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=bldgAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>						
  						</div></td>
  						
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="machine"/></div></td>
  						<%   						
  						   machinecAsOnDtOfNPA ="asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_PARTICULAR_MC+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=machinecAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="assets"/></div></td>
  						<%   						
  						    otherAssetsAsOnDtOfNPA = "asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS+")";						
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=otherAssetsAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="currentAssets"/></div></td>
  						<%  						
  						   currAssetsAsOnDtOfNPA="asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS +")";						
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=currAssetsAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="psOthers"/></div></td>
  						<%  						
  						   otherValAsOnDtOfNPA="asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=otherValAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						
  					</tr>
  					<tr>
  						<td class="TableData" rowspan="6"><div align="center"><bean:message key="asondateoflodgementoffirstclaim"/></div></td>
  						<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
  						<%
  						   landAsOnLodgemnt = "asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_PARTICULAR_LAND+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=landAsOnLodgemnt%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						<td rowspan="6" class="TableData">&nbsp;</td>
  						<%
  						   netwrthAsOnLodgemnt ="asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  						<html:text property="<%=netwrthAsOnLodgemnt%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						<td rowspan="6" class="TableData">&nbsp;</td>
  						<%
  						   rsnRdctnDtSnctnAsOnLodgemnt="asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  						<html:textarea property="<%=rsnRdctnDtSnctnAsOnLodgemnt%>" name="cpTcDetailsForm"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="bldg"/></div></td>
  						<%  						
  						   bldgAsOnDtOfLodgemnt ="asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG+")";
  					        %>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=bldgAsOnDtOfLodgemnt%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="machine"/></div></td>
  						<%   						
  						   machinecAsOnLodgemnt ="asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_PARTICULAR_MC+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=machinecAsOnLodgemnt%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="assets"/></div></td>
  						<%   						
  						   otherAssetsAsOnLodgemnt = "asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS+")";						
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=otherAssetsAsOnLodgemnt%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>						
  						</div></td>
  						
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="currentAssets"/></div></td>
  						<%  						
  						   currAssetsAsOnLodgemnt="asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS +")";						
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=currAssetsAsOnLodgemnt%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						
  					</tr>
  					<tr>
  						<td class="TableData"><div align="center"><bean:message key="psOthers"/></div></td>
  						<%  						
  						   otherValAsOnLodgemnt="asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=otherValAsOnLodgemnt%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						
  					</tr>
  					  					  					  					  					  				
					<tr>
						<td class="TableData" rowspan="6"><div align="center"><bean:message key="asondateoflodgementofsecondclaim"/></div></td>
						<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
						<%
						   landAsOnLodgemntOfSecClm = "asOnDateOfLodgemntOfSecondClm("+ ClaimConstants.CLM_SAPGD_PARTICULAR_LAND+")";
						%>
						<td class="TableData"><div align="center">
						<html:text property="<%=landAsOnLodgemntOfSecClm%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						</div></td>
						<%
						  amntRealizedThruDisposalOfSecurityStr = "amntRealizedThruDisposalOfSecurity("+ClaimConstants.CLM_SAPGD_PARTICULAR_LAND+")";
						%>
						<td class="TableData"><html:text property="<%=amntRealizedThruDisposalOfSecurityStr%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
						<%
						   netwrthAsOnLodgemntOfSecClm ="asOnDateOfLodgemntOfSecondClm("+ ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR+")";
						%>						
						<td class="TableData" rowspan="6"><div align="center">
						<html:text property="<%=netwrthAsOnLodgemntOfSecClm%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						</div></td>						
						<td rowspan="6" class="TableData">&nbsp;<html:text property="amntRealizedThruInvocationOfPerGuarantees" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
						<%
						   rsnRdctnDtSnctnAsOnLodgemntOfSecClm="asOnDateOfLodgemntOfSecondClm("+ ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION+ClaimConstants.CLM_DELIMITER1 +ClaimConstants.CLM_SAPGD_PARTICULAR_LAND+")";
						%>
						<td class="TableData"><div align="center">
						<html:textarea property="<%=rsnRdctnDtSnctnAsOnLodgemntOfSecClm%>" name="cpTcDetailsForm"/>
						</div></td>
					</tr>
					<tr>
						<td class="TableData"><div align="center"><bean:message key="bldg"/></div></td>
						<%  						
						   bldgAsOnDtOfLodgemntOfSecClm ="asOnDateOfLodgemntOfSecondClm("+ ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG+")";
						%>
						<td class="TableData"><div align="center">
						<html:text property="<%=bldgAsOnDtOfLodgemntOfSecClm%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						</div></td>
						<%
						amntRealizedThruDisposalOfSecurityStr = "amntRealizedThruDisposalOfSecurity("+ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG +")";
						%>
						<td class="TableData"><html:text property="<%=amntRealizedThruDisposalOfSecurityStr%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
						<%
						rsnRdctnDtSnctnAsOnLodgemntOfSecClm = "asOnDateOfLodgemntOfSecondClm(" + ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION+ClaimConstants.CLM_DELIMITER1 + ClaimConstants.CLM_SAPGD_PARTICULAR_BLDG +")";
						%>
						<td class="TableData"><div align="center"><html:textarea property="<%=rsnRdctnDtSnctnAsOnLodgemntOfSecClm%>" name="cpTcDetailsForm"/></div></td>
					</tr>
					<tr>
						<td class="TableData"><div align="center"><bean:message key="machine"/></div></td>
						<%   						
						   machinecAsOnLodgemntOfSecClm ="asOnDateOfLodgemntOfSecondClm("+ ClaimConstants.CLM_SAPGD_PARTICULAR_MC+")";
						%>
						<td class="TableData"><div align="center">
						<html:text property="<%=machinecAsOnLodgemntOfSecClm%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						</div></td>
						<%
						amntRealizedThruDisposalOfSecurityStr = "amntRealizedThruDisposalOfSecurity("+ClaimConstants.CLM_SAPGD_PARTICULAR_MC+")";
						%>
						<td class="TableData"><html:text property="<%=amntRealizedThruDisposalOfSecurityStr%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
						<%
						rsnRdctnDtSnctnAsOnLodgemntOfSecClm = "asOnDateOfLodgemntOfSecondClm("+ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION+ClaimConstants.CLM_DELIMITER1 +ClaimConstants.CLM_SAPGD_PARTICULAR_MC+")";
						%>
						<td class="TableData"><div align="center"><html:textarea property="<%=rsnRdctnDtSnctnAsOnLodgemntOfSecClm%>" name="cpTcDetailsForm"/></div></td>
					</tr>
					<tr>
						<td class="TableData"><div align="center"><bean:message key="assets"/></div></td>
						<%   						
						   otherAssetsAsOnLodgemntOfSecClm = "asOnDateOfLodgemntOfSecondClm("+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS+")";						
						%>
						<td class="TableData"><div align="center">
						<html:text property="<%=otherAssetsAsOnLodgemntOfSecClm%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>						
						</div></td>
						<%
						amntRealizedThruDisposalOfSecurityStr = "amntRealizedThruDisposalOfSecurity(" +ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS+")";
						%>
						<td class="TableData"><html:text property="<%=amntRealizedThruDisposalOfSecurityStr%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
						<%
						rsnRdctnDtSnctnAsOnLodgemntOfSecClm ="asOnDateOfLodgemntOfSecondClm(" + ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION+ClaimConstants.CLM_DELIMITER1 +ClaimConstants.CLM_SAPGD_PARTICULAR_OTHER_FIXED_MOV_ASSETS+")";
						%>
						<td class="TableData"><div align="center"><html:textarea property="<%=rsnRdctnDtSnctnAsOnLodgemntOfSecClm%>" name="cpTcDetailsForm"/></div></td>
					</tr>
					<tr>
						<td class="TableData"><div align="center"><bean:message key="currentAssets"/></div></td>
						<%  						
						   currAssetsAsOnLodgemntOfSecClm="asOnDateOfLodgemntOfSecondClm("+ ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS +")";						
						%>
						<td class="TableData"><div align="center">
						<html:text property="<%=currAssetsAsOnLodgemntOfSecClm%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						</div></td>
						<%
						amntRealizedThruDisposalOfSecurityStr = "amntRealizedThruDisposalOfSecurity("+ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS+")";
						%>
						<td class="TableData"><html:text property="<%=amntRealizedThruDisposalOfSecurityStr%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
						<%
						rsnRdctnDtSnctnAsOnLodgemntOfSecClm ="asOnDateOfLodgemntOfSecondClm("+ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION+ClaimConstants.CLM_DELIMITER1+ ClaimConstants.CLM_SAPGD_PARTICULAR_CUR_ASSETS+")"; 
						%>
						<td class="TableData"><div align="center"><html:textarea property="<%=rsnRdctnDtSnctnAsOnLodgemntOfSecClm%>" name="cpTcDetailsForm"/></div></td>
					</tr>
					<tr>
						<td class="TableData"><div align="center"><bean:message key="psOthers"/></div></td>
						<%  						
						   otherValAsOnLodgemntOfSecClm="asOnDateOfLodgemntOfSecondClm("+ ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS+")";
						%>
						<td class="TableData"><div align="center">
						<html:text property="<%=otherValAsOnLodgemntOfSecClm%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
						</div></td>
						<%
						amntRealizedThruDisposalOfSecurityStr ="amntRealizedThruDisposalOfSecurity(" + ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS +")";
						%>
						<td class="TableData"><html:text property="<%=amntRealizedThruDisposalOfSecurityStr%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></td>
						<%
						rsnRdctnDtSnctnAsOnLodgemntOfSecClm = "asOnDateOfLodgemntOfSecondClm("+ClaimConstants.CLM_SAPGD_REASONS_FOR_REDUCTION+ClaimConstants.CLM_DELIMITER1+ClaimConstants.CLM_SAPGD_PARTICULAR_OTHERS+")";
						%>
						<td class="TableData"><div align="center"><html:textarea property="<%=rsnRdctnDtSnctnAsOnLodgemntOfSecClm%>" name="cpTcDetailsForm"/></div></td>
					</tr>  					  					  					  					  					  					  					  					
                    </table></td>
                  </tr>
                <tr>
					<td colspan="4" class="SubHeading">
						&nbsp;<bean:message key="orderofchargenote"/>
					</td>
		</tr>
		<tr>
		<td colspan="4" class="SubHeading">&nbsp;<bean:message key="particularsnote"/>
		</td>
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
							<td><font color="#FF0000" size="2">*</font>Amount Claimed in second and final installment</td>
						</tr>
						<%!int m=1;%>
						<% String firstSettAmntStr = "";%>
						
						<logic:iterate property="cgpnDetails" name="cpTcDetailsForm" id="object">
						<%
						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
						hashmap =(java.util.HashMap)object;
						
						cgpn = (String)hashmap.get(ClaimConstants.CLM_CGPAN);						
						loanType = (String)hashmap.get(ClaimConstants.CGPAN_LOAN_TYPE);
						loanLimit = ((Double)hashmap.get(ClaimConstants.CGPAN_APPRVD_AMNT)).doubleValue();																		
						if(hashmap.containsKey(ClaimConstants.CGPAN_CLM_APPLIED_AMNT))
						{
							appliedClmAmnt = ((Double)hashmap.get(ClaimConstants.CGPAN_CLM_APPLIED_AMNT)).doubleValue();
						}
						settlementAmnt = ((Double)hashmap.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_AMNT)).doubleValue();
						if((Double)hashmap.get(ClaimConstants.CGPAN_CLM_APPLIED_AMNT) != null)
						{
						        if( m == 1)
						        {
								firstSettAmntStr = ((Double)hashmap.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_AMNT)).toString();
							}
							else if(m > 1)
							{
							 	firstSettAmntStr = "";
							}
						}
						else						
						{
						     	firstSettAmntStr = "";
						}
						settlementDt = (java.util.Date)hashmap.get(ClaimConstants.CLM_SETTLMNT_FIRST_SETTLMNT_DT);
						if(settlementDt != null)
						{
						     settlementDtStr = sdf.format(settlementDt);
						}
						%>						
						<tr>
							<td class="TableData"><div align="center">&nbsp;<%=m%></div></td>
							<td class="TableData"><div align="center">
                                                         &nbsp;<%=cgpn%></div></td>
							<td class="TableData"><div align="center">
								<%=loanType%></div></td>
                                                        <td class="TableData"><div align="center">
								<%=loanLimit%></div></td>								
							<td class="TableData"><div align="center">
								<%=appliedClmAmnt%></div></td>
							<td class="TableData"><div align="center">
								<%=firstSettAmntStr%></div></td>
							<td class="TableData"><div align="center">
								<%=settlementDtStr%></div></td>								
							<%
							    amountClaimed="claimSummaryDetails("+ ClaimConstants.SECOND_INSTALLMENT+ ClaimConstants.CLM_DELIMITER1+cgpn+")";
  							%>
							<td class="TableData"><div align="center">
							    <html:text property="<%=amountClaimed%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
							</div></td>
						</tr>				
						<%m++;%>
  						</logic:iterate>
					</table></td>
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
          <a href="javascript:submitForm('displayDisclaimerForSecInstallment.do?method=displayDisclaimerForSecInstallment')"><img src="images/Next.gif" alt="Apply" height="37" border="0"></a>
          <a href="javascript:document.cpForm.reset()"><img src="images/Reset.gif" alt="Reset" height="37" border="0"></a>
          <a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" height="37" border="0"></a>
          </div>
      </div></td>
      <td  align="right" valign="bottom">
      <img src="images/TableRightBottom.gif" height="51"></td>
  </tr>
</table>
</html:form>
</body>
</html>