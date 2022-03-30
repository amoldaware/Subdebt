<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="com.cgtsi.reports.QueryBuilderFields" %>
<%@ page import="com.cgtsi.actionform.ReportActionForm" %>
<% session.setAttribute("CurrentPage","queryBuilderSelection.do?method=queryBuilderSelection");%>
<HTML>
<HEAD>
<TITLE>
CGTSI-Query Builder Fields Selection
</TITLE>
<link href="css/StyleSheet.css" rel="stylesheet" type="text/css">
<script language="JavaScript1.2" src="js/CGTSI.js"></script>

<script>
function chkAll_onclick() {

	if(document.reportForm.elements['queryBuilderFields.selectAllSelChkBox'].checked)
	{
	    document.reportForm.elements['queryBuilderFields.selectAllSelChkBox'].checked = true;
		document.reportForm.elements['queryBuilderFields.applnRefnoSelChkBox'].checked = true;
		document.reportForm.elements['queryBuilderFields.cgpanSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.bankRefNoSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.appSubmittedSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.tcPLRSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.wcPLRSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.chiefPromoterSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.itPANSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.ssiDetailsSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.tcSanctionedSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.tcTenureSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.tcInterestRateSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.wcSanctionedSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.projectOutlaySelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.approvedDateSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.approvedAmtSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.guarFeeSelChkBox'].checked = true;
	    document.reportForm.elements['queryBuilderFields.guarFeeStartDateSelChkBox'].checked = true;
	}
	else
	{
	    document.reportForm.elements['queryBuilderFields.selectAllSelChkBox'].checked = false;
		document.reportForm.elements['queryBuilderFields.applnRefnoSelChkBox'].checked = false;
		document.reportForm.elements['queryBuilderFields.cgpanSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.bankRefNoSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.appSubmittedSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.tcPLRSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.wcPLRSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.chiefPromoterSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.itPANSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.ssiDetailsSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.tcSanctionedSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.tcTenureSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.tcInterestRateSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.wcSanctionedSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.projectOutlaySelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.approvedDateSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.approvedAmtSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.guarFeeSelChkBox'].checked = false;
	    document.reportForm.elements['queryBuilderFields.guarFeeStartDateSelChkBox'].checked = false;
	}	
}


function resetValues()
{
	document.reportForm.reset();
}
</script>
</HEAD>
<body>

	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="queryBuilderResult.do?method=queryBuilderResult" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</tr>
		<tr>
		<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
		<td>
		<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
			<tr>
			<td colspan = 2>
				<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
					<TR>
						<TD width="27%" class="Heading"><bean:message key="querySelectionHeader" /></TD>
						<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
					</TR>
					<TR>
						<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
					</TR>

				</TABLE>
			</td>
			</tr>
			<tr>
			<td class="HeadingBg" align="center">Field Name</td>
			<td class="HeadingBg" align="center">Select</td>
			</tr><tr><td class="TableData" align="center">Select All Fields : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.selectAllSelChkBox" onclick="chkAll_onclick()"/> </td>
			</tr><tr><td class="TableData" align="center">Application Reference Number : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.applnRefnoSelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">CGPAN : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.cgpanSelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">Bank Application Reference Number : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.bankRefNoSelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">Application Submitted Date : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.appSubmittedSelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">Term Loan PLR Value : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.tcPLRSelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">Working Capital PLR Value : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.wcPLRSelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">Chief Promoter Name : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.chiefPromoterSelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">IT Pan No : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.itPANSelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">SSI Details : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.ssiDetailsSelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">Term Credit Sactioned : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.tcSanctionedSelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">Interest Rate of Term Credit : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.tcTenureSelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">Tenure of Term Credit : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.tcInterestRateSelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">Working Capital Sanctioned : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.wcSanctionedSelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">Total Project Outlay : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.projectOutlaySelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">Approved Date Time : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.approvedDateSelChkBox" />
			</td>
			</tr><tr>
			<td class="TableData" align="center">Approved Amount : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.approvedAmtSelChkBox" />
			</td>
			</tr><tr>
			<td class="TableData" align="center">Guarantee Fee : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.guarFeeSelChkBox" />
			</td>
			</tr><tr><td class="TableData" align="center">Status/Guarantee Fee Start Date : </td>
			<td class="TableData" align="center">
			<html:checkbox property="queryBuilderFields.guarFeeStartDateSelChkBox" />
			</td>
			</tr>
			<TR >
				<TD colspan=5 align="center" valign="baseline" >
				<DIV align="center">
				<!--Fix Bug 070904 - 21 -->
					<A href="javascript:submitForm('queryBuilderResult.do?method=queryBuilderResult')">
					<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
				<!--Fix completed-->
					<A href="javascript:resetValues()">
					<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
					<A href="javascript:submitForm('queryBuilderCancel.do?method=queryBuilderCancel')">
					<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>

				</DIV>
				</TD>
			</TR>
		</table>
		</td>
		<TD width="20" background="images/TableVerticalRightBG.gif">
								&nbsp;
		</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top">
			<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
			</TD>
			<TD background="images/TableBackground2.gif">
								&nbsp;
			</TD>
			<TD width="20" align="left" valign="top">
			<IMG src="images/TableRightBottom1.gif" width="23" height="15">
			</TD>
		</TR>
		</html:form>
	</table>


</body>
</html>
