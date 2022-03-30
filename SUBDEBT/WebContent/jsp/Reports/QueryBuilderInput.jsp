<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="com.cgtsi.reports.QueryBuilderFields" %>
<%@ page import="com.cgtsi.actionform.ReportActionForm" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","queryBuilderInput.do?method=queryBuilderInput");%>
<html:html>

<head>
<meta http-equiv="Content-Language" content="en-us">
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>CGSTI-Query Selection</title>
<link href="css/StyleSheet.css" rel="stylesheet" type="text/css">
<script language="JavaScript1.2" src="js/CGTSI.js"></script>
<script language="JavaScript1.2" src="js/selectdate.js"></script>
</head>

<body>

	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="queryBuilderSelection.do?method=queryBuilderSelection" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</tr>
		<tr>
		<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
		<td>
		<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
			<tr>
			<td class="HeadingBg" align="center">Field Name</td>
			<td class="HeadingBg" align="center">Condition</td>
			<td class="HeadingBg" align="center">Value</td>
			<td class="HeadingBg" align="center">Relation</td>
			<td class="HeadingBg" align="center"> Order By</td>
			</tr>
			<tr>
			<td class="TableData" align="center">CGPAN</td>
			<td class="TableData" align="center">
			<div align="center">
			<html:select name="reportForm" property="queryBuilderFields.cgpanCombo" >
			<html:option value="=">=</html:option>
			<html:option value="<>"><></html:option>
			</html:select></div></td>
			<td class="TableData" align="center">&nbsp;
			<div align="center">
			<html:text property="queryBuilderFields.cgpan" maxlength="13" /></div></td>
			
			<td class="TableData" align="center">&nbsp;
			<div align="center">
			<html:select name="reportForm" property="queryBuilderFields.cgpanBoolean" >
			<html:option  value="and">AND</html:option>
			<html:option value="or">OR</html:option>
			</html:select></div></td>
			<td class="TableData" align="center">&nbsp;</td>
			</tr>
			<tr>
			<td class="TableData" align="center">Application Submitted Date</td>
			<td class="TableData" align="center">
			<div align="center">
			<html:select name="reportForm" property="queryBuilderFields.appSubmittedCombo">
			<html:option value="=">=</html:option>
			<html:option value="<="><=</html:option>
			<html:option value=">=">>=</html:option>
			<html:option value="<>"><></html:option>
			</html:select></div></td>
			<td class="TableData" align="center">&nbsp;
			<div align="center">
			<html:text property="queryBuilderFields.appSubmitted" maxlength="10" />
			
			</div></td>
			<td class="TableData" align="center">&nbsp;
			<div align="center">
			<html:select name="reportForm" property="queryBuilderFields.appSubmittedBoolean">
			<html:option  value="and">AND</html:option>
			<html:option value="or">OR</html:option>
			</html:select></div></td>
			<td class="TableData" align="center">
			<div align="center">
			<html:checkbox property="queryBuilderFields.appSubmittedChkBox" /></div>
			</td>
			</tr>
			<tr>
			<td class="TableData" align="center">SSI Name</td>
			<td class="TableData" align="center">
			<div align="center">
			<html:select name="reportForm" property="queryBuilderFields.ssiNameCombo">
			<html:option value="=">=</html:option>
			<html:option value="LIKE">LIKE</html:option>
			</html:select></div></td>
			<td class="TableData" align="center">&nbsp;
			<div align="center">
			<html:text property="queryBuilderFields.ssiName" maxlength="100" /></div></td>
			<td class="TableData" align="center">&nbsp;
			<div align="center">
			<html:select name="reportForm" property="queryBuilderFields.ssiNameBoolean">
			<html:option  value="and">AND</html:option>
			<html:option value="or">OR</html:option>
			</html:select></div></td>
			<td class="TableData" align="center">&nbsp;</td>
			</tr>
			<tr>
			<td class="TableData" align="center">Term Credit Sanctioned</td>
			<td class="TableData" align="center">
			<div align="center">
			<html:select name="reportForm" property="queryBuilderFields.tcSanctionedCombo">
			<html:option value="=">=</html:option>
			<html:option value="<="><=</html:option>
			<html:option value=">=">>=</html:option>
			</html:select></div></td>
			<td class="TableData" align="center">&nbsp;
			<div align="center">
			<html:text property="queryBuilderFields.tcSanctioned" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></div></td>
			<td class="TableData" align="center">&nbsp;
			<div align="center">
			<html:select name="reportForm" property="queryBuilderFields.tcSanctionedBoolean">
			<html:option  value="and">AND</html:option>
			<html:option value="or">OR</html:option>
			</html:select></div></td>
			<td class="TableData" align="center">
			<div align="center">
			<html:checkbox property="queryBuilderFields.tcSanctionedChkBox" /></div></td>
			</tr>
			<tr>
			<td class="TableData" align="center">Working Capital Sanctioned</td>
			<td class="TableData" align="center">
			<div align="center">
			<html:select name="reportForm" property="queryBuilderFields.wcSanctionedCombo">
			<html:option value="=">=</html:option>
			<html:option value="<="><=</html:option>
			<html:option value=">=">>=</html:option>
			</html:select></div></td>
			<td class="TableData" align="center">&nbsp;
			<div align="center">
			<html:text property="queryBuilderFields.wcSanctioned" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/></div></td>
			<td class="TableData" align="center">&nbsp;
			<div align="center">
			<html:select name="reportForm" property="queryBuilderFields.wcSanctionedBoolean">
			<html:option  value="and">AND</html:option>
			<html:option value="or">OR</html:option>
			</html:select></div></td>
			<td  class="TableData" align="center">
			<div align="center">
			<html:checkbox property="queryBuilderFields.wcSanctionedChkBox" /></div></td>
			</tr>
			<tr>
			<td class="TableData" align="center">Guarantee Fee Paid Date</td>
			<td class="TableData" align="center">
			<div align="center">
			<html:select name="reportForm" property="queryBuilderFields.guarFeePaidCombo">
			<html:option value="=">=</html:option>
			<html:option value="<="><=</html:option>
			<html:option value=">=">>=</html:option>
			<html:option value="<>"><></html:option>
			</html:select></div></td>
			<td class="TableData" align="center">&nbsp;
			<div align="center">
			<html:text property="queryBuilderFields.guarFeePaid" maxlength="10" />
			
			</div></td>
			<td class="TableData" align="center">&nbsp;
			<div align="center">
			<html:select name="reportForm" property="queryBuilderFields.guarFeePaidBoolean">
			<html:option  value="and">AND</html:option>
			<html:option value="or">OR</html:option>
			</html:select></div></td>
			<td class="TableData" align="center">
			<div align="center">
			<html:checkbox property="queryBuilderFields.guarFeePaidChkBox" /></div></td>
			</tr>
		<TR >
						<TD colspan=5 align="center" valign="baseline" >
							<DIV align="center">
									<A href="javascript:submitForm('queryBuilderSelection.do?method=queryBuilderSelection')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
								<A href="javascript:document.reportForm.reset()">
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

</html:html>