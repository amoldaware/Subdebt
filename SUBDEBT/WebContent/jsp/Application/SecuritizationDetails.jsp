
<table width="100%" border="0" cellspacing="1" cellpadding="0" colspan="5">

	<tr>
		<td class="ColumnBackground" width="30%">
			<bean:message key="spreadOverPLR" />
		</td>
		<td class="TableData">
		<%
		String appSecFlag=session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).toString();

			if((appSecFlag.equals("11"))||(appSecFlag.equals("12"))||(appSecFlag.equals("13")))		

			{
			%>
			<bean:write name="appForm" property="spreadOverPLR"/>
			<%}
			else
			{ %>
			<html:text property="spreadOverPLR" size="20" alt="spreadOverPLR" name="appForm" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)" maxlength="5"/>
			<%}%>
		</td>
		<td class="ColumnBackground">
			&nbsp;&nbsp;<bean:message key="pplRepaymentInEqual" />
		</td>
		<td class="TableData" colspan="4">
		<%
		if((appSecFlag.equals("11"))||(appSecFlag.equals("12"))||(appSecFlag.equals("13")))		

		{
		%>
		<bean:write name="appForm" property="pplRepaymentInEqual"/>
		<%}
		else
		{ %>
			<html:radio name="appForm" value="Y" property="pplRepaymentInEqual"  ></html:radio>
											
			<bean:message key="yes" />&nbsp;&nbsp;			
				<html:radio name="appForm" value="N" property="pplRepaymentInEqual" ></html:radio>		
			<bean:message key="no"/>
		<%}%>
		</td>
	</tr>	
	<tr align="left">
		<td class="ColumnBackground">
			<bean:message key="tangibleNetWorth" />
		</td>
		<td class="TableData">
		<%
		if((appSecFlag.equals("11"))||(appSecFlag.equals("12"))||(appSecFlag.equals("13")))		

		{
		%>
		<bean:write name="appForm" property="tangibleNetWorth"/>&nbsp;&nbsp;<bean:message key="inRs" />
		<%}
		else
		{ %>
			<html:text property="tangibleNetWorth" size="20" alt="tangibleNetWorth" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>&nbsp;<bean:message key="inRs" />
		<%}%>
		</td>
		<td class="ColumnBackground">
			&nbsp;&nbsp;<bean:message key="fixedACR" />
		</td>
		<td class="TableData">
		<%
		if((appSecFlag.equals("11"))||(appSecFlag.equals("12"))||(appSecFlag.equals("13")))		

		{
		%>
		<bean:write name="appForm" property="fixedACR"/>
		<%}
		else
		{ %>
			<html:text property="fixedACR" size="20" alt="fixedACR" name="appForm" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)" maxlength="5"/>
		<%}%>
		</td>
	</tr>
	<tr align="left">
		<td class="ColumnBackground">
			<bean:message key="currentRatio" />
		</td>
		<td class="TableData" colspan="3">
		<%
		if((appSecFlag.equals("11"))||(appSecFlag.equals("12"))||(appSecFlag.equals("13")))		

		{
		%>
		<bean:write name="appForm" property="currentRatio"/>
		<%}
		else
		{ %>
			<html:text property="currentRatio" size="20" alt="currentRatio" name="appForm" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)" maxlength="5"/>
		<%}%>
		</td>
<!--		<td class="ColumnBackground">
			&nbsp;&nbsp;<bean:message key="financialPartUnit" />
			<br><font color="#FF0000" size="1">&nbsp;
				<bean:message key="financialPartUnitHint"/>
				</font>						
			</br>
		</td>
		<td class="TableData">
		<%
		if((appSecFlag.equals("11"))||(appSecFlag.equals("12"))||(appSecFlag.equals("13")))		

		{
		%>
		<bean:write name="appForm" property="financialPartUnit"/>
		<%}
		else
		{ %>
			<html:text property="financialPartUnit" size="20" alt="financialPartUnit" name="appForm" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)" maxlength="13"/>
		<%}%>
		</td>-->
	</tr>
	<tr align="left">
		<td class="ColumnBackground">
			<bean:message key="minimumDSCR" />
		</td>
		<td class="TableData">
		<%
		if((appSecFlag.equals("11"))||(appSecFlag.equals("12"))||(appSecFlag.equals("13")))		

		{
		%>
		<bean:write name="appForm" property="minimumDSCR"/>
		<%}
		else
		{ %>
			<html:text property="minimumDSCR" size="20" alt="minimumDSCR" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
		<%}%>
		</td>
		<td class="ColumnBackground">
			&nbsp;&nbsp;<bean:message key="avgDSCR" />
		</td>
		<td class="TableData">
		<%
		if((appSecFlag.equals("11"))||(appSecFlag.equals("12"))||(appSecFlag.equals("13")))		

		{
		%>
		<bean:write name="appForm" property="avgDSCR"/>
		<%}
		else
		{ %>
			<html:text property="avgDSCR" size="20" alt="avgDSCR" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
		<%}%>
		</td>
	</tr>
</table>
