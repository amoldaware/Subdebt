<%@ page import="com.cgtsi.util.SessionConstants"%>
<table width="100%" border="0" cellspacing="1" cellpadding="0">
	<TR>
		<TD colspan="8">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="31%" class="Heading"><bean:message key="facilityDetails" /></TD>
					<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
				</TR>
				<TR>
					<TD colspan="8" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	<%
		String appFFlag=(String)session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG);
		if(appFFlag.equals("1") || appFFlag.equals("2"))
		{
		%>
		
	<tr align="left">
		<td class="ColumnBackground">
			<bean:message key="facilityRehabilitation" />
		</td>
		<td class="TableData" colspan="3">
			<html:radio name="appForm" value="Y" property="rehabilitation" ><bean:message key="yes" /></html:radio>
			<html:radio name="appForm" value="N" property="rehabilitation" ><bean:message key="no" /></html:radio>
		</td>
	</tr>
	<%}%>
	<tr align="left">
		<td class="ColumnBackground">&nbsp;<bean:message key="compositeLoan" />
		</td>
		<td colspan="8" class="TableData">&nbsp;No
		</td>
	</tr>
	<tr align="left">
		<td width="30%" class="ColumnBackground" id="loanType"><div align="left">&nbsp;
			<bean:message key="loanType"/></div>
			<html:hidden property="loanType" name="appForm"/>
		</td>
		<td colspan="8" class="TableData"><div align="left">&nbsp;Term Loan Capital</div>
		</td>
	</tr>
	<tr>
		<td class="ColumnBackground">&nbsp;<bean:message key="scheme" />
		</td>
		<td class="TableData" colspan="8">&nbsp;
		<%
		if (session.getAttribute(SessionConstants.MCGF_FLAG).equals("M"))
		{%>
		<bean:write property="scheme" name="appForm"/>
		<%}
		else
		{%>
		<bean:write property="scheme" name="appForm"/><%}%>
		</td>               
	</tr>
</table>