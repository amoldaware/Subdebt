<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ page import="com.cgtsi.reports.QueryBuilderFields" %>
<%@ page import="com.cgtsi.reports.QueryReport" %>
<%@ page import="com.cgtsi.actionform.ReportActionForm" %>
<%@ page import="java.util.ArrayList" %>
<% session.setAttribute("CurrentPage","queryBuilderResult.do?method=queryBuilderResult");%>
<html:html>

<head>
<meta http-equiv="Content-Language" content="en-us">
<meta name="GENERATOR" content="Microsoft FrontPage 5.0">
<meta name="ProgId" content="FrontPage.Editor.Document">
<meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
<title>CGSTI-Query Builder Report</title>
<link href="css/StyleSheet.css" rel="stylesheet" type="text/css">
<script language="JavaScript1.2" src="js/CGTSI.js"></script>
</head>

<body>
<html:errors />
	<html:form action="queryBuilderSelection.do?method=queryBuilderSelection" method="POST" enctype="multipart/form-data">
	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	
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
			<td>
				<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
				<td class="TableData" align="left"><b><bean:message key="queryCgpan" /></b>
				<bean:write name="reportForm" property="queryBuilderFields.cgpan"/>
				</td>
				<td class="TableData" align="left"><b><bean:message key="queryAppSubDate" /></b>
				<bean:write name="reportForm" property="queryBuilderFields.appSubmitted"/>
				</td>
				<td class="TableData" align="left"><b><bean:message key="queryGuarFeePaidDate" /></b>
				<bean:write name="reportForm" property="queryBuilderFields.guarFeePaid"/>
				</td>
				</tr>
				<tr>
				<td class="TableData" align="left"><b><bean:message key="queryssiName" /></b>
				<bean:write name="reportForm" property="queryBuilderFields.ssiName"/>
				</td>
				<td class="TableData" align="left"><b><bean:message key="querytcSanctioned" /></b>
				<bean:write name="reportForm" property="queryBuilderFields.tcSanctioned"/>
				</td>
				<td class="TableData" align="left"><b><bean:message key="querywcSanctioned" /></b>
				<bean:write name="reportForm" property="queryBuilderFields.wcSanctioned"/>
				</td>
				</tr>
				</TABLE>
			</td>
			</tr>
			<tr>
			<td>
				<br>
				<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
				<tr>
				<%
				ReportActionForm rForm = (ReportActionForm)session.getAttribute("reportForm");
				ArrayList resultSet = rForm.getQueryReport();
				QueryBuilderFields fields = rForm.getQueryBuilderFields();
				
				if(resultSet.size() != 0)
				{

					out.println("<td class=\"HeadingBg\" align=\"center\">S.No</td>");

					if(fields.isApplnRefnoSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Application Reference Number</td>");
					}
					
					if(fields.isCgpanSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">CGPAN</td>");
					}

					if(fields.isBankRefNoSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Bank Application Reference Number</td>");
					}
					
					if(fields.isAppSubmittedSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Application Submitted Date</td>");
					}
					if(fields.isTcPLRSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Term Loan PLR Value</td>");
					}
					if(fields.isWcPLRSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Working Capital PLR Value</td>");
					}
					if(fields.isChiefPromoterSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Chief Promoter Name</td>");
					}
					if(fields.isItPANSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">IT Pan No.</td>");
					}
					if(fields.isSsiDetailsSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">SSI Details</td>");
					}
					if(fields.isTcSanctionedSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Term Credit Sanctioned</td>");
					}
					if(fields.isTcInterestRateSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Interest Rate of Term Credit</td>");
					}
					if(fields.isTcTenureSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Tenure of Term Credit</td>");
					}					
					if(fields.isWcSanctionedSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Working Capital Sanctioned</td>");
					}
					if(fields.isProjectOutlaySelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Total Project Outlay</td>");
					}
					if(fields.isApprovedDateSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Approved Date Time</td>");
					}
					if(fields.isApprovedAmtSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Approved Amount</td>");
					}
					if(fields.isGuarFeeSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Guarantee Fee</td>");
					}										
					if(fields.isGuarFeeStartDateSelChkBox())
					{
						out.println("<td class=\"HeadingBg\" align=\"center\">Status/Guarantee Fee Start Date</td>");
					}
					out.println("</tr>");
					QueryReport report = null;
					for(int i = 0; i < resultSet.size(); i++)
					{
						report = (QueryReport)resultSet.get(i);
						out.println("<tr>");
						out.println("<td class=\"TableData\" align=\"center\">");
						out.println(i);
						out.println("</td>");

						if(fields.isApplnRefnoSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"center\">");
							out.println(report.getApplnRefNo());
							out.println("</td>");
						}

						if(fields.isCgpanSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"center\">");
							String cgpan = report.getCgpan();
							if((cgpan == null) || (cgpan.equals("")))
							{
								out.println("");
							}
							else
							{
								out.println(cgpan);
							}
							out.println("</td>");
						}

						if(fields.isBankRefNoSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"center\">");
							out.println(report.getBankApplnRefNumber());
							out.println("</td>");
						}
						if(fields.isAppSubmittedSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"center\">");
							out.println(report.getApplSubmittedDate());
							out.println("</td>");							
						}
						if(fields.isTcPLRSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"right\">");
							out.println("<div align=\"right\">");
							out.println(report.getTermLoanPLR());
							out.println("</div>");							
							out.println("</td>");							
						}
						if(fields.isWcPLRSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"right\">");
							out.println("<div align=\"right\">");
							out.println(report.getWorkingCapitalPLR());
							out.println("</div>");							
							out.println("</td>");							
						}
						if(fields.isChiefPromoterSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"center\">");
							out.println(report.getChiefPromoterName());
							out.println("</td>");							
						}
						if(fields.isItPANSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"center\">");
							out.println(report.getITPanNo());
							out.println("</td>");							
						}
						if(fields.isSsiDetailsSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"center\">");
							out.println("<b>Name : </b>"+report.getSsiName()+"<br/>");
							out.println("<b>Address : </b>"+report.getSsiAddress()+"<br/>");
							out.println("<b>City : </b>"+report.getCity()+"<br/>");
							out.println("<b>Pin : </b>"+report.getPin()+"<br/>");
							out.println("<b>District : </b>"+report.getDistrict()+"<br/>");
							out.println("<b>State : </b>"+report.getState()+"<br/>");
							out.println("<b>Constitution : </b>"+report.getUnitType()+"<br/>");
							out.println("</td>");							
						}
						if(fields.isTcSanctionedSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"right\">");
							out.println("<div align=\"right\">");
							out.println(report.getTermCreditSanctioned());
							out.println("</div>");							
							out.println("</td>");							
						}
						if(fields.isTcInterestRateSelChkBox())
						{
							out.println("<td class=\"TableData\"  align=\"right\">");
							out.println("<div align=\"right\">");
							out.println(report.getTermLoanIntRate());
							out.println("</div>");							
							out.println("</td>");							
						}
						if(fields.isTcTenureSelChkBox())
						{
							out.println("<td class=\"TableData\"  align=\"right\">");
							out.println("<div align=\"right\">");
							out.println(report.getTermLoanTenure());
							out.println("</div>");							
							out.println("</td>");							
						}					
						if(fields.isWcSanctionedSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"right\">");
							out.println("<div align=\"right\">");
							out.println(report.getWorkingCapitalSanctioned());
							out.println("</div>");							
							out.println("</td>");							
						}
						if(fields.isProjectOutlaySelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"right\">");
							out.println("<div align=\"right\">");
							out.println(report.getProjectOutlay());
							out.println("</div>");							
							out.println("</td>");							
						}
						if(fields.isApprovedDateSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"center\">");
							out.println(report.getApprovedDate());
							out.println("</td>");							
						}
						if(fields.isApprovedAmtSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"right\">");
							out.println("<div align=\"right\">");
							out.println(report.getApprovedAmount());
							out.println("</div>");							
							out.println("</td>");							
						}
						if(fields.isGuarFeeSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"right\">");
							out.println("<div align=\"right\">");
							out.println(report.getGuaranteeFee());
							out.println("</div>");							
							out.println("</td>");							
						}										
						if(fields.isGuarFeeStartDateSelChkBox())
						{
							out.println("<td class=\"TableData\" align=\"center\">");
							out.println(report.getGuaranteeFeeDate());
							out.println("</td>");							
						}
						out.println("</tr>");
					}
					report = null;
					
				}
				resultSet = null;
				fields = null;
				%>
				</TABLE>
			</td>
			</tr>
			<TR >
			<TD colspan=5 align="center" valign="baseline" >
			<DIV align="center">
				<A href="javascript:submitForm('queryBuilderInput.do?method=queryBuilderInput')">
				<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>

			</DIV>
			</TD>
			</TR>	
			</TABLE>
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
	
	</table>
</html:form>
</body>
</html:html>