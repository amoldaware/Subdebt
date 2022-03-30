<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","chequeDetailsReportPage.do?method=chequeDetailsReportPage");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="chequeDetailsReportPage.do?method=chequeDetailsReportPage" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="8"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="20%" class="Heading"><bean:message key="chequeHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
									<tr> 
									  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="chequeNumbers" /></b></td>
									 <td class="tableData"> <div align="left"><bean:write property="chequeNumber" name="ifForm"/></div></td>
									</tr> 
									<tr> 
									  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="chequeIssuedDate" /></b></td>
									 <td class="tableData"> <div align="left">
									<bean:define id="chequeDate" name="ifForm" property="chequeDate"/>
								  <%String date1 = null;
								  java.util.Date newApplicationDate = (java.util.Date)chequeDate;
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

									</tr>
									<html:hidden property="chequeId" name="ifForm"/>
									<tr> 
									  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="chequeAmount" /></b></td>
									<td class="tableData"> <div align="left"><bean:write property="chequeAmount" name="ifForm"/></div></td>
									  </tr> 
									  <tr> 
									  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="chequeIssuedTo" /></b></td>
									 <td class="tableData"> <div align="left"><bean:write property="chequeIssuedTo" name="ifForm"/></div></td>
									</tr>
									<tr> 
									  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="chequeBankName" /></b></td>
									 <td class="tableData"> <div align="left"><bean:write property="bankName" name="ifForm"/></div></td>
									</tr>
									<tr> 
									  <td class="tableData"> <div align="left">&nbsp; <b><bean:message key="remarks" /></b></td>
									 <td class="tableData"> <div align="left"><bean:write property="chequeRemarks" name="ifForm"/></div></td>
									</tr>

 									</TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
						<DIV align="center">
									<A href="javascript:submitForm('chequeDetailsReport.do?method=chequeDetailsReport')">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>

									
							</DIV>
						</TD>
					</TR>
				</TABLE>
			</TD>
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
</TABLE>

