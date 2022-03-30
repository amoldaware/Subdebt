<%@ page language="java"%>
<%@ page import = "com.cgtsi.claim.ClaimConstants"%>
<%@ page import = "com.cgtsi.actionform.ClaimActionForm"%>
<%@ page import = "java.util.HashMap"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","proceedFromRecoveryFilterPage.do?method=proceedFromRecoveryFilterPage");%>

<%
String npaClassifiedDt = "";
String npaReportingDt = "";
String reasonForTurningNPA = "";
String cgpan = "";
String hiddencgpan = "";
String dsbrsmntdt = "";
String principal = "";
String interestCharges = "";
String osAsOnNpa = "";
String osAsStatedinCivilSuit = "";
String osAsOnLodgementOfClm = "";
String wccgpan = "";
String hidencgpan = "";
String wcAsOnNPA = "";
String wcOsAsOnInCivilSuit = "";
String wcOsAtLdgmntClm = "";
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
String cgpantodisplay = "";
String tcPrincipal1 = "";
String tcInterestCharges1 = "";
String wcAmount1 = "";
String wcOtherCharges1 = "";
String recMode = "";
String cgpn = "";
String amountClaimed = "";
java.util.HashMap hashmap = null;
java.util.Date dsbrsDt = null;
String repaidStr = "";
String tcfield = "TC".trim();
String wcfield = "WC".trim();
String R1field="R1".trim();
%>
<body onload="setCPOthersEnabled()"/>
<%
String focusField="dateOfRecallNotice";
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
    focusField="test";
}
%>

