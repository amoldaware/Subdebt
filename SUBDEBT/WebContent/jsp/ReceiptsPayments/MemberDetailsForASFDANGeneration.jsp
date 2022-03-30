<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ page import="com.cgtsi.common.Constants"%>
<%
String danType = (String)session.getAttribute(SessionConstants.DAN_TYPE);
if (danType.equals(Constants.CGDAN))
{
	session.setAttribute("CurrentPage","generateDANs.do?method=generateCGDAN");
}
else if (danType.equals(Constants.SFDAN))
{
	session.setAttribute("CurrentPage","generateDANs.do?method=generateSFDAN");
}
else if (danType.equals(Constants.SFDANEXP))
{
	session.setAttribute("CurrentPage","generateDANs.do?method=showSFDANGenFilterForExpired");
}
else if (danType.equals(Constants.SHDAN))
{
	session.setAttribute("CurrentPage","generateDANs.do?method=generateSHDAN");
}
else if (danType.equals(Constants.CLDAN))
{
	session.setAttribute("CurrentPage","generateDANs.do?method=generateCLDAN");
}
else if (danType.equals("EXCESS"))
{
	session.setAttribute("CurrentPage","generateExcessVoucherFilter.do?method=generateExcessVoucherFilter");
}
else if (danType.equals("BATCHSFDAN"))
{
	session.setAttribute("CurrentPage","generateBatchASFDANs.do?method=showBatchSFDANGenFilter");
}
%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<%
		String targetURL = (String)session.getAttribute(SessionConstants.TARGET_URL) ;
	%>	
	<html:form action="<%=targetURL%>" method="POST" focus="selectMember">

		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="661" border="0" cellspacing="1" cellpadding="0">
							 <TR>
								<TD colspan="7">
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="31%" class="Heading"><bean:message key="chooseMember" /></TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>
							
							<table width="661" border="0" cellspacing="1" cellpadding="1">
								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<bean:message key="selectMember" />
										</div>
									 </td>
									 <td class="TableData" width="500" valign="top">
										<div align="left" >
										  <html:select property="selectMember" name="rpGenerateDANForm">
											<html:option value="">Select</html:option><html:option value="All">All</html:option>	
											<html:options name="rpGenerateDANForm" property="mliNames"/>					
										  </html:select>
										</div>
									 </td>
								</tr>
							 </table>									
						</td>
					</tr>

					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">								
								<A href="javascript:submitForm('<%=targetURL%>')"><IMG  src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>								
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