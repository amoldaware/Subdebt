<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showMutualFundsDetails.do?method=showMutualFundsDetails");%>
<%
String flag = (String)session.getAttribute("invFlag");
String focusField="investmentReferenceNumber";
if (flag.equals("0"))
{
	session.setAttribute("CurrentPage","showMutualFundsDetails.do?method=showMutualFundsDetails");
}
else if (flag.equals("1"))
{
session.setAttribute("CurrentPage","showMutualFundsDetails.do?method=showRatingDetailsForMutualFunds");

}
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
	focusField="test";
}
%>
<html:form action="updateMutualFundsDetails.do?method=updateMutualFundsDetails" method="POST" enctype="multipart/form-data" focus="<%=focusField%>">
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
    <td align="right" valign="bottom" width="20"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
    <td width="43" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr>
    <td background="images/TableVerticalLeftBG.gif" width="20">&nbsp;</td>
    <td width="713"><table width="647" border="0" cellspacing="1" cellpadding="0">
	<tr>
	  <TD colspan="3">			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpMutualFundsDetails.do?method=helpMutualFundsDetails')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
          <tr> 
            <td colspan="3" width="735"><table width="645" border="0" cellspacing="0" cellpadding="0">
                <tr> 
                  <td width="207" class="Heading">&nbsp;<bean:message key="enterMutualFundDetails"/></td>
                  <td width="19"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                  <td width="32">&nbsp;</td>
                  <td class="subHeading2" width="379"><div align="right">&nbsp;</div></td>
                </tr>
                <tr> 
                  <td colspan="4" class="Heading" width="598"><img src="images/Clear.gif" width="5" height="5"></td>
                </tr>
              </table></td>
          </tr>
                <tr align="left" valign="top"> 
                  <td width="20%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="investmentReferenceNumber"/>&nbsp;</div></td>
                  <td align="left" valign="top" class="tableData" colspan="2">
					<html:select property="investmentReferenceNumber" styleId="investeeName"  name="ifForm" onchange="javascript:submitForm('showMutualFundsDetails.do?method=showInvDetails')">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="investmentRefNumbers"/>
					</html:select>&nbsp;&nbsp;
	              </td>
                </tr>          
			<tr align="left" valign="top"> 
			  <td width="30%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="investee"/></div></td>
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
			  <td align="left" valign="top" class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="instrument"/></div></td>
			  <td align="left" class="tableData" colspan="2">&nbsp;<bean:message key="mutualFunds"/></td>
				<html:hidden property="instrumentName" alt="instrumentName" value="Mutual Funds"/>

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
			<html:select property="agency" styleId="agencyName" onchange="javascript:submitForm('showMutualFundsDetails.do?method=showRatingDetailsForMutualFunds')"  name="ifForm">
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
			
          <tr> 
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="mutualFundId"/></td>
            <td class="tableData" width="378" colspan="2"> <html:text property="mutualFundId" maxlength="25"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="parValue"/></td>
            <td class="tableData" width="378" colspan="2"> <html:text property="parValue" maxlength="16" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)"/>&nbsp<bean:message key="inRs"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="dateOfPurchase"/></td>
            <td class="tableData" width="378" colspan="2"> <html:text property="dateOfPurchase"  styleId="dateOfPurchase" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/>&nbsp;
              <img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.dateOfPurchase')" align="center" width="24" height="22"></td>
          </tr>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="navOnDateOfPurchase"/></td>
            <td class="tableData" width="378" colspan="2"> <html:text property="navAsOnDateOfPurchase" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="navAsOnDate"/> &nbsp;</td>
            <td class="tableData" width="378" colspan="2"> <html:text property="navAsOnDate" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="noOfUnits"/> </td>
            <td class="tableData" width="378" colspan="2"> <html:text property="noOfUnits" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" disabled="true"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="costOfPurchase"/></td>
            <td class="tableData" width="378" colspan="2"> <html:text property="costOfPurchase" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16" disabled="true"/>
			<bean:message key="inRs"/></td>
          </tr>          
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="isinNumber"/></td>
            <td class="tableData" width="378" colspan="2"> <html:text property="isinNumber" maxlength="25"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="mutualFundName"/> </td>
            <td class="tableData" width="378" colspan="2"> <html:text property="mutualFundName" maxlength="50"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<bean:message key="openOrClose"/></td>
            <td class="tableData" width="378" colspan="2"> 
			<html:radio property="openOrClose" value="O"/> Open <html:radio property="openOrClose" value="C"/> Close</td>
          </tr>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="dateOfSelling"/></td>
            <td class="tableData" width="378" colspan="2">&nbsp;<html:text  property="dateOfSelling" styleId="dateOfSelling" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/>  <img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.dateOfSelling')" align="center">
</td>
          </tr>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="schemeNature"/></td>
            <td class="tableData" width="378" colspan="2">
				<html:select property="schemeNature" styleId="schemeNature"  name="ifForm">
					<html:option value="">Select </html:option>
					<html:options name="ifForm" property="instrumentSchemeTypes"/>
				</html:select>
			</td>
          </tr>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="exitLoad"/></td>
            <td class="tableData" width="378" colspan="2"> <html:text property="exitLoad" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16"/>&nbsp<bean:message key="inRs"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="entryLoad"/></td>
            <td class="tableData" width="378" colspan="2"> <html:text property="entryLoad" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16"/> &nbsp<bean:message key="inRs"/></td>
          </tr>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="markToMarket"/></td>
            <td class="tableData" width="378" colspan="2"> <html:text property="markToMarket" maxlength="5" /></td>
          </tr>
          <tr>
            <td class="ColumnBackground" width="353">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="referenceNo"/> </td>
            <td class="tableData" width="378" colspan="2"> <html:text property="referenceNumber" maxlength="20" /></td>
          </tr>
          <tr align="center" valign="baseline"> 
            <td colspan="3" width="735"> <div align="center"><html:link href="javascript:submitForm('updateMutualFundsDetails.do?method=updateMutualFundsDetails')"><img src="images/Save.gif" alt="Apply" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div></td>
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