<html:form action="addFirstClaimsPageDetails.do?method=addFirstClaimsPageDetails" method="POST" focus="<%=focusField%>" enctype="multipart/form-data">
<html:errors />
<html:hidden name="cpTcDetailsForm" property="test"/>
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
      <td colspan="2">
      <DIV align="right">			
      		<A HREF="javascript:submitForm('helpEnterClaimFirstApplication.do')">
      	        HELP</A>
      </DIV>    
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td>
  		<table width="100%" border="0" cellspacing="1" cellpadding="0">
                  <tr> 
                    <td colspan="4" class="SubHeading"><br> <div align="right"><bean:message key="subheading1"/></div></td>
                  </tr>
                  <tr> 
                    <td colspan="4" class="SubHeading"><br> <div align="center"><bean:message key="subheading2"/></div></td>
                  </tr>
                  <tr> 
                    <td colspan="4" class="SubHeading"><br> <div align="center"><bean:message key="subheading3"/></div></td>
                  </tr>
                  <tr> 
                    <td colspan="4" class="SubHeading"><br><bean:message key="subheading4"/><br><br></td>
                  </tr>
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
  
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="memberDetails.memberId" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp; <bean:message key="lendingbankname"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="memberDetails.memberBankName" name="cpTcDetailsForm"/></div></td>
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="city"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="memberDetails.city" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="district"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="memberDetails.district" name="cpTcDetailsForm"/></div></td>
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="state"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="memberDetails.state" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="telephone"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="memberDetails.telephone" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="eMail"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp; <bean:write property="memberDetails.email" name="cpTcDetailsForm"/></div></td>
                  </tr>
  				        <tr> 
                    <td class="SubHeading" colspan="4"> &nbsp;<bean:message key="borrower/unitdetails"/></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="name"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="borrowerDetails.borrowerName" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="completeaddress"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="borrowerDetails.address" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="district"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="borrowerDetails.district" name="cpTcDetailsForm"/></div></td>
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="state"/></div></td>
                    <td class="TableData"> <div align="left"> &nbsp;<bean:write property="borrowerDetails.state" name="cpTcDetailsForm"/></div></td>
                    <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="pin"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="borrowerDetails.pinCode" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  </tr>                
  				<tr> 
                    <td class="SubHeading" colspan="4"> &nbsp;<bean:message key="statusofaccounts"/></td>
                  </tr>                  
                  <tr> 
                  
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dateofnpa"/></div></td>                    
                    <%                    
                    npaClassifiedDt = "npaDetails.".trim() +ClaimConstants.NPA_CLASSIFIED_DT;
                    
                    %>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp;<bean:write property="<%=npaClassifiedDt%>" name="cpTcDetailsForm"/> </div></td>
                  </tr>
                  <%
                  npaReportingDt = "npaDetails.".trim() + ClaimConstants.NPA_REPORTING_DT;
                  %>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="dateofreporting"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp; <bean:write property="<%=npaReportingDt%>" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  <%
                  reasonForTurningNPA = "npaDetails.".trim() + ClaimConstants.REASONS_FOR_TURNING_NPA;
                  %>
                  <tr> 
                    <td class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="reasonfornpa"/></div></td>
                    <td class="TableData" colspan="3"> <div align="left"> &nbsp; <bean:write property="<%=reasonForTurningNPA%>" name="cpTcDetailsForm"/></div></td>
                  </tr>
                  
                  
                  <tr colspan="3"> 
		                      <td class="ColumnBackground"> <div align="left">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="dateofissueofrecallnotice"/></div></td>
		                      <td class="TableData">
		    		  	<html:text property="dateOfRecallNotice" maxlength="10" name="cpTcDetailsForm"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dateOfRecallNotice')" align="center" >
		    		  </td>
		    		  <td class="ColumnBackground">&nbsp;<bean:message key="uploadfile"/></td>
 		    		  <td class="TableData">&nbsp;<html:file property="recallnoticefilepath" name="cpTcDetailsForm"/></td>
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
				  						&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="forumthruwhichlegalproceedingsinitiated"/></td>						
				  						<td class="TableData"><html:select property="forumthrulegalinitiated" name="cpTcDetailsForm" onchange="setCPOthersEnabled()">
				  								<html:option value="">Select</html:option>
				  								<html:option value="Civil Court">Civil Court</html:option>
				  								<html:option value="DRT">DRT</html:option>
				  								<html:option value="LokAdalat">Lok Adalat</html:option>
				  								<html:option value="Revenue Recovery Autority">Revenue Recovery Autority</html:option>
				  								<html:option value="Securitisation Act ">Securitisation Act, 2002</html:option>
				  								<html:option value="Others">Others</html:option>
				  							</html:select>
				  						</td>						
				  						<td class="ColumnBackground">&nbsp;<bean:message key="otherforum"/></td>
				  						<td class="TableData"><html:text property="otherforums" name="cpTcDetailsForm" disabled="true"/></td>
				  					</tr>
				  					<tr>
				               <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="suit/caseregnumber"/></td>
				  						<td class="TableData" >						
				  						<html:text property="caseregnumber" name="cpTcDetailsForm"/></td>
				  						
				  						<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="filingdate"/></td>
				  						<td class="TableData"  width="20%"><html:text property="legaldate" maxlength="10" name="cpTcDetailsForm"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.legaldate')" align="center"></td>
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
				  					<tr colspan="3">
				                          <td class="ColumnBackground">&nbsp;<bean:message key="amntClaimedInTheSuit"/></td>
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
				  						<td class="TableData" width="20%" colspan="3">
				  							<html:text property="currentstatusremarks" name="cpTcDetailsForm" maxlength="4000"/>
				  						</td>
				  					</tr>
				  					<tr>
				                          <td class="ColumnBackground">&nbsp;<bean:message key="recoveryProceedingsConcluded"/></td>
				  						<td class="TableData" width="20%" colspan="3">
				  							<html:radio property="proceedingsConcluded"  name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_YES_FLAG%>"><bean:message key="otsyes"/></html:radio> 
				  							<html:radio property="proceedingsConcluded" name="cpTcDetailsForm" value="<%=ClaimConstants.DISBRSMNT_NO_FLAG%>"><bean:message key="otsno"/></html:radio>
				  						</td>
				  					</tr>
				                      </table></td>
                  </tr>
                  <logic:notEqual property="tcCounter" value="0" name="cpTcDetailsForm">  				
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
  					  %>
  					          <html:hidden property="<%=hiddencgpan%>" name="cpTcDetailsForm" value="<%=cgpan%>"/>
  					  <tr>					  
  						  <td class="TableData">&nbsp;<%=i%></td>
  						  <td class="TableData">&nbsp;<%= cgpan %></td>
  						  <td class="TableData">
  						  <%
  						  dsbrsmntdt = "lastDisbursementDate(key-"+i+")";						  
  						  %>
  						  <font color="#FF0000" size="2">*</font><html:text property="<%=dsbrsmntdt%>" maxlength="10" name="cpTcDetailsForm"/><!-- <img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.<%=dsbrsmntdt%>')" align="center"> -->
  						  </td>
  						  <%
  						     principal="tcprincipal(key-"+i+")";						  
  						  %>
  						  <td class="TableData">
  							<font color="#FF0000" size="2">*</font><html:text property="<%=principal%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						  </td>
  						  <%
  						    interestCharges="tcInterestCharge(key-"+i+")";
  						  %>
  						  <td class="TableData">
  							<font color="#FF0000" size="2">*</font><html:text property="<%=interestCharges%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						  </td>
  						  <%
  						   osAsOnNpa="tcOsAsOnDateOfNPA(key-"+i+")";
  						  %>
  						  <td class="TableData">
  							<font color="#FF0000" size="2">*</font><html:text property="<%=osAsOnNpa%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						  </td>
  						  <%
  						   osAsStatedinCivilSuit="tcOsAsStatedInCivilSuit(key-"+i+")";
  						  %>
  						  <td class="TableData">
  							<font color="#FF0000" size="2">*</font><html:text property="<%=osAsStatedinCivilSuit%>" maxlength="16" name="cpTcDetailsForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						  </td>
  						  <%
  						    osAsOnLodgementOfClm="tcOsAsOnLodgementOfClaim(key-"+i+")";
  						  %>
  						  <td class="TableData">
  							<font color="#FF0000" size="2">*</font><html:text property="<%=osAsOnLodgementOfClm%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
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
  				</logic:notEqual>
  				 
  				<tr>
  				<td><br></td>
  				</tr>
  				
  		    <logic:notEqual property="wcCounter" value="0" name="cpTcDetailsForm">
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
  					  <logic:iterate property="wcCgpansVector" id="object" name="cpTcDetailsForm" scope="session">
  					  <% 
  					     wccgpan = (java.lang.String)object; 
  					     hidencgpan = "wcCgpan(key-"+j+")";					  					  
  					  %>
  					  <html:hidden property="<%=hidencgpan%>" name="cpTcDetailsForm" value="<%=wccgpan%>"/>
  					  <tr>
  						  <td class="TableData">&nbsp;<%=j%></td>
  						  <td class="TableData">&nbsp;<%=wccgpan%></td>
  						  <td class="TableData"><div align="center">
  						  <%
  						    wcAsOnNPA = "wcOsAsOnDateOfNPA(key-"+j+")";
  						  %>
  						   <font color="#FF0000" size="2">*</font><html:text property="<%=wcAsOnNPA%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></div>
  						  </td>
  						  <td class="TableData"><div align="center">
  						  <%
  						    wcOsAsOnInCivilSuit = "wcOsAsStatedInCivilSuit(key-"+j+")";
  						  %>
  						  <font color="#FF0000" size="2">*</font><html:text property="<%=wcOsAsOnInCivilSuit%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></div>
  						  </td>
  						  <td class="TableData"><div align="center">
  						  <%
  						    wcOsAtLdgmntClm= "wcOsAsOnLodgementOfClaim(key-"+j+")";
  						  %>
  						  <font color="#FF0000" size="2">*</font><html:text property="<%=wcOsAtLdgmntClm%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
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
  						<html:text property="dateOfReleaseOfWC" maxlength="10" name="cpTcDetailsForm"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dateOfReleaseOfWC')" align="center">
  					</td>
  				</tr>
  				</logic:notEqual>
  				<tr>
  				<td><br></tr>
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
  						<%  						
  						   netwrthAsOnDtOfSnctn ="asOnDtOfSanctionDtl("+ ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  						<html:text property="<%=netwrthAsOnDtOfSnctn%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
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
  						<html:text property="<%=machinecAsOnDtOfSnctn%>" maxlength="16" name="cpTcDetailsForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
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
  						<%
  						   netwrthAsOnDtOfNPA ="asOnDtOfNPA("+ ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  						<html:text property="<%=netwrthAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>						
  						</div></td>
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
  						<html:text property="<%=otherValAsOnDtOfNPA%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  					</tr>
  					<tr>
  						<td class="TableData" rowspan="6"><div align="center"><bean:message key="asondateoflodgementofcredit"/></div></td>
  						<td class="TableData"><div align="center"><bean:message key="land"/></div></td>
  						<%
  						   landAsOnLodgemnt = "asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_PARTICULAR_LAND+")";
  						%>
  						<td class="TableData"><div align="center">
  						<html:text property="<%=landAsOnLodgemnt%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
  						<%
  						   netwrthAsOnLodgemnt ="asOnLodgemntOfCredit("+ ClaimConstants.CLM_SAPGD_NETWORTH_OF_GUARANTOR+")";
  						%>
  						<td class="TableData" rowspan="6"><div align="center">
  						<html:text property="<%=netwrthAsOnLodgemnt%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						</div></td>
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
  						</div></td>
  						<%
  						}
  						%>
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
  						else if((cgpantodisplay.indexOf(R1field)) >= 0)
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
  						<td class="TableData"><div align="center">  						
  						<%
  						if((cgpantodisplay.indexOf(wcfield)) >= 0)
  						{
  						%>  						  						
  						<html:text property="<%=wcOtherCharges1%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  						<%
  						}
  						else if((cgpantodisplay.indexOf(R1field)) >= 0)
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
  					<td class="ColumnBackground">&nbsp;<bean:message key="ifrecoverydonethruots"/></td>
  					<td class="TableData" colspan="3">
  						<html:text property="dateOfSeekingOTS" maxlength="10" name="cpTcDetailsForm"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.dateOfSeekingOTS')" align="center">
  					</td>
  				</tr>	
          <!-- added mseCategory by sukumar @path on 16-sep-2009 -->
          <TR>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2">&nbsp;<font color="#FF0000" size="2">*</font>Whether the Unit Assisted is an MSE as per the MSMED Act 2006 definition of MSE:</TD>
						<TD align="left" valign="top" class="ColumnBackground" colspan="7">
                 <input type="radio" name="microCategory" value="Y">Y
                 <input type="radio" name="microCategory" value="N" checked="checked">N
    		</TD>
          </TR>		
          <!-- added by sukumar for capturing the Subsidy Details -->
          <tr> 
              <td  class="SubHeading">&nbsp;Subsidy Details</td>
         </tr>
          <tr>
            <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font>Subsidy Credit Date</td>
				  	<td class="TableData"><html:text property="subsidyDate" maxlength="10" name="cpTcDetailsForm"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.subsidyDate')" align="center"></td>
				    <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font>Amount</td>
				  	<td class="TableData" colspan="2"><html:text property="subsidyAmt" name="cpTcDetailsForm"/></td>
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
  						<logic:iterate property="cgpnDetails" name="cpTcDetailsForm" id="object">
  						<%
  						hashmap =(java.util.HashMap)object;													
  						cgpn = (String)hashmap.get(ClaimConstants.CLM_CGPAN);						
  						%>						
  						<tr>
  							<td class="TableData"><div align="center">&nbsp;<%=m%></div></td>
  							<td class="TableData"><div align="center">&nbsp;<%=cgpn%></div></td>
  							<td class="TableData"><div align="center">&nbsp;<%=hashmap.get(ClaimConstants.CGPAN_APPRVD_AMNT)%></div></td>
  							<%
  							    amountClaimed="claimSummaryDetails("+cgpn+")";
  							%>
  							<td class="TableData"><div align="center">
  							<font color="#FF0000" size="2">*</font><html:text property="<%=amountClaimed%>" name="cpTcDetailsForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
  							</div></td>													
  						</tr>
  						<%m++;%>
  						</logic:iterate>
  					</table></td>
           </tr>
           <tr> 
              <td colspan="4" class="SubHeading">&nbsp;RTGS Details</td>
         </tr>
          <tr>
              <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;IFS Code</td>
				  	  <td class="TableData"><html:text property="ifsCode" maxlength="10" name="cpTcDetailsForm"/></td>
				      <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;NEFT Code</td>
				  	  <td class="TableData"><html:text property="neftCode" maxlength="10" name="cpTcDetailsForm"/></td>
			    </tr>
          <tr>
              <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;Bank Account Name</td>
				  	  <td class="TableData"><html:text property="rtgsBankName" maxlength="30" name="cpTcDetailsForm"/></td>
				      <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;Branch Name</td>
				  	  <td class="TableData"><html:text property="rtgsBranchName" maxlength="30" name="cpTcDetailsForm"/></td>
			    </tr>
          <tr>
              <td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;Bank Account Number</td>
				  	  <td class="TableData"><html:text property="rtgsBankNumber" maxlength="15" name="cpTcDetailsForm" onkeypress="return numbersOnly(this, event)"/></td>
				      <td class="ColumnBackground" colspan="2">&nbsp;<font color="#FF0000" size="2"></font>&nbsp;</td>
				   </tr>
           <tr><td>&nbsp;</td></tr>
  				<tr>
  					<td colspan="4" class="SubHeading">
  						&nbsp;<bean:message key="amounteligibleforclaimnote"/>
  						<br><br>
  						<bean:message key="termloannote"/>
  						<br>
  						&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="termloannote1a"/>		(<strong>or</strong>)
  						<br>
  						&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="termloannote1b"/>
  						<br>
  						<bean:message key="workingcapitalnote"/>
  						<br>
  						&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="workingcapitalnote2a"/>		(<strong>or</strong>)
  						<br>
  						&nbsp;&nbsp;&nbsp;&nbsp;<bean:message key="workingcapitalnote2b"/>
  					</td>
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
            <a href="javascript:submitForm('addFirstClaimsPageDetails.do?method=addFirstClaimsPageDetails')"><img src="images/Next.gif" alt="Submit" width="49" height="37" border="0"></a>
            <a href="javascript:document.cpTcDetailsForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>            
            <a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a></div>
        </div></td>
        <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
    </tr>
</table>
</html:form>
</body>
</html>
