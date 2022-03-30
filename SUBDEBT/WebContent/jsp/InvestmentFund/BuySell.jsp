<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp"%>
<% 
String flag = (String)session.getAttribute("flag");
if (flag.equals("0"))
{
	session.setAttribute("CurrentPage","showMakeRequestBuyOrSell.do?method=showMakeRequestBuyOrSell");
}
else if (flag.equals("1"))
{
	session.setAttribute("CurrentPage","showBuyOrSellInvRefNos.do?method=showBuyOrSellInvRefNos");
}
%>
<body onload="disableUnits(this)">
<html:form action="updateMakeRequestBuyOrSell.do?method=updateMakeRequestBuyOrSell" method="POST" focus="investeeName">
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
				<A HREF="javascript:submitForm('helpMakeRequestDetails.do?method=helpMakeRequestDetails')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
	<TR>
		<TD colspan="4"> 
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="31%" class="Heading">
					<bean:message key="make_request" /></TD>
					<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
				</TR>
				<TR>
					<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
				</TR>

			</TABLE>
		</TD>

	</TR>      
          <tr> 
            <td>
            	<table width="100%" border="0" cellspacing="1" cellpadding="1">
		<tr>
			<td class="ColumnBackground" width="242">&nbsp;<bean:message key="modeOfTransaction"/></td>
			<td class="tableData" width="605">
				<html:radio  property="isBuyOrSellRequest" value="B" onclick="enableInvRefNo()"/>Buy
				<html:radio  property="isBuyOrSellRequest" value="S" onclick="enableInvRefNo()"/>Sell
			</td>
		</tr>
                <tr align="left" valign="top"> 
                  <td width="242" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="investee"/> </div></td>
                  <td align="left" valign="top" class="tableData" width="605">
				<html:select property="investeeName" styleId="investeeName"  name="ifForm" onchange="disableUnitsSubmitForm()">
					<html:option value="">Select </html:option>
					<html:options name="ifForm" property="investeeNames"/>
				</html:select>
                  </td>
                </tr>
                <tr align="left" valign="top"> 
                  <td align="left" valign="top" class="ColumnBackground" width="242"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="instrumentName"/></div></td>
                  <td align="left" class="tableData" width="605">
					  <html:select property="instrumentName" styleId="instrumentName"  name="ifForm" onchange="disableUnits(this);submitForm('showBuyOrSellInvRefNos.do?method=showBuyOrSellInvRefNos');">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="instrumentNames"/>
					  </html:select>
				  </td>
                </tr>
				<tr align="left" valign="top"> 
                  <td width="242" valign="top" class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="investmentReferenceNumber"/>&nbsp;</div></td>
                  <td align="left" valign="top" class="tableData">
					<html:select property="investmentRefNumber" name="ifForm" >
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="investmentRefNumbers"/>
					</html:select>
	              </td>
                </tr>
			  <bean:define id="nuVal" name="ifForm" property="noOfUnits"/>
			  <%
			  String reqVal1 = (String)nuVal;
			  if (request.getParameter("noOfUnits")!=null)
			  {
				reqVal1 = (String)request.getParameter("noOfUnits");
			  }
			  %>
                <tr>
					<td class="ColumnBackground" width="242">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="numberOfUnits"/></td>
					<td class="tableData" width="605">
						<html:text  property="noOfUnits" maxlength="5" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
					</td>
                </tr>
			  <bean:define id="wuVal" name="ifForm" property="worthOfUnits"/>
			  <%
			  String reqVal2 = (String)wuVal;
			  if (request.getParameter("worthOfUnits")!=null)
			  {
				reqVal2 = (String)request.getParameter("worthOfUnits");
			  }
			  %>
                <tr>
					<td class="ColumnBackground" width="242">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="worthAmount"/>&nbsp;</td>
					<td class="tableData" width="605">
						<html:text  property="worthOfUnits" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
                        <bean:message key="inRs"/>
					</td>
                </tr>
                <%--
                <tr>
                  <td align="left" valign="top" class="ColumnBackground" width="242"> <div align="left">&nbsp;<bean:message key="referenceNumberOrCertificateNumber"/></div></td>
                  <td align="left" class="tableData" width="605">
					  <html:select property="investmentRefNumber" styleId="investmentRefNumber"  name="ifForm">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="receiptNumbers"/>
					  </html:select>
				  </td>
                </tr>
                --%>
              </table></td>
          </tr>
          <tr > 
            <td height="20" >&nbsp;</td>
          </tr>
          <tr > 
            <td align="center" valign="baseline" > <div align="center"><html:link href="javascript:submitForm('updateMakeRequestBuyOrSell.do?method=updateMakeRequestBuyOrSell')"><img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></html:link> 
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