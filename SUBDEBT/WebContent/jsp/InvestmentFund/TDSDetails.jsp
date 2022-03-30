<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
String flag = (String) session.getAttribute("flag");
String focusObj="investeeName";
if (flag.equals("I"))
{
	session.setAttribute("CurrentPage","showTDSDetails.do?method=showTDSDetails");
}
else if (flag.equals("U"))
{
	session.setAttribute("CurrentPage","showUpdateTDS.do?method=showUpdateTDSDetail");
	focusObj="tdsAmount";
}
%>
<% 
	org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
	if (errors!=null && !errors.isEmpty())
	{
		focusObj="test";
	}
%>
<html:form action="updateTDSDetails.do?method=updateTDSDetails" method="POST" focus="<%=focusObj%>">
<html:hidden name="ifForm" property="test"/>
<html:errors />
  <table width="100%" border="0" cellpadding="0" cellspacing="0">

	<tr>
		<td><br></td>
	</tr>
    <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
    <tr> 
      <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
      <td><table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
	  <tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpTDSDetails.do?method=helpTDSDetails')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
          <tr> 
            <td><table width="100%" border="0" cellspacing="1" cellpadding="1">
                <tr > 
                  <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="25%" class="Heading">
						<bean:message key="TDSDetails" /></td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr align="left" valign="top"> 
                  <td width="20%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="investee"/>
				  &nbsp;</div></td>
                  <td align="left" valign="top" class="tableData">
				  <%
					if (flag.equals("I"))
					{
				%>
				<html:select property="investeeName" styleId="investeeName"  name="ifForm">
					<html:option value="">Select </html:option>
					<html:options name="ifForm" property="investeeNames"/>
				</html:select>
				<%
					}
					else if (flag.equals("U"))
					{
				%>
				<bean:write property="investeeName" name="ifForm"/>
				<%
					}
				  %>
                  </td>
                </tr>
                <tr align="left" valign="top"> 
                  <td align="left" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentName"/></div></td>
                  <td align="left" class="tableData">
				  <%
					if (flag.equals("I"))
					{
				%>
					  <html:select property="instrumentName" styleId="instrumentName"  name="ifForm" onchange="javascript:submitForm('showTDSDetails.do?method=showTDSInvRefNos')">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="instrumentNames"/>
					  </html:select>
				<%
					}
					else if (flag.equals("U"))
					{
				%>
				<bean:write property="instrumentName" name="ifForm"/>
				<%
					}
				  %>

				  </td>
                </tr>
				<tr>
					<td class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="investmentReferenceNumbers"/></td>
					<td class="tableData">
				  <%
					if (flag.equals("I"))
					{
				%>
					  <html:select property="investmentRefNumber" styleId="investmentRefNumber"  name="ifForm">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="investmentRefNumbers"/>
					  </html:select>
				<%
					}
					else if (flag.equals("U"))
					{
				%>
				<bean:write property="investmentRefNumber" name="ifForm"/>
				<%
					}
				  %>

					</td>
				</tr>
				<tr>
					<td class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="tdsAmount"/></td>
					<td class="tableData">
						<html:text  property="tdsAmount" name="ifForm" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
					</td>
				</tr>
<!--				<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="tdsReminderDate"/></td>
					<td class="tableData" align="center">
						<html:text  property="reminderDate" name="ifForm" maxlength="10"/><img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.reminderDate')" align="center">
					</td>
				</tr>-->
				<tr>
					<td class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="tdsDeductedDate"/></td>
					<td class="tableData" align="center">
						<html:text  property="tdsDeductedDate" name="ifForm" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/><img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.tdsDeductedDate')" align="center">
					</td>
				</tr>				
				<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="tdsCertificateReceived" /> 
					<td class="tableData" align="center">
						<html:radio name="ifForm" value="Y" property="isTDSCertificateReceived"/>Yes
						<html:radio  name="ifForm" value="N" property="isTDSCertificateReceived"/>No
					</td>
				</tr>
				<html:hidden property="tdsID" name="ifForm"/>
              </table></td>
          </tr>
          <tr > 
            <td height="20" >&nbsp;</td>
          </tr>
          <tr > 
            <td align="center" valign="baseline" > <div align="center"><html:link href="javascript:submitForm('updateTDSDetails.do?method=updateTDSDetails')"><img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></html:link>
			 <%
		  if (flag.equals("I"))
		  {
		  %>
			<html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></html:link> 
			<%}%>
					  <%
		  if (flag.equals("U"))
		  {
		  %>
		            <A href="javascript:history.back()">
						<IMG src="images/Back.gif" alt="Save" width="49" height="37" border="0">
						</A>
		<%
		  }
			  %>
              </div></td>
          </tr>
        </table></td>
      <td width="20" background = "images/TableVerticalRightBG.gif">&nbsp;</td>
    </tr>
    <tr> 
      <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
      <td background="images/TableBackground2.gif">&nbsp;</td>
      <td width="20" align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
    </tr>
  </table>

  <br>
  <p>&nbsp;</p>
</html:form>
