<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showPlanInvestmentBuyOrSell.do");%>

<html:form action="updatePlanInvestmentBuyOrSell.do?method=updatePlanInvestmentBuyOrSell" method="POST" enctype="multipart/form-data">

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
            <td><table width="100%" border="0" cellspacing="1" cellpadding="1">
                <tr > 
                  <td colspan="2"></td>
                </tr>
				<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="modeOfTransaction"/></td>
					<td class="tableData">
						<html:radio  property="isBuyOrSellRequest" value ="B"/>Buy
						<html:radio  property="isBuyOrSellRequest" value ="S"/>Sell
					</td>
				</tr>
                <tr align="left" valign="top"> 
                  <td width="20%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;
				  <bean:message key="investee"/></div></td>
                  <td align="left" valign="top" class="tableData">
				<html:select property="investeeName" styleId="investeeName"  name="ifForm">
					<html:option value="">Select </html:option>
					<html:options name="ifForm" property="investeeNames"/>
				</html:select>
                  </td>
                </tr>
                <tr align="left" valign="top"> 
                  <td align="left" valign="top" class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="instrumentName"/></div></td>
                  <td align="left" class="tableData">
					  <html:select property="instrumentName" styleId="instrumentName"  name="ifForm">
						<html:option value="">Select </html:option>
						<html:options name="ifForm" property="instrumentTypes"/>
					  </html:select>
				  </td>
                </tr>
				<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="numberOfUnits"/> </td>
					<td class="tableData">
						<html:text  property="noOfUnits"/>
					</td>
				</tr>
              </table></td>
          </tr>
          <tr > 
            <td height="20" >&nbsp;</td>
          </tr>
          <tr > 
            <td align="center" valign="baseline" > <div align="center"><html:link href="javascript:submitForm('updatePlanInvestmentBuyOrSell.do?method=updatePlanInvestmentBuyOrSell')"><img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></html:link> 
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
