<TABLE border="0" cellpadding="1" cellspacing="1" width="100%" >
	<TR>
		<TD width="31%" class="SubHeading"><bean:message key="mcgfHeader" /></TD>
	</TR>  
 <TR>
    <TD class="ColumnBackground"><font color="#FF0000" size="2">*</font><bean:message key="participatingBankName"/></TD>
    <TD class="TableData">
	<%
		String appMCGFFlag=session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).toString();

		if((appMCGFFlag.equals("11"))||(appMCGFFlag.equals("12"))||(appMCGFFlag.equals("13")))		

		{
		%>
		<bean:write name="appForm" property="participatingBank"/>
		<%}
		else
		{ %>
		<html:select property="participatingBank" name="appForm" size="1">
		<html:option value="">Select</html:option>
		<html:options property="participatingBanks" name="appForm"/>
		</html:select>
	<%}%>
    </TD>
    
    <TD class="ColumnBackground"><bean:message key="participatingBankBranchName"/>
    </TD>
    <TD class="TableData">
	<%
	if((appMCGFFlag.equals("11"))||(appMCGFFlag.equals("12"))||(appMCGFFlag.equals("13")))		

		{
		%>
		<bean:write name="appForm" property="participatingBankBranch"/>
		<%}
		else
		{ %>
    	<html:text property="participatingBankBranch" size="20"  name="appForm" maxlength="100"/>
	<%}%>
    </TD>    
  </TR>
  <TR>
    <TD class="ColumnBackground"><bean:message key="district"/></TD>
    <TD class="TableData">
	<%
	if((appMCGFFlag.equals("11"))||(appMCGFFlag.equals("12"))||(appMCGFFlag.equals("13")))		

	{
	%>
	<bean:write name="appForm" property="mcgfDistrict"/>
	<%}
	else
	{ %>
    	<html:text property="mcgfDistrict" size="20"  name="appForm" maxlength="100"/>
	<%}%>
    </TD>
	<TD class="ColumnBackground"><bean:message key="mcgfId"/></TD>
	<TD class="TableData"><bean:write property="mcgfId" name="appForm" /></TD>
  </TR>
  <TR>
    <TD class="ColumnBackground"><bean:message key="mcgfName"/></TD>
    <TD class="TableData"><bean:write property="mcgfName" name="appForm" /></TD>
    <TD class="ColumnBackground"><bean:message key="mcgfApprovedAmount"/></TD>
    
    <TD class="TableData">
	<%
	if((appMCGFFlag.equals("11"))||(appMCGFFlag.equals("12"))||(appMCGFFlag.equals("13")))		

	{
	%>
	<bean:write name="appForm" property="mcgfApprovedAmt"/>&nbsp;<bean:message key="inRs"/>
	<%}
	else
	{ %>
    	<html:text property="mcgfApprovedAmt" size="20"  name="appForm" maxlength="13" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>&nbsp;<bean:message key="inRs"/>
	<%}%>
    </TD>
  </TR>
  <TR>
    <TD class="ColumnBackground"><bean:message key="mcgfApprovedDate"/></TD>
    <TD class="TableData">
	<%
	if((appMCGFFlag.equals("11"))||(appMCGFFlag.equals("12"))||(appMCGFFlag.equals("13")))		

	{
	%>
	<bean:write name="appForm" property="mcgfApprovedDate"/>
	<%}
	else
	{ %>
	    <html:text property="mcgfApprovedDate" size="20"  name="appForm" maxlength="10"/>
	    <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.mcgfApprovedDate')" align="center">
	<%}%>
    </TD>  
    <TD class="ColumnBackground"><bean:message key="mcgfGuaranteeCoverStartDate"/></TD>
    <TD class="TableData">
	<%
	if((appMCGFFlag.equals("11"))||(appMCGFFlag.equals("12"))||(appMCGFFlag.equals("13")))		

	{
	%>
	<bean:write name="appForm" property="mcgfGuaranteeCoverStartDate"/>
	<%}
	else
	{ %>
	    <html:text property="mcgfGuaranteeCoverStartDate" size="20"  name="appForm" maxlength="10"/>
	    <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.mcgfGuaranteeCoverStartDate')" align="center">
	<%}%>
    </TD>
 </TR>
</TABLE>

