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
<%@ page import = "com.cgtsi.reports.ApplicationReport"%>
<%@ page import = "com.cgtsi.claim.DtlsAsOnLogdementOfClaim"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<script src="/csrfguard"></script>

<% session.setAttribute("CurrentPage","historyReportDetails.do?method=historyReportDetails");%>
<%
String npaClassifiedDt = null;
String npaReportingDt = null;
String reasonForTurningNPA = null;
String cgpan = null;
String hiddencgpan = null;
String dsbrsmntdt = null;
String principal = null;
String interestCharges = null;
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
%>

<html:errors />
<html:form action="historyReportDetails.do?method=historyReportDetails" method="POST" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">    

    </table>
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
       
    <tr> 
         <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
         <td width="248" background="images/TableBackground1.gif"><img src="images/ReportsHeading.gif" width="131" height="25"></td>
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
                          <td colspan="6" class="Heading1" align="center"><u><bean:message key="reportHeader"/></u></td>
                      </tr>
                      <tr> <td colspan="6">&nbsp;</td></tr>
                        <tr> 
                          <td width="50%" height="20" class="Heading">HISTORY&nbsp;For CGPAN&nbsp;&nbsp;<bean:write property="appReport.cgpan" name="cpTcDetailsForm"/></td>
                          <td class="Heading" width="50%">&nbsp;&nbsp;</td>
                          <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        </tr>
                        <tr> 
                          <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                        </tr>
                      </table></td>
                  </tr>
                  
                  <tr> 
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="cgpanForApplication" /></b><bean:write property="appReport.cgpan" name="cpTcDetailsForm"/></div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="memberIdForApplication" /></b></b><bean:write property="appReport.memberId" name="cpTcDetailsForm"/></div></td>
                  
                  
				</tr>
				<tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="asDate" /></b> <bean:define id="applicationDate" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>
				  <%String date1 = null;
				  java.util.Date newApplicationDate = applicationDate.getApplicationDate();
				  if(newApplicationDate != null)
				  {
	  				   date1 = dateFormat.format(newApplicationDate);
				  }
				  else
				  {
					   date1 = ""; 
				  }
				  %>
				  <%=date1%></div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="nameOfSsi" /></b><bean:write property="appReport.ssiName" name="cpTcDetailsForm"/></div></td>
                </tr>
				<tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="addresses" /></b><bean:write property="appReport.address" name="cpTcDetailsForm"/>
				  <br>&nbsp;<bean:write property="appReport.city" name="cpTcDetailsForm"/> 
				  </br></div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="district" /></b><bean:write property="appReport.district" name="cpTcDetailsForm"/></div></td>
                </tr>
				<tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="state" /></b><bean:write property="appReport.state" name="cpTcDetailsForm"/></div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="unitType" /></b>	<bean:define id="unitType" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>
				  <%String type = unitType.getUnitType();
				  if(type != null)
				  {
	  				   type = type.toUpperCase();
				  }
				  else
				  {
					   type = ""; 
				  }
				  %>
				  <%=type%></div></td>
                </tr>
                <tr>
                   <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="CreditFacilitySanctioned" /></b> <bean:write property="appReport.loanType" name="cpTcDetailsForm"/></div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="bankReference" /></b><bean:write property="appReport.referenceNumber" name="cpTcDetailsForm"/></div></td>
                </tr>
				<tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="ChiefPromoter" /></b><bean:write property="appReport.chiefPromoter" name="cpTcDetailsForm"/></div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="itpanNo" /></b><bean:write property="appReport.itpan" name="cpTcDetailsForm"/></div></td>
                </tr>
				<tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="Gender" /></b><bean:write property="appReport.gender" name="cpTcDetailsForm"/></div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="OtherPromoters" /></b><bean:define id="others" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>
				<% String othersFinal = others.getOthers();
				  if(othersFinal.equals(",,"))
				  {
				  %>
	  			   <%=""%>
				   <%
				  }
				  else
				  {
				  %>
	  			   <%=othersFinal%>
				   <%
				  }
				  %>
				</div></td>
               </tr>
				<tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="SSIRegnNo" /></b> <bean:write property="appReport.registrationNumber" name="cpTcDetailsForm"/></div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="DateofInc" /></b>
				  <bean:define id="startDate" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>
				  <%String date2 = null;
				  java.util.Date newStartDate = startDate.getStartDate();
				  if(newStartDate != null)
				  {
	  				   date2 = dateFormat.format(newStartDate);
				  }
				  else
				  {
					 date2 = "";
				  }
				  %>
				  <%=date2%></div></td>
				</tr>
				<tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="IndustryType" /></b><bean:write property="appReport.industryType" name="cpTcDetailsForm"/></div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="employees" /></b><bean:write property="appReport.employees" name="cpTcDetailsForm"/></div></td>
                </tr>
				<tr>
                  <td class="tableData"> <div align="left">&nbsp; <b>
				  <bean:message key="Turnover" /></b>
				  <bean:define id="turnover" property="appReport" name="cpTcDetailsForm" type="com.cgtsi.reports.ApplicationReport"/>
				  <%double turnoverValue = turnover.getTurnover();%>
				  <%=decimalFormat.format(turnoverValue)%>
				 </div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="Exports" /></b><bean:write property="appReport.export" name="cpTcDetailsForm"/></div></td>
                </tr>
				<tr>
				 <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="Remarks" /></b>	 
				  <bean:define id="remarks" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>
				  <% String remarksFinal = remarks.getRemarks();
				  if((remarksFinal != null) && (!remarksFinal.equals("")))
				  {
				  %>
	  			   <%=remarksFinal%>
				   <%
				  }
				  else
				  {
				  %>
	  			   <%=""%>
				   <%
				  }
				  %>
				</div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="Outstanding" /></b><bean:write property="appReport.outstanding" name="cpTcDetailsForm"/></div></td>
                </tr>
				<tr>
				<td class="tableData"> <div align="left">&nbsp; <b><bean:message key="Status" /></b><bean:write property="appReport.status" name="cpTcDetailsForm"/></div></td>
				<td class="tableData" colspan="3"> <div align="left">&nbsp;  <b><bean:message key="industrySector" /></b><bean:write property="appReport.industrySector" name="cpTcDetailsForm"/></div></td>
				</tr>
				<tr>
				<td class="tableData"> <div align="left">&nbsp; <b><bean:message key="borrowerId" /></b><bean:write property="appReport.bid" name="cpTcDetailsForm"/></div></td>
				<td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="projectOutlayRs" /></b>
				  <bean:define id="projectOutlay" property="appReport" name="cpTcDetailsForm" type="com.cgtsi.reports.ApplicationReport"/>
				  <%double projectCost = projectOutlay.getProjectOutlay();%>
				  <%=decimalFormat.format(projectCost)%>
				  </div></td>
				</tr>
				<tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="TCSanctionedAmount" /></b>
				  <bean:define id="tcSanctioned" property="appReport" name="cpTcDetailsForm" type="com.cgtsi.reports.ApplicationReport"/>
				  <%double tcAmount = tcSanctioned.getTcSanctioned();%>
				  <%=decimalFormat.format(tcAmount)%>
				  </div></td>
                  <td class="tableData" colspan="3"> <div align="left" colspan="3">&nbsp; <b><bean:message key="TCSanctionedDate" /></b>
				  <bean:define id="tcSanctionedOn" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>
				  <%String date3 = null;
				  java.util.Date newTcSanctionedOn = tcSanctionedOn.getTcSanctionedOn();
				  if(newTcSanctionedOn != null)
				  {
	  				   date3 = dateFormat.format(newTcSanctionedOn);
				  }
				  else
				  {
					   date3 = ""; 
				  }
				  %>
				  <%=date3%>
				 </tr>
				<tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="TCPlr" /></b><bean:define id="tcPlr" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>
				  <%double newTcPlr =  tcPlr.getTcPlr();
				  String newTcPlr1 = newTcPlr+"% p.a.";
				  %>
				  <%=newTcPlr1%></div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="TCRate" /></b><bean:define id="tcRate" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>
				  <%double newTcRate =  tcRate.getTcRate();
				  String newTcRate1 = newTcRate+"% p.a.";
				  %>
				  <%=newTcRate1%></div></td>
                </tr>
				<tr>
                <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="TCPromoterContribution" /></b><bean:write property="appReport.tcPromoterContribution" name="cpTcDetailsForm"/></div></td>
				<td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="TCSubsidy" /></b><bean:write 	property="appReport.tcSubsidy" name="cpTcDetailsForm"/></div></td>
				</TR>
				<tr>
                <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="TCEquity" /></b><bean:write property="appReport.tcEquity" name="cpTcDetailsForm"/></div></td>
				    <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="TCOutstanding" /></b><bean:write property="appReport.tcOutstanding" name="cpTcDetailsForm"/></div></td>
				</TR>
					<tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="moratoriumPeriod" /></b><bean:write property="appReport.repaymentMoratorium" name="cpTcDetailsForm"/></div></td>
               	  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="Periodicity" /></b><bean:write property="appReport.repaymentPeriodicity" name="cpTcDetailsForm"/></div></td>
                  </tr>
				<tr>
				<td class="tableData"> <div align="left">&nbsp; <b><bean:message key="FirstInstallmentDate"/></b>
				  <bean:define id="firstInstallmentDueDate" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>
				  <%String date4 = null;
				  java.util.Date newDueDate = firstInstallmentDueDate.getFirstInstallmentDueDate();
				  if(newDueDate != null)
				  {
	  				   date4 = dateFormat.format(newDueDate);
				  }
				  else
				  {
					   date4 = ""; 
				  }
				  %>
				  <%=date4%>
				</div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="NoOfInstallments" /></b><bean:write property="appReport.numberOfInstallments" name="cpTcDetailsForm"/></div></td>
				  <tr>

                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="DisbursementAmount" /></b> <bean:write property="appReport.disbursementAmount" name="cpTcDetailsForm"/></div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="disbursementDate" /></b>
				  <bean:define id="disbursementDate" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>
				  <%String date5 = null;
				  java.util.Date newDisbursementDate = disbursementDate.getDisbursementDate();
				  if(newDisbursementDate != null)
				  {
	  				   date5 = dateFormat.format(newDisbursementDate);
				  }
				  else
				  {
					   date5 = ""; 
				  }
				  %>
				  <%=date5%>
				  </div></td>
                </tr>
				<tr>
          <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="dbrCreatedModifiedBy" /></b><bean:write property="appReport.dbrCreatedModifiedBy" name="cpTcDetailsForm"/></div></td>
          <td class="tableData" colspan="3"> <div align="left">&nbsp;<b><bean:message key="dbrCreatedModifiedDt"/></b>
          <bean:define id="dbrCreatedModifiedDt" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>        
         <%String date8 = null;
				  java.util.Date newDbrCreatedModifiedDt = dbrCreatedModifiedDt.getDbrCreatedModifiedDt();
				  if(newDbrCreatedModifiedDt != null)
				  {
	  				   date8 = dateFormat.format(newDbrCreatedModifiedDt);
				  }
				  else
				  {
					   date8 = ""; 
				  }
				  %>
				  <%=date8%>
         </div></td>
        </tr>
        <tr>
          <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="FinalDisbursement" /></b><bean:write property="appReport.finalDisbursement" name="cpTcDetailsForm"/></div></td>
				  <td class="tableData" colspan="3"></td>
        </tr> 
				<tr>
          <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="WCFBLimitSanctioned" /></b>
				  <bean:define id="wcFbSanctioned" property="appReport" name="cpTcDetailsForm" type="com.cgtsi.reports.ApplicationReport"/>
				  <%double wcFbAmount = wcFbSanctioned.getWcFbSanctioned();%>
				  <%=decimalFormat.format(wcFbAmount)%>
          </div></td>
          <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="WCFBLimitSanctionedOn" /></b>
				  <bean:define id="wcFbSanctionedOn" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>
				  <%String date6 = null;
				  java.util.Date newWcFbSanctionedOn = wcFbSanctionedOn.getWcFbSanctionedOn();
				  if(newWcFbSanctionedOn != null)
				  {
	  				   date6 = dateFormat.format(newWcFbSanctionedOn);
				  }
				  else
				  {
					   date6 = ""; 
				  }
				  %>
				  <%=date6%>
				</tr>
				<tr>
                  <td class="tableData" > <div align="left">&nbsp; <b><bean:message key="WCFBPrincipalOutstanding" /></b><bean:write property="appReport.wcFbPrincipalOutstanding" name="cpTcDetailsForm"/></div></td>
                    <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="WCNFBPrincipalOutstanding" /></b><bean:write property="appReport.wcNfbPrincipalOutstanding" name="cpTcDetailsForm"/></div></td>
                </tr>
				<tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="WCNFBLimitSanctioned" /></b><bean:write property="appReport.wcNfbSanctioned" name="cpTcDetailsForm"/></div></td>
                  <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="WCNFBLimitSanctionedOn" /></b>
				  <bean:define id="wcNfbSanctionedOn" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>
				  <%String date7 = null;
				  java.util.Date newWcNfbSanctionedOn = wcNfbSanctionedOn.getWcNfbSanctionedOn();
				  if(newWcNfbSanctionedOn != null)
				  {
	  				   date7 = dateFormat.format(newWcNfbSanctionedOn);
				  }
				  else
				  {
					   date7 = ""; 
				  }
				  %>
				  <%=date7%>
				</tr>

				<tr>
				 <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="WCPlr" /></b><bean:define id="wcPlr" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>
				  <%double newWcPlr =  wcPlr.getWcPlr();
				  String newWcPlr1 = newWcPlr+"% p.a.";
				  %>
				  <%=newWcPlr1%></div></td>
                 <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="WCInterest" /></b><bean:define id="wcInterest" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>
				  <%double newWcInterest =  wcInterest.getWcInterest();
				  String newWcInterest1 = newWcInterest+"% p.a.";
				  %>
				  <%=newWcInterest1%></div></td>
                </tr>
				<tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="WCPromoterContribution" /></b><bean:write property="appReport.wcPromoterContribution" name="cpTcDetailsForm"/></div></td>
                    <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="WCSubsidy" /></b><bean:write property="appReport.wcSubsidy" name="cpTcDetailsForm"/></div></td>
                    
                </tr>
				<tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="WCEquity" /></b><bean:write property="appReport.wcEquity" name="cpTcDetailsForm"/></div></td>
			  				  <td class="tableData" colspan="3"> <div align="left">&nbsp;</div></td>
                </tr>
                
                
                <tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="appRefNo" /></b><bean:write property="appReport.appRefNo" name="cpTcDetailsForm"/></div></td>
                    <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="ssiReferenceNumber" /></b><bean:write property="appReport.ssiReferenceNumber" name="cpTcDetailsForm"/></div></td>
                    
                </tr>
                <tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="appMliBranchCode" /></b><bean:write property="appReport.appMliBranchCode" name="cpTcDetailsForm"/></div></td>
                    <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="appBankAppRefNo" /></b><bean:write property="appReport.appBankAppRefNo" name="cpTcDetailsForm"/></div></td>
                    
                </tr>
                <tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="appCompositeLoan" /></b><bean:write property="appReport.appCompositeLoan" name="cpTcDetailsForm"/></div></td>
                    <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="usrId" /></b><bean:write property="appReport.usrId" name="cpTcDetailsForm"/></div></td>
                    
                </tr>
                <tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="appLoanType" /></b><bean:write property="appReport.appLoanType" name="cpTcDetailsForm"/></div></td>
                    <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="appCollateralSecurityTaken" /></b><bean:write property="appReport.appCollateralSecurityTaken" name="cpTcDetailsForm"/></div></td>
                    
                </tr>
                <tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="appThirdPartyGuarTaken" /></b><bean:write property="appReport.appThirdPartyGuarTaken" name="cpTcDetailsForm"/></div></td>
                    <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="appSubsidySchemeName" /></b><bean:write property="appReport.appSubsidySchemeName" name="cpTcDetailsForm"/></div></td>
                    
                </tr>
                <tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="appRehabilitation" /></b><bean:write property="appReport.appRehabilitation" name="cpTcDetailsForm"/></div></td>
                    <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="appApprovedDateTime" /></b> 
                    <bean:define id="appApprovedDateTime" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>        
         <%String date10 = null;
				  java.util.Date newAppApprovedDateTime = appApprovedDateTime.getAppApprovedDateTime();
				  if(newAppApprovedDateTime != null)
				  {
	  				   date10 = dateFormat.format(newAppApprovedDateTime);
				  }
				  else
				  {
					   date10 = ""; 
				  }
				  %>
				  <%=date10%>
         </div></td>
                    
                </tr>
                <tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="appApprovedAmount" /></b><bean:write property="appReport.appApprovedAmount" name="cpTcDetailsForm"/></div></td>
                    <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="appGuaranteeFee" /></b><bean:write property="appReport.appGuaranteeFee" name="cpTcDetailsForm"/></div></td>
                    
                </tr>
                <tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="appGuarStartDateTime" /></b>
                  <bean:define id="appGuarStartDateTime" name="cpTcDetailsForm" property="appReport" type="com.cgtsi.reports.ApplicationReport"/>        
         <%String date9 = null;
				  java.util.Date newAppGuarStartDateTime = appGuarStartDateTime.getAppGuarStartDateTime();
				  if(newAppGuarStartDateTime != null)
				  {
	  				   date9 = dateFormat.format(newAppGuarStartDateTime);
				  }
				  else
				  {
					   date9 = ""; 
				  }
				  %>
				  <%=date9%>
         </div></td>
                    <td class="tableData" colspan="3"> <div align="left">&nbsp;</div></td>
                    
                </tr>
                
                
                
                
                <tr>
                  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="dciAmountRaised" /></b><bean:write property="appReport.dciAmountRaised" name="cpTcDetailsForm"/></div></td>
                    <td class="tableData" colspan="3"> <div align="left">&nbsp; <b><bean:message key="dciAppropriationFlag" /></b><bean:write property="appReport.dciAppropriationFlag" name="cpTcDetailsForm"/></div></td>
                    
                </tr>
                
                
                
    <% String claimRefNumber=(String)request.getAttribute("claimRefNumber");
        if(claimRefNumber==null)
        {
        out.println("<tr><td class=\"Heading\" colspan=\"11\"><center>No Claim Details Found for the Entered CGPAN</center</td></tr>");
        }
        else
        {
        %>
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
						<%=wcAmount%>
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
    <%}%>
		</table>
             </td>
          </tr>
		   <tr><td colspan="3" align="left" width="700"><font size="2" color="red">Report Generated On : 
					<% java.util.Date loggedInTime=new java.util.Date();
			          java.text.SimpleDateFormat dateFormat1=new java.text.SimpleDateFormat("dd MMMMM yyyy ':' HH.mm");
			          String date11=dateFormat1.format(loggedInTime);
					  out.println(date11);
					  %>hrs.</font></td></tr>
        </table></td>
      <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
    </tr>
     <tr> 
        <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
        <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
          <div>
            <div align="center">            
            <a href="javascript:submitForm('historyReport.do?method=historyReport')"><img src="images/Back.gif" alt="Submit" width="49" height="37" border="0"></a>            
        </div></td>
        <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
    </tr>
</table>
</html:form>
</body>
</html>
