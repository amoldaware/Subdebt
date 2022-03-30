<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="java.text.DecimalFormat"%>
<% 
String flag= (String)session.getAttribute("flag");
if (flag.equals("1"))
{
	session.setAttribute("CurrentPage","showBankAccountMaster.do?method=showBankAccountMaster");
}
else if (flag.equals("2"))
{
	session.setAttribute("CurrentPage","showBankAccountMaster.do?method=showBankAccountList");
}
else if (flag.equals("3"))
{
	session.setAttribute("CurrentPage","showBankAccountMaster.do?method=showBankAccountDetails");
}
DecimalFormat df= new DecimalFormat("######################.##");
df.setDecimalSeparatorAlwaysShown(false);
%>

<html:form action="updateBankAccountMaster.do?method=updateBankAccountMaster" method="POST" focus="bankName">
<html:errors />
  <table width="100%" border="0" cellpadding="0" cellspacing="0">
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
				<A HREF="javascript:submitForm('helpBankAccountMaster.do?method=helpBankAccountMaster')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
          <tr> 
            <td><table width="100%" border="0" cellspacing="1" cellpadding="1">
                <tr > 
			<TD colspan="4"> 
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR>
						<TD width="31%" class="Heading">
						<bean:message key="enterBankAccountDetails" /></TD>
						<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
					</TR>
					<TR>
						<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
					</TR>

				</TABLE>
			</TD>
                </tr>
				<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="accountType" /> 
					<td class="tableData" align="center">
						<html:radio name="ifForm" value="S" property="accountType" onclick="javascript:submitForm('showBankAccountMaster.do?method=showBankAccountList')"/><bean:message key="savingsAccount"/>
						<html:radio  name="ifForm" value="C" property="accountType" onclick="javascript:submitForm('showBankAccountMaster.do?method=showBankAccountList')"/><bean:message key="currentAccount"/>
					</td>
				</tr>
				<tr>
					<TD align="left" valign="top" class="ColumnBackground">
						&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="selectAccountNumber" />
					</TD>
					<TD align="left" class="TableData">
						<html:select property="accComb" name="ifForm" onchange="javascript:submitForm('showBankAccountMaster.do?method=showBankAccountDetails')">
							<html:option value="">Select</html:option>
							<html:options property="bankNames" name="ifForm"/>			
						</html:select>
					</TD>
				</tr>
				<tr align="left" valign="top"> 
                  <td width="20%" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="bankName"/>
				  &nbsp;</div></td>
                  <td align="left" valign="top" class="tableData">
                  	<html:text property="bankName" name="ifForm" maxlength="20" />
                  </td>
                </tr>
                <tr align="left" valign="top"> 
                  <td align="left" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="bankBranchName"/></div></td>
                  <td align="left" class="tableData">
                  <html:text property="bankBranchName" name="ifForm" maxlength="20"/>
					</td>
                </tr>
				<tr>
					<td class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="modAccountNumber"/></td>
					<td class="tableData">
					<html:text property="accountNumber" name="ifForm" maxlength="20"/>
					</td>
				</tr>
				<tr>
					<td class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="minBalance"/></td>
					<td class="tableData">
					<bean:define id="balVal" name="ifForm" property="minBalance"/>
					<%
						double balance = ((Double)balVal).doubleValue();
						String strBalance = df.format(balance);
					%>
					<html:text property="minBalance" name="ifForm" value="<%=strBalance%>" maxlength="16" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)"/>
					</td>
				</tr>
              </table></td>
          </tr>
          <tr > 
            <td height="20" >&nbsp;</td>
          </tr>
          <tr > 
            <td align="center" valign="baseline" > <div align="center">
			<html:link href="javascript:submitForm('updateBankAccountMaster.do?method=updateBankAccountMaster')">
			<img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0">
			</html:link> 
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
