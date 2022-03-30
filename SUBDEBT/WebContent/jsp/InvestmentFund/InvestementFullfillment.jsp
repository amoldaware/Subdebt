<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
String flag = (String)session.getAttribute("fullfilmentFlag");
String focusObj="investeeName";
if (flag.equals("0"))
{
	session.setAttribute("CurrentPage","showInvestementFullfillment.do?method=showInvestementFullfillment");
}
else if (flag.equals("1"))
{
	session.setAttribute("CurrentPage","showInvestementFullfillment.do?method=showInvFullfilmentRefNos");
	focusObj="instrumentName";
}
else if (flag.equals("2"))
{
	session.setAttribute("CurrentPage","showInvestementFullfillment.do?method=showInvFullfilmentDetails");
	focusObj="instrumentType";
}
%>
<% 
	org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
	if (errors!=null && !errors.isEmpty())
	{
		focusObj="test";
	}
%>
<body onload="ioFlagOption(),disableCheque()">
<html:form action="updateInvestementFullfillment.do?method=updateInvestementFullfillment" method="POST" focus="<%=focusObj%>">
<html:hidden name="ifForm" property="test"/>
<html:errors />
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="20"><br></td>
	</tr>
    <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif" width="864"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
    <tr> 
      <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
      <td width="100%">
      	<table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
		<tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpInvestmentFulfillmentDetails.do?method=helpInvestmentFulfillmentDetails')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
	<TR>
		<TD colspan="4"> 
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="31%" class="Heading">
					<bean:message key="investment_fulfillment" /></TD>
					<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
				</TR>
				<TR>
					<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
				</TR>

			</TABLE>
		</TD>

	</TR>      
      	
          <tr> 
            <td><table width="100%" border="0" cellspacing="1" cellpadding="1">
                <tr align="left" valign="top"> 
                  <td width="371" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="investee"/>&nbsp;</div></td>
                  <td align="left" valign="top" class="tableData" width="476">
					<html:select property="investeeName" styleId="investeeName"  name="ifForm">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="investeeNames"/>
					</html:select>&nbsp;
                  </td>
                </tr>
                <tr align="left" valign="top"> 
                  <td align="left" valign="top" class="ColumnBackground" width="371"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentName"/></div></td>
                  <td align="left" class="tableData" width="476">
					<html:select property="instrumentName" styleId="investment"  name="ifForm" onchange="javascript:submitForm('showInvestementFullfillment.do?method=showInvFullfilmentRefNos')">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="instrumentNames"/>
					</html:select>					
					
				  </td>
                </tr>
 
				<tr align="left" valign="top"> 
                  <td align="left" valign="top" class="ColumnBackground" width="371"> <div align="left">&nbsp;<bean:message key="typeOfFlow"/></div></td>
                  <td align="left" class="tableData" width="476">
					<html:radio  property="inflowOutFlowFlag" value="I" onclick="ioFlagOption()"/><bean:message key="inflow"/>
					<html:radio  property="inflowOutFlowFlag" value="O" onclick="ioFlagOption()"/><bean:message key="outflow"/>
				  </td>
                </tr>
                <tr align="left" valign="top"> 
                  <td align="left" valign="top" class="ColumnBackground" width="371"> <div align="left">&nbsp;<bean:message key="investmentRefNumber"/></div></td>
                  <td align="left" class="tableData" width="476">
					<html:select property="investmentRefNumber" styleId="investment"  name="ifForm"
					onchange="javascript:submitForm('showInvestementFullfillment.do?method=showInvFullfilmentDetails')">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="investmentRefNumbers"/>
					</html:select>

				  </td>
                </tr>
                <tr align="left" valign="top"> 
                  <td align="left" valign="top" class="ColumnBackground" width="371"> <div align="left">&nbsp;<bean:message key="receiptNumber"/></div></td>
                  <td align="left" class="tableData" width="476">
				<html:text property="receiptNumber" maxlength="25" styleId="instrumentNumber" size="20"/>
		  </td>
                </tr>                
              </table></td>
          </tr>
          <tr > 
            <td height="20" >&nbsp;
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="35%" class="Heading">&nbsp;<bean:message key="instrumentDetails"/></td>
                  <td colspan="3"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                </tr>
                <tr>
                  <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                </tr>
              </table>
              <table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr>
                  <td class="ColumnBackground" width="376">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentType"/></td>
                  <td class="tableData" width="476">
					<html:select property="instrumentType" styleId="instrumentType"  name="ifForm" onchange="disableCheque()">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="instrumentTypes"/>
					</html:select>
                </tr>

				<TR>
					<TD align="left" valign="top" class="ColumnBackground">
						<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="chqBankName"/>
					</TD>
					<TD align="left" valign="top" class="TableData">
							<html:select property="bnkName" name="ifForm" >
								<html:option value="">Select</html:option>
								<html:options name="ifForm" property="bankAcctDetails"></html:options>
							</html:select>	
					</TD>

				</TR>

                <tr>
                  <td class="ColumnBackground" width="376">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentNumber"/></td>
                  <td class="tableData" width="476"><html:text property="instrumentNumber"  styleId="instrumentNumber" size="20" maxlength="20"/></td>
                </tr>
                <tr>
                  <td class="ColumnBackground" width="376">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<span id="instdate" name="instdate"><bean:message key="instrumentDate"/></span></td>
                  <td class="tableData" width="476">
                    <table cellpadding="0" cellspacing="0">
                      <tr>
                        <td><html:text property="instrumentDate"  styleId="instrumentDate" size="20" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/>
                        <img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.instrumentDate')" align="center"></tr>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td class="ColumnBackground" width="376">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentAmount"/></td>
                  <td class="tableData" width="476">
                  <html:text property="instrumentAmount"  styleId="instrumentAmount" size="20" maxlength="16" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)"/></td>
                </tr>
                <tr>
                  <td class="ColumnBackground" width="376">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="drawnAtBank"/></td>
                  <td class="tableData" width="476"><html:text property="drawnBank"  styleId="drawnBank" size="20" maxlength="50"/></td>
                </tr>
                <tr>
                  <td class="ColumnBackground" width="376">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="drawnAtBranch"/></td>
                  <td class="tableData" width="476"><html:text property="drawnBranch"  styleId="drawnBranch" size="50" maxlength="20" /></td>
                </tr>
                <tr>
                  <td class="ColumnBackground" width="376">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="payableAt"/></td>
                  <td class="tableData" width="476"><html:text property="payableAt"  styleId="payableAt" size="20" maxlength="50"/></td>
                </tr>
              </table>
              &nbsp;</td>
          </tr>
          <tr > 
            <td align="center" valign="baseline" > <div align="center"><html:link href="javascript:submitForm('updateInvestementFullfillment.do?method=updateInvestementFullfillment')"><img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></html:link> 
              </div></td>
          </tr>
        </table></td>
      <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
    </tr>
    <tr> 
      <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
      <td background="images/TableBackground2.gif" width="864">&nbsp;</td>
      <td width="23" align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
    </tr>
  </table>

  <br>
  <p>&nbsp;</p>
</html:form>
</body>