<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%
String flag = (String)session.getAttribute("ceilingFlag");
String focusObj="maturityType";
if (flag.equals("0"))
{
	session.setAttribute("CurrentPage","showMaturityWiseCeiling.do?method=showMaturityWiseCeiling");
}
else if (flag.equals("1"))
{
	session.setAttribute("CurrentPage","fetchMaturityWiseCeiling.do?method=fetchMaturityWiseCeiling");
	focusObj="ceilingStartDate";
}
%>

<html:form action="setMaturityWiseCeiling.do?method=setMaturityWiseCeiling" method="POST" focus="<%=focusObj%>">

  <table width="100%" border="0" cellspacing="0" cellpadding="0">
  <html:errors />
    <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="323" background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td align="right" background="images/TableBackground1.gif"> </td>
      <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0" height="162">
	<tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpMaturityWiseCeiling.do?method=helpMaturityWiseCeiling')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
        <tr>
            <td height="162"> <table border="0" cellspacing="1" cellpadding="0" height="112">
                <tr> 
                  <td colspan="2" ><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="35%" class="Heading">&nbsp;
						<bean:message key="maturityWiseCeiling" /> 
						</td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
                <tr>
                  <td width="360" class="ColumnBackground" height="25"> &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;
				  <bean:message key="maturityType"/>
                    </td>
                  <td class="tableData" width="474" height="25"> <div align="left"> 
						<html:select property="maturityType" name="ifForm" onchange="javascript:submitForm('fetchMaturityWiseCeiling.do?method=fetchMaturityWiseCeiling')">
						<html:option value="">Select</html:option>
						<html:options property="maturities" name="ifForm"/>
						</html:select>

                    </div></td>
                </tr>
		<TR>
		  <TD align="left" valign="top" class="ColumnBackground" > <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="ceilingStartDate"/></div></TD>
		  <TD align="left" class="tableData" align="center" >
			<html:text property="ceilingStartDate" name="ifForm" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/><img src="images/CalendarIcon.gif" onClick="showCalendar('ifForm.ceilingStartDate')" align="center">
		  </TD>
		</TR>
		<TR align="left" valign="top">
		  <TD align="left" valign="top" class="ColumnBackground" > <div align="left">&nbsp;<bean:message key="ceilingEndDate"/></div></TD>
		  <TD align="left" class="tableData" align="center" >
			<html:text property="ceilingEndDate" name="ifForm" maxlength="10" onkeypress="return dateOnly(this, event)" onkeyup="isValidDate(this)"/><img src="images/CalendarIcon.gif" onClick="showCalendar('ifForm.ceilingEndDate')" align="center">
		  </TD>
		</TR>
		<TR align="left" valign="top">
		  <TD align="left" valign="top" class="ColumnBackground" > <div align="left">&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="ceilingLimit"/></div></TD>
		  <TD align="left" class="tableData" >
			<html:text property="ceilingLimit" name="ifForm" maxlength="5" onkeypress="return decimalOnly(this, event, 3)" onkeyup="isValidDecimal(this)"/>&nbsp;<bean:message key="inPer"/>
		  </TD>
		</TR>                
              </table>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center"><html:link href="javascript:submitForm('setMaturityWiseCeiling.do?method=setMaturityWiseCeiling')"><img src="images/Save.gif" alt="Ok" width="49" height="37" border="0"></html:link><html:link href="javascript:document.ifForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
