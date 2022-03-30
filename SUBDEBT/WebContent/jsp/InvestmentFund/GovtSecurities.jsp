<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.lang.Double"%>
<%
DecimalFormat df= new DecimalFormat("######################.##");
df.setDecimalSeparatorAlwaysShown(false);
%>
<% 
String flag = (String)session.getAttribute("invFlag");
String focusObj="investmentReferenceNumber";
if (flag.equals("0"))
{
	session.setAttribute("CurrentPage","showGovtSecuritiesDetails.do?method=showGovtSecuritiesDetails");
}
else if (flag.equals("1"))
{
	session.setAttribute("CurrentPage","showGovtSecuritiesDetails.do?method=showInvDetails");
	focusObj="maturityName";
}
else if (flag.equals("2"))
{
	session.setAttribute("CurrentPage","showCalGovtSecuritiesDetails.do?method=calMaturityDateAmt");
	focusObj="callOrPutDuration";
}
else if (flag.equals("3"))
{
session.setAttribute("CurrentPage","showGovtSecuritiesDetails.do?method=showRatingDetailsForGovtSecurities");
	focusObj="callOrPutDuration";
}
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
	focusObj="test";
}
%>
<body >
<html:form action="updateGovtSecuritiesDetails.do?method=updateGovtSecuritiesDetails" method="POST" enctype="multipart/form-data" focus="<%=focusObj%>">
<html:hidden name="ifForm" property="test"/>
<html:errors />
  <html:link href="#">
                      <html:hidden property="namenext"  styleId="namenext" value=" "/>
  </html:link>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td class="Fontstyle">&nbsp;</td>
    </tr>
  </table>
  <table width="718" border="0" cellspacing="0" cellpadding="0" align="center">
    <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td width="20" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
  <tr>
    <td background="images/TableVerticalLeftBG.gif" width="20">&nbsp;</td>
    <td width="713">
    	<table width="647" border="0" cellspacing="1" cellpadding="0">
	<tr>
	  <TD colspan="3">			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpGovtSecuritiesDetails.do?method=helpGovtSecuritiesDetails')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
          <tr> 
            <td colspan="3" width="735">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="224" class="Heading">&nbsp;Enter Govt.
                    Securities Details</td>
                  <td width="19"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                  <td width="15">&nbsp;</td>
                  <td class="subHeading2" ><div align="right">&nbsp;</div></td>
                </tr>
                <tr> 
                  <td colspan="4" class="Heading" ><img src="images/Clear.gif" width="5" height="5"></td>
                </tr>
              </table></td>
          </tr>
                <tr align="left" valign="top"> 
                  <td width="30%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="investmentReferenceNumber"/>&nbsp;</div></td>
                  <td align="left" valign="top" class="tableData" colspan="2">
					<html:select property="investmentReferenceNumber" styleId="investeeName"  name="ifForm" onchange="javascript:submitForm('showGovtSecuritiesDetails.do?method=showInvDetails')">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="investmentRefNumbers"/>
					</html:select>&nbsp;&nbsp;
	              </td>
                </tr>          
			<tr align="left" valign="top"> 
			  <td width="20%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;Investee&nbsp;</div></td>
			  <td align="left" valign="top" class="tableData" colspan="2">
				<html:select property="investeeName" styleId="investeeName"  name="ifForm" disabled="true">
					<html:option value="">Select </html:option>
					<html:options name="ifForm" property="investeeNames"/>
				</html:select>&nbsp;&nbsp;
			  </td>
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
			  <td align="left" valign="top" class="ColumnBackground"> <div align="left">&nbsp;Instrument</div></td>
			  <td align="left" class="tableData" colspan="2">&nbsp;<bean:message key="govtSecurities"/></td>
			  <html:hidden property="instrumentName" alt="instrumentName" value="Government Securities"/>
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
			<html:select property="agency" styleId="agencyName" onchange="javascript:submitForm('showGovtSecuritiesDetails.do?method=showRatingDetailsForGovtSecurities')"  name="ifForm">
					<html:option value="">Select </html:option>
					<html:options name="ifForm" property="agencies"/>
				</html:select>&nbsp;&nbsp;
			  </td>
			</tr>  

			<tr align="left" valign="top"> 
			  <td width="30%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
                  <bean:message key="rating"/>&nbsp;</div></td>
			  <td align="left" valign="top" class="tableData">
				<html:select property="rating" styleId="rating"  name="ifForm">
					<html:option value="">Select </html:option>
					<html:options name="ifForm" property="instrumentRatings"/>
				</html:select>&nbsp;&nbsp;
			  </td>
			  <td align="left" valign="top" class="ColumnBackground">
			  Rating Ceiling Set : <bean:write property="ratingCeiling" name="ifForm"/>
			  </td>
			</tr>			
		<bean:define id="faceVal" name="ifForm" property="faceValue"/>
		<%
			double dFaceVal = Double.parseDouble((String)faceVal);
			String sFaceVal = df.format(dFaceVal);
		%>
          <tr> 
            <td class="ColumnBackground" >&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="seriesName"/></td>
            <td class="tableData"  colspan="2"> <html:text property="seriesName" maxlength="50"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" >&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="faceValue"/></td>
            <td class="tableData" colspan="2" > <html:text property="faceValue" maxlength="16" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" value="<%=sFaceVal%>"/> &nbsp<bean:message key="inRs"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" >&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="noOfSecurities"/></td>
            <td class="tableData" colspan="2" > <html:text property="numberOfSecurities" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" disabled="true"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" >&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="folioNumber"/></td>
            <td class="tableData" colspan="2" > <html:text property="folioNumber" maxlength="25"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" >&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="certificateNumber"/></td>
            <td class="tableData" colspan="2" > <html:text property="certificateNumber" maxlength="25"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" >&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="costOfPurchase"/></td>
            <td class="tableData"  colspan="2"> <html:text property="costOfPurchase" maxlength="16" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" disabled="true"/> &nbsp<bean:message key="inRs"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" >&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="couponRate"/></td>
            <td class="tableData" colspan="2" > <html:text property="couponRate" maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)" /> <bean:message key="inPa" /></td>
          </tr>
          <tr>
            <td class="ColumnBackground" >&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="tenureType"/></td>
            <td class="tableData" colspan="2" > 
			<html:radio  property="tenureType" value="D" />Days&nbsp;&nbsp;
              <html:radio  property="tenureType" value="M"/>Months&nbsp;&nbsp; <html:radio  property="tenureType" value="Y"/>Years&nbsp;&nbsp;&nbsp;
              <html:text property="tenure"  size="9" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" /> </td>
          </tr>
          <tr>
            <td class="ColumnBackground" >&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="dateOfInvestment"/></td>
            <td class="tableData"  colspan="2"><html:text property="dateOfInvestment" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/><img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.dateOfInvestment')">&nbsp; &nbsp; <a href="javascript:submitForm('showCalGovtSecuritiesDetails.do?method=calMaturityDateAmt')">Calculate Maturity Date and Amount</a>
			</td>
          </tr>
          <tr>
            <td class="ColumnBackground" >&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="maturityAmount"/></td>
            <td class="tableData" colspan="2" > <html:text property="maturityAmount" maxlength="16" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)"/> &nbsp<bean:message key="inRs"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" >&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; <bean:message key="maturityDate"/></td>
            <td class="tableData" colspan="2" > <html:text property="maturityDate" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/><img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.maturityDate')"></td>
          </tr>
		<bean:define id="ytmVal" name="ifForm" property="ytmValue"/>
		<%
			double dYtm = ((Double)ytmVal).doubleValue();
			String sYtm = df.format(dYtm);
		%>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="ytmValue"/> </td>
            <td class="tableData" width="378" colspan="2"> <html:text property="ytmValue" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16" value="<%=sYtm%>"/>
			</td>
          </tr>
          <tr>
            <td class="ColumnBackground" >&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp; <bean:message key="callOrPutOption"/></td>
            <td class="tableData" colspan="2" > <html:radio property="callOrPutOption" value="C"/> Call <html:radio property="callOrPutOption" value="P"/> Put</td>
          </tr>
          <tr>
            <td class="ColumnBackground" >&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="CallOrPutDuration"/></td>
            <td class="tableData" colspan="2" > <html:text property="callOrPutDuration" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/> <bean:message key="inMonths"/></td>
          </tr>
          <tr align="center" valign="baseline"> 
            <td colspan="3" width="735"> <div align="center"><html:link href="javascript:submitForm('updateGovtSecuritiesDetails.do?method=updateGovtSecuritiesDetails')"><img src="images/Save.gif" alt="Apply" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div></td>
          </tr>
        </table></td>
    <td background="images/TableVerticalRightBG.gif" width="43">&nbsp;</td>
  </tr>
  <tr>
    <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
    <td background="images/TableBackground2.gif" width="713"><div align="center"></div></td>
    <td align="left" valign="top" width="43"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
  </tr>
</table>
</html:form>
</body>