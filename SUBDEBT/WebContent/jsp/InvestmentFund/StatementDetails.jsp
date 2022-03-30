<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showStatementDetails.do?method=showStatementDetails");%>

<html:form action="updateStatementDetails.do?method=updateStatementDetails" method="POST" focus="bankName">
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
				<A HREF="javascript:submitForm('helpBankStatementDetails.do?method=helpBankStatementDetails')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="1" cellpadding="1">
                <tr >
			<TD colspan="13">
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR>
						<TD width="18%" class="Heading">
						<bean:message key="enterStatementDetails" /></TD>
						<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
					</TR>
					<TR>
						<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
					</TR>

				</TABLE>
			</TD>
                </tr>
                <tr align="left" valign="top">
                  <td colspan="2" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="bankName"/>
				  &nbsp;</div></td>
                  <td colspan="11" valign="top" class="tableData">
                  	<html:select property="bankName" name="ifForm">
                  	<html:option value="">Select</html:option>
                  	<html:options property="bankNames" name="ifForm"/>
                  	</html:select>
                  </td>
                </tr>

                <tr align="left" valign="top">
                  <td  colspan="2" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="openingBalance"/>
				  &nbsp;</div></td>
                  <td colspan="11" align="left" valign="top" class="tableData">
                  	<html:text property="openingBalance" name="ifForm" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16"/>&nbsp;<bean:message key="inRs" />
                  </td>
                </tr>
                <tr align="left" valign="top">
                  <td colspan="2" align="left" valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="closingBalance"/></div></td>
                  <td colspan="11" align="left" class="tableData">
                  <html:text property="closingBalance" name="ifForm" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16"/>&nbsp;<bean:message key="inRs" />
		  </td>
                </tr>
		<tr>
			<td colspan="2" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="creditPendingForTheDay"/></td>
			<td colspan="11" class="tableData">
			<html:text property="creditPendingForTheDay" name="ifForm" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16"/>&nbsp;<bean:message key="inRs" />
			</td>
		</tr>


		<tr>
			<td colspan="2" class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="statementDate"/></td>
			<td colspan="11" class="tableData">
			<html:text property="statementDate" name="ifForm" maxlength="10" name = "ifForm" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/>
		 <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('ifForm.statementDate')" align="center">
			</td>
		</tr>

<!--		<TR>
	<TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font>
		  &nbsp;<bean:message key="statementDate" /> 
	</TD>
	  <TD align="left" valign="center" class="TableData">
		   <html:text property="statementDate" size="20"  alt="Reference" name="ifForm" maxlength = "10" />
		  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('ifForm.statementDate')" align="center">
	  </TD>
	  </TR>
-->

		<bean:define id="transactionTemp" property="transactions" name="ifForm"/>
		<%
			java.util.Map map=(java.util.Map)transactionTemp;
			int mapSize=map.size();

			int counter=0;
		%>
		<logic:iterate id="transaction" property="transactions" name="ifForm">
		<%
		counter++;
		java.util.Map.Entry entry=(java.util.Map.Entry)transaction;

		String key=(String)entry.getKey();

		//System.out.println(key);

		String fromToProperty="transactions("+key+")."+"transactionFromTo";
		String transactionNature="transactions("+key+")."+"transactionNature";
		String transactionDate="transactions("+key+")."+"transactionDate";
		String transactionAmount="transactions("+key+")."+"transactionAmount";
		String valueDate="transactions("+key+")."+"valueDate";
		String chequeNumber="transactions("+key+")."+"chequeNumber";
		


		%>
                <tr align="left" valign="top">
                  <td  valign="top" class="ColumnBackground"> <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="transactionFromTo"/>
				  &nbsp;</div></td>
                  <td align="left" valign="top" class="tableData">
                  	<html:text property="<%=fromToProperty%>" name="ifForm" maxlength="100"/>
                  </td>
                  <td align="left" valign="top" class="ColumnBackground"> <div align="left">&nbsp;<bean:message key="transactionNature"/></div></td>
                  <td align="left" class="tableData">
                  <bean:message key="credit"/><html:radio property="<%=transactionNature%>" name="ifForm" value="C"/>
                  <bean:message key="debit"/><html:radio property="<%=transactionNature%>" name="ifForm" value="D"/>
		  </td>
			<td class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="transactionDate"/></td>
			<td class="tableData">
			<html:text property="<%=transactionDate%>" name="ifForm" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/><!-- <img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.transactionDate')" align="center">-->
			</td>


		  </td>
			<td class="ColumnBackground">&nbsp;&nbsp;<bean:message key="valueDate"/></td>
			<td class="tableData">
			<html:text property="<%=valueDate%>" name="ifForm" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/><!-- <img src="images/CalendarIcon.gif" onclick="showCalendar('ifForm.valueDate')" align="center"> -->
			</td>

		  </td>
			<td class="ColumnBackground">&nbsp;&nbsp;<bean:message key="chequeNumber"/></td>
			<td class="tableData">
			<html:text property="<%=chequeNumber%>" name="ifForm" size="20" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>
			</td>



			<td class="ColumnBackground">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="transactionAmount"/></td>
			<td class="tableData">
			<html:text property="<%=transactionAmount%>" name="ifForm" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidDecimal(this)" maxlength="16"/>
			<%
			if(mapSize==counter)
			{%>
			<a href="javascript:submitForm('addMoreTransactionDetails.do?method=addMoreTransactionDetails')">Add more</a>
			<%}%>
			</td>
		</tr>
		</logic:iterate>
		<tr>
			<td colspan="2" class="ColumnBackground">&nbsp;<bean:message key="remarks"/></td>
			<td colspan="11" class="tableData">
			<html:textarea property="remarks" cols="75" rows="5" alt="Message" name="ifForm" />
			</td>
		</tr>

              </table></td>
          </tr>
          <tr >
            <td height="20" >&nbsp;</td>
          </tr>
          <tr >
            <td align="center" valign="baseline" > <div align="center"><html:link href="javascript:submitForm('updateStatementDetails.do?method=updateStatementDetails')"><img src="images/Save.gif" alt="Save" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></html:link>
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
