<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% 
String flag = (String)session.getAttribute("invFlag");
String focusObj="investmentReferenceNumber";
if (flag.equals("0"))
{
	session.setAttribute("CurrentPage","showFixedDetails.do?method=showFixedDetails");
}
else if (flag.equals("1"))
{
	session.setAttribute("CurrentPage","showFixedDetails.do?method=showInvDetails");
	focusObj="maturityName";
}
else if (flag.equals("2"))
{
	session.setAttribute("CurrentPage","showCalFixedDetails.do?method=calMaturityDateAmt");
	focusObj="maturityAmount";
}
else if (flag.equals("3"))
{
	session.setAttribute("CurrentPage","showFixedDetails.do?method=showRatingDetailsForFixedDeposit");
	focusObj="maturityAmount";
}
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
	focusObj="test";
}
%>
<body >
<html:form action="updateFixedDetails.do?method=updateFixedDetails" method="POST" focus="<%=focusObj%>">
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
      <td>
      <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
	  <tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpFixedDepositDetails.do?method=helpFixedDepositDetails')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
	<TR>
		<TD colspan="4"> 
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="31%" class="Heading">
					<bean:message key="enterFixedDepositDetails" /></TD>
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
                <tr > 
                  <td colspan="2"></td>
                </tr>
                <tr align="left" valign="top"> 
                  <td width="20%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="investmentReferenceNumber"/>&nbsp;</div></td>
                  <td align="left" valign="top" class="tableData" colspan="2">
					<html:select property="investmentReferenceNumber" styleId="investeeName"  name="ifForm" onchange="javascript:submitForm('showFixedDetails.do?method=showInvDetails')">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="investmentRefNumbers"/>
					</html:select>&nbsp;&nbsp;
	              </td>
                </tr>               
				
                <tr align="left" valign="top"> 
                  <td width="20%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="investee"/>&nbsp;</div></td>
                  <td align="left" valign="top" class="tableData" colspan="2">
					<html:select property="investeeName" styleId="investeeName"  name="ifForm" disabled="true">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="investeeNames"/>
					</html:select>&nbsp;&nbsp;
	              </td>
                </tr>

                </tr>

				<TR align="left">
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="instrumentCategory" />
					</TD>
					<TD align="left" class="TableData" colspan="2">
						<html:select property="instrumentCategory" name="ifForm" >
							<html:option value="">Select</html:option>
							<html:options property="instrumentCategories" name="ifForm"/>			
						</html:select>
					</TD>
				</tr>

                <tr align="left" valign="top"> 
                  <td align="left" valign="top" class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="instrument"/></div></td>
                  <td align="left" class="tableData" colspan="2">&nbsp;<bean:message key="fixedDeposit"/></td>
				  <html:hidden property="instrumentName" alt="instrumentName" value="Fixed Deposit"/>
                </tr>

			<tr align="left" valign="top"> 
			  <td width="30%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
                  <bean:message key="maturityType"/>&nbsp;</div></td>
			  <td align="left" valign="top" class="tableData" colspan="2">
				<html:select property="maturityName" styleId="investeeName"  name="ifForm">
					<html:option value="">Select </html:option>
					<html:options name="ifForm" property="maturities"/>
				</html:select>&nbsp;&nbsp;
			  </td>
			</tr>  

			<tr align="left" valign="top"> 
			  <td width="30%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
                  <bean:message key="agencyNames"/>&nbsp;</div></td>
			  <td align="left" valign="top" class="tableData" colspan="2">
			<html:select property="agency" styleId="agencyName" onchange="javascript:submitForm('showFixedDetails.do?method=showRatingDetailsForFixedDeposit')"  name="ifForm">
					<html:option value="">Select </html:option>
					<html:options name="ifForm" property="agencies"/>
				</html:select>&nbsp;&nbsp;
			  </td>
			</tr>  

			<tr align="left" valign="top"> 
			  <td width="30%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
                  <bean:message key="rating"/>&nbsp;</div></td>
			  <td align="left" valign="top" class="tableData">
				<html:select property="rating" styleId="rating"  name="ifForm" >
					<html:option value="">Select </html:option>
					<html:options name="ifForm" property="instrumentRatings"/>
				</html:select>&nbsp;&nbsp;
			  </td>

			  <td align="left" valign="top" class="ColumnBackground">
			  Rating Ceiling Set : <bean:write property="ratingCeiling" name="ifForm"/>
			  </td>
			</tr>	
			
				<tr>
					<td class="ColumnBackground"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="principalAmount"/>
					</div></td>
					<td class="tableData" colspan="2"><div align="left">
						<html:text  property="principalAmount" maxlength="16" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" disabled="true"/> <bean:message key="inRs"/>
					</div></td>
				</tr>
				<tr>
					<td class="ColumnBackground" width="30%"><div align="left">&nbsp;<bean:message key="compoundingFrequency"/> </div></td>
					<td class="tableData" colspan="2"><div align="left">
                        <html:select property="compoundingFrequency" styleId="rating"  name="ifForm">
							<html:option value="">Select </html:option>
							<html:option value="12">Monthly </html:option>
							<html:option value="4">Quarterly </html:option>
							<html:option value="2">Half-Yearly </html:option>
							<html:option value="1">Annually </html:option>
						</html:select>
					</div></td>
				</tr>
				<tr>
					<td class="ColumnBackground"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="interestRate"/> 
					</div></td>
					<td class="tableData" colspan="2"><div align="left">
						<html:text  property="interestRate" maxlength="5" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)" /> <bean:message key="inPa"/> 
					</div></td>
				</tr>
				<tr>
					<td class="ColumnBackground"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="tenureType"/> 
					</div></td>
					<td class="tableData" colspan="2"><div align="left">
                        &nbsp;<html:radio  value="D" property="tenureType"/><bean:message key="days"/>&nbsp;&nbsp;
                        <html:radio  property="tenureType" value="M"/><bean:message key="months"/>&nbsp;&nbsp;
                        <html:radio  property="tenureType" value="Y"/><bean:message key="years"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <html:text  property="tenure" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" />
					</div></td>
				</tr>
				<tr>
					<td class="ColumnBackground"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="fdReceiptNumber"/> 
					</div></td>
					<td class="tableData" colspan="2"><div align="left">
						<html:text  property="receiptNumber" maxlength="25"/>
					</div></td>
				</tr>
				<tr>
					<td class="ColumnBackground"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
					<bean:message key="dateOfDeposit"/>
					</div></td>
					<td class="tableData" align="center" colspan="2"><div align="left">
						<html:text  property="dateOfDeposit" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/><img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.dateOfDeposit')" align="center" width="24" height="22"> &nbsp; &nbsp; <a href="javascript:submitForm('showCalFixedDetails.do?method=calMaturityDateAmt')">Calculate Maturity Date and Amount</a>
					</div></td>
				</tr>
                <tr>
					<td class="ColumnBackground"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="maturityAmount"/>
					</div></td>
					<td class="tableData" colspan="2"><div align="left">
						<html:text  property="maturityAmount" maxlength="16" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)"/> <bean:message key="inRs"/>
					</div></td>
                </tr>
				<tr>
					<td class="ColumnBackground"><div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; 
					<bean:message key="maturityDate"/>
					</div></td>
					<td class="tableData" colspan="2"><div align="left">
						<html:text  property="maturityDate" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/>  <img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.maturityDate')" align="center">
					</div></td>
				</tr>
              </table></td>
          </tr>
          <tr > 
            <td height="20" >&nbsp;</td>
          </tr>
          <tr > 
            <td align="center" valign="baseline" > <div align="center"><html:link href="javascript:submitForm('updateFixedDetails.do?method=updateFixedDetails')"><img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></html:link> 
              </div></td>
          </tr>
        </table></td>
      <td width="20" background="images/TableVerticalRightBG.gif">&nbsp;</td>
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
</body>